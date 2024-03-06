package Vehiculo;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VehiculosWB extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtMatricula;
    private JTextField txtMarca;
    private JTextField txtModelo;

    private List<Vehiculo> vehiculosLista;  

    public VehiculosWB() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblVehiculos2 = new JLabel("Inserte los siguientes datos:");
        lblVehiculos2.setBounds(21, 38, 150, 20);
        contentPane.add(lblVehiculos2);

        JLabel lblVehiculos = new JLabel("Vehículos");
        lblVehiculos.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblVehiculos.setBounds(21, 13, 117, 20);
        contentPane.add(lblVehiculos);

        txtMatricula = new JTextField();
        txtMatricula.setBounds(77, 69, 86, 20);
        contentPane.add(txtMatricula);
        txtMatricula.setColumns(10);

        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setBounds(21, 72, 67, 14);
        contentPane.add(lblMatricula);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(21, 113, 67, 14);
        contentPane.add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setColumns(10);
        txtMarca.setBounds(77, 110, 86, 20);
        contentPane.add(txtMarca);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(21, 160, 67, 14);
        contentPane.add(lblModelo);

        txtModelo = new JTextField();
        txtModelo.setColumns(10);
        txtModelo.setBounds(77, 157, 86, 20);
        contentPane.add(txtModelo);

        JButton btnNewButton = new JButton("Enviar Datos");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarServicio();
            }
        });
        btnNewButton.setBounds(10, 211, 414, 23);
        contentPane.add(btnNewButton);

        vehiculosLista = new ArrayList<>();
    }

    private void guardarServicio() {
        String matricula = txtMatricula.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();

        if (matricula == null || marca == null || modelo == null) {
            JOptionPane.showMessageDialog(null, "Error: Los campos no pueden ser nulos.");
            return;
        }

        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Los campos no pueden estar vacíos.");
            return;
        }

        Vehiculo vehiculo = new Vehiculo(matricula, marca, modelo);

        vehiculosLista.add(vehiculo);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Vehiculos.dat", true))) {
            outputStream.writeObject(vehiculosLista);
            JOptionPane.showMessageDialog(null, "Datos guardados con éxito");
            dispose();
        } catch (Exception e) {
            System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
        }
    }
}

