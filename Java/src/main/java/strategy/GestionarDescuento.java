package strategy;
//USA LA ESTRATEGIA QUE LE PASO PARA GESTIONAR LOS DESCUENTOS SIN CALCULAR NADA
public class GestionarDescuento {

    private final TipoDescuento tipoDescuento;

    public GestionarDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public double aplicar(double subtotal) {
    if (tipoDescuento == null) {
        throw new IllegalStateException("No se ha seleccionado una estrategia de descuento");
    }//     TOTAL  -   VALOR DEL DESCUENTO
    return subtotal - tipoDescuento.descuento(subtotal);  
}
}