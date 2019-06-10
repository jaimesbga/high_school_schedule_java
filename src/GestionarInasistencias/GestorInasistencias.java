package GestionarInasistencias;

import java.util.List;

import ConexionBD.Conexion;
import Consultas.ActividadDAO;
import Consultas.HorarioDAO;
import Consultas.InasistenciasDAO;
import Consultas.ItemHorario;
import Consultas.SeccionDAO;
import Entidades.Actividad;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.UtilFechas;
import Utilitario.Tablas.ModeloTablaInasistencias;
import Utilitario.Tablas.ModeloTablaListado;

public class GestorInasistencias {
	private Autenticacion autenticacion= null;
	private Conexion conexion = null;
	private VentanaGestionarInasistencias inasistencias = null;
	@SuppressWarnings("unused")
	private String query = "";
	private int id_seccion = -1;
	private int id_actividad = -1;
	private int rowSelect = -1;
	private int estado_ventana = 1;
	
	public int getId_seccion() {
		return id_seccion;
	}



	public void setId_seccion(int id_seccion) {
		this.id_seccion = id_seccion;
	}



	public GestorInasistencias(Autenticacion autenticacion, VentanaGestionarInasistencias inasistencias){
		this.autenticacion = autenticacion;
		this.inasistencias = inasistencias;
		setConexion(new Conexion(this.autenticacion));
	}
	
	
	
	public int getEstado_ventana() {
		return estado_ventana;
	}



	public void setEstado_ventana(int estado_ventana) {
		this.estado_ventana = estado_ventana;
	}



	public int getRowSelect() {
		return rowSelect;
	}


	public void setRowSelect(int rowSelect) {
		this.rowSelect = rowSelect;
	}


	//----------------------------------------------------------------------------------------------------	
	public boolean estaDisponible(){
		boolean retorno = false;
			String seccion = inasistencias.getC_seccion().getSelectedItem().toString();
			String periodo = inasistencias.getC_periodo().getSelectedItem().toString();
			
			SeccionDAO seccionDAO = new SeccionDAO(autenticacion);
			int id = seccionDAO.getIdSeccion(seccion, periodo);
				if(id!=-1){
					id_seccion = id ;
					retorno = true;
				}
			
		return retorno;
	}
//----------------------------------------------------------------------------------------------------
	public boolean procesarDatosActividad(){
		boolean retorno = false;
				ActividadDAO actividadDAO = new ActividadDAO(autenticacion);
				
			if(actividadDAO.existeActividad(id_seccion,
					inasistencias.getChooser().getDate())==-1){
				//------------------------------------------------------------
					Actividad actividad = new Actividad();
	actividad.setId_actividad(actividadDAO.buscarNuevoId());
	id_actividad = actividad.getId_actividad();
	actividad.setFecha(UtilFechas.convertirFecha(inasistencias.getChooser().getDate(), UtilFechas.YYYY_MM_DD));
	actividad.setDia_semana(inasistencias.getChooser().getDate());
	actividad.setId_seccion(id_seccion);
	actividad.setTipo_actividad(inasistencias.getC_tipo_actividad().getSelectedIndex()+1);
	actividad.setObservaciones(inasistencias.getTa_observaciones().getText());
				actividadDAO.guardarActividad(actividad);
				//------------------------------------------------------------
				retorno = true;	
				inasistencias.getB_procesar().setEnabled(false);
		///Cargar de tabla.....
				HorarioDAO horarioDAO = new HorarioDAO(autenticacion);
				List<ItemHorario> list = horarioDAO.getHorarioSeccionporDia(id_seccion, 
						actividad.getDia_semana());
				
				ModeloTablaInasistencias modeloTablaInasistencias = 
					new ModeloTablaInasistencias(list);
				
				inasistencias.getT_inasistencias().setModel(modeloTablaInasistencias);
				inasistencias.actualizarAnchoColumnas();
			
			//---------	
				setEstado_ventana(2);
	MostrarMensajes.mostrarMensaje(inasistencias.getVentana(),
			"La actividad Diaria ha sido almacenada con exito.", MostrarMensajes.MENSAJE_EXITO);			
				
			}else{
				
	Boolean tip = MostrarMensajes.mostrarMensaje(inasistencias.getVentana(),
			"La Actividad para esa fecha ya existe. Desea Cargarla", MostrarMensajes.MENSAJE_PREGUNTA);	
				
				if(tip){
					id_actividad = actividadDAO.existeActividad(id_seccion,
							inasistencias.getChooser().getDate());
						if(id_actividad!=-1){
							cargarDatos(id_actividad);
						}
				}
	
	
			}
			
			inasistencias.getB_guardar().setEnabled(true);
			inasistencias.getP_horario().setEnabled(true);
			
			return retorno;		
	}
//-----------------------------------------------------------------------------------------------------
	public void ProcesarInasistentes(ModeloTablaListado tablaListado){
		String stringNumero = "";
		String stringID = "";
		
			for (int i = 0; i < tablaListado.getRowCount(); i++) {
				try{
					if( Boolean.valueOf(tablaListado.getValueAt(i, 0).toString())==true){
						stringID = stringID + String.valueOf(tablaListado.getItem(i).getValorInt()) + "/";
						stringNumero = stringNumero + tablaListado.getValueAt(i, 1).toString() + "/";
						
					}//fin if 
				}catch (Exception e) {
					// TODO: handle exception
					System.err.println("Error en conversion StringToBoolean Linea 88 - Gestionar Inasistencias");
				}//fin cath	
					
			}
			
		if(!stringID.isEmpty()){	
			stringID = stringID.substring(0, stringID.length()-1);
			stringNumero = stringNumero.substring(0, stringNumero.length()-1);
		}else{
			stringID = "";
			stringNumero = "";
		}	
		
			Item item = new Item();
			item.setNombre(stringNumero);
			item.setValorString(stringID);
			
			inasistencias.getT_inasistencias().getModel().setValueAt(item, getRowSelect(), 3);
	asignarInasistenciasSimilares(inasistencias.getT_inasistencias().getModel()
			.getValueAt(getRowSelect(), 2).toString(), item);
			//System.out.println(stringNumero);
	}
//-----------------------------------------------------------------------------------------------------	
	private void asignarInasistenciasSimilares(String nombreMateria,Item Inasistentes){
		@SuppressWarnings("unused")
		String comparacion = nombreMateria;
		
			for (int i = 0; i < inasistencias.getT_inasistencias().getRowCount(); i++) {
				
				if(inasistencias.getT_inasistencias().getModel().getValueAt(i, 2).toString()
						.compareToIgnoreCase(nombreMateria)==0){						
					inasistencias.getT_inasistencias().getModel().setValueAt(Inasistentes, i, 3);
						//---------------------
					if(inasistencias.getT_inasistencias().getRowCount()> i+1){
						if(inasistencias.getT_inasistencias().getModel().getValueAt(i+1, 2).toString()
								.compareToIgnoreCase(nombreMateria)!=0){
							
								break;
						}
						
					}
						//---------------------
					
				}
				
				
			}// for 
			
	}
//------------------------------------------------------------------------------------------------------
	public boolean GuardarInasistencias(){
	 ModeloTablaInasistencias tabla = (ModeloTablaInasistencias) inasistencias.getT_inasistencias().getModel();
	 InasistenciasDAO inasistenciasDAO = new InasistenciasDAO(autenticacion);	
	  if(id_actividad !=-1){
			for (int i = 0; i < tabla.getRowCount(); i++) {
				if(!tabla.getValueAt(i, 0).toString().isEmpty()){					
					int id_horario = tabla.getIdHorario(i);
					String data[] = tabla.getItem(i);
						//--------------------------
				if(data != null){	
					for (int j = 0; j < data.length; j++) {
								try {
									
					if(!inasistenciasDAO.siExiste(id_actividad, id_horario,Integer.parseInt(data[j]))){	
									inasistenciasDAO.guardarInasistencia(id_actividad, id_horario,
											Integer.parseInt(data[j]));
					}//fin fi 
								
								} catch (Exception e) {
									// TODO: handle exception
		MostrarMensajes.mostrarMensaje(inasistencias.getVentana(),
				"Las inasistencias no han sido almacenadas.", MostrarMensajes.MENSAJE_ERROR);
								}					
						
					}
						//--------------------------
				}	
				}
			}// fin for
	MostrarMensajes.mostrarMensaje(inasistencias.getVentana(),
			"Las inasistencias han sido alcenadas con exito.", MostrarMensajes.MENSAJE_EXITO);	
	setEstado_ventana(3);		
		}
	  	inasistenciasDAO.desconectarInasistenciasDAO();
		return true;
	}
//Cerrar Venatana validacion
		public void operacionCerrarVentana(){
			
			boolean tip = MostrarMensajes.mostrarMensaje(inasistencias.getVentana(),
					"No se han registrado inasistencias.Realmente desea cerrar?", MostrarMensajes.MENSAJE_PREGUNTA);
			if(tip){
				inasistencias.getVentana().dispose();
			}
			
		}
		
		public void InicializarControles(){
			inasistencias.getVentana().setVisible(false);
			inasistencias.getVentana().dispose();
			new VentanaGestionarInasistencias(autenticacion,inasistencias.getDesktopPane());
	}
//----------------Cargar Datos---------------------------------------------------------------------
		public void cargarDatos(int id_actividad){
			///Cargar de tabla.....
			HorarioDAO horarioDAO = new HorarioDAO(autenticacion);
			ActividadDAO actividad = new ActividadDAO(autenticacion);
			
			Actividad acti = actividad.getActividad(id_actividad);
			inasistencias.getTa_observaciones().setText(acti.getObservaciones());
			inasistencias.getC_tipo_actividad().setSelectedIndex(acti.getTipo_actividad()-1);
			
			List<ItemHorario> list = horarioDAO.getHorarioSeccionporDia(id_seccion, 
					actividad.getActividad(id_actividad).getDia_semana());
			
			ModeloTablaInasistencias modeloTablaInasistencias = 
				new ModeloTablaInasistencias(list);
			
			horarioDAO.getFromHorarioInasistentes(id_actividad, modeloTablaInasistencias);
			inasistencias.getT_inasistencias().setModel(modeloTablaInasistencias);
			inasistencias.actualizarAnchoColumnas();			
			inasistencias.getT_inasistencias().setEnabled(true);
			setEstado_ventana(3);
		//---------	
		}



		public void setConexion(Conexion conexion) {
			this.conexion = conexion;
		}



		public Conexion getConexion() {
			return conexion;
		}
		
}
