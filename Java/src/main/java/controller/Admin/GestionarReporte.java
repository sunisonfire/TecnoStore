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

public class GestionarReporte {

    private static final int STOCK_BAJO = 5;
    private static final Path ARCHIVO = Path.of("reporte_ventas.txt");

    private final IVentaDao ventaDao = new VentaDao();
    private final ICelularDao celularDao = new CelularDao();
    private final MenuAdministrador menu = new MenuAdministrador();
    private final GestionarCelular gestionarCelular= new GestionarCelular();
    
    // ---------- 1. Top 3 más vendidos ----------
    public void celularesMasVendidos() {
        Map<String, Integer> unidades = ventasValidas().stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getMarca().getNombre() + " " + d.getCelular().getModelo(),
                        Collectors.summingInt(DetalleVenta::getCantidad)));

        if (unidades.isEmpty()) {
            System.out.println("Todavía no hay ventas registradas.");
            return;
        }

        System.out.println("\n--- TOP 3 CELULARES MÁS VENDIDOS ---");
        unidades.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.printf("%-32s %d unidades%n", e.getKey(), e.getValue()));
    }

    // ---------- 2. Stock bajo ----------
    public void celularesStockBajo() {
        List<Celular> bajos = celularDao.obtenerTodos().stream()
                .filter(c -> c.getStock() < STOCK_BAJO)
                .toList();

        System.out.println("\n--- CELULARES CON STOCK BAJO (menos de " + STOCK_BAJO + ") ---");
        if (bajos.isEmpty()) {
            System.out.println("No hay celulares con stock inferior a " + STOCK_BAJO + " unidades.");
            return;
        }
        gestionarCelular.mostrarCelulares(bajos);
    }

    // ---------- 3. Ventas totales ----------
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

    // ---------- 4. Exportar a archivo ----------
    public void exportarVentas() {
        List<Venta> ventas = ventaDao.obtenerTodos();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas para exportar.");
            return;
        }

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedWriter w = Files.newBufferedWriter(ARCHIVO)) {
            w.write("REPORTE DE VENTAS - TECNOSTORE");
            w.newLine();
            w.write("=".repeat(50));
            w.newLine();

            for (Venta v : ventas) {
                w.write(String.format("%nVenta #%d | %s | %s%n", v.getIdVenta(),
                        v.getFechaHora().format(f), v.getEstado()));
                w.write(String.format("Cliente: %s %s | Pago: %s | Entrega: %s%n",
                        v.getCliente().getNombre(), v.getCliente().getApellido(),
                        v.getMetodoPago(), v.getLugar()));

                for (DetalleVenta d : v.getDetalles()) {
                    w.write(String.format("   %-22s x%-3d $%,.2f%n",
                            d.getCelular().getModelo(), d.getCantidad(), d.getSubtotal()));
                }
                w.write(String.format("   Total: $%,.2f%n", v.getTotal()));
                w.write("-".repeat(50));
                w.newLine();
            }

            double totalVendido = ventas.stream()
                    .filter(v -> v.getEstado() != Venta.Estado.CANCELADO)
                    .mapToDouble(Venta::getTotal)
                    .sum();
            w.write(String.format("%nTOTAL VENDIDO (sin canceladas): $%,.2f%n", totalVendido));

            System.out.println("Reporte guardado en: " + ARCHIVO.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo: " + e.getMessage());
        }
    }

    // ---------- Auxiliar ----------
    private List<Venta> ventasValidas() {
        return ventaDao.obtenerTodos().stream()
                .filter(v -> v.getEstado() != Venta.Estado.CANCELADO)
                .toList();
    }
}