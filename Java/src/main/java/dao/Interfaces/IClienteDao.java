package dao.Interfaces;

import java.util.List;
import model.Cliente;

public interface IClienteDao {

    // U - ACTUALIZAR
    // Todos los datos del cliente están en persona
    boolean actualizar(Cliente cl);

    // D - ELIMINAR
    // Borra el cliente y luego su persona
    boolean eliminar(int idCliente);

    // DUPLICADOS?
    boolean existeEmail(String email);

    boolean existeEmailDeOtro(String email, int idPersona);

    boolean existeIdentificacion(String identificacion);

    boolean existeIdentificacionDeOtro(String identificacion, int idPersona);

    // C - CREAR O MAS BIEN, REGISTRARSE
    // Inserta en persona y luego en cliente. Deja el idPersona en el objeto.
    boolean insertar(Cliente cl);

    // R - LEER
    // Inicio de sesión: el cliente con ese correo, o null si no existe
    Cliente login(String email);

    Cliente obtenerPorId(int idCliente);

    List<Cliente> obtenerTodos();
    
}
