package GestionarProfesores;

import java.sql.ResultSet;
import javax.swing.JDesktopPane;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.ModeloTablaListado;
import Consultas.ProfesoresDAO;
import Entidades.Persona;
import Entidades.Profesor;

public class GestorProfesores {
	private VentanaListadoProfesores ventanaListado;
	private VentanaEditarProfesores ventanaEditar;
	private JDesktopPane desktopPanel;
	private Autenticacion autenticacion;
	private Profesor seleccionado;
	
	public GestorProfesores(Autenticacion autenticacion, JDesktopPane desktopPanel) {
		this.autenticacion = autenticacion;
		this.desktopPanel = desktopPanel;
	}
	
	public void guardar(){
		seleccionado.getPersona().setNombre(ventanaEditar.getTxtNombre().getText());
		if(seleccionado.getPersona().getNombre().length()==0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El nombre es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		seleccionado.getPersona().setCedula(ventanaEditar.getTxtCedula().getText());
		if(seleccionado.getPersona().getCedula().length()==0){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La cédula es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		seleccionado.getPersona().setFecha_nacimiento(ventanaEditar.getTxtFechaNacimiento().getDate());
		if(seleccionado.getPersona().getFecha_nacimiento()==null){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La fecha de nacimiento es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		if(ventanaEditar.getTxtSexo().getSelectedItem().toString().compareTo("Masculino")==0){
			seleccionado.getPersona().setSexo("M");
		}
		else{
			seleccionado.getPersona().setSexo("F");
		}

		seleccionado.getPersona().setDireccion(ventanaEditar.getTxtDireccion().getText());
		seleccionado.getPersona().setCorreo(ventanaEditar.getTxtCorreo().getText());
		seleccionado.getPersona().setTelefono(ventanaEditar.getTxtTelefono().getText());
		
		ProfesoresDAO dao = new ProfesoresDAO(autenticacion);
		if(!dao.validarProfesor(seleccionado.getPersona().getId_persona(), seleccionado.getPersona().getNombre())){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La persona ya se encuentra registrada", MostrarMensajes.MENSAJE_ERROR);
			dao.cerrarConexion();
			return ;
		}
		
		int errores = 0;
		if(seleccionado.getPersona().getId_persona() == -1){
			seleccionado.getPersona().setId_persona(dao.buscarNuevoId());			
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
	
	public void eliminar(int id_persona){
		ProfesoresDAO dao = new ProfesoresDAO(autenticacion);
		boolean res = dao.eliminar(id_persona);
		dao.cerrarConexion();
		
		if(res){
			MostrarMensajes.mostrarMensaje("Operación exitosa", MostrarMensajes.MENSAJE_EXITO);
			if(ventanaEditar!=null && seleccionado.getPersona().getId_persona()==id_persona){
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
		seleccionado = new Profesor();
		seleccionado.setPersona(new Persona());
		seleccionado.getPersona().setId_persona(-1);
		getVentanaEditar();		
		ventanaEditar.getTxtNombre().setText("");
		ventanaEditar.getTxtCedula().setText("");
		ventanaEditar.getTxtCorreo().setText("");
		ventanaEditar.getTxtDireccion().setText("");
		ventanaEditar.getTxtSexo().setSelectedIndex(0);
		ventanaEditar.getTxtTelefono().setText("");
		ventanaEditar.getTxtFechaNacimiento().setDate(null);
		
		ventanaEditar.getBtnEliminar().setVisible(false);
		ventanaEditar.mostrarVentana();
	}
	
	public void prepararEditar(int id_persona){
		ProfesoresDAO dao = new ProfesoresDAO(autenticacion);
		seleccionado = dao.buscar(id_persona); 
		dao.cerrarConexion();
		if(seleccionado!=null){
			getVentanaEditar();			
			ventanaEditar.getTxtNombre().setText(seleccionado.getPersona().getNombre());
			ventanaEditar.getTxtCedula().setText(seleccionado.getPersona().getCedula());
			ventanaEditar.getTxtFechaNacimiento().setDate(seleccionado.getPersona().getFecha_nacimiento());
			ventanaEditar.getTxtTelefono().setText(seleccionado.getPersona().getTelefono());
			ventanaEditar.getTxtCorreo().setText(seleccionado.getPersona().getCorreo());
			ventanaEditar.getTxtDireccion().setText(seleccionado.getPersona().getDireccion());
			ventanaEditar.getTxtSexo().setSelectedItem(seleccionado.getPersona().getSexo());
			
			ventanaEditar.getBtnEliminar().setVisible(true);
			ventanaEditar.mostrarVentana();
		}
	}
	
	public void prepararListar(){
		getVentanaListado();
		try{
			ProfesoresDAO dao = new ProfesoresDAO(autenticacion);
			ResultSet res = dao.getListadoProfesores();
			ModeloTablaListado modelo = ventanaListado.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();				
				itm.setValorBoolean(false);
				itm.setValorInt(res.getInt("id_persona"));				
				
				modelo.setValueAt(itm, cont, 0);
				modelo.setValueAt(res.getString("cedula"), cont, 1);
				modelo.setValueAt(res.getString("nombre"), cont, 2);				
				modelo.setValueAt(res.getString("telefono"), cont, 3);
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}

	public void setVentanaListado(VentanaListadoProfesores ventana){
		ventanaListado = ventana;
	}
	
	public VentanaListadoProfesores getVentanaListado(){
		if(ventanaListado == null){
			ventanaListado = new VentanaListadoProfesores(desktopPanel);
			ventanaListado.setGestor(this);
		}
		
		return ventanaListado;
	}
	
	public Profesor getSeleccionado() {
		return seleccionado;
	}

	public void setVentanaEditar(VentanaEditarProfesores ventana){
		ventanaEditar = ventana;
	}
	
	public VentanaEditarProfesores getVentanaEditar(){
		if(ventanaEditar == null){
			ventanaEditar = new VentanaEditarProfesores(desktopPanel);
			ventanaEditar.setGestor(this);			
		}
		
		return ventanaEditar;
	}
	
}
