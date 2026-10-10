package controller.Cliente;

import controller.Admin.GestionarCliente;
import java.util.Scanner;
import view.MenuCliente;
import view.Validaciones.Confirmacion;
import view.Validaciones.Correo;
import view.Validaciones.Telefono;

public class GestionarCuenta {

    private final Confirmacion confirmacion = new Confirmacion();
    private final Correo correo = new Correo();
    private final Telefono telefono = new Telefono();
    private final Scanner sc = new Scanner(System.in);
    private final GestionarCliente gestionarCliente = new GestionarCliente();
    private final MenuCliente menuCliente = new MenuCliente();

    private Cliente clienteActual;

    // Devuelve true si el login fue exitoso
    public boolean iniciarSesion() {
        String email = correo.validarCorreo("Ingrese su correo:");
        clienteActual = gestionarCliente.login(email);
        if (clienteActual == null) {
            System.out.println("No existe una cuenta con ese correo.");
        }
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
        if (confirmacion.validarConfirmacion("¿Seguro que desea eliminar su cuenta?")) {
            gestionarCliente.eliminar(clienteActual.getId());
            System.out.println("Cuenta eliminada.");
            clienteActual = null;
            return true;
        }
        System.out.println("Operación cancelada.");
        return false;
    }

    public void actualizarNombre() {
        String nuevo = pedirTexto("Nuevo nombre:");
        clienteActual.setNombre(nuevo);
        gestionarCliente.actualizar(clienteActual);
        System.out.println("Nombre actualizado.");
    }

    public void actualizarApellido() {
        String nuevo = pedirTexto("Nuevo apellido:");
        clienteActual.setApellido(nuevo);
        gestionarCliente.actualizar(clienteActual);
        System.out.println("Apellido actualizado.");
    }

    public void actualizarEmail() {
        String nuevo = correo.validarCorreo("Nuevo correo:");

        if (nuevo.equalsIgnoreCase(clienteActual.getEmail())) {
            System.out.println("Ese ya es su correo actual.");
            return;
        }
        if (gestionarCliente.existeEmail(nuevo)) {
            System.out.println("Ese correo ya está registrado.");
            return;
        }

        clienteActual.setEmail(nuevo);
        gestionarCliente.actualizar(clienteActual);
        System.out.println("Correo actualizado.");
    }

    public void actualizarIdentificacion() {
        String nueva = pedirIdentificacion("Nueva identificación:");

        if (gestionarCliente.existeIdentificacion(nueva)) {
            System.out.println("Esa identificación ya está registrada.");
            return;
        }

        clienteActual.setIdentificacion(nueva);
        gestionarCliente.actualizar(clienteActual);
        System.out.println("Identificación actualizada.");
    }

    public void actualizarTelefono() {
        String nuevo = telefono.validarTelefono("Nuevo teléfono:");
        clienteActual.setTelefono(nuevo);
        gestionarCliente.actualizar(clienteActual);
        System.out.println("Teléfono actualizado.");
    }

    // ---------- Auxiliares ----------

    // Texto no vacío y solo letras (con tildes, ñ y espacios)
    private String pedirTexto(String mensaje) {
        String t;
        do {
            System.out.println(mensaje);
            t = sc.nextLine().trim();
            if (!t.matches("[\\p{L} ]{2,}")) {
                System.out.println("Error, use solo letras (mínimo 2).");
                t = "";
            }
        } while (t.isEmpty());
        return t;
    }

    // Solo dígitos, entre 6 y 10
    private String pedirIdentificacion(String mensaje) {
        String t;
        do {
            System.out.println(mensaje);
            t = sc.nextLine().trim();
            if (!t.matches("\\d{6,10}")) {
                System.out.println("Error, debe tener entre 6 y 10 dígitos.");
                t = "";
            }
        } while (t.isEmpty());
        return t;
    }
}