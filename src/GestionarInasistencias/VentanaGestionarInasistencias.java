package GestionarInasistencias;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Utilitario.Autenticacion;
import Utilitario.MostrarMensajes;

import com.toedter.calendar.JDateChooser;

public class VentanaGestionarInasistencias {
	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="10,54"
	private JPanel p_principal = null;
	private JPanel p_registro_actividad = null;
	private JComboBox c_periodo = null;
	private JLabel l_seccion = null;
	private JLabel l_periodo = null;
	private JButton b_buscar = null;
	private JLabel l_fecha_actividad = null;
	private JLabel l_tipo_actividad = null;
	private JComboBox c_tipo_actividad = null;
	private JLabel l_observaciones = null;
	private JTextArea ta_observaciones = null;
	private JButton b_procesar = null;
	private String[] secciones = new String[]{
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R",
			"S","T","U","V","W","X","Y","Z"
			};
	private JComboBox c_seccion = null;
	private Autenticacion autenticacion = null;
	private JDesktopPane desktopPane = null;
	private JDateChooser chooser = null;
	private JPanel p_horario = null;
	private GestorInasistencias gestor = null;
	private JScrollPane ScrollPane = null;
	private JTable t_inasistencias = null;
	private int[] anchoColumnas = null;
	private JButton b_guardar = null;

	
	public VentanaGestionarInasistencias(Autenticacion autenticacion, JDesktopPane desktopPane){
		this.autenticacion = autenticacion;
		this.desktopPane = desktopPane;
		
		getVentana();
		int x = Math.max(0, (desktopPane.getWidth() - ventana.getWidth()-15) / 2);
        int y = Math.max(0, (desktopPane.getHeight() - ventana.getHeight() -70) / 2);
        ventana.setLocation(new Point(x, y)); 		
		anchoColumnas = new int[]{10,25,25,40};
		
		gestor = new GestorInasistencias(autenticacion,this);
		actualizarAnchoColumnas();
		
	}
	
	
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}


	public JInternalFrame getVentana(){
		if(ventana == null){
			ventana = new JInternalFrame("Gestor de Actividades Diarias");	
			ventana.setVisible(true);
			ventana.setSize(new Dimension(552, 527));			
			
			ventana.setIconifiable(true);
			ventana.setResizable(false);
			ventana.setClosable(true);
			ventana.setMaximizable(false);
			ventana.setContentPane(getP_principal());
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);			
			ventana.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {   
				public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {    
					System.out.println("Evento internalFrameClosed()");
					
					switch (gestor.getEstado_ventana()) {					
					case 1:getVentana().dispose();break;				
					case 2:gestor.operacionCerrarVentana();break;
					case 3:{
		boolean tip = MostrarMensajes.mostrarMensaje(getVentana(), "Desea Agregar Otra Actividad.?",
				MostrarMensajes.MENSAJE_PREGUNTA);	
						if(tip){
							gestor.InicializarControles();
						}else{
							getVentana().dispose();
						}
		
					}break;
					
					}	// fin switvh			
				}

			});
			desktopPane.add(ventana);
		}
			return ventana;
	}


	/**
	 * This method initializes p_principal	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_principal() {
		if (p_principal == null) {
			p_principal = new JPanel();
			p_principal.setLayout(null);
			p_principal.add(getP_registro_actividad(), null);
			p_principal.add(getP_horario(), null);
			p_principal.add(getB_guardar(), null);
		}
		return p_principal;
	}

	/**
	 * This method initializes p_registro_actividad	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_registro_actividad() {
		if (p_registro_actividad == null) {
			l_observaciones = new JLabel();
			l_observaciones.setBounds(new Rectangle(15, 103, 104, 16));
			l_observaciones.setText("Observaciones:");
			l_tipo_actividad = new JLabel();
			l_tipo_actividad.setBounds(new Rectangle(237, 74, 103, 16));
			l_tipo_actividad.setText("Tipo de Actividad:");
			l_fecha_actividad = new JLabel();
			l_fecha_actividad.setBounds(new Rectangle(20, 74, 38, 16));
			l_fecha_actividad.setText("Fecha:");
			l_periodo = new JLabel();
			l_periodo.setBounds(new Rectangle(180, 35, 60, 16));
			l_periodo.setText("Periodo:");
			l_seccion = new JLabel();
			l_seccion.setBounds(new Rectangle(18, 36, 57, 16));
			l_seccion.setText("Seccion:");
			p_registro_actividad = new JPanel();
			p_registro_actividad.setLayout(null);
			p_registro_actividad.setBounds(new Rectangle(6, 10, 525, 197));
			p_registro_actividad.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)), "Datos de Actividad Diaria", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			p_registro_actividad.setBackground(new Color(214, 217, 223));
			p_registro_actividad.add(getC_periodo(), null);
			p_registro_actividad.add(l_seccion, null);
			p_registro_actividad.add(l_periodo, null);
			p_registro_actividad.add(getB_buscar(), null);
			p_registro_actividad.add(l_fecha_actividad, null);
			p_registro_actividad.add(l_tipo_actividad, null);
			p_registro_actividad.add(getC_tipo_actividad(), null);
			p_registro_actividad.add(l_observaciones, null);
			p_registro_actividad.add(getTa_observaciones(), null);
			p_registro_actividad.add(getB_procesar(), null);
			p_registro_actividad.add(getC_seccion(), null);
			p_registro_actividad.add(getChooser(),null);
		}
		return p_registro_actividad;
	}


	/**
	 * This method initializes c_periodo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	@SuppressWarnings("deprecation")
	public JComboBox getC_periodo() {
		int year_begin = 0;
		java.util.Date date = new java.util.Date();
		int year = date.getYear();
		year_begin = year + 1900;
				if (c_periodo == null) {
			c_periodo = new JComboBox();
			c_periodo.setBounds(new Rectangle(244, 30, 86, 25));
			for(int i = 0; i<10;i++){
				c_periodo.addItem(year_begin);
				year_begin = year_begin + 1;
			}
		}
		return c_periodo;
	}


	/**
	 * This method initializes b_buscar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_buscar() {
		if (b_buscar == null) {
			b_buscar = new JButton("Disponibilidad");
			b_buscar.setBounds(new Rectangle(341, 30, 168, 27));
			b_buscar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen008.png")));
			b_buscar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
						if(gestor.estaDisponible()){
							b_buscar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen029.png")));
							b_buscar.repaint();
							b_procesar.setEnabled(true);
						}else{
							b_buscar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen008.png")));
							b_procesar.setEnabled(false);
						}
				}
			});
		}
		return b_buscar;
	}


	/**
	 * This method initializes c_tipo_actividad	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JDateChooser getChooser() {
		if (chooser == null) {
			Date date = new Date();
			chooser = new JDateChooser(date);
			chooser.setLocation(new Point(68, 70));
			chooser.setSize(new Dimension(148, 25));
		}
		return chooser;
	}
	
	public JComboBox getC_tipo_actividad() {
		if (c_tipo_actividad == null) {
			c_tipo_actividad = new JComboBox();
			c_tipo_actividad.setBounds(new Rectangle(343, 70, 168, 25));
			c_tipo_actividad.addItem("Clase Normal");
			c_tipo_actividad.addItem("Clase Suspendida");
			c_tipo_actividad.addItem("Otra Actividad");
		}
		return c_tipo_actividad;
	}


	/**
	 * This method initializes ta_observaciones	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	public JTextArea getTa_observaciones() {
		if (ta_observaciones == null) {
			ta_observaciones = new JTextArea();
			ta_observaciones.setBounds(new Rectangle(15, 122, 494, 31));
			ta_observaciones.setText("Ninguna Observacion");
		}
		return ta_observaciones;
	}


	/**
	 * This method initializes b_procesar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getB_procesar() {
		if (b_procesar == null) {
			b_procesar = new JButton("Procesar");
			b_procesar.setBounds(new Rectangle(177, 154, 160, 30));
			b_procesar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			b_procesar.setEnabled(false);
			b_procesar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
							gestor.procesarDatosActividad();
							
				}
			});
		}
		return b_procesar;
	}


	/**
	 * This method initializes c_seccion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	public JComboBox getC_seccion() {
		if (c_seccion == null) {
			c_seccion = new JComboBox(secciones);
			c_seccion.setBounds(new Rectangle(76, 30, 96, 26));
		}
		return c_seccion;
	}


	/**
	 * This method initializes p_horario	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	public JPanel getP_horario() {
		if (p_horario == null) {
			p_horario = new JPanel();
			p_horario.setLayout(null);
			p_horario.setBounds(new Rectangle(8, 212, 523, 237));
			p_horario.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)), "Horario del Dia", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)));
			p_horario.setEnabled(false);
			p_horario.add(getScrollPane(), null);
		}
		return p_horario;
	}


	/**
	 * This method initializes ScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollPane() {
		if (ScrollPane == null) {
			ScrollPane = new JScrollPane();
			ScrollPane.setBounds(new Rectangle(8, 24, 506, 204));
			ScrollPane.setViewportView(getT_inasistencias());
		}
		return ScrollPane;
	}


	/**
	 * This method initializes t_inasistencias	
	 * 	
	 * @return javax.swing.JTable	
	 */
	public JTable getT_inasistencias() {
		if (t_inasistencias == null) {
			t_inasistencias = new JTable(new Object[][]{},new Object[]{"N°","Bloque","Materia","Inasistentes"});
			t_inasistencias.setEnabled(true);
			t_inasistencias.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {				
					//-----------------------------------------------------------------						
				if(getT_inasistencias()!= null){	
					String ban = "";
					
					try {
						ban = t_inasistencias.getModel().getValueAt(t_inasistencias.getSelectedRow(), 3).toString();
					} catch (Exception e2) {
						ban = "";
					// TODO: handle exception
					}	
					 
					
						/*if(ban.isEmpty()){
							ban = "";
						}*/
					
					VentanaListaEstudiantes estudiantes = new VentanaListaEstudiantes(autenticacion,
							getVentana().getDesktopPane(),gestor.getId_seccion(),ban);
					gestor.setRowSelect(t_inasistencias.getSelectedRow());
					estudiantes.setGestorInasistencias(gestor);
					
				}
					//-----------------------------------------------------------------

					
				}
			});
			t_inasistencias.getTableHeader().setReorderingAllowed(false);
		}
		return t_inasistencias;
	}	
	
	public void actualizarAnchoColumnas(){
		try{
			//getColumn(getColumnName(0)).setMaxWidth(30);
			//getColumn(getColumnName(0)).setMinWidth(30);			
			
			JViewport scroll =  (JViewport)t_inasistencias.getParent();
			int ancho = scroll.getWidth();
			int anchoColumna;
			TableColumnModel modeloColumna = t_inasistencias.getColumnModel();
			TableColumn columnaTabla;
			for (int i = 0; i < t_inasistencias.getColumnCount(); i++) {
			    columnaTabla = modeloColumna.getColumn(i);
			    anchoColumna = (anchoColumnas[i]*ancho)/100;
			    columnaTabla.setPreferredWidth(anchoColumna);           
			}		
			
			t_inasistencias.getColumnModel().getColumn(0).setResizable(false);			
			t_inasistencias.getColumnModel().getColumn(1).setResizable(false);
			t_inasistencias.getColumnModel().getColumn(2).setResizable(false);
			t_inasistencias.getColumnModel().getColumn(3).setResizable(false);
			t_inasistencias.setSelectionMode(0);
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.err.println("Error en funcion actualizarAnchoColumnas()");
		}
	}


	/**
	 * This method initializes b_guardar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	public JButton getB_guardar() {
		if (b_guardar == null) {
			b_guardar = new JButton("Guardar");
			b_guardar.setBounds(new Rectangle(194, 459, 160, 30));
			b_guardar.setEnabled(false);
			b_guardar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			b_guardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestor.GuardarInasistencias();
				}
			});
		}
		return b_guardar;
	}
}
