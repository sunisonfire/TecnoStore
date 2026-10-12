package view;

import java.util.List;
import java.util.Scanner;
import model.Cliente;
import view.Validaciones.EnteroRango;
import java.time.format.DateTimeFormatter;
import model.Venta;

public class MenuAdministrador {

    private final Scanner sc = new Scanner(System.in);
    EnteroRango v = new EnteroRango();

    public int escogerAccion() {

        return v.validarEnteroRango("""
                ╔════════════════════════════════╗
                ║   ELIJA QUÉ GESTIONAR    ║
                ╠════════════════════════════════╣
                ║  [1] Gestionar Celulares ║
                ║  [2] Gestionar Clientes  ║
                ║  [3] Gestionar Ventas    ║
                ║  [4] Reportes y Análisis ║
                ║  [0] Salir               ║
                ╚════════════════════════════════╝

        """, 0, 4);

    }

    public int opcionGestionarCelular() {

        return v.validarEnteroRango("""
                               ╔════════════════════════════════╗
                               ║   ELIJA QUÉ GESTIONAR    ║
                               ╠════════════════════════════════╣
                               ║  [1] Agregar             ║
                               ║  [2] Listar              ║
                               ║  [3] Actualizar          ║
                               ║  [4] Eliminar            ║
                               ║  [0] Salir               ║
                               ╚════════════════════════════════╝

                               """, 0, 4);

    }

    public int opcionGestionarCliente() {

        return v.validarEnteroRango("""
                                 ╔════════════════════════════════╗
                                 ║    GESTIONAR CLIENTE     ║
                                 ╠════════════════════════════════╣
                                 ║  [1] Listar              ║
                                 ║  [0] Salir               ║
                                 ╚════════════════════════════════╝
                               """, 0, 1);

    }

    public int opcionGestionarVenta() {

        return v.validarEnteroRango("""
                                     ╔════════════════════════════════╗
                                     ║      GESTIONAR VENTA     ║
                                     ╠════════════════════════════════╣
                                     ║  [1] Actualizar Estado   ║
                                     ║  [2] Lista               ║
                                     ║  [3] Eliminar            ║
                                     ║  [0] Salir               ║
                                     ╚════════════════════════════════╝

                               """, 0, 3);

    }

    public int opcionActualizarCelular() {

        return v.validarEnteroRango("""
            ╔════════════════════════════╗
            ║ ACTUALIZAR CELULAR    ║
            ╠════════════════════════════╣
            ║  [1] Modelo           ║
            ║  [2] Marca            ║
            ║  [3] Stock            ║
            ║  [4] Sistema Operativo║
            ║  [5] Gama             ║
            ║  [6] Precio           ║
            ║  [0] Salir            ║
            ╚════════════════════════════╝
            """, 0, 6);

    }

    public void reportes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String pedirUsername() {
        System.out.print("Usuario: ");
        return sc.nextLine().trim();
    }

    public long pedirContrasena() {
        while (true) {
            System.out.print("Contraseña: ");
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("La contraseña debe ser numérica.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    // dentro de MenuAdministrador (reutiliza el Scanner que ya agregaste)

    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.printf("%-5s | %-15s | %-15s | %-28s | %-12s%n",
                "ID", "Nombre", "Apellido", "Email", "Teléfono");
        for (Cliente c : clientes) {
            System.out.printf("%-5d | %-15s | %-15s | %-28s | %-12s%n",
                    c.getIdCliente(), c.getNombre(), c.getApellido(),
                    c.getEmail(), c.getTelefono());
        }
    }

    public String pedirTexto(String etiqueta) {
        System.out.print(etiqueta);
        return sc.nextLine().trim();
    }

    public void mostrarVentas(List<Venta> ventas) {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.printf("%-5s | %-16s | %-22s | %-10s | %-14s | %-9s | %-12s%n",
                "ID", "Fecha", "Cliente", "Estado", "Pago", "Lugar", "Total");
        for (Venta v : ventas) {
            System.out.printf("%-5d | %-16s | %-22s | %-10s | %-14s | %-9s | $%-11.2f%n",
                    v.getIdVenta(), v.getFechaHora().format(f),
                    v.getCliente().getNombre() + " " + v.getCliente().getApellido(),
                    v.getEstado(), v.getMetodoPago(), v.getLugar(), v.getTotal());
        }
    }

    public void mostrarDetalleVenta(Venta v) {
        System.out.println("Venta #" + v.getIdVenta() + " | " + v.getCliente().getNombre()
                + " " + v.getCliente().getApellido());
        System.out.printf("%-24s | %-8s | %-12s | %-12s%n", "Celular", "Cant.", "Precio", "Subtotal");
        for (var d : v.getDetalles()) {
            System.out.printf("%-24s | %-8d | $%-11.2f | $%-11.2f%n",
                    d.getCelular().getModelo(), d.getCantidad(),
                    d.getPrecioUnitario(), d.getSubtotal());
        }
        System.out.printf("Total: $%.2f%n", v.getTotal());
    }

    public Venta.Estado pedirEstado() {
        int op = v.validarEnteroRango("""
            Nuevo estado:
              [1] PENDIENTE
              [2] ENVIADO
              [3] CANCELADO
              [0] Volver
            """, 0, 3);
        return switch (op) {
            case 1 ->
                Venta.Estado.PENDIENTE;
            case 2 ->
                Venta.Estado.ENVIADO;
            case 3 ->
                Venta.Estado.CANCELADO;
            default ->
                null;
        };
    }

}
