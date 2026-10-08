package view;

import java.util.Scanner;

public class Validaciones {

    public String validarTexto(String mensaje) {
        boolean validacion;
        String texto = "";
        Scanner x = new Scanner(System.in);
        int contador = 0;
        do {
            validacion = true;
            System.out.println(mensaje);
            texto = x.nextLine();
            for (int i = 0; i < texto.length(); i++) {
                contador += texto.charAt(i) == ' ' ? 1 : 0;
                if (!Character.isLetter(texto.charAt(i)) || texto.charAt(i) != ' ') {
                    if (texto.charAt(i) == ' ' && i == 0) {
                        validacion = false;
                        break;
                    }
                }
            }
        } while (validacion == false);
        return texto;
    }

    public double validarDecimal(String mensaje) {
        System.out.println(mensaje);
        Scanner x = new Scanner(System.in);
        while (!x.hasNextDouble()) {
            System.out.println("Error, se espera un valor decimal");
        }
        return x.nextDouble();
    }

    public int validarEntero(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextInt();
    }

    public long validarEnteroGrande(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextLong()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextLong();
    }
    public boolean validarConfirmacion(String mensaje) {
    Scanner x = new Scanner(System.in);
    String r;
    do {
        System.out.println(mensaje + " (s/n)");
        r = x.nextLine().trim().toLowerCase();
    } while (!r.equals("s") && !r.equals("n"));
    return r.equals("s");
}
}

