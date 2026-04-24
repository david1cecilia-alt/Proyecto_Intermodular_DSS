package dao;
import java.sql.*;


import vo.UsuarioVO;

public class UsuarioDAO {
    public UsuarioVO validarLogin(Connection con, String user, String pass) throws SQLException {
    // Esta consulta busca el nombre en AlumnoVO o Profesores dependiendo de quién coincida con el DNI   
    String sql = "SELECT u.username, u.rol, u.clave,  " +
                 "COALESCE(a.nombre, p.nombre) as nombre_real " +
                 "FROM Usuarios u " +
                 "LEFT JOIN Alumnos a ON u.username = a.dni " +
                 "LEFT JOIN Profesores p ON u.username = p.dni " +
                 "WHERE u.username = ? AND u.clave = ?";
                 
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, user);
        ps.setString(2, pass);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Necesitarás añadir el atributo 'nombreReal' a tu UsuarioVO y su constructor
                return new UsuarioVO(rs.getString("username"), rs.getString("rol"), rs.getString("nombre_real"),rs.getString("clave"));
            }
        }
    }
    return null;
    
}
public int cambiarContrasena(Connection con, String username, String nuevaclave) throws SQLException {
    // La consulta busca por el 'username' que tienes en tu UsuarioVO
    String sql = "UPDATE Usuarios SET clave = ? WHERE username = ?";
    
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nuevaclave);
        ps.setString(2, username);
        
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            System.out.println("La contraseña de " + username + " ha sido actualizada.");
        }
        
        return filasAfectadas;
    }
}



}