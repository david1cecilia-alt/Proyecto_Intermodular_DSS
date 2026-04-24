package vo;

public class ProfesorVO {
    private int id_profesor;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private int id_autoescuela;

    // Constructor completo
    public ProfesorVO(int id_profesor, String nombre, String apellidos, String dni, String telefono,int id_autoescuela) {
        this.id_profesor = id_profesor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.id_autoescuela = id_autoescuela;
    }

    // Getters y Setters
    public int getIdProfesor() { return id_profesor; }
    public void setIdProfesor(int id_profesor) { this.id_profesor = id_profesor; }


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public String getApellidos() { return apellidos;}
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }


    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }


    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getId_autoescuela() {return id_autoescuela;}
    public void setId_autoescuela(int id_autoescuela) { this.id_autoescuela = id_autoescuela;} 
    

    // Método toString para que AppConex lo imprima bonito
    @Override
    public String toString() {
        return "Profesor [ID: " + id_profesor + " | DNI: " + dni + " | Nombre: " + nombre + " " + apellidos + " | Tel: " + telefono + " | ID Autoescuela: " + id_autoescuela + "]" ;
    }
}