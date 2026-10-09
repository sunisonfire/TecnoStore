package view.Validaciones;


public class EnteroRango extends Entero {
    
    public int validarEnteroRango(String mensaje, int min, int max) {
        int n;
        do {
            n = validarEntero(mensaje + " (" + min + "-" + max + ")");
            if (n < min || n > max) {
                System.out.println("Error, fuera de rango");
            }
        } while (n < min || n > max);
        return n;
    }
}
