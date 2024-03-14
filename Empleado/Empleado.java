package Empleado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Empleado implements Serializable {

	private String nombre;
	private String nif;
	private String correo;
	private String telefono;
	private String codigo;

	public Empleado() {}
	
	public Empleado(String nif) {
		this.nif = nif;
	}

	public Empleado(String nif, String name, String tlf, String mail, String codigoOperario) {
		this(nif);
		this.nombre = name;
		this.correo = mail;
		this.telefono = tlf;
		this.codigo = codigoOperario;
	}

	@Override
	public String toString() {
		return "Nombre: " + nombre + ", NIF: " + nif;
	}

	public static boolean empleadoExiste(String nif, List<Empleado> le) {
		for (Empleado e : le) {
			if (nif.equals(e.nif))
				return true;
		}
		return false;
	}
}