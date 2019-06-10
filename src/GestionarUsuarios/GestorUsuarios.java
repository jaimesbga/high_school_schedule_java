package GestionarUsuarios;

import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.ModeloTablaListado;
import Consultas.UsuariosDAO;
import Entidades.Usuario;;

public class GestorUsuarios {
	private VentanaListadoUsuarios ventanaListado;
	private VentanaEditarUsuarios ventanaEditar;
	private JDesktopPane desktopPanel;
	private Autenticacion autenticacion;
	private Usuario seleccionado;
	
	public GestorUsuarios(Autenticacion autenticacion, JDesktopPane desktopPanel) {
		this.autenticacion = autenticacion;
		this.desktopPanel = desktopPanel;
	}
	
	public void guardar(){
		seleccionado.setNombre(ventanaEditar.getTxtNombre().getText());
		if(seleccionado.getNombre().length()==0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El nombre es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		seleccionado.setUsuario(ventanaEditar.getTxtUsuario().getText());
		if(seleccionado.getUsuario().length()==0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El usuario es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		String clave1 = String.valueOf(ventanaEditar.getTxtClave1().getPassword());
		String clave2 = String.valueOf(ventanaEditar.getTxtClave2().getPassword());
		
		if(clave1.length() == 0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La clave es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		if(clave1.compareTo(clave2) == 0){
			seleccionado.setClave(clave1);
		}
		else{
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Las claves no coinciden", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}

		UsuariosDAO dao = new UsuariosDAO(autenticacion);
		if(!dao.validarExistencia(seleccionado.getId_usuario(), seleccionado.getUsuario())){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Ya se encuentra el usuario registrado", MostrarMensajes.MENSAJE_ERROR);
			dao.cerrarConexion();
			return ;
		}
		
		int errores = 0;
		if(seleccionado.getId_usuario()==-1){
			seleccionado.setId_usuario(dao.buscarNuevoId());			
			errores = dao.guardar(seleccionado) ? 0 : 1;
		}
		else{
			errores = dao.editar(seleccionado) ? 0 : 1;
		}
		dao.cerrarConexion();
		
		if(errores == 0){
			ventanaEditar.getBtnEliminar().setVisible(true);
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Operación exitosa", MostrarMensajes.MENSAJE_EXITO);
			if(ventanaListado!=null){
				prepararListar();
			}
		}
		else{
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Ocurrió un error al guardar", MostrarMensajes.MENSAJE_ERROR);
		}
	}
	
	public void eliminar(int id_usuario){
		UsuariosDAO dao = new UsuariosDAO(autenticacion);
		boolean res = dao.eliminar(id_usuario);
		dao.cerrarConexion();
		
		if(res){
			MostrarMensajes.mostrarMensaje("Operación exitosa", MostrarMensajes.MENSAJE_EXITO);
			if(ventanaEditar!=null && seleccionado.getId_usuario()==id_usuario){
				ventanaEditar.getVentana().dispose();
				setVentanaEditar(null);
			}
			
			if(ventanaListado!=null){
				prepararListar();
			}
		}else{
			MostrarMensajes.mostrarMensaje("Ocurrió un error al eliminar", MostrarMensajes.MENSAJE_ERROR);			
		}		
		
	}
	
	public void prepararCrear(){
		seleccionado = new Usuario();
		seleccionado.setId_usuario(-1);
		getVentanaEditar();		
		ventanaEditar.getTxtNombre().setText("");
		ventanaEditar.getTxtUsuario().setText("");
		ventanaEditar.getTxtClave1().setText("");
		ventanaEditar.getTxtClave2().setText("");
		ventanaEditar.getBtnEliminar().setVisible(false);
		ventanaEditar.mostrarVentana();
	}
	
	public void prepararEditar(int id_usuario){
		UsuariosDAO dao = new UsuariosDAO(autenticacion);
		seleccionado = dao.buscar(id_usuario); 
		dao.cerrarConexion();
		if(seleccionado!=null){
			getVentanaEditar();			
			ventanaEditar.getTxtNombre().setText(seleccionado.getNombre());
			ventanaEditar.getTxtUsuario().setText(seleccionado.getUsuario());
			ventanaEditar.getTxtClave1().setText(seleccionado.getClave());
			ventanaEditar.getTxtClave2().setText(seleccionado.getClave());
			ventanaEditar.getBtnEliminar().setVisible(true);
			ventanaEditar.mostrarVentana();
		}		
	}
	
	public void prepararListar(){
		getVentanaListado();
		try{
			UsuariosDAO dao = new UsuariosDAO(autenticacion);
			ResultSet res = dao.getListadoUsuarios();
			ModeloTablaListado modelo = ventanaListado.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();				
				itm.setValorBoolean(false);
				itm.setValorInt(res.getInt("id_usuario"));				
				
				modelo.setValueAt(itm, cont, 0);
				modelo.setValueAt(res.getString("nombre"), cont, 1);
				modelo.setValueAt(res.getString("usuario"), cont, 2);
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
		}
		catch(Exception e){			
		}		
	}

	public void setVentanaListado(VentanaListadoUsuarios ventana){
		ventanaListado = ventana;
	}
	
	public VentanaListadoUsuarios getVentanaListado(){
		if(ventanaListado == null){
			ventanaListado = new VentanaListadoUsuarios(desktopPanel);
			ventanaListado.setGestor(this);
		}
		
		return ventanaListado;
	}
	
	public Usuario getSeleccionado() {
		return seleccionado;
	}

	public void setVentanaEditar(VentanaEditarUsuarios ventana){
		ventanaEditar = ventana;
	}
	
	public VentanaEditarUsuarios getVentanaEditar(){
		if(ventanaEditar == null){
			ventanaEditar = new VentanaEditarUsuarios(desktopPanel);
			ventanaEditar.setGestor(this);			
		}
		
		return ventanaEditar;
	}
	
}
