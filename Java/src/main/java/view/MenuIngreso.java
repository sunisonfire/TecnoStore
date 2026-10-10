package view;

public class MenuIngreso {


    public int escogerOpcionPersona() {

        return v.validarEntero("""
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                  ¿Cómo deseas ingresar?           
                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶

                   [ 1 ]  Administrador
                   [ 2 ]  Cliente
                   [ 3 ]  Salir

                ︶︶︶︶︶︶︶︶︶︶︶︶︶︶︶
                """);

    }

}
