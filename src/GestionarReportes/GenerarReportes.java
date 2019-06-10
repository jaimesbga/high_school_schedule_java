package GestionarReportes;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Point;
import javax.swing.JTextField;

import Utilitario.Autenticacion;
import Utilitario.Centrar;

import com.toedter.calendar.JDateChooser;

public class GenerarReportes {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="60,27"
	private JPanel panelVentana = null;
	private JPanel panelSuperior = null;  //  @jve:decl-index=0:visual-constraint="642,203"
	private JPanel panelCentral = null;
	private JComboBox cbxTipoReporte = null;
	private JLabel lblTipoReporte = null;
	private JPanel panelInferior = null;
	private JButton btnAceptar = null;
	private JButton btnCerrar = null;
	private JLabel lblSeccion = null;
	private JComboBox cbxSeccion = null;
	private JLabel lblAlumno = null;
	private JTextField txtCedula = null;
	private JLabel lblFechaInicio = null;
	private JLabel lblFechaFin = null;
	private JDateChooser txtFechaInicio = null;
	private JDateChooser txtFechaFin = null;
	private GestorGenerarReporte gestor;
	
	public GenerarReportes(Autenticacion autenticacion, JDesktopPane desktopPane){
		gestor = new GestorGenerarReporte(autenticacion, this);
		getVentana();
		
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);
		
		ventana.setVisible(true);
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getVentana() {
		if (ventana == null) {
			ventana = new JInternalFrame();
			ventana.setSize(new Dimension(486, 276));
			ventana.setClosable(true);
			ventana.setIconifiable(true);
			ventana.setTitle("Generar Reportes");
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
			panelVentana.setLayout(new BorderLayout());
			panelVentana.add(getPanelSuperior(), BorderLayout.NORTH);
			panelVentana.add(getPanelCentral(), BorderLayout.CENTER);
			panelVentana.add(getPanelInferior(), BorderLayout.SOUTH);
		}
		return panelVentana;
	}

	/**
	 * This method initializes panelSuperior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelSuperior() {
		if (panelSuperior == null) {
			lblTipoReporte = new JLabel();
			lblTipoReporte.setText("Tipo de reporte:");
			lblTipoReporte.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTipoReporte.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblTipoReporte.setPreferredSize(new Dimension(100, 20));
			panelSuperior = new JPanel();
			panelSuperior.setLayout(new FlowLayout());
			panelSuperior.setSize(new Dimension(186, 73));
			panelSuperior.add(lblTipoReporte, null);
			panelSuperior.add(getCbxTipoReporte(), null);			
		}
		return panelSuperior;
	}

	/**
	 * This method initializes panelCentral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelCentral() {
		if (panelCentral == null) {
			lblFechaFin = new JLabel();
			lblFechaFin.setText("Fecha final:");
			lblFechaFin.setSize(new Dimension(116, 20));
			lblFechaFin.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFechaFin.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblFechaFin.setLocation(new Point(0, 105));
			lblFechaInicio = new JLabel();
			lblFechaInicio.setText("Fecha inicial:");
			lblFechaInicio.setSize(new Dimension(116, 20));
			lblFechaInicio.setHorizontalAlignment(SwingConstants.RIGHT);
			lblFechaInicio.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblFechaInicio.setLocation(new Point(0, 75));
			lblAlumno = new JLabel();
			lblAlumno.setText("Cédula del alumno:");
			lblAlumno.setSize(new Dimension(116, 20));
			lblAlumno.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlumno.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblAlumno.setLocation(new Point(0, 45));
			lblSeccion = new JLabel();
			lblSeccion.setHorizontalAlignment(SwingConstants.RIGHT);
			lblSeccion.setLocation(new Point(0, 15));
			lblSeccion.setSize(new Dimension(116, 20));
			lblSeccion.setFont(new Font("SansSerif", Font.BOLD, 12));
			lblSeccion.setText("Sección:");
			panelCentral = new JPanel();
			panelCentral.setLayout(null);
			panelCentral.add(lblSeccion, null);
			panelCentral.add(getCbxSeccion(), null);
			panelCentral.add(lblAlumno, null);
			panelCentral.add(getTxtCedula(), null);
			panelCentral.add(lblFechaInicio, null);
			panelCentral.add(lblFechaFin, null);
			panelCentral.add(getTxtFechaInicio(), null);
			panelCentral.add(getTxtFechaFin(), null);
		}
		return panelCentral;
	}

	/**
	 * This method initializes cbxTipoReporte	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getCbxTipoReporte() {
		if (cbxTipoReporte == null) {
			cbxTipoReporte = new JComboBox();
			cbxTipoReporte.setPreferredSize(new Dimension(250, 26));
			cbxTipoReporte.setModel(new DefaultComboBoxModel(gestor.getTiposReporte()));
			cbxTipoReporte.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					gestor.seleccionarReporte();
				}
			});
		}
		return cbxTipoReporte;
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
			panelInferior.add(getBtnAceptar(), null);
			panelInferior.add(getBtnCerrar(), null);
		}
		return panelInferior;
	}

	/**
	 * This method initializes btnAceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton();
			btnAceptar.setPreferredSize(new Dimension(115, 35));
			btnAceptar.setText("Aceptar");
			btnAceptar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen017.png")));
			btnAceptar.setMnemonic(KeyEvent.VK_A);
			btnAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.generarReporte();
				}
			});
		}
		return btnAceptar;
	}

	/**
	 * This method initializes btnCerrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton();
			btnCerrar.setPreferredSize(new Dimension(115, 35));
			btnCerrar.setText("Cerrar");
			btnCerrar.setMnemonic(KeyEvent.VK_C);
			btnCerrar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen007.png")));
			btnCerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ventana.dispose();
				}
			});
		}
		return btnCerrar;
	}

	/**
	 * This method initializes cbxSeccion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getCbxSeccion() {
		if (cbxSeccion == null) {
			cbxSeccion = new JComboBox();
			cbxSeccion.setBounds(new Rectangle(123, 13, 184, 26));
			cbxSeccion.setEnabled(false);
			cbxSeccion.setModel(new DefaultComboBoxModel(gestor.getSecciones()));
		}
		return cbxSeccion;
	}

	/**
	 * This method initializes txtCedula	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTxtCedula() {
		if (txtCedula == null) {
			txtCedula = new JTextField();
			txtCedula.setLocation(new Point(123, 41));
			txtCedula.setEnabled(false);
			txtCedula.setSize(new Dimension(186, 25));
		}
		return txtCedula;
	}

	/**
	 * This method initializes txtFechaInicio	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JDateChooser getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			txtFechaInicio = new JDateChooser();
			txtFechaInicio.setLocation(new Point(123, 75));
			txtFechaInicio.setEnabled(false);
			txtFechaInicio.setSize(new Dimension(188, 25));
		}
		return txtFechaInicio;
	}

	/**
	 * This method initializes txtFechaFin	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JDateChooser getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JDateChooser();
			txtFechaFin.setLocation(new Point(123, 105));
			txtFechaFin.setEnabled(false);
			txtFechaFin.setSize(new Dimension(188, 25));
		}
		return txtFechaFin;
	}
}
