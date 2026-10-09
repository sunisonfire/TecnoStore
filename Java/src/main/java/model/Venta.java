package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private LocalDateTime fechaHora;
    private MetodoPago metodoPago;
    private Estado estado;
    private Lugar lugar;
    private double subtotal;
    private double total;
    private List<DetalleVenta> detalles;
    
    public enum MetodoPago {
        PSE, TARJETA, NEQUI, TRANSFERENCIA
    }

    public enum Estado {
        PENDIENTE, ENVIADO, CANCELADO
    }
    
    public enum Lugar {
        LOCAL, DOMICILIO
    }
    // Desde BD
    public Venta(int idVenta, Cliente cliente, LocalDateTime fechaHora, MetodoPago metodoPago,
                 Estado estado, Lugar lugar, double subtotal, double total,
                 List<DetalleVenta> detalles) {
        this.idVenta = idVenta;
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.lugar = lugar;
        this.subtotal = subtotal;
        this.total = total;
        this.detalles = detalles;
    }

    // Venta nueva
    public Venta(Cliente cliente, LocalDateTime fechaHora, MetodoPago metodoPago,
                 Estado estado, Lugar lugar) {
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.lugar = lugar;
        this.detalles = new ArrayList<>();
        calcularTotales();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        calcularTotales();
    }

    public void eliminarDetalle(DetalleVenta detalle) {
        detalles.remove(detalle);
        calcularTotales();
    }

    private void calcularTotales() {
        this.subtotal = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
        this.total = this.subtotal;
    }

    // getters y setters (sin setSubtotal ni setTotal)...

    @Override
    public String toString() {
        // No imprime los detalles aquí para evitar recursión y tablas anidadas
        return "Venta #" + idVenta + " | " + fechaHora + " | Total: $" + total;
    }
}