package GestionarInicioAplicacion;

import java.util.Properties;

import ConexionBD.Conexion;
import ConexionBD.CrearBD;
import GestionarVentanaPrincipal.VentanaPrincipal;
import Utilitario.Autenticacion;
import Utilitario.MostrarMensajes;
import Utilitario.UtilArchivos;
import Utilitario.VariablesGlobales;

public class GestorInicioAplicacion {
	private VentanaInicioAplicacion ventana;
	
	public GestorInicioAplicacion(VentanaInicioAplicacion ventana) {
		this.ventana = ventana;
	}
		
	public void iniciarCargaAplicacion(){		
        int avance = 100 / 4;
        boolean aplicacionValida = true;
        Properties propiedades = null;
        Properties preferencias = null;
        Autenticacion autenticacion = new Autenticacion();
        try{
	        ventana.getProgressBar().setValue(0);
	        ventana.getProgressBar().setString("Validando instalación - 0%");	        
	        propiedades = buscarPropiedades();
	        autenticacion.setUsuarioBD(propiedades.getProperty("usuario"));        	
	        autenticacion.setClaveBD(propiedades.getProperty("clave"));        	
        	autenticacion.setIpBD("127.0.0.1");
        	autenticacion.setUsuario(null);
	        Thread.sleep(200);	        
	        
	        ventana.getProgressBar().setString("Validando servidor - " + (avance) + "%");
	        Conexion conexion = new Conexion(autenticacion);
            boolean conectado = conexion.conectar();            
            conexion.desconectar();            
	        ventana.getProgressBar().setValue(avance);
            Thread.sleep(300);            
            
            ventana.getProgressBar().setString("Configurando servidor - " + (avance*2) + "%");
	        ventana.getProgressBar().setValue(avance*2);	        	        
	        Thread.sleep(200);
            if(!conectado){
            	CrearBD crearBD = new CrearBD();
            	aplicacionValida = crearBD.ejecutar(autenticacion.getUsuarioBD(), autenticacion.getClaveBD());
            }
            
            ventana.getProgressBar().setString("Buscando preferencias - " + (avance*3) + "%");
	        ventana.getProgressBar().setValue(avance*3);
	        preferencias = buscarPreferencias();	        
	        Thread.sleep(200);
	        
            for (int i = ventana.getProgressBar().getValue(); i <= 100; i++) {
            	ventana.getProgressBar().setString("Iniciando aplicación - " + i + "%");
            	ventana.getProgressBar().setValue(i);
                Thread.sleep(30);
            }
        }
        catch(Exception e){
        	if(VariablesGlobales.DEBUG){
        		System.out.println("Error: GestorInicioAplicacion.iniciarCargaAplicacion() "+e.getMessage());
        		e.printStackTrace();
        		aplicacionValida = false;
        		
        		MostrarMensajes.mostrarMensaje(e.getMessage(), MostrarMensajes.MENSAJE_ERROR);
        	}
        }
        
        if(aplicacionValida){
        	ventana.getVentana().setVisible(false);
        	new VentanaPrincipal(autenticacion, preferencias);        	
        	
        }else{
        	MostrarMensajes.mostrarMensaje("El programa se cerrará debido a errores previos.\nAsegúrese que el servidor se este ejecutando", MostrarMensajes.MENSAJE_ERROR);
        	System.exit(0);
        }
	}
	
	public Properties buscarPropiedades() throws Exception{
		Properties p = UtilArchivos.leerXMLPropiedades(UtilArchivos.ARCHIVO_CONFIGURACION);
		
		if(!p.containsKey("usuario") || !p.containsKey("clave")){
			throw new Exception("No se pudo leer el archivo de configuración");
		}		
		
		return p;
	}
	
	public Properties buscarPreferencias() throws Exception{
		Properties p = UtilArchivos.leerXMLPropiedades(UtilArchivos.ARCHIVO_PREFERENCIAS);	
		
		return p;
	}
}
