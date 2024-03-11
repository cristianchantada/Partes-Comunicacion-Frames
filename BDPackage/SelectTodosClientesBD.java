package BDPackage;

import java.sql.*;

public class SelectTodosClientesBD {

	static Connection conexion = null;
	static java.sql.Statement stmt = null;
	static ResultSet rs;

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String direccionPServer = "jdbc:mysql://localhost:3306/partes";
			String user = "root";
			String password = "0000";
			conexion = DriverManager.getConnection(direccionPServer, user, password);
			stmt = conexion.createStatement();
			String sql = "select * from clientes";
			rs = stmt.executeQuery(sql);
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
