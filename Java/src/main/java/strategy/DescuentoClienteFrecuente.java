package strategy;
//CLASES DEL DESCUENTO                IMPLEMENTA EL TIPO DE DESCUENTO
public class DescuentoClienteFrecuente implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return subtotal * 0.10;   // 10% FIJO
    }
}