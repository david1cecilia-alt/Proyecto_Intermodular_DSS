package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import vo.TipoExamenVO;

public class TipoExamenDAO {
    public List<TipoExamenVO> obtenerTiposExamen(Connection conexion) throws SQLException {
        String consulta = "SELECT id_tipo_examen, descripcion FROM TipoExamen";
        List<TipoExamenVO> tipos = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            
            while (rs.next()) {
                TipoExamenVO tipo = new TipoExamenVO(
                    rs.getInt("id_tipo_examen"),
                    rs.getString("descripcion")
                );
                tipos.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return tipos;
    }
}