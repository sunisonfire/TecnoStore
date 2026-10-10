package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Administrador;

/**
 * DAO de administrador. Los datos personales viven en la tabla persona, por eso
 * insertar, actualizar y eliminar se apoyan en PersonaDao dentro de una transacción.
 */
public class AdministradorDao {

    private final Conexion conexion = new Conexion();
    private final PersonaDao personaDao = new PersonaDao();

    // Consulta base: une persona con administrador
    private static final String SELECT_BASE = """
            SELECT a.id_administrador, p.id_persona, p.nombre, p.apellido,
                   p.email, p.identificacion, p.telefono,
                   a.username, a.contrasena
            FROM administrador a
            JOIN persona p ON p.id_persona = a.id_persona
            """;

    // Convierte una fila del ResultSet en un Administrador
    private Administrador mapear(ResultSet rs) throws SQLException {
        return new Administrador(
                rs.getInt("id_administrador"),
                rs.getInt("id_persona"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("email"),
                rs.getString("identificacion"),
                rs.getString("telefono"),
                rs.getString("username"),
                rs.getLong("contrasena"));
    }

    // ==================== C - create ====================

    // Inserta en persona y luego en administrador. Deja el idPersona en el objeto.
    public boolean insertar(Administrador a) {
        String sql = "INSERT INTO administrador (id_persona, username, contrasena) VALUES (?, ?, ?)";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                if (!personaDao.insertar(c, a)) {
                    throw new SQLException("No se pudo insertar la persona");
                }

                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, a.getIdPersona());
                    ps.setString(2, a.getUsername());
                    ps.setLong(3, a.getContraseña());
                    ps.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar administrador: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================

    // Inicio de sesión: el administrador si username y contraseña coinciden, o null
    public Administrador login(String username, long contrasena) {
        String sql = SELECT_BASE + " WHERE a.username = ? AND a.contrasena = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setLong(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al iniciar sesión: " + e.getMessage());
        }
        return null;
    }

    public Administrador obtenerPorId(int idAdministrador) {
        String sql = SELECT_BASE + " WHERE a.id_administrador = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idAdministrador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar administrador: " + e.getMessage());
        }
        return null;
    }

    public List<Administrador> obtenerTodos() {
        List<Administrador> administradores = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY p.nombre, p.apellido";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                administradores.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar administradores: " + e.getMessage());
        }
        return administradores;
    }

    // ---------- Duplicados ----------

    public boolean existeUsername(String username) {
        return existe("SELECT 1 FROM administrador WHERE username = ?", username);
    }

    // Para actualizar: ¿otro administrador distinto ya usa este username?
    public boolean existeUsernameDeOtro(String username, int idAdministrador) {
        return existe("SELECT 1 FROM administrador WHERE username = ? AND id_administrador <> ?",
                username, idAdministrador);
    }

    public boolean existeEmail(String email) {
        return personaDao.existeEmail(email);
    }

    public boolean existeIdentificacion(String identificacion) {
        return personaDao.existeIdentificacion(identificacion);
    }

    private boolean existe(String sql, Object... parametros) {
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

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

    // Actualiza persona y administrador en una sola transacción
    public boolean actualizar(Administrador a) {
        String sql = "UPDATE administrador SET username = ?, contrasena = ? WHERE id_administrador = ?";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                personaDao.actualizar(c, a);

                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setString(1, a.getUsername());
                    ps.setLong(2, a.getContraseña());
                    ps.setInt(3, a.getIdAdministrador());
                    ps.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar administrador: " + e.getMessage());
        }
        return false;
    }

    // ==================== D - delete ====================

    // Borra el administrador y luego su persona
    public boolean eliminar(int idAdministrador) {
        Administrador a = obtenerPorId(idAdministrador);
        if (a == null) {
            return false;
        }

        String sql = "DELETE FROM administrador WHERE id_administrador = ?";

        try (Connection c = conexion.conexion()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(sql)) {
                    ps.setInt(1, idAdministrador);
                    ps.executeUpdate();
                }

                personaDao.eliminar(c, a.getIdPersona());

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar administrador: " + e.getMessage());
        }
        return false;
    }
}