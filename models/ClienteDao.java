package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Clientes.Cliente;


public class ClienteDao implements DaoInterface<Cliente> {

	private List<Cliente> listaClientes = new ArrayList<>();

	private String user;
	private String password;
	private String url;
	private Connection conn;
	private String sql;
	private int rowsAffected;
	private Statement stmt;
	private PreparedStatement preparedStatement;
	private ResultSet rs;

	public ClienteDao() {
		// initialize driver class
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("Fail to initialize Oracle JDBC driver in ClienteDao: " + e.toString());
		}

		user = "root";
		password = "0000";
		url = "jdbc:mysql://localhost:3306/partes";

		// connect
		conn = null;
		try {
			this.conn = DriverManager.getConnection(url, user, password);
			System.out.println(" Connection status: " + conn);
		} catch (Exception e) {
			System.out.println("Connection failed in ClienteDao: " + e.toString());
		}

		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cliente get(Cliente cliente) {
		String clientNif = cliente.getNif();
		Cliente newCliente = new Cliente(clientNif);
		sql = "SELECT * FROM clientes WHERE nif = '" + clientNif + "'";
		try {
			this.rs = stmt.executeQuery(sql);
			if (rs.next()) {
				newCliente.setNombre(rs.getString("nombre"));
				newCliente.setCorreo(rs.getString("email"));
				newCliente.setTelefono(rs.getString("telefono"));
			} else {
				System.out.println("No se encontró ningún cliente con el NIF proporcionado.");
			}
		} catch (SQLException e1) {
			System.out.println("SQL exception in ClienteDao");
			e1.printStackTrace();
		} finally {
			closeConnection();
		}
		return newCliente;
	}

	@Override
	public List<Cliente> getAll() {
		listaClientes = new ArrayList<>();
		sql = "SELECT * FROM clientes";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Cliente newCliente = new Cliente();
				newCliente.setNombre(rs.getString("nombre"));
				newCliente.setCorreo(rs.getString("email"));
				newCliente.setTelefono(rs.getString("telefono"));
				newCliente.setNif(rs.getString("nif"));
				listaClientes.add(newCliente);
			}
		} catch (SQLException e1) {
			System.out.println("SQL exception en ClienteDao");
			e1.printStackTrace();
		} finally {
			closeConnection();
		}
		return listaClientes;
	}

	@Override
	public void save(Cliente cliente) {
		String sql = "INSERT INTO clientes (nif, nombre, email, telefono) VALUES (?, ?, ?, ?)";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, cliente.getNif());
			preparedStatement.setString(2, cliente.getNombre());
			preparedStatement.setString(3, cliente.getCorreo());
			preparedStatement.setString(4, cliente.getNif());

			rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				Mensaje.verMensaje("Cliente insertado correctamente");
			} else {
				Mensaje.verMensaje("Error al insertar cliente");
			}
		} catch (SQLException e) {
			System.out.println("Error al insertar el cliente en ClienteDao: " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public void update(Cliente cliente, String[] params) {
	    if (params.length != 4) {
	        System.out.println("Número incorrecto de parámetros para la actualización del cliente");
	        return;
	    }
	    
	    String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, nif = ? WHERE nif = ?";
	    try {
	         preparedStatement = conn.prepareStatement(sql);
	         preparedStatement.setString(1, params[0]);
	         preparedStatement.setString(2, params[1]);
	         preparedStatement.setString(3, params[2]);
	         preparedStatement.setString(4, params[3]);
	         preparedStatement.setString(5, cliente.getNif());
	        
	        rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            Mensaje.verMensaje("Cliente actualizado correctamente");
	        } else {
	            Mensaje.verMensaje("No se encontró ningún cliente con el NIF proporcionado");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el cliente en ClienteDao: " + e.getMessage());
	    } finally {
	        closeConnection();
	    }
	}

	@Override
	public void delete(Cliente cliente) {
	    String sql = "DELETE FROM clientes WHERE nif = ?";
	    try {
	        preparedStatement = conn.prepareStatement(sql);
	        preparedStatement.setString(1, cliente.getNif());
	        
	        int rowsAffected = preparedStatement.executeUpdate();

	        if (rowsAffected > 0) {
	            Mensaje.verMensaje("Cliente eliminado correctamente");
	        } else {
	            Mensaje.verMensaje("No se encontró ningún cliente con el NIF proporcionado");
	        }	      
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar el cliente en ClienteDao: " + e.getMessage());
	    } finally {
	        closeConnection();
	    }
	}


	public void closeConnection() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(conn != null) {
				System.out.println("La conexión en ClienteDao se ha cerrado con éxito");				
			}
			// commit only when updating the DB
			// conn.commit();
			// disconnect
			conn.close();
		} catch (SQLException e) {
			System.out.println("Close connection error in ClienteDao");
			e.printStackTrace();
		}
	}

}
