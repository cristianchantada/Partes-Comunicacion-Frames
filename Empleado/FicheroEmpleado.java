package Empleado;

import static Main.PartesFinal.ABSOLUTE_PATH_TO_MODEL_DIR;
import static Mensajes.Mensaje.verMensaje;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Mensajes.Mensaje;

public class FicheroEmpleado {

	private final static String RUTA_A_FICHERO_VEHICULOS = ABSOLUTE_PATH_TO_MODEL_DIR + "empleados.dat";

	public List<Empleado> leerFicheroEmpleado() {
		List<Empleado> listaEmpleados = new ArrayList<>();
		ObjectInputStream miFichero = null;
		try {
			miFichero = new ObjectInputStream(new FileInputStream(RUTA_A_FICHERO_VEHICULOS));
			while (true) {
				listaEmpleados = (ArrayList<Empleado>) miFichero.readObject();
			}

		} catch (ClassNotFoundException e) {
			Mensaje.verMensaje("ERROR LECTURA 03:" + "\nFicheroEmpleado() en FicheroEmpleados.java"
					+ "\nNo puedo cargar desde el fichero la lista de Empleados");
		} catch (EOFException e) {
		} catch (IOException e) {
			Mensaje.verMensaje(
					"ERROR O PASO 01:\nNo se da leído el fichero de Empleados.\nCrearemos fichero de Empleados (Empleados.dat)");
		} finally {
			try {
				if (miFichero != null) {
					miFichero.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listaEmpleados;
	}

	public boolean crearFichero(List<Empleado> empleado) {

		ObjectOutputStream miFichero = null;
		try {
			miFichero = new ObjectOutputStream(new FileOutputStream(RUTA_A_FICHERO_VEHICULOS));
			miFichero.writeObject(empleado);
			miFichero.close();
			return true;
		} catch (IOException write) {
			Mensaje.verMensaje("ERROR ESCRITURA Empleado 01:\n" + "\nMétodo crearFichero en FicheroEmpleados.java"
					+ "\nNo doy escrito la lista de \nEmpleados en Empleados.dat");
			return false;
		}
	}
}