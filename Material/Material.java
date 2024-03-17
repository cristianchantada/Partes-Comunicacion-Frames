package Material;

import java.io.Serializable;

public class Material implements Serializable {

	private static final long serialVersionUID = 1L;
	private String desc;
	private int ctdad;
	
	public Material() {}
	
	public Material(String desc, int ctdad) {
		this.desc = desc;
		this.ctdad = ctdad;
	}

	public String getDesc() {
		return desc;
	}

	public int getCtdad() {
		return ctdad;
	}
	
}
