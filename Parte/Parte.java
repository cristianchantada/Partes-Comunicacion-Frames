package Parte;
import java.io.Serializable;
import java.util.Date;

import Clientes.Cliente;
import Empleado.Empleado;
import Localizacion.Localizacion;
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
    private Servicio servicio; //opcional
    private VentanaMaterial material; //opcional
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

    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, VentanaMaterial material){
        this(fecha, cliente, empleado, vehiculo, servicio);
        this.material = material;
    }
    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, Localizacion localizacion){
        this(fecha, cliente, empleado, vehiculo, servicio);
        this.localizacion = localizacion;
    }

    public Parte(Date fecha, Cliente cliente, Empleado empleado, Vehiculo vehiculo, Servicio servicio, VentanaMaterial material, Localizacion localizacion){
        this(fecha, cliente, empleado, vehiculo, servicio, material);
        this.localizacion = localizacion;
    }


    public EstadoParte getEstado() {
        return estado;
    }

    public void setEstado(EstadoParte estado) {
        this.estado = estado;
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