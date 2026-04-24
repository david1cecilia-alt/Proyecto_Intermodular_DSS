package dao;
import java.sql.*;
import java.util.*;
import vo.VehiculoVO;

public class VehiculoDAO {
    public List<VehiculoVO> obtenerVehiculos(Connection con) throws SQLException {
        List<VehiculoVO> lista = new ArrayList<>();
        String sql = "SELECT id_vehiculo, matricula, modelo, marca FROM Vehiculos";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new VehiculoVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        }
        return lista;
    }

    public List<String> listarVehiculosConProfesor(Connection con) throws SQLException {
    List<String> lista = new ArrayList<>();
    // Usamos exactamente la consulta de tu imagen
    String sql = "SELECT v.matricula, v.modelo, p.nombre " +
                 "FROM Vehiculos v " +
                 "LEFT JOIN Profesores p ON v.id_profesor_asignado = p.id_profesor";

    try (Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        
        while (rs.next()) {
            // Obtenemos el nombre del profesor, manejando el caso de que sea null 
            String nombreProf = rs.getString("nombre");
            if (nombreProf == null) {
                nombreProf = "Sin asignar";
            }
            
            // Formato de salida similar a tus otros métodos de listado
            lista.add(rs.getString("matricula") + " (" + rs.getString("modelo") + ") - Prof: " + nombreProf);
        }
    }
    return lista;
    }



}