/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DatosVenta implements Serializable{

    private List<Venta> venta = new ArrayList<>();
    private final String nombreArchivo = "ventas.dat";


    //ADMININSTRAR EL ARCHIVO.
    public void cargar() {
        File f = new File(nombreArchivo);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
                ventas = (List<Venta>) ois.readObject();
            } catch (Exception e) {
            }
        }
    }

    public void reemplazar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(ventas);
        } catch (Exception e) {e.printStackTrace();}
    }

    //TENER LA LISTA CARGADA CON LOS DATOS DEL ARCHIVO
    public DatosVenta() {
        cargar();
    }

    //GESTIONAR LA LISTA
    public void guardar(Venta venta) {
        ventas.add(venta);
        reemplazar();
    }

    public List<Venta> getObtenerLista() {
        cargar();
        return ventas;
    }

}
