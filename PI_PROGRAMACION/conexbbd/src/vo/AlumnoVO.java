package vo;

import java.sql.Date;

public class AlumnoVO {
    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String estado;
    private Date fecha_nacimiento;
    private String telefono;
    private String email;
    private int id_profesor_tutor;
    private int id_autoescuela;
    

    
   // Dentro de tu clase AlumnoVO.java
    public AlumnoVO(int id, String nombre, String apellidos, String dni, 
                    String estado, String telefono, String email, 
                    java.sql.Date fecha_nacimiento, int id_profesor_tutor, int id_autoescuela) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.estado = estado;
        this.telefono = telefono;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.id_profesor_tutor = id_profesor_tutor;
        this.id_autoescuela = id_autoescuela;
}

    
    public int getId() { return id; }
    public void setId(int id) {this.id = id;}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) {this.apellidos = apellidos;}

    public String getDni() { return dni; }
    public void setDni(String dni) {this.dni = dni;}

    public String getEstado() { return estado; }
    public void setEstado(String estado) {this.estado = estado;}
    
    public Date getfecha_nacimiento() { return fecha_nacimiento; }
    public void setfecha_nacimiento(Date fecha_nacimiento) {this.fecha_nacimiento = fecha_nacimiento;}

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getEmail() { return email; }
    public void setEmail(String email) {this.email = email;}

    public int getIdProfesorTutor() { return id_profesor_tutor; }
    public void setIdProfesorTutor(int id_profesor_tutor) {this.id_profesor_tutor = id_profesor_tutor;}

    public int getIdAutoescuela() { return id_autoescuela; }
    public void setIdAutoescuela(int id_autoescuela) {this.id_autoescuela = id_autoescuela;}

    @Override
    public String toString() {
        return "ID: " + id + " | DNI: " + dni + " | Nombre: " + nombre + " " + apellidos + " | Estado: " + estado;
    }
}