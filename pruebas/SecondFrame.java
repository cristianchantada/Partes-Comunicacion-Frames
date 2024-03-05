package pruebas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Clase JFrame 2
class SecondFrame extends JFrame {
 JTextField textField;
 
 private final JButton sendButton = new JButton("Send to Parent Frame");

 SecondFrame(FrameCommunication communication) {
     super("Second Frame");
     textField = new JTextField(20);

     JPanel panel = new JPanel();
     panel.add(textField);

     

     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     getContentPane().add(panel);
     sendButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             // Llamar al método sendData() de la interfaz para enviar datos al segundo JFrame
             communication.sendData(textField.getText());
         }
     });
     panel.add(sendButton);
     pack();
     setLocationRelativeTo(null);
     setVisible(true);
 }

 // Método para recibir datos del primer JFrame
 public void receiveData(String data) {
     textField.setText(data);
 }
}