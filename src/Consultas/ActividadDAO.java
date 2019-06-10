package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Actividad;
import Utilitario.Autenticacion;
import Utilitario.UtilFechas;

public class ActividadDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public ActividadDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public ActividadDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public boolean isConectado(){
		if(!conectado){
			return conexion.conectar(autoCommit);
		}
		return conectado;
	}
//------------------------------------------------------------------------	
	public int buscarNuevoId(){
		int retorno = -1;
		String query = "SELECT IFNULL(MAX(id)+1,1) as id_actividad " +
				       " FROM `actividadesdiarias` WHERE 1";
		if(isConectado()){
				try {
					ResultSet resultSet = conexion.consultar(query);
					
					if(resultSet.next())
						retorno = resultSet.getInt("id_actividad");
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
			return retorno;
	}
//----------------------------------------------------------------------------	
	public int existeActividad(int id_seccion, java.util.Date fecha){
		int retorno = -1;
			
		String fech = fechaDateChooserTofechaSQL(fecha);
		
			//System.out.print(fech);
			
			String query = "SELECT id FROM `actividadesdiarias`" +
					" WHERE fecha = '"+ fech+"'  AND id_seccion = "+id_seccion;
				if(isConectado()){
						try {
							ResultSet resultSet = conexion.consultar(query);
						if(resultSet.next()){
								retorno = resultSet.getInt(1);							
						}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
				}
		return retorno;
	}
//-------------------------------------------------------------------------------------
	public String fechaDateChooserTofechaSQL(java.util.Date fecha){						
			return UtilFechas.convertirFecha(fecha, UtilFechas.YYYY_MM_DD);
	}
//----------------------------------------------------------------------------------
	public boolean guardarActividad(Actividad actividad){
		boolean retorno = false;
			StringBuilder query = new StringBuilder();			
				query.append("INSERT INTO actividadesdiarias VALUES(")
					  .append(actividad.getId_actividad()+",'")
					  .append(actividad.getFecha()+"',")
					  .append(actividad.getDia_semana()+",")
					  .append(actividad.getId_seccion()+",")
					  .append(actividad.getTipo_actividad()+",'")
					  .append(actividad.getObservaciones()+"')");
			if(isConectado()){			
				try {
					//System.out.println(query.toString());
					conexion.actualizar(query.toString());
					retorno = true;
				} catch (Exception e) {
					// TODO: handle exception
				}					
					
			}	
		return retorno;
	}
//----------------------------------------------------------------------------------------
	public Actividad getActividad(int id_actividad){
		Actividad actividad = new Actividad();
		if(isConectado()){
			String query = "SELECT * FROM actividadesdiarias WHERE id = "+id_actividad;
				try {
					ResultSet resultSet = conexion.consultar(query);
						if(resultSet.next()){
							actividad.setId_actividad(id_actividad);
							actividad.setFecha(resultSet.getString("fecha"));
							actividad.setDia_semana(resultSet.getInt("dia_semana"));
							actividad.setId_seccion(resultSet.getInt("id_seccion"));
							actividad.setTipo_actividad(resultSet.getInt("tipo_actividad"));
							actividad.setObservaciones(resultSet.getString("observacion"));
						}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			
		}
			return actividad;
	}
	
}
