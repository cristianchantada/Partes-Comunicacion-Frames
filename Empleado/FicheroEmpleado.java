package Empleado;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase FicheroEmpleados
 *
 * Obtiene y actualiza la información sobre los empleados almacenados en empleados.dat
 *
 * @author Clase 1ºCS DAW IES Val do Asma
 * @version 1.0
 */

public class FicheroEmpleado {
    /**
     * Lista de clientes asociada al fichero empleados.dat almacenado en disco.
     */
	static public List <Empleado> lempleado=new ArrayList<>();
	
	/**
     * Crea un objeto FicheroEmpleado que contiene la lista de empleados que están almacenados en empleados.dat
     * Fichero.lempleados contiene la lista de empledos tipo List Empleado
     * Excepción si: no existe fichero o no lo da leído.
     *               
     */
	public FicheroEmpleado() {
		    try (ObjectInputStream miFichero = new ObjectInputStream(new FileInputStream("empleados.dat"))) {
			// Cuando no haya mas objetos saltara la excepcion EOFException
			
			while (true) {
				lempleado= (ArrayList<Empleado>) miFichero.readObject();
			}
		} catch (ClassNotFoundException e) {System.out.println("1");
		} catch (EOFException e) {System.out.println("2");
		} catch (IOException e) {System.out.println("3");
		}
	}	

	/**
     * Crea un objeto FicheroEmpleado a partir de una lista de empleados y lo almacena en el disco en el fichero empleados.dat
     * Genera empleados.dat o bien una excepción si no da creado el fichero
     * @param empleados: lista de empleados clase List Empleado
     */
	public static void crearFichero(List<Empleado> empleados) {
		
		try (ObjectOutputStream miFichero = new ObjectOutputStream(new FileOutputStream("Clientes.dat"))) {

			miFichero.writeObject(empleados);

		} catch (IOException write) {
			System.out.println("ERROR 01: No se da grabado la lista de empleados");
		}

	}
	
}