package Vehiculo;
import java.io.Serializable;
import java.util.List;

import Clientes.Cliente;

public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String marca;
    private String modelo;
    
    public Vehiculo() {}
    
    public Vehiculo(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public static boolean vehiculoExiste(String matricula ,List<Vehiculo> lv ) {
		if(lv != null) {
			for(Vehiculo v : lv ) {
				if(matricula.equals(v.matricula)) return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return matricula
				+ ", Marca: " + marca
				+ ", Modelo: " + modelo;
	}  
}
