package GestionarEstudiantes;

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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTabbedPane;
import java.awt.Rectangle;

public class VentanaEditarEstudiantes {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="49,21"
	private JPanel panelVentana = null;
	private JPanel panelInferior = null;
	private JPanel panelCentral = null;
	private JButton btnGuardar = null;
	private JButton btnEliminar = null;
	private JLabel lblNombre = null;
	private JTextField txtNombre = null;
	
	private GestorEstudiantes gestor;  //  @jve:decl-index=0:
	private JLabel lblFechaNacimiento = null;
	private JDateChooser txtFechaNacimiento = null;
	private JLabel lblDireccion = null;
	private JLabel lblTelefono = null;
	private JLabel lblCorreo = null;
	private JLabel lblSexo = null;
	private JTextField txtTelefono = null;
	private JTextField txtCorreo = null;
	private JScrollPane scrollDireccion = null;
	private JTextArea txtDireccion = null;
	private JComboBox txtSexo = null;
	private JTabbedPane pestanas = null;
	private JPanel panelRepresentante = null;
	private JLabel lblNombreR = null;
	private JLabel lblCedulaR = null;
	private JLabel lblTelefonoR = null;
	private JTextField txtNombreR = null;
	private JTextField txtCedulaR = null;
	private JTextField txtTelefonoR = null;
	private JLabel lblNumeroLista = null;
	private JLabel lblSeccion = null;
	private JTextField txtNumeroLista = null;
	private JComboBox txtSeccion = null;
	private JLabel lblCedula = null;
	private JTextField txtCedula = null;
	public VentanaEditarEstudiantes(JDesktopPane desktopPane) {
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);
	}
	
	public void setGestor(GestorEstudiantes gestor){
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
			ventana.setSize(new Dimension(481, 452));
			ventana.setTitle("Estudiante");
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
			panelVentana.add(getPanelInferior(), BorderLayout.SOUTH);
			panelVentana.add(getPestanas(), BorderLayout.CENTER);
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
			lblCedula = new JLabel();
			lblCedula.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCedula.setText("*Cédula:");
			lblCedula.setBounds(new Rectangle(0, 45, 133, 20));
			lblCedula.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblSeccion = new JLabel();
			lblSeccion.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSeccion.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblSeccion.setBounds(new Rectangle(0, 105, 133, 20));
			lblSeccion.setText("*Sección:");
			lblNumeroLista = new JLabel();
			lblNumeroLista.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNumeroLista.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblNumeroLista.setBounds(new Rectangle(0, 75, 133, 20));
			lblNumeroLista.setText("Número de lista:");
			lblSexo = new JLabel();
			lblSexo.setText("*Sexo:");
			lblSexo.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSexo.setLocation(new Point(0, 225));
			lblSexo.setSize(new Dimension(133, 20));
			lblSexo.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblCorreo = new JLabel();
			lblCorreo.setText("Correo:");
			lblCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCorreo.setLocation(new Point(0, 195));
			lblCorreo.setSize(new Dimension(133, 20));
			lblCorreo.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblTelefono = new JLabel();
			lblTelefono.setText("Teléfono:");
			lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTelefono.setBounds(new Rectangle(0, 165, 133, 20));
			lblTelefono.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblDireccion = new JLabel();
			lblDireccion.setText("Dirección:");
			lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
			lblDireccion.setBounds(new Rectangle(0, 255, 133, 20));
			lblDireccion.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblFechaNacimiento = new JLabel();
			lblFechaNacimiento.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblFechaNacimiento.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFechaNacimiento.setBounds(new Rectangle(0, 135, 133, 20));
			lblFechaNacimiento.setText("*Fecha de nacimiento:");
			lblNombre = new JLabel();
			lblNombre.setText("*Nombre:");
			lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNombre.setLocation(new Point(0, 15));
			lblNombre.setSize(new Dimension(133, 20));
			lblNombre.setFont(new Font("SansSerif", Font.BOLD, 12));
			panelCentral = new JPanel();
			panelCentral.setLayout(null);
			panelCentral.add(lblNombre, null);
			panelCentral.add(getTxtNombre(), null);
			panelCentral.add(lblFechaNacimiento, null);
			panelCentral.add(getTxtFechaNacimiento(), null);
			panelCentral.add(lblDireccion, null);
			panelCentral.add(lblTelefono, null);
			panelCentral.add(lblCorreo, null);
			panelCentral.add(lblSexo, null);
			panelCentral.add(getTxtTelefono(), null);
			panelCentral.add(getTxtCorreo(), null);
			panelCentral.add(getScrollDireccion(), null);
			panelCentral.add(getTxtSexo(), null);
			panelCentral.add(lblNumeroLista, null);
			panelCentral.add(lblSeccion, null);
			panelCentral.add(getTxtNumeroLista(), null);
			panelCentral.add(getTxtSeccion(), null);
			panelCentral.add(lblCedula, null);
			panelCentral.add(getTxtCedula(), null);
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
						gestor.eliminar(gestor.getSeleccionado().getPersona().getId_persona());
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
			txtNombre.setBounds(new Rectangle(140, 13, 230, 25));
		}
		return txtNombre;
	}

	/**
	 * This method initializes txtFechaNacimiento	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JDateChooser getTxtFechaNacimiento() {
		if (txtFechaNacimiento == null) {
			txtFechaNacimiento = new JDateChooser();
			txtFechaNacimiento.setBounds(new Rectangle(140, 133, 148, 25));
		}
		return txtFechaNacimiento;
	}

	/**
	 * This method initializes txtTelefono	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtTelefono() {
		if (txtTelefono == null) {
			txtTelefono = new JTextField();
			txtTelefono.setBounds(new Rectangle(140, 163, 227, 25));
		}
		return txtTelefono;
	}

	/**
	 * This method initializes txtCorreo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtCorreo() {
		if (txtCorreo == null) {
			txtCorreo = new JTextField();
			txtCorreo.setBounds(new Rectangle(140, 193, 225, 25));
		}
		return txtCorreo;
	}

	/**
	 * This method initializes scrollDireccion	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollDireccion() {
		if (scrollDireccion == null) {
			scrollDireccion = new JScrollPane();
			scrollDireccion.setBounds(new Rectangle(140, 253, 297, 82));
			scrollDireccion.setViewportView(getTxtDireccion());
		}
		return scrollDireccion;
	}

	/**
	 * This method initializes txtDireccion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTxtDireccion() {
		if (txtDireccion == null) {
			txtDireccion = new JTextArea();
		}
		return txtDireccion;
	}

	/**
	 * This method initializes txtSexo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getTxtSexo() {
		if (txtSexo == null) {
			txtSexo = new JComboBox();
			txtSexo.setModel(new DefaultComboBoxModel(new String[]{"Masculino", "Femenino"}));
			txtSexo.setBounds(new Rectangle(140, 223, 121, 25));
		}
		return txtSexo;
	}

	/**
	 * This method initializes pestanas	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getPestanas() {
		if (pestanas == null) {
			pestanas = new JTabbedPane();
			pestanas.addTab("Estudiante", null, getPanelCentral(), null);
			pestanas.addTab("Representante", null, getPanelRepresentante(), null);
		}
		return pestanas;
	}

	/**
	 * This method initializes panelRepresentante	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelRepresentante() {
		if (panelRepresentante == null) {
			lblTelefonoR = new JLabel();
			lblTelefonoR.setText("Teléfono");
			lblTelefonoR.setSize(new Dimension(146, 20));
			lblTelefonoR.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTelefonoR.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblTelefonoR.setLocation(new Point(3, 75));
			lblCedulaR = new JLabel();
			lblCedulaR.setText("Cédula:");
			lblCedulaR.setSize(new Dimension(146, 20));
			lblCedulaR.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCedulaR.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblCedulaR.setLocation(new Point(3, 45));
			lblNombreR = new JLabel();
			lblNombreR.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNombreR.setText("Nombre:");
			lblNombreR.setLocation(new Point(3, 15));
			lblNombreR.setSize(new Dimension(146, 20));
			lblNombreR.setFont(new Font("SansSerif", Font.BOLD, 12));
			panelRepresentante = new JPanel();
			panelRepresentante.setLayout(null);
			panelRepresentante.add(lblNombreR, null);
			panelRepresentante.add(lblCedulaR, null);
			panelRepresentante.add(lblTelefonoR, null);
			panelRepresentante.add(getTxtNombreR(), null);
			panelRepresentante.add(getTxtCedulaR(), null);
			panelRepresentante.add(getTxtTelefonoR(), null);
		}
		return panelRepresentante;
	}

	/**
	 * This method initializes txtNombreR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtNombreR() {
		if (txtNombreR == null) {
			txtNombreR = new JTextField();
			txtNombreR.setSize(new Dimension(230, 25));
			txtNombreR.setLocation(new Point(155, 13));
		}
		return txtNombreR;
	}

	/**
	 * This method initializes txtCedulaR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtCedulaR() {
		if (txtCedulaR == null) {
			txtCedulaR = new JTextField();
			txtCedulaR.setLocation(new Point(155, 43));
			txtCedulaR.setSize(new Dimension(128, 25));
		}
		return txtCedulaR;
	}

	/**
	 * This method initializes txtTelefonoR	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtTelefonoR() {
		if (txtTelefonoR == null) {
			txtTelefonoR = new JTextField();
			txtTelefonoR.setLocation(new Point(155, 73));
			txtTelefonoR.setSize(new Dimension(230, 25));
		}
		return txtTelefonoR;
	}

	/**
	 * This method initializes txtNumeroLista	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtNumeroLista() {
		if (txtNumeroLista == null) {
			txtNumeroLista = new JTextField();
			txtNumeroLista.setBounds(new Rectangle(140, 73, 88, 25));
		}
		return txtNumeroLista;
	}

	/**
	 * This method initializes txtSeccion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getTxtSeccion() {
		if (txtSeccion == null) {
			txtSeccion = new JComboBox();
			txtSeccion.setBounds(new Rectangle(140, 103, 227, 26));
		}
		return txtSeccion;
	}

	/**
	 * This method initializes txtCedula	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtCedula() {
		if (txtCedula == null) {
			txtCedula = new JTextField();
			txtCedula.setBounds(new Rectangle(140, 43, 146, 25));
		}
		return txtCedula;
	}

}
