import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.SwingConstants;
import com.toedter.components.JLocaleChooser;

import Interfaces.ComunicaDatos;

import com.toedter.calendar.JDateChooser;

public class FicheroServicio extends JFrame implements Serializable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private  List<String> he = new ArrayList<>();;
	private List<String> hs = new ArrayList<>();;
    public JTextField txtServicio;
    private JTextArea textArea;
    private static List<String> desc = new ArrayList<>();
    private static List<Date> fecha = new ArrayList<>();
    private static final String RUTA = new File("").getAbsolutePath() + "\\src\\";
    JDateChooser txtFin;
    JDateChooser txtInicio;
    private String servicio;
    private String inicio;
    private String fin;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public FicheroServicio(ComunicaDatos frameServicios) {
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 720, 319);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	        setContentPane(contentPane);
	        contentPane.setLayout(null);

	        JButton btnAdd = new JButton("Añadir");
	        btnAdd.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                servicio = txtServicio.getText();
	                
	                
	                
	                
	                
	                inicio = txtInicio.getDate().toString();
	                fin = txtFin.getDate().toString();
	                if (desc.contains(servicio)&& he.contains(inicio)&& hs.contains(fin)){
	                    desc.add(servicio);
	                    he.add(inicio);
	                    hs.add(fin);
	                    fecha.add(new Date());
	                }
	                textArea.append(servicio + "\t" + inicio +"\t" + fin + "\n");
	                txtServicio.setText("");
	                txtInicio.setDate(null);;
	                txtFin.setDate(null);
	            }
	        });
	        btnAdd.setBounds(565, 49, 108, 30);
	        contentPane.add(btnAdd);

	        txtServicio = new JTextField();
	        txtServicio.setBounds(66, 49, 170, 30);
	        contentPane.add(txtServicio);
	        txtServicio.setColumns(10);

	        JLabel lblDesc = new JLabel("Servicio");
	        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
	        lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblDesc.setBounds(66, 11, 170, 27);
	        contentPane.add(lblDesc);

	        JLabel lblTE = new JLabel("Inicio");
	        lblTE.setHorizontalAlignment(SwingConstants.CENTER);
	        lblTE.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblTE.setBounds(265, 14, 108, 21);
	        contentPane.add(lblTE);

	        JButton btnSave = new JButton("Guardar");
	        btnSave.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	
	            	frameServicios.enviaDatosServicios(
	            			"Servicio: " + servicio 
	            			+ ", fecha inicio: " + inicio 
	            			+ ", fecha fin: " + fin);
	            	
	            	
	            	guardarServicio();
	            	textArea.setText("");
	            	dispose();
	            }
	        });
	        btnSave.setBounds(545, 212, 119, 44);
	        contentPane.add(btnSave);

	        textArea = new JTextArea();
	        textArea.setBounds(58, 124, 453, 132);
	        textArea.setEditable(false);
	        contentPane.add(textArea);
	        
	        JLabel lblFin = new JLabel("Fin");
	        lblFin.setHorizontalAlignment(SwingConstants.CENTER);
	        lblFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblFin.setBounds(403, 14, 108, 21);
	        contentPane.add(lblFin);
	        
	        txtFin = new JDateChooser();
	        txtFin.setBounds(403, 49, 108, 30);
	        contentPane.add(txtFin);
	        
	        txtInicio = new JDateChooser();
	        txtInicio.setBounds(265, 49, 108, 30);
	        contentPane.add(txtInicio);
	        setVisible(true);
	    }
	private void guardarServicio() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(RUTA + "Servicios.dat", true))) {
            outputStream.writeObject(desc);
            outputStream.writeObject(he);
            outputStream.writeObject(hs);
            outputStream.writeObject(fecha);
        } catch (Exception e) {
            System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
        }
	}
}