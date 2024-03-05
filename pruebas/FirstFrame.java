package pruebas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Definición de la interfaz
interface FrameCommunication {
    void sendData(String data);
}

// Clase JFrame 1
class FirstFrame extends JFrame implements FrameCommunication {
    JTextField textField;
    SecondFrame secondFrame = new SecondFrame(this);

    FirstFrame() {
        super("First Frame");
        textField = new JTextField(20);
        JButton sendButton = new JButton("Send to Second Frame");

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = textField.getText();
                // Llamar al método sendData() de la interfaz para enviar datos al segundo JFrame
                sendToSecondFrame(data);
            }
        });

        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(sendButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Implementación del método de la interfaz
    public void sendData(String data) {
        textField.setText(data);
    }

    // Método para enviar datos al segundo JFrame
    private void sendToSecondFrame(String data) {
        
        secondFrame.receiveData(data);
    }
}



