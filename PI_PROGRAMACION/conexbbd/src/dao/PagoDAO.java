package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import vo.PagoVO;

public class PagoDAO {
    public List<PagoVO> obtenerPagos(Connection conexion) throws SQLException {
        String consulta = "SELECT id_pago, id_alumno, fecha_pago, importe, metodo FROM Pagos";
        List<PagoVO> pagos = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            
            while (rs.next()) {
                PagoVO pago = new PagoVO(
                    rs.getInt("id_pago"),
                    rs.getInt("id_alumno"),
                    rs.getTimestamp("fecha_pago"),
                    rs.getBigDecimal("importe"),
                    rs.getString("metodo")
                );
                pagos.add(pago);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return pagos;
    }

    public List<PagoVO> obtenerPagosTarjeta(Connection con) throws SQLException {
    List<PagoVO> lista = new ArrayList<>();
    // Ajusta 'metodo' al nombre real de tu columna
    String sql = "SELECT * FROM Pagos WHERE metodo = 'Tarjeta'"; 
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(new PagoVO(rs.getInt("id_pago"), 
                                 rs.getInt("id_alumno"),
                                 rs.getTimestamp("fecha_pago"),
                                 rs.getBigDecimal("importe"), 
                                 rs.getString("metodo")));
        }
    }
    return lista;
}
public double obtenerIngresosTotales(Connection conexion) throws SQLException {
    String consulta = "SELECT SUM(importe) FROM Pagos";
    try (Statement stmt = conexion.createStatement();
         ResultSet rs = stmt.executeQuery(consulta)) {
        
        if (rs.next()) {
            return rs.getDouble(1); // Devuelve el resultado del SUM
        }
    }
    return 0.0;
}
 public List<PagoVO> consultarMisPagos(Connection con, int idAlumno) throws SQLException {
        List<PagoVO> lista = new ArrayList<>();
        String sql = "SELECT id_pago, id_alumno, fecha_pago, importe, metodo FROM Pagos WHERE id_alumno = ? ORDER BY fecha_pago DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new PagoVO(
                        rs.getInt("id_pago"),
                        rs.getInt("id_alumno"),
                        rs.getTimestamp("fecha_pago"),
                        rs.getBigDecimal("importe"),
                        rs.getString("metodo")
                    ));
                }
            }
        }
        return lista;
    }
// 8. Total pagado por cada alumno
public List<String> totalPagadoPorAlumno(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT a.nombre, SUM(p.importe) as total FROM Alumnos a " +
                 "JOIN Pagos p ON a.id_alumno = p.id_alumno GROUP BY a.id_alumno";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(rs.getString("nombre") + ": " + rs.getDouble("total") + "€");
        }
    }
    return lista;
}

// 10. Alumno que más ha pagado
public String alumnoMasPagador(Connection con) throws SQLException {
    String sql = "SELECT a.nombre, SUM(p.importe) as total FROM Alumnos a " +
                 "JOIN Pagos p ON a.id_alumno = p.id_alumno " +
                 "GROUP BY a.id_alumno ORDER BY total DESC LIMIT 1";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) return "El alumno que más ha pagado es " + rs.getString("nombre") + " (" + rs.getDouble("total") + "€)";
    }
    return "No hay pagos registrados.";
}
public int comprarClases(Connection con, int idAlumno, BigDecimal importe, String metodo) throws SQLException {
    // 1. Insertamos el registro del pago
    String sql = "INSERT INTO Pagos (id_alumno, fecha_pago, importe, metodo) VALUES (?, CURRENT_TIMESTAMP, ?, ?)";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idAlumno);
        ps.setBigDecimal(2, importe);
        ps.setString(3, metodo); // Ejemplo: "Tarjeta", "Efectivo", "Transferencia"
        
        int resultado = ps.executeUpdate();
        
        if (resultado > 0) {
            System.out.println("Compra realizada con éxito.");
        }
        
        return resultado;
    }
}
public int comprarClases(Connection con, int idAlumno, double importe, String concepto) throws SQLException {
    // Usamos la tabla Pagos de tu SQL
    String sql = "INSERT INTO Pagos (id_alumno, fecha_pago, importe, metodo) VALUES (?, NOW(), ?, 'Tarjeta')";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idAlumno);
        ps.setDouble(2, importe);
        // El concepto podrías guardarlo si añades la columna, si no, con el importe vale
        return ps.executeUpdate();
    }
}

}