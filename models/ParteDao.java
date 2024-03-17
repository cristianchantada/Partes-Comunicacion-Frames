package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Clientes.Cliente;
import Empleado.Empleado;
import Localizacion.Localizacion;
import Mensajes.Mensaje;
import Parte.EstadoParte;
import Parte.Parte;
import Vehiculo.Vehiculo;

public class ParteDao implements DaoInterface<Parte> {

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public ParteDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/partes";
            String user = "root";
            String password = "0000";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Failed to initialize ParteDao: " + e.getMessage());
        }
    }


    @Override
    public void save(Parte parte) {
        String sql = "INSERT INTO partes (cliente, obra, empleado, fecha, materiales, servicios, vehiculo, estado, localizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, parte.getCliente().getNif());
            preparedStatement.setString(2, parte.getDescripcion());
            preparedStatement.setString(3, parte.getEmpleado().getNif());
            
            java.util.Date utilDate = parte.getFecha();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(4, sqlDate);
            
            preparedStatement.setString(5, parte.getDescripcionMaterial());
            preparedStatement.setString(6, parte.getDescripcionServicio());
            preparedStatement.setString(7, parte.getVehiculo().getMatricula());

            String estadoParte;
			if (parte.getEstado().name().equals("EN_PROCESO")) {
				estadoParte = "En proceso";
			} else {
				estadoParte = "Terminado";
			}         
            preparedStatement.setString(8, estadoParte);
            
            preparedStatement.setInt(9, parte.getLocalizacion().getId());
            preparedStatement.executeUpdate();
            Mensaje.verMensaje("Parte insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el parte en ParteDao: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    
    private Enum<EstadoParte> getEstado() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Parte get(Parte t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Parte> getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(Parte t, String[] params) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Parte t) {
		// TODO Auto-generated method stub
		
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
                System.out.println("La conexión en ParteDao se ha cerrado con éxito");                           
            }          
          } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión en ParteDao: " + e.getMessage());
        }
    }
}
