package dao;
import java.sql.*;
import java.util.*;
import vo.ProfesorVO;

public class ProfesorDAO {
    public List<ProfesorVO> obtenerProfesores(Connection con) throws SQLException {
        List<ProfesorVO> lista = new ArrayList<>();
        String sql = "SELECT id_profesor, nombre, apellidos, dni, telefono FROM Profesores";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ProfesorVO(
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4), 
                    rs.getString(5), 
                    rs.getInt(6)
                ));
            }
        }
        return lista;
    }

    public int crearProfesor(Connection con, ProfesorVO profesor) throws SQLException {
    String sql = "INSERT INTO Profesores (id_Profesor, nombre, apellidos, dni, telefono, id_autoescuela) VALUES (?,?,?,?,?,?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, profesor.getIdProfesor());
        ps.setString(2, profesor.getNombre());
        ps.setString(3, profesor.getApellidos());
        ps.setString(4, profesor.getDni());
        ps.setString(5, profesor.getTelefono());
        ps.setInt(6, profesor.getId_autoescuela());
        return ps.executeUpdate();
    }
}
    public int eliminarProfesor(Connection con, String dni) throws SQLException {
    String sql = "DELETE FROM Profesores WHERE dni = ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        
        // Retorna 1 si se eliminó, 0 si no se encontró el DNI
        return ps.executeUpdate();
    }
}

public List<String> clasesPorProfesor(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT p.nombre, COUNT(c.id_clase) AS total FROM Profesores p " +
                 "LEFT JOIN Clases c ON p.id_profesor = c.id_profesor GROUP BY p.nombre";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(rs.getString("nombre") + ": " + rs.getInt("total") + " clases");
        }
    }
    return lista;
}

public List<String> profesoresDisponibles(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT DISTINCT p.nombre " +
                 "FROM Profesores p " +
                 "JOIN Disponibilidad d ON p.id_profesor = d.id_profesor";
    
    try (Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) { 
            lista.add(rs.getString("nombre")); 
        }
    }
    return lista;
}

public ProfesorVO obtenerProfesorMasClases(Connection con) throws SQLException {
    // Seleccionamos todos los campos del profesor y contamos sus clases
    String sql = "SELECT p.id_profesor, p.nombre, p.apellidos, p.dni, p.telefono, " +
                 "p.id_autoescuela, " + 
                 "COUNT(c.id_clase) AS total " +
                 "FROM Profesores p " +
                 "JOIN Clases c ON p.id_profesor = c.id_profesor " +
                 "GROUP BY p.id_profesor, p.nombre, p.apellidos, p.dni, p.telefono, " +
                 "p.id_autoescuela " +   
                 "ORDER BY total DESC " +
                 "LIMIT 1";

    try (Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        
        if (rs.next()) {
            // Creamos el objeto usando tu constructor: id, nombre, apellidos, dni, telefono
            return new ProfesorVO(
                rs.getInt("id_profesor"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getInt("id_autoescuela")
            );
        }
    }
    return null; // Si no hay clases o profesores, devuelve null
}

public int obtenerIdPorDni(Connection con, String dni) throws SQLException {
    String sql = "SELECT id_profesor FROM Profesores WHERE dni = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("id_profesor");
        }
    }
    return -1; // No encontrado
}

}

