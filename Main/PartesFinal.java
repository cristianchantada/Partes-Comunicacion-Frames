package Main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import com.toedter.calendar.JDateChooser;
import Clientes.*;
import Empleado.*;
import Interfaces.ComunicaDatos;
import Localizacion.Localizacion;
import Material.*;
import Parte.*;
import Servicio.*;
import Vehiculo.*;
import VistoBueno.VentanaVistoBueno;
import models.LocalizacionDao;
import models.ParteDao;

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
	JTextPane vistoBuenoTextArea = new JTextPane();
	private JTextField txtKilometros = new JTextField();
	private JTextField txtDireccion = new JTextField();
	private JTextField txtCodigoPostal = new JTextField();
	private JTextField textLocalidad = new JTextField();
	private JTextField textProvincia = new JTextField();
	private ButtonGroup estadoParteButtonGroup = new ButtonGroup();
	private JRadioButton radiobuttonTerminado = new JRadioButton("Terminado");
	private JRadioButton radiobuttonEnProceso = new JRadioButton("En proceso");
	private JButton botonAltaParte = new JButton("Alta Parte");
	private JButton botonLimpiarParte = new JButton("Limpiar");
	private JButton newEmployeeButton = new JButton("Crear nuevo empleado");
	private JButton newClientButton = new JButton("Crear nuevo cliente");
	private JButton newVehicleButton = new JButton("Crear nuevo vehículo");
	private JButton loadReportButton = new JButton("Cargar parte");
	private JButton newMaterialButton = new JButton("Agregar materiales");
	private JButton newServicetButton = new JButton("Agregar Servicios");
	private JComboBox<Object> partesSelectorComboBox;
	private JComboBox<Object> clientNifComboBox;
	private JComboBox<Object> employeeNifComboBox;
	private JComboBox<Object> vehiculoComboBox;
	private Cliente selectedCliente = new Cliente();
	private Vehiculo selectedVehicle = new Vehiculo();
	private Empleado selectedEmployee = new Empleado();
	
	public List<Material> listaMateriales;
	public List<Servicio> listaServicios;
	public List<String> listaVistoBueno;
	private ArrayList<Empleado> listaDeEmpleados;
	private ArrayList<Cliente> listaDeClientes;
	private ArrayList<Vehiculo> listaVehiculos;
	
	private boolean isParteCargado = false;

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
		setBounds(10, 10, 1048, 689);
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
		for (Servicio servicio : this.listaServicios) {
			txtareaServicios.setText(txtareaServicios.getText() + "\n " + "SERVICIO: " + servicio.getDesc()
					+ "|  FECHA INICIO: " + servicio.getHe() + "|  FECHA FIN: " + servicio.getHs() + "\n");
		}
	}

	public void enviaDatosMaterial(List<Material> listaMateriales) {
		this.listaMateriales = listaMateriales;
		for (Material material : this.listaMateriales) {
			txtareaMaterial.setText(txtareaMaterial.getText() + "Material: " + material.getDesc() + " | Cantidad: "
					+ material.getCtdad() + "\n");
		}
	}

	@Override
	public void enviaDatosVistoBueno(List<String> listaVistoBueno) {
		this.listaVistoBueno = listaVistoBueno;
		String codigoResponsableVistoBueno = this.listaVistoBueno.get(1);
		String responsableVistoBueno = this.listaVistoBueno.get(1);
		String descripciónVistoBueno = this.listaVistoBueno.get(2);
		vistoBuenoTextArea.setText("Código Responsable: " + codigoResponsableVistoBueno
				+ "\nNombre responsable: " + responsableVistoBueno
				+ "\nObservaciones: " + descripciónVistoBueno);
	}

	public void enviaConfirmacionDatosGuardados(boolean isDataSaved) {
		if (isDataSaved) {
			dispose();
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
				
				if(txtCalendarFechaParte.getDate() != null) {
					parte.setFecha(txtCalendarFechaParte.getDate());
				} else {
					verMensaje("El campo fecha no puede quedar vacío");
					return;
				}

				parte.setDescripcion(txtDescripcionObraServicio.getText());
				parte.setDescripcionMaterial(txtareaMaterial.getText());
				parte.setDescripcionServicio(txtareaServicios.getText());
				
				if(!txtKilometros.getText().equals("")) {
					try {
						parte.setKilometrosRealizados(Double.parseDouble(txtKilometros.getText()));
					} catch(Exception excp) {
						verMensaje("El campo kilómetros solo debe contener dígitos");
						return;
					}
				} else {
					verMensaje("Debe indicar los kilómetros que recorrió el vehículo para realizar el servicio");
					return;
				}
				
				parte.setListaMateriales(listaMateriales);
				parte.setListaServicio(listaServicios);
			
				if(vehiculoComboBox.getSelectedIndex() != 0) {
					parte.setVehiculo(selectedVehicle);
				} else {
					verMensaje("Debe seleccionar el vehículo que se usó para acudir al servicio");
					return;
				}
				
				if(clientNifComboBox.getSelectedIndex() != 0) {
					parte.setCliente(selectedCliente);
				} else {
					verMensaje("Debe seleccionar un cliente para dar un parte de alta");
					return;
				}

				if(employeeNifComboBox.getSelectedIndex() != 0) {
					parte.setEmpleado(selectedEmployee);
				} else {
					verMensaje("Debe seleccionar un empleado para dar un parte de alta");
					return;
				}
				
				Localizacion localizacion = new Localizacion();
				localizacion.setCp(txtCodigoPostal.getText());
				localizacion.setDireccion(txtDireccion.getText());
				localizacion.setLocalidad(textLocalidad.getText());
				localizacion.setProvincia(textProvincia.getText());

				LocalizacionDao localizacionDao = new LocalizacionDao();
				localizacionDao.save(localizacion);
				Localizacion auxLocation = new Localizacion();
				LocalizacionDao localizacionDao2 = new LocalizacionDao();
				auxLocation = localizacionDao2.getLocationByData(localizacion);
				localizacion.setId(auxLocation.getId());

				parte.setLocalizacion(localizacion);

				FicheroPartes ficheroPartes = new FicheroPartes();
				List<Parte> listaPartes = ficheroPartes.consultarPartes();
				listaPartes.add(parte);
				ficheroPartes.generarPartes(listaPartes);

				ParteDao parteDao = new ParteDao();
				parteDao.save(parte);

			}
		});
		botonAltaParte.setBounds(738, 595, 90, 30);
		panelAplicacion.add(botonAltaParte);
	}

	private void montarBotonLimpiar() {
		botonLimpiarParte.setBounds(111, 609, 90, 30);
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
				activarTodo();
			}
		});
		// Colocar todo como al principio
		lanzarDatosEmpleado = false;
	}

	private void montarBotonNuevoCliente() {
		PartesFinal parteActual = this;
		newClientButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaCliente ventanaCliente = new VentanaCliente(parteActual);
				ventanaCliente.setVisible(true);
			}
		});
		newClientButton.setBounds(440, 80, 179, 20);
		panelAplicacion.add(newClientButton);
	}

	private void montarBotonNuevoEmpleado() {
		PartesFinal parteActual = this;
		newEmployeeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaEmpleado ventanaEmpleado = new VentanaEmpleado(parteActual);
				ventanaEmpleado.setVisible(true);
			}
		});
		newEmployeeButton.setBounds(440, 111, 179, 20);
		panelAplicacion.add(newEmployeeButton);
	}

	private void montarBotonNuevoVehiculo() {
		PartesFinal parteActual = this;
		newVehicleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaVehiculo ventanaVehiculo = new VentanaVehiculo(parteActual);
				ventanaVehiculo.setVisible(true);
			}
		});
		newVehicleButton.setBounds(440, 541, 179, 20);
		panelAplicacion.add(newVehicleButton);
	}

	private void montarInputsClaseParte() {

		// Input fecha del parte JCalendar
		txtCalendarFechaParte.setFocusable(false);
		txtCalendarFechaParte.setBounds(485, 139, 152, 20);
		panelAplicacion.add(txtCalendarFechaParte);

		// Descripción del parte
		txtDescripcionObraServicio.setAlignmentY(0.1f);
		txtDescripcionObraServicio.setAlignmentX(0.1f);
		txtDescripcionObraServicio.setColumns(10);
		txtDescripcionObraServicio.setBounds(108, 138, 300, 20);
		panelAplicacion.add(txtDescripcionObraServicio);

		// Textarea de observaciones
		textareaObservaciones.setBorder(new LineBorder(new Color(0, 0, 0)));
		textareaObservaciones.setBounds(108, 433, 300, 50);
		panelAplicacion.add(textareaObservaciones);

		// Radiobuttons del enum del Estado Parte
		radiobuttonEnProceso.setBounds(160, 499, 109, 23);
		panelAplicacion.add(radiobuttonEnProceso);
		radiobuttonTerminado.setBounds(271, 499, 109, 23);
		panelAplicacion.add(radiobuttonTerminado);
		estadoParteButtonGroup.add(radiobuttonEnProceso);
		estadoParteButtonGroup.add(radiobuttonTerminado);

		loadReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (partesSelectorComboBox.getSelectedIndex() != 0) {
					
					Parte parte = (Parte) partesSelectorComboBox.getSelectedItem();

					textareaObservaciones.setText(parte.getObservaciones());
					txtCalendarFechaParte.setDate(parte.getFecha());
					txtDescripcionObraServicio.setText(parte.getDescripcion());
					txtareaMaterial.setText(parte.getDescripcionMaterial());
					txtareaServicios.setText(parte.getDescripcionServicio());
					txtKilometros.setText(String.valueOf(parte.getKilometrosRealizados()));

					employeeNifComboBox.removeAllItems();
					clientNifComboBox.removeAllItems();
					vehiculoComboBox.removeAllItems();

					employeeNifComboBox.addItem(parte.getEmpleado());
					clientNifComboBox.addItem(parte.getCliente());
					vehiculoComboBox.addItem(parte.getVehiculo());

					employeeNifComboBox.setEditable(false);
					clientNifComboBox.setEditable(false);
					vehiculoComboBox.setEditable(false);

					if (parte.getEstado() == EstadoParte.EN_PROCESO) {
						radiobuttonEnProceso.setSelected(true);
					} else if (parte.getEstado() == EstadoParte.TERMINADO) {
						radiobuttonTerminado.setSelected(true);
					}

					Localizacion localizacion = parte.getLocalizacion();
					txtCodigoPostal.setText(localizacion.getCp());
					txtDireccion.setText(localizacion.getDireccion());
					textLocalidad.setText(localizacion.getLocalidad());
					textProvincia.setText(localizacion.getProvincia());

					desactivarTodo();
					
					isParteCargado = true;

				} else {
					verMensaje("Debes seleccionar un parte existente");
					isParteCargado = false;
				}
			}
		});

		loadReportButton.setBounds(896, 32, 109, 23);

		panelAplicacion.add(loadReportButton);

		partesSelectorComboBox = new JComboBox<Object>();
		partesSelectorComboBox.setBounds(108, 33, 766, 20);
		ArrayList<Parte> listaPartes = new ArrayList<>();
		FicheroPartes ficheroPartes = new FicheroPartes();
		listaPartes = (ArrayList<Parte>) ficheroPartes.consultarPartes();

		partesSelectorComboBox.addItem("--Escoja un parte de la lista--");
		for (Parte parte : listaPartes) {
			partesSelectorComboBox.addItem(parte);
		}
		panelAplicacion.add(partesSelectorComboBox);
	}

	private void montarInputsCliente() {

		clientNifComboBox = new JComboBox<>();
		clientNifComboBox.setBounds(108, 80, 300, 20);
		listaDeClientes = new ArrayList<>();
		listaDeClientes = (ArrayList<Cliente>) FicheroCliente.leerFichero();
		añadirItemsComboBoxCliente();
		panelAplicacion.add(clientNifComboBox);
	}

	private void montarInputsEmpleado() {
		employeeNifComboBox = new JComboBox<>();
		employeeNifComboBox.setBounds(108, 111, 300, 20);
		listaDeEmpleados = new ArrayList<>();
		FicheroEmpleado ficheroEmpleados = new FicheroEmpleado();
		listaDeEmpleados = (ArrayList<Empleado>) ficheroEmpleados.leerFicheroEmpleado();
		añadirItemsComboBoxEmpleado();
		panelAplicacion.add(employeeNifComboBox);
		
	}

	private void montarInputsMaterial() {
		txtareaMaterial.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtareaMaterial.setBounds(108, 173, 300, 119);
		txtareaMaterial.setEnabled(false);
		panelAplicacion.add(txtareaMaterial);

		PartesFinal parteActual = this;

		newMaterialButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Creo el objeto material y le paso la clase (para uso de interface)
				VentanaMaterial misMateriales = new VentanaMaterial(parteActual);
				misMateriales.inputMaterial.requestFocus();
				lanzarDatosEmpleado = true;
			}
		});

		newMaterialButton.setBounds(418, 228, 179, 20);
		panelAplicacion.add(newMaterialButton);

	}

	private void montarInputsServicios() {
		txtareaServicios.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtareaServicios.setBounds(108, 303, 300, 119);
		txtareaServicios.setEnabled(false);
		panelAplicacion.add(txtareaServicios);

		PartesFinal parteActual = this;

		newServicetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				VentanaServicio misServicios = new VentanaServicio(parteActual);
				misServicios.txtServicio.requestFocus();
				lanzarDatosEmpleado = true;
			}
		});

		newServicetButton.setBounds(418, 340, 179, 20);
		panelAplicacion.add(newServicetButton);

	}

	private void montarInputsVehiculo() {
		vehiculoComboBox = new JComboBox<>();
		vehiculoComboBox.setBounds(108, 541, 300, 20);
		listaVehiculos = new ArrayList<>();
		listaVehiculos = (ArrayList<Vehiculo>) FicheroVehiculos.leerFichero();
		añadirItemsComboBoxVehiculo();
		panelAplicacion.add(vehiculoComboBox);
		txtKilometros.setColumns(10);
		txtKilometros.setBounds(108, 572, 80, 20);
		panelAplicacion.add(txtKilometros);
	}

	private void montarInputsLocalizacion() {
		txtDireccion.setColumns(10);
		txtDireccion.setAlignmentY(0.1f);
		txtDireccion.setAlignmentX(0.1f);
		txtDireccion.setBounds(705, 201, 300, 20);

		txtCodigoPostal.setColumns(10);
		txtCodigoPostal.setAlignmentY(0.1f);
		txtCodigoPostal.setAlignmentX(0.1f);
		txtCodigoPostal.setBounds(705, 229, 72, 20);

		textLocalidad.setColumns(10);
		textLocalidad.setAlignmentY(0.1f);
		textLocalidad.setAlignmentX(0.1f);
		textLocalidad.setBounds(705, 258, 229, 20);

		textProvincia.setColumns(10);
		textProvincia.setAlignmentY(0.1f);
		textProvincia.setAlignmentX(0.1f);
		textProvincia.setBounds(705, 287, 211, 20);

		panelAplicacion.add(txtCodigoPostal);
		panelAplicacion.add(textLocalidad);
		panelAplicacion.add(textProvincia);
		panelAplicacion.add(txtDireccion);
	}

	public void montarInputsVistoBueno() {
		PartesFinal parteActual = this;
		vistoBuenoTextArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (partesSelectorComboBox.getSelectedIndex() != 0 && isParteCargado) {					
					VentanaVistoBueno ventanaVistoBueno = new VentanaVistoBueno(parteActual);
					ventanaVistoBueno.setVisible(true);
					vistoBuenoTextArea.setEnabled(false);
				} else {
					partesSelectorComboBox.requestFocus();
					verMensaje("Debe cargar un parte antes de proceder a su firma");
					
				}
			}
		});
		vistoBuenoTextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		vistoBuenoTextArea.setEditable(false);
		vistoBuenoTextArea.setBounds(653, 340, 352, 175);
		panelAplicacion.add(vistoBuenoTextArea);

	}

	private void montarLabels() {
		JLabel labelLugarSitio = new JLabel("Lugar/Sitio:");
		labelLugarSitio.setBounds(440, 173, 109, 14);
		panelAplicacion.add(labelLugarSitio);

		JLabel label = new JLabel("New label");
		label.setBounds(430, 55, 35, -1);
		panelAplicacion.add(label);

		JLabel labelDireccion = new JLabel("Dirección:");
		labelDireccion.setAlignmentY(0.0f);
		labelDireccion.setBounds(643, 204, 64, 14);
		panelAplicacion.add(labelDireccion);

		JLabel labelCodigoPostal = new JLabel("CP:");
		labelCodigoPostal.setAlignmentY(0.0f);
		labelCodigoPostal.setBounds(643, 233, 64, 14);
		panelAplicacion.add(labelCodigoPostal);

		JLabel labelLocalidad = new JLabel("Localidad:");
		labelLocalidad.setAlignmentY(0.0f);
		labelLocalidad.setBounds(643, 262, 64, 14);
		panelAplicacion.add(labelLocalidad);

		JLabel labelProvincia = new JLabel("Provincia:");
		labelProvincia.setAlignmentY(0.0f);
		labelProvincia.setBounds(643, 287, 64, 14);
		panelAplicacion.add(labelProvincia);

		JLabel labelKilometros = new JLabel("Km realizados:");
		labelKilometros.setBounds(22, 572, 120, 20);
		panelAplicacion.add(labelKilometros);

		JLabel labelObservaciones = new JLabel("Observaciones:");
		labelObservaciones.setBounds(10, 433, 100, 14);
		panelAplicacion.add(labelObservaciones);

		JLabel labelVehiculo = new JLabel("Vehículo:");
		labelVehiculo.setBounds(46, 541, 64, 20);
		panelAplicacion.add(labelVehiculo);

		JLabel labelEstado = new JLabel("Estado:");
		labelEstado.setBounds(108, 503, 46, 14);
		panelAplicacion.add(labelEstado);

		JLabel labelServicios = new JLabel("Servicios");
		labelServicios.setBounds(10, 303, 64, 20);
		panelAplicacion.add(labelServicios);

		JLabel labelDescripcionObraServicio = new JLabel("Obra/Servicio:");
		labelDescripcionObraServicio.setBounds(10, 139, 128, 20);
		panelAplicacion.add(labelDescripcionObraServicio);

		JLabel labelMateriales = new JLabel("Materiales");
		labelMateriales.setBounds(10, 173, 64, 20);
		panelAplicacion.add(labelMateriales);

		JLabel labelEmpleado = new JLabel("Empleado:");
		labelEmpleado.setBounds(10, 111, 64, 20);
		panelAplicacion.add(labelEmpleado);

		JLabel labelCliente = new JLabel("Cliente:");
		labelCliente.setBounds(10, 80, 46, 20);
		panelAplicacion.add(labelCliente);

		JLabel labelFecha = new JLabel("Fecha:");
		labelFecha.setBounds(440, 142, 46, 14);
		panelAplicacion.add(labelFecha);

		JLabel parteSelectLabel = new JLabel("Selecciona parte:");
		parteSelectLabel.setBounds(108, 11, 152, 14);
		panelAplicacion.add(parteSelectLabel);

		JLabel vistoBuenoLabel = new JLabel("Visto bueno:");
		vistoBuenoLabel.setAlignmentY(0.0f);
		vistoBuenoLabel.setBounds(643, 315, 90, 14);
		panelAplicacion.add(vistoBuenoLabel);

	}
	
	public void desactivarTodo() {
		textareaObservaciones.setEnabled(false);
		txtDescripcionObraServicio.setEnabled(false);
		txtareaMaterial.setEnabled(false);
		txtareaServicios.setEnabled(false);
		txtKilometros.setEnabled(false);
		employeeNifComboBox.setEnabled(false);
		clientNifComboBox.setEnabled(false);
		vehiculoComboBox.setEnabled(false);
		txtCodigoPostal.setEnabled(false);
		txtDireccion.setEnabled(false);
		textLocalidad.setEnabled(false);
		textProvincia.setEnabled(false);
		radiobuttonEnProceso.setEnabled(false);
		radiobuttonTerminado.setEnabled(false);
		botonAltaParte.setEnabled(false);
		newEmployeeButton.setEnabled(false);
		newClientButton.setEnabled(false);
		newMaterialButton.setEnabled(false);
		newServicetButton.setEnabled(false);
		newVehicleButton.setEnabled(false);
		txtCalendarFechaParte.setEnabled(false);
	}
	
	public void activarTodo() {
		textareaObservaciones.setEnabled(true);
		txtDescripcionObraServicio.setEnabled(true);
		txtareaMaterial.setEnabled(true);
		txtareaServicios.setEnabled(true);
		txtKilometros.setEnabled(true);
		employeeNifComboBox.setEnabled(true);
		clientNifComboBox.setEnabled(true);
		vehiculoComboBox.setEnabled(true);
		txtCodigoPostal.setEnabled(true);
		txtDireccion.setEnabled(true);
		textLocalidad.setEnabled(true);
		textProvincia.setEnabled(true);
		radiobuttonEnProceso.setEnabled(true);
		radiobuttonTerminado.setEnabled(true);
		botonAltaParte.setEnabled(true);
		newEmployeeButton.setEnabled(true);
		newClientButton.setEnabled(true);
		newMaterialButton.setEnabled(true);
		newServicetButton.setEnabled(true);
		newVehicleButton.setEnabled(true);
		txtCalendarFechaParte.setEnabled(true);
		
		eliminarItemsComboBoxEmpleado();
		eliminarItemsComboBoxCliente();
		eliminarItemsComboBoxVehiculo();
		añadirItemsComboBoxCliente();
		añadirItemsComboBoxVehiculo();
		añadirItemsComboBoxEmpleado();
		
	}
	
	
	public void añadirItemsComboBoxEmpleado() {
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
	}
	
	public void añadirItemsComboBoxCliente() {
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
	}
	
	public void añadirItemsComboBoxVehiculo() {
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
	}

	public void eliminarItemsComboBoxEmpleado() {
		employeeNifComboBox.removeAllItems();
	}
	
	public void eliminarItemsComboBoxCliente() {
		clientNifComboBox.removeAllItems();
	}
	
	public void eliminarItemsComboBoxVehiculo() {
		vehiculoComboBox.removeAllItems();
	}
	
	public boolean comprobarRellenoString(String texto){
		if(texto.isEmpty()) {
			return false;
		}
		else return true;
	}

}
