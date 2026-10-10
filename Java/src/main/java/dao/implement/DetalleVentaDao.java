package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.DetalleVenta;

/**
 * DAO de detalle_venta. Un detalle no conoce su venta en el modelo, por eso
 * insertar recibe el idVenta. Los métodos con Connection se usan dentro de la
 * transacción de VentaDao.
 */
public class DetalleVentaDao {

    private final Conexion conexion = new Conexion();
    private final CelularDao celularDao = new CelularDao();   // debe tener obtenerPorId(int)

    private static final String SELECT_BASE = """
            SELECT id_detalle_venta, id_celular, cantidad, precio_unitario
            FROM detalle_venta
            """;

    // Convierte una fila del ResultSet en un DetalleVenta
    private DetalleVenta mapear(ResultSet rs) throws SQLException {
        return new DetalleVenta(
                rs.getInt("id_detalle_venta"),
                celularDao.obtenerPorId(rs.getInt("id_celular")),
                rs.getInt("cantidad"),
                rs.getDouble("precio_unitario"));
    }

    // ==================== C - create ====================

    // Versión para transacciones: usa la conexión recibida y deja el id en d
    public boolean insertar(Connection c, DetalleVenta d, int idVenta) throws SQLException {
        String sql = "INSERT INTO detalle_venta (id_venta, id_celular, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idVenta);
            ps.setInt(2, d.getCelular().getIdCelular());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    d.setIdDetalleVenta(keys.getInt(1));
                    return true;
                }
            }
            throw new SQLException("No se obtuvo el id del detalle");
        }
    }

    // Versión independiente
    public boolean insertar(DetalleVenta d, int idVenta) {
        try (Connection c = conexion.conexion()) {
            return insertar(c, d, idVenta);
        } catch (SQLException e) {
            System.err.println("Error al insertar detalle: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================

    public DetalleVenta obtenerPorId(int idDetalleVenta) {
        String sql = SELECT_BASE + " WHERE id_detalle_venta = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idDetalleVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar detalle: " + e.getMessage());
        }
        return null;
    }

    // Todos los detalles de una venta
    public List<DetalleVenta> obtenerPorVenta(int idVenta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE id_venta = ? ORDER BY id_detalle_venta";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles: " + e.getMessage());
        }
        return detalles;
    }

    // ==================== U - update ====================

    // Ojo: no recalcula subtotal y total de la venta. Para eso usa VentaDao.actualizar.
    public boolean actualizar(DetalleVenta d) {
        String sql = "UPDATE detalle_venta SET id_celular = ?, cantidad = ?, precio_unitario = ? WHERE id_detalle_venta = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, d.getCelular().getIdCelular());
            ps.setInt(2, d.getCantidad());
            ps.setDouble(3, d.getPrecioUnitario());
            ps.setInt(4, d.getIdDetalleVenta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar detalle: " + e.getMessage());
        }
        return false;
    }

    // ==================== D - delete ====================

    public boolean eliminar(int idDetalleVenta) {
        String sql = "DELETE FROM detalle_venta WHERE id_detalle_venta = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idDetalleVenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalle: " + e.getMessage());
        }
        return false;
    }

    // Borra todos los detalles de una venta (dentro de la transacción de VentaDao)
    public void eliminarPorVenta(Connection c, int idVenta) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM detalle_venta WHERE id_venta = ?")) {
            ps.setInt(1, idVenta);
            ps.executeUpdate();
        }
    }
}