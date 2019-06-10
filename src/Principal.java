import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

import GestionarInicioAplicacion.VentanaInicioAplicacion;
import Utilitario.VariablesGlobales;



public class Principal {
	
	public static void main(String[] args) {
		if(args.length == 1){
			try{
				if(args[0].compareTo("-debug")==0){				
					VariablesGlobales.DEBUG = true;
				}
			}
			catch(Exception e){				
			}
		}
		
		Principal principal = new Principal();
		principal.cambiarEstiloSwing();
		
		new VentanaInicioAplicacion();
	}
	
	public void cambiarEstiloSwing(){
		JDialog.setDefaultLookAndFeelDecorated(true);
		try{			
		//NimbusLookAndFeel laf = new NimbusLookAndFeel();	
		MetalLookAndFeel laf = new MetalLookAndFeel();
			UIManager.setLookAndFeel(laf);			
		}
		catch(Exception e){			
		}
	}

}
