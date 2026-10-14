package controller.Cliente;

import controller.Admin.GestionarIVA;
import dao.Interfaces.IVentaDao;
import dao.implement.CelularDao;
import dao.implement.VentaDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Celular;
import model.Cliente;
import model.DetalleVenta;
import model.Venta;
import view.MenuCliente;
import view.Validaciones.Confirmacion;
import view.Validaciones.EnteroRango;
import strategy.DescuentoClienteFrecuente;
import strategy.DescuentoPorMonto;
import strategy.GestionarDescuento;
import strategy.SinDescuento;
import strategy.TipoDescuento;

public class GestionarPedido {

    private final IVentaDao ventaDao = new VentaDao();
    private final CelularDao celularDao = new CelularDao();
    private final MenuCliente menuCliente = new MenuCliente();
    private final Confirmacion confirmacion = new Confirmacion();
    private final EnteroRango entero = new EnteroRango();

    public void crearPedido(Cliente cliente) {
        List<DetalleVenta> carrito = new ArrayList<>();

        // 1. Armar el carrito (todo en memoria)
        while (true) {
            Celular celular = elegirCelular();

            if (celular != null) {
                int disponible = celular.getStock() - cantidadEnCarrito(carrito, celular.getIdCelular());
                if (disponible <= 0) {
                    System.out.println("Ya tienes en el carrito todas las unidades disponibles.");
                } else {
                    int cantidad = entero.validarEnteroRango(
                            "Cantidad (máx. " + disponible + "): ", 1, disponible);
                    agregarAlCarrito(carrito, celular, cantidad);
                    System.out.println("Agregado: " + celular.getModelo() + " x" + cantidad);
                }
            }

            int opcion = menuCliente.opcionSeguirComprando();   // 1 seguir, 2 pagar, 0 cancelar
            if (opcion == 0) {
                System.out.println("Compra cancelada. No se descontó nada del stock.");
                return;
            }
            if (opcion == 2) {
                if (carrito.isEmpty()) {
                    System.out.println("Tu carrito está vacío.");
                } else {
                    break;
                }
            }
        }

        // 2. Datos de la compra
        Venta.MetodoPago metodo = menuCliente.pedirMetodoPago();
        Venta.Lugar lugar = menuCliente.pedirLugar();

        Venta venta = new Venta(cliente, LocalDateTime.now(), metodo, Venta.Estado.PENDIENTE, lugar);
        carrito.forEach(venta::agregarDetalle);

        TipoDescuento tipo = elegirDescuento(cliente, venta.getSubtotal());
        double baseConDescuento = new GestionarDescuento(tipo).aplicar(venta.getSubtotal());
        venta.setTotal(GestionarIVA.conIva(baseConDescuento));

        // 3. Resumen y confirmación
        menuCliente.mostrarResumen(venta);
        if (!confirmacion.validarConfirmacion("¿Confirmar la compra?")) {
            System.out.println("Compra cancelada. No se descontó nada del stock.");
            return;
        }

        // 4. Recién aquí se escribe en la base de datos (venta + detalles + stock, una transacción)
        if (ventaDao.insertar(venta)) {
            System.out.println("¡Compra realizada! Pedido #" + venta.getIdVenta());
        } else {
            System.out.println("No se pudo completar la compra (puede que el stock haya cambiado). No se cobró nada.");
        }
    }

    // ---------- Auxiliares ----------
    private Celular elegirCelular() {
        List<Celular> disponibles = celularDao.obtenerTodos().stream()
                .filter(c -> c.getStock() > 0)
                .toList();

        if (disponibles.isEmpty()) {
            System.out.println("No hay celulares disponibles.");
            return null;
        }

        menuCliente.mostrarCatalogo(disponibles);
        int ultimoId = celularDao.obtenerUltimoId();
        int id = entero.validarEnteroRango("ID del celular (0 para volver): ", 0, ultimoId);
        if (id == 0) {
            return null;
        }

        return disponibles.stream()
                .filter(c -> c.getIdCelular() == id)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Ese celular no está disponible.");
                    return null;
                });
    }

    private int cantidadEnCarrito(List<DetalleVenta> carrito, int idCelular) {
        return carrito.stream()
                .filter(d -> d.getCelular().getIdCelular() == idCelular)
                .mapToInt(DetalleVenta::getCantidad)
                .sum();
    }

    // Si el celular ya está en el carrito, suma la cantidad en vez de repetir la fila
    private void agregarAlCarrito(List<DetalleVenta> carrito, Celular celular, int cantidad) {
        for (DetalleVenta d : carrito) {
            if (d.getCelular().getIdCelular() == celular.getIdCelular()) {
                d.setCantidad(d.getCantidad() + cantidad);
                return;
            }
        }
        carrito.add(new DetalleVenta(celular, cantidad, celular.getPrecio()));
    }

    public void verMisPedidos(Cliente cliente) {
        List<Venta> pedidos = ventaDao.obtenerPorCliente(cliente.getIdCliente());
        menuCliente.mostrarPedidos(pedidos);
    }

// El cliente solo puede cancelar sus propios pedidos que sigan pendientes
    public void cancelarPedido(Cliente cliente) {
        List<Venta> pendientes = ventaDao.obtenerPorCliente(cliente.getIdCliente()).stream()
                .filter(v -> v.getEstado() == Venta.Estado.PENDIENTE)
                .toList();

        if (pendientes.isEmpty()) {
            System.out.println("No tienes pedidos pendientes para cancelar.");
            return;
        }

        menuCliente.mostrarPedidos(pendientes);
        int ultimoId = pendientes.stream().mapToInt(Venta::getIdVenta).max().orElse(0);
        int id = entero.validarEnteroRango("ID del pedido a cancelar (0 para volver): ", 0, ultimoId);
        if (id == 0) {
            return;
        }

        if (pendientes.stream().noneMatch(v -> v.getIdVenta() == id)) {
            System.out.println("Ese pedido no está entre tus pedidos pendientes.");
            return;
        }

        if (!confirmacion.validarConfirmacion("¿Cancelar el pedido #" + id + "? Las unidades volverán al stock.")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (ventaDao.actualizarEstado(id, Venta.Estado.CANCELADO)) {
            System.out.println("Pedido cancelado.");
        } else {
            System.out.println("No se pudo cancelar el pedido.");
        }
    }

    // El controller decide la estrategia; GestionarDescuento solo la ejecuta
    private TipoDescuento elegirDescuento(Cliente cliente, double subtotal) {
        boolean esFrecuente = ventaDao.obtenerPorCliente(cliente.getIdCliente()).size() >= 3;

        if (esFrecuente) {
            return new DescuentoClienteFrecuente();
        }
        if (subtotal > 5_000_000) {
            return new DescuentoPorMonto();
        }
        return new SinDescuento();
    }
}
