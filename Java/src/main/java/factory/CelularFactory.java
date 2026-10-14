package factory;

import model.Celular;
import model.Celular.Gama;
import model.Celular.SistemaOperativo;
import model.Marca;
// FACTORYY
public class CelularFactory {

    // Celular NUEVO (sin id, lo asigna la BD). Lo usa agregarCelular()
    public static Celular crear(int stock, String modelo, double precio, Marca marca,
                                SistemaOperativo so, Gama gama) {
        if (precio <= 0) throw new IllegalArgumentException("El precio debe ser positivo");
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
        return new Celular(stock, modelo, precio, marca, so, gama);
    }

    // Celular que VIENE de la BD (con id). Lo usa CelularDao.mapear()
    public static Celular desdeBD(int idCelular, int stock, String modelo, double precio,
                                  Marca marca, SistemaOperativo so, Gama gama) {
        return new Celular(idCelular, stock, modelo, precio, marca, so, gama);
    }
}