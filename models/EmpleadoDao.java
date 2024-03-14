package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Empleado.Empleado;


public class EmpleadoDao implements DaoInterface<Empleado> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public EmpleadoDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize EmpleadoDao: " + e.getMessage());
        }
    }

    @Override
    public Empleado get(Empleado empleado) {
        Empleado newEmpleado = null;
        String sql = "SELECT * FROM empleado WHERE nif = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, empleado.getNif());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newEmpleado = new Empleado(
                    rs.getString("nif"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("codigo")
                );
            } else {
                System.out.println("No se encontró ningún empleado con el NIF proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in EmpleadoDao.get: " + e.getMessage());
        } finally {
        	closeConnection();
        }
        return newEmpleado;
    }

    @Override
    public List<Empleado> getAll() {
        List<Empleado> listaEmpleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Empleado newEmpleado = new Empleado(
                    rs.getString("nif"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("codigo")
                );
                listaEmpleados.add(newEmpleado);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in EmpleadoDao.getAll: " + e.getMessage());
        }
        return listaEmpleados;
    }

    @Override
    public void save(Empleado empleado) {
        String sql = "INSERT INTO empleado (nif, nombre, telefono, correo, codigo) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, empleado.getNif());
            preparedStatement.setString(2, empleado.getNombre());
            preparedStatement.setString(3, empleado.getTelefono());
            preparedStatement.setString(4, empleado.getCorreo());
            preparedStatement.setString(5, empleado.getCodigo());
            preparedStatement.executeUpdate();
            Mensaje.verMensaje("Empleado insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el empleado en EmpleadoDao: " + e.getMessage());
        }
    }

    @Override
    public void update(Empleado empleado, String[] params) {
        // Verificar si se proporcionan parámetros válidos
        if (params.length != 5) {
            System.out.println("Número incorrecto de parámetros para la actualización del empleado");
            return;
        }
        
        String sql = "UPDATE empleado SET nombre = ?, telefono = ?, correo = ?, codigo = ? WHERE nif = ?";
        try {
             preparedStatement = conn.prepareStatement(sql);
             preparedStatement.setString(1, params[0]); // nuevo nombre
             preparedStatement.setString(2, params[1]); // nuevo telefono
             preparedStatement.setString(3, params[2]); // nuevo correo
             preparedStatement.setString(4, params[3]); // nuevo codigo
             preparedStatement.setString(5, empleado.getNif()); // nif del empleado a actualizar
             preparedStatement.executeUpdate();
             Mensaje.verMensaje("Empleado actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar el empleado en EmpleadoDao: " + e.getMessage());
        }
    }

    @Override
    public void delete(Empleado empleado) {
        String sql = "DELETE FROM empleado WHERE nif = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, empleado.getNif());
            preparedStatement.executeUpdate();
            Mensaje.verMensaje("Empleado eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el empleado en EmpleadoDao: " + e.getMessage());
        }
    }

    
    public void closeConnection() {
        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (conn != null) {
	                conn.close();
	                System.out.println("La conexión en EmpleadoDao se ha cerrado con éxito");	                
	            }
            } catch (SQLException e) {
            System.out.println("Close connection error in EmpleadoDao: " + e.getMessage());
        }
    }
}
