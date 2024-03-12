package pruebasConexionBD;

import java.sql.*;

public class SelectTodosClientesBD {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String direccionPServer = "jdbc:mysql://localhost:3306/partes";
			String user = "root";
			String password = "0000";
			Connection conexion = DriverManager.getConnection(direccionPServer, user, password);
			Statement stmt = conexion.createStatement();
			String sql = "select * from clientes";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Cliente: " + rs.getString("nombre"));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Error en el fichero ConexionDB");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("La conexión a la BD ha fallado");
			e.printStackTrace();
		}
	}
}
