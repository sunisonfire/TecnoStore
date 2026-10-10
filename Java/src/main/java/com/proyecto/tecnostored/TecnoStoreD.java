package com.proyecto.tecnostored;

import dao.implement.Conexion;
import java.sql.Connection;

public class TecnoStoreD {

    public static void main(String[] args) {
        Conexion db = Conexion.getInstance();
        Connection conn = db.conectar();
        
        if (conn != null) {
            // Iniciar el menú principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciar();
        } else {
            System.err.println("No se pudo establecer coneccion con la base de datos");
        }
    }
}
