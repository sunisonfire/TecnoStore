package controller.Cliente;

import controller.Admin.GestionarVenta;
import model.Cliente;

public class GestionarPedido {

    private final GestionarVenta gestionarVenta = new GestionarVenta();

    public void crearPedido(Cliente cliente) {
        gestionarVenta.crearVenta(cliente);
    }

    public void verMisPedidos(Cliente cliente) {
        // lista solo las ventas de este cliente
    }
}