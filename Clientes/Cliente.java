package Clientes;

import java.io.Serializable;
import java.util.List;


public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String nif;
	private String correo;
	private String telefono;
	
	public Cliente(String nif) {
		this.nif=nif;
		nombre="";
		correo="";
		telefono="";		
	}
	
	public Cliente(String nif, String name, String tlf, String mail) {
		this.nif=nif;
		nombre=name;
		correo=mail;
		telefono=tlf;	
	}

	@Override
	public String toString() {
		return " Cliente{" + "nombre='" + nombre + '\'' + ", nif='" + nif + '\'' + ", correo='" + correo + '\''
				+ ", telefono='" + telefono + '\'' + '}';
	}

	public static boolean clienteExiste(String nif,List<Cliente> lc ) {
		for(Cliente c:lc ) {
			if (nif.equals(c.nif)) return true;
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