package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.DetalleVenta;
import model.Venta;

/**
 * DAO de venta. Insertar y actualizar guardan la venta y sus detalles en una
 * sola transacción; si algo falla, no queda nada a medias.
 */
public class VentaDao {

    private final Conexion conexion = new Conexion();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();

    // Consulta base: venta + cliente + persona (el cliente se arma con un solo JOIN)
    private static final String SELECT_BASE = """
            SELECT v.id_venta, v.fecha_hora, v.metodo_pago, v.estado, v.lugar,
                   v.subtotal, v.total,
                   cl.id_cliente, p.id_persona, p.nombre, p.apellido,
                   p.email, p.identificacion, p.telefono
            FROM venta v
            JOIN cliente cl ON cl.id_cliente = v.id_cliente
            JOIN persona p ON p.id_persona = cl.id_persona
            """;

    // Convierte una fila del ResultSet en una Venta (con sus detalles)
    private Venta mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getInt("id_cliente"),
                rs.getInt("id_persona"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email"),
                rs.getString("identificacion"),
                rs.getString("telefono"));

        int idVenta = rs.getInt("id_venta");

        return new Venta(
                idVenta,
                cliente,
                rs.getTimestamp("fecha_hora").toLocalDateTime(),
                Venta.MetodoPago.valueOf(rs.getString("metodo_pago")),
                Venta.Estado.valueOf(rs.getString("estado")),
                Venta.Lugar.valueOf(rs.getString("lugar")),
                rs.getDouble("subtotal"),
                rs.getDouble("total"),
                detalleVentaDao.obtenerPorVenta(idVenta));
    }

    // Llena los 7 parámetros comunes de INSERT y UPDATE (enums como texto)
    private void llenarVenta(PreparedStatement ps, Venta v) throws SQLException {
        ps.setInt(1, v.getCliente().getIdCliente());
        ps.setTimestamp(2, Timestamp.valueOf(v.getFechaHora()));
        ps.setString(3, v.getMetodoPago().name());
        ps.setString(4, v.getEstado().name());
        ps.setString(5, v.getLugar().name());
        ps.setDouble(6, v.getSubtotal());
        ps.setDouble(7, v.getTotal());
    }

    // ==================== C - create ====================

    // Inserta la venta y todos sus detalles. Deja los ids generados en los objetos.
    public boolean insertar(Venta v) {
        String sql = """
                INSERT INTO venta (id_cliente, fecha_hora, metodo_pago, estado, lugar, subtotal, total)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    llenarVenta(ps, v);

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("No se pudo insertar la venta");
                    }
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("No se obtuvo el id de la venta");
                        }
                        v.setIdVenta(keys.getInt(1));   // requiere setIdVenta en Venta
                    }
                }

                for (DetalleVenta d : v.getDetalles()) {
                    detalleVentaDao.insertar(c, d, v.getIdVenta());
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar venta: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================

    public Venta obtenerPorId(int idVenta) {
        String sql = SELECT_BASE + " WHERE v.id_venta = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        }
        return null;
    }

    public List<Venta> obtenerTodos() {
        return obtenerLista(SELECT_BASE + " ORDER BY v.fecha_hora DESC", null);
    }

    // Ventas de un cliente (para "Ver mis pedidos")
    public List<Venta> obtenerPorCliente(int idCliente) {
        return obtenerLista(SELECT_BASE + " WHERE v.id_cliente = ? ORDER BY v.fecha_hora DESC", idCliente);
    }

    private List<Venta> obtenerLista(String sql, Object parametro) {
        List<Venta> ventas = new ArrayList<>();

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setObject(1, parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        }
        return ventas;
    }

    // ==================== U - update ====================

    // Actualiza la venta y reemplaza sus detalles por los que tiene el objeto
    public boolean actualizar(Venta v) {
        String sql = """
                UPDATE venta SET id_cliente = ?, fecha_hora = ?, metodo_pago = ?, estado = ?,
                                 lugar = ?, subtotal = ?, total = ?
                WHERE id_venta = ?
                """;

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    llenarVenta(ps, v);
                    ps.setInt(8, v.getIdVenta());
                    ps.executeUpdate();
                }

                detalleVentaDao.eliminarPorVenta(c, v.getIdVenta());
                for (DetalleVenta d : v.getDetalles()) {
                    detalleVentaDao.insertar(c, d, v.getIdVenta());
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar venta: " + e.getMessage());
        }
        return false;
    }

    // Solo cambia el estado (ENVIADO, CANCELADO...)
    public boolean actualizarEstado(int idVenta, Venta.Estado estado) {
        String sql = "UPDATE venta SET estado = ? WHERE id_venta = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, estado.name());
            ps.setInt(2, idVenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
        }
        return false;
    }

    // ==================== D - delete ====================

    // Borra los detalles y luego la venta
    public boolean eliminar(int idVenta) {
        String sql = "DELETE FROM venta WHERE id_venta = ?";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                detalleVentaDao.eliminarPorVenta(c, idVenta);

                boolean eliminada;
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, idVenta);
                    eliminada = ps.executeUpdate() > 0;
                }

                c.commit();
                return eliminada;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
        }
        return false;
    }
}