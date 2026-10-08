package model;

public class Administrador {
    private final int idAdministrador;
    private String username;
    private long contraseña;

    public Administrador(int idAdministrador, String username, long contraseña) {
        this.idAdministrador = idAdministrador;
        this.username = username;
        this.contraseña = contraseña;
    }

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
           | Contraseña        | %-20s |
           +-------------------+----------------------+"""
                .formatted(
                        idAdministrador,
                        username,
                        contraseña);
    }
}
