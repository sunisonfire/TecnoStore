package model;

public class Celular {

    private int idCelular, stock;
    private String modelo;
    private double precio;
    private Marca marca;
    private SistemaOperativo sistemaOperativo;
    private Gama gama;


    public enum SistemaOperativo {
        IOS, ANDROID
    }

    public enum Gama {
        ALTA, MEDIA, BAJA
    }

    //Constructor con todo para cuando se trae de la bd
    public Celular(int idCelular, int stock, String modelo, double precio, Marca marca, SistemaOperativo sistemaOperativo, Gama gama) {
        this.idCelular = idCelular;
        this.stock = stock;
        this.modelo = modelo;
        this.precio = precio;
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
        this.gama = gama;
    }

    //Constructor para la insercion de nuevos celulares a la base de datos, no lleva el id por que ese se crea en la bd auto incremental
    public Celular(int stock, String modelo, double precio, Marca marca, SistemaOperativo sistemaOperativo, Gama gama) {
        this.stock = stock;
        this.modelo = modelo;
        this.precio = precio;
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
        this.gama = gama;
    }

    public int getIdCelular() {
        return idCelular;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public SistemaOperativo getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(SistemaOperativo sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

        @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | ID                | %-20d |
           | Username          | %-20s |
           | Contraseña        | %-20s |
           | Precio            | $%-19.2f |
           | Stock             | %-20d |
           | Sistema operativo | %-20s |
           | Gama              | %-20s |
           +-------------------+----------------------+"""
                .formatted(
                        idCelular,
                        modelo,
                        marca,
                        precio,
                        stock,
                        sistemaOperativo,
                        gama);
    }

}
