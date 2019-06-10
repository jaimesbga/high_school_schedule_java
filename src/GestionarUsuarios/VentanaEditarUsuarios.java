package GestionarUsuarios;

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
import javax.swing.JPasswordField;

public class VentanaEditarUsuarios {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="48,24"
	private JPanel panelVentana = null;
	private JPanel panelInferior = null;
	private JPanel panelCentral = null;
	private JButton btnGuardar = null;
	private JButton btnEliminar = null;
	private JLabel lblNombre = null;
	private JTextField txtNombre = null;
	
	private GestorUsuarios gestor;  //  @jve:decl-index=0:
	private JLabel lblUsuario = null;
	private JTextField txtUsuario = null;
	private JLabel lblClave1 = null;
	private JLabel lblClave2 = null;
	private JPasswordField txtClave1 = null;
	private JPasswordField txtClave2 = null;
	public VentanaEditarUsuarios(JDesktopPane desktopPane) {
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);		
	}
	
	public void setGestor(GestorUsuarios gestor){
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
			ventana.setTitle("Usuario");
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
			lblClave2 = new JLabel();
			lblClave2.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblClave2.setLocation(new Point(14, 105));
			lblClave2.setSize(new Dimension(91, 20));
			lblClave2.setHorizontalAlignment(SwingConstants.RIGHT);
			lblClave2.setText("*Confirmación:");
			lblClave1 = new JLabel();
			lblClave1.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblClave1.setLocation(new Point(14, 75));
			lblClave1.setSize(new Dimension(91, 20));
			lblClave1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblClave1.setText("*Clave:");
			lblUsuario = new JLabel();
			lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblUsuario.setLocation(new Point(14, 45));
			lblUsuario.setSize(new Dimension(91, 20));
			lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
			lblUsuario.setText("*Usuario:");
			lblNombre = new JLabel();
			lblNombre.setText("*Nombre:");
			lblNombre.setSize(new Dimension(91, 20));
			lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblNombre.setLocation(new Point(14, 15));
			panelCentral = new JPanel();
			panelCentral.setLayout(null);
			panelCentral.add(lblNombre, null);
			panelCentral.add(getTxtNombre(), null);
			panelCentral.add(lblUsuario, null);
			panelCentral.add(getTxtUsuario(), null);
			panelCentral.add(lblClave1, null);
			panelCentral.add(lblClave2, null);
			panelCentral.add(getTxtClave1(), null);
			panelCentral.add(getTxtClave2(), null);
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
						gestor.eliminar(gestor.getSeleccionado().getId_usuario());
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
			txtNombre.setSize(new Dimension(200, 25));
			txtNombre.setLocation(new Point(112, 13));
		}
		return txtNombre;
	}

	/**
	 * This method initializes txtUsuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtUsuario() {
		if (txtUsuario == null) {
			txtUsuario = new JTextField();
			txtUsuario.setLocation(new Point(112, 43));
			txtUsuario.setSize(new Dimension(200, 25));
		}
		return txtUsuario;
	}

	/**
	 * This method initializes txtClave1	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	public JPasswordField getTxtClave1() {
		if (txtClave1 == null) {
			txtClave1 = new JPasswordField();
			txtClave1.setLocation(new Point(112, 73));
			txtClave1.setSize(new Dimension(200, 25));
		}
		return txtClave1;
	}

	/**
	 * This method initializes txtClave2	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	public JPasswordField getTxtClave2() {
		if (txtClave2 == null) {
			txtClave2 = new JPasswordField();
			txtClave2.setLocation(new Point(112, 103));
			txtClave2.setSize(new Dimension(200, 25));
		}
		return txtClave2;
	}

}
