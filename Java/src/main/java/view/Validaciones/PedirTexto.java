package view.Validaciones;

import java.util.Scanner;

public class PedirTexto {
    //// Texto no vacío y solo letras (con tildes, ñ y espacios)
    ///
    private final Scanner sc = new Scanner(System.in);

    public String pedirTexto(String mensaje) {
        String t;
        do {
            System.out.println(mensaje);
            t = sc.nextLine().trim();
            if (!t.matches("[\\p{L} ]{2,}")) {
                System.out.println("Error, use solo letras (mínimo 2).");
                t = "";
            }
        } while (t.isEmpty());
        return t;
    }
}
