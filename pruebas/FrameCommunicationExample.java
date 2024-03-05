package pruebas;

import javax.swing.SwingUtilities;

public class FrameCommunicationExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FirstFrame();
            }
        });
    }
}