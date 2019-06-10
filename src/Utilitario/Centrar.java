package Utilitario;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Centrar {
	public static Point centrarEnPantalla(Dimension dimension){
		Point p = new Point();
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) (pantalla.getWidth() / 2 - dimension.getWidth() / 2);
		int y = (int) (pantalla.getHeight() / 2 - dimension.getHeight() / 2);
		
		p.setLocation(x, y);
		
		return p;
	}
	
	public static Point centrarEnComponente(Dimension sizeVentana, Dimension padreBounds){
        return centrarEnComponente(sizeVentana, new Rectangle(padreBounds));
	}
	
	public static Point centrarEnComponente(Dimension sizeVentana, Rectangle padreBounds){
		Point p = new Point();
		
        int x = (int) Math.max(0, padreBounds.getX() + (padreBounds.getWidth() - sizeVentana.getWidth()) / 2);
        int y = (int)Math.max(0, padreBounds.getY() + (padreBounds.getHeight() - sizeVentana.getHeight()) / 2);
        
        p.setLocation(x, y);
        
        return p;
	}
}
