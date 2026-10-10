package view.IngresoDatos;

import dao.implement.MarcaDao;
import java.util.List;
import model.Celular;
import model.Marca;
import view.Validaciones.Entero;
import view.Validaciones.Texto;
import view.Validaciones.Decimal;

public class DatosCelular {

    Entero v = new Entero();
    Texto t = new Texto();
    Decimal d = new Decimal();

    private final MarcaDao marcaDao = new MarcaDao();

    public Celular ingresarCelular() {
        String modelo = t.validarTexto("Ingrese el modelo del celular");
        double precio = d.validarDecimal("Ingrese el precio");
        int stock = v.validarEntero("Ingrese el stock");
        Celular.SistemaOperativo sistemaOperativo = elegirSistemaOperativo();
        Celular.Gama gama = elegirGama();

        // Marca: se muestran las que existen para que elija un id válido
        List<Marca> marcas = marcaDao.obtenerTodos();
        if (marcas.isEmpty()) {
            System.out.println("No hay marcas registradas. Cree una marca primero.");
            return null;
        }
        for (Marca m : marcas) {
            System.out.println(m.getIdMarca() + ". " + m.getNombre());
        }

        Marca marca;
        do {
            int idMarca = v.validarEntero("Ingrese el id de la marca");
            marca = marcaDao.obtenerPorId(idMarca);
            if (marca == null) {
                System.out.println("La marca no existe, intente de nuevo.");
            }
        } while (marca == null);

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
                default ->
                    System.out.println("Opción inválida, intente de nuevo.");
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
                default ->
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }
}
