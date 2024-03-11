package Servicio;

import java.io.*;
import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;
import java.util.ArrayList;
import java.util.List;

public class FicheroServicio {
	
	private final static String RUTA_A_FICHERO_SERVICIO = ABSOLUTE_PATH_TO_MODEL_DIR + "servicios.dat";
	
	public static List<Servicio> leerFichero() {
	    ArrayList<Servicio> listaServicios = new ArrayList<>();
	    
	    File archivoServicios = new File(RUTA_A_FICHERO_SERVICIO);
	    
	    if (archivoServicios.length() == 0) {
	        System.out.println("El archivo de servicios está vacío.");
	        return listaServicios;
	    }
	    
	    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_SERVICIO))) {
	        while (true) {
	            try {
	            	listaServicios = (ArrayList<Servicio>) miFichero.readObject();
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
	    return listaServicios;
	}

	public static void guardarServicio(List<Servicio> listaServicios) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_SERVICIO))) {
			outputStream.writeObject(listaServicios);
		} catch (Exception e) {
			System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
		}
	}

}