package model;

public class Marca {

    private int idMarca;
    private String nombre;

    // Constructor completo para traer de la bd
    public Marca(int idMarca, String nombre) {
        this.idMarca = idMarca;
        this.nombre = nombre;
    }

    //Constructor sin id para creacion de nuevas marcas
    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

        @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | ID                | %-20d |
           | Nombre            | %-20s |
           +-------------------+----------------------+"""
                .formatted(
                        idMarca,
                        nombre);
    }

}
