package vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PagoVO {
    private int idPago;
    private int idAlumno;
    private Timestamp fecha_pago;
    private BigDecimal importe;
    private String metodo;

    public PagoVO(int idPago, int idAlumno, Timestamp fecha_pago, BigDecimal importe, String metodo) {
        this.idPago = idPago;
        this.idAlumno = idAlumno;
        this.fecha_pago = fecha_pago;
        this.importe = importe;
        this.metodo = metodo;
    }

    // Getters
    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) {this.idPago = idPago;}


    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) {this.idAlumno = idAlumno;}


    public Timestamp getfecha_pago() { return fecha_pago; }
    public void setfecha_pago(Timestamp fecha_pago) {this.fecha_pago = fecha_pago;}


    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) {this.importe = importe;}


    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) {this.metodo = metodo;}


    @Override
    public String toString() {
        return "Pago [ID: " + idPago + " | Alumno ID: " + idAlumno + 
               " | Fecha: " + fecha_pago + " | Importe: " + importe + "€ | Método: " + metodo + "]";
    }
}