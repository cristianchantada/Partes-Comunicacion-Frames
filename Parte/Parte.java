package Parte;
import java.io.Serializable;
import java.util.Date;

import Clientes.Cliente;
import Empleado.Empleado;
import EstadoPartesEnum.EstadoParte;
import Material.Material;
import Servicios.Servicio;


public class Parte implements Serializable {

    private String descripcion;
    private Date fecha;
    private String observaciones;
    private EstadoParte estado = EstadoParte.EN_PROCESO;
    private Cliente cliente;
    private Empleado empleado;
    private Vehiculo vehiculo; //opcional
    private Servicio servicio; //opcional
    private Material material; //opcional
    private Localizacion localizacion;

    public Parte() {}

    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio) {
        this();
        this.fecha = fecha;
        this.cliente = cliente;
        this.empleado = empleado;
        this.vehiculo = vehiculo;
        this.servicio = servicio;
    }

    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, Material material){
        this(fecha, cliente, empleado, vehiculo, servicio);
        this.material = material;
    }
    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, Localizacion localizacion){
        this(fecha, cliente, empleado, vehiculo, servicio);
        this.localizacion = localizacion;
    }

    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, Material material, Localizacion localizacion){
        this(fecha, cliente, empleado, vehiculo, servicio, material);
        this.localizacion = localizacion;
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

    public EstadoParte getEstado() {
        return estado;
    }

    public void setEstado(EstadoParte estado) {
        this.estado = estado;
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

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion localizacion) {
        this.localizacion = localizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "----- Parte con fecha" + fecha + "-------\n" +
                "\t\tObservaciones:" + observaciones + "\n" +
                "\t\tEstado:\n\t\t\t" + estado + "\n" +
                "\t\tCliente:\n\t\t\t" + cliente + "\n" +
                "\t\tEmpleado:\n\t\t\t" + empleado + "\n" +
                "\t\tVehiculo:\n\t\t\t" + vehiculo + "\n" +
                "\t\tServicio:\n\t\t\t" + servicio + "\n" +
                "\t\tMaterial:\n\t\t\t" + material + "\n" +
                "\t\tlocalizacion\t\t\t: " + localizacion + "\n\n";
    }
}
