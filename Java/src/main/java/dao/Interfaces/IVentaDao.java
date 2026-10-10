package dao.Interfaces;

import java.util.List;
import model.Venta;

public interface IVentaDao {

    // ==================== U - update ====================
    // Actualiza la venta y reemplaza sus detalles por los que tiene el objeto
    boolean actualizar(Venta v);

    // Solo cambia el estado (ENVIADO, CANCELADO...)
    boolean actualizarEstado(int idVenta, Venta.Estado estado);

    // ==================== D - delete ====================
    // Borra los detalles y luego la venta
    boolean eliminar(int idVenta);

    // ==================== C - create ====================
    // Inserta la venta y todos sus detalles. Deja los ids generados en los objetos.
    boolean insertar(Venta v);

    // Ventas de un cliente (para "Ver mis pedidos")
    List<Venta> obtenerPorCliente(int idCliente);

    // ==================== R - read ====================
    Venta obtenerPorId(int idVenta);

    List<Venta> obtenerTodos();
    
}
