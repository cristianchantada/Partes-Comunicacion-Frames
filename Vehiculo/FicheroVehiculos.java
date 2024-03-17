package Vehiculo;

import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Mensajes.Mensaje;

import static Mensajes.Mensaje.verMensaje;

public class FicheroVehiculos {

	private final static String RUTA_A_FICHERO_VEHICULOS = ABSOLUTE_PATH_TO_MODEL_DIR + "vehiculos.dat";

	public static List<Vehiculo> leerFichero() {
	    ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();
	    
	    File archivoVehiculos = new File(RUTA_A_FICHERO_VEHICULOS);
	    
	    if (archivoVehiculos.length() == 0) {
	        System.out.println("El archivo de vehículos está vacío.");
	        return listaVehiculos;
	    }
	    
	    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_VEHICULOS))) {
	        while (true) {
	            try {
	            	listaVehiculos = (ArrayList<Vehiculo>) miFichero.readObject();
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
	    return listaVehiculos;
	}
	
	public static boolean grabarVehiculo(List<Vehiculo> listaVehiculos) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_VEHICULOS))) {
			outputStream.writeObject(listaVehiculos);
			return true;
        } catch (Exception e) {
            System.out.println("Error al serializar los datos en el archivo binario: " + e.getMessage());
            return false;
        }
    }
	
}
