package vo;
import java.sql.Date;

public class BonoVO {
    private int id;
    private int idAlumno;
    private int clasesTotales;
    private int clasesRestantes;
    private Date fechaCompra;

    public BonoVO(int id, int idAlumno, int clasesTotales, int clasesRestantes, Date fechaCompra) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.clasesTotales = clasesTotales;
        this.clasesRestantes = clasesRestantes;
        this.fechaCompra = fechaCompra;
    }


    //Getters Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getIdAlumno() {return idAlumno;}
    public void setIdAlumno(int idAlumno) {this.idAlumno = idAlumno;}

    public int getClasesTotales() {return clasesTotales;}
    public void setClasesTotales(int clasesTotales) {this.clasesTotales = clasesTotales;}
    
    public int getClasesRestantes() {return clasesRestantes;}
    public void setClasesRestantes(int clasesRestantes) {this.clasesRestantes = clasesRestantes;}

    public Date getFechaCompra() {return fechaCompra;}
    public void setFechaCompra(Date fechaCompra) {this.fechaCompra = fechaCompra;}

    

    @Override
    public String toString() {
        return "Bono ID: " + id + " | Restantes: " + clasesRestantes + "/" + clasesTotales;
    }

}