package view;

import dao.MarcaDao;
import model.Celular;
import model.Marca;
import model.Celular.Gama;
import model.Celular.SistemaOperativo;

public class MenuIngreso {

    Validaciones v = new Validaciones();

    public Celular ingresarCelular() {
        String modelo = v.validarTexto("Ingrese el modelo");
        double precio = v.validarEntero("Ingrese el precio");
        int stock = v.validarEntero("Ingrese el stock");

        SistemaOperativo sistemaOperativo = elegirSistemaOperativo();
        Gama gama = elegirGama();

        int idMarca = v.validarEntero("Ingrese el id de la marca");
        Marca marca = MarcaDao.buscar(idMarca);
        if (marca == null) {
            System.out.println("La marca no existe");
            return null;
        }

        return new Celular(stock, modelo, precio, marca, sistemaOperativo, gama);
    }

    private SistemaOperativo elegirSistemaOperativo() {
        while (true) {
            int opcion = v.validarEntero("""
                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                        Sistema operativo 
                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶

                       [ 1 ]  IOS
                       [ 2 ]  ANDROID

                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                    """);

            switch (opcion) {
                case 1 -> {
                    return SistemaOperativo.IOS;
                }
                case 2 -> {
                    return SistemaOperativo.ANDROID;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private Gama elegirGama() {
        while (true) {
            int opcion = v.validarEntero("""
                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                             Gama 
                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶

                       [ 1 ]  ALTA
                       [ 2 ]  MEDIA
                       [ 3 ]  BAJA

                    ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                    """);

            switch (opcion) {
                case 1 -> {
                    return Gama.ALTA;
                }
                case 2 -> {
                    return Gama.MEDIA;
                }
                case 3 -> {
                    return Gama.BAJA;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    public int escogerOpcionPersona() {

        return v.validarEntero("""
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                  ¿Cómo deseas ingresar?           
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶

                   [ 1 ]  Administrador
                   [ 2 ]  Cliente
                   [ 3 ]  Salir

                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                """);

    }

}
