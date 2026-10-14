package view;

import controller.Admin.GestionarIVA;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Celular;
import model.DetalleVenta;
import model.Venta;
import view.Validaciones.EnteroRango;

public class MenuCliente {

    EnteroRango v = new EnteroRango();

    public int escogerTipoLogin() {
        return v.validarEnteroRango("""
                +------------------------------+
                |         BIENVENIDO           |
                +------------------------------+
                |  [1] Registrarse             |
                |  [2] Iniciar Sesion          |
                |  [0] Salir                   |
                +------------------------------+
                """, 0, 2);
    }

    public int opcionAccionCliente() {
        return v.validarEnteroRango("""
                +----------------------------------+
                |             MI CUENTA            |
                +----------------------------------+
                |  [1] Carrito                     |
                |  [2] Ver mi perfil               |
                |  [3] Actualizar mi perfil        |
                |  [4] Eliminar cuenta             |
                |  [0] Salir                       |
                +----------------------------------+
                """, 0, 4);
    }

    public int opcionActualizarDatos() {
        return v.validarEnteroRango("""
                +----------------------------------+
                |        ACTUALIZAR DATOS          |
                +----------------------------------+
                |  [1] Nombre                      |
                |  [2] Apellido                    |
                |  [3] Email                       |
                |  [4] Identificacion              |
                |  [5] Telefono                    |
                |  [0] Salir                       |
                +----------------------------------+
                """, 0, 5);
    }

    // gestionPedido() y opcionActualizarPedido() igual:
    // mismo texto, pero con "⟮0⟯ Salir" y rango 0-4 y 0-3 respectivamente
    public int gestionPedido() {
    return v.validarEnteroRango("""
            +----------------------------------+
            |          MIS PEDIDOS             |
            +----------------------------------+
            |  [1] Hacer un pedido             |
            |  [2] Ver mis pedidos             |
            |  [3] Cancelar un pedido          |
            |  [0] Volver                      |
            +----------------------------------+
            """, 0, 3);
}

public void mostrarPedidos(List<Venta> pedidos) {
    if (pedidos.isEmpty()) {
        System.out.println("Aún no tienes pedidos.");
        return;
    }
    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    for (Venta p : pedidos) {
        System.out.printf("%nPedido #%d | %s | %s | %s | %s%n",
                p.getIdVenta(), p.getFechaHora().format(f),
                p.getEstado(), p.getMetodoPago(), p.getLugar());
        for (DetalleVenta d : p.getDetalles()) {
            System.out.printf("   %-22s x%-3d $%-11.2f%n",
                    d.getCelular().getModelo(), d.getCantidad(), d.getSubtotal());
        }
        System.out.printf("   Total: $%.2f%n", p.getTotal());
    }
}

    public int opcionSeguirComprando() {
        return v.validarEnteroRango("""
            +----------------------------------+
            |           TU CARRITO             |
            +----------------------------------+
            |  [1] Seguir agregando            |
            |  [2] Continuar con el pago       |
            |  [0] Cancelar compra             |
            +----------------------------------+
            """, 0, 2);
    }

    public Venta.MetodoPago pedirMetodoPago() {
        int op = v.validarEnteroRango("""
            +----------------------------------+
            |          METODO DE PAGO          |
            +----------------------------------+
            |  [1] PSE                         |
            |  [2] TARJETA                     |
            |  [3] NEQUI                       |
            |  [4] TRANSFERENCIA               |
            +----------------------------------+
            """, 1, 4);
        return Venta.MetodoPago.values()[op - 1];
    }

    public Venta.Lugar pedirLugar() {
        int op = v.validarEnteroRango("""
            +----------------------------------+
            |        TIPO DE ENTREGA           |
            +----------------------------------+
            |  [1] Recoger en el local         |
            |  [2] Domicilio                   |
            +----------------------------------+
            """, 1, 2);
        return op == 1 ? Venta.Lugar.LOCAL : Venta.Lugar.DOMICILIO;
    }

    public void mostrarCatalogo(List<Celular> celulares) {
    System.out.printf("""
            +------+------------------------+--------------+--------+
            | ID   | Modelo                 | Precio       | Stock  |
            +------+------------------------+--------------+--------+
            """);

    for (Celular c : celulares) {
        System.out.printf("| %-4d | %-22s | $%-11.2f | %-6d |%n",
                c.getIdCelular(),
                c.getModelo(),
                c.getPrecio(),
                c.getStock());
    }

    System.out.println("+------+------------------------+--------------+--------+");
}

    public void mostrarResumen(Venta venta) {
    final int ANCHO = 69;
    String linea = "+" + "-".repeat(ANCHO - 2) + "+";
    String separador = "+" + "-".repeat(24) + "+" + "-".repeat(10)
            + "+" + "-".repeat(15) + "+" + "-".repeat(15) + "+";

    double baseSinIva = venta.getTotal() / (1 + GestionarIVA.TASA);
    double iva = venta.getTotal() - baseSinIva;
    double descuento = venta.getSubtotal() - baseSinIva;

    System.out.println();
    System.out.println(linea);
    System.out.println("|" + centrar("RESUMEN DE TU COMPRA", ANCHO - 2) + "|");
    System.out.println(linea);

    System.out.printf("| %-22s | %-8s | %-13s | %-13s |%n",
            "Producto", "Cantidad", "Precio", "Subtotal");
    System.out.println(separador);

    for (DetalleVenta d : venta.getDetalles()) {
        System.out.printf("| %-22s | %-8d | %13s | %13s |%n",
                cortar(d.getCelular().getModelo(), 22),
                d.getCantidad(),
                dinero(d.getPrecioUnitario()),
                dinero(d.getSubtotal()));
    }
    System.out.println(separador);

    System.out.printf("| %-65s |%n", "Pago: " + venta.getMetodoPago());
    System.out.printf("| %-65s |%n", "Entrega: " + venta.getLugar());
    System.out.println(linea);

    System.out.printf("| %-20s %44s |%n", "Subtotal:", dinero(venta.getSubtotal()));
    System.out.printf("| %-20s %44s |%n", "Descuento:", "-" + dinero(descuento));
    System.out.printf("| %-20s %44s |%n", "IVA 19%:", dinero(iva));
    System.out.println(linea);

    System.out.printf("| %-20s %44s |%n", "TOTAL:", dinero(venta.getTotal()));
    System.out.println(linea);
}

// ---------- Auxiliares de formato ----------
private String dinero(double valor) {
    return String.format("$%,.1f", valor);
}

private String cortar(String texto, int max) {
    return texto.length() <= max ? texto : texto.substring(0, max);
}

private String centrar(String texto, int ancho) {
    int izquierda = (ancho - texto.length()) / 2;
    return " ".repeat(izquierda) + texto + " ".repeat(ancho - texto.length() - izquierda);
}
}
