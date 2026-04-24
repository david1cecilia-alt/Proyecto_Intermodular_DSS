package dao;
import java.sql.*;
import java.util.*;
import vo.BonoVO;

public class BonoDAO {
    public List<BonoVO> obtenerBonos(Connection con) throws SQLException {
        List<BonoVO> lista = new ArrayList<>();
        String sql = "SELECT id_bono, id_alumno, clases_totales, clases_restantes, fecha_compra FROM Bonos";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new BonoVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDate(5)));
            }
        }
        return lista;
    }
    
public int añadirBono(Connection con, BonoVO bono) throws SQLException {
    String sql = "INSERT INTO Bonos (id_alumno, clases_totales, clases_restantes, fecha_compra) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, bono.getIdAlumno());
        ps.setInt(2, bono.getClasesTotales());
        ps.setInt(3, bono.getClasesRestantes());
        ps.setDate(4, bono.getFechaCompra());
        
        return ps.executeUpdate();
    }
}




}