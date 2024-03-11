package Localizacion;

public class Localizacion {
    private String direccion;
    private String codigoPostal;
    private String localidad;
    private String provincia;

    public Localizacion() {}
    
    public Localizacion(String direccion, String codigoPostal, String localidad, String provincia) {
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
    }


}
