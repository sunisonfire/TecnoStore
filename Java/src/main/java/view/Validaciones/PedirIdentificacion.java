package view.Validaciones;

import java.util.Scanner;

public class PedirIdentificacion {
    // Solo dígitos, entre 6 y 10

    private final Scanner sc = new Scanner(System.in);

    public String pedirIdentificacion(String mensaje) {
        String t;
        do {
            System.out.println(mensaje);
            t = sc.nextLine().trim();
            if (!t.matches("\\d{6,10}")) {
                System.out.println("Error, debe tener entre 6 y 10 dígitos.");
                t = "";
            }
        } while (t.isEmpty());
        return t;
    }
}
