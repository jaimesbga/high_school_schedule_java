package Consultas;

import java.sql.ResultSet;

import ConexionBD.Conexion;
import Entidades.Bloque;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;

public class BloquesDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;
	private boolean autoCommit;
	
	public BloquesDAO(Autenticacion autenticacion) {
		this.autenticacion = autenticacion;
		autoCommit = true;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public BloquesDAO(Autenticacion autenticacion, boolean autoCommit) {
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
		this.autoCommit = autoCommit;
	}
	
	public ResultSet getBloques(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_bloque, numero, descripcion ")
				 .append("FROM bloque ")
				 .append("WHERE estado = 1 ")
				 .append("ORDER BY numero ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}
	
	public boolean validarBloque(int id_bloque, int numero, String descripcion){
		boolean valor = false;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*)")
				 .append(" FROM bloque")
				 .append(" WHERE id_bloque!=")
				 .append(id_bloque)
				 .append(" AND estado=1")
				 .append(" AND (numero=")
				 .append(numero)
				 .append(" OR LOWER(descripcion)='")
				 .append(descripcion.toLowerCase())
				 .append("')");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					valor = res.getInt(1)==0;
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: BloquesDAO.buscar(): "+e.getMessage());
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
			query.append("SELECT IFNULL((SELECT MAX(id_bloque)+1 FROM bloque), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					id = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: BloquesDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		return id;
	}
	
	public int buscarNuevoNumeroBloque(){
		int id = 1;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT IFNULL((SELECT MAX(numero)+1 FROM bloque), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					id = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: BloquesDAO.buscarNuevoNumeroBloque(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		return id;
	}
	
	public Bloque buscar(int id_bloque){
		Bloque bloque = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_bloque, numero, descripcion, estado ")
				 .append("FROM bloque ")
				 .append("WHERE id_bloque=")
				 .append(id_bloque);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					bloque = new Bloque();
					bloque.setId_bloque(res.getInt("id_bloque"));
					bloque.setNumero(res.getInt("numero"));
					bloque.setDescripcion(res.getString("descripcion"));
					bloque.setEstado(res.getInt("estado"));
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: BloquesDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return bloque;
	}
	
	public boolean editar(Bloque bloque){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE bloque ")
				 .append("SET numero=")
				 .append(bloque.getNumero())
				 .append(", descripcion='")
				 .append(bloque.getDescripcion())
				 .append("', estado=")
				 .append(bloque.getEstado())
				 .append(" WHERE id_bloque=")
				 .append(bloque.getId_bloque());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Bloque bloque){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO bloque(id_bloque, numero, descripcion, estado) ")
				 .append("VALUES(")
				 .append(bloque.getId_bloque()).append(", ")
				 .append(bloque.getNumero()).append(", '")
				 .append(bloque.getDescripcion()).append("', ")
				 .append(bloque.getEstado())
				 .append(")");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_bloque){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE bloque ")
				 .append("SET estado=0 ")
				 .append("WHERE id_bloque=")
				 .append(id_bloque);
			
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
