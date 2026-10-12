package view;

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
    System.out.println("""

            +--------------------------------------------------+
            |              RESUMEN DE TU COMPRA                |
            +--------------------------------------------------+
            """);

    System.out.printf("| %-22s | %-8s | %-11s | %-11s |%n",
            "Producto", "Cantidad", "Precio", "Subtotal");

    System.out.println(
            "+------------------------+----------+-------------+-------------+");

    for (DetalleVenta d : venta.getDetalles()) {
        System.out.printf("| %-22s | %-8d | $%-10.2f | $%-10.2f |%n",
                d.getCelular().getModelo(),
                d.getCantidad(),
                d.getPrecioUnitario(),
                d.getSubtotal());
    }

    System.out.println(
            "+------------------------+----------+-------------+-------------+");

    System.out.printf("| Pago: %-43s |%n", venta.getMetodoPago());
    System.out.printf("| Entrega: %-40s |%n", venta.getLugar());

    System.out.println("+--------------------------------------------------+");
    System.out.printf("| TOTAL: $%-42.2f |%n", venta.getTotal());
    System.out.println("+--------------------------------------------------+");
}
}
