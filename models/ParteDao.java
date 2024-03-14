package models;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Clientes.Cliente;
import Empleado.Empleado;
import Localizacion.Localizacion;
import Mensajes.Mensaje;
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
    public Parte get(Parte parte) {
    	
        Parte newParte = new Parte();
        String sql = "SELECT * FROM partes WHERE id = ?";
        
        String clienteNif = "";
        String empleadoNif = "";
        String matriculaVehiculo = "";
        int localizacionId = 0;
        
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, parte.getId());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	
                newParte.setId(rs.getInt("id"));
                newParte.setDescripcion(rs.getString("obra"));
                newParte.setFecha(rs.getDate("fecha"));           
                newParte.setMaterialDescription(rs.getString("materiales"));
                newParte.setServicioDescription(rs.getString("servicios"));
    
                clienteNif = rs.getString("cliente");
            	empleadoNif = rs.getString("empleado");
                matriculaVehiculo = rs.getString("vehiculo");
                localizacionId = rs.getInt("localizacion"); 

                if(rs.getString("estado").equals("Terminado")) {
                	newParte.setEstado(EstadoParte.TERMINADO);
                } else {
                	newParte.setEstado(EstadoParte.EN_PROCESO);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el parte en ParteDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
               
        Cliente cliente = new Cliente(clienteNif);
        ClienteDao clienteDao = new ClienteDao();
        cliente = clienteDao.get(cliente);   
        newParte.setCliente(cliente);
        
        Empleado empleado = new Empleado(empleadoNif);
        EmpleadoDao empleadoDao = new EmpleadoDao();
        empleado = empleadoDao.get(empleado);
        newParte.setEmpleado(empleado);
        
        Vehiculo vehiculo = new Vehiculo(matriculaVehiculo);
        VehiculoDao vehiculoDao = new VehiculoDao();
        vehiculo = vehiculoDao.get(vehiculo);
        newParte.setVehiculo(vehiculo);
        
        Localizacion localizacion = new Localizacion(localizacionId);
        LocalizacionDao localizacionDao = new LocalizacionDao();
        localizacion = localizacionDao.get(localizacion);
        newParte.setLocalizacion(localizacion);
        
        return newParte;
    }

    @Override
    public List<Parte> getAll() {
        List<Parte> partesList = new ArrayList<>();
        String sql = "SELECT * FROM partes";
        
        
        try {
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                /*Parte parte = new Parte();
                parte.setId(rs.getInt("id"));                              
                parte = get(parte);
                partesList.add(parte);*/ 
            	
            	System.out.println("Aquí entra");
            	
            	Parte newParte = new Parte(rs.getInt("id"));
            	
                String clienteNif = "";
                String empleadoNif = "";
                String matriculaVehiculo = "";
                int localizacionId = 0;
                String sqlSingleParte = "SELECT * FROM partes WHERE id = ?";
                
                try {
                	ResultSet rs2;
                	PreparedStatement praparedStatement2;
                	praparedStatement2 = conn.prepareStatement(sqlSingleParte);
                	praparedStatement2.setInt(1, newParte.getId());
                    rs2 = praparedStatement2.executeQuery();
                    if (rs2.next()) {
                    	
                        newParte.setId(rs2.getInt("id"));
                        newParte.setDescripcion(rs2.getString("obra"));
                        newParte.setFecha(rs2.getDate("fecha"));           
                        newParte.setMaterialDescription(rs2.getString("materiales"));
                        newParte.setServicioDescription(rs2.getString("servicios"));
            
                        clienteNif = rs2.getString("cliente");
                    	empleadoNif = rs2.getString("empleado");
                        matriculaVehiculo = rs2.getString("vehiculo");
                        localizacionId = rs2.getInt("localizacion"); 

                        if(rs2.getString("estado").equals("Terminado")) {
                        	newParte.setEstado(EstadoParte.TERMINADO);
                        } else {
                        	newParte.setEstado(EstadoParte.EN_PROCESO);
                        }
                        
                        Cliente cliente = new Cliente(clienteNif);
                        ClienteDao clienteDao = new ClienteDao();
                        cliente = clienteDao.get(cliente);   
                        newParte.setCliente(cliente);
                        
                        Empleado empleado = new Empleado(empleadoNif);
                        EmpleadoDao empleadoDao = new EmpleadoDao();
                        empleado = empleadoDao.get(empleado);
                        newParte.setEmpleado(empleado);
                        
                        Vehiculo vehiculo = new Vehiculo(matriculaVehiculo);
                        VehiculoDao vehiculoDao = new VehiculoDao();
                        vehiculo = vehiculoDao.get(vehiculo);
                        newParte.setVehiculo(vehiculo);
                        
                        Localizacion localizacion = new Localizacion(localizacionId);
                        LocalizacionDao localizacionDao = new LocalizacionDao();
                        localizacion = localizacionDao.get(localizacion);
                        newParte.setLocalizacion(localizacion);

                    	partesList.add(newParte);                   	

                    } 
                } catch (SQLException e) {
                    System.out.println("Error al obtener el parte en ParteDao: " + e.getMessage());
                } 
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in ParteDao.getAll: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return partesList;
    }

    @Override
    public void save(Parte parte) {
        String sql = "INSERT INTO partes (cliente, obra, empleado, fecha, materiales, servicios, vehiculo, estado, localizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, parte.getCliente().getNif());
            preparedStatement.setString(2, parte.getDescripcion());
            preparedStatement.setString(3, parte.getEmpleado().getNif());
            preparedStatement.setDate(4, (java.sql.Date) parte.getFecha());
            preparedStatement.setString(5, parte.getMaterialDescription());
            preparedStatement.setString(6, parte.getServicioDescription());
            preparedStatement.setString(7, parte.getVehiculo().getMatricula());
            preparedStatement.setString(8, parte.getEstado().name());
            preparedStatement.setInt(9, parte.getLocalizacion().getId());
            preparedStatement.executeUpdate();
            Mensaje.verMensaje("Parte insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar el parte en ParteDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(Parte parte, String[] params) {
        String sql = "UPDATE partes SET cliente = ?, obra = ?, empleado = ?, fecha = ?, materiales = ?, servicios = ?, vehiculo = ?, estado = ?, localizacion = ? WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, parte.getCliente().getNif());
            preparedStatement.setString(2, parte.getDescripcion());
            preparedStatement.setString(3, parte.getEmpleado().getNif());
            preparedStatement.setDate(4, (java.sql.Date) parte.getFecha());
            preparedStatement.setString(5, parte.getMaterialDescription());
            preparedStatement.setString(6, parte.getServicioDescription());
            preparedStatement.setString(7, parte.getVehiculo().getMatricula());
            preparedStatement.setString(8, parte.getEstado().name());
            preparedStatement.setInt(9, parte.getLocalizacion().getId());
            preparedStatement.setInt(10, parte.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Mensaje.verMensaje("Parte actualizado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún parte con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el parte en ParteDao: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(Parte parte) {
        String sql = "DELETE FROM partes WHERE id = ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, parte.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Mensaje.verMensaje("Parte eliminado correctamente");
            } else {
                Mensaje.verMensaje("No se encontró ningún parte con el ID proporcionado");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el parte en ParteDao: " + e.getMessage());
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
                System.out.println("La conexión en ParteDao se ha cerrado con éxito");                           
            }          
          } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión en ParteDao: " + e.getMessage());
        }
    }
}
