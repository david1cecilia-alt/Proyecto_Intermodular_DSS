package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import vo.AlumnoVO;
import vo.BonoVO; 

public class AlumnoDAO {

    // MÉTODO 1
    public List<AlumnoVO> obtenerAlumnos(Connection conexion) throws SQLException {
        String consulta = "SELECT id_alumno, nombre, apellidos, dni, estado, telefono, email, fecha_nacimiento, id_profesor_tutor, id_autoescuela FROM Alumnos";
        List<AlumnoVO> alumnos = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
        
             ResultSet resultado = stmt.executeQuery(consulta)) {
            
            while (resultado.next()) {
                AlumnoVO alum = new AlumnoVO(
                    resultado.getInt("id_alumno"),
                    resultado.getString("nombre"),
                    resultado.getString("apellidos"),
                    resultado.getString("dni"),
                    resultado.getString("estado"),
                    resultado.getString("telefono"),
                    resultado.getString("email"),
                    resultado.getDate("fecha_nacimiento"),
                    resultado.getInt("id_profesor_tutor"),
                    resultado.getInt("id_autoescuela")
                );
                alumnos.add(alum);
            }
        }
        return alumnos;
    }

    // MÉTODO 2
    public List<BonoVO> obtenerMisBonos(Connection con, String dni) throws SQLException {
        List<BonoVO> lista = new ArrayList<>();
        // Consulta con JOIN para buscar los bonos a través del DNI del alumno
        String sql = "SELECT b.* FROM Bonos b JOIN Alumnos a ON b.id_alumno = a.id_alumno WHERE a.dni = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni); // Sustituye el "?" por el DNI del usuario logeado
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new BonoVO(
                        rs.getInt("id_bono"),
                        rs.getInt("id_alumno"),
                        rs.getInt("clases_totales"),
                        rs.getInt("clases_restantes"),
                        rs.getDate("fecha_compra")
                    ));
                }
            }
        }
        return lista;
    }
    
    //Metodo 3
    public int darAltaAlumno(Connection con, AlumnoVO alumno) throws SQLException {
    String sql = "INSERT INTO Alumnos (nombre, apellidos, dni, fecha_nacimiento, telefono, email, estado, id_profesor_tutor, id_autoescuela) VALUES (?,?,?,?,?,?,?,?,?)";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, alumno.getNombre());
        ps.setString(2, alumno.getApellidos());
        ps.setString(3, alumno.getDni());
        ps.setDate(4, new java.sql.Date(alumno.getfecha_nacimiento().getTime()));
        ps.setString(5, alumno.getTelefono());
        ps.setString(6, alumno.getEmail());
        ps.setString(7, alumno.getEstado());
        ps.setInt(8, alumno.getIdProfesorTutor());
        ps.setInt(9, alumno.getIdAutoescuela());

        // Para INSERT se usa executeUpdate. Devuelve el número de filas afectadas.
        return ps.executeUpdate(); 
    }
    }   
    //Metodo 4
    public int eliminarAlumno(Connection con, String dni) throws SQLException {
    String sql = "DELETE FROM Alumnos WHERE dni = ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);

        // executeUpdate devuelve el número de filas borradas
        int filasAfectadas = ps.executeUpdate(); 
        return filasAfectadas;
    }
    }

    // MÉTODO 5
    public List<AlumnoVO> obtenerAlumnosEnPracticas(Connection conexion) throws SQLException {
        String consulta = "SELECT * FROM Alumnos WHERE estado = 'Practicas'";
        List<AlumnoVO> alumnosEnPracticas = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta)) {

            while (resultado.next()) {
                AlumnoVO alum = new AlumnoVO(
                    resultado.getInt("id_alumno"),
                    resultado.getString("nombre"),
                    resultado.getString("apellidos"),
                    resultado.getString("dni"),
                    resultado.getString("estado"),
                    resultado.getString("telefono"),
                    resultado.getString("email"),
                    resultado.getDate("fecha_nacimiento"),
                    resultado.getInt("id_profesor_tutor"),
                    resultado.getInt("id_autoescuela")
                );
                alumnosEnPracticas.add(alum);
            }
        }
        return alumnosEnPracticas;
    }
    public List<String> alumnosPorEstado(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT estado, COUNT(*) as total FROM Alumnos GROUP BY estado";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(rs.getString("estado") + ": " + rs.getInt("total"));
        }
    }
    return lista;
}
public int obtenerIdPorDni(Connection con, String dni) throws SQLException {
    String sql = "SELECT id_alumno FROM Alumnos WHERE dni = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("id_alumno");
        }
    }
    return -1;
}
public int obtenerIdTutor(Connection con, int idAlumno) throws SQLException {
    String sql = "SELECT id_profesor_tutor FROM Alumnos WHERE id_alumno = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idAlumno);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("id_profesor_tutor");
        }
    }
    return 0;
}


}