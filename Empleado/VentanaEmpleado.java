package Empleado;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Interfaces.ComunicaDatos;
import Main.PartesFinal;

import java.awt.event.*;
import static Mensajes.Mensaje.verMensaje;
import static Validacion.Validator.*;

public class VentanaEmpleado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnInsertar = new JButton("Añadir");
	public JTextField nifInput = new JTextField();
	private JTextField nameInput = new JTextField();
	private JTextField emailInput = new JTextField();
	private JTextField phoneInput = new JTextField();
	private JTextField codeInput = new JTextField();
	private Empleado empleado;
	private boolean isDataSave;

	public VentanaEmpleado(ComunicaDatos interfazComunicacion) {	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 303, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		montarLabels();
		montarInputs();
		
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nif=nifInput.getText().replaceAll("\\s", "").replaceAll("-", "")
						, name=nameInput.getText()
						, email=emailInput.getText()
						, phone=phoneInput.getText()
						, code=codeInput.getText();	
				
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
					verMensaje("El nombre del empleado no es válido, por favor, inténtelo de nuevo");
					checker = false;
					nameInput.setText("");
				}
				
				if(!validarCodigoOperario(code)) {
					verMensaje("El código del operario no es válido, inserte otro con formato OPNNNNN");
					checker = false;
					codeInput.setText("");
				}

				if (checker) {
					empleado = new Empleado(nif, name, phone, email, code);
					List<Empleado> listaEmpleados = new ArrayList<>();
					FicheroEmpleado ficheroEmpleado = new FicheroEmpleado();
					listaEmpleados = ficheroEmpleado.leerFicheroEmpleado();
					
					if(Empleado.empleadoExiste(nif, listaEmpleados)) {
						verMensaje("El empleado con NIF " + nif + " ya existe en la bases de datos" );
						nifInput.setText("");
						checker = false;
					}
					
					if(checker) {
						
						listaEmpleados.add(empleado);
						isDataSave = ficheroEmpleado.crearFichero(listaEmpleados);
						
						if(isDataSave) {
							verMensaje("El empleado " + empleado.getNombre() + " con NIF " + empleado.getNif() 
							+ " ha sido creado con éxito");
							PartesFinal partesFinal = new PartesFinal();
							partesFinal.setVisible(true);
						}						
						interfazComunicacion.enviaConfirmacionDatosGuardados(isDataSave);				
						dispose();
					}
				}
			}
		});
		btnInsertar.setBounds(158, 189, 89, 23);
		contentPane.add(btnInsertar);
		setVisible(true);
	}
	
	public void montarInputs() {
		
		nifInput.setBounds(90, 24, 158, 20);
		contentPane.add(nifInput);
		nifInput.setColumns(10);

		nameInput.setColumns(10);
		nameInput.setBounds(90, 55, 158, 20);
		contentPane.add(nameInput);
			
		emailInput.setColumns(10);
		emailInput.setBounds(90, 88, 158, 20);
		contentPane.add(emailInput);
		
		codeInput.setColumns(10);
		codeInput.setBounds(90, 150, 158, 20);
		contentPane.add(codeInput);
		
		phoneInput.setColumns(10);
		phoneInput.setBounds(90, 119, 158, 20);
		contentPane.add(phoneInput);
	}
	
	public void montarLabels() {
		JLabel lblnif = new JLabel("NIF");
		lblnif.setBounds(10, 24, 46, 14);
		contentPane.add(lblnif);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 55, 58, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCorreo = new JLabel("Correo");
		lblCorreo.setBounds(10, 88, 58, 14);
		contentPane.add(lblCorreo);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(10, 119, 70, 14);
		contentPane.add(lblTelfono);
		
		JLabel lbCodigo = new JLabel("Código");
		lbCodigo.setBounds(10, 150, 70, 14);
		contentPane.add(lbCodigo);
	}
	
}
