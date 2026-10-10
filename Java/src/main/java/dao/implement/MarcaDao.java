package dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Marca;

/**
 * DAO de marca. Lo usa CelularDao para armar la marca de cada celular.
 */
public class MarcaDao {

    private final Conexion conexion = new Conexion();

    private static final String SELECT_BASE = """
            SELECT id_marca, nombre
            FROM marca
            """;

    // Convierte una fila del ResultSet en una Marca
    private Marca mapear(ResultSet rs) throws SQLException {
        return new Marca(
                rs.getInt("id_marca"),
                rs.getString("nombre"));
    }

    // ==================== C - create ====================

    // Inserta y deja el id generado en el objeto
    public boolean insertar(Marca marca) {
        String sql = "INSERT INTO marca (nombre) VALUES (?)";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, marca.getNombre());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    marca.setIdMarca(keys.getInt(1));   // requiere setIdMarca en Marca
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar marca: " + e.getMessage());
        }
        return false;
    }

    // ==================== R - read ====================

    public Marca obtenerPorId(int idMarca) {
        return obtenerUna(SELECT_BASE + " WHERE id_marca = ?", idMarca);
    }

    public Marca obtenerPorNombre(String nombre) {
        return obtenerUna(SELECT_BASE + " WHERE nombre = ?", nombre);
    }

    public List<Marca> obtenerTodos() {
        List<Marca> marcas = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY nombre";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                marcas.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar marcas: " + e.getMessage());
        }
        return marcas;
    }

    // Busca una sola fila con un parámetro (int o String)
    private Marca obtenerUna(String sql, Object parametro) {
        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar marca: " + e.getMessage());
        }
        return null;
    }

    // ---------- Duplicados ----------

    public boolean existeNombre(String nombre) {
        return existe("SELECT 1 FROM marca WHERE nombre = ?", nombre);
    }

    // Para actualizar: ¿otra marca distinta ya usa este nombre?
    public boolean existeNombreDeOtra(String nombre, int idMarca) {
        return existe("SELECT 1 FROM marca WHERE nombre = ? AND id_marca <> ?", nombre, idMarca);
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

    public boolean actualizar(Marca marca) {
        String sql = "UPDATE marca SET nombre = ? WHERE id_marca = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getIdMarca());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar marca: " + e.getMessage());
        }
        return false;
    }

    // ==================== D - delete ====================

    public boolean eliminar(int idMarca) {
        String sql = "DELETE FROM marca WHERE id_marca = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idMarca);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar marca: " + e.getMessage());
        }
        return false;
    }
}