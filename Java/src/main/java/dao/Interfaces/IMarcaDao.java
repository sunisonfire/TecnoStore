package dao.Interfaces;

import java.util.List;
import model.Marca;

public interface IMarcaDao {

    // ==================== U - update ====================
    boolean actualizar(Marca marca);

    // ==================== D - delete ====================
    boolean eliminar(int idMarca);

    // ---------- Duplicados ----------
    boolean existeNombre(String nombre);

    // Para actualizar: ¿otra marca distinta ya usa este nombre?
    boolean existeNombreDeOtra(String nombre, int idMarca);

    // ==================== C - create ====================
    // Inserta y deja el id generado en el objeto
    boolean insertar(Marca marca);

    // ==================== R - read ====================
    Marca obtenerPorId(int idMarca);

    Marca obtenerPorNombre(String nombre);

    List<Marca> obtenerTodos();
    
}
