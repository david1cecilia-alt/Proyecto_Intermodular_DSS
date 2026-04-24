package vo;


import java.sql.Time;

public class DisponibilidadVO {
    private int id_profesor;
    private int diaSemana; 
    private Time horaInicio;
    private Time horaFin;

    public DisponibilidadVO(int id_profesor, int diaSemana, Time horaInicio, Time horaFin) {
        this.id_profesor = id_profesor;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y Setters
    public int getIdProfesor() { return id_profesor; }
    public void setIdProfesor(int id_profesor) {this.id_profesor = id_profesor;}

    public int getDiaSemana() { return diaSemana; }
    public void setDiaSemana(int diaSemana) {this.diaSemana = diaSemana;}

    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) {this.horaInicio = horaInicio;}

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) {this.horaFin = horaFin;}

    @Override
    public String toString() {
        return "DisponibilidadVO [id_profesor=" + id_profesor + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio
                + ", horaFin=" + horaFin + "]";
    }
    
}

