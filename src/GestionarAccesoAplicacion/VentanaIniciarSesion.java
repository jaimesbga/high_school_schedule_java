package GestionarAccesoAplicacion;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Point;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import Consultas.UsuariosDAO;
import Entidades.Usuario;
import Utilitario.Autenticacion;
import Utilitario.Centrar;
import Utilitario.MostrarMensajes;

public class VentanaIniciarSesion {

	private JDialog ventana = null;  //  @jve:decl-index=0:visual-constraint="120,56"
	private JPanel panelVentana = null;
	private JPanel panelDatos = null;
	private JButton btn_aceptar = null;
	private JButton btn_cancelar = null;
	private JLabel lbl_usuario = null;
	private JLabel lbl_clave = null;
	private JTextField txt_usuario = null;
	private JPasswordField txt_clave = null;

	private Autenticacion autenticacion;
	private boolean conectado;
	public VentanaIniciarSesion(Autenticacion autenticacion, Rectangle v){
		this.autenticacion = autenticacion;
		conectado = false;
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), v.getBounds()));
	}
	
	public void aceptar(){
		conectado = false;
		String _usuario = txt_usuario.getText();
		String _clave = String.valueOf(txt_clave.getPassword());
		UsuariosDAO dao = new UsuariosDAO(autenticacion);
		Usuario usuario = dao.validarUsuario(_usuario, _clave);
		if(usuario == null){
			MostrarMensajes.mostrarMensaje(ventana, "Usuario o contraseña incorrecta", MostrarMensajes.MENSAJE_ERROR);
			txt_clave.setText("");
		}else{
			conectado = true;
			autenticacion.setUsuario(usuario);
			MostrarMensajes.mostrarMensaje(ventana, "Bienvenido(a): "+usuario.getNombre(), MostrarMensajes.MENSAJE_EXITO);
			ventana.setVisible(false);
		}		
	}
	
	public void cancelar(){
		conectado = false;
		ventana.setVisible(false);
	}
	
	public boolean getConectado(){
		return conectado;
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	public JDialog getVentana() {
		if (ventana == null) {
			ventana = new JDialog();
			ventana.setSize(new Dimension(323, 221));
			ventana.setTitle("Iniciar sesión");
			ventana.setModal(true);
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
			panelVentana = new JPanel();
			panelVentana.setLayout(null);
			panelVentana.add(getPanelDatos(), null);
			panelVentana.add(getBtn_aceptar(), null);
			panelVentana.add(getBtn_cancelar(), null);
		}
		return panelVentana;
	}

	/**
	 * This method initializes panelDatos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelDatos() {
		if (panelDatos == null) {
			lbl_clave = new JLabel();
			lbl_clave.setText("Contraseña:");
			lbl_clave.setLocation(new Point(15, 70));
			lbl_clave.setSize(new Dimension(80, 25));
			lbl_clave.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl_usuario = new JLabel();
			lbl_usuario.setText("Usuario:");
			lbl_usuario.setLocation(new Point(15, 35));
			lbl_usuario.setSize(new Dimension(80, 25));
			lbl_usuario.setHorizontalAlignment(SwingConstants.RIGHT);
			panelDatos = new JPanel();
			panelDatos.setLayout(null);
			panelDatos.setBounds(new Rectangle(13, 10, 289, 116));
			panelDatos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED), "Datos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
			panelDatos.add(lbl_usuario, null);
			panelDatos.add(lbl_clave, null);
			panelDatos.add(getTxt_usuario(), null);
			panelDatos.add(getTxt_clave(), null);
		}
		return panelDatos;
	}

	/**
	 * This method initializes btn_aceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_aceptar() {
		if (btn_aceptar == null) {
			btn_aceptar = new JButton();
			btn_aceptar.setMnemonic(KeyEvent.VK_A);
			btn_aceptar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen017.png")));
			btn_aceptar.setBounds(new Rectangle(30, 140, 120, 35));
			btn_aceptar.setText("Aceptar");
			btn_aceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aceptar();
				}
			});
		}
		return btn_aceptar;
	}

	/**
	 * This method initializes btn_cancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_cancelar() {
		if (btn_cancelar == null) {
			btn_cancelar = new JButton();
			btn_cancelar.setText("Cancelar");
			btn_cancelar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen007.png")));
			btn_cancelar.setBounds(new Rectangle(166, 140, 120, 35));
			btn_cancelar.setMnemonic(KeyEvent.VK_C);
			btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelar();
				}
			});
		}
		return btn_cancelar;
	}

	/**
	 * This method initializes txt_usuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxt_usuario() {
		if (txt_usuario == null) {
			txt_usuario = new JTextField();
			txt_usuario.setLocation(new Point(100, 35));
			txt_usuario.setSize(new Dimension(170, 25));
			txt_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == 10) {
                        aceptar();
                    }
				}
			});
		}
		return txt_usuario;
	}

	/**
	 * This method initializes txt_clave	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxt_clave() {
		if (txt_clave == null) {
			txt_clave = new JPasswordField();
			txt_clave.setText("");
			txt_clave.setSize(new Dimension(170, 25));
			txt_clave.setLocation(new Point(100, 70));
			txt_clave.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == 10) {
                        aceptar();
                    }
				}
			});
		}
		return txt_clave;
	}

}
