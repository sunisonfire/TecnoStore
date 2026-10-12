package controller.Admin;

import dao.Interfaces.IAdministradorDao;
import dao.implement.AdministradorDao;
import model.Administrador;
import view.MenuAdministrador;

public class GestionarAdmin {
    private final IAdministradorDao dao = new AdministradorDao();
    private final MenuAdministrador menu = new MenuAdministrador();
    private Administrador adminActual;

    public boolean iniciarSesion() {
        for (int intentos = 1; intentos <= 3; intentos++) {
            String username = menu.pedirUsername();
            long contrasena = menu.pedirContrasena();

            Administrador admin = dao.login(username, contrasena);
            if (admin != null) {
                adminActual = admin;
                menu.mostrarMensaje("Bienvenido, " + admin.getUsername());
                return true;
            }
            menu.mostrarMensaje("Credenciales incorrectas (" + intentos + "/3)");
        }
        menu.mostrarMensaje("Demasiados intentos fallidos.");
        return false;
    }

    public Administrador getAdminActual() {
        return adminActual;
    }

    public void cerrarSesion() {
        adminActual = null;
    }
}