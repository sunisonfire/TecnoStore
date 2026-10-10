package view;

import view.Validaciones.EnteroRango;

public class MenuAdministrador {
    
    EnteroRango v = new EnteroRango();
    
    public int escogerAccion() {

        return v.validarEnteroRango("""
                ╔════════════════════════════════╗
                ║   ELIJA QUÉ GESTIONAR    ║
                ╠════════════════════════════════╣
                ║  [1] Gestionar Celulares ║
                ║  [2] Gestionar Clientes  ║
                ║  [3] Gestionar Ventas    ║
                ║  [4] Reportes y Análisis ║
                ║  [0] Salir               ║
                ╚════════════════════════════════╝

        """,0,4);

    }

    public int opcionGestionarCelular() {

        return v.validarEnteroRango("""
                               ╔════════════════════════════════╗
                               ║   ELIJA QUÉ GESTIONAR    ║
                               ╠════════════════════════════════╣
                               ║  [1] Agregar             ║
                               ║  [2] Listar              ║
                               ║  [3] Actualizar          ║
                               ║  [4] Eliminar            ║
                               ║  [0] Salir               ║
                               ╚════════════════════════════════╝

                               """,0,4);

    }
    
    public int opcionGestionarCliente() {

        return v.validarEnteroRango("""
                                 ╔════════════════════════════════╗
                                 ║    GESTIONAR CLIENTE     ║
                                 ╠════════════════════════════════╣
                                 ║  [1] Listar              ║
                                 ║  [2] Eliminar            ║
                                 ║  [0] Salir               ║
                                 ╚════════════════════════════════╝
                               """,0,2);

    }
    
    public int opcionGestionarVenta() {

        return v.validarEnteroRango("""
                                     ╔════════════════════════════════╗
                                     ║      GESTIONAR VENTA     ║
                                     ╠════════════════════════════════╣
                                     ║  [1] Actualizar Estado   ║
                                     ║  [2] Lista               ║
                                     ║  [3] Eliminar            ║
                                     ║  [0] Salir               ║
                                     ╚════════════════════════════════╝

                               """,0,3);

    }

    public int opcionActualizarCelular() {

    return v.validarEnteroRango("""
            ╔════════════════════════════╗
            ║ ACTUALIZAR CELULAR    ║
            ╠════════════════════════════╣
            ║  [1] Modelo           ║
            ║  [2] Marca            ║
            ║  [3] Stock            ║
            ║  [4] Sistema Operativo║
            ║  [5] Gama             ║
            ║  [6] Precio           ║
            ║  [0] Salir            ║
            ╚════════════════════════════╝
            """,0,6);

}

    public void reportes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   

}
