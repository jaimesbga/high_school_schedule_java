package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Utilitario.Autenticacion;

public class SeccionesDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public SeccionesDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public ResultSet getSecciones(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_seccion, nombre ")
				 .append("FROM seccion ")
				 .append("WHERE estado = 1 ")
				 .append("ORDER BY nombre ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}
	
	public void cerrarConexion(){
		conexion.desconectar();
	}
	
	public boolean isConectado(){
		if(!conectado){
			return conexion.conectar(autoCommit);
		}
		return conectado;
	}
}
