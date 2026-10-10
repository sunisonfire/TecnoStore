package dao.Interfaces;

import java.util.List;
import model.Administrador;

public interface IAdministradorDao {

    // ==================== U - update ====================
    // Actualiza persona y administrador en una sola transacción
    boolean actualizar(Administrador a);

    // ==================== D - delete ====================
    // Borra el administrador y luego su persona
    boolean eliminar(int idAdministrador);

    boolean existeEmail(String email);

    boolean existeIdentificacion(String identificacion);

    // ---------- Duplicados ----------
    boolean existeUsername(String username);

    // Para actualizar: ¿otro administrador distinto ya usa este username?
    boolean existeUsernameDeOtro(String username, int idAdministrador);

    // ==================== C - create ====================
    // Inserta en persona y luego en administrador. Deja el idPersona en el objeto.
    boolean insertar(Administrador a);

    // ==================== R - read ====================
    // Inicio de sesión: el administrador si username y contraseña coinciden, o null
    Administrador login(String username, long contrasena);

    Administrador obtenerPorId(int idAdministrador);

    List<Administrador> obtenerTodos();
    
}
