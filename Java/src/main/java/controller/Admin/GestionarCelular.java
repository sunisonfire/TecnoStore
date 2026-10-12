package controller.Admin;

import dao.implement.CelularDao;
import dao.implement.MarcaDao;
import java.util.List;
import java.util.Scanner;
import model.Celular;
import model.Celular.Gama;
import model.Celular.SistemaOperativo;
import model.Marca;
import view.MenuAdministrador;
import view.Validaciones.Confirmacion;

public class GestionarCelular {

    private final Scanner sc = new Scanner(System.in);
    private final Confirmacion confirmacion = new Confirmacion();

    public int pedirIdCelular() {
        while (true) {
            System.out.print("ID del celular: ");
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero.");
            }
        }
    }

    public int pedirStock(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            try {
                int stock = Integer.parseInt(sc.nextLine().trim());
                if (stock >= 0) {
                    return stock;
                }
                System.out.println("El stock no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero.");
            }
        }
    }

    public double pedirPrecio(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            try {
                double precio = Double.parseDouble(sc.nextLine().trim());
                if (precio > 0) {
                    return precio;
                }
                System.out.println("El precio debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número (ej: 1500000 o 1500000.50).");
            }
        }
    }

    public String pedirTextoNoVacio(String etiqueta) {
        while (true) {
            System.out.print(etiqueta);
            String t = sc.nextLine().trim();
            if (!t.isEmpty()) {
                return t;
            }
            System.out.println("No puede estar vacío.");
        }
    }

    public SistemaOperativo pedirSistemaOperativo() {
        while (true) {
            System.out.print("Sistema operativo (1 = IOS, 2 = ANDROID): ");
            String t = sc.nextLine().trim();
            if (t.equals("1")) {
                return SistemaOperativo.IOS;
            }
            if (t.equals("2")) {
                return SistemaOperativo.ANDROID;
            }
            System.out.println("Opción inválida.");
        }
    }

    public Gama pedirGama() {
        while (true) {
            System.out.print("Gama (1 = ALTA, 2 = MEDIA, 3 = BAJA): ");
            String t = sc.nextLine().trim();
            if (t.equals("1")) {
                return Gama.ALTA;
            }
            if (t.equals("2")) {
                return Gama.MEDIA;
            }
            if (t.equals("3")) {
                return Gama.BAJA;
            }
            System.out.println("Opción inválida.");
        }
    }

// Muestra las marcas y devuelve el id elegido
    public int pedirIdMarca(List<Marca> marcas) {
        System.out.println("Marcas disponibles:");
        for (Marca m : marcas) {
            System.out.println(m.getIdMarca() + " - " + m.getNombre());
        }
        while (true) {
            System.out.print("ID de la marca: ");
            try {
                int id = Integer.parseInt(sc.nextLine().trim());
                for (Marca m : marcas) {
                    if (m.getIdMarca() == id) {
                        return id;
                    }
                }
                System.out.println("Esa marca no existe.");
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero.");
            }
        }
    }

    public void mostrarCelulares(List<Celular> celulares) {
    if (celulares.isEmpty()) {
        System.out.println("""

                +------------------------------------------+
                |       NO HAY CELULARES REGISTRADOS      |
                +------------------------------------------+
                """);
        return;
    }

    System.out.printf("""
            +------+------------------------+--------------+--------------+--------+----------+--------+
            | ID   | Modelo                 | Marca        | Precio       | Stock  | SO       | Gama   |
            +------+------------------------+--------------+--------------+--------+----------+--------+
            """);

    for (Celular c : celulares) {
        System.out.printf(
                "| %-4d | %-22s | %-12s | $%-11.2f | %-6d | %-8s | %-6s |%n",
                c.getIdCelular(),
                c.getModelo(),
                c.getMarca().getNombre(),
                c.getPrecio(),
                c.getStock(),
                c.getSistemaOperativo(),
                c.getGama()
        );
    }

    System.out.println(
            "+------+------------------------+--------------+--------------+--------+----------+--------+");
}

    private final CelularDao celularDao = new CelularDao();
    private final MarcaDao marcaDao = new MarcaDao();
    private final MenuAdministrador menu = new MenuAdministrador();

    // ---------- Crear ----------
    public void agregarCelular() {
        String modelo = pedirTextoNoVacio("Modelo: ");
        int idMarca = pedirIdMarca(marcaDao.obtenerTodos());
        Marca marca = marcaDao.obtenerPorId(idMarca);
        double precio = pedirPrecio("Precio: ");
        int stock = pedirStock("Stock: ");
        var so = pedirSistemaOperativo();
        var gama = pedirGama();

        Celular celular = new Celular(stock, modelo, precio, marca, so, gama);

        if (celularDao.insertar(celular)) {
            System.out.println("Celular agregado con ID " + celular.getIdCelular() + ".");
        } else {
            System.out.println("No se pudo agregar el celular.");
        }
    }

    // ---------- Listar ----------
    public void listarCelular() {
        mostrarCelulares(celularDao.obtenerTodos());
    }

    // ---------- Eliminar ----------
    public void eliminarCelular() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }

        if (!confirmacion.validarConfirmacion("¿Seguro que quieres eliminar " + celular.getModelo() + "?")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (celularDao.eliminar(celular.getIdCelular())) {
            System.out.println("Celular eliminado.");
        } else {
            System.out.println("No se pudo eliminar (puede tener ventas asociadas).");
        }
    }

    // ---------- Actualizar ----------
    public void actualizarModelo() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        celular.setModelo(pedirTextoNoVacio("Nuevo modelo: "));
        guardar(celular, "Modelo actualizado.");
    }

    public void actualizarMarca() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        int idMarca = pedirIdMarca(marcaDao.obtenerTodos());
        celular.setMarca(marcaDao.obtenerPorId(idMarca));
        guardar(celular, "Marca actualizada.");
    }

    public void actualizarStock() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        int nuevo = pedirStock("Nuevo stock: ");
        if (celularDao.actualizarStock(celular.getIdCelular(), nuevo)) {
            System.out.println("Stock actualizado.");
        } else {
            System.out.println("No se pudo actualizar el stock.");
        }
    }

    public void actualizarSistemaOperativo() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        celular.setSistemaOperativo(pedirSistemaOperativo());
        guardar(celular, "Sistema operativo actualizado.");
    }

    public void actualizarGama() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        celular.setGama(pedirGama());
        guardar(celular, "Gama actualizada.");
    }

    public void actualizarPrecio() {
        Celular celular = buscarCelular();
        if (celular == null) {
            return;
        }
        celular.setPrecio(pedirPrecio("Nuevo precio: "));
        guardar(celular, "Precio actualizado.");
    }

    // ---------- Auxiliares ----------
    // Pide el id y busca el celular; si no existe avisa y devuelve null
    private Celular buscarCelular() {
        mostrarCelulares(celularDao.obtenerTodos());
        int id = pedirIdCelular();
        Celular celular = celularDao.obtenerPorId(id);
        if (celular == null) {
            System.out.println("No existe un celular con ese ID.");
        }
        return celular;
    }

    private void guardar(Celular celular, String mensajeOk) {
        if (celularDao.actualizar(celular)) {
            System.out.println(mensajeOk);
        } else {
            System.out.println("No se pudo actualizar el celular.");
        }
    }
}
