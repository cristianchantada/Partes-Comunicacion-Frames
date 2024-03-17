package Parte;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import Clientes.Cliente;
import Empleado.Empleado;
import Localizacion.Localizacion;
import Material.Material;
import Material.VentanaMaterial;
import Servicio.Servicio;
import Vehiculo.Vehiculo;

public class Parte implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String descripcion;
    private Date fecha;
    private String observaciones;
    private EstadoParte estado = EstadoParte.EN_PROCESO;
    private Cliente cliente;
    private Empleado empleado;
    private Vehiculo vehiculo; //opcional
    private List<Servicio> listaServicios; //opcional
    private String descripcionServicio;
    public List<Material> listaMateriales;
    private String descripcionMaterial;
    private VentanaMaterial Vmaterial; //opcional
    private Localizacion localizacion;
    private double kilometrosRealizados;

    public Parte() {}

    public EstadoParte getEstado() {
        return estado;
    }

    public void setEstado(EstadoParte estado) {
        this.estado = estado;
    }
    
    

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<Servicio> getListaServicio() {
		return listaServicios;
	}

	public void setListaServicio(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public VentanaMaterial getMaterial() {
		return Vmaterial;
	}

	public void setMaterial(VentanaMaterial Vmaterial) {
		this.Vmaterial = Vmaterial;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}
	
	

	public double getKilometrosRealizados() {
		return kilometrosRealizados;
	}

	public void setKilometrosRealizados(double kilometrosRealizados) {
		this.kilometrosRealizados = kilometrosRealizados;
	}

	public String getDescripcionServicio() {
		return descripcionServicio;
	}

	public void setDescripcionServicio(String descripcionServicio) {
		this.descripcionServicio = descripcionServicio;
	}

	public String getDescripcionMaterial() {
		return descripcionMaterial;
	}

	public void setDescripcionMaterial(String descripcionMaterial) {
		this.descripcionMaterial = descripcionMaterial;
	}

	public void setListaMateriales(List<Material> listaMateriales) {
		this.listaMateriales = listaMateriales;
	}
	
	

	@Override
    public String toString() {
        return "Parte con fecha " + fecha +
                " | Observaciones: " + observaciones +
                " | Estado: " + estado +
                " | Cliente: " + cliente.getNif()+
                " | Empleado: " + empleado.getNif();
    }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	
}