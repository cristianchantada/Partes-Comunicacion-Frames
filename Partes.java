import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	static boolean lanzarDatosEmpleado = false;

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

	// Implementación de interfaces para el intercambio de datos
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
		setBounds(10, 10, 656, 600);
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
		txtMaterial.setBounds(108, 98, 500, 119);
		contentPane.add(txtMaterial);

		Partes parteActual = this;

		txtMaterial.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!lanzarDatosEmpleado) {
					if (txtMaterial.getText().isBlank()) {
						// Creo el objeto material y le paso la clase (para uso de interface)
						Material misMateriales = new Material(parteActual);
						misMateriales.tMaterial.requestFocus();
						lanzarDatosEmpleado = true;
					} else {
						lanzarDatosEmpleado = false;
						Mensaje.verMensaje("No está en blanco");
					}
				}

				// Mensaje.verMensaje("Lanzo ventana de pedir Material. L284");
				// txtServicios.requestFocus();
			}
		});
	}

	private void mostrarLineaServicios() {
		JLabel lblServicios = new JLabel("Servicios");
		lblServicios.setBounds(10, 228, 64, 20);
		contentPane.add(lblServicios);
		txtServicios.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtServicios.setBounds(108, 228, 500, 119);
		contentPane.add(txtServicios);

		Partes parteActual = this;

		txtServicios.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!lanzarDatosEmpleado) {
					if (txtMaterial.getText().isBlank()) {
						// Creo el objeto material y le paso la clase (para uso de interface)
						FicheroServicio misServicios = new FicheroServicio(parteActual);
						misServicios.txtServicio.requestFocus();
						lanzarDatosEmpleado = true;
					} else {
						lanzarDatosEmpleado = false;
						Mensaje.verMensaje("No está en blanco");
					}
				}

				// Mensaje.verMensaje("Lanzo ventana de pedir Material. L284");
				// txtServicios.requestFocus();
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
		lblFecha.setBounds(430, 8, 46, 14);
		contentPane.add(lblFecha);

		dateFecha.setFocusable(false);
		dateFecha.setBounds(475, 5, 152, 20);
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

	private void mostrarBotonLimpiar() {
		JButton btnLimpiarParte = new JButton("Limpiar");
		btnLimpiarParte.setBounds(10, 524, 90, 30);
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
						((JTextPane) componente).setText("");
					}
				}
			}
		});

		// Colocar todo como al principio
		lanzarDatosEmpleado = false;
	}

	private void mostrarBotonAltaParte() {
		// Botón alta parte
		btnAltaParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Mensaje.verMensaje("Grabar Parte");
			}
		});
		btnAltaParte.setBounds(537, 520, 90, 30);
		contentPane.add(btnAltaParte);

	}
}
