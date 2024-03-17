package Clientes;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static Mensajes.Mensaje.verMensaje;
import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;

public class FicheroCliente {

	private final static String RUTA_A_FICHERO_CLIENTES = ABSOLUTE_PATH_TO_MODEL_DIR + "clientes.dat";

	public static List<Cliente> leerFichero() {
	    ArrayList<Cliente> clientes = new ArrayList<>();
	    
	    File archivoClientes = new File(RUTA_A_FICHERO_CLIENTES);
	    
	    if (archivoClientes.length() == 0) {
	        System.out.println("El archivo de clientes está vacío.");
	        return clientes;
	    }
	    
	    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_CLIENTES))) {
	        while (true) {
	            try {
	                clientes = (ArrayList<Cliente>) miFichero.readObject();
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
	    return clientes;
	}

	public static boolean crearFichero(List<Cliente> cliente) {
		try (ObjectOutputStream miFichero = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_CLIENTES))) {
			miFichero.writeObject(cliente);
			return true;		
		} catch (IOException e) {
			System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
			return false;
		}
	}

}