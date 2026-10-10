package controller.Admin;

import dao.implement.ClienteDao;
import model.Cliente;

public class GestionarCliente {

    private final ClienteDao clienteDao = new ClienteDao();

    public Cliente registrarse(String email) {
        return clienteDao.login(email);
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

    public boolean eliminarCliente(int idCliente) {
        return clienteDao.eliminar(idCliente);
    }

    public void listarCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
