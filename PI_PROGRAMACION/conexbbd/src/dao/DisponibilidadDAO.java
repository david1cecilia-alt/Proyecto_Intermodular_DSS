package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import vo.DisponibilidadVO;

public class DisponibilidadDAO {

    /*
      Registra el horario fijo (rutina) de un profesor.
     */
    public int registrarRutina(Connection con, DisponibilidadVO d) throws SQLException {
        String sql = "INSERT INTO Disponibilidad (id_profesor, dia_semana, hora_inicio, hora_fin) VALUES (?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdProfesor());
            ps.setInt(2, d.getDiaSemana());
            ps.setTime(3, d.getHoraInicio());
            ps.setTime(4, d.getHoraFin());
            return ps.executeUpdate();
        }
    }

    /*
      Calcula la disponibilidad real para una fecha específica restando las clases ocupadas.
     */
    public List<String> consultarDisponibilidadReal(Connection con, Date fecha) throws SQLException {
        List<String> resultados = new ArrayList<>();

        // TRADUCCIÓN DE FECHA A DÍA DE LA SEMANA (1=Lunes, ..., 7=Domingo)
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int diaJava = cal.get(Calendar.DAY_OF_WEEK); 
        // Ajuste: En Calendar 1 es Domingo. Lo pasamos a Lunes=1, ..., Domingo=7
        int diaBuscadoSQL = (diaJava == Calendar.SUNDAY) ? 7 : diaJava - 1;

        String sql = "SELECT p.nombre, p.apellidos, d.hora_inicio, d.hora_fin, p.id_profesor " +
                     "FROM Disponibilidad d " +
                     "JOIN Profesores p ON d.id_profesor = p.id_profesor " +
                     "WHERE d.dia_semana = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, diaBuscadoSQL);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellidos");
                    Time inicio = rs.getTime("hora_inicio");
                    Time fin = rs.getTime("hora_fin");
                    int idProf = rs.getInt("id_profesor");

                    // Buscar clases ocupadas para este profesor en ESTA fecha exacta
                    String sqlClases = "SELECT fecha_hora FROM Clases WHERE id_profesor = ? AND DATE(fecha_hora) = ?";
                    
                    try (PreparedStatement ps2 = con.prepareStatement(sqlClases)) {
                        ps2.setInt(1, idProf);
                        ps2.setDate(2, fecha);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            StringBuilder ocupacion = new StringBuilder();
                            while (rs2.next()) {
                                // Sacamos solo la hora de la fecha_hora completa
                                Time horaClase = new Time(rs2.getTimestamp("fecha_hora").getTime());
                                ocupacion.append(" [Clase: ").append(horaClase).append("]");
                            }

                            String estado = ocupacion.length() == 0 ? " -> TODO LIBRE" : " ->" + ocupacion.toString();
                            resultados.add("Prof: " + nombreCompleto + " (" + inicio + " - " + fin + ")" + estado);
                        }
                    }
                }
            }
        }
        return resultados;
    }

    /*
      Método para listar toda la tabla de disponibilidad .
      Útil para la opción 3 del menú si no se quiere filtrar por fecha.
     */
    public List<DisponibilidadVO> obtenerTodaLaDisponibilidad(Connection con) throws SQLException {
        List<DisponibilidadVO> lista = new ArrayList<>();
        String sql = "SELECT * FROM Disponibilidad ORDER BY dia_semana, hora_inicio";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new DisponibilidadVO(
                    rs.getInt("id_profesor"),
                    rs.getInt("dia_semana"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fin")
                ));
            }
        }
        return lista;
    }
    public boolean existeDisponibilidad(Connection con, int id_profesor, int dia) throws SQLException {
    String sql = "SELECT COUNT(*) FROM Disponibilidad WHERE id_profesor = ? AND dia_semana = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_profesor);
        ps.setInt(2, dia);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1) > 0;
        }
    }
    return false;
}

public int eliminarDisponibilidad(Connection con, int id_profesor, int diaSemana) throws SQLException {
    String sql = "DELETE FROM Disponibilidad WHERE id_profesor = ? AND dia_semana = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id_profesor);
        ps.setInt(2, diaSemana);
        return ps.executeUpdate();
    }
}
public List<String> consultarDisponibilidadFiltrada(Connection con, Date fecha, int duracionClase) throws SQLException {
    List<String> resultados = new ArrayList<>();
    
    // 1. Obtener el día de la semana (Lunes=1...)
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    int diaJava = cal.get(Calendar.DAY_OF_WEEK);
    int diaBuscadoSQL = (diaJava == Calendar.SUNDAY) ? 7 : diaJava - 1;

    // 2. Consulta de Profesores que trabajan ese día
    String sql = "SELECT p.nombre, p.id_profesor, d.hora_inicio, d.hora_fin " +
                 "FROM Disponibilidad d JOIN Profesores p ON d.id_profesor = p.id_profesor " +
                 "WHERE d.dia_semana = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, diaBuscadoSQL);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idProf = rs.getInt("id_profesor");
                String nombre = rs.getString("nombre");
                Time hInicioProg = rs.getTime("hora_inicio");
                Time hFinProg = rs.getTime("hora_fin");

                // 3. Consultar clases ocupadas incluyendo la DURACIÓN (asumiendo que está en la tabla Clases)
                // Si la duración no está en la DB, cámbialo por un valor fijo
                String sqlClases = "SELECT fecha_hora, duracion_minutos FROM Clases WHERE id_profesor = ? AND DATE(fecha_hora) = ?";
                
                try (PreparedStatement ps2 = con.prepareStatement(sqlClases)) {
                    ps2.setInt(1, idProf);
                    ps2.setDate(2, fecha);
                    
                    ResultSet rs2 = ps2.executeQuery();
                    StringBuilder ocupacion = new StringBuilder();
                    
                    while (rs2.next()) {
                        Time horaClase = new Time(rs2.getTimestamp("fecha_hora").getTime());
                        int duracion_minutos = rs2.getInt("duracion_minutos"); // Duración en minutos
                        ocupacion.append(String.format(" [Ocupado: %s (%d min)]", horaClase, duracion_minutos));
                    }

                    resultados.add("Prof: " + nombre + " | Horario: " + hInicioProg + "-" + hFinProg + " | " + 
                                  (ocupacion.length() == 0 ? "LIBRE TOTAL" : ocupacion.toString()));
                }
            }
        }
    }
    return resultados;
}
public boolean esHuecoValido(Connection con, int idProfesor, Date fecha, Time horaInicio, int duracion_minutos) throws SQLException {
    // 1. ¿El profesor trabaja ese día a esa hora?
    Calendar cal = Calendar.getInstance();
    cal.setTime(fecha);
    int diaSQL = (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1;

    String sqlHorario = "SELECT COUNT(*) FROM Disponibilidad WHERE id_profesor = ? AND dia_semana = ? " +
                        "AND ? >= hora_inicio AND ADDTIME(?, SEC_TO_TIME(?*60)) <= hora_fin";
    
    try (PreparedStatement ps = con.prepareStatement(sqlHorario)) {
        ps.setInt(1, idProfesor);
        ps.setInt(2, diaSQL);
        ps.setTime(3, horaInicio);
        ps.setTime(4, horaInicio);
        ps.setInt(5, duracion_minutos);
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) == 0) return false; // No trabaja o no cabe
    }

    // 2. ¿Choca con otra clase?
    String sqlChoque = "SELECT COUNT(*) FROM Clases WHERE id_profesor = ? AND DATE(fecha_hora) = ? " +
                       "AND (TIME(fecha_hora) < ADDTIME(?, SEC_TO_TIME(?*60)) " +
                       "AND ADDTIME(TIME(fecha_hora), SEC_TO_TIME(duracion_minutos*60)) > ?)";
    
    try (PreparedStatement ps = con.prepareStatement(sqlChoque)) {
        ps.setInt(1, idProfesor);
        ps.setDate(2, fecha);
        ps.setTime(3, horaInicio);
        ps.setInt(4, duracion_minutos);
        ps.setTime(5, horaInicio);
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) return false; // Hay choque
    }
    return true;
}


}