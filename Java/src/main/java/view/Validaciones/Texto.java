package view.Validaciones;

import java.util.Scanner;


public class Texto {
    private final Scanner sc = new Scanner(System.in);

    public String validarTexto(String mensaje) {
        String texto;
        do {
            System.out.println(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Error, no puede estar vacío");
            }
        } while (texto.isEmpty());
        return texto;
    }
}

