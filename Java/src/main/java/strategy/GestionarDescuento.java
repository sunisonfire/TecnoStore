package strategy;

public class GestionarDescuento {

    private TipoDescuento tipoDescuento;

    public GestionarDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public double calcularTotal(double subtotal) {
        if (tipoDescuento == null) {
            throw new IllegalStateException("No se ha seleccionado una estrategia de descuento");
        }
        double base = subtotal - tipoDescuento.descuento(subtotal);
        return base * 1.19;   // IVA 19% sobre el valor ya descontado
    }
}