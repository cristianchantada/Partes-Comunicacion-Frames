package Empleado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Empleado implements Serializable {

	
	//static ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();

	// String

	private String nombre;
	private String nif;
	private String correo;
	private String telefono;
	private String codigo;
	
	public Empleado(String nif) {
	    this.nif=nif;
		nombre="";
		correo="";
		telefono="";
		codigo="";
	}
	
	public Empleado(String nif, String name, String tlf, String mail,String codigoOperario) {
		this.nif=nif;
		nombre=name;
		correo=mail;
		telefono=tlf;
		codigo=codigoOperario;
	}
	

	@Override
	public String toString() {
		return " Cliente{" + "nombre='" + nombre + '\'' + ", nif='" + nif + '\'' + ", correo='" + correo + '\''
				+ ", telefono='" + telefono + '\'' + '}';
	}


	public static boolean empleadoExiste(String nif,List<Empleado> le ) {
		for(Empleado e:le ) {
			if (nif.equals(e.nif)) return true;
		} 
		
		return false;
		
	
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}