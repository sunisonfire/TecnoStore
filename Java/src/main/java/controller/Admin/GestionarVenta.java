package controller.Admin;

import dao.Interfaces.IVentaDao;
import dao.implement.VentaDao;
import java.util.List;
import model.Venta;
import model.Venta.Estado;
import view.MenuAdministrador;
import view.Validaciones.Confirmacion;
import view.Validaciones.EnteroRango;

public class GestionarVenta {

    private final IVentaDao ventaDao = new VentaDao();
    private final MenuAdministrador menu = new MenuAdministrador();
    private final Confirmacion confirmacion = new Confirmacion();
    private final EnteroRango entero = new EnteroRango();

    // ---------- Listar ----------
    public void listarVenta() {
        List<Venta> ventas = ventaDao.obtenerTodos();
        menu.mostrarVentas(ventas);

        if (!ventas.isEmpty() && confirmacion.validarConfirmacion("Ver el detalle de una venta?")) {
            Venta venta = buscarVenta();
            if (venta != null) {
                menu.mostrarDetalleVenta(venta);
            }
        }
    }

    // ---------- Actualizar estado ----------
    public void actualizarVenta() {
        menu.mostrarVentas(ventaDao.obtenerTodos());
        Venta venta = buscarVenta();
        if (venta == null) {
            return;
        }

        if (venta.getEstado() == Estado.CANCELADO) {
            System.out.println("Una venta cancelada no se puede modificar.");
            return;
        }

        System.out.println("Estado actual: " + venta.getEstado());
        Estado nuevo = menu.pedirEstado();
        if (nuevo == null) {
            return;
        }

        if (nuevo == venta.getEstado()) {
            System.out.println("La venta ya está en ese estado.");
            return;
        }
        if (nuevo == Estado.PENDIENTE) {
            System.out.println("Una venta enviada no puede volver a pendiente.");
            return;
        }
        if (nuevo == Estado.CANCELADO
                && !confirmacion.validarConfirmacion("Cancelar devuelve las unidades al stock. ¿Continuar?")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (ventaDao.actualizarEstado(venta.getIdVenta(), nuevo)) {
            System.out.println("Estado actualizado a " + nuevo + ".");
        } else {
            System.out.println("No se pudo actualizar el estado.");
        }
    }

    // ---------- Eliminar ----------
    public void eliminarVenta() {
        menu.mostrarVentas(ventaDao.obtenerTodos());
        Venta venta = buscarVenta();
        if (venta == null) {
            return;
        }

        String aviso = venta.getEstado() == Estado.CANCELADO
                ? ""
                : " Sus unidades volverán al stock.";

        if (!confirmacion.validarConfirmacion("¿Eliminar la venta #" + venta.getIdVenta() + "?" + aviso)) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (ventaDao.eliminar(venta.getIdVenta())) {
            System.out.println("Venta eliminada.");
        } else {
            System.out.println("No se pudo eliminar la venta.");
        }
    }

    // ---------- Auxiliar ----------
    private Venta buscarVenta() {
    int id = entero.validarEnteroRango("ID de la venta: ", 1, ventaDao.obtenerUltimoId());
    Venta venta = ventaDao.obtenerPorId(id);
    if (venta == null) {
        System.out.println("No existe una venta con ese ID.");
    }
    return venta;
}
}