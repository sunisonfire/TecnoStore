package view.Validaciones;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Fecha {
    private final Scanner sc = new Scanner(System.in);
    
    public LocalDate validarFecha(String mensaje) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.println(mensaje + " (dd/MM/yyyy)");
            try {
                return LocalDate.parse(sc.nextLine().trim(), f);
            } catch (DateTimeParseException e) {
                System.out.println("Error, fecha inválida");
            }
        }
    }
}
