package view;

public class MenuAdministrador {
    
    Validaciones v = new Validaciones();
    
    public int escogerAccion() {

        return v.validarEntero("""
                ╔════════════════════════════════╗
                ║   ELIJA QUÉ GESTIONAR    ║
                ╠════════════════════════════════╣
                ║  [1] Gestionar Celulares ║
                ║  [2] Gestionar Clientes  ║
                ║  [3] Gestionar Ventas    ║
                ║  [4] Reportes y Análisis ║
                ║  [5] Salir               ║
                ╚════════════════════════════════╝

        """);

    }

    public int opcionGestionarCelular() {

        return v.validarEntero("""
                               ╔════════════════════════════════╗
                               ║   ELIJA QUÉ GESTIONAR    ║
                               ╠════════════════════════════════╣
                               ║  [1] Agregar             ║
                               ║  [2] Listar              ║
                               ║  [3] Actualizar          ║
                               ║  [4] Eliminar            ║
                               ║  [5] Salir               ║
                               ╚════════════════════════════════╝

                               """);

    }
    
    public int opcionGestionarCliente() {

        return v.validarEntero("""
                                 ╔════════════════════════════════╗
                                 ║    GESTIONAR CLIENTE     ║
                                 ╠════════════════════════════════╣
                                 ║  [1] Listar              ║
                                 ║  [2] Eliminar            ║
                                 ║  [3] Salir               ║
                                 ╚════════════════════════════════╝
                               """);

    }
    
    public int opcionGestionarVenta() {

        return v.validarEntero("""
                                     ╔════════════════════════════════╗
                                     ║      GESTIONAR VENTA     ║
                                     ╠════════════════════════════════╣
                                     ║  [1] Actualizar Estado   ║
                                     ║  [2] Lista               ║
                                     ║  [3] Eliminar            ║
                                     ║  [4] Salir               ║
                                     ╚════════════════════════════════╝

                               """);

    }

    public int opcionActualizarCelular() {

    return v.validarEntero("""
            ╔════════════════════════════╗
            ║ ACTUALIZAR CELULAR    ║
            ╠════════════════════════════╣
            ║  [1] Modelo           ║
            ║  [2] Mar              ║
            ║  [3] Stock            ║
            ║  [4] Sistema Operativo║
            ║  [5] Gama             ║
            ║  [6] Precio           ║
            ║  [7] Salir            ║
            ╚════════════════════════════╝
            """);

}
   

}
