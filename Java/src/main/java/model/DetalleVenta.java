package model;

public class DetalleVenta {
    private int idDetalleVenta;
    private Celular celular;
    private int cantidad;
    private double precioUnitario;

    // Constructor completo (desde BD)
    public DetalleVenta(int idDetalleVenta, Celular celular, int cantidad, double precioUnitario) {
        this.idDetalleVenta = idDetalleVenta;
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Constructor sin id (nuevo detalle)
    public DetalleVenta(Celular celular, int cantidad, double precioUnitario) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Se calcula, ya no se guarda ni tiene setter
    public double getSubtotal() {
        return cantidad * precioUnitario;
    }

    public int getIdDetalleVenta() { return idDetalleVenta; }
    public void setIdDetalleVenta(int idDetalleVenta) { this.idDetalleVenta = idDetalleVenta; }
    public Celular getCelular() { return celular; }
    public void setCelular(Celular celular) { this.celular = celular; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | ID                | %-20d |
           | Celular           | %-20s |
           | Cantidad          | %-20d |
           | Precio Unitario   | $%-19.2f |
           | Subtotal          | $%-19.2f |
           +-------------------+----------------------+"""
                .formatted(
                        idDetalleVenta,
                        celular.getModelo(),   // ajusta al getter que tenga tu clase Celular
                        cantidad,
                        precioUnitario,
                        getSubtotal());
    }
}