package vo;

public class UsuarioVO {
    private String username;
    private String rol;
    private String clave;
    private String nombreReal; // Nuevo atributo

    // Constructor actualizado para recibir 3 parámetros
    public UsuarioVO(String username, String rol, String nombreReal,String clave) {
        this.username = username;
        this.rol = rol;
        this.nombreReal = nombreReal;
        this.clave = clave;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) {this.username = username;}

    public String getRol() { return rol; }
    public void setRol(String rol) {this.rol = rol;}

    public String getNombreReal() { return nombreReal; }
    public void setNombreReal(String nombreReal) {this.nombreReal = nombreReal;} 

    public String getclave() {return clave;}
    public void setclave(String clave) {this.clave = clave;}
}