package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Materia;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;

public class MateriasDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public MateriasDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public MateriasDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public ResultSet getListadoMaterias(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_materia, nombre, estado ")
				 .append("FROM materia ")
				 .append("WHERE estado = 1 ")
				 .append("ORDER BY nombre ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}
	
	public boolean validarMateria(int id_materia, String nombre){
		boolean valor = false;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*)")
				 .append(" FROM materia")
				 .append(" WHERE id_materia!=")
				 .append(id_materia)
				 .append(" AND estado=1")
				 .append(" AND LOWER(nombre)='")
				 .append(nombre.toLowerCase())
				 .append("'");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					valor = res.getInt(1)==0;
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: MateriasDAO.validarMateria(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		
		return valor;
	}
	
	public int buscarNuevoId(){
		int id = 1;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT IFNULL((SELECT MAX(id_materia)+1 FROM materia), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					id = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: MateriasDAO.buscarNuevoId(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		return id;
	}
	
	public Materia buscar(int id_materia){
		Materia materia = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_materia, nombre, estado ")
				 .append("FROM materia ")
				 .append("WHERE id_materia=")
				 .append(id_materia);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					materia = new Materia();
					materia.setId_materia(res.getInt("id_materia"));					
					materia.setNombre(res.getString("nombre"));
					materia.setEstado(res.getInt("estado"));
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: MateriasDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return materia;
	}
	
	public boolean editar(Materia materia){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE materia ")
				 .append("SET nombre='")
				 .append(materia.getNombre())
				 .append("', estado=")
				 .append(materia.getEstado())
				 .append(" WHERE id_materia=")
				 .append(materia.getId_materia());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Materia materia){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO materia(id_materia, nombre, estado) ")
				 .append("VALUES(")
				 .append(materia.getId_materia()).append(", '")				 
				 .append(materia.getNombre()).append("', ")
				 .append(materia.getEstado())
				 .append(")");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_materia){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE materia ")
				 .append("SET estado=0 ")
				 .append("WHERE id_materia=")
				 .append(id_materia);
			
			return conexion.actualizar(query.toString());
		}
		return false;
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
