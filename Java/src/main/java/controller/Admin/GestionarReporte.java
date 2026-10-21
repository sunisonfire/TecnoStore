package controller.Admin;

import dao.Interfaces.ICelularDao;
import dao.Interfaces.IVentaDao;
import dao.implement.CelularDao;
import dao.implement.VentaDao;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.Celular;
import model.DetalleVenta;
import model.Venta;
import view.MenuAdministrador;
import model.DatosVenta;
public class GestionarReporte {

    // SE USA EN MAYUSCULA POR QUE ES FINAL
    private static final int STOCK_BAJO = 5;
    private static final Path ARCHIVO = Path.of("C:/Users/ASUS/Documents/JAVAA/TecnoStoreD/Reporte/reporte_ventas.txt");

    private final IVentaDao ventaDao = new VentaDao();
    private final ICelularDao celularDao = new CelularDao();
    private final MenuAdministrador menu = new MenuAdministrador();
    private final GestionarCelular gestionarCelular = new GestionarCelular();

    // STREAM API
    // TOP CELULARES MAS VENDIDOS (3)
    public void celularesMasVendidos() {
        Map<String, Integer> unidades = ventasValidas().stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getMarca().getNombre() + " " + d.getCelular().getModelo(),
                        Collectors.summingInt(v -> v.getCantidad())));

        if (unidades.isEmpty()) {
            System.out.println("Todavía no hay ventas registradas.");
            return;
        }

        System.out.println("""

        +--------------------------------------------------+
        |             TOP 3 CELULARES MAS VENDIDOS         |
        +--------------------------------------------------+
        |  CELULAR                         | UNIDADES      |
        +--------------------------------------------------+""");

        unidades.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.printf(
                "|  %-31.31s | %-13d |%n",
                e.getKey(), e.getValue()));

        System.out.println("+--------------------------------------------------+");
    }

    // STOCK BAJO (<5)
    // ALERTA DE STOCK BAJO (<= 5): consola + archivo stock_bajo.dat
    public void celularesStockBajo() {
        new AlertaStockService().ejecutar(true);
    }

// LEER EL REPORTE BINARIO stock_bajo.dat
    public void verReporteStockBajo() {
        new AlertaStockService().leerArchivo();
    }

    /*
    REPORTE VENTAS POR MARCA
    */
    
    
    // VENTAS TOTALES EXCLUYENDO LAS CANCELADAS
    public void ventasTotales() {
        List<Venta> ventas = ventasValidas();

        if (ventas.isEmpty()) {
            System.out.println("Todavía no hay ventas registradas.");
            return;
        }

        System.out.println("\n--- VENTAS TOTALES (sin canceladas) ---");
        menu.mostrarVentas(ventas);

        double total = ventas.stream().mapToDouble(Venta::getTotal).sum();
        long unidades = ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .mapToInt(DetalleVenta::getCantidad)
                .sum();

        System.out.printf("%nVentas: %d | Unidades vendidas: %d | Total vendido: $%,.2f%n",
                ventas.size(), unidades, total);
    }

    // EL ARCHIVO .TXT
    public void exportarVentas() {
        List<Venta> ventas = ventaDao.obtenerTodos();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para exportar.");
            return;
        }

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO)) {
            w.write("+--------------------------------------------------+");
            w.newLine();
            w.write("|          REPORTE DE VENTAS - TECNOSTORE          |");
            w.newLine();
            w.write("+--------------------------------------------------+");
            w.newLine();

            for (Venta v : ventas) {
                w.newLine();
                w.write("+--------------------------------------------------+");
                w.newLine();
                w.write(String.format("| VENTA #%d | %-36s |%n",
                        v.getIdVenta(),
                        v.getFechaHora().format(f)));
                w.write(String.format("| Estado: %-41s |%n", v.getEstado()));
                w.write(String.format("| Cliente: %-40s |%n",
                        v.getCliente().getNombre() + " " + v.getCliente().getApellido()));
                w.write(String.format("| Pago: %-43s |%n", v.getMetodoPago()));
                w.write(String.format("| Entrega: %-40s |%n", v.getLugar()));
                w.write("+--------------------------------------------------+");
                w.newLine();

                for (DetalleVenta d : v.getDetalles()) {
                    w.write(String.format("| %-24.24s | x%-3d | $%-15.2f |%n",
                            d.getCelular().getModelo(),
                            d.getCantidad(),
                            d.getSubtotal()));
                }

                w.write("+--------------------------------------------------+");
                w.newLine();
                w.write(String.format("| TOTAL DE LA VENTA: $%-29.2f |%n", v.getTotal()));
                w.write("+--------------------------------------------------+");
                w.newLine();
            }

            double totalVendido = ventas.stream()
                    .filter(v -> v.getEstado() != Venta.Estado.CANCELADO)
                    .mapToDouble(Venta::getTotal)
                    .sum();
            w.newLine();
            w.write("+--------------------------------------------------+");
            w.newLine();
            w.write(String.format("| TOTAL VENDIDO: $%-34.2f |", totalVendido));
            w.newLine();
            w.write("+--------------------------------------------------+");
            w.newLine();

            System.out.println("Reporte guardado en: " + ARCHIVO.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo: " + e.getMessage());
        }
    }

    // COMPROBAR QUE UNA VENTA ES VALIDA (!= CANCELADA)
    private List<Venta> ventasValidas() {
        return ventaDao.obtenerTodos().stream()
                .filter(v -> v.getEstado() != Venta.Estado.CANCELADO)
                .toList();
    }

    // EL ARCHIVO .DAT (mismo contenido del .txt, pero guardando los objetos)
    public void exportarVentasDat() {
        List<Venta> ventas = ventaDao.obtenerTodos();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para exportar.");
            return;
        }

        DatosVenta datos = new DatosVenta();
        datos.guardarTodas(ventas);
        System.out.println("Ventas guardadas en reporte_ventas.dat (" + ventas.size() + ")");
    }

// LEER EL .DAT Y MOSTRARLO
    public void verVentasDat() {
        List<Venta> ventas = new DatosVenta().getObtenerLista();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas en el archivo .dat. Exporte primero.");
            return;
        }

        System.out.println("\n--- VENTAS DESDE reporte_ventas.dat ---");
        menu.mostrarVentas(ventas);
        ventas.forEach(menu::mostrarDetalleVenta);

        double total = ventas.stream()
                .filter(v -> v.getEstado() != Venta.Estado.CANCELADO)
                .mapToDouble(Venta::getTotal)
                .sum();
        System.out.printf("%nTotal vendido (sin canceladas): $%,.2f%n", total);
    }
}
