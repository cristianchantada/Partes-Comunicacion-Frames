package Empleados;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Mensajes.Mensaje;

/**
 * Clase FicheroEmpleado
 *
 * Obtiene y actualiza la información sobre los Empleados almacenados en
 * Empleados.dat
 *
 * @author Clase 1ºCS DAW IES Val do Asma
 * @version 1.0
 */

public class FicheroEmpleado {
	/**
	 * Lista de Empleados asociada al fichero Empleados.dat almacenado en disco.
	 */
	static public List<Empleado> lEmpleados = new ArrayList<>();

	/**
	 * Crea un objeto FicheroEmpleado que contiene la lista de Empleados que están
	 * almacenados en Empleados.dat Fichero.lcientes contiene la lista de Empleados
	 * tipo List Empleado Excepción si: no existe fichero o no lo da leído.
	 * 
	 */
	public FicheroEmpleado() {
		ObjectInputStream miFichero = null;

		try {

			miFichero = new ObjectInputStream(new FileInputStream("Empleados.dat"));

			// Cuando no haya mas objetos saltará la excepcion EOFException

			while (true) {
				lEmpleados = (ArrayList<Empleado>) miFichero.readObject();
			}
			
		} catch (ClassNotFoundException e) {
			Mensaje.verMensaje("ERROR LECTURA 03:"
					+ "\nFicheroEmpleado() en FicheroEmpleados.java"
					+ "\nNo puedo cargar desde el fichero la lista de Empleados");
		} catch (EOFException e) {
			/* Se ha llegado al final del fichero */
		} catch (IOException e) {
			Mensaje.verMensaje(
					"ERROR O PASO 01:\nNo se da leído el fichero de Empleados.\nCrearemos fichero de Empleados (Empleados.dat)");
		} finally {
			try {
				// Cierra el ObjectInputStream en el bloque finally
				if (miFichero != null) {
					miFichero.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Crea un objeto FicheroEmpleado a partir de una lista de Empleados y lo almacena
	 * en el disco en el fichero Empleados.dat Genera Empleados.dat o bien una
	 * excepción si no da creado el fichero
	 * 
	 * @param Empleado: lista de Empleados clase List Empleado
	 */
	public static void crearFichero(List<Empleado> Empleado) {

		ObjectOutputStream miFichero=null;
		
		try {
			
			miFichero = new ObjectOutputStream(new FileOutputStream("Empleados.dat"));
		
			miFichero.writeObject(Empleado);

		} catch (IOException write) {
			Mensaje.verMensaje("ERROR ESCRITURA Empleado 01:\n"
					+ "\nMétodo crearFichero en FicheroEmpleados.java"
					+ "\nNo doy escrito la lista de \nEmpleados en Empleados.dat");
		}

	}

}