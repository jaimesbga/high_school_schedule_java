package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;

import java.sql.ResultSet;

public class Conexion {
    private Connection conexion;
    private Autenticacion autenticacion;    

    public Conexion(Autenticacion a) {
        autenticacion = a;
        conexion = null;
    }

    /**
     * Método para conectarse a la base de datos
     * @return Retorna true si se pudo conectar o false en caso contrario
     */    
    public boolean conectar() {
        return conectar(true);
    }
    
    /**
     * Método para conectarse a la base de datos
     * @param autoCommit Parámetro boolen para fijar el autoCommit de la conexión
     * @return Retorna true si se pudo conectar o false en caso contrario
     */
    public boolean conectar(boolean autoCommit) {
        try {
            Class.forName("com.mysql.jdbc.Driver");            
            conexion = DriverManager.getConnection("jdbc:mysql://" + autenticacion.getIpBD() + "/liceo", autenticacion.getUsuarioBD(), autenticacion.getClaveBD());            
            conexion.setAutoCommit(autoCommit);
            return true;
        }
        catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
    }

    /**
     * Método que sirve para cerrar la conexion de la base de datos
     */
    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
            }
            catch (Exception e) {
            }
        }
    }
    
    /**
     * Método que sirve para cerrar la conexion de la base de datos
     * @param resultSet ResultSet para cerrar la conexión
     */
    public void desconectar(ResultSet resultSet){
    	try{
    		resultSet.close();
    		resultSet.getStatement().close();
    		conexion.close();
    	}
    	catch(Exception e){    		
    	}
    }
    
    /**
     * Método que consulta en la base de datos
     * @param query Consulta que se realizará en la base de datos
     * @return ResultSet con los resultados de la consulta. NULL en caso de haber un error
     */
    public ResultSet consultar(String query){
        try{
        	Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            return rs;
        }
        catch(Exception e){
        	if(VariablesGlobales.DEBUG){
        		System.out.println("[Error] Conexion.consultar(): "+query);
        		e.printStackTrace();
        	}
        	
            return null;
        }
    }
    
    /**
     * Método para realizar insert, update o delete en la base de datos
     * @param query Consulta que se realizará en la base de datos
     * @return Retorna true si se ejecuto exitosamente, false en caso contrario
     */
    public boolean actualizar(String query){
        try{
        	Statement statement = conexion.createStatement();        	
        	statement.executeUpdate(query);
        	statement.close();
            return true;
        }
        catch(Exception e){
        	System.out.println("Error Conexion "+query);
        	e.printStackTrace();
            return false;
        }        
    }
   
    /**
     * Método para realizar commit a las transacciones
     * @return Retorna true si se ejecuto exitosamente, false en caso contrario
     */
    public boolean commit(){
    	try{
    		conexion.commit();
    		return true;
    	}
    	catch (Exception e) {
    		if(VariablesGlobales.DEBUG){
        		System.out.println("[Error] Conexion.commit()");
        		e.printStackTrace();
        	}
    		return false;
		}
    }
    
    /**
     * Método que realiza rollback en caso necesario
     * @return Retorna true si se ejecuto exitosamente, false en caso contrario
     */
    public boolean roollback(){
    	try{
    		conexion.rollback();
    		return true;
    	}
    	catch (Exception e) {
    		if(VariablesGlobales.DEBUG){
        		System.out.println("[Error] Conexion.rollback()");
        		e.printStackTrace();
        	}
    		return false;
		}
    }
    
    public Connection getConexion(){
    	return conexion;
    }
}
