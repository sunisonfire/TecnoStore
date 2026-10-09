package view.Validaciones;

import java.util.Scanner;

public class Confirmacion {
    private final Scanner sc = new Scanner(System.in);
    public boolean validarConfirmacion(String mensaje) {
        String r;
        do {
            System.out.println(mensaje + " (s/n)");
            r = sc.nextLine().trim().toLowerCase();
        } while (!r.equals("s") && !r.equals("n"));
        return r.equals("s");
    }
}
