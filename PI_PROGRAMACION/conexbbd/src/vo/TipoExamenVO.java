package vo;

public class TipoExamenVO {
    private int id;
    private String descripcion;

    // Constructor completo
    public TipoExamenVO(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }


    // Método toString para que AppConex lo imprima bonito
    @Override
    public String toString() {
        return "Tipo Examen [ID: " + id + " | Descripción: " + descripcion + "]";
    }
}
