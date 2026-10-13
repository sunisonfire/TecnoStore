package strategy;

public class DescuentoClienteFrecuente implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return subtotal * 0.10;   // 10% fijo
    }
}