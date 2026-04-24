package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion() throws Exception {
        try {
            // Se cambia la base de datos a BDAUTOESCUELA
            String url = "jdbc:mysql://localhost:3306/BDAUTOESCUELA";
            String user = "root"; 
            String pwd = "mysql"; // Asegúrate de que esta sea tu contraseña
            return DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            throw (e);
        }
    }
}