package vo;

public class ExamenVO {
    private int id;
    private int idAlumno;
    private int idTipoExamen;

    // Constructor completo
    public ExamenVO(int id, int idAlumno, int idTipoExamen) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idTipoExamen = idTipoExamen;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }


    public int getIdTipoExamen() { return idTipoExamen; }
    public void setIdTipoExamen(int idTipoExamen) { this.idTipoExamen = idTipoExamen; }


    // Método toString para que AppConex lo imprima bonito
    @Override
    public String toString() {
        return "Examen [ID: " + id + " | Alumno ID: " + idAlumno + " | Tipo Examen ID: " + idTipoExamen + "]";
    }
}
