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
	
	// Métodos Xabi
	/*
	private static ArrayList<String> desc = new ArrayList<>();
	private static ArrayList<Integer> ctdad = new ArrayList<>();
	
	public static void leerFicheroMaterial() {
		try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_A_FICHERO_MATERIAL))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				desc.add(parts[0]);
				ctdad.add(Integer.parseInt(parts[1]));
			}
		} catch (Exception e) {

		}
	}

	public static void escribirEnFicheroMaterial() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(
				new FileOutputStream(RUTA_A_FICHERO_MATERIAL, false))) {
			outputStream.writeObject(desc);
			outputStream.writeObject(ctdad);
		} catch (Exception e) {
			System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
		}
	}
	
	public static void guardarDatos() {
		try (FileWriter writer = new FileWriter(RUTA_A_FICHERO_MATERIAL, false)) {
			for (int i = 0; i < desc.size(); i++) {
				writer.write(desc.get(i) + "," + ctdad.get(i) + "\n");
			}
		} catch (Exception e) {

		}
	}*/
	
}
