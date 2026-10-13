package controller.Admin;

public final class GestionarIVA {

    public static final double TASA = 0.19;   // el 19% vive solo aquí

    private GestionarIVA() {
    }

    public static double calcular(double base) {
        return base * TASA;           // valor del IVA
    }

    public static double conIva(double base) {
        return base + calcular(base); // base + IVA
    }
}