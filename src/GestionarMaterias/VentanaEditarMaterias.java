package GestionarMaterias;

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
import java.awt.Rectangle;

public class VentanaEditarMaterias {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="48,24"
	private JPanel panelVentana = null;
	private JPanel panelInferior = null;
	private JPanel panelCentral = null;
	private JButton btnGuardar = null;
	private JButton btnEliminar = null;
	private JLabel lblNombre = null;
	private JTextField txtNombre = null;
	
	private GestorMaterias gestor;  //  @jve:decl-index=0:
	public VentanaEditarMaterias(JDesktopPane desktopPane) {
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);		
	}
	
	public void setGestor(GestorMaterias gestor){
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
			ventana.setTitle("Materia");
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
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(20);
			panelInferior = new JPanel();
			panelInferior.setLayout(flowLayout);
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
			lblNombre = new JLabel();
			lblNombre.setText("*Nombre:");
			lblNombre.setSize(new Dimension(91, 20));
			lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblNombre.setLocation(new Point(14, 29));
			panelCentral = new JPanel();
			panelCentral.setLayout(null);
			panelCentral.add(lblNombre, null);
			panelCentral.add(getTxtNombre(), null);
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
						gestor.eliminar(gestor.getSeleccionado().getId_materia());
					}
				}
			});
		}
		return btnEliminar;
	}

	/**
	 * This method initializes txtNombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(new Rectangle(112, 25, 200, 25));
		}
		return txtNombre;
	}

}
