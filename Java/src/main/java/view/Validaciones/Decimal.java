package view.Validaciones;

import java.util.Scanner;

public class Decimal {
    private final Scanner sc = new Scanner(System.in);
    public double validarDecimal(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Error, se espera un valor decimal");
            }
        }
    }
}
