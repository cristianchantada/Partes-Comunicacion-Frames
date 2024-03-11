package BDPackage;

import java.sql.*;

public class ConexionBD {

	public static void main(String args) {

		try {
			// Cargo la clase del driver.
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establecer conexión con BD MySQL.
			String direccionPServer = "jdbc:mysql://localhost:3306/partes";
			String user = "root";
			String password = "0000";
			try {
				// Intento establecer la conexión
				DriverManager.getConnection(direccionPServer, user, password);
				System.out.println("Conexión establecida con la BD");
			} catch (SQLException e) {
				System.out.println("La conexión a la BD ha fallado");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error en el fichero ConexionDB");
			e.printStackTrace();
		}
	}

}
