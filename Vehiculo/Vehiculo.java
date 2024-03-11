package Vehiculo;
import java.io.Serializable;

public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String marca;
    private String modelo;
    
    public Vehiculo(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

	@Override
	public String toString() {
		return "Nuevo vehículo añadido a la BD: "
				+ "\n\tMatrícula: " + matricula
				+ "\n\tMarca: " + marca
				+ "\n\tModelo: " + modelo;
	}  
}
