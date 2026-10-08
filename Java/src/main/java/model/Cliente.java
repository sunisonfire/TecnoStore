package model;

public class Cliente {
    private final int idCliente;
    private String nombre;
    private String apellido;
    private String email;
    private String identificacion;
    private String telefono;

    public Cliente(int idCliente, String nombre, String apellido, String email, String identificacion, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | ID                | %-20d |
           | Nombre            | %-20s |
           | Apellido          | %-20s |
           | Email             | $%-19.2f |
           | Identificacion    | %-20d |
           | Telefono          | %-20s |
           +-------------------+----------------------+"""
                .formatted(
                        idCliente,
                        nombre,
                        apellido,
                        email,
                        identificacion,
                        telefono);
}
}
