package view;

public class MenuCliente {

    Validaciones v = new Validaciones();

    public int escogerTipoLogin() {

        return v.validarEntero("""
                ╭───────────────────────────╮
                      ✦ Bienvenido ✦
                ╰───────────────────────────╯

                   ⟮1⟯  Registrarse
                   ⟮2⟯  Iniciar Sesión
                   ⟮5⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """);

    }

    public int opcionAccionCliente() {

        return v.validarEntero("""
                ╭──────────────────────────────╮
                       ✦ Mi Cuenta ✦
                ╰──────────────────────────────╯

                   ⟮1⟯  Carrito
                   ⟮2⟯  Ver mi perfil
                   ⟮3⟯  Actualizar mi perfil
                   ⟮4⟯  Eliminar cuenta
                   ⟮5⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """);

    }

    public int gestionPedido() {

        return v.validarEntero("""
                ╭─────────────────────────────────╮
                    ✦ Gestionar Pedidos ✦
                ╰─────────────────────────────────╯

                   ⟮1⟯  Crear Pedido
                   ⟮2⟯  Actualizar mi pedido
                   ⟮3⟯  Ver mis pedidos
                   ⟮4⟯  Eliminar Pedido
                   ⟮5⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """);

    }

    public int opcionActualizarPedido() {
        // Solo cuando sigue en pendiente
        return v.validarEntero("""
                ╭───────────────────────────╮
                 ✦ Actualizar Pedido ✦
                ╰───────────────────────────╯

                   ⟮1⟯  Agregar celular
                   ⟮2⟯  Eliminar celular
                   ⟮3⟯  Cancelar pedido
                   ⟮4⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """);

    }

    public int opcionActualizarDatos() {

        return v.validarEntero("""
                ╭───────────────────────────╮
                  ✦ Actualizar Datos ✦
                ╰───────────────────────────╯

                   ⟮1⟯  Nombre
                   ⟮2⟯  Apellido
                   ⟮3⟯  Email
                   ⟮4⟯  Identificación
                   ⟮5⟯  Teléfono
                   ⟮6⟯  Salir

                ┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈
                """);

    }
}