package GestionarVentanaPrincipal;

import java.util.Properties;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import ConexionBD.Conexion;
import GestionarAccesoAplicacion.VentanaIniciarSesion;
import GestionarBloques.GestorBloques;
import GestionarEstudiantes.GestorEstudiantes;
import GestionarInasistencias.VentanaGestionarInasistencias;
import GestionarMaterias.GestorMaterias;
import GestionarProfesores.GestorProfesores;
import GestionarReportes.GenerarReportes;
import GestionarSeccion.VentanaEditarSeccion;
import GestionarSeccion.VentanaHorario;
import GestionarUsuarios.GestorUsuarios;
import Utilitario.Autenticacion;
import Utilitario.MostrarMensajes;
import Utilitario.UtilArchivos;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class GestorVentanaPrincipal {
	private VentanaPrincipal ventana;
	private boolean conectadoServidor;
	private boolean autenticado;
	private Autenticacion autenticacion;
	private Properties preferencias;
	
	
	public GestorVentanaPrincipal(VentanaPrincipal ventana, Autenticacion autenticacion, Properties preferencias){
		this.ventana = ventana;
		this.autenticacion = autenticacion;
		this.preferencias = preferencias;
		conectadoServidor = false;	
		
		cargarPreferencias(this.preferencias);
		
		setConectadoServidor(probarConexion(autenticacion));
		ventana.getVentana().setVisible(true);
		setAutenticado(false);
	}
	
	public void verVentanaSecciones(){
		new VentanaEditarSeccion(ventana.getDesktopPanel(),autenticacion);		
	}
	
	public void verVentanaReportes(){
		new GenerarReportes(autenticacion,ventana.getDesktopPanel());		
	}
	public void verVentanaHorarios(){
		new VentanaHorario(autenticacion,ventana.getDesktopPanel());
		//new VentanaGestionarInasistencias(autenticacion,ventana.getDesktopPanel());
	}
	
	public void verVentanaInasistencias(){
		//new VentanaHorario(autenticacion,ventana.getDesktopPanel());
		new VentanaGestionarInasistencias(autenticacion,ventana.getDesktopPanel());
	}
	
	public void btnPrueba(){
		GestorEstudiantes g = new GestorEstudiantes(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();		
	}
	
	public void gestionarProfesores(){
		GestorProfesores g = new GestorProfesores(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();		
	}
	
	public void gestionarEstudiantes(){
		GestorEstudiantes g = new GestorEstudiantes(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();
	}
	
	public void gestionarBloques(){
		GestorBloques g = new GestorBloques(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();		
	}
	
	public void gestionarMaterias(){
		GestorMaterias g = new GestorMaterias(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();		
	}
	
	public void gestionarUsuarios(){
		GestorUsuarios g = new GestorUsuarios(autenticacion, ventana.getDesktopPanel());
		g.prepararListar();		
	}
	
	public void cargarPreferencias(Properties p){
		if(p!=null){
			String tema = p.getProperty("tema");
			cambiarTema(tema);
		}
	}
	
	public void cerrarSesion(){
		if(MostrarMensajes.mostrarMensaje(ventana.getVentana(), "¿Realmente desea cerrar sesión?", MostrarMensajes.MENSAJE_PREGUNTA)){
			JInternalFrame vec[] = ventana.getDesktopPanel().getAllFrames();
	    	for(int i=0; i<vec.length; i++){
	    		ventana.getDesktopPanel().getDesktopManager().closeFrame(vec[i]);    		
	    	}
			autenticacion.setUsuario(null);
			setAutenticado(false);
		}		
	}
	
	public void mostraVentanaServidor(){
		
	}
	
	public void mostrarVentanaUsuario(){
		VentanaIniciarSesion v = new VentanaIniciarSesion(autenticacion, ventana.getVentana().getBounds());
		v.getVentana().setVisible(true);
		if(v.getConectado()){
			setAutenticado(true);
		}
	}
	
	public boolean probarConexion(Autenticacion a){
		boolean conectado = false;
		Conexion conexion = new Conexion(a);
		conectado = conexion.conectar();
		conexion.desconectar();
		return conectado;
	}
	
	public void setConectadoServidor(boolean conectado){
		conectadoServidor = conectado;
		if(conectadoServidor){
			mostraVentanaServidor();
		}
	}
	
	public void setAutenticado(boolean estado){
		autenticado = estado;
		if(!autenticado){
			ventana.getBtn_acceso1().setEnabled(false);
			ventana.getM_mantenimiento().setEnabled(false);
			ventana.getMi_iniciarSesion().setVisible(true);
			ventana.getMi_desconectar().setVisible(false);
			ventana.getM_datos().setEnabled(false);
			ventana.getM_reportes().setEnabled(false);
			mostrarVentanaUsuario();
		}else{
			ventana.getMi_iniciarSesion().setVisible(false);
			ventana.getM_mantenimiento().setEnabled(true);
			ventana.getMi_desconectar().setVisible(true);
			ventana.getBtn_acceso1().setEnabled(true);
			ventana.getM_datos().setEnabled(true);
			ventana.getM_reportes().setEnabled(true);
			
		}
	}
	
	public void cambiarTema(String tema){
		boolean valido = false;
		try{
			if(tema.compareToIgnoreCase("metal")==0){
				valido = true;
				UIManager.setLookAndFeel(new MetalLookAndFeel());
				ventana.getMi_tema1().setSelected(true);
			}
			if(tema.compareToIgnoreCase("windows")==0){
				valido = true;
				UIManager.setLookAndFeel(new WindowsLookAndFeel());
				ventana.getMi_tema2().setSelected(true);
			}
			if(valido == false){
				UIManager.setLookAndFeel(new MetalLookAndFeel());
			}			
		}
		catch(Exception ex){
		}
		
		if(valido){
			preferencias.setProperty("tema", tema);
			UtilArchivos.escribirXMLPropiedades(UtilArchivos.ARCHIVO_PREFERENCIAS, preferencias);
		}
					
		SwingUtilities.updateComponentTreeUI(ventana.getVentana());
		
	}
	
	public void salir(){		
		if(MostrarMensajes.mostrarMensaje(ventana.getVentana(), "¿Realmente desea salir?", MostrarMensajes.MENSAJE_PREGUNTA)){
			System.exit(0);
		}		
	}
}
