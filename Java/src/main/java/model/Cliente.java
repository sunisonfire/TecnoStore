package model;

import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    private final int idCliente;
    private static final long serialVersionUID = 1L;
    // Constructor completo (desde BD)
    public Cliente(int idCliente, int idPersona, String nombre, String apellido,String email, String identificacion, String telefono) {
        super(idPersona, nombre, apellido, email, identificacion, telefono);
        this.idCliente = idCliente;
    }

    // Constructor para crear uno nuevo (Sin el id)
    public Cliente(String nombre, String apellido, String email,String identificacion, String telefono) {
        super(nombre, apellido, email, identificacion, telefono);
        this.idCliente = 0; // lo asigna la BD
    }

    public int getIdCliente() {
        return idCliente;
    }
  
    @Override
    public String toString() {
        return """
           +-------------------+----------------------+
           | Campo             | Valor                |
           +-------------------+----------------------+
           | Id                | %-20d |
           +-------------------+----------------------+"""
                .formatted(idCliente);
}
}
