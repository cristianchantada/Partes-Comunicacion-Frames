package Vehiculo;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static Vehiculo.FicheroVehiculos.*;
import static Validacion.Validator.*;
import static Mensajes.Mensaje.verMensaje;

public class VentanaVehiculo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JTextField txtMatricula = new JTextField();
	private JTextField txtMarca = new JTextField();
	private JTextField txtModelo = new JTextField();
	private JButton btnNewButton = new JButton("Enviar Datos");
	private Vehiculo vehiculo;

	public VentanaVehiculo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		montarInputs();
		montarLabels();

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String matricula = txtMatricula.getText();
				String marca = txtMarca.getText();
				String modelo = txtModelo.getText();
				
				if(!validarMatricula(matricula)) {
					verMensaje("El formato de la matrícula no es correcto o contiene letras no válidas");
					txtMatricula.setText("");
					return;				
				}
				
				if(matricula.isEmpty()) {
					verMensaje("El campo matrícula no puede quedar vacío");
					return;
				}
				
				if(marca.isEmpty()) {
					verMensaje("El campo marca no puede quedar vacío");
					return;
				}
				
				if(modelo.isEmpty()) {
					verMensaje("El campo modelo no puede quedar vacío");
					return;
				}
				
				List<Vehiculo> listaVehiculos = new ArrayList<>();
				listaVehiculos = leerFichero();
				vehiculo = new Vehiculo(matricula, marca, modelo);
				listaVehiculos.add(vehiculo);
				grabarVehiculo(listaVehiculos);
				verMensaje(vehiculo.toString());
				dispose();
			}
		});
		btnNewButton.setBounds(10, 211, 414, 23);
		contentPane.add(btnNewButton);
	}

	private void montarInputs() {
		txtMatricula.setBounds(77, 69, 86, 20);
		contentPane.add(txtMatricula);
		txtMatricula.setColumns(10);

		txtMarca.setColumns(10);
		txtMarca.setBounds(77, 110, 86, 20);
		contentPane.add(txtMarca);

		txtModelo.setColumns(10);
		txtModelo.setBounds(77, 157, 86, 20);
		contentPane.add(txtModelo);
	}

	private void montarLabels() {
		JLabel lblVehiculos2 = new JLabel("Inserte los siguientes datos:");
		lblVehiculos2.setBounds(21, 38, 150, 20);
		contentPane.add(lblVehiculos2);

		JLabel lblVehiculos = new JLabel("Vehículos");
		lblVehiculos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVehiculos.setBounds(21, 13, 117, 20);
		contentPane.add(lblVehiculos);

		JLabel lblMatricula = new JLabel("Matrícula:");
		lblMatricula.setBounds(21, 72, 67, 14);
		contentPane.add(lblMatricula);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(21, 113, 67, 14);
		contentPane.add(lblMarca);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(21, 160, 67, 14);
		contentPane.add(lblModelo);
	}
}
