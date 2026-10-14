package strategy;
// INTERFAZ PARA HACER QUE SEPAN CALCULAR UN DESCUENTO
public interface TipoDescuento {
    double descuento(double subtotal);   // DEVOLVER EL VALOR A DESCONTAR
}