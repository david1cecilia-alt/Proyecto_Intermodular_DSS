package vo;

import java.math.BigDecimal;

public class TipoClaseVO {
    private int id;
    private String nombreClase;
    private BigDecimal precioHora;

    // Constructor completo
    public TipoClaseVO(int id, String nombreClase, BigDecimal precioHora) {
        this.id = id;
        this.nombreClase = nombreClase;
        this.precioHora = precioHora;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNombreClase() { return nombreClase; }
    public void setNombreClase(String nombreClase) { this.nombreClase = nombreClase; }


    public BigDecimal getPrecioHora() { return precioHora; }
    public void setPrecioHora(BigDecimal precioHora) { this.precioHora = precioHora; }


    // Método toString para que AppConex lo imprima bonito
    @Override
    public String toString() {
        return "Tipo Clase [ID: " + id + " | Nombre: " + nombreClase + " | Precio/Hora: " + precioHora + "€]";
    }
}
