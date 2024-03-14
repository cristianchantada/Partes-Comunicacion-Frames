package models;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Vehiculo.Vehiculo;

public class VehiculoDao implements DaoInterface<Vehiculo> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public VehiculoDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize VehiculoDao: " + e.getMessage());
        }
    }
    
    @Override
    public Vehiculo get(Vehiculo vehiculo) {
        Vehiculo newVehiculo = null;
        String sql = "SELECT * FROM vehiculos WHERE matricula = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, vehiculo.getMatricula());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newVehiculo = new Vehiculo(
                    rs.getString("matricula"),
                    rs.getString("marca"),
                    rs.getString("modelo")
                );
            } else {
                System.out.println("No se encontró ningún vehículo con la matrícula proporcionada.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in VehiculoDao.get: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return newVehiculo;
    }

    @Override
    public List<Vehiculo> getAll() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(
                    rs.getString("matricula"),
                    rs.getString("marca"),
                    rs.getString("modelo")
                );
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in VehiculoDao.getAll: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return vehiculos;
    }

    @Override
    public void save(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (matricula, marca, modelo) VALUES (?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, vehiculo.getMatricula());
            preparedStatement.setString(2, vehiculo.getMarca());
            preparedStatement.setString(3, vehiculo.getModelo());
            preparedStatement.executeUpdate();
            Mensaje.verMensaje("Vehículo insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el vehículo en VehiculoDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Vehiculo vehiculo, String[] params) {
        if (params.length != 3) {
            System.out.println("Número incorrecto de parámetros para la actualización del vehículo");
            return;
        }
        
        String sql = "UPDATE vehiculos SET marca = ?, modelo = ? WHERE matricula = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, params[0]); // nueva marca
            preparedStatement.setString(2, params[1]); // nuevo modelo
            preparedStatement.setString(3, vehiculo.getMatricula()); // matrícula del vehículo a actualizar
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Vehículo actualizado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún vehículo con la matrícula proporcionada");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el vehículo en VehiculoDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Vehiculo vehiculo) {
        String sql = "DELETE FROM vehiculos WHERE matricula = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, vehiculo.getMatricula());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Vehículo eliminado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún vehículo con la matrícula proporcionada");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el vehículo en VehiculoDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
                System.out.println("La conexión en VehiculoDao se ha cerrado con éxito");
                
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión en VehiculoDao: " + e.getMessage());
        }
    }
}
