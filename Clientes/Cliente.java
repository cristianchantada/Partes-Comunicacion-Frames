package Clientes;
import java.io.Serializable;
import java.util.List;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String nif;
	private String correo;
	private String telefono;
	
	public Cliente() {}
	
	public Cliente(String nif){
		this();
		this.nif = nif;
	}
	
	public Cliente(String nif, String name, String tlf, String mail) {
		this(nif);
		this.nombre=name;
		this.correo=mail;
		this.telefono=tlf;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre
				+ ", NIF: " + nif; 

	}

	public static boolean clienteExiste(String nif,List<Cliente> lc ) {
		if(lc != null) {
			for(Cliente c:lc ) {
				if(nif.equals(c.nif)) return true;
			}
			return false;
		}
		return false;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getNif() {
		return nif;
	}
}
		