package dao.implement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private boolean mensajeMostrado = false;
    private static Conexion instancia;   // única instancia de la clase

    private Conexion() {
    }               // evita new Conexion() desde fuera

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection conexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore", "root", "Enhypen!0");
            if (!mensajeMostrado) {
                System.out.println("Conexion exitosa!");
                mensajeMostrado = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
