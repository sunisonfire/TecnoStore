package view.Validaciones;

import java.util.Scanner;

public class SoloLetras {
    private final Scanner sc = new Scanner(System.in);

    public String validarSoloLetras(String mensaje) {
        String texto;
        do {
            System.out.println(mensaje);
            texto = sc.nextLine().trim();
            if (!texto.matches("[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+( [A-Za-zÁÉÍÓÚáéíóúÑñÜü]+)*")) {
                System.out.println("Error, solo letras y espacios simples");
                texto = "";
            }
        } while (texto.isEmpty());
        return texto;
    }
}
