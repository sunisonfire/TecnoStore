package model;

public class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String email;
    private String identificacion;
    private String telefono;

    // Constructor completo (desde BD)
    public Persona(int idPersona, String nombre, String apellido, String email,String identificacion, String telefono) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.telefono = telefono;
    }

     // Constructor sin id (nueva persona), ahora con apellido
    public Persona(String nombre, String apellido, String email,String identificacion, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.identificacion = identificacion;
        this.telefono = telefono;
    }

    //Getters y Setter
    public int getIdPersona() {
        return idPersona;
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
           | Email             | %-20s |
           | Identificacion    | %-20s |
           | Telefono          | %-20s |
           +-------------------+----------------------+"""
                .formatted(idPersona, nombre, apellido, email, identificacion, telefono);
    }
 
}
