package strategy;
//                              IMPLEMENTA TIPO DE DESCUENTO
public class DescuentoPorMonto implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return subtotal > 5_000_000 ? subtotal * 0.05 : 0; // 5% SI ES MAYOR A 5M
    }
}