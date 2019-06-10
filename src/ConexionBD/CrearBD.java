package ConexionBD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import Utilitario.Path;

public class CrearBD {
	public CrearBD(){		
	}
	
	public boolean ejecutar(String usuario, String clave){
		boolean res = false;
		try {
            Class.forName("com.mysql.jdbc.Driver");            
            //Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/?user="+usuario+"&password="+clave);            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/?", usuario, clave);
            
            Statement statement = conexion.createStatement();        	
        	statement.executeUpdate("CREATE DATABASE IF NOT EXISTS liceo");
        	statement.close();
        	conexion.close();
        	
        	conexion = DriverManager.getConnection("jdbc:mysql://localhost/liceo", usuario, clave);        	
        	statement = conexion.createStatement();
        	
        	FileReader fr = new FileReader(Path.getPath("Archivos/Configuracion/BD.sql"));
        	BufferedReader br = new BufferedReader(fr);
        	
        	StringBuilder sentencia = new StringBuilder("");
        	String lee = "";
        	while((lee = br.readLine()) != null){
        		if(lee.length()>0){
        			lee.replaceAll("\n", " ");
        			
        			sentencia.append(lee);
        			
        			if(lee.indexOf(";") >= 0){
        				statement.executeUpdate(sentencia.toString());
        				sentencia = new StringBuilder("");        				
        			}
        		}
        	}
        	
        	br.close();
        	fr.close();
        	
            res = true;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	res = false;
        }        
        
        return res;
	}
}
