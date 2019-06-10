package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Utilitario.Autenticacion;

public class InasistenciasDAO {

	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public InasistenciasDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public InasistenciasDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public void guardarInasistencia(int id_actividad,int id_horario,int id_estudiante){
		if(conexion.conectar()){
			String query = "INSERT IGNORE INTO inasistencias VALUES("+
				id_actividad+","+id_horario+","+id_estudiante+")";
				conexion.actualizar(query);
				conexion.desconectar();
		}
	}
	
	public boolean siExiste(int id_actividad,int id_horario,int id_estudiante){
		if(conexion.conectar()){
			String query = "SELECT * FROM inasistencias WHERE id_actividadesdiarias = "+id_actividad +
			" AND id_horario="+id_horario+" AND id_estudiante="+id_estudiante;
				try {
					ResultSet resultSet = conexion.consultar(query);
						if(resultSet.next()){
							return true;
						}else{
							return false;
						}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			  
				
		}
		return false;
	}
	
	public void desconectarInasistenciasDAO(){
		if(isConectado()){
		conexion.desconectar();
		}
	}
	
	public boolean isConectado(){
		if(!conectado){
			return conexion.conectar(autoCommit);
		}
		return conectado;
	}
}
