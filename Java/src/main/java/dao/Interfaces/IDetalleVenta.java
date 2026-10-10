package dao.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.DetalleVenta;

public interface IDetalleVenta {

    // ==================== U - update ====================
    // Ojo: no recalcula subtotal y total de la venta. Para eso usa VentaDao.actualizar.
    boolean actualizar(DetalleVenta d);

    // ==================== D - delete ====================
    boolean eliminar(int idDetalleVenta);

    // Borra todos los detalles de una venta (dentro de la transacción de VentaDao)
    void eliminarPorVenta(Connection c, int idVenta) throws SQLException;

    // ==================== C - create ====================
    // Versión para transacciones: usa la conexión recibida y deja el id en d
    boolean insertar(Connection c, DetalleVenta d, int idVenta) throws SQLException;

    // Versión independiente
    boolean insertar(DetalleVenta d, int idVenta);

    // ==================== R - read ====================
    DetalleVenta obtenerPorId(int idDetalleVenta);

    // Todos los detalles de una venta
    List<DetalleVenta> obtenerPorVenta(int idVenta);
    
}
