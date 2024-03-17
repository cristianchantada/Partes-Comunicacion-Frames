package Material;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Interfaces.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaMaterial extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();
	private JButton btnAdd = new JButton("Add");
	private JButton btnGrabarYSalir = new JButton("Terminar Introducción");
	public JTextField inputMaterial;
	private JTextField inputUnidades;
	private JTextArea textareaMateriales;
	private JLabel noStringWarningLabel = new JLabel("No pueden existir caracteres alfabéticos, solo dígitos.");
	private JLabel messageIsEmptyLabel = new JLabel("El material no puede quedar vacío.");
	private JLabel unitsIsEmptyLabel = new JLabel("Tiene que establecer un número mínimo de unidades.");
	private List<Material> listaMateriales = new ArrayList<>();

	public VentanaMaterial(ComunicaDatos interfazDeComunicacion) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 636, 401);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		montarInputs();
		montarLabels();

		btnAdd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String unidadesStr;
				String materialName = inputMaterial.getText();

				if (inputMaterial.getText().isEmpty()) {
					messageIsEmptyLabel.setVisible(true);
				} else {
					messageIsEmptyLabel.setVisible(false);
				}

				try {
					unidadesStr = inputUnidades.getText();
					if (unidadesStr.isEmpty()) {
						unitsIsEmptyLabel.setVisible(true);
					} else {
						unitsIsEmptyLabel.setVisible(false);
					}

					noStringWarningLabel.setVisible(false);

					Material material = new Material(materialName, Integer.parseInt(unidadesStr));
					listaMateriales.add(material);
					textareaMateriales.append(material.getDesc() + ", Unidades: " + unidadesStr);
					inputMaterial.setText("");
					inputUnidades.setText("");

				} catch (NumberFormatException nfe) {
					System.out.println("Error en el parseo a entero de la cadena introducida:  " + nfe.getMessage());
					noStringWarningLabel.setVisible(true);
					return;
				}
				if (inputMaterial.getText().isEmpty() || unidadesStr.isEmpty()) {
					return;
				}
				noStringWarningLabel.setVisible(false);
			}
		});
		btnAdd.setBounds(414, 53, 89, 23);
		contentPane.add(btnAdd);

		btnGrabarYSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				interfazDeComunicacion.enviaDatosMaterial(listaMateriales);
				dispose();
			}
		});
		btnGrabarYSalir.setBounds(428, 289, 170, 44);
		contentPane.add(btnGrabarYSalir);
		setVisible(true);
	}

	private void montarInputs() {
		textareaMateriales = new JTextArea();
		textareaMateriales.setFocusable(false);
		textareaMateriales.setEditable(false);
		textareaMateriales.setBounds(39, 126, 559, 132);
		contentPane.add(textareaMateriales);

		inputMaterial = new JTextField();
		inputMaterial.setBounds(39, 49, 170, 30);
		contentPane.add(inputMaterial);
		inputMaterial.setColumns(10);

		inputUnidades = new JTextField();
		inputUnidades.setBounds(265, 49, 108, 30);
		contentPane.add(inputUnidades);
		inputUnidades.setColumns(10);
	}

	private void montarLabels() {

		noStringWarningLabel.setForeground(new Color(255, 0, 0));
		noStringWarningLabel.setBounds(265, 87, 333, 14);
		noStringWarningLabel.setVisible(false);
		contentPane.add(noStringWarningLabel);

		messageIsEmptyLabel.setForeground(new Color(255, 0, 0));
		messageIsEmptyLabel.setBounds(39, 87, 204, 14);
		messageIsEmptyLabel.setVisible(false);
		contentPane.add(messageIsEmptyLabel);

		unitsIsEmptyLabel.setForeground(new Color(255, 0, 0));
		unitsIsEmptyLabel.setBounds(265, 101, 333, 14);
		unitsIsEmptyLabel.setVisible(false);
		contentPane.add(unitsIsEmptyLabel);

		JLabel lblMaterial = new JLabel("Material");
		lblMaterial.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMaterial.setBounds(66, 11, 170, 27);
		contentPane.add(lblMaterial);

		JLabel lblUnidades = new JLabel("Unidades");
		lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnidades.setBounds(265, 14, 77, 21);
		contentPane.add(lblUnidades);
	}

}
