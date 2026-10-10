package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Persona;

/**
 * DAO de la tabla persona (la tabla base de Cliente y Administrador).
 *
 * Los métodos con Connection se usan dentro de la transacción de otro DAO
 * (ClienteDao, AdministradorDao) y lanzan SQLException para que ese DAO haga el
 * rollback. Los métodos sin Connection abren y cierran la suya.
 */
public class PersonaDao {

    private final Conexion conexion = new Conexion();

    private static final String SELECT_BASE = """
            SELECT id_persona, nombre, apellido, email, identificacion, telefono
            FROM persona
            """;

    // Convierte una fila del ResultSet en una Persona
    private Persona mapear(ResultSet rs) throws SQLException {
        return new Persona(
                rs.getInt("id_persona"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email"),
                rs.getString("identificacion"),
                rs.getString("telefono"));
    }

    // ==================== C - create ====================
    // Versión para transacciones: usa la conexión recibida y deja el id en p
    public boolean insertar(Connection c, Persona p) throws SQLException {
        String sql = "INSERT INTO persona (nombre, apellido, email, identificacion, telefono) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getIdentificacion());
            ps.setString(5, p.getTelefono());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setIdPersona(keys.getInt(1));   // requiere setIdPersona en Persona
                    return true;
                }
            }
            throw new SQLException("No se obtuvo el id de la persona");
        }
    }

    // Versión independiente
    public boolean insertar(Persona p) {
        try (Connection c = conexion.conexion()) {
            return insertar(c, p);
        } catch (SQLException e) {
            System.err.println("Error al insertar persona: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================
    public Persona obtenerPorId(int idPersona) {
        return obtenerUna(SELECT_BASE + " WHERE id_persona = ?", idPersona);
    }

    public Persona obtenerPorEmail(String email) {
        return obtenerUna(SELECT_BASE + " WHERE email = ?", email);
    }

    public Persona obtenerPorIdentificacion(String identificacion) {
        return obtenerUna(SELECT_BASE + " WHERE identificacion = ?", identificacion);
    }

    public List<Persona> obtenerTodos() {
        List<Persona> personas = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY nombre, apellido";

        try (Connection c = conexion.conexion(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                personas.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar personas: " + e.getMessage());
        }
        return personas;
    }

    // Busca una sola fila con un parámetro (int o String)
    private Persona obtenerUna(String sql, Object parametro) {
        try (Connection c = conexion.conexion(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar persona: " + e.getMessage());
        }
        return null;
    }

    // ---------- Duplicados (consultas livianas, sin armar objetos) ----------
    public boolean existeEmail(String email) {
        return existe("SELECT 1 FROM persona WHERE email = ?", email);
    }

    public boolean existeIdentificacion(String identificacion) {
        return existe("SELECT 1 FROM persona WHERE identificacion = ?", identificacion);
    }

    // Para actualizar: ¿otra persona distinta ya usa este correo?
    public boolean existeEmailDeOtra(String email, int idPersona) {
        return existe("SELECT 1 FROM persona WHERE email = ? AND id_persona <> ?", email, idPersona);
    }

    // Para actualizar: ¿otra persona distinta ya usa esta identificación?
    public boolean existeIdentificacionDeOtra(String identificacion, int idPersona) {
        return existe("SELECT 1 FROM persona WHERE identificacion = ? AND id_persona <> ?", identificacion, idPersona);
    }

    private boolean existe(String sql, Object... parametros) {
        try (Connection c = conexion.conexion(); PreparedStatement ps = c.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al validar datos: " + e.getMessage());
        }
        return false;
    }

    // ==================== U - update ====================
    public boolean actualizar(Connection c, Persona p) throws SQLException {
        String sql = "UPDATE persona SET nombre = ?, apellido = ?, email = ?, identificacion = ?, telefono = ? WHERE id_persona = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getIdentificacion());
            ps.setString(5, p.getTelefono());
            ps.setInt(6, p.getIdPersona());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Persona p) {
        try (Connection c = conexion.conexion()) {
            return actualizar(c, p);
        } catch (SQLException e) {
            System.err.println("Error al actualizar persona: " + e.getMessage());
        }
        return false;
    }

    // ==================== D - delete ====================
    public boolean eliminar(Connection c, int idPersona) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM persona WHERE id_persona = ?")) {
            ps.setInt(1, idPersona);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idPersona) {
        try (Connection c = conexion.conexion()) {
            return eliminar(c, idPersona);
        } catch (SQLException e) {
            System.err.println("Error al eliminar persona: " + e.getMessage());
        }
        return false;
    }
}
