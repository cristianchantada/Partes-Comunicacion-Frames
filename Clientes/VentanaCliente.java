package Clientes;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static Mensajes.Mensaje.verMensaje;
import java.awt.event.*;
import static Validacion.Validator.*;

public class VentanaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JButton createClientButton = new JButton("Crear nuevo cliente");
	private JTextField emailInput = new JTextField();
	private JTextField nifInput = new JTextField();
	private JTextField nameInput = new JTextField();
	private JTextField phoneInput = new JTextField();
	private Cliente cliente;

	public VentanaCliente(Cliente c) {
		
		cliente = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 329);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		montarLabels();
		montarInputs();
		
		createClientButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pulsarBoton();
			}
		});
		createClientButton.setBounds(195, 180, 139, 23);
		contentPane.add(createClientButton);
		setVisible(true);
	}
	
	public void setNifInInput(String nif) {
		nifInput.setText(nif);
		nifInput.setEnabled(false);
	}

	public void pulsarBoton() {
		String nif = nifInput.getText().replaceAll("\\s", "").replaceAll("-", "");
		String name = nameInput.getText();
		String email = emailInput.getText();
		String phone = phoneInput.getText();
		
		boolean checker = true;
		if(!nifValidator(nif)) {
			verMensaje("El NIF no es correcto, por favor, buelva a intentarlo");
			nifInput.setText("");
			checker = false;
		}
		
		if(!emailValidator(email)){
			verMensaje("El formato de email no es válido, por favor, vuelva a introducirlo");
			emailInput.setText("");
			checker = false;
		}

		if(!phoneValidator(phone)) {
			verMensaje("El número de teléfono no es válido, por favor, vuelva a introducirlo");			
			checker = false;
			phoneInput.setText("");
		}

		if(!validarNombre(name)) {
			verMensaje("El nombre del cliente no es válido, por favor, inténtelo de nuevo");
			checker = false;
			nameInput.setText("");
		}

		if (checker) {
			cliente = new Cliente(nif, name, phone, email);
			List<Cliente> listaClientes = new ArrayList<>();
			listaClientes = FicheroCliente.leerFichero();
			listaClientes.add(cliente);
			FicheroCliente.crearFichero(listaClientes);
			verMensaje(cliente.toString());
			dispose();
		}
	}
	
	public void montarInputs() {
		nifInput.setBounds(193, 29, 203, 20);
		nifInput.setColumns(10);
		contentPane.add(nifInput);
		
		nameInput.setBounds(193, 60, 203, 20);
		nameInput.setColumns(10);
		contentPane.add(nameInput);

		phoneInput.setBounds(193, 91, 203, 20);
		phoneInput.setColumns(10);
		contentPane.add(phoneInput);

		emailInput.setBounds(193, 122, 203, 20);
		contentPane.add(emailInput);
		emailInput.setColumns(10);
	}
	
	private void montarLabels() {
		JLabel nameLabel = new JLabel("Nombre");
		nameLabel.setBounds(33, 63, 123, 20);
		contentPane.add(nameLabel);
		
		JLabel nifLabel = new JLabel("NIF nuevo cliente");
		nifLabel.setBounds(33, 29, 150, 20);
		contentPane.add(nifLabel);

		JLabel phoneLabel = new JLabel("Teléfono");
		phoneLabel.setBounds(33, 94, 123, 20);
		contentPane.add(phoneLabel);

		JLabel emailLabel = new JLabel("Correo electrónico");
		emailLabel.setBounds(33, 125, 123, 20);
		contentPane.add(emailLabel);
	}
}
