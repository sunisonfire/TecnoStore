package controller;

import view.MenuCliente;
import view.MenuAdministrador;
import view.MenuIngreso;
import controller.Admin.GestionarCelular;
import controller.Admin.GestionarCliente;
import controller.Admin.GestionarVenta;
import controller.Cliente.GestionarCuenta;
import controller.Cliente.GestionarPedido;
import controller.Admin.GestionarAdmin;
import controller.Admin.GestionarReporte;
import controller.Admin.GestionarMarca;

public class GestionarFlujo {
    GestionarMarca gestionarMarca = new GestionarMarca();
    GestionarReporte gestionarReporte = new GestionarReporte();
    GestionarCuenta gestionarCuenta = new GestionarCuenta();
    GestionarPedido gestionarPedido = new GestionarPedido();
    GestionarVenta gestionarVenta = new GestionarVenta();
    GestionarCliente gestionarCliente = new GestionarCliente();
    GestionarCelular gestionarCelular = new GestionarCelular();
    MenuAdministrador menuAdministrador = new MenuAdministrador();
    MenuCliente menuCliente = new MenuCliente();
    MenuIngreso menuIngreso = new MenuIngreso();
    GestionarAdmin gestionarAdmin = new GestionarAdmin();

    //Escoger tipo de persona (Admin o Cliente)
    public void inicio() {
        int opcion;
        do {
            opcion = menuIngreso.escogerOpcionPersona();

            switch (opcion) {
                case 1 -> {
                    if (gestionarAdmin.iniciarSesion()) {
                        menuAdminAccion();   // solo entra si el login fue exitoso
                        gestionarAdmin.cerrarSesion();
                    }
                }

                case 2 ->
                    menuClienteLogin();
                case 0 ->
                    System.out.println("¡Hasta pronto!");
            }
        } while (opcion != 0);
    }

    // Admin
    // Que va a gestionar
    private void menuAdminAccion() {
        int opcion;
        do {
            opcion = menuAdministrador.escogerAccion();

            switch (opcion) {
                case 1 ->
                    menuAdminCelular();
                case 2 ->
                    menuAdminCliente();
                case 3 ->
                    menuAdminVenta();
                case 4 ->
                    menuAdminReporte();
                case 5 ->
                    menuAccionMarca();
                case 0 ->
                    System.out.println("¡Hasta pronto!");
            }
        } while (opcion != 0);
    }

    // Gestiionar Celulares
    private void menuAdminCelular() {
        int opcion;
        do {
            opcion = menuAdministrador.opcionGestionarCelular();

            switch (opcion) {
                case 1 ->
                    gestionarCelular.agregarCelular();
                case 2 ->
                    gestionarCelular.listarCelular();
                case 3 ->
                    menuActualizarCelular();
                case 4 ->
                    gestionarCelular.eliminarCelular();
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }

    //Actualizar celular
    private void menuActualizarCelular() {
        int opcion;
        do {
            opcion = menuAdministrador.opcionActualizarCelular();

            switch (opcion) {
                case 1 ->
                    gestionarCelular.actualizarModelo();
                case 2 ->
                    gestionarCelular.actualizarMarca();
                case 3 ->
                    gestionarCelular.actualizarStock();
                case 4 ->
                    gestionarCelular.actualizarSistemaOperativo();
                case 5 ->
                    gestionarCelular.actualizarGama();
                case 6 ->
                    gestionarCelular.actualizarPrecio();
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }

    //Gestionar Cliente
    private void menuAdminCliente() {
    int opcion;
    do {
        opcion = menuAdministrador.opcionGestionarCliente();

        switch (opcion) {
            case 1 -> gestionarCliente.listarCliente();
            case 0 -> System.out.println("Volviendo...");
        }
    } while (opcion != 0);
}

    // Gestionar Venta
    private void menuAdminVenta() {
        int opcion;
        do {
            opcion = menuAdministrador.opcionGestionarVenta();

            switch (opcion) {
                case 1 ->
                    gestionarVenta.actualizarVenta();
                case 2 ->
                    gestionarVenta.listarVenta();
                case 3 ->
                    gestionarVenta.eliminarVenta();
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }

    // Cliente
    // Registrarse vs Iniciar sesión
    public void menuClienteLogin() {
        int opcion;
        do {
            opcion = menuCliente.escogerTipoLogin();

            switch (opcion) {
                case 1 ->
                    gestionarCliente.registrarse();
                case 2 -> {
                    if (gestionarCuenta.iniciarSesion()) {
                        menuClienteAccion();   // solo entra si el login fue exitoso
                    }
                }
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }

// Mi cuenta
    private void menuClienteAccion() {
        int opcion;
        do {
            opcion = menuCliente.opcionAccionCliente();

            switch (opcion) {
                case 1 ->
                    menuClientePedido();
                case 2 ->
                    gestionarCuenta.verPerfil();
                case 3 ->
                    menuActualizarDatos();
                case 4 -> {
                    if (gestionarCuenta.eliminarCuenta()) {
                        opcion = 0;   // la cuenta ya no existe, sale del menú
                    }
                }
                case 0 ->
                    gestionarCuenta.cerrarSesion();
            }
        } while (opcion != 0);
    }

// Pedidos del cliente
    private void menuClientePedido() {
    int opcion;
    do {
        opcion = menuCliente.gestionPedido();

        switch (opcion) {
            case 1 -> gestionarPedido.crearPedido(gestionarCuenta.getClienteActual());
            case 2 -> gestionarPedido.verMisPedidos(gestionarCuenta.getClienteActual());
            case 3 -> gestionarPedido.cancelarPedido(gestionarCuenta.getClienteActual());
            case 0 -> System.out.println("Volviendo...");
        }
    } while (opcion != 0);
}

    // Actualizar datos
    private void menuActualizarDatos() {
        int opcion;
        do {
            opcion = menuCliente.opcionActualizarDatos();

            switch (opcion) {
                case 1 ->
                    gestionarCuenta.actualizarNombre();
                case 2 ->
                    gestionarCuenta.actualizarApellido();
                case 3 ->
                    gestionarCuenta.actualizarEmail();
                case 4 ->
                    gestionarCuenta.actualizarIdentificacion();
                case 5 ->
                    gestionarCuenta.actualizarTelefono();
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }
    // Reportes
private void menuAdminReporte() {
    int opcion;
    do {
        opcion = menuAdministrador.opcionReportes();

        switch (opcion) {
            case 1 -> gestionarReporte.celularesMasVendidos();
            case 2 -> gestionarReporte.celularesStockBajo();
            case 3 -> gestionarReporte.ventasTotales();
            case 4 -> gestionarReporte.exportarVentas();
            case 0 -> System.out.println("Volviendo...");
        }
    } while (opcion != 0);
}

        // Gestionar Marcas
    private void menuAccionMarca() {
        int opcion;
        do {
            opcion = menuAdministrador.escogerAccionMarca();

            switch (opcion) {
                case 1 ->
                    gestionarMarca.crearMarca();
                case 2 ->
                    gestionarMarca.eliminarMarca();
                case 0 ->
                    System.out.println("Volviendo...");
            }
        } while (opcion != 0);
    }
}

    
