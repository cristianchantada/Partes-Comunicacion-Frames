package Material;

import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;
import static Mensajes.Mensaje.verMensaje;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroMaterial {

	private final static String RUTA_A_FICHERO_MATERIAL = ABSOLUTE_PATH_TO_MODEL_DIR + "materiales.dat";

	public static List<Material> leerFichero() {
	    ArrayList<Material> listaMateriales = new ArrayList<>();
	    
	    File archivoMaterial = new File(RUTA_A_FICHERO_MATERIAL);
	    
	    if (archivoMaterial.length() == 0) {
	        System.out.println("El archivo de materiales está vacío.");
	        return listaMateriales;
	    }
	    
	    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_MATERIAL))) {
	        while (true) {
	            try {
	            	listaMateriales = (ArrayList<Material>) miFichero.readObject();
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
	    return listaMateriales;
	}
	
	public static void grabarMaterial(List<Material> listaVehiculos) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_MATERIAL))) {
            outputStream.writeObject(listaVehiculos);
            verMensaje("Datos guardados con éxito");         
        } catch (Exception e) {
            System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
        }
    }
	
}
