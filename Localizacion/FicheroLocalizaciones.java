package Localizacion;

import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;
import static Mensajes.Mensaje.verMensaje;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroLocalizaciones {
    
	private final static String RUTA_A_FICHERO_LOCALIZACIONES = ABSOLUTE_PATH_TO_MODEL_DIR + "localizaciones.dat";

	public static List<Localizacion> leerFichero() {
	    ArrayList<Localizacion> listaLocalizaciones = new ArrayList<>();
	    
	    File archivoLocalizaciones = new File(RUTA_A_FICHERO_LOCALIZACIONES);
	    
	    if (archivoLocalizaciones.length() == 0) {
	        System.out.println("El archivo de localizaciones está vacío.");
	        return listaLocalizaciones;
	    }
	    
	    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_LOCALIZACIONES))) {
	        while (true) {
	            try {
	            	listaLocalizaciones = (ArrayList<Localizacion>) miFichero.readObject();
	            } catch (EOFException e) {
	                break;
	            }
	        }
	    } catch (ClassNotFoundException e) {
	        System.out.println("Clase de objeto no encontrada: " + e.getMessage());
	        e.printStackTrace();
	    } catch (IOException e) {
	        System.out.println("Error de entrada/salida: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return listaLocalizaciones;
	}
	
	public static void grabarVehiculo(List<Localizacion> listaLocalizaciones) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_LOCALIZACIONES))) {
            outputStream.writeObject(listaLocalizaciones);
            verMensaje("Datos de localización guardados con éxito");         
        } catch (Exception e) {
            System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
        }
    }

}