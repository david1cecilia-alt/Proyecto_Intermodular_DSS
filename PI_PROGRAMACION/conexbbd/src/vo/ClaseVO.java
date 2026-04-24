package vo;




import java.sql.Timestamp;

public class ClaseVO {
    private int idClase;
    private int idAlumno;
    private int id_profesor;
    private int idVehiculo;
    private int idTipoClase;
    private Integer idBono; // Usamos Integer porque puede ser NULL en la BD
    private Timestamp fechaHora;
    private int duracionMinutos;
    private int numAlum;

    // Constructor completo (9 atributos según tu CREATE TABLE)
    public ClaseVO(int idClase, int idAlumno, int id_profesor, int idVehiculo, int idTipoClase, 
                   Integer idBono, Timestamp fechaHora, int duracionMinutos, int numAlum) {
        this.idClase = idClase;
        this.idAlumno = idAlumno;
        this.id_profesor = id_profesor;
        this.idVehiculo = idVehiculo;
        this.idTipoClase = idTipoClase;
        this.idBono = idBono;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.numAlum = numAlum;
    }

    @Override
    public String toString() {
        return "Clase #" + idClase + " | Alumno ID: " + idAlumno + " | Prof ID: " + id_profesor + 
               " | Fecha: " + fechaHora + " | Duración: " + duracionMinutos + " min";
    }

    public int getIdClase() { return idClase; }
    public void setIdClase(int idClase) {this.idClase = idClase;}

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) {this.idAlumno = idAlumno;}

    public int getIdProfesor() { return id_profesor; }
    public void setIdProfesor(int id_profesor) {this.id_profesor = id_profesor;}

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) {this.idVehiculo = idVehiculo;}

    public int getIdTipoClase() { return idTipoClase; }
    public void setIdTipoClase(int idTipoClase) {this.idTipoClase = idTipoClase;}

    public Integer getIdBono() { return idBono; }
    public void setIdBono(Integer idBono) {this.idBono = idBono;}

    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) {this.fechaHora = fechaHora;}

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) {this.duracionMinutos = duracionMinutos;}

    public void setNumAlum(int numAlum) {this.numAlum = numAlum;}    
    public int getNumAlum() { return numAlum; }

    
}


