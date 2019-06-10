package GestionarBloques;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Point;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import Utilitario.Centrar;
import Utilitario.MostrarMensajes;
import java.awt.Font;
import javax.swing.WindowConstants;

public class VentanaEditarBloques {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="48,24"
	private JPanel panelVentana = null;
	private JPanel panelInferior = null;
	private JPanel panelCentral = null;
	private JButton btnGuardar = null;
	private JButton btnEliminar = null;
	private JLabel lblNumero = null;
	private JLabel lblDescripcion = null;
	private JTextField txtNumero = null;
	private JTextField txtDescripcion = null;
	
	private GestorBloques gestor;  //  @jve:decl-index=0:
	public VentanaEditarBloques(JDesktopPane desktopPane) {
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);		
	}
	
	public void setGestor(GestorBloques gestor){
		this.gestor = gestor;
	}
	
	public void mostrarVentana(){
		ventana.setVisible(true);
		ventana.hide();
		ventana.show();
	}
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	public JInternalFrame getVentana() {
		if (ventana == null) {
			ventana = new JInternalFrame();
			ventana.setSize(new Dimension(358, 220));
			ventana.setTitle("Bloque");
			ventana.setIconifiable(true);
			ventana.setResizable(true);
			ventana.setClosable(true);
			ventana.setMaximizable(true);
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			ventana.setContentPane(getPanelVentana());
			ventana.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
				public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
					ventana.dispose();
					gestor.setVentanaEditar(null);
				}
			});
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
			panelVentana = new JPanel();
			panelVentana.setLayout(new BorderLayout());
			panelVentana.add(getPanelCentral(), BorderLayout.CENTER);
			panelVentana.add(getPanelInferior(), BorderLayout.SOUTH);
		}
		return panelVentana;
	}

	/**
	 * This method initializes panelInferior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelInferior() {
		if (panelInferior == null) {
			panelInferior = new JPanel();
			panelInferior.setLayout(new FlowLayout());
			panelInferior.add(getBtnGuardar(), null);
			panelInferior.add(getBtnEliminar(), null);
		}
		return panelInferior;
	}

	/**
	 * This method initializes panelCentral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			lblDescripcion = new JLabel();
			lblDescripcion.setText("*Descripción:");
			lblDescripcion.setSize(new Dimension(117, 20));
			lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
			lblDescripcion.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblDescripcion.setLocation(new Point(10, 66));
			lblNumero = new JLabel();
			lblNumero.setText("Número de bloque:");
			lblNumero.setSize(new Dimension(117, 20));
			lblNumero.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNumero.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblNumero.setLocation(new Point(10, 36));
			panelCentral = new JPanel();
			panelCentral.setLayout(null);
			panelCentral.add(lblNumero, null);
			panelCentral.add(lblDescripcion, null);
			panelCentral.add(getTxtNumero(), null);
			panelCentral.add(getTxtDescripcion(), null);
		}
		return panelCentral;
	}

	/**
	 * This method initializes btnGuardar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnGuardar() {
		if (btnGuardar == null) {
			btnGuardar = new JButton();
			btnGuardar.setPreferredSize(new Dimension(115, 35));
			btnGuardar.setMnemonic(KeyEvent.VK_G);
			btnGuardar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			btnGuardar.setText("Guardar");
			btnGuardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.guardar();
				}
			});
		}
		return btnGuardar;
	}

	/**
	 * This method initializes btnEliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton();
			btnEliminar.setPreferredSize(new Dimension(115, 35));
			btnEliminar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen018.png")));
			btnEliminar.setText("Eliminar");
			btnEliminar.setMnemonic(KeyEvent.VK_E);
			btnEliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					if(MostrarMensajes.mostrarMensaje("¿Realmente desea eliminar el registro?", MostrarMensajes.MENSAJE_PREGUNTA)){
						gestor.eliminar(gestor.getSeleccionado().getId_bloque());
					}
				}
			});
		}
		return btnEliminar;
	}

	/**
	 * This method initializes txtNumero	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtNumero() {
		if (txtNumero == null) {
			txtNumero = new JTextField();
			txtNumero.setLocation(new Point(134, 34));
			txtNumero.setSize(new Dimension(57, 25));
		}
		return txtNumero;
	}

	/**
	 * This method initializes txtDescripcion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtDescripcion() {
		if (txtDescripcion == null) {
			txtDescripcion = new JTextField();
			txtDescripcion.setLocation(new Point(134, 64));
			txtDescripcion.setSize(new Dimension(200, 25));
		}
		return txtDescripcion;
	}

}
