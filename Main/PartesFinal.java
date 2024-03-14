package Main;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import Clientes.*;
import Empleado.Empleado;
import Empleado.FicheroEmpleado;
import Empleado.VentanaEmpleado;
import Interfaces.ComunicaDatos;
import Localizacion.Localizacion;
import Material.*;
import Parte.FicheroPartes;
import Parte.Parte;
import Servicio.*;
import Vehiculo.FicheroVehiculos;
import Vehiculo.Vehiculo;
import Vehiculo.VentanaVehiculo;

import static Mensajes.Mensaje.verMensaje;;

public class PartesFinal extends JFrame implements ComunicaDatos {

	private static final long serialVersionUID = 1L;
	static boolean lanzarDatosEmpleado = false;

    public final static String ABSOLUTE_PATH_TO_MODEL_DIR = System.getProperty("user.dir") + "//model//";

	private JPanel panelAplicacion = new JPanel();
	private JDateChooser txtCalendarFechaParte = new JDateChooser();
	private JTextPane textareaObservaciones = new JTextPane();
	private JTextField txtDescripcionObraServicio = new JTextField();
	private JTextPane txtareaMaterial = new JTextPane();
	public JTextPane txtareaServicios = new JTextPane();
	private JTextField txtMatricula = new JTextField();
	private JTextField txtKilometros = new JTextField();
	private JTextField txtDireccion = new JTextField();
	private JTextField txtCodigoPostal = new JTextField();
	private JTextField textLocalidad = new JTextField();
	private JTextField textProvincia = new JTextField();
	private ButtonGroup estadoParte = new ButtonGroup();
	private JRadioButton radiobuttonTerminado = new JRadioButton("Terminado");
	private JRadioButton radiobuttonEnProceso = new JRadioButton("En proceso");
	private JButton botonAltaParte = new JButton("Alta Parte");
	private JButton botonLimpiarParte = new JButton("Limpiar");
	private JButton newEmployeeButton = new JButton("Crear nuevo empleado");
	private JButton newClientButton = new JButton("Crear nuevo cliente");
	private JButton newVehicleButton = new JButton("Crear nuevo vehículo");
	private JButton loadReportButton = new JButton("Cargar parte");
	
	private Cliente cliente = new Cliente();
	private Cliente selectedCliente = new Cliente();
	private Vehiculo selectedVehicle = new Vehiculo();
	private Empleado selectedEmployee = new Empleado();
	
	public List<Material> listaMateriales;
	public List<Servicio> listaServicios;
	

	// Lanzador de la aplicación
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartesFinal frame = new PartesFinal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor
	public PartesFinal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 873, 764);
		panelAplicacion.setFocusable(false);
		panelAplicacion.setOpaque(false);
		panelAplicacion.setAlignmentY(0.1f);
		panelAplicacion.setAlignmentX(0.1f);
		panelAplicacion.setBorder(null);
		setContentPane(panelAplicacion);
		panelAplicacion.setLayout(null);
		llamarMetodosMontajeDeComponentes();
		panelAplicacion.repaint();
	}
	
	// Método que llama a montar todos los componentes de la app.
	private void llamarMetodosMontajeDeComponentes() {
		montarInputsLocalizacion();
		montarInputsCliente();
		montarInputsClaseParte();
		montarInputsEmpleado();
		montarInputsMaterial();
		montarInputsServicios();
		montarInputsVehiculo();
		montarBotonLimpiar();
		montarBotonAltaParte();
		montarBotonNuevoCliente();
		montarBotonNuevoEmpleado();
		montarBotonNuevoVehiculo();
		montarLabels();
		montarInputsVistoBueno();
	}

	public void onSalirFrameMateriales(String materiales) {
		txtareaMaterial.setText(materiales);
		verMensaje(materiales + " XXXXXXX");
	}

	public void onSalirFrameServicios(String servicios) {
		JOptionPane.showMessageDialog(this, "Datos devueltos: " + servicios);
	}


	// Implementación de interfaces para el intercambio de datos
	public void enviaDatosServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;	
		for(Servicio servicio : this.listaServicios) {
			txtareaServicios.setText(txtareaServicios.getText() + "\n " 
		+ "SERVICIO: " + servicio.getDesc() + "|  FECHA INICIO: " + servicio.getHe() + "|  FECHA FIN: " + servicio.getHs() + "\n" );
		}
	}

	public void enviaDatosMaterial(List<Material> listaMateriales) {
		this.listaMateriales = listaMateriales;
		for(Material material : this.listaMateriales) {
			txtareaServicios.setText(txtareaServicios.getText() + "\n " 
		+ "Material: " + material.getDesc() + "| Cantidad: " + material.getCtdad() + "\n" );
		}
	}

	public static String pedirCliente() {
		String s = JOptionPane.showInputDialog("Introduce el cliente:");
		verMensaje(
				"Si el cliente existe paso al siguiente campo\n" + "si no existe hago que salte panel de alta cliente");
		return s;
	}

	// Métodos para el montaje de los componentes en pantalla
	private void montarBotonAltaParte() {
		botonAltaParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			Parte parte = new Parte();
			parte.setObservaciones(textareaObservaciones.getText());
			parte.setFecha(txtCalendarFechaParte.getDate());
			parte.setDescripcion(txtDescripcionObraServicio.getText());
			parte.setDescripcionMaterial(txtareaMaterial.getText());
			parte.setDescripcionServicio(txtareaServicios.getText());
			parte.setKilometrosRealizados(Double.parseDouble(txtKilometros.getText()));
			
			parte.setVehiculo(selectedVehicle);
			parte.setCliente(selectedCliente);
			parte.setEmpleado(selectedEmployee);
				
			Localizacion localizacion = new Localizacion();	
			localizacion.setCodigoPostal(txtCodigoPostal.getText());
			localizacion.setDireccion(txtDireccion.getText());
			localizacion.setLocalidad(textLocalidad.getText());
			localizacion.setProvincia(textProvincia.getText());
			parte.setLocalizacion(localizacion);
			
			FicheroPartes ficheroPartes = new FicheroPartes();
			List<Parte> listaPartes = ficheroPartes.consultarPartes();
			listaPartes.add(parte);
			ficheroPartes.generarPartes(listaPartes);
			
			}
		});
		botonAltaParte.setBounds(738, 666, 90, 30);
		panelAplicacion.add(botonAltaParte);
	}

	private void montarBotonLimpiar() {
		botonLimpiarParte.setBounds(108, 666, 90, 30);
		panelAplicacion.add(botonLimpiarParte);
		botonLimpiarParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] componentes = panelAplicacion.getComponents();
				for (Component componente : componentes) {
					if (componente instanceof JTextField) {
						((JTextField) componente).setText("");
						// Establecemos el texto en blanco para JTextField
					} else if (componente instanceof JComboBox) {
						((JComboBox<?>) componente).setSelectedIndex(0);
					} else if (componente instanceof JTextPane) {
						((JTextPane) componente).setText("");
					}
				}
			}
		});
		// Colocar todo como al principio
		lanzarDatosEmpleado = false;
	}
	
	private void montarBotonNuevoCliente() {
		newClientButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaCliente ventanaCliente = new VentanaCliente(cliente);
				ventanaCliente.setVisible(true);			
			}
		});
		newClientButton.setBounds(440, 151, 179, 20);
		panelAplicacion.add(newClientButton);
	}
	
	
	private void montarBotonNuevoEmpleado() {
		newEmployeeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaEmpleado ventanaEmpleado = new VentanaEmpleado();
				ventanaEmpleado.setVisible(true);	
			}
		});
		newEmployeeButton.setBounds(440, 182, 179, 20);
		panelAplicacion.add(newEmployeeButton);
	}
	
	private void montarBotonNuevoVehiculo() {
		
		newVehicleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaVehiculo ventanaVehiculo= new VentanaVehiculo();
				ventanaVehiculo.setVisible(true);
			}
		});
		newVehicleButton.setBounds(440, 612, 179, 20);
		panelAplicacion.add(newVehicleButton);
	}
	

	private void montarInputsClaseParte() {

		// Input fecha del parte JCalendar
		txtCalendarFechaParte.setFocusable(false);
		txtCalendarFechaParte.setBounds(485, 210, 152, 20);
		panelAplicacion.add(txtCalendarFechaParte);

		// Descripción del parte
		txtDescripcionObraServicio.setAlignmentY(0.1f);
		txtDescripcionObraServicio.setAlignmentX(0.1f);
		txtDescripcionObraServicio.setColumns(10);
		txtDescripcionObraServicio.setBounds(108, 209, 300, 20);
		panelAplicacion.add(txtDescripcionObraServicio);

		// Textarea de observaciones
		textareaObservaciones.setBorder(new LineBorder(new Color(0, 0, 0)));
		textareaObservaciones.setBounds(108, 504, 300, 50);
		panelAplicacion.add(textareaObservaciones);

		// Radiobuttons del enum del Estado Parte
		radiobuttonEnProceso.setBounds(160, 570, 109, 23);
		panelAplicacion.add(radiobuttonEnProceso);
		radiobuttonTerminado.setBounds(271, 570, 109, 23);
		panelAplicacion.add(radiobuttonTerminado);
		estadoParte.add(radiobuttonEnProceso);
		estadoParte.add(radiobuttonTerminado);

		loadReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		loadReportButton.setBounds(299, 103, 109, 23);
		panelAplicacion.add(loadReportButton);
		
		
		JComboBox<String> partesSelectorComboBox = new JComboBox<String>();
		partesSelectorComboBox.setBounds(108, 104, 152, 20);
		ArrayList<Parte> listaPartes = new ArrayList<>();
		FicheroPartes ficheroPartes = new FicheroPartes();
		listaPartes = (ArrayList<Parte>) ficheroPartes.consultarPartes();

		partesSelectorComboBox.addItem("--Escoja un parte de la lista--");
		for (Parte parte : listaPartes) {
			partesSelectorComboBox.addItem(parte.getDescripcion() + ", " + parte.getFecha());
		}
		partesSelectorComboBox.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (partesSelectorComboBox.getSelectedIndex() != 0) {
					
				}
			}
		});
					
		panelAplicacion.add(partesSelectorComboBox);
		
	}

	private void montarInputsCliente() {

		JComboBox<Object> clientNifComboBox = new JComboBox<>();
		clientNifComboBox.setBounds(108, 151, 300, 20);
		ArrayList<Cliente> listaDeClientes = new ArrayList<>();
		listaDeClientes = (ArrayList<Cliente>) FicheroCliente.leerFichero();

		clientNifComboBox.addItem("--Escoja un cliente de la lista--");
		for (Cliente cliente : listaDeClientes) {
			clientNifComboBox.addItem(cliente);
		}
		clientNifComboBox.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clientNifComboBox.getSelectedIndex() != 0) {
					selectedCliente = (Cliente) clientNifComboBox.getSelectedItem(); 
				}
			}
		});
		panelAplicacion.add(clientNifComboBox);
	}

	private void montarInputsEmpleado() {

		// Combo box del NIF del Empleado
		JComboBox<Object> employeeNifComboBox = new JComboBox<>();
		employeeNifComboBox.setBounds(108, 182, 300, 20);
		ArrayList<Empleado> listaDeEmpleados = new ArrayList<>();
		FicheroEmpleado ficheroEmpleados = new FicheroEmpleado();
		listaDeEmpleados = (ArrayList<Empleado>) ficheroEmpleados.leerFicheroEmpleado();

		employeeNifComboBox.addItem("--Escoja un empleado de la lista--");
		for (Empleado empleado : listaDeEmpleados) {
			employeeNifComboBox.addItem(empleado);
		}
		employeeNifComboBox.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (employeeNifComboBox.getSelectedIndex() != 0) {
					selectedEmployee = (Empleado) employeeNifComboBox.getSelectedItem();	
				}
			}
		});
		panelAplicacion.add(employeeNifComboBox);
	}

	private void montarInputsMaterial() {
		txtareaMaterial.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtareaMaterial.setBounds(108, 244, 300, 119);
		panelAplicacion.add(txtareaMaterial);

		PartesFinal parteActual = this;

		txtareaMaterial.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!lanzarDatosEmpleado) {
					if (txtareaMaterial.getText().isBlank()) {
						// Creo el objeto material y le paso la clase (para uso de interface)
						VentanaMaterial misMateriales = new VentanaMaterial(parteActual);
						misMateriales.inputMaterial.requestFocus();
						lanzarDatosEmpleado = true;
					} else {
						lanzarDatosEmpleado = false;
						verMensaje("No está en blanco");
					}
				}
			}
		});
	}

	private void montarInputsServicios() {
		txtareaServicios.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtareaServicios.setBounds(108, 374, 300, 119);
		panelAplicacion.add(txtareaServicios);
		
		PartesFinal parteActual = this;

		txtareaServicios.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!lanzarDatosEmpleado) {
					if (txtareaServicios.getText().isBlank()) {
						// Creo el objeto material y le paso la clase (para uso de interface)
						VentanaServicio misServicios = new VentanaServicio(parteActual);
						misServicios.txtServicio.requestFocus();
						lanzarDatosEmpleado = true;
					} else {
						lanzarDatosEmpleado = false;
						verMensaje("No está en blanco");
					}
				}
			}
		});
	}

	private void montarInputsVehiculo() {
		
		JComboBox<Object> vehiculoComboBox = new JComboBox<>();
		vehiculoComboBox.setBounds(108, 612, 300, 20);
		ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
		listaVehiculos = (ArrayList<Vehiculo>) FicheroVehiculos.leerFichero();

		vehiculoComboBox.addItem("--Escoja un vehículo de la lista--");
		for (Vehiculo vehiculo : listaVehiculos) {
			vehiculoComboBox.addItem(vehiculo);
		}
		vehiculoComboBox.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (vehiculoComboBox.getSelectedIndex() != 0) {
					selectedVehicle = (Vehiculo) vehiculoComboBox.getSelectedItem();		   
				}
			}
		});
		panelAplicacion.add(vehiculoComboBox);

		txtKilometros.setColumns(10);
		txtKilometros.setBounds(328, 374, 80, 20);
		panelAplicacion.add(txtKilometros);
	}

	private void montarInputsLocalizacion() {
		txtDireccion.setColumns(10);
		txtDireccion.setAlignmentY(0.1f);
		txtDireccion.setAlignmentX(0.1f);
		txtDireccion.setBounds(547, 275, 300, 20);

		txtCodigoPostal.setColumns(10);
		txtCodigoPostal.setAlignmentY(0.1f);
		txtCodigoPostal.setAlignmentX(0.1f);
		txtCodigoPostal.setBounds(547, 303, 72, 20);

		textLocalidad.setColumns(10);
		textLocalidad.setAlignmentY(0.1f);
		textLocalidad.setAlignmentX(0.1f);
		textLocalidad.setBounds(547, 332, 229, 20);

		textProvincia.setColumns(10);
		textProvincia.setAlignmentY(0.1f);
		textProvincia.setAlignmentX(0.1f);
		textProvincia.setBounds(547, 361, 211, 20);

		panelAplicacion.add(txtCodigoPostal);
		panelAplicacion.add(textLocalidad);
		panelAplicacion.add(textProvincia);
		panelAplicacion.add(txtDireccion);
	}
	
	
	public void montarInputsVistoBueno() {
		JTextPane vistoBuenoTextArea = new JTextPane();
		vistoBuenoTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		vistoBuenoTextArea.setBounds(476, 414, 350, 175);
		panelAplicacion.add(vistoBuenoTextArea);
		
		JLabel vistoBuenoLabel = new JLabel("Visto bueno:");
		vistoBuenoLabel.setAlignmentY(0.0f);
		vistoBuenoLabel.setBounds(485, 389, 64, 14);
		panelAplicacion.add(vistoBuenoLabel);
	}

	private void montarLabels() {
		JLabel labelLugarSitio = new JLabel("Lugar/Sitio:");
		labelLugarSitio.setBounds(440, 244, 109, 14);
		panelAplicacion.add(labelLugarSitio);

		JLabel label = new JLabel("New label");
		label.setBounds(430, 55, 35, -1);
		panelAplicacion.add(label);

		JLabel labelDireccion = new JLabel("Dirección:");
		labelDireccion.setAlignmentY(0.0f);
		labelDireccion.setBounds(485, 278, 64, 14);
		panelAplicacion.add(labelDireccion);

		JLabel labelCodigoPostal = new JLabel("CP:");
		labelCodigoPostal.setAlignmentY(0.0f);
		labelCodigoPostal.setBounds(485, 307, 64, 14);
		panelAplicacion.add(labelCodigoPostal);

		JLabel labelLocalidad = new JLabel("Localidad:");
		labelLocalidad.setAlignmentY(0.0f);
		labelLocalidad.setBounds(485, 336, 64, 14);
		panelAplicacion.add(labelLocalidad);

		JLabel labelProvincia = new JLabel("Provincia:");
		labelProvincia.setAlignmentY(0.0f);
		labelProvincia.setBounds(485, 361, 64, 14);
		panelAplicacion.add(labelProvincia);

		JLabel labelKilometros = new JLabel("Km realizados:");
		labelKilometros.setBounds(225, 374, 120, 20);
		panelAplicacion.add(labelKilometros);

		JLabel labelObservaciones = new JLabel("Observaciones:");
		labelObservaciones.setBounds(10, 504, 100, 14);
		panelAplicacion.add(labelObservaciones);

		JLabel labelVehiculo = new JLabel("Vehículo:");
		labelVehiculo.setBounds(46, 612, 64, 20);
		panelAplicacion.add(labelVehiculo);

		JLabel labelEstado = new JLabel("Estado:");
		labelEstado.setBounds(108, 574, 46, 14);
		panelAplicacion.add(labelEstado);

		JLabel labelServicios = new JLabel("Servicios");
		labelServicios.setBounds(10, 374, 64, 20);
		panelAplicacion.add(labelServicios);

		JLabel labelDescripcionObraServicio = new JLabel("Obra/Servicio:");
		labelDescripcionObraServicio.setBounds(10, 210, 128, 20);
		panelAplicacion.add(labelDescripcionObraServicio);

		JLabel labelMateriales = new JLabel("Materiales");
		labelMateriales.setBounds(10, 244, 64, 20);
		panelAplicacion.add(labelMateriales);

		JLabel labelEmpleado = new JLabel("Empleado:");
		labelEmpleado.setBounds(10, 182, 64, 20);
		panelAplicacion.add(labelEmpleado);

		JLabel labelCliente = new JLabel("Cliente:");
		labelCliente.setBounds(10, 151, 46, 20);
		panelAplicacion.add(labelCliente);

		JLabel labelFecha = new JLabel("Fecha:");
		labelFecha.setBounds(440, 213, 46, 14);
		panelAplicacion.add(labelFecha);
		
		JLabel parteSelectLabel = new JLabel("Selecciona parte:");
		parteSelectLabel.setBounds(108, 82, 152, 14);
		panelAplicacion.add(parteSelectLabel);
		
	}

}
