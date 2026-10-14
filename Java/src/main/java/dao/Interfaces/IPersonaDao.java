package dao.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Persona;

public interface IPersonaDao {

    // U - ACTUALIZAR
    boolean actualizar(Connection c, Persona p) throws SQLException;

    boolean actualizar(Persona p);

    // D - ELIMINAR
    boolean eliminar(Connection c, int idPersona) throws SQLException;

    boolean eliminar(int idPersona);

    //  DUPLICADOS?
    boolean existeEmail(String email);

    boolean existeEmailDeOtra(String email, int idPersona);

    boolean existeIdentificacion(String identificacion);

    boolean existeIdentificacionDeOtra(String identificacion, int idPersona);

    // C - CREAR
    // Versión para transacciones: usa la conexión recibida y deja el id en p
    boolean insertar(Connection c, Persona p) throws SQLException;
    
    boolean insertar(Persona p);

    Persona obtenerPorEmail(String email);

    // R - LEER
    Persona obtenerPorId(int idPersona);

    Persona obtenerPorIdentificacion(String identificacion);

    List<Persona> obtenerTodos();
    
}
