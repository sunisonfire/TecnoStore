package view.Validaciones;

import java.util.Scanner;

public class EnteroGrande {
    private final Scanner sc = new Scanner(System.in);
    
    public long validarEnteroGrande(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error, se espera un valor entero");
            }
        }
    }
}
