package dao.Interfaces;

import java.util.List;
import model.Administrador;

public interface IAdministradorDao {

    // U - ACTUALIZAR
    // Actualiza persona y administrador en una sola transacción
    boolean actualizar(Administrador a);

    // D - ELIMINAR
    // Borra el administrador y luego su persona
    boolean eliminar(int idAdministrador);

    //EXISTE
    boolean existeEmail(String email);

    boolean existeIdentificacion(String identificacion);

    // DUPLICADO?
    boolean existeUsername(String username);

    boolean existeUsernameDeOtro(String username, int idAdministrador);

    // C - CREAR
    // Inserta en persona y luego en administrador. Deja el idPersona en el objeto.
    boolean insertar(Administrador a);

    // R - LEER
    // Inicio de sesión: el administrador si username y contraseña coinciden, o null
    Administrador login(String username, long contrasena);

    Administrador obtenerPorId(int idAdministrador);

    List<Administrador> obtenerTodos();
    
}
