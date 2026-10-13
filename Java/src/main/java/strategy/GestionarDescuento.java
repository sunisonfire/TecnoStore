package strategy;

public class GestionarDescuento {

    private final TipoDescuento tipoDescuento;

    public GestionarDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public double aplicar(double subtotal) {
    if (tipoDescuento == null) {
        throw new IllegalStateException("No se ha seleccionado una estrategia de descuento");
    }
    return subtotal - tipoDescuento.descuento(subtotal);   // base ya descontada, sin IVA
}
}