package model;

public class Administrador extends Persona {
    private final int idAdministrador;
    private String username;
    private long contraseña;

    //Constructor
    public Administrador(int idAdministrador, int idPersona, String nombre, String apellido,String email, String identificacion, String telefono,String username, long contraseña) {
        super(idPersona, nombre, apellido, email, identificacion, telefono);
        this.idAdministrador = idAdministrador;
        this.username = username;
        this.contraseña = contraseña;
    }
    
    //Getters y Setters
    public int getIdAdministrador() {
        return idAdministrador; 
    }
    public String getUsername() {
        return username; 
    }
    public void setUsername(String username) {
        this.username = username; 
    }
    public long getContraseña() {
        return contraseña; 
    }
    public void setContraseña(long contraseña) {
        this.contraseña = contraseña; 
    }

    
    @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | ID                | %-20d |
           | Username          | %-20s |
           +-------------------+----------------------+"""
                .formatted(
                        idAdministrador,
                        username,
                        contraseña);
    }
}
