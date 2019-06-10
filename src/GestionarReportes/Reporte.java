package GestionarReportes;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import Utilitario.MostrarMensajes;
import Utilitario.Path;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte extends Thread{
	private HashMap<String, Object> parametros;
    private JasperPrint jasperPrint;
    private String archivo;
    private Connection conexion;
	
    public Reporte() {
        parametros = new HashMap<String, Object>();
        jasperPrint = null;
        archivo = "";
        conexion = null;
    }
    
    public void run(){
    	try{
    		generarReporte();
    		visualizarReporte();
    	}
    	catch(Exception e){
    		MostrarMensajes.mostrarMensaje("No se pudo generar el reporte", MostrarMensajes.MENSAJE_ERROR);
    	}
    }
    
    public void exportarArchivoPDF(String rutaSalida){
        if(jasperPrint == null){            
            return ;
        }        
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, rutaSalida);        
    }
    
    public void visualizarReporte() {
    	JasperViewer.viewReport(jasperPrint, false);    	
	}
    
    public void generarReporte() throws Exception{
        try{        	
            File jasper = new File(Path.getPath() + archivo);
            
            if(jasper == null || !jasper.exists() || jasper.isDirectory()){
                throw new Exception("No se encontro el archivo "+archivo);
            }
            
            jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conexion);
        }
        catch(Exception e){
            System.out.println("Error: Reporte.generarReporte(): "+e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally{
	        try{
	        	conexion.close();
	        }
	        catch(Exception e){
	        }
        }
    }
    
	public void setParametros(HashMap<String, Object> parametros) {
		this.parametros = parametros;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}    
}
