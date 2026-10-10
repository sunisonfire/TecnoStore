package controller.Cliente;

import controller.Admin.GestionarCliente;
import view.MenuCliente;

public class GestionarCuenta {

    private final GestionarCliente gestionarCliente = new GestionarCliente();
    private final MenuCliente menuCliente = new MenuCliente();

    private Cliente clienteActual;

    // Devuelve true si el login fue exitoso
    public boolean iniciarSesion() {
        // aquí pides el email como ya lo haces
        clienteActual = gestionarCliente.login(email);
        return clienteActual != null;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void cerrarSesion() {
        clienteActual = null;
    }

    public void verPerfil() {
        System.out.println("Nombre: " + clienteActual.getNombre());
        System.out.println("Apellido: " + clienteActual.getApellido());
        System.out.println("Email: " + clienteActual.getEmail());
        System.out.println("Identificación: " + clienteActual.getIdentificacion());
        System.out.println("Teléfono: " + clienteActual.getTelefono());
    }

    // Devuelve true si la cuenta fue eliminada
    public boolean eliminarCuenta() {
        int confirmar = menuCliente.confirmar("¿Seguro que desea eliminar su cuenta?");
        if (confirmar == 1) {
            gestionarCliente.eliminar(clienteActual.getId());
            System.out.println("Cuenta eliminada.");
            clienteActual = null;
            return true;
        }
        System.out.println("Operación cancelada.");
        return false;
    }

    public void actualizarNombre() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizarApellido() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizarEmail() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizarIdentificacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void actualizarTelefono() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}