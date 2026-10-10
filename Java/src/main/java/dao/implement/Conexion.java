package dao.implement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore", "root", "Enhypen!0");
                System.out.println("Conexion exitosa!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
