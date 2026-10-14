package dao.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Celular;


public interface ICelularDao {

    // U - ACTUALIZAR
    boolean actualizar(Celular celular);

    // Fija el stock a un valor exacto (por ejemplo, desde el menú del administrador)
    boolean actualizarStock(int idCelular, int nuevoStock);

    // Para ventas: resta unidades solo si hay suficiente stock, en una sola operación.
    // Devuelve false si no alcanza el stock (así no queda negativo).
    boolean descontarStock(Connection c, int idCelular, int cantidad) throws SQLException;

    //D -ELIMINAR
    boolean eliminar(int idCelular);

    // C - CREAR
    // Inserta y deja el id generado en el objeto
    boolean insertar(Celular celular);

    // R - LEER
    Celular obtenerPorId(int idCelular);

    List<Celular> obtenerPorMarca(int idMarca);

    List<Celular> obtenerTodos();

    // DEVOLVER UNIDADES SI SE CANCELA VENTA
    boolean reponerStock(Connection c, int idCelular, int cantidad) throws SQLException;
    
    //EXTRA IG
    int obtenerUltimoId();
}
