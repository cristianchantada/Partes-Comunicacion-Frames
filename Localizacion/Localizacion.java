package Localizacion;

import java.io.Serializable;

public class Localizacion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
    private String direccion;
    private String cp;
    private String localidad;
    private String provincia;

    public Localizacion() {}
    
    public Localizacion(int id) {
    	this.id = id;
    }
    
    public Localizacion(int id, String direccion, String cp, String localidad, String provincia) {
    	this(id);
    	this.direccion = direccion;
        this.cp = cp;
        this.localidad = localidad;
        this.provincia = provincia;
    }

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    


}