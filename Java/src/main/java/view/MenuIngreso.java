package view;

import view.Validaciones.EnteroRango;

public class MenuIngreso {

    EnteroRango v = new EnteroRango();
    public int escogerOpcionPersona() {

        return v.validarEnteroRango("""
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                  ¿Cómo deseas ingresar?           
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶

                   [ 1 ]  Administrador
                   [ 2 ]  Cliente
                   [ 0 ]  Salir

                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                """,0,3);

    }

}
