package strategy;

public class SinDescuento implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return 0;
    }
}

