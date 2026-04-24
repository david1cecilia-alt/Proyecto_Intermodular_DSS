package dao;
import java.sql.*;
import java.util.*;
import vo.TipoClaseVO;

public class TipoClaseDAO {
    public List<TipoClaseVO> obtenerTiposClase(Connection con) throws SQLException {
        List<TipoClaseVO> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_clase, nombre_clase, precio_hora FROM TipoDeClase";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new TipoClaseVO(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3)));
            }
        }
        return lista;
    }
}