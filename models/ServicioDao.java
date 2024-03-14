package models;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Servicio.Servicio;

public class ServicioDao implements DaoInterface<Servicio> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public ServicioDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize ServicioDao: " + e.getMessage());
        }
    }
    
    @Override
    public Servicio get(Servicio servicio) {
        Servicio newServicio = null;
        String sql = "SELECT * FROM servicios WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, servicio.getId());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                newServicio = new Servicio(
                    rs.getString("descripcion"),
                    rs.getString("horaEntrada"),
                    rs.getString("horaSalida"),
                    rs.getDate("fecha")
                );
                newServicio.setId(rs.getInt("id"));
            } else {
                System.out.println("No se encontró ningún servicio con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in ServicioDao.get: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return newServicio;
    }

    @Override
    public List<Servicio> getAll() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Servicio servicio = new Servicio(
                    rs.getString("descripcion"),
                    rs.getString("horaEntrada"),
                    rs.getString("horaSalida"),
                    rs.getDate("fecha")
                );
                servicio.setId(rs.getInt("id"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in ServicioDao.getAll: " + e.getMessage());
        }
        return servicios;
    }

    @Override
    public void save(Servicio servicio) {
        String sql = "INSERT INTO servicios (descripcion, horaEntrada, horaSalida, fecha) VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, servicio.getDesc());
            preparedStatement.setTime(2, Time.valueOf(servicio.getHe()));
            preparedStatement.setTime(3, Time.valueOf(servicio.getHs()));
            preparedStatement.setDate(4, new java.sql.Date(servicio.getFecha().getTime()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                servicio.setId(generatedKeys.getInt(1));
            }
            Mensaje.verMensaje("Servicio insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el servicio en ServicioDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Servicio servicio, String[] params) {
        if (params.length != 4) {
            System.out.println("Número incorrecto de parámetros para la actualización del servicio");
            return;
        }
        
        String sql = "UPDATE servicios SET descripcion = ?, horaEntrada = ?, horaSalida = ?, fecha = ? WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, params[0]);
            preparedStatement.setTime(2, Time.valueOf(params[1]));
            preparedStatement.setTime(3, Time.valueOf(params[2]));
            preparedStatement.setDate(4, new java.sql.Date(parseDate(params[3]).getTime()));
            preparedStatement.setInt(5, servicio.getId());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Servicio actualizado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún servicio con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el servicio en ServicioDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Servicio servicio) {
        String sql = "DELETE FROM servicios WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, servicio.getId());
            
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Mensaje.verMensaje("Servicio eliminado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún servicio con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el servicio en ServicioDao: " + e.getMessage());
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
                System.out.println("La conexión en ServicioDao se ha cerrado con éxito");
                
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión en ServicioDao: " + e.getMessage());
        }
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing date in ServicioDao: " + e.getMessage());
            return null;
        }
    }
}
