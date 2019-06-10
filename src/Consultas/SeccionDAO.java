package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Utilitario.Autenticacion;

public class SeccionDAO {
	@SuppressWarnings("unused")
	private Autenticacion autenticacion = null;
	private Conexion conexion = null;
	//private boolean conectado = false;	
	private String query = null;
		public SeccionDAO(Autenticacion autenticacion){
			this.autenticacion = autenticacion;
			conexion = new Conexion(autenticacion);			
		}
	
		public int getIdSeccion(String nombreSeccion, String perido){
			int value = -1;
				if(conexion.conectar()){
					query = "SELECT id_seccion FROM seccion WHERE nombre = '"+ nombreSeccion+
					"' AND periodo = "+perido;
					ResultSet resultSet = conexion.consultar(query);
					try {
						if(resultSet.next()){
							value = resultSet.getInt("id_seccion");
						}// fin if 
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}// fin try - cathc

				}// fin if conexion
				conexion.desconectar();
					return value;
		}			

		
}
