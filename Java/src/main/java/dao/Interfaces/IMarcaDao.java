package dao.Interfaces;

import java.util.List;
import model.Marca;

public interface IMarcaDao {

    // U - ACTUALIZAR
    boolean actualizar(Marca marca);

    // D - ELIMINAR
    boolean eliminar(int idMarca);

    // DUPLICADOS?
    boolean existeNombre(String nombre);

    boolean existeNombreDeOtra(String nombre, int idMarca);

    // C - CREAR
    // Inserta y deja el id generado en el objeto
    boolean insertar(Marca marca);

    // R - LEER
    Marca obtenerPorId(int idMarca);

    Marca obtenerPorNombre(String nombre);

    List<Marca> obtenerTodos();
    
}
