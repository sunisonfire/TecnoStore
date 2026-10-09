package view.Validaciones;

import java.util.Scanner;

public class Telefono {
    private final Scanner sc = new Scanner(System.in);
    
    public String validarTelefono(String mensaje) {
        String t;
        do {
            System.out.println(mensaje);
            t = sc.nextLine().trim();
            if (!t.matches("3\\d{9}")) {
                System.out.println("Error, debe ser un celular de 10 dígitos que empiece por 3");
                t = "";
            }
        } while (t.isEmpty());
        return t;
    }
}
