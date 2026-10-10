package dao.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Persona;

public interface IPersonaDao {

    // ==================== U - update ====================
    boolean actualizar(Connection c, Persona p) throws SQLException;

    boolean actualizar(Persona p);

    // ==================== D - delete ====================
    boolean eliminar(Connection c, int idPersona) throws SQLException;

    boolean eliminar(int idPersona);

    // ---------- Duplicados (consultas livianas, sin armar objetos) ----------
    boolean existeEmail(String email);

    // Para actualizar: ¿otra persona distinta ya usa este correo?
    boolean existeEmailDeOtra(String email, int idPersona);

    boolean existeIdentificacion(String identificacion);

    // Para actualizar: ¿otra persona distinta ya usa esta identificación?
    boolean existeIdentificacionDeOtra(String identificacion, int idPersona);

    // ==================== C - create ====================
    // Versión para transacciones: usa la conexión recibida y deja el id en p
    boolean insertar(Connection c, Persona p) throws SQLException;

    // Versión independiente
    boolean insertar(Persona p);

    Persona obtenerPorEmail(String email);

    // ==================== R - read ====================
    Persona obtenerPorId(int idPersona);

    Persona obtenerPorIdentificacion(String identificacion);

    List<Persona> obtenerTodos();
    
}
