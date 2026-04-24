package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import vo.AlumnoVO;
import vo.ClaseVO;

public class ClaseDAO {

    public List<String> obtenerProximasClasesNombres(Connection con, String dniProfesor) throws SQLException {
    List<String> lista = new ArrayList<>();
    
        // El SQL con JOINs para obtener los nombres directamente
        String sql = "SELECT a.nombre AS nombre_alumno, p.nombre AS nombre_profesor, c.fecha_hora, c.duracion_minutos " +
                 "FROM Clases c " +
                 "JOIN Profesores p ON c.id_profesor = p.id_profesor " +
                 "JOIN Alumnos a ON c.id_alumno = a.id_alumno " +
                 "WHERE p.dni = ? " +
                 "ORDER BY c.fecha_hora ASC"; // Ordenadas por fecha

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dniProfesor);
        
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Creamos la frase que queremos mostrar
                    String linea = "Fecha: " + rs.getTimestamp("fecha_hora") +
                                   " | Alumno: " + rs.getString("nombre_alumno") +
                                  " | Profesor: " + rs.getString("nombre_profesor") +
                                 " | Duración: " + rs.getInt("duracion_minutos") + " min";
                
                    lista.add(linea);
                }
            }
        }   
            return lista;
    }


    // Método para insertar una nueva clase
    public int crearClase(Connection con, ClaseVO clase) throws SQLException {
        String sql = "INSERT INTO Clases (id_alumno, id_profesor, id_vehiculo, id_tipo_clase, id_bono, fecha_hora, duracion_minutos, num_alum) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, clase.getIdAlumno());
            ps.setInt(2, clase.getIdProfesor());
            ps.setInt(3, clase.getIdVehiculo());
            ps.setInt(4, clase.getIdTipoClase());
            if (clase.getIdBono() != null) ps.setInt(5, clase.getIdBono()); else ps.setNull(5, Types.INTEGER);
            ps.setTimestamp(6, clase.getFechaHora());
            ps.setInt(7, clase.getDuracionMinutos());
            ps.setInt(8, clase.getNumAlum());
            return ps.executeUpdate();
        }
    }


// Cambiamos ClaseVO por String para poder devolver nombres
public List<String> obtenerMisClasesProfesor(Connection con, String dniProfesor) throws SQLException {
    List<String> lista = new ArrayList<>();
    
    // SQL para sacar los nombres directamente
    String sql = "SELECT c.id_clase, a.nombre, a.apellidos, c.fecha_hora " +
                 "FROM Clases c " +
                 "JOIN Profesores p ON c.id_profesor = p.id_profesor " +
                 "JOIN Alumnos a ON c.id_alumno = a.id_alumno " +
                 "WHERE p.dni = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dniProfesor);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Creamos el texto que queremos que se vea en el forEach
                String info = "Clase ID: " + rs.getInt(1) + 
                              " | Alumno: " + rs.getString(2) + " " + rs.getString(3) + 
                              " | Fecha: " + rs.getTimestamp(4);
                lista.add(info);
            }
        }
    }
    return lista;
}



// Ver solo los alumnos asignados a este profesor
public List<AlumnoVO> obtenerMisAlumnosTutorizados(Connection con, String dniProfesor) throws SQLException {
    List<AlumnoVO> lista = new ArrayList<>();
    String sql = "SELECT a.* FROM Alumnos a " +
                 "JOIN Profesores p ON a.id_profesor_tutor = p.id_profesor " +
                 "WHERE p.dni = ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dniProfesor);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new AlumnoVO(
                    rs.getInt("id_alumno"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("dni"),
                    rs.getString("estado"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getDate("fecha_nacimiento"), 
                    rs.getInt("id_profesor_tutor"),
                    rs.getInt("id_autoescuela")
                ));
            }
        }
    }
    return lista;
}

public List<String> obtenerClaseDetallada(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    
    // Consulta con JOINs para obtener los nombres reales
    String sql = "SELECT c.fecha_hora, a.nombre AS alu_nom, a.apellidos AS alu_ape, " +
                 "p.nombre AS prof_nom, p.apellidos AS prof_ape, v.marca, v.modelo " +
                 "FROM Clases c " +
                 "JOIN Alumnos a ON c.id_alumno = a.id_alumno " +
                 "JOIN Profesores p ON c.id_profesor = p.id_profesor " +
                 "JOIN Vehiculos v ON c.id_vehiculo = v.id_vehiculo " +
                 "ORDER BY c.fecha_hora DESC";

    try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            // Montamos la frase aquí mismo
            String detalle = String.format(
                "Fecha: %s | Alumno: %s %s | Prof: %s %s | Vehículo: %s %s",
                rs.getTimestamp("fecha_hora"),
                rs.getString("alu_nom"), rs.getString("alu_ape"),
                rs.getString("prof_nom"), rs.getString("prof_ape"),
                rs.getString("marca"), rs.getString("modelo")
            );
            lista.add(detalle);
        }
    }
    return lista;
}



public List<ClaseVO> obtenerClasesLargas(Connection con) throws SQLException {
    List<ClaseVO> lista = new ArrayList<>();
    // Seleccionamos todos los campos que requiere tu constructor de 9 parámetros
    String sql = "SELECT id_clase, id_alumno, id_profesor, id_vehiculo, id_tipo_clase, " +
                 "id_bono, fecha_hora, duracion_minutos, num_alum " +
                 "FROM Clases " +
                 "WHERE duracion_minutos > 45";

    try (Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        
        while (rs.next()) {
            // Importante: id_bono puede ser NULL, rs.getInt devolvería 0. 
            // Si quieres mantener el null, usamosgetObject.
            Integer idBonoValue = (Integer) rs.getObject("id_bono");

            lista.add(new ClaseVO(
                rs.getInt("id_clase"),
                rs.getInt("id_alumno"),
                rs.getInt("id_profesor"),
                rs.getInt("id_vehiculo"),
                rs.getInt("id_tipo_clase"),
                idBonoValue,
                rs.getTimestamp("fecha_hora"),
                rs.getInt("duracion_minutos"),
                rs.getInt("num_alum")
            ));
        }
    }
    return lista;
}
public List<String> consultarMisClases(Connection con, String dni) throws SQLException {
    List<String> lista = new ArrayList<>();
    // SQL con JOINs para traer los nombres y filtrar por DNI
    String sql = "SELECT c.fecha_hora, a.nombre, a.apellidos, p.nombre, p.apellidos " +
                 "FROM Clases c " +
                 "JOIN Alumnos a ON c.id_alumno = a.id_alumno " +
                 "JOIN Profesores p ON c.id_profesor = p.id_profesor " +
                 "WHERE a.dni = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Construimos la frase que se imprimirá
                String fila = String.format(
                    "Fecha: %s | Alumno: %s %s | Profesor: %s %s",
                    rs.getTimestamp(1),
                    rs.getString(2), rs.getString(3), // Nombre y Apellido Alumno
                    rs.getString(4), rs.getString(5)  // Nombre y Apellido Profesor
                );
                lista.add(fila);
            }
        }
    }
    return lista;
}

public int insertarClase(Connection con, int idA, int idP, Timestamp fechaHora, int dur) throws SQLException {
    String sql = "INSERT INTO Clases (id_alumno, id_profesor, fecha_hora, duracion_minutos) VALUES (?,?,?,?)";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idA);
        ps.setInt(2, idP);
        ps.setTimestamp(3, fechaHora);
        ps.setInt(4, dur);
        return ps.executeUpdate();
    }
}


}