package VistoBueno;

import javax.swing.border.EmptyBorder;
import Interfaces.ComunicaDatos;
import Vehiculo.FicheroVehiculos;
import Vehiculo.Vehiculo;

import static Mensajes.Mensaje.verMensaje;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaVistoBueno extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JTextField textNombreResponsable = new JTextField();
	private JButton signButton = new JButton("Firmar parte");
	private JComboBox<String> comboBox = new JComboBox<>();
	private JTextArea textAreaObservaciones = new JTextArea();
	List<String> datosVistoBuenoList = new ArrayList<>();

	public VentanaVistoBueno(ComunicaDatos interfazComunicadora) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 573, 338);		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		montarLabels();
		
		signButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				datosVistoBuenoList.add((String) comboBox.getSelectedItem());
				datosVistoBuenoList.add(textNombreResponsable.getText());
				datosVistoBuenoList.add(textAreaObservaciones.getText());
				interfazComunicadora.enviaDatosVistoBueno(datosVistoBuenoList);
				dispose();
				verMensaje("Parte firmado correctamente");
			}
		});

		
		signButton.setBounds(276, 248, 198, 37);
		contentPane.add(signButton);
			
		textAreaObservaciones.setBounds(160, 81, 314, 156);
		contentPane.add(textAreaObservaciones);
				
		comboBox.setBounds(160, 14, 314, 22);
		
		/*
		 * Simulación datos de administradores
		 */

		contentPane.add(comboBox);

		comboBox.addItem("--Escoja un responsable administrador de la lista--");
		comboBox.addItem(" AD1234");
		comboBox.addItem(" AD5678");
		comboBox.addItem(" AD9098");

		comboBox.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() != 0) {
					if(comboBox.getSelectedItem() == " AD1234") {
						textNombreResponsable.setText("Pepe Botella");
					} else if(comboBox.getSelectedItem() == " AD5678") {
						textNombreResponsable.setText("Pancracio Menéndez");
					} else {
						textNombreResponsable.setText("Julita del Monte");
					}
				}
			}
		});
		
		textNombreResponsable.setBounds(162, 50, 171, 20);
		textNombreResponsable.setEnabled(false);
		contentPane.add(textNombreResponsable);
		textNombreResponsable.setColumns(10);
	}
	
	public void montarLabels() {
		JLabel responsableLabel = new JLabel("Código del responsable:");
		responsableLabel.setBounds(10, 11, 171, 28);
		contentPane.add(responsableLabel);
		
		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setBounds(10, 46, 127, 28);
		contentPane.add(nombreLabel);
		
		JLabel bservacionesLabel = new JLabel("Observaciones:");
		bservacionesLabel.setBounds(10, 79, 127, 28);
		contentPane.add(bservacionesLabel);
	}
	
	
}
