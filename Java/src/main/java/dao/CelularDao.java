package dao;

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

public class CelularDao {

    private Connection connection;
    private MarcaDao marcaDao;

    public CelularDao(Conexion conexion, MarcaDao marcaDao) {
    this.connection = conexion.conexion();
    this.marcaDao = marcaDao;
}

    // Convierte una fila del ResultSet en un Celular (se reutiliza en todos los SELECT)
    private Celular construirCelular(ResultSet rs) throws SQLException {
        Marca marca = marcaDao.obtenerPorId(rs.getInt("id_marca"));

        return new Celular(
                rs.getInt("id_celular"),
                rs.getInt("stock"),
                rs.getString("modelo"),
                rs.getDouble("precio"),
                marca,
                SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
                Gama.valueOf(rs.getString("gama"))
        );
    }

    // C - create
    public boolean insertarCelular(Celular celular) {
        String sql = "insert into celular (id_marca, modelo, stock, sistema_operativo, gama, precio) "
                   + "values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, celular.getMarca().getIdMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getStock());
            stmt.setString(4, celular.getSistemaOperativo().name());
            stmt.setString(5, celular.getGama().name());
            stmt.setDouble(6, celular.getPrecio());

            if (stmt.executeUpdate() > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        celular.setIdCelular(keys.getInt(1));
                    }
                }
                System.out.println("Celular insertado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // R - read
    public Celular obtenerPorId(int id) {
        String sql = "select * from celular where id_celular = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirCelular(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public List<Celular> obtenerTodos() {
        String sql = "select * from celular order by modelo";
        List<Celular> celulares = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                celulares.add(construirCelular(rs));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return celulares;
    }

    public List<Celular> obtenerPorMarca(int idMarca) {
        String sql = "select * from celular where id_marca = ? order by modelo";
        List<Celular> celulares = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMarca);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    celulares.add(construirCelular(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return celulares;
    }

    // U - update
    public boolean actualizarCelular(Celular celular) {
        String sql = "update celular set id_marca = ?, modelo = ?, stock = ?, "
                   + "sistema_operativo = ?, gama = ?, precio = ? where id_celular = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, celular.getMarca().getIdMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getStock());
            stmt.setString(4, celular.getSistemaOperativo().name());
            stmt.setString(5, celular.getGama().name());
            stmt.setDouble(6, celular.getPrecio());
            stmt.setInt(7, celular.getIdCelular());

            if (stmt.executeUpdate() > 0) {
                System.out.println("Celular actualizado correctamente: " + celular.getModelo());
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // Actualizar solo el stock (para ventas)
    public boolean actualizarStock(int idCelular, int nuevoStock) {
        String sql = "update celular set stock = ? where id_celular = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idCelular);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Stock actualizado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    // D - delete
    public boolean eliminarCelular(int id) {
        String sql = "delete from celular where id_celular = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Celular eliminado correctamente");
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}