package dao.implement;

import factory.CelularFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Celular;
import model.Marca;

public class VentaMarcaDao {
    private final Conexion conexion = Conexion.getInstancia();
    private final MarcaDao marcaDao = new MarcaDao();   // debe tener obtenerPorId(int)

    private static final String BASE_CONSULTA_MARCA = """
            select c.nombre as cliente, v.fecha_hora, dv.precio_unitario, m.nombre as marca from detalle_venta dv
            left join venta v
            ON dv.id_venta=v.id_venta
            left join persona c
            on v.id_cliente=c.id_persona
            left join celular cc
            on dv.id_celular=cc.id_celular
            left join marca m
            on cc.id_marca=m.id_marca
            """;
    // Convierte una fila del ResultSet en un Celular (se reutiliza en todos los SELECT)
    private Celular mapear(ResultSet rs) throws SQLException {
    VentaMarca ventaMarca = VentaMarca.obtenerPorId(rs.getInt("id_marca"));

    return CelularFactory.desdeBD(
            rs.getInt("id_celular"),
            rs.getInt("stock"),
            rs.getString("modelo"),
            rs.getDouble("precio"),
            marca,
            Celular.SistemaOperativo.valueOf(rs.getString("sistema_operativo")),
            Celular.Gama.valueOf(rs.getString("gama")));
}
}
