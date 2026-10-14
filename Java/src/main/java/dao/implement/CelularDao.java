package dao.implement;

import dao.Interfaces.ICelularDao;
import factory.CelularFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Celular;
import model.Celular.Gama;
import model.Celular.SistemaOperativo;
import model.Marca;

//DEPENDE DE MARCA
public class CelularDao implements ICelularDao {

    private final Conexion conexion = Conexion.getInstancia();
    private final MarcaDao marcaDao = new MarcaDao();   // debe tener obtenerPorId(int)

    private static final String SELECT_BASE = """
            SELECT id_celular, id_marca, modelo, stock, sistema_operativo, gama, precio
            FROM celular
            """;

    // Convierte una fila del ResultSet en un Celular (se reutiliza en todos los SELECT)
    private Celular mapear(ResultSet rs) throws SQLException {
    Marca marca = marcaDao.obtenerPorId(rs.getInt("id_marca"));

    return CelularFactory.desdeBD(
            rs.getInt("id_celular"),
            rs.getInt("stock"),
            rs.getString("modelo"),
            rs.getDouble("precio"),
            marca,
            SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
            Gama.valueOf(rs.getString("gama")));
}

    // C - CREAR

    // Inserta y deja el id generado en el objeto
    @Override
    public boolean insertar(Celular celular) {
        String sql = """
                INSERT INTO celular (id_marca, modelo, stock, sistema_operativo, gama, precio)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, celular.getMarca().getIdMarca());
            ps.setString(2, celular.getModelo());
            ps.setInt(3, celular.getStock());
            ps.setString(4, celular.getSistemaOperativo().name());
            ps.setString(5, celular.getGama().name());
            ps.setDouble(6, celular.getPrecio());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    celular.setIdCelular(keys.getInt(1));   // requiere setIdCelular en Celular
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar celular: " + e.getMessage());
        }
        return false;
    }

    // R - LEER

    @Override
    public Celular obtenerPorId(int idCelular) {
        String sql = SELECT_BASE + " WHERE id_celular = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idCelular);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar celular: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Celular> obtenerTodos() {
        return obtenerLista(SELECT_BASE + " ORDER BY modelo", null);
    }

    @Override
    public List<Celular> obtenerPorMarca(int idMarca) {
        return obtenerLista(SELECT_BASE + " WHERE id_marca = ? ORDER BY modelo", idMarca);
    }

    private List<Celular> obtenerLista(String sql, Object parametro) {
        List<Celular> celulares = new ArrayList<>();

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setObject(1, parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    celulares.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar celulares: " + e.getMessage());
        }
        return celulares;
    }

    // U - ACTUALIZAR

    @Override
    public boolean actualizar(Celular celular) {
        String sql = """
                UPDATE celular SET id_marca = ?, modelo = ?, stock = ?,
                                   sistema_operativo = ?, gama = ?, precio = ?
                WHERE id_celular = ?
                """;

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, celular.getMarca().getIdMarca());
            ps.setString(2, celular.getModelo());
            ps.setInt(3, celular.getStock());
            ps.setString(4, celular.getSistemaOperativo().name());
            ps.setString(5, celular.getGama().name());
            ps.setDouble(6, celular.getPrecio());
            ps.setInt(7, celular.getIdCelular());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar celular: " + e.getMessage());
        }
        return false;
    }

    // Fija el stock a un valor exacto (por ejemplo, desde el menú del administrador)
    @Override
    public boolean actualizarStock(int idCelular, int nuevoStock) {
        String sql = "UPDATE celular SET stock = ? WHERE id_celular = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idCelular);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
        }
        return false;
    }

    // Para ventas: resta unidades solo si hay suficiente stock, en una sola operación.
    // Devuelve false si no alcanza el stock (así no queda negativo).
    @Override
    public boolean descontarStock(Connection c, int idCelular, int cantidad) throws SQLException {
        String sql = "UPDATE celular SET stock = stock - ? WHERE id_celular = ? AND stock >= ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idCelular);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        }
    }

    // Para cancelar o eliminar una venta: devuelve las unidades al stock
    @Override
    public boolean reponerStock(Connection c, int idCelular, int cantidad) throws SQLException {
        String sql = "UPDATE celular SET stock = stock + ? WHERE id_celular = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idCelular);
            return ps.executeUpdate() > 0;
        }
    }

    // D - ELIMINAR

    @Override
    public boolean eliminar(int idCelular) {
        String sql = "DELETE FROM celular WHERE id_celular = ?";

        try (Connection c = conexion.conexion();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, idCelular);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar celular: " + e.getMessage());
        }
        return false;
    }
    
    @Override
public int obtenerUltimoId() {
    String sql = "SELECT MAX(id_celular) FROM celular";

    try (Connection c = conexion.conexion();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1);   // devuelve 0 si la tabla está vacía
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener el último id: " + e.getMessage());
    }
    return 0;
}
}