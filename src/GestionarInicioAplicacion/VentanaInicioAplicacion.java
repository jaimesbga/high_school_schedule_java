package GestionarInicioAplicacion;

import javax.swing.JWindow;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

import Utilitario.Centrar;

public class VentanaInicioAplicacion {

	private JWindow ventana = null;  //  @jve:decl-index=0:visual-constraint="113,46"
	private JPanel panelVentana = null;
	private JLabel jLabel = null;
	private JProgressBar progressBar = null;

	private GestorInicioAplicacion gestor;
	public VentanaInicioAplicacion() {
		gestor = new GestorInicioAplicacion(this);
		getVentana();
		ventana.setLocation(Centrar.centrarEnPantalla(ventana.getSize()));
		getVentana().setVisible(true);
		gestor.iniciarCargaAplicacion();
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JWindow	
	 */
	public JWindow getVentana() {
		if (ventana == null) {
			ventana = new JWindow();
			ventana.setSize(new Dimension(531, 247));
			ventana.setContentPane(getPanelVentana());
		}
		return ventana;
	}

	/**
	 * This method initializes panelVentana	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelVentana() {
		if (panelVentana == null) {
			jLabel = new JLabel();
			jLabel.setText("0%");
			jLabel.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen001.png")));
			jLabel.setBounds(new Rectangle(0, 0, 531, 248));
			panelVentana = new JPanel();
			panelVentana.setLayout(null);
			panelVentana.add(getProgressBar(), null);
			panelVentana.add(jLabel, null);			
		}
		return panelVentana;
	}

	/**
	 * This method initializes progressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	public JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setBounds(new Rectangle(1, 232, 531, 13));
			progressBar.setStringPainted(true);
		}
		return progressBar;
	}

}
