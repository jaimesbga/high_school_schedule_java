package Utilitario;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class UtilArchivos {
	public static final int ARCHIVO_PREFERENCIAS = 1;
	public static final int ARCHIVO_CONFIGURACION = 2;
	
	public static Properties leerXMLPropiedades(int archivo){
		Properties p = new Properties();
		
		if(archivo == ARCHIVO_PREFERENCIAS){
			p = leerXMLPropiedades("Archivos/Preferencias/aplicacion.xml");
		}
		if(archivo == ARCHIVO_CONFIGURACION){
			p = leerXMLPropiedades("Archivos/Configuracion/propiedades.xml");
		}
		
		return p;
	}
	
	public static Properties leerXMLPropiedades(String ruta){
		Properties p = new Properties();
		try{
			FileInputStream fis = new FileInputStream(Path.getPath(ruta));			
			p.loadFromXML(fis);
			fis.close();
		}
		catch(Exception e){			
			if(VariablesGlobales.DEBUG){
				System.out.println("[Error] UtilArchivos.leerXMLPropiedades(): "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	public static boolean escribirXMLPropiedades(int archivo, Properties propiedades){
		boolean retorna = false;
		if(archivo == ARCHIVO_PREFERENCIAS){
			retorna = escribirXMLPropiedades("Archivos/Preferencias/aplicacion.xml", propiedades);
		}
		if(archivo == ARCHIVO_CONFIGURACION){
			retorna = escribirXMLPropiedades("Archivos/Configuracion/propiedades.xml", propiedades);
		}
		
		return retorna;
	}
	
	public static boolean escribirXMLPropiedades(String ruta, Properties propiedades){
		boolean retorna = false;
		try{
			FileOutputStream fos = new FileOutputStream(Path.getPath(ruta));			
			propiedades.storeToXML(fos, null);
			fos.close();
			
			retorna = true;
		}
		catch(Exception e){			
			if(VariablesGlobales.DEBUG){
				System.out.println("[Error] UtilArchivos.escribirXMLPropiedades(): "+e.getMessage());
				e.printStackTrace();
			}			
		}
		
		return retorna;
	}
	
}
