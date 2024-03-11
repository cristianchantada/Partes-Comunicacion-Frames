package Imagenes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import Mensajes.Mensaje;

public class Imagen {
    String imagen;  
	public static void iconoInsertar(JButton boton) {
		InputStream inputStream = Imagen.class.getResourceAsStream("../recursos/insertar.png");
        try {
		  BufferedImage imagen = ImageIO.read(inputStream);
		  boton.setIcon(new ImageIcon(imagen));
        } catch (IOException e) {
        	Mensaje.verMensaje("No se da cargado la imagen");
        }
	}
}
