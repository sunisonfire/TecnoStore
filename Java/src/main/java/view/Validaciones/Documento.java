package view.Validaciones;

import java.util.Scanner;

public class Documento {
    private final Scanner sc = new Scanner(System.in);
    public String validarDocumento(String mensaje) {
        String d;
        do {
            System.out.println(mensaje);
            d = sc.nextLine().trim();
            if (!d.matches("\\d{6,10}")) {
                System.out.println("Error, debe tener entre 6 y 10 dígitos");
                d = "";
            }
        } while (d.isEmpty());
        return d;
    }
}
