package dao.implement;

import dao.Interfaces.IClienteDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

/**
 * DAO de cliente. Los datos personales viven en la tabla persona, por eso
 * insertar, actualizar y eliminar se apoyan en PersonaDao dentro de una transacción.
 */
public class ClienteDao implements IClienteDao {

    private final Conexion conexion = new Conexion();
    private final PersonaDao personaDao = new PersonaDao();

    // Consulta base: une persona con cliente
    private static final String SELECT_BASE = """
            SELECT cl.id_cliente, p.id_persona, p.nombre, p.apellido,
                   p.email, p.identificacion, p.telefono
            FROM cliente cl
            JOIN persona p ON p.id_persona = cl.id_persona
            """;

    // Convierte una fila del ResultSet en un Cliente
    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id_cliente"),
                rs.getInt("id_persona"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email"),
                rs.getString("identificacion"),
                rs.getString("telefono"));
    }

    // ==================== C - create (registrarse) ====================

    // Inserta en persona y luego en cliente. Deja el idPersona en el objeto.
    @Override
    public boolean insertar(Cliente cl) {
        String sql = "INSERT INTO cliente (id_persona) VALUES (?)";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                if (!personaDao.insertar(c, cl)) {
                    throw new SQLException("No se pudo insertar la persona");
                }

                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, cl.getIdPersona());
                    ps.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================

    // Inicio de sesión: el cliente con ese correo, o null si no existe
    @Override
    public Cliente login(String email) {
        return obtenerUno(SELECT_BASE + " WHERE p.email = ?", email);
    }

    @Override
    public Cliente obtenerPorId(int idCliente) {
        return obtenerUno(SELECT_BASE + " WHERE cl.id_cliente = ?", idCliente);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY p.nombre, p.apellido";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Busca una sola fila con un parámetro (int o String)
    private Cliente obtenerUno(String sql, Object parametro) {
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // ---------- Duplicados (delegan en PersonaDao) ----------

    @Override
    public boolean existeEmail(String email) {
        return personaDao.existeEmail(email);
    }

    @Override
    public boolean existeIdentificacion(String identificacion) {
        return personaDao.existeIdentificacion(identificacion);
    }

    @Override
    public boolean existeEmailDeOtro(String email, int idPersona) {
        return personaDao.existeEmailDeOtra(email, idPersona);
    }

    @Override
    public boolean existeIdentificacionDeOtro(String identificacion, int idPersona) {
        return personaDao.existeIdentificacionDeOtra(identificacion, idPersona);
    }

    // ==================== U - update ====================

    // Todos los datos del cliente están en persona
    @Override
    public boolean actualizar(Cliente cl) {
        return personaDao.actualizar(cl);
    }

    // ==================== D - delete ====================

    // Borra el cliente y luego su persona
    @Override
    public boolean eliminar(int idCliente) {
        Cliente cl = obtenerPorId(idCliente);
        if (cl == null) {
            return false;
        }

        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, idCliente);
                    ps.executeUpdate();
                }

                personaDao.eliminar(c, cl.getIdPersona());

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
        return false;
    }
}