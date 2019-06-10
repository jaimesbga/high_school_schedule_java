package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Estudiante;
import Entidades.Seccion;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;

public class EstudiantesDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public EstudiantesDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public EstudiantesDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public ResultSet getListadoEstudiantes(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT p.id_persona AS id_persona, p.nombre AS nombre, s.nombre AS seccion, e.numero_lista AS numero_lista, p.cedula AS cedula ")				 
				 .append("FROM estudiante e, persona p, seccion s ")
				 .append("WHERE e.id_persona = p.id_persona ")
				 .append("AND s.id_seccion = e.id_seccion ")
				 .append("AND e.estado = 1 ")
				 .append("ORDER BY nombre ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}	

	public ResultSet getListadoEstudiantes(int id_seccion){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT p.id_persona AS id_persona, p.nombre AS nombre, s.nombre AS seccion, e.numero_lista AS numero_lista ")				 
				 .append("FROM estudiante e, persona p, seccion s ")
				 .append("WHERE e.id_persona = p.id_persona ")
				 .append("AND s.id_seccion = e.id_seccion ")
				 .append("AND e.estado = 1 AND s.id_seccion ="+id_seccion)
				 .append(" ORDER BY numero_lista, nombre ASC");
			
			//System.out.println(query.toString());
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
	
	public boolean validarEstudiante(int id_persona, String nombre){
		boolean valor = false;
		
		PersonasDAO personasDAO = new PersonasDAO(autenticacion);
		valor = personasDAO.validarPersona(id_persona, nombre);
		personasDAO.cerrarConexion();
		
		return valor;
	}
	
	public boolean validarNumeroLista(int id_persona, int id_seccion, int numero_lista){
		boolean valor = false;
		
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) ")
				 .append("FROM estudiante ")
				 .append("WHERE id_persona!=")
				 .append(id_persona)
				 .append(" AND id_seccion=")
				 .append(id_seccion)
				 .append(" AND numero_lista=")
				 .append(numero_lista);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					valor = res.getInt(1)==0;
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: EstudiantesDAO.validarNumeroLista(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		
		return valor;
	}
	
	public int buscarNumeroLista(int id_seccion){
		int numero = 1;
		
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT IFNULL((SELECT MAX(numero_lista)+1 FROM estudiante ")
				 .append("WHERE id_seccion=").append(id_seccion)
				 .append("), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					numero = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: EstudiantesDAO.buscarNuevoId(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		
		return numero;
	}
	
	public Estudiante buscar(int id_persona){
		Estudiante estudiante = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT numero_lista, estado, nombre_representante, ")
				 .append("cedula_representante, telefono_representante, id_seccion ")
				 .append("FROM estudiante ")
				 .append("WHERE id_persona=")
				 .append(id_persona);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					estudiante = new Estudiante();
					estudiante.setSeccion(new Seccion());
					estudiante.setNumero_lista(res.getInt("numero_lista"));
					estudiante.setEstado(res.getInt("estado"));
					estudiante.setNombre_representante(res.getString("nombre_representante"));
					estudiante.setCedula_representante(res.getString("cedula_representante"));
					estudiante.setTelefono_representante(res.getString("telefono_representante"));
					estudiante.getSeccion().setId_seccion(res.getInt("id_seccion"));					
					
					PersonasDAO personasDAO = new PersonasDAO(autenticacion);
					estudiante.setPersona(personasDAO.buscar(id_persona));
					personasDAO.cerrarConexion();
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: EstudiantesDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return estudiante;
	}
	
	public boolean editar(Estudiante estudiante){
		if(isConectado()){
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			personasDAO.editar(estudiante.getPersona());
			personasDAO.cerrarConexion();
			
			StringBuilder query = new StringBuilder();
			query.append("UPDATE estudiante ")
				 .append("SET numero_lista=").append(estudiante.getNumero_lista())
				 .append(", estado=").append(estudiante.getEstado())
				 .append(", nombre_representante='").append(estudiante.getNombre_representante())
				 .append("', cedula_representante='").append(estudiante.getCedula_representante())
				 .append("', telefono_representante='").append(estudiante.getTelefono_representante())
				 .append("', id_seccion=").append(estudiante.getSeccion().getId_seccion())
				 .append(" WHERE id_persona=")
				 .append(estudiante.getPersona().getId_persona());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Estudiante estudiante){
		if(isConectado()){			
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			personasDAO.guardar(estudiante.getPersona());
			personasDAO.cerrarConexion();
			
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO estudiante(id_persona, numero_lista, nombre_representante, ")
				 .append("cedula_representante, telefono_representante, id_seccion) ")
				 .append("VALUES(")
				 .append(estudiante.getPersona().getId_persona()).append(", ")				 
				 .append(estudiante.getNumero_lista())
				 .append(", '").append(estudiante.getNombre_representante())
				 .append("', '").append(estudiante.getCedula_representante())
				 .append("', '").append(estudiante.getTelefono_representante())
				 .append("', ").append(estudiante.getSeccion().getId_seccion())
				 .append(")");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_persona){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE estudiante ")
				 .append("SET estado=0 ")
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
