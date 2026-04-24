package vo;


public class AutoescuelaVO {
    private int id;
    private String nombre;
    private String cif;
    private String direccion;
    private String telefono;

    public AutoescuelaVO(int id, String nombre, String cif, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.cif = cif;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCif() {return cif;}
    public void setCif(String cif) {this.cif = cif;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    @Override
    public String toString() {
        return "AutoescuelaVO [id=" + id + ", nombre=" + nombre + ", cif=" + cif + ", direccion=" + direccion
                + ", telefono=" + telefono + "]";
    }



    
    
}