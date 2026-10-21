package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatosVenta {
    

    private List<Venta> ventas = new ArrayList<>();
    private final String nombreArchivo = "C:/Users/ASUS/Documents/JAVAA/TecnoStoreD/Reporte/reporte_ventas.dat";

    // TENER LA LISTA CARGADA CON LOS DATOS DEL ARCHIVO
    public DatosVenta() {
        cargar();
    }

    // ADMINISTRAR EL ARCHIVO

    @SuppressWarnings("unchecked")
    public void cargar() {
        File f = new File(nombreArchivo);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                ventas = (List<Venta>) ois.readObject();
            } catch (Exception e) {
                System.out.println("No se pudo leer " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }

    public void reemplazar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(ventas);
        } catch (Exception e) {
            System.out.println("No se pudo escribir " + nombreArchivo + ": " + e.getMessage());
        }
    }

    // GESTIONAR LA LISTA

    public void guardar(Venta venta) {
        ventas.add(venta);
        reemplazar();
    }

    // Copia completa: deja el archivo igual a la lista recibida (por ejemplo, la de la BD)
    public void guardarTodas(List<Venta> nuevas) {
        ventas = new ArrayList<>(nuevas);
        reemplazar();
    }

    public List<Venta> getObtenerLista() {
        return ventas;
    }

    public void actualizar(Venta venta, int id) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getIdVenta() == id) {
                ventas.set(i, venta);
                reemplazar();
                break;
            }
        }
    }

    public boolean verificar(int id) {
        for (Venta v : ventas) {
            if (v.getIdVenta() == id) {
                return true;
            }
        }
        return false;
    }

    public void eliminar(int id) {
        for (Venta v : ventas) {
            if (v.getIdVenta() == id) {
                ventas.remove(v);
                reemplazar();
                break;
            }
        }
    }
}