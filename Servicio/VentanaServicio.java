package Servicio;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.border.EmptyBorder;
import Interfaces.ComunicaDatos;
import com.toedter.calendar.JDateChooser;

public class VentanaServicio extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane = new JPanel();
	public JTextField txtServicio;
	private JTextArea textArea;
	private JButton botonGuardarServicio = new JButton("Guardar");
	private JButton botonAnhadir = new JButton("Añadir");
	private JDateChooser txtFin;
	private JDateChooser txtInicio;
	private JLabel noServiceEmptyWarningLabel;
	private JLabel noInitDateWarningLabel;
	private JLabel noEndDateWarningLabel;
	private Servicio servicio;
	private String servicioName;
	private String inicio;
	private String fin;
	private List<Servicio> listaServicios = new ArrayList<>();
	
	public VentanaServicio(ComunicaDatos frameServicios) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 319);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		montarLabels();
		montarInputs();
		
		botonAnhadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				servicioName = txtServicio.getText();

				if (servicioName.isEmpty()) {
					noServiceEmptyWarningLabel.setVisible(true);

				} else {
					noServiceEmptyWarningLabel.setVisible(false);
				}

				if (txtInicio.getDate() == null) {
					noInitDateWarningLabel.setVisible(true);

				} else {
					noInitDateWarningLabel.setVisible(false);
					inicio = txtInicio.getDate().toString();
				}

				if (txtFin.getDate() == null) {
					noEndDateWarningLabel.setVisible(true);

				} else {
					noEndDateWarningLabel.setVisible(false);
					fin = txtFin.getDate().toString();
				}

				if (servicioName.isEmpty() || txtInicio.getDate() == null || txtFin.getDate() == null) {
					return;
				}
				
				servicio = new Servicio(servicioName, inicio, fin, new Date());
				
				textArea.append("SERVICIO: " + servicio + "|  FECHA INICIO: " + inicio + "|  FECHA FIN: " + fin + "\n");
				txtServicio.setText("");
				txtInicio.setDate(null);
				txtFin.setDate(null);
			}
		});
		
		botonAnhadir.setBounds(586, 49, 108, 30);
		contentPane.add(botonAnhadir);

		botonGuardarServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameServicios.enviaDatosServicios(listaServicios);
				textArea.setText("");
				dispose();
			}
		});
		
		botonGuardarServicio.setBounds(606, 212, 88, 44);
		contentPane.add(botonGuardarServicio);
		setVisible(true);
	}
	
	
	public void montarInputs() {
		txtServicio = new JTextField();
		txtServicio.setBounds(58, 49, 170, 30);
		txtServicio.setColumns(10);
		contentPane.add(txtServicio);
		
		textArea = new JTextArea();
		textArea.setBounds(58, 124, 518, 132);
		textArea.setEditable(false);
		contentPane.add(textArea);

		txtFin = new JDateChooser();
		txtFin.setBounds(468, 49, 108, 30);
		contentPane.add(txtFin);

		txtInicio = new JDateChooser();
		txtInicio.setBounds(310, 49, 108, 30);
		contentPane.add(txtInicio);
	}
	
	public void montarLabels() {
		JLabel labelDescripcion = new JLabel("Servicio");
		labelDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		labelDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelDescripcion.setBounds(66, 11, 170, 27);
		contentPane.add(labelDescripcion);

		JLabel labelHoraInicio = new JLabel("Inicio");
		labelHoraInicio.setHorizontalAlignment(SwingConstants.CENTER);
		labelHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelHoraInicio.setBounds(265, 14, 108, 21);
		contentPane.add(labelHoraInicio);
		
		JLabel labelHoraFin = new JLabel("Fin");
		labelHoraFin.setHorizontalAlignment(SwingConstants.CENTER);
		labelHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelHoraFin.setBounds(403, 14, 108, 21);
		contentPane.add(labelHoraFin);
		
		noServiceEmptyWarningLabel = new JLabel("La descripción del servicio no puede quedar vacía");
		noServiceEmptyWarningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noServiceEmptyWarningLabel.setForeground(new Color(255, 0, 0));
		noServiceEmptyWarningLabel.setBounds(10, 82, 310, 14);
		noServiceEmptyWarningLabel.setVisible(false);
		contentPane.add(noServiceEmptyWarningLabel);

		noInitDateWarningLabel = new JLabel("Debe introducir una fecha de inicio válida");
		noInitDateWarningLabel.setForeground(new Color(255, 0, 0));
		noInitDateWarningLabel.setBounds(287, 99, 325, 14);
		noInitDateWarningLabel.setVisible(false);
		contentPane.add(noInitDateWarningLabel);

		noEndDateWarningLabel = new JLabel("Debe introducir una fecha de fin válida");
		noEndDateWarningLabel.setForeground(new Color(255, 0, 0));
		noEndDateWarningLabel.setBounds(468, 82, 226, 14);
		noEndDateWarningLabel.setVisible(false);
		contentPane.add(noEndDateWarningLabel);
	}

}
