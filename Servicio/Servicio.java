package Servicio;

import java.io.*;
import java.util.Date;

public class Servicio implements Serializable {

	private static final long serialVersionUID = 1L;
	private String desc;
	private String he;
	private String hs;
	private Date fecha;

	public Servicio() {}
	
	public Servicio(String desc, String he, String hs, Date fecha) {
		this.desc = desc;
		this.he = he;
		this.hs = hs;
		this.fecha = fecha;
	}

	public String getDesc() {
		return desc;
	}

	public String getHe() {
		return he;
	}

	public String getHs() {
		return hs;
	}

	public Date getFecha() {
		return fecha;
	}
	
	

}