package view.Validaciones;

import java.util.Scanner;

public class Correo {
    private final Scanner sc = new Scanner(System.in);
    public String validarCorreo(String mensaje) {
        String c;
        do {
            System.out.println(mensaje);
            c = sc.nextLine().trim();
            if (!c.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
                System.out.println("Error, correo inválido");
                c = "";
            }
        } while (c.isEmpty());
        return c;
    }
}
