package view.Validaciones;

import java.util.Scanner;

public class Entero {
    private final Scanner sc = new Scanner(System.in);
    public int validarEntero(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error, se espera un valor entero");
            }
        }
    }
}
