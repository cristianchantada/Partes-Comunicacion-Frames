package Clientes;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Mensajes.Mensaje;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import Validacion.*;

public class VentanaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtNIF;
	private JTextField txtNombre;
	private JTextField txtCorreo;
	private JTextField txtTelefono;

	/**
	 * Create the frame.
	 */
	public VentanaCliente() {
		
		setBounds(100, 100, 315, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtNIF = new JTextField();
		txtNIF.setBounds(88, 21, 158, 20);
		contentPane.add(txtNIF);
		txtNIF.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(88, 52, 158, 20);
		contentPane.add(txtNombre);
		
		txtCorreo = new JTextField();
		txtCorreo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!Validator.validateEmail(txtCorreo.getText())) {
					Mensaje.verMensaje("El correo no es correcto.\nPor favor escribe el correo correcto");
					txtCorreo.requestFocus();	
				}
					
			}
		});
		txtCorreo.setColumns(10);
		txtCorreo.setBounds(88, 85, 158, 20);
		contentPane.add(txtCorreo);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(88, 116, 158, 20);
		contentPane.add(txtTelefono);
		
		JButton btnInsertar = new JButton("Añadir");
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nif=txtNIF.getText(), 
					   nombre=txtNombre.getText(),
					   correo=txtCorreo.getText(), 
					   tfno=txtTelefono.getText();
				
				List <Cliente> listaClientes=new ArrayList<>();
				listaClientes=FicheroCliente.lClientes;
				//Cargo el cliente nuevo
				Cliente nuevoCliente=new Cliente(nif,nombre,
						                         correo,tfno);
				//Grabo el nuevo cliente
				listaClientes.add(nuevoCliente);	
				FicheroCliente.crearFichero(listaClientes);
				
				dispose();
			}
		});
		btnInsertar.setBounds(123, 147, 89, 23);
		contentPane.add(btnInsertar);
		
		JLabel lblnif = new JLabel("NIF");
		lblnif.setBounds(10, 24, 46, 14);
		contentPane.add(lblnif);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 55, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(10, 88, 46, 14);
		contentPane.add(lblCorreo);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(10, 119, 46, 14);
		contentPane.add(lblTelfono);
	}

	public void setNIF(String idNIF) {
		txtNIF.setText(idNIF);
	}

	public String getNif() {
		return this.txtNIF.getText();
	}

	public String getNombre() {
		return this.txtNombre.getText();
	}

	public String getEmail() {
		return this.txtCorreo.getText();
	}

	public String getTelefono() {
		return this.txtNIF.getText();
	}
}
