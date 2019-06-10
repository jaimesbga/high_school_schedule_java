package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Persona;
import Utilitario.Autenticacion;
import Utilitario.UtilFechas;
import Utilitario.VariablesGlobales;

public class PersonasDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public PersonasDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public PersonasDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public boolean validarPersona(int id_persona, String nombre){
		boolean valor = false;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*)")
				 .append(" FROM persona")
				 .append(" WHERE id_persona!=")
				 .append(id_persona)				 
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
					System.out.println("Error: PersonasDAO.validarPersona(): "+e.getMessage());
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
			query.append("SELECT IFNULL((SELECT MAX(id_persona)+1 FROM persona), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					id = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: PersonasDAO.buscarNuevoId(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		return id;
	}
	
	public Persona buscar(int id_persona){
		Persona persona = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_persona, nombre, fecha_nacimiento, direccion, telefono, correo, sexo, cedula ")
				 .append("FROM persona ")
				 .append("WHERE id_persona=")
				 .append(id_persona);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					persona = new Persona();
					persona.setId_persona(res.getInt("id_persona"));					
					persona.setNombre(res.getString("nombre"));
					persona.setFecha_nacimiento(res.getDate("fecha_nacimiento"));
					persona.setDireccion(res.getString("direccion"));
					persona.setTelefono(res.getString("telefono"));
					persona.setCorreo(res.getString("correo"));
					persona.setSexo(res.getString("sexo"));
					persona.setCedula(res.getString("cedula"));
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: PersonasDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return persona;
	}
	
	public Persona buscarCedula(String cedula){
		Persona persona = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_persona, nombre, fecha_nacimiento, direccion, telefono, correo, sexo, cedula ")
				 .append("FROM persona ")
				 .append("WHERE cedula='")
				 .append(cedula)
				 .append("'");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					persona = new Persona();
					persona.setId_persona(res.getInt("id_persona"));					
					persona.setNombre(res.getString("nombre"));
					persona.setFecha_nacimiento(res.getDate("fecha_nacimiento"));
					persona.setDireccion(res.getString("direccion"));
					persona.setTelefono(res.getString("telefono"));
					persona.setCorreo(res.getString("correo"));
					persona.setSexo(res.getString("sexo"));
					persona.setCedula(res.getString("cedula"));
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: PersonasDAO.buscarCedula(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return persona;
	}
	
	public boolean editar(Persona persona){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE persona ")
				 .append("SET nombre='")
				 .append(persona.getNombre())
				 .append("', fecha_nacimiento='")
				 .append(UtilFechas.convertirFecha(persona.getFecha_nacimiento(), UtilFechas.YYYY_MM_DD))
				 .append("', direccion='")
				 .append(persona.getDireccion())
				 .append("', telefono='")
				 .append(persona.getTelefono())
				 .append("', correo='")
				 .append(persona.getCorreo())
				 .append("', sexo='")
				 .append(persona.getSexo())
				 .append("', cedula='")
				 .append(persona.getCedula())
				 .append("' WHERE id_persona=")
				 .append(persona.getId_persona());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Persona persona){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO persona(id_persona, nombre, fecha_nacimiento, direccion, telefono, correo, sexo, cedula) ")
				 .append("VALUES(")
				 .append(persona.getId_persona()).append(", '")				 
				 .append(persona.getNombre()).append("', '")
				 .append(UtilFechas.convertirFecha(persona.getFecha_nacimiento(), UtilFechas.YYYY_MM_DD)).append("', '")
				 .append(persona.getDireccion()).append("', '")				 
				 .append(persona.getTelefono()).append("', '")				 
				 .append(persona.getCorreo()).append("', '")				 
				 .append(persona.getSexo()).append("', '")
				 .append(persona.getCedula()).append("'")
				 .append(")");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_persona){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM persona ")
				 .append("WHERE id_persona=")
				 .append(id_persona);
			
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

	public Conexion getConexion() {
		return conexion;
	}	
}