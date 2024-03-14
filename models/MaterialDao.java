package models;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import Material.Material;

public class MaterialDao implements DaoInterface<Material> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public MaterialDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize MaterialDao: " + e.getMessage());
        }
    }
    
    @Override
    public Material get(Material material) {
        Material newMaterial = null;
        String sql = "SELECT * FROM materiales WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, material.getId());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newMaterial = new Material(
                    rs.getString("desc"),
                    rs.getInt("ctdad")
                );
                newMaterial.setId(rs.getInt("id"));
            } else {
                System.out.println("No se encontró ningún material con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in MaterialDao.get: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return newMaterial;
    }

    @Override
    public List<Material> getAll() {
        List<Material> materiales = new ArrayList<>();
        String sql = "SELECT * FROM materiales";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Material material = new Material(
                    rs.getString("desc"),
                    rs.getInt("ctdad")
                );
                material.setId(rs.getInt("id"));
                materiales.add(material);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in MaterialDao.getAll: " + e.getMessage());
        }
        return materiales;
    }

    @Override
    public void save(Material material) {
        String sql = "INSERT INTO materiales (desc, ctdad) VALUES (?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, material.getDesc());
            preparedStatement.setInt(2, material.getCtdad());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                material.setId(generatedKeys.getInt(1));
            }
            Mensaje.verMensaje("Material insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el material en MaterialDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Material material, String[] params) {
        if (params.length != 2) {
            System.out.println("Número incorrecto de parámetros para la actualización del material");
            return;
        }
        
        String sql = "UPDATE materiales SET desc = ?, ctdad = ? WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, params[0]); // nueva descripción
            preparedStatement.setInt(2, Integer.parseInt(params[1])); // nueva cantidad
            preparedStatement.setInt(3, material.getId()); // id del material a actualizar
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Material actualizado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún material con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el material en MaterialDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Material material) {
        String sql = "DELETE FROM materiales WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, material.getId());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Material eliminado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún material con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el material en MaterialDao: " + e.getMessage());
        } finally {
            closeConnection();
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
                System.out.println("La conexión en MaterialDao se ha cerrado con éxito");
                
            }
             } catch (SQLException e) {
            System.out.println("Close connection error in MaterialDao: " + e.getMessage());
        }
    }
}
