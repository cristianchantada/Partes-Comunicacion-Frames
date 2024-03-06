import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import com.toedter.calendar.JDateChooser;

import Clientes.*;
import Interfaces.ComunicaDatos;
import Material.Material;
import Mensajes.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;

public class Partes extends JFrame implements ComunicaDatos {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField txtNIF = new JTextField();;
	private JTextField txtObra = new JTextField();
	private JLabel lblFecha;
	private JLabel lblEmpleado;
	private JTextField txtNIFE = new JTextField();
	private JTextPane txtMaterial = new JTextPane();
	public JTextPane txtServicios = new JTextPane();

	// Datos de la aplicación
	public Cliente c;
	private JLabel lblMaterial;

	// Inicialización de los controles
	private JButton btnAltaParte = new JButton("Alta Parte");
	private JDateChooser dateFecha = new JDateChooser();

	// Control de flujo en la aplicación
	static boolean lanzarDatosEmpleado=false;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtDireccion;
	private JTextField txtCP;
	private JTextField textField_2;
	private JTextField textField_3;


	public void onSalirFrameMateriales(String materiales) {
		// JOptionPane.showMessageDialog(this, "Datos devueltos: " + materiales);
		txtMaterial.setText(materiales);
		Mensaje.verMensaje(materiales + " XXXXXXX");
	}

	public void onSalirFrameServicios(String servicios) {
		JOptionPane.showMessageDialog(this, "Datos devueltos: " + servicios);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Partes frame = new Partes();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	//Implementación de interfaces para el intercambio de datos
	public void enviaDatosServicios(String datos) {
		txtServicios.setText(datos);
	}
	
	public void enviaDatosMaterial(String datos) {
		txtMaterial.setText(datos);
	}
	
	/**
	 * Create the frame.
	 */
	public Partes() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 873, 600);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setOpaque(false);
		contentPane.setAlignmentY(0.1f);
		contentPane.setAlignmentX(0.1f);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		mostrarLineaCliente();
		mostrarLineaFecha();
		mostrarLineaObra();
		mostrarLineaEmpleado();
		mostrarLineaMaterial();
		mostrarLineaServicios();
		mostrarBotonLimpiar();
		mostrarBotonAltaParte();

		contentPane.repaint();

	}

	public static String pedirCliente() {
		String s = JOptionPane.showInputDialog("Introduce el cliente:");
		Mensaje.verMensaje(
				"Si el cliente existe paso al siguiente campo\n" + "si no existe hago que salte panel de alta cliente");

		return s;
	}

	private void mostrarLineaCliente() {
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 5, 46, 20);
		contentPane.add(lblCliente);

		txtNIF = new JTextField();
		txtNIF.setBounds(108, 5, 300, 20);
		contentPane.add(txtNIF);
		txtNIF.setColumns(10);
	}

	private void mostrarLineaMaterial() {
			lblMaterial = new JLabel("Materiales");
			lblMaterial.setBounds(10, 98, 64, 20);
			contentPane.add(lblMaterial);
			
			txtMaterial.setBorder(new LineBorder(new Color(0, 0, 0)));
			txtMaterial.setBounds(108, 98, 300, 119);
			contentPane.add(txtMaterial);
			
			Partes parteActual=this;

			txtMaterial.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
				  if (!lanzarDatosEmpleado) {
					if (txtMaterial.getText().isBlank()) {
					   //Creo el objeto material y le paso la clase (para uso de interface)
					   Material misMateriales = new Material(parteActual);
					   misMateriales.tMaterial.requestFocus();
					   lanzarDatosEmpleado=true;
				    } else {
				    	lanzarDatosEmpleado=false;
				    	Mensaje.verMensaje("No está en blanco");
				    }
				  } 
				  
				  //Mensaje.verMensaje("Lanzo ventana de pedir Material. L284");
				  //txtServicios.requestFocus();	
				}
			});
			

	}

	private void mostrarLineaEmpleado() {

		// Zona Empleado
		lblEmpleado = new JLabel("Empleado:");
		lblEmpleado.setBounds(10, 64, 64, 20);
		contentPane.add(lblEmpleado);

		txtNIFE = new JTextField();

		txtNIFE.setColumns(10);
		txtNIFE.setBounds(108, 64, 300, 20);
		contentPane.add(txtNIFE);

	}

	private void mostrarLineaFecha() {

		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(440, 8, 46, 14);
		contentPane.add(lblFecha);

		dateFecha.setFocusable(false);
		dateFecha.setBounds(485, 5, 152, 20);
		contentPane.add(dateFecha);
	}

	private void mostrarLineaObra() {
		// Colocar etiqueta
		JLabel lblObra = new JLabel("Obra/Servicio:");
		lblObra.setBounds(10, 34, 128, 20);
		contentPane.add(lblObra);

		txtObra.setAlignmentY(0.1f);
		txtObra.setAlignmentX(0.1f);

		// Campo Obra
		txtObra.setColumns(10);
		txtObra.setBounds(108, 33, 300, 20);
		contentPane.add(txtObra);
	}

	private void mostrarLineaServicios() {
		JLabel lblServicios = new JLabel("Servicios");
		lblServicios.setBounds(10, 228, 64, 20);
		contentPane.add(lblServicios);
		

		txtServicios.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtServicios.setBounds(108, 228, 300, 119);
		contentPane.add(txtServicios);
	}
	private void mostrarBotonLimpiar() {
		JButton btnLimpiarParte = new JButton("Limpiar");
		btnLimpiarParte.setBounds(108, 520, 90, 30);
		contentPane.add(btnLimpiarParte);

		btnLimpiarParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Component[] componentes = contentPane.getComponents();
				for (Component componente : componentes) {
					if (componente instanceof JTextField) {
						((JTextField) componente).setText(""); 
						// Establecemos el texto en blanco para JTextField
					} else if (componente instanceof JComboBox) {
						((JComboBox<?>) componente).setSelectedIndex(0);
					} else if (componente instanceof JTextPane) {
						((JTextPane)componente).setText("");
					}
				}
			}
		});
		
		//Colocar todo como al principio
		lanzarDatosEmpleado=false;
	}

	private void mostrarBotonAltaParte() {
		// Botón alta parte
		btnAltaParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Mensaje.verMensaje("Grabar Parte");
			}
		});
		btnAltaParte.setBounds(757, 520, 90, 30);
		contentPane.add(btnAltaParte);
		
		JLabel lblVehiculo = new JLabel("Vehículo:");
		lblVehiculo.setBounds(10, 374, 64, 20);
		contentPane.add(lblVehiculo);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(108, 374, 100, 20);
		contentPane.add(textField);
		
		JLabel lblKm = new JLabel("Km realizados:");
		lblKm.setBounds(225, 374, 120, 20);
		contentPane.add(lblKm);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(328, 374, 80, 20);
		contentPane.add(textField_1);
		
		JLabel lbrObservaciones = new JLabel("Observaciones:");
		lbrObservaciones.setBounds(10, 420, 100, 14);
		contentPane.add(lbrObservaciones);
		
		JTextPane textPane = new JTextPane();
		textPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		textPane.setBounds(108, 420, 300, 50);
		contentPane.add(textPane);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(108, 486, 46, 14);
		contentPane.add(lblEstado);
		
		//Botones del estado parte
		ButtonGroup estadoParte=new ButtonGroup();
		
		JRadioButton rdbtnEstado = new JRadioButton("En proceso");
		rdbtnEstado.setBounds(160, 482, 109, 23);
		contentPane.add(rdbtnEstado);
		
		JRadioButton rdbtnTerminado = new JRadioButton("Terminado");
		rdbtnTerminado.setBounds(271, 482, 109, 23);
		contentPane.add(rdbtnTerminado);
		
		estadoParte.add(rdbtnEstado);
		estadoParte.add(rdbtnTerminado);
		
		JLabel lblLocalización = new JLabel("Lugar/Sitio:");
		lblLocalización.setBounds(440, 67, 109, 14);
		contentPane.add(lblLocalización);
		
		JLabel label = new JLabel("New label");
		label.setBounds(430, 55, 35, -1);
		contentPane.add(label);
		
		JLabel lblDireccion = new JLabel("Dirección:");
		lblDireccion.setAlignmentY(0.0f);
		lblDireccion.setBounds(485, 101, 64, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblCp = new JLabel("CP:");
		lblCp.setAlignmentY(0.0f);
		lblCp.setBounds(485, 130, 64, 14);
		contentPane.add(lblCp);
		
		JLabel lblLocalidad = new JLabel("Localidad:");
		lblLocalidad.setAlignmentY(0.0f);
		lblLocalidad.setBounds(485, 159, 64, 14);
		contentPane.add(lblLocalidad);
		
		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setAlignmentY(0.0f);
		lblProvincia.setBounds(485, 184, 64, 14);
		contentPane.add(lblProvincia);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setAlignmentY(0.1f);
		txtDireccion.setAlignmentX(0.1f);
		txtDireccion.setBounds(547, 98, 300, 20);
		contentPane.add(txtDireccion);
		
		txtCP = new JTextField();
		txtCP.setColumns(10);
		txtCP.setAlignmentY(0.1f);
		txtCP.setAlignmentX(0.1f);
		txtCP.setBounds(547, 126, 72, 20);
		contentPane.add(txtCP);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setAlignmentY(0.1f);
		textField_2.setAlignmentX(0.1f);
		textField_2.setBounds(547, 155, 229, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setAlignmentY(0.1f);
		textField_3.setAlignmentX(0.1f);
		textField_3.setBounds(547, 184, 211, 20);
		contentPane.add(textField_3);

	}
}
