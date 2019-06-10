package GestionarReportes;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ConexionBD.Conexion;
import Consultas.EstudiantesDAO;
import Consultas.PersonasDAO;
import Consultas.SeccionesDAO;
import Entidades.Estudiante;
import Entidades.Persona;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.VariablesGlobales;

public class GestorGenerarReporte {
	public static final int REPORTE_VACIO = 0;
	public static final int REPORTE_ESTUDIANTE = 1;
	public static final int REPORTE_ESTUDIANTES_SECCION = 2;
	private Autenticacion autenticacion;
	private GenerarReportes ventana;
	
	public GestorGenerarReporte(Autenticacion autenticacion, GenerarReportes ventana){
		this.autenticacion = autenticacion;
		this.ventana = ventana;
	}
	
	public void generarReporte(){
		int tipoReporte = ((Item)ventana.getCbxTipoReporte().getSelectedItem()).getValorInt();
		if(tipoReporte == REPORTE_VACIO){
			MostrarMensajes.mostrarMensaje("Seleccione un tipo de reporte", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		if(ventana.getTxtFechaInicio().getDate() == null){
			MostrarMensajes.mostrarMensaje("Debe seleccionar la fecha inicial", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		if(ventana.getTxtFechaFin().getDate() == null){
			MostrarMensajes.mostrarMensaje("Debe seleccionar la fecha final", MostrarMensajes.MENSAJE_ERROR);
			return ;
		}
		
		Reporte reporte = new Reporte();		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		Conexion conexion = new Conexion(autenticacion);
		
		parametros.put("fecha_inicio", ventana.getTxtFechaInicio().getDate());
		parametros.put("fecha_fin", ventana.getTxtFechaFin().getDate());
		
		if(tipoReporte == REPORTE_ESTUDIANTE){
			if(ventana.getTxtCedula().getText().length()==0){
				MostrarMensajes.mostrarMensaje("Debe ingresar la cédula del alumno", MostrarMensajes.MENSAJE_ERROR);
				return ;
			}
			
			PersonasDAO personasDAO = new PersonasDAO(autenticacion);
			Persona persona = personasDAO.buscarCedula(ventana.getTxtCedula().getText());
			personasDAO.cerrarConexion();
			
			Estudiante estudiante = null;
			if(persona != null){
				EstudiantesDAO estudiantesDAO = new EstudiantesDAO(autenticacion);
				estudiante = estudiantesDAO.buscar(persona.getId_persona());				
				estudiantesDAO.cerrarConexion();
			}
			
			if(persona == null || estudiante == null){
				MostrarMensajes.mostrarMensaje("No se encontro un alumno con la cédula", MostrarMensajes.MENSAJE_ERROR);
				return ;
			}
			
			parametros.put("id_seccion", estudiante.getSeccion().getId_seccion());
			parametros.put("min_id_persona", persona.getId_persona());
			parametros.put("max_id_persona", persona.getId_persona());
			
			reporte.setArchivo("Archivos/jasper/InasistenciasEstudiante.jasper");
			
		}
		if(tipoReporte == REPORTE_ESTUDIANTES_SECCION){
			int index = ventana.getCbxSeccion().getSelectedIndex();
			if(index == -1){
				MostrarMensajes.mostrarMensaje("Debe seleccionar la sección", MostrarMensajes.MENSAJE_ERROR);
				return ;
			}
			else{
				int id_seccion = ((Item)ventana.getCbxSeccion().getSelectedItem()).getValorInt();
				
				parametros.put("id_seccion", id_seccion);
				parametros.put("min_id_persona", -1);
				parametros.put("max_id_persona", 999999);
				
				reporte.setArchivo("Archivos/jasper/InasistenciasEstudiante.jasper");
			}
		}
		
		
		conexion.conectar();
		reporte.setParametros(parametros);		
		reporte.setConexion(conexion.getConexion());
		
		reporte.start();
	}
	
	public void seleccionarReporte(){
		int reporte = ((Item)ventana.getCbxTipoReporte().getSelectedItem()).getValorInt();
		
		if(reporte == REPORTE_VACIO){
			ventana.getCbxSeccion().setEnabled(false);
			ventana.getTxtCedula().setEnabled(false);
			ventana.getTxtFechaInicio().setEnabled(false);
			ventana.getTxtFechaFin().setEnabled(false);
		}
		
		if(reporte == REPORTE_ESTUDIANTE){
			ventana.getCbxSeccion().setEnabled(false);
			ventana.getTxtCedula().setEnabled(true);
			ventana.getTxtFechaInicio().setEnabled(true);
			ventana.getTxtFechaFin().setEnabled(true);
		}
		
		if(reporte == REPORTE_ESTUDIANTES_SECCION){
			ventana.getCbxSeccion().setEnabled(true);
			ventana.getTxtCedula().setEnabled(false);
			ventana.getTxtFechaInicio().setEnabled(true);
			ventana.getTxtFechaFin().setEnabled(true);
		}
	}
	
	public Item[] getTiposReporte(){
		Item items[] = new Item[3];
		
		items[0] = new Item();
		items[0].setNombre("Seleccione un tipo de reporte");
		items[0].setValorInt(REPORTE_VACIO);
		
		items[1] = new Item();
		items[1].setNombre("Reporte de inasistencias por alumno");
		items[1].setValorInt(REPORTE_ESTUDIANTE);
		
		items[2] = new Item();
		items[2].setNombre("Reporte de inasistencias por sección");
		items[2].setValorInt(REPORTE_ESTUDIANTES_SECCION);
		
		return items;
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
}
