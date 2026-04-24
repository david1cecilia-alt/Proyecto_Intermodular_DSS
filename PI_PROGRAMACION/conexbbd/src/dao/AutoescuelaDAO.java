package dao;
import java.sql.*;
import java.util.*;
import vo.AutoescuelaVO;

public class AutoescuelaDAO {
    public List<AutoescuelaVO> obtenerAutoescuelas(Connection con) throws SQLException {
        List<AutoescuelaVO> lista = new ArrayList<>();
        String sql = "SELECT id_autoescuela, nombre, cif, direccion, telefono FROM Autoescuela";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new AutoescuelaVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }
        return lista;
    }
}