package controller.Admin;

import dao.Interfaces.IMarcaDao;
import dao.implement.MarcaDao;
import model.Marca;
import view.MenuAdministrador;

public class GestionarMarca {

    private final IMarcaDao marcaDao = new MarcaDao();
    private final MenuAdministrador menu = new MenuAdministrador();

    // C - CREAR
    public void crearMarca() {
        String nombre = menu.pedirTexto("Nombre de la nueva marca: ");

        if (nombre.isEmpty()) {
            menu.mostrarMensaje("El nombre no puede estar vacío.");
            return;
        }
        if (marcaDao.existeNombre(nombre)) {
            menu.mostrarMensaje("Ya existe una marca con ese nombre.");
            return;
        }

        Marca marca = new Marca(nombre);
        if (marcaDao.insertar(marca)) {
            menu.mostrarMensaje("Marca creada correctamente:");
            menu.mostrarMensaje(marca.toString());
        } else {
            menu.mostrarMensaje("No se pudo crear la marca.");
        }
    }

    // D - ELIMINAR
    public void eliminarMarca() {
        String nombre = menu.pedirTexto("Nombre de la marca a eliminar: ");

        Marca marca = marcaDao.obtenerPorNombre(nombre);
        if (marca == null) {
            menu.mostrarMensaje("No existe una marca con ese nombre.");
            return;
        }

        menu.mostrarMensaje(marca.toString());
        String confirmar = menu.pedirTexto("¿Seguro que deseas eliminarla? (s/n): ");

        if (!confirmar.equalsIgnoreCase("s")) {
            menu.mostrarMensaje("Eliminación cancelada.");
            return;
        }

        if (marcaDao.eliminar(marca.getIdMarca())) {
            menu.mostrarMensaje("Marca eliminada correctamente.");
        } else {
            menu.mostrarMensaje("No se pudo eliminar. Puede que tenga celulares asociados.");
        }
    }
}