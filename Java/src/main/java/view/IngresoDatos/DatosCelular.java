package view.IngresoDatos;

import dao.implement.MarcaDao;
import model.Celular;
import model.Marca;
import view.Validaciones.Entero;
import view.Validaciones.Texto;
import view.Validaciones.Decimal;

public class DatosCelular {
        Entero v = new Entero();
        Texto t= new Texto();
        Decimal d= new Decimal();

    public Celular ingresarCelular() {
        //Modelo
        String modelo = t.validarTexto("Ingrese el modelo del celular");
        //Precio
        double precio = d.validarDecimal("Ingrese el precio");
        //Stock
        int stock = v.validarEntero("Ingrese el stock");
        //Sistema Operativo (Enum)
        Celular.SistemaOperativo sistemaOperativo = elegirSistemaOperativo();
        //Gama (Enum)
        Celular.Gama gama = elegirGama();
        //Marca como id
        int idMarca = v.validarEntero("Ingrese el id de la marca");
        Marca marca = MarcaDao.buscar(idMarca);
        if (marca == null) {
            System.out.println("La marca no existe");
            return null;
        }

        return new Celular(stock, modelo, precio, marca, sistemaOperativo, gama);
    }

    
    private Celular.SistemaOperativo elegirSistemaOperativo() {
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
                    return Celular.SistemaOperativo.IOS;
                }
                case 2 -> {
                    return Celular.SistemaOperativo.ANDROID;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private Celular.Gama elegirGama() {
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
                    return Celular.Gama.ALTA;
                }
                case 2 -> {
                    return Celular.Gama.MEDIA;
                }
                case 3 -> {
                    return Celular.Gama.BAJA;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }
}
