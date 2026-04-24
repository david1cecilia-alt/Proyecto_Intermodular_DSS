package dao;
import java.sql.*;
import java.util.*;
import vo.*;


public class ExamenDAO {

    public List<String> obtenerExamenes(Connection con, String dniAlumno) throws SQLException {
    List<String> lista = new ArrayList<>();
    // Cambiamos t.id por t.id_tipo_examen
    String sql = "SELECT a.nombre, t.descripcion " +
                 "FROM Examen e " +
                 "JOIN Alumnos a ON e.id_alumno = a.id_alumno " +
                 "JOIN TipoExamen t ON e.id_tipo_examen = t.id_tipo_examen " + 
                 "WHERE a.dni = ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dniAlumno);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String fila = "Alumno: " + rs.getString(1) + " | Tipo: " + rs.getString(2);
                lista.add(fila);
            }
        }
    }
    return lista;
}



public int solicitarExamen(Connection con, int idAlumno, int idTipoExamen, java.sql.Date fecha) throws SQLException {
    // El campo 'resultado' lo ponemos como 'Pendiente' por defecto
    // El campo 'intento' se podría calcular, pero por ahora lo ponemos a 1
    String sql = "INSERT INTO Examen (id_alumno, id_tipo_examen, fecha_examen, intento, resultado) VALUES (?, ?, ?, 1, 'Pendiente')";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idAlumno);
        ps.setInt(2, idTipoExamen);
        ps.setDate(3, fecha);
        
        return ps.executeUpdate();
    }
}


public List<String> verRutinaLibreTutor(Connection con, int idAlumno) throws SQLException {
    List<String> rutinaLibre = new ArrayList<>();

    // 1. Buscamos el ID del tutor del alumno
    String sqlTutor = "SELECT id_profesor_tutor FROM Alumnos WHERE id_alumno = ?";
    int idTutor = -1;
    try (PreparedStatement ps = con.prepareStatement(sqlTutor)) {
        ps.setInt(1, idAlumno);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) idTutor = rs.getInt("id_profesor_tutor");
    }

    if (idTutor == -1) return rutinaLibre;

    // 2. Buscamos su rutina fija (1-7) y comprobamos si tiene clases en esos días/horas
    // Usamos un LEFT JOIN con Clases para ver qué horarios están sin ocupar
    String sql = "SELECT d.dia_semana, d.hora_inicio, d.hora_fin " +
                 "FROM Disponibilidad d " +
                 "WHERE d.id_profesor = ? " +
                 "AND NOT EXISTS (" +
                 "    SELECT 1 FROM Clases c " +
                 "    WHERE c.id_profesor = d.id_profesor " +
                 "    AND DAYOFWEEK(c.fecha_hora)-1 = d.dia_semana " +
                 "    AND TIME(c.fecha_hora) = d.hora_inicio" +
                 ") " +
                 "ORDER BY d.dia_semana, d.hora_inicio";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idTutor);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String dia = traducirDia(rs.getInt("dia_semana"));
                rutinaLibre.add(String.format("%s: %s - %s (LIBRE)", 
                    dia, rs.getTime("hora_inicio"), rs.getTime("hora_fin")));
            }
        }
    }
    return rutinaLibre;
}

private String traducirDia(int d) {
    String[] dias = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    return (d > 0 && d < 8) ? dias[d] : "Día " + d;
}

// 6. Exámenes con tipo y resultado
public List<String> obtenerExamenesDetallados(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT a.nombre, a.apellidos, te.descripcion, e.fecha_examen, e.resultado " +
                 "FROM Examen e " +
                 "JOIN Alumnos a ON e.id_alumno = a.id_alumno " +
                 "JOIN TipoExamen te ON e.id_tipo_examen = te.id_tipo_examen";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(String.format("%s %s | Tipo: %s | Fecha: %s | Resultado: %s",
                rs.getString("nombre"), rs.getString("apellidos"), rs.getString("descripcion"), 
                rs.getDate("fecha_examen"), rs.getString("resultado")));
        }
    }
    return lista;
}

// 9. Alumnos que han suspendido
public List<String> obtenerAlumnosSuspensos(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    String sql = "SELECT DISTINCT a.nombre, a.apellidos FROM Alumnos a " +
                 "JOIN Examen e ON a.id_alumno = e.id_alumno WHERE e.resultado = 'No Apto'";
    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            lista.add(rs.getString("nombre") + " " + rs.getString("apellidos"));
        }
    }
    return lista;
}


}