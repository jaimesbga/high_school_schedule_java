package GestionarEstudiantes;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JDesktopPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;

import Utilitario.Centrar;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.JTableListado;
import javax.swing.WindowConstants;

public class VentanaListadoEstudiantes {
	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="90,27"
	private JPanel panelVentana = null;
	private JPanel panelInferior = null;
	private JPanel panelCentral = null;
	private JScrollPane scrollTabla = null;
	private JTableListado tablaListado = null;
	private JButton btnCerrar = null;
	private JButton btnEditar = null;
	private JButton btnNuevo = null;
	private JButton btnEliminar = null;

	private GestorEstudiantes gestor;  //  @jve:decl-index=0:
	public VentanaListadoEstudiantes(JDesktopPane desktopPane){
		getVentana();
		ventana.setLocation(Centrar.centrarEnComponente(ventana.getSize(), desktopPane.getSize()));
		desktopPane.add(ventana);
		ventana.setVisible(true);
		try{
			ventana.setSelected(true);
		}catch(Exception e){			
		}
	}
	
	public void setGestor(GestorEstudiantes gestor){
		this.gestor = gestor;
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	public JInternalFrame getVentana() {
		if (ventana == null) {
			ventana = new JInternalFrame();
			ventana.setSize(new Dimension(453, 342));
			ventana.setTitle("Listado de estudiantes");
			ventana.setClosable(true);
			ventana.setIconifiable(true);
			ventana.setMaximizable(true);
			ventana.setVisible(false);
			ventana.setResizable(true);
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			ventana.setContentPane(getPanelVentana());
			ventana.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
				public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
					ventana.dispose();
					gestor.setVentanaListado(null);
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
			panelVentana.add(getPanelCentral(), BorderLayout.CENTER);
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
			panelInferior.add(getBtnNuevo(), null);
			panelInferior.add(getBtnEditar(), null);			
			panelInferior.add(getBtnEliminar(), null);
			panelInferior.add(getBtnCerrar(), null);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			panelCentral = new JPanel();
			panelCentral.setLayout(new GridBagLayout());
			panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			panelCentral.add(getScrollTabla(), gridBagConstraints);
		}
		return panelCentral;
	}

	/**
	 * This method initializes scrollTabla	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollTabla() {
		if (scrollTabla == null) {
			scrollTabla = new JScrollPane();
			scrollTabla.setViewportView(getTablaListado());
		}
		return scrollTabla;
	}

	/**
	 * This method initializes tablaListado	
	 * 	
	 * @return javax.swing.JTable	
	 */
	public JTableListado getTablaListado() {
		if (tablaListado == null) {
			tablaListado = new JTableListado(
					new String[]{"", "N°", "Cédula", "Nombre", "Sección"},
					new int[]{10, 30, 50, 20},
					false,
					true,
					true,
					scrollTabla);
		}
		tablaListado.actualizarAnchoColumnas();
		
		return tablaListado;
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
			btnCerrar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen007.png")));
			btnCerrar.setText("Cerrar");
			btnCerrar.setVisible(false);
		}
		return btnCerrar;
	}

	/**
	 * This method initializes btnEditar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEditar() {
		if (btnEditar == null) {
			btnEditar = new JButton();
			btnEditar.setPreferredSize(new Dimension(115, 35));
			btnEditar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen020.png")));
			btnEditar.setText("Editar");
			btnEditar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = tablaListado.getFilaSeleccionada();
					if(index>=0){
						gestor.prepararEditar(tablaListado.getModel().getItem(index).getValorInt());
					}
				}
			});
		}
		return btnEditar;
	}

	/**
	 * This method initializes btnNuevo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnNuevo() {
		if (btnNuevo == null) {
			btnNuevo = new JButton();
			btnNuevo.setPreferredSize(new Dimension(115, 35));
			btnNuevo.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen021.png")));
			btnNuevo.setText("Nuevo");
			btnNuevo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.prepararCrear();
				}
			});
		}
		return btnNuevo;
	}

	/**
	 * This method initializes btnEliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEliminar() {
		if (btnEliminar == null) {
			btnEliminar = new JButton();
			btnEliminar.setPreferredSize(new Dimension(115, 35));
			btnEliminar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen018.png")));
			btnEliminar.setText("Eliminar");
			btnEliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int index = tablaListado.getFilaSeleccionada();
					if(index >= 0){
						if(MostrarMensajes.mostrarMensaje("¿Realmente desea eliminar el registro?", MostrarMensajes.MENSAJE_PREGUNTA)){
							Item itm = tablaListado.getModel().getItem(index);
							gestor.eliminar(itm.getValorInt());
						}
					}
				}
			});
		}
		return btnEliminar;
	}

}
