package view;

public class MenuIngreso {
    
    Validaciones v = new Validaciones();
    
    public Producto ingresarProducto() {
        String nombre = v.validarTexto("Ingrese el nombre");
        int stock = v.validarEntero("Ingrese el stock");
        int stockMinimo = v.validarEntero("Ingrese el stock minimo");
        int idBodega = v.validarEntero("Ingrese id de la bodega");
        Bodega bodega = bodegaDao.buscar(idBodega);
        if (bodega == null) {
            System.out.println("La bodega no existe");
            return null;
        }
        return new Producto(nombre, stock, stockMinimo, bodega);
    }

    public Bodega ingresarBodega() {
    String ciudad = v.validarTexto("Ingrese la ciudad de la bodega");
    return new Bodega(ciudad);
}

    public int escogerOpcionPersona() {

        return v.validarEntero("""
                               1. Administrador
                               2. Cliente
                               3. Salir
                               """);

    }


}
