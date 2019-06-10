package GestionarBloques;

import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.ModeloTablaListado;
import Consultas.BloquesDAO;
import Entidades.Bloque;

public class GestorBloques {
	private VentanaListadoBloques ventanaListado;
	private VentanaEditarBloques ventanaEditar;
	private JDesktopPane desktopPanel;
	private Autenticacion autenticacion;
	private Bloque seleccionado;
	
	public GestorBloques(Autenticacion autenticacion, JDesktopPane desktopPanel) {
		this.autenticacion = autenticacion;
		this.desktopPanel = desktopPanel;
	}
	
	public void guardar(){
		BloquesDAO dao = new BloquesDAO(autenticacion);
		if(seleccionado.getId_bloque()==-1 && ventanaEditar.getTxtNumero().getText().length()==0){
			seleccionado.setNumero(dao.buscarNuevoNumeroBloque());
			ventanaEditar.getTxtNumero().setText(String.valueOf(seleccionado.getNumero()));
		}
		try{
			seleccionado.setNumero(Integer.parseInt(ventanaEditar.getTxtNumero().getText()));
		}
		catch(Exception e){
			dao.cerrarConexion();
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El número de bloque debe ser un valor numérico", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		seleccionado.setDescripcion(ventanaEditar.getTxtDescripcion().getText());
		if(seleccionado.getDescripcion().length()==0){
			dao.cerrarConexion();
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La descripción no puede estar vacía", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}

		
		if(!dao.validarBloque(seleccionado.getId_bloque(), seleccionado.getNumero(), seleccionado.getDescripcion())){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El número de bloque o la descripción ya se encuentran", MostrarMensajes.MENSAJE_ERROR);
			dao.cerrarConexion();
			return ;
		}
		
		int errores = 0;
		if(seleccionado.getId_bloque() == -1){
			seleccionado.setId_bloque(dao.buscarNuevoId());
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
		BloquesDAO dao = new BloquesDAO(autenticacion);
		boolean res = dao.eliminar(id_bloque);
		dao.cerrarConexion();
		
		if(res){
			MostrarMensajes.mostrarMensaje("Operación exitosa", MostrarMensajes.MENSAJE_EXITO);
			if(ventanaEditar!=null && seleccionado.getId_bloque()==id_bloque){
				ventanaEditar.getVentana().dispose();
				setVentanaEditar(null);
			}
			
			if(ventanaListado!=null){
				prepararListar();
			}
		}else{			
			
		}		
		
	}
	
	public void prepararCrear(){
		seleccionado = new Bloque();
		seleccionado.setId_bloque(-1);
		getVentanaEditar();
		ventanaEditar.getTxtNumero().setText("");
		ventanaEditar.getTxtDescripcion().setText("");
		ventanaEditar.getBtnEliminar().setVisible(false);
		ventanaEditar.mostrarVentana();
	}
	
	public void prepararEditar(int id_bloque){
		BloquesDAO dao = new BloquesDAO(autenticacion);
		seleccionado = dao.buscar(id_bloque); 
		dao.cerrarConexion();
		if(seleccionado!=null){
			getVentanaEditar();
			ventanaEditar.getTxtNumero().setText(String.valueOf(seleccionado.getNumero()));
			ventanaEditar.getTxtDescripcion().setText(seleccionado.getDescripcion());
			ventanaEditar.getBtnEliminar().setVisible(true);
			ventanaEditar.mostrarVentana();
		}		
	}
	
	public void prepararListar(){
		getVentanaListado();
		try{
			BloquesDAO dao = new BloquesDAO(autenticacion);
			ResultSet res = dao.getBloques();
			ModeloTablaListado modelo = ventanaListado.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();				
				itm.setValorBoolean(false);
				itm.setValorInt(res.getInt("id_bloque"));				
				
				modelo.setValueAt(itm, cont, 0);				
				modelo.setValueAt(res.getString("numero"), cont, 1);
				modelo.setValueAt(res.getString("descripcion"), cont, 2);
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
		}
		catch(Exception e){			
		}		
	}

	public void setVentanaListado(VentanaListadoBloques ventana){
		ventanaListado = ventana;
	}
	
	public VentanaListadoBloques getVentanaListado(){
		if(ventanaListado == null){
			ventanaListado = new VentanaListadoBloques(desktopPanel);
			ventanaListado.setGestor(this);
		}
		
		return ventanaListado;
	}
	
	public Bloque getSeleccionado() {
		return seleccionado;
	}

	public void setVentanaEditar(VentanaEditarBloques ventana){
		ventanaEditar = ventana;
	}
	
	public VentanaEditarBloques getVentanaEditar(){
		if(ventanaEditar == null){
			ventanaEditar = new VentanaEditarBloques(desktopPanel);
			ventanaEditar.setGestor(this);			
		}
		
		return ventanaEditar;
	}
	
}
