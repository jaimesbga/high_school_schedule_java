package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Profesor;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;

public class ProfesoresDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public ProfesoresDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public ProfesoresDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public ResultSet getListadoProfesores(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT pe.id_persona AS id_persona, pe.nombre AS nombre, pe.fecha_nacimiento AS fecha_nacimiento, ")
				 .append("pe.direccion AS direccion, pe.telefono AS telefono, pe.correo AS correo, pe.sexo AS sexo, pr.titulo AS titulo, pe.cedula AS cedula ")
				 .append("FROM profesor pr, persona pe ")
				 .append("WHERE pr.id_persona = pe.id_persona ")
				 .append("ORDER BY nombre ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}
	
	public int buscarNuevoId(){
		int id = 1;
		PersonasDAO personasDAO = new PersonasDAO(autenticacion);
		id = personasDAO.buscarNuevoId();
		personasDAO.cerrarConexion();
		
		return id;
	}
	
	public boolean validarProfesor(int id_persona, String nombre){
		boolean valor = false;
		
		PersonasDAO personasDAO = new PersonasDAO(autenticacion);
		valor = personasDAO.validarPersona(id_persona, nombre);
		personasDAO.cerrarConexion();
		
		return valor;
	}
	
	public Profesor buscar(int id_persona){
		Profesor profesor = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT titulo ")
				 .append("FROM profesor ")
				 .append("WHERE id_persona=")
				 .append(id_persona);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					profesor = new Profesor();										
					profesor.setTitulo(res.getInt("titulo"));
					
					PersonasDAO personasDAO = new PersonasDAO(autenticacion);
					profesor.setPersona(personasDAO.buscar(id_persona));
					personasDAO.cerrarConexion();
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: ProfesoresDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return profesor;
	}
	
	public boolean editar(Profesor profesor){
		if(isConectado()){
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			personasDAO.editar(profesor.getPersona());
			personasDAO.cerrarConexion();
			
			StringBuilder query = new StringBuilder();
			query.append("UPDATE profesor ")
				 .append("SET titulo=")
				 .append(profesor.getTitulo())
				 .append(" WHERE id_persona=")
				 .append(profesor.getPersona().getId_persona());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Profesor profesor){
		if(isConectado()){			
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			personasDAO.guardar(profesor.getPersona());
			personasDAO.cerrarConexion();
			
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO profesor(id_persona, titulo) ")
				 .append("VALUES(")
				 .append(profesor.getPersona().getId_persona()).append(", ")				 
				 .append(profesor.getTitulo())
				 .append(")");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_persona){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM profesor ")				 
				 .append("WHERE id_persona=")
				 .append(id_persona);
			
			boolean res =  conexion.actualizar(query.toString());
			
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			res = res && personasDAO.eliminar(id_persona);
			personasDAO.cerrarConexion();
			
			return res;
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
