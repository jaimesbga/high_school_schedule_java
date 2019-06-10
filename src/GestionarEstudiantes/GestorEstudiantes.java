package GestionarEstudiantes;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.VariablesGlobales;
import Utilitario.Tablas.ModeloTablaListado;
import Consultas.EstudiantesDAO;
import Consultas.SeccionesDAO;
import Entidades.Estudiante;
import Entidades.Persona;
import Entidades.Seccion;

public class GestorEstudiantes {
	private VentanaListadoEstudiantes ventanaListado;
	private VentanaEditarEstudiantes ventanaEditar;
	private JDesktopPane desktopPanel;
	private Autenticacion autenticacion;
	private Estudiante seleccionado;
	
	public GestorEstudiantes(Autenticacion autenticacion, JDesktopPane desktopPanel) {
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

		if(ventanaEditar.getTxtSeccion().getSelectedIndex() == -1){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La sección es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}else{
			seleccionado.getSeccion().setId_seccion(((Item)ventanaEditar.getTxtSeccion().getSelectedItem()).getValorInt());
		}
		
		seleccionado.getPersona().setDireccion(ventanaEditar.getTxtDireccion().getText());
		seleccionado.getPersona().setCorreo(ventanaEditar.getTxtCorreo().getText());
		seleccionado.getPersona().setTelefono(ventanaEditar.getTxtTelefono().getText());
		seleccionado.setNombre_representante(ventanaEditar.getTxtNombreR().getText());
		seleccionado.setCedula_representante(ventanaEditar.getTxtCedulaR().getText());
		seleccionado.setTelefono_representante(ventanaEditar.getTxtTelefonoR().getText());
		
		
		EstudiantesDAO dao = new EstudiantesDAO(autenticacion);
		if(!dao.validarEstudiante(seleccionado.getPersona().getId_persona(), seleccionado.getPersona().getNombre())){
			MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "La persona ya se encuentra registrada", MostrarMensajes.MENSAJE_ERROR);
			dao.cerrarConexion();
			return ;
		}
		
		if(ventanaEditar.getTxtNumeroLista().getText().length() == 0){
			if(seleccionado.getPersona().getId_persona() == -1){
				seleccionado.setNumero_lista(dao.buscarNumeroLista(seleccionado.getSeccion().getId_seccion()));
				ventanaEditar.getTxtNumeroLista().setText(String.valueOf(seleccionado.getNumero_lista()));
			}else{
				dao.cerrarConexion();
				MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El número de lista es un campo obligatorio", MostrarMensajes.MENSAJE_ERROR);
				return ;
			}
		}
		else{
			try{
				seleccionado.setNumero_lista(Integer.parseInt(ventanaEditar.getTxtNumeroLista().getText()));
				if(!dao.validarNumeroLista(seleccionado.getPersona().getId_persona(), seleccionado.getSeccion().getId_seccion(), seleccionado.getNumero_lista())){
					MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "Ya se encuentra un estudiante con el número de lista", MostrarMensajes.MENSAJE_ERROR);
				}
			}
			catch(Exception e){
				MostrarMensajes.mostrarMensaje(ventanaEditar.getVentana(), "El número de lista debe ser un valor numérico", MostrarMensajes.MENSAJE_ERROR);
				dao.cerrarConexion();
				return ;
			}
		}
		
		
		
		int errores = 0;
		if(seleccionado.getPersona().getId_persona() == -1){
			seleccionado.setEstado(1);
			seleccionado.getPersona().setId_persona(dao.buscarNuevoId());			
			errores = dao.guardar(seleccionado) ? 0 : 1;
			if(errores > 0){
				dao.eliminar(seleccionado.getPersona().getId_persona());
			}
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
		EstudiantesDAO dao = new EstudiantesDAO(autenticacion);
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
		seleccionado = new Estudiante();
		seleccionado.setPersona(new Persona());
		seleccionado.setSeccion(new Seccion());
		seleccionado.getPersona().setId_persona(-1);
		getVentanaEditar();		
		ventanaEditar.getTxtNombre().setText("");
		ventanaEditar.getTxtCedula().setText("");
		ventanaEditar.getTxtCorreo().setText("");
		ventanaEditar.getTxtDireccion().setText("");
		ventanaEditar.getTxtSexo().setSelectedIndex(0);
		ventanaEditar.getTxtTelefono().setText("");
		ventanaEditar.getTxtFechaNacimiento().setDate(null);
		ventanaEditar.getTxtSeccion().setModel(new DefaultComboBoxModel(getSecciones()));
		
		ventanaEditar.getBtnEliminar().setVisible(false);
		ventanaEditar.mostrarVentana();
	}
	
	public void prepararEditar(int id_persona){
		EstudiantesDAO dao = new EstudiantesDAO(autenticacion);
		seleccionado = dao.buscar(id_persona); 
		dao.cerrarConexion();
		if(seleccionado!=null){
			getVentanaEditar();
			ventanaEditar.getTxtSeccion().setModel(new DefaultComboBoxModel(getSecciones()));
			
			ventanaEditar.getTxtNombre().setText(seleccionado.getPersona().getNombre());
			ventanaEditar.getTxtCedula().setText(seleccionado.getPersona().getCedula());
			ventanaEditar.getTxtFechaNacimiento().setDate(seleccionado.getPersona().getFecha_nacimiento());
			ventanaEditar.getTxtTelefono().setText(seleccionado.getPersona().getTelefono());
			ventanaEditar.getTxtCorreo().setText(seleccionado.getPersona().getCorreo());
			ventanaEditar.getTxtDireccion().setText(seleccionado.getPersona().getDireccion());
			ventanaEditar.getTxtSexo().setSelectedItem(seleccionado.getPersona().getSexo());
			ventanaEditar.getTxtNombreR().setText(seleccionado.getNombre_representante());
			ventanaEditar.getTxtCedulaR().setText(seleccionado.getCedula_representante());
			ventanaEditar.getTxtTelefonoR().setText(seleccionado.getTelefono_representante());
			ventanaEditar.getTxtNumeroLista().setText(String.valueOf(seleccionado.getNumero_lista()));
			
			for(int i=0; i<ventanaEditar.getTxtSeccion().getItemCount(); i++){
				Item itm = (Item)ventanaEditar.getTxtSeccion().getItemAt(i);
				if(itm.getValorInt() == seleccionado.getSeccion().getId_seccion()){
					ventanaEditar.getTxtSeccion().setSelectedIndex(i);
					break;
				}
			}
			
			ventanaEditar.getBtnEliminar().setVisible(true);
			ventanaEditar.mostrarVentana();
		}
	}
	
	public void prepararListar(){
		getVentanaListado();
		try{
			EstudiantesDAO dao = new EstudiantesDAO(autenticacion);
			ResultSet res = dao.getListadoEstudiantes();
			ModeloTablaListado modelo = ventanaListado.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();				
				itm.setValorBoolean(false);
				itm.setValorInt(res.getInt("id_persona"));				
				
				modelo.setValueAt(itm, cont, 0);
				modelo.setValueAt(res.getString("numero_lista"), cont, 1);
				modelo.setValueAt(res.getString("cedula"), cont, 2);
				modelo.setValueAt(res.getString("nombre"), cont, 3);				
				modelo.setValueAt(res.getString("seccion"), cont, 4);
				
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	public Object[] getSecciones(){
		List<Item> lista = new ArrayList<Item>();
		
		SeccionesDAO seccionesDao = new SeccionesDAO(autenticacion);
		ResultSet res = seccionesDao.getSecciones();
		try{
			while(res.next()){
				Item itm = new Item();
				itm.setValorInt(res.getInt("id_seccion"));
				itm.setNombre(res.getString("nombre"));
				lista.add(itm);
			}
			res.close();
		}
		catch(Exception e){
			if(VariablesGlobales.DEBUG){
				e.printStackTrace();
			}
		}
		seccionesDao.cerrarConexion();
		
		return lista.toArray();
	}

	public void setVentanaListado(VentanaListadoEstudiantes ventana){
		ventanaListado = ventana;
	}
	
	public VentanaListadoEstudiantes getVentanaListado(){
		if(ventanaListado == null){
			ventanaListado = new VentanaListadoEstudiantes(desktopPanel);
			ventanaListado.setGestor(this);			
		}
		
		return ventanaListado;
	}
	
	public Estudiante getSeleccionado() {
		return seleccionado;
	}

	public void setVentanaEditar(VentanaEditarEstudiantes ventana){
		ventanaEditar = ventana;
	}
	
	public VentanaEditarEstudiantes getVentanaEditar(){
		if(ventanaEditar == null){
			ventanaEditar = new VentanaEditarEstudiantes(desktopPanel);
			ventanaEditar.setGestor(this);			
		}
		
		return ventanaEditar;
	}
	
}
