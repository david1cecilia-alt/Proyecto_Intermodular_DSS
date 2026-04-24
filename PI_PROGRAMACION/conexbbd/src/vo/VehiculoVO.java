// VehiculoVO.java
package vo;

public class VehiculoVO{ 
    private int id;
    private String matricula;
    private String modelo;
    private String marca;

    public VehiculoVO(int id, String matricula, String modelo, String marca){
        this.id = id;
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;

    }


    // Getters y Setters

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    
    public String getMatricula() { return matricula;}
    public void setMatricula(String matricula) {this.matricula = matricula;}

    public String getModelo() { return modelo;}
    public void setModelo(String modelo) {this.modelo = modelo;}

    public String getMarca() { return marca;}
    public void setMarca(String marca) {this.marca = marca;}


    
    @Override
    public String toString() {
        return "ID: " + id + " | Matricula: " + matricula + " | Modelo: " + " | Marca: " + marca;
    }
}