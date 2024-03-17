package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Localizacion.Localizacion;
import Mensajes.Mensaje;

public class LocalizacionDao implements DaoInterface<Localizacion> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public LocalizacionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize LocalizacionDao: " + e.getMessage());
        }
    }
    
    @Override
    public Localizacion get(Localizacion localizacion) {
        Localizacion newLocalizacion = null;
        String sql = "SELECT * FROM localizaciones WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, localizacion.getId());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newLocalizacion = new Localizacion(
                    rs.getInt("id"),
                	rs.getString("direccion"),
                    rs.getString("cp"),
                    rs.getString("localidad"),
                    rs.getString("provincia")
                );
            } else {
                System.out.println("No se encontró ninguna localización con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in LocalizacionDao.get: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return newLocalizacion;
    }
    
    public Localizacion getLocationByData(Localizacion localizacion) {
        Localizacion newLocalizacion = null;
        String sql = "SELECT * FROM localizaciones WHERE direccion = ? AND cp = ? AND localidad = ? AND provincia = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, localizacion.getDireccion());
            preparedStatement.setString(2, localizacion.getCp());
            preparedStatement.setString(3, localizacion.getLocalidad());
            preparedStatement.setString(4, localizacion.getProvincia());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newLocalizacion = new Localizacion(
                    rs.getInt("id"),
                	rs.getString("direccion"),
                    rs.getString("cp"),
                    rs.getString("localidad"),
                    rs.getString("provincia")
                );
            } else {
                System.out.println("No se encontró ninguna localización con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in LocalizacionDao.get: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return newLocalizacion;
    }

    @Override
    public List<Localizacion> getAll() {
        List<Localizacion> localizaciones = new ArrayList<>();
        String sql = "SELECT * FROM localizaciones";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Localizacion localizacion = new Localizacion(
                	rs.getInt("id"),
                    rs.getString("direccion"),
                    rs.getString("cp"),
                    rs.getString("localidad"),
                    rs.getString("provincia")
                );
                localizaciones.add(localizacion);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in LocalizacionDao.getAll: " + e.getMessage());
        }
        return localizaciones;
    }

    @Override
    public void save(Localizacion localizacion) {
        String sql = "INSERT INTO localizaciones (direccion, cp, localidad, provincia) VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, localizacion.getDireccion());
            preparedStatement.setString(2, localizacion.getCp());
            preparedStatement.setString(3, localizacion.getLocalidad());
            preparedStatement.setString(4, localizacion.getProvincia());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar la localización en LocalizacionDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Localizacion localizacion, String[] params) {
        if (params.length != 4) {
            System.out.println("Número incorrecto de parámetros para la actualización de la localización");
            return;
        }
        
        String sql = "UPDATE localizaciones SET direccion = ?, cp = ?, localidad = ?, provincia = ? WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, params[0]); // nueva dirección
            preparedStatement.setString(2, params[1]); // nuevo código postal
            preparedStatement.setString(3, params[2]); // nueva localidad
            preparedStatement.setString(4, params[3]); // nueva provincia
            preparedStatement.setInt(5, localizacion.getId()); // id de la localización a actualizar
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Localización actualizada correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ninguna localización con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la localización en LocalizacionDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Localizacion localizacion) {
        String sql = "DELETE FROM localizaciones WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, localizacion.getId());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Localización eliminada correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ninguna localización con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar la localización en LocalizacionDao: " + e.getMessage());
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
                System.out.println("La conexión en LocalizacionDao se ha cerrado con éxito");                
            }
            } catch (SQLException e) {
            System.out.println("Close connection error in LocalizacionDao: " + e.getMessage());
        }
    }

}