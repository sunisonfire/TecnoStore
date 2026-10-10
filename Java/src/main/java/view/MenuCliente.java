package view;

import view.Validaciones.EnteroRango;

public class MenuCliente {

    EnteroRango v = new EnteroRango();

    public int escogerTipoLogin() {
        return v.validarEnteroRango("""
                ╭───────────────────────────╮
                      ✦ Bienvenido ✦
                ╰───────────────────────────╯

                   ⟮1⟯  Registrarse
                   ⟮2⟯  Iniciar Sesión
                   ⟮0⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """, 0, 2);
    }

    public int opcionAccionCliente() {
        return v.validarEnteroRango("""
                ╭──────────────────────────────╮
                       ✦ Mi Cuenta ✦
                ╰──────────────────────────────╯

                   ⟮1⟯  Carrito
                   ⟮2⟯  Ver mi perfil
                   ⟮3⟯  Actualizar mi perfil
                   ⟮4⟯  Eliminar cuenta
                   ⟮0⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """, 0, 4);
    }

    public int opcionActualizarDatos() {
        return v.validarEnteroRango("""
                ╭───────────────────────────╮
                  ✦ Actualizar Datos ✦
                ╰───────────────────────────╯

                   ⟮1⟯  Nombre
                   ⟮2⟯  Apellido
                   ⟮3⟯  Email
                   ⟮4⟯  Identificación
                   ⟮5⟯  Teléfono
                   ⟮0⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """, 0, 5);
    }

    // gestionPedido() y opcionActualizarPedido() igual:
    // mismo texto, pero con "⟮0⟯ Salir" y rango 0-4 y 0-3 respectivamente

    public int gestionPedido() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}