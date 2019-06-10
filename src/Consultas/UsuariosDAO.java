package Consultas;

import java.sql.ResultSet;
import Utilitario.Autenticacion;
import Utilitario.VariablesGlobales;
import ConexionBD.Conexion;
import Entidades.Usuario;

public class UsuariosDAO {
	private Autenticacion autenticacion;
	private Conexion conexion;
	private boolean conectado;	
	
	public UsuariosDAO(Autenticacion autenticacion){
		this.autenticacion = autenticacion;
		conexion = new Conexion(this.autenticacion);
		conectado = false;
	}
	
	public Usuario validarUsuario(String _usuario, String _clave){
		Conexion con = new Conexion(autenticacion);
		Usuario usuario = null;
		try{
			if(con.conectar()){
				StringBuilder query = new StringBuilder();
				query.append("SELECT id_usuario, nombre ")
					 .append("FROM usuario ")
					 .append("WHERE usuario = '")
					 .append(_usuario)
					 .append("' AND clave = '")
					 .append(_clave)
					 .append("'");
				
				ResultSet res = con.consultar(query.toString());
				if(res != null){
					if(res.next()){
						usuario = new Usuario();
						usuario.setId_usuario(res.getInt("id_usuario"));
						usuario.setNombre(res.getString("nombre"));
					}					
				}
			}
		}
		catch(Exception e){
			if(VariablesGlobales.DEBUG){
				System.out.println("[ERROR] UsuariosDAO.validarUsuario(): "+e.getMessage());
				e.printStackTrace();
			}
		}
		finally{
			con.desconectar();
		}
		
		return usuario;
	}

	public ResultSet getListadoUsuarios(){
		ResultSet res = null;
		if(isConectado()){			
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_usuario, nombre, usuario ")
				 .append("FROM usuario ")				 
				 .append("ORDER BY nombre ASC");
			
			res = conexion.consultar(query.toString()); 
		}
		
		return res;
	}
	
	public boolean validarExistencia(int id_usuario, String usuario){
		boolean valor = false;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*)")
				 .append(" FROM usuario")
				 .append(" WHERE id_usuario!=")
				 .append(id_usuario)				 
				 .append(" AND LOWER(usuario)='")
				 .append(usuario.toLowerCase())
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
					System.out.println("Error: UsuariosDAO.validarExistencia(): "+e.getMessage());
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
			query.append("SELECT IFNULL((SELECT MAX(id_usuario)+1 FROM usuario), 1)");
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					id = res.getInt(1);
				}
				res.close();
			}			
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: UsuariosDAO.buscarNuevoId(): "+e.getMessage());
					e.printStackTrace();
				}
			}			
		}
		return id;
	}
	
	public Usuario buscar(int id_usuario){
		Usuario usuario = null;
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("SELECT id_usuario, nombre, usuario, clave ")
				 .append("FROM usuario ")
				 .append("WHERE id_usuario=")
				 .append(id_usuario);
			
			ResultSet res = conexion.consultar(query.toString());
			try{
				if(res.next()){
					usuario = new Usuario();
					usuario.setId_usuario(res.getInt("id_usuario"));					
					usuario.setNombre(res.getString("nombre"));
					usuario.setUsuario(res.getString("usuario"));
					usuario.setClave(res.getString("clave"));
				}
			}
			catch(Exception e){
				if(VariablesGlobales.DEBUG){
					System.out.println("Error: UsuariosDAO.buscar(): "+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return usuario;
	}
	
	public boolean editar(Usuario usuario){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("UPDATE usuario ")
				 .append("SET nombre='")
				 .append(usuario.getNombre())
				 .append("', usuario='")
				 .append(usuario.getUsuario())
				 .append("', clave='")
				 .append(usuario.getClave())
				 .append("' WHERE id_usuario=")
				 .append(usuario.getId_usuario());
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public boolean guardar(Usuario usuario){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO usuario(id_usuario, nombre, usuario, clave) ")
				 .append("VALUES(")
				 .append(usuario.getId_usuario()).append(", '")				 
				 .append(usuario.getNombre()).append("', '")
				 .append(usuario.getUsuario()).append("', '")
				 .append(usuario.getClave())
				 .append("')");				 
			
			return conexion.actualizar(query.toString());
		}
		
		return false;
	}
	
	public boolean eliminar(int id_usuario){
		if(isConectado()){
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM usuario ")				 
				 .append("WHERE id_USUARIO=")
				 .append(id_usuario);
			
			return conexion.actualizar(query.toString());
		}
		return false;
	}
	
	public void cerrarConexion(){
		conexion.desconectar();
	}
	
	public boolean isConectado(){
		if(!conectado){
			return conexion.conectar(true);
		}
		return conectado;
	}
}
