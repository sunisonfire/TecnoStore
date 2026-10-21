
package controller.Admin;

import dao.Interfaces.IVentaDao;
import dao.implement.VentaDao;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.ResultSet;
import dao.Interfaces.IClienteDao;
import dao.implement.Conexion;
import dao.implement.PersonaDao
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Celular;
import model.Venta;
import model.VentaMarca;
public class ReporteVentas {
    private final IVentaDao ventaDao = new VentaDao();
    private final Conexion conexion = Conexion.getInstancia();
    
    public void lol() {
        int opcion;
        do {
            opcion = menuIngreso.marcasDisponibles();

            switch (opcion) {
                case 1 -> {
                    if (gestionarAdmin.iniciarSesion()) {
                        menuAdminAccion();   // solo entra si el login fue exitoso
                        gestionarAdmin.cerrarSesion();
                    }
                }

                case 2 ->
                    menuClienteLogin();
                case 0 ->
                    System.out.println("¡Hasta pronto!");
            }
        } while (opcion != 0);
    }
    private VentaMarca mapear(ResultSet rs) throws SQLException {

        return new VentaM(
                idVenta,
                cliente,
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                Venta.MetodoPago.valueOf(rs.getString("metodo_pago")),
                Venta.Estado.valueOf(rs.getString("estado")),
                Venta.Lugar.valueOf(rs.getString("lugar")),
                rs.getDouble("subtotal"),
                rs.getDouble("total"),
                detalleVentaDao.obtenerPorVenta(idVenta));
    }
    
    private List<Venta> obtenerLista(String sql, Object parametro) {
        List<Venta> ventas = new ArrayList<>();

        try (Connection c = conexion.conexion(); PreparedStatement ps = c.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setObject(1, parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
        return ventas;
    }
    
    public List<Venta> obtener() {
        return obtenerLista(BASE_CONSULTA_MARCA + " ORDER BY v.fecha_hora DESC", null);
    }
    
    private List<Venta> ventasValidas() {
        
        if(text=="Samsung"){
            
        }else if(){
            
        }
        return ventaDao.obtenerTodos().stream()
                .filter(v -> v.getMarca() == Venta.marca.Samsung)
                .toList();
    }
    
    public void reporteVentasMarca() {
        Map<String, Integer> validas = ventasValidas().stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                d -> d.getCelular().getMarca().getNombre() + " " + d.getCelular().getModelo(),
                Collectors.summingInt(v -> v.getCantidad())));

        if (validas.isEmpty()) {
            System.out.println("Todavía no hay ventas registradas.");
            return;
        }

        System.out.println("""

        +--------------------------------------------------+
        |             VENTAS POR MARCA                     |
        +--------------------------------------------------+
        |  CELULAR                         | UNIDADES      |
        +--------------------------------------------------+""");

        validas.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.printf(
                "|  %-31.31s | %-13d |%n",
                e.getKey(), e.getValue()));

        System.out.println("+--------------------------------------------------+");
    }
    
    private static final String BASE_CONSULTA_MARCA = """
            select c.nombre as cliente, v.fecha_hora, dv.precio_unitario, m.nombre as marca from detalle_venta dv
            left join venta v
            ON dv.id_venta=v.id_venta
            left join persona c
            on v.id_cliente=c.id_persona
            left join celular cc
            on dv.id_celular=cc.id_celular
            left join marca m
            on cc.id_marca=m.id_marca
            """;
    
    

}
