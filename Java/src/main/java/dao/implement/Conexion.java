package dao.implement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// SINGLETON
public class Conexion {

    private boolean mensajeMostrado = false;
    private static Conexion instancia;   // LA UNICA INSTANCIA DE LA CLASE
    // PERTENECE A CLASE, NO OBJETO

    //CONSTRUCTOR PRIVADO
    private Conexion() {
    }               // evita new Conexion() desde fuera

    
    //STATIC QUE ENTREGA LA INSTANCIAA
    public static synchronized Conexion getInstancia() {
        //          EN UN HILO A LA VEZ
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    // Conexion instancia = new Conexion();
    // CLASE     VARIABLE     OBJETO

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
