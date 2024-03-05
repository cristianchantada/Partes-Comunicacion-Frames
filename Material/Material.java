package Material;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Interfaces.*;
import Mensajes.Mensaje;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Material extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField tMaterial;
	private JTextField tUnidades;
	private JTextArea textArea;
	private static ArrayList<String> desc = new ArrayList<>();
	private static ArrayList<Integer> ctdad = new ArrayList<>();
	JLabel noStringAdviseLabel;

	private static final String RUTA = new File("").getAbsolutePath() + "\\src\\";
	private JLabel messageIsEmptyLabel;
	private JLabel unitsIsEmptyLabel;

	public void onSalirFrameMateriales(String materiales) {
		JOptionPane.showMessageDialog(this, "Datos devueltos: " + materiales);
	}

	public void onSalirFrameServicios(String servicios) {
		JOptionPane.showMessageDialog(this, "Datos devueltos: " + servicios);
	}

	// Creo el objeto que recibe el objeto de intercambio de datos
	public Material(ComunicaDatos frameMaterial) {

		setBounds(100, 100, 636, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String material = tMaterial.getText();
				desc.add(material);
				
				if(tMaterial.getText().isEmpty()) {
					messageIsEmptyLabel.setVisible(true);
					return;
				} else {
					messageIsEmptyLabel.setVisible(false);
				}
				
				try {
					String unidadesStr = tUnidades.getText();
					
					if(unidadesStr.isEmpty()) {
						unitsIsEmptyLabel.setVisible(true);
						return;
					} else {
						unitsIsEmptyLabel.setVisible(false);
					}

					ctdad.add(Integer.parseInt(unidadesStr));
					noStringAdviseLabel.setVisible(false);
					textArea.append(material + ", Unidades: " + unidadesStr + "\n");
					tMaterial.setText("");
					tUnidades.setText("");
				} catch (NumberFormatException nfe) {
					System.out.println("Error en el parseo a entero de la cadena introducida:  " + nfe.getMessage());
					noStringAdviseLabel.setVisible(true);
				}
			}
		});
		btnAdd.setBounds(414, 53, 89, 23);
		contentPane.add(btnAdd);

		tMaterial = new JTextField();
		tMaterial.setBounds(66, 49, 170, 30);
		contentPane.add(tMaterial);
		tMaterial.setColumns(10);

		tUnidades = new JTextField();
		tUnidades.setBounds(265, 49, 108, 30);
		contentPane.add(tUnidades);
		tUnidades.setColumns(10);

		JLabel lblMaterial = new JLabel("Material");
		lblMaterial.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMaterial.setBounds(66, 11, 170, 27);
		contentPane.add(lblMaterial);

		JLabel lblUnidades = new JLabel("Unidades");
		lblUnidades.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUnidades.setBounds(265, 14, 77, 21);
		contentPane.add(lblUnidades);

		JButton btnGrabarYSalir = new JButton("Terminar Introducción");
		btnGrabarYSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// guardarDatos();
				// escribirEnArchivoBinario();
				// onSalirFrameMateriales((String) desc.toString() + "-" + ctdad.toString());

				// Utilizo el objeto de interface frameMaterial
				frameMaterial.enviaDatosMaterial(textArea.getText());
				
				dispose();
				

			
			}
		});
		btnGrabarYSalir.setBounds(433, 212, 144, 44);
		contentPane.add(btnGrabarYSalir);

		textArea = new JTextArea();
		textArea.setFocusable(false);
		textArea.setEditable(false);
		textArea.setBounds(58, 124, 342, 132);
		contentPane.add(textArea);

		noStringAdviseLabel = new JLabel("Debe introducir un número, no letras");
		noStringAdviseLabel.setForeground(new Color(255, 0, 0));
		noStringAdviseLabel.setBounds(265, 90, 238, 14);
		noStringAdviseLabel.setVisible(false);
		contentPane.add(noStringAdviseLabel);
		
		messageIsEmptyLabel = new JLabel("El material no puede quedar vacío");
		messageIsEmptyLabel.setForeground(new Color(255, 0, 0));
		messageIsEmptyLabel.setBounds(66, 90, 204, 14);
		messageIsEmptyLabel.setVisible(false);
		contentPane.add(messageIsEmptyLabel);
		
		unitsIsEmptyLabel = new JLabel("Tiene que establecer un número de unidades");
		unitsIsEmptyLabel.setForeground(new Color(255, 0, 0));
		unitsIsEmptyLabel.setBounds(265, 102, 259, 14);
		unitsIsEmptyLabel.setVisible(false);
		contentPane.add(unitsIsEmptyLabel);

		setVisible(true);
	}

	static void cargarDatos() {
		try (BufferedReader reader = new BufferedReader(new FileReader(RUTA + "Materiales(Datos).txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				desc.add(parts[0]);
				ctdad.add(Integer.parseInt(parts[1]));
			}
		} catch (Exception e) {

		}
	}

	static void guardarDatos() {
		try (FileWriter writer = new FileWriter(RUTA + "Materiales(Datos).txt", false)) {
			for (int i = 0; i < desc.size(); i++) {
				writer.write(desc.get(i) + "," + ctdad.get(i) + "\n");
			}
		} catch (Exception e) {

		}
	}

	static void escribirEnArchivoBinario() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(
				new FileOutputStream(RUTA + "materiales(frame).dat", false))) {
			outputStream.writeObject(desc);
			outputStream.writeObject(ctdad);
		} catch (Exception e) {
			System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
		}
	}
}