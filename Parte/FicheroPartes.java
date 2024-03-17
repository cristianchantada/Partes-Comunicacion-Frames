package Parte;
import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FicheroPartes {
	
	private final static String RUTA_A_FICHERO_PARTES = ABSOLUTE_PATH_TO_MODEL_DIR + "partes.dat";

    public void verPartes() {
        List<Parte> partes = consultarPartes();
        int i = 0;
        for (Parte parte : partes) {
            System.out.println("Parte nº" + i + ":\n" + parte);
            i++;
        }
    }

    public void generarPartes(List<Parte> partes) {
    	
        try (ObjectOutputStream ficheroPartes = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_PARTES))) {
            for (Parte parte : partes) {
                ficheroPartes.writeObject(parte);
            }
            System.out.println("Grabando partes en el fichero binario...");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error en la escritura de datos en fichero partes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Parte> consultarPartes() {
        List<Parte> partes = new ArrayList<>();
        
    	File archivoPartes = new File(RUTA_A_FICHERO_PARTES);
    	
	    if (archivoPartes.length() == 0) {	    
	        return partes;
	    }
         
        try (ObjectInputStream ficheroPartes = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_PARTES))) {
            while (true) {
                Parte parte = (Parte) ficheroPartes.readObject();
                partes.add(parte);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se ha encontrado la clase especificada" + e.getMessage());
            e.printStackTrace();
        } catch (EOFException e) {
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error en la escritura de datos en fichero partes: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
            e.printStackTrace();
        }
        return partes;
    }
    
    
    

}
