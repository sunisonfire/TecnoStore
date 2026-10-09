package view.Validaciones;

public class DecimalPositivo extends Decimal {

    public double validarDecimalPositivo(String mensaje) {
        double valor;
        do {
            valor = validarDecimal(mensaje);
            if (valor <= 0) {
                System.out.println("Error, debe ser mayor que 0");
            }
        } while (valor <= 0);
        return valor;
    }
}