package GestionarMaterias;

import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.ModeloTablaListado;
import Consultas.MateriasDAO;
import Entidades.Materia;

public class GestorMaterias {
	private VentanaListadoMaterias ventanaListado;
	private VentanaEditarMaterias ventanaEditar;
	private JDesktopPane desktopPanel;
	private Autenticacion autenticacion;
	private Materia seleccionado;
	
	public GestorMaterias(Autenticacion autenticacion, JDesktopPane desktopPanel) {
		this.autenticacion = autenticacion;
		this.desktopPanel = desktopPanel;
	}
	
	public void guardar(){
		seleccionado.setNombre(ventanaEditar.getTxtNombre().getText());
		if(seleccionado.getNombre().length()==0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El nombre es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}

		MateriasDAO dao = new MateriasDAO(autenticacion);
		if(!dao.validarMateria(seleccionado.getId_materia(), seleccionado.getNombre())){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Ya se encuentra una materia con el mismmo nombre", MostrarMensajes.MENSAJE_ERROR);
			dao.cerrarConexion();
			return ;
		}
		
		int errores = 0;
		if(seleccionado.getId_materia()==-1){
			seleccionado.setId_materia(dao.buscarNuevoId());
			seleccionado.setEstado(1);
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
	
	public void eliminar(int id_bloque){
		MateriasDAO dao = new MateriasDAO(autenticacion);
		boolean res = dao.eliminar(id_bloque);
		dao.cerrarConexion();
		
		if(res){
			MostrarMensajes.mostrarMensaje("Operación exitosa", MostrarMensajes.MENSAJE_EXITO);
			if(ventanaEditar!=null && seleccionado.getId_materia()==id_bloque){
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
		seleccionado = new Materia();
		seleccionado.setId_materia(-1);
		getVentanaEditar();		
		ventanaEditar.getTxtNombre().setText("");
		ventanaEditar.getBtnEliminar().setVisible(false);
		ventanaEditar.mostrarVentana();
	}
	
	public void prepararEditar(int id_bloque){
		MateriasDAO dao = new MateriasDAO(autenticacion);
		seleccionado = dao.buscar(id_bloque); 
		dao.cerrarConexion();
		if(seleccionado!=null){
			getVentanaEditar();			
			ventanaEditar.getTxtNombre().setText(seleccionado.getNombre());
			ventanaEditar.getBtnEliminar().setVisible(true);
			ventanaEditar.mostrarVentana();
		}		
	}
	
	public void prepararListar(){
		getVentanaListado();
		try{
			MateriasDAO dao = new MateriasDAO(autenticacion);
			ResultSet res = dao.getListadoMaterias();
			ModeloTablaListado modelo = ventanaListado.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();				
				itm.setValorBoolean(false);
				itm.setValorInt(res.getInt("id_materia"));				
				
				modelo.setValueAt(itm, cont, 0);
				modelo.setValueAt(res.getString("nombre"), cont, 1);
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
		}
		catch(Exception e){			
		}		
	}

	public void setVentanaListado(VentanaListadoMaterias ventana){
		ventanaListado = ventana;
	}
	
	public VentanaListadoMaterias getVentanaListado(){
		if(ventanaListado == null){
			ventanaListado = new VentanaListadoMaterias(desktopPanel);
			ventanaListado.setGestor(this);
		}
		
		return ventanaListado;
	}
	
	public Materia getSeleccionado() {
		return seleccionado;
	}

	public void setVentanaEditar(VentanaEditarMaterias ventana){
		ventanaEditar = ventana;
	}
	
	public VentanaEditarMaterias getVentanaEditar(){
		if(ventanaEditar == null){
			ventanaEditar = new VentanaEditarMaterias(desktopPanel);
			ventanaEditar.setGestor(this);			
		}
		
		return ventanaEditar;
	}
	
}
