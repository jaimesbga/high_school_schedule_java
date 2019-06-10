package Utilitario;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MostrarMensajes {
	public static final int MENSAJE_ERROR = 1;
	public static final int MENSAJE_EXITO = 2;
	public static final int MENSAJE_PREGUNTA = 3;
	
	public static boolean mostrarMensaje(String mensaje, int tipoMensaje){
		return mostrarMensaje(null, mensaje, tipoMensaje);
	}
	
	public static boolean mostrarMensaje(Component ventana, String mensaje, int tipoMensaje){
		MostrarMensajes obj = new MostrarMensajes();		
		
		if(tipoMensaje == MENSAJE_ERROR){
			JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen006.png")));
		}
		if(tipoMensaje == MENSAJE_EXITO){
			JOptionPane.showMessageDialog(ventana, mensaje, "Correcto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen017.png")));
		}
		if(tipoMensaje == MENSAJE_PREGUNTA){			
			int op = JOptionPane.showConfirmDialog(ventana, mensaje, "Sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen009.png")));
			return op == JOptionPane.YES_OPTION;
		}
		
		return true;
	}
	
	/*public static boolean mostrarMensaje(JDialog ventana, String mensaje, int tipoMensaje){
		MostrarMensajes obj = new MostrarMensajes();		
		
		if(tipoMensaje == MENSAJE_ERROR){
			JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen006.png")));
		}
		if(tipoMensaje == MENSAJE_EXITO){
			JOptionPane.showMessageDialog(ventana, mensaje, "Correcto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen017.png")));
		}
		if(tipoMensaje == MENSAJE_PREGUNTA){			
			int op = JOptionPane.showConfirmDialog(ventana, "Realmente desea salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen009.png")));
			return op == JOptionPane.YES_OPTION;
		}
		
		return true;
	}*/
	
	/*public static boolean mostrarMensaje(JInternalFrame ventana, String mensaje, int tipoMensaje){
		MostrarMensajes obj = new MostrarMensajes();		
		
		if(tipoMensaje == MENSAJE_ERROR){
			JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen006.png")));
		}
		if(tipoMensaje == MENSAJE_EXITO){
			JOptionPane.showMessageDialog(ventana, mensaje, "Correcto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen017.png")));
		}
		if(tipoMensaje == MENSAJE_PREGUNTA){			
			int op = JOptionPane.showConfirmDialog(ventana, "Realmente desea salir?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(obj.getClass().getResource("/Imagenes/Imagen009.png")));
			return op == JOptionPane.YES_OPTION;
		}
		
		return true;
	}*/
}
