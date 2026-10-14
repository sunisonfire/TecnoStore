package strategy;
//                        ESTRATEGIA DE SIN DESCUENTO
public class SinDescuento implements TipoDescuento {
    @Override
    public double descuento(double subtotal) {
        return 0;
    }
}

