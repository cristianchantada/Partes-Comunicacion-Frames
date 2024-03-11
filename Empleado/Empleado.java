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
		return "El empleado ha sido creado con los siguientes datos:" + "\n\tNombre: " + nombre + "\n\tNIF: " + nif
				+ "\n\tTeléfono: " + telefono + "\n\tCorreo electrónico: " + correo + "\n\tCódigo empleado" + codigo;
	}

	public static boolean empleadoExiste(String nif, List<Empleado> le) {
		for (Empleado e : le) {
			if (nif.equals(e.nif))
				return true;
		}
		return false;
	}
}