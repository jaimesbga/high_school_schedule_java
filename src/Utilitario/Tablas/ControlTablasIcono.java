package Utilitario.Tablas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class ControlTablasIcono implements Icon {
	private int width = 10;
    private int height = 10;
	
	@Override
	public int getIconHeight() {		
		return height;
	}

	@Override
	public int getIconWidth() {		
		return width;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color color = c.getForeground();
        g.setColor(color);

        // Dibujar lines horizontales
        g.drawLine(x, y, x+8, y);
        g.drawLine(x, y+2, x+8, y+2);
        g.drawLine(x, y+8, x+2, y+8);

        // Dibujar lineas verticales
        g.drawLine(x, y+1, x, y+7);
        g.drawLine(x+4, y+1, x+4, y+4);
        g.drawLine(x+8, y+1, x+8, y+4);

        // Dibujar flecha
        g.drawLine(x+3, y+6, x+9, y+6);
        g.drawLine(x+4, y+7, x+8, y+7);
        g.drawLine(x+5, y+8, x+7, y+8);
        g.drawLine(x+6, y+9, x+6, y+9);		
	}

}
