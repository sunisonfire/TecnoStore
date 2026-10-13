package strategy;

public class DescuentoPorMonto implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return subtotal > 5_000_000 ? subtotal * 0.05 : 0;   // 5% si supera 5 millones
    }
}