package controller.Admin;

import dao.implement.Conexion;
import dao.implement.MarcaDao;
import factory.CelularFactory;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Celular;
import model.Celular.Gama;
import model.Celular.SistemaOperativo;
import model.Marca;


public class AlertaStockService {

    private static final int LIMITE_STOCK = 5;
    private static final String SQL = "SELECT * FROM celular WHERE stock <= ?";
    private static final Path ARCHIVO =
            Path.of("C:/Users/ASUS/Documents/JAVAA/TecnoStoreD/Reporte/stock_bajo.dat");
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final Conexion conexion = Conexion.getInstancia();
    private final MarcaDao marcaDao = new MarcaDao();

    // 1. JDBC -> List<Celular>
    public List<Celular> obtenerStockBajo() throws SQLException {
        List<Celular> lista = new ArrayList<>();

        try (Connection c = conexion.conexion()) {
            if (c == null) {
                throw new SQLException("No se pudo establecer la conexion con la base de datos.");
            }
            try (PreparedStatement ps = c.prepareStatement(SQL)) {
                ps.setInt(1, LIMITE_STOCK);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapear(rs));
                    }
                }
            }
        }
        return lista;
    }

    // Misma logica de mapeo que usa CelularDao
    private Celular mapear(ResultSet rs) throws SQLException {
        Marca marca = marcaDao.obtenerPorId(rs.getInt("id_marca"));

        return CelularFactory.desdeBD(
                rs.getInt("id_celular"),
                rs.getInt("stock"),
                rs.getString("modelo"),
                rs.getDouble("precio"),
                marca,
                SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
                Gama.valueOf(rs.getString("gama")));
    }

    private String formatear(Celular c) {
        return String.format("ID: %d | Modelo: %s | Marca: %s | Stock: %d | Precio: %.0f",
                c.getIdCelular(), c.getModelo(), c.getMarca().getNombre(), c.getStock(), c.getPrecio());
    }

    // 2. Consola
    public void mostrarAlerta(List<Celular> celulares) {
        System.out.println("\n--- ALERTA DE STOCK BAJO ---");
        celulares.stream().map(this::formatear).forEach(System.out::println);
    }

    // 3. Archivo binario .dat con fecha y hora
    // [UTF fechaHora][int cantidad] + por cada celular:
    // [int id][UTF modelo][UTF marca][int stock][double precio]
    public void generarArchivo(List<Celular> celulares) throws IOException {
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(Files.newOutputStream(ARCHIVO)))) {
            out.writeUTF(LocalDateTime.now().format(FORMATO_FECHA));
            out.writeInt(celulares.size());
            for (Celular c : celulares) {
                out.writeInt(c.getIdCelular());
                out.writeUTF(c.getModelo());
                out.writeUTF(c.getMarca().getNombre());
                out.writeInt(c.getStock());
                out.writeDouble(c.getPrecio());
            }
        }
        System.out.println("Archivo generado: " + ARCHIVO.toAbsolutePath());
    }

    // 4. Leer el .dat y mostrarlo
    public void leerArchivo() {
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(ARCHIVO)))) {
            String fechaHora = in.readUTF();
            int cantidad = in.readInt();

            System.out.println("\n--- REPORTE STOCK BAJO (generado: " + fechaHora + ") ---");
            for (int i = 0; i < cantidad; i++) {
                System.out.println(String.format(
                        "ID: %d | Modelo: %s | Marca: %s | Stock: %d | Precio: %.0f",
                        in.readInt(), in.readUTF(), in.readUTF(), in.readInt(), in.readDouble()));
            }
        } catch (NoSuchFileException e) {
            System.out.println("Aun no existe stock_bajo.dat. Genere primero la alerta.");
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    // Flujo completo con manejo de excepciones
    public void ejecutar(boolean generarArchivo) {
        try {
            List<Celular> celulares = obtenerStockBajo();

            if (celulares.isEmpty()) {
                System.out.println("No hay celulares con stock menor o igual a " + LIMITE_STOCK + ".");
                return;
            }

            mostrarAlerta(celulares);

            if (generarArchivo) {
                generarArchivo(celulares);
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo: " + e.getMessage());
        }
    }
}