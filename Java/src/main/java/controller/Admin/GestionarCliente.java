package controller.Admin;

import dao.implement.ClienteDao;
import model.Cliente;
import view.MenuAdministrador;

public class GestionarCliente {

    private final ClienteDao clienteDao = new ClienteDao();
    private final MenuAdministrador menuAdministrador = new MenuAdministrador();

    // Antes se llamaba registrarse(String), pero en realidad hacía login.
    public Cliente login(String email) {
        return clienteDao.login(email);
    }

    // Registro: lo llama GestionarFlujo con registrarse()
    public void registrarse() {
        String nombre = menuAdministrador.pedirTexto("Nombre: ");
        String apellido = menuAdministrador.pedirTexto("Apellido: ");

        String email;
        while (true) {
            email = menuAdministrador.pedirTexto("Email: ");
            if (!clienteDao.existeEmail(email)) {
                break;
            }
            System.out.println("Ese email ya está registrado.");
        }

        String identificacion;
        while (true) {
            identificacion = menuAdministrador.pedirTexto("Identificación: ");
            if (!clienteDao.existeIdentificacion(identificacion)) {
                break;
            }
            System.out.println("Esa identificación ya está registrada.");
        }

        String telefono = menuAdministrador.pedirTexto("Teléfono: ");

        Cliente cliente = new Cliente(nombre, apellido, email, identificacion, telefono);

        if (clienteDao.insertar(cliente)) {
            System.out.println("Registro exitoso.");
        } else {
            System.out.println("No se pudo completar el registro.");
        }
    }

    public boolean existeEmailDeOtro(String email, int idPersona) {
        return clienteDao.existeEmailDeOtro(email, idPersona);
    }

    public boolean existeIdentificacionDeOtro(String identificacion, int idPersona) {
        return clienteDao.existeIdentificacionDeOtro(identificacion, idPersona);
    }

    public boolean actualizar(Cliente cliente) {
        return clienteDao.actualizar(cliente);
    }

    public void listarCliente() {
        menuAdministrador.mostrarClientes(clienteDao.obtenerTodos());
    }

    public boolean eliminarCliente(int idCliente) {
        return clienteDao.eliminar(idCliente);
    }
}
