package Clientes;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Mensajes.Mensaje;

/**
 * Clase FicheroCliente
 *
 * Obtiene y actualiza la información sobre los Clientes almacenados en
 * Cliente.dat
 *
 * @author Clase 1ºCS DAW IES Val do Asma
 * @version 1.0
 */

public class FicheroCliente {
	/**
	 * Lista de Clientes asociada al fichero Clientes.dat almacenado en disco.
	 */
	static public List<Cliente> lClientes = new ArrayList<>();

	/**
	 * Crea un objeto FicheroCliente que contiene la lista de Clientes que están
	 * almacenados en Clientes.dat Fichero.lcientes contiene la lista de Clientes
	 * tipo List Cliente Excepción si: no existe fichero o no lo da leído.
	 * 
	 */

	public FicheroCliente() {
		ObjectInputStream miFichero = null;

		try {

			miFichero = new ObjectInputStream(new FileInputStream("Clientes.dat"));

			// Cuando no haya mas objetos saltará la excepcion EOFException

			while (true) {
				lClientes = (List<Cliente>) miFichero.readObject();
			}
			
		} catch (ClassNotFoundException e) {
			Mensaje.verMensaje("ERROR LECTURA 03:"
					+ "\nFicheroCliente() en FicheroClientes.java"
					+ "\nNo puedo cargar desde el fichero la lista de Clientes");
		} catch (EOFException e) {
			/* Se ha llegado al final del fichero */
		} catch (IOException e) {
			Mensaje.verMensaje(
					"ERROR O PASO 01:\nNo se da leído el fichero de Clientes.\nCrearemos fichero de Clientes (Clientes.dat)");
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
	 * Crea un objeto FicheroCliente a partir de una lista de Clientes y lo almacena
	 * en el disco en el fichero Clientes.dat Genera Clientes.dat o bien una
	 * excepción si no da creado el fichero
	 * 
	 * @param Cliente: lista de Clientes clase List Cliente
	 */
	public static void crearFichero(List<Cliente> Cliente) {

		ObjectOutputStream miFichero=null;
		
		try {
			
			miFichero = new ObjectOutputStream(new FileOutputStream("Clientes.dat"));
		
			miFichero.writeObject(Cliente);

		} catch (IOException write) {
			Mensaje.verMensaje("ERROR ESCRITURA Cliente 01:\n"
					+ "\nMétodo crearFichero en FicheroClientes.java"
					+ "\nNo doy escrito la lista de \nClientes en Clientes.dat");
		}

	}

}