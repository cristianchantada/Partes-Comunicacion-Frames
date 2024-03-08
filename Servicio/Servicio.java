package Servicio;
import java.io.*;
import java.util.Date;
public class Servicio implements Serializable {

	private String desc;
	private String he;
	private String hs;
	private Date fecha;

	public Servicio() {
	}
	public Servicio(String desc, String he, String hs, Date fecha) {
		this.desc = desc;
		this.he = he;
		this.hs = hs;
		this.fecha = fecha;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getHE() {
		return he;
	}
	public void setHE(String he) {
		this.he = he;
	}
	public String getHS() {
		return hs;
	}
	public void setHS(String hs) {
		this.hs = hs;
	}
	public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}