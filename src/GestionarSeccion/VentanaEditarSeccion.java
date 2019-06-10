package GestionarSeccion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import Utilitario.Autenticacion;

public class VentanaEditarSeccion {

	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="136,39"
	private JPanel panelVentana = null;
	private JScrollPane scrollVentana = null;
	public JDesktopPane desktopPane;
	public final int ERROR = 1;
	public final int MENSAJE = 2;
	private JPanel p_seccion = null;
	private JLabel l_seccion = null;
	private JLabel l_periodo = null;
	private JComboBox c_seccion = null;
	private JComboBox c_periodo = null;
	private JButton b_crear_seccion = null;
	private JTabbedPane tp_horario = null;
	private JButton b_guardar_horario = null;
	private JButton b_ver_horario = null;
	private String[] secciones = new String[]{
	"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R",
	"S","T","U","V","W","X","Y","Z"
	};
	private Autenticacion autenticacion = null;
	private GestorEditarSeccion gestor = null;
	private GestorSecciones gestorSecciones = null;  //  @jve:decl-index=0:
	private controlesHorario[] paneles = null;
	private int estadoVentana = -1;
	private int idSeccion = -1;
	private int tipoInicio = 1;
		///estadoVentana (-1 = Sin Guardar Seccion)(0 = Seccion creada sin guardar Horario)
		//(1 = Horario Guardado)
		////tipoInicio (1 - Crear Seccion)(2- Actualizar Seccion)
	
	public int getTipoInicio() {
		return tipoInicio;
	}

	public void setTipoInicio(int tipoInicio) {
		this.tipoInicio = tipoInicio;
	}

	public int getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(int idSeccion) {
		this.idSeccion = idSeccion;
	}

	public int getEstadoVentana() {
		return estadoVentana;
	}

	public void setEstadoVentana(int estadoVentana) {
		this.estadoVentana = estadoVentana;
	}

	public void InicializarControles(){
			this.setVisible(false);
			this.ventana.dispose();
			new VentanaEditarSeccion(desktopPane,autenticacion);
	}
	
	public VentanaEditarSeccion(JDesktopPane p, Autenticacion autenticacion){		
		this.autenticacion = autenticacion;
		desktopPane = p;
		gestor = new GestorEditarSeccion(autenticacion);
		gestorSecciones = new GestorSecciones(autenticacion,this);
		desktopPane.add(getVentana());
		int x = Math.max(0, (desktopPane.getWidth() - ventana.getWidth()-15) / 2);
        int y = Math.max(0, (desktopPane.getHeight() - ventana.getHeight() -70) / 2);
        ventana.setLocation(new Point(x, y));  
        
        ventana.setVisible(true);
	}
	
	public void mostrarMensaje(String msj, int tipo){
		if(tipo == ERROR){
			//JOptionPane.showMessageDialog(ventana, msj, "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Image007.png")));
		}
		if(tipo == MENSAJE){
			//JOptionPane.showMessageDialog(ventana, msj, "Correcto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Imagenes/Image006.png")));
		}
	}
	
	public void cerrar(){
		if(estadoVentana != 0){
			ventana.dispose();
		}
	}	

	
	public void setVisible(boolean param){
		ventana.setVisible(param);
	}
	
	/**
	 * This method initializes ventana	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	public JInternalFrame getVentana() {
		if (ventana == null) {
			ventana = new JInternalFrame();
			ventana.setTitle("Gestionar Secciones");
			ventana.setClosable(true);
			ventana.setBounds(new Rectangle(0, 0, 554, 539));
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			ventana.setContentPane(getScrollVentana());
			ventana.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {   
				public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {    
					System.out.println(estadoVentana); // TODO Auto-generated Event stub internalFrameClosing()
					
					if(estadoVentana == 0){
						gestorSecciones.errorCerrarInternalFrame();
					}else if(estadoVentana != 0){
						//-------------------------------						
						if(estadoVentana == 1){	
							if(JOptionPane.showConfirmDialog(ventana, "Desea agregar una nueva seccion?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen009.png")))==JOptionPane.YES_OPTION){
								InicializarControles();
							}else{
								getVentana().dispose();
							}
						}else{
							getVentana().dispose();
						}
						//-------------------------------
					}
				}
			
			});
			this.tp_horario.setEnabled(false);
			b_guardar_horario.setEnabled(false);
			b_ver_horario.setEnabled(false);
			
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
			panelVentana.setPreferredSize(new Dimension(525, 500));
			panelVentana.setSize(new Dimension(525, 400));
			panelVentana.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)), "Registro de Seccion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.PLAIN, 14), Color.blue));
			panelVentana.add(getP_seccion(), null);
			panelVentana.add(getTp_horario(), null);
			panelVentana.add(getB_guardar_horario(), null);
			panelVentana.add(getB_ver_horario(), null);
		}
		return panelVentana;
	}

	/**
	 * This method initializes scrollVentana	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollVentana() {
		if (scrollVentana == null) {
			JScrollBar jScrollBar = new JScrollBar();
			jScrollBar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			scrollVentana = new JScrollPane();
			scrollVentana.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollVentana.setViewportView(getPanelVentana());
			scrollVentana.setHorizontalScrollBar(jScrollBar);                        
			scrollVentana.getVerticalScrollBar().setUnitIncrement(20);                        
		}
		return scrollVentana;
	}

	/**
	 * This method initializes p_seccion	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_seccion() {
		if (p_seccion == null) {
			l_periodo = new JLabel();
			l_periodo.setBounds(new Rectangle(153, 25, 53, 25));
			l_periodo.setText("Periodo:");
			l_seccion = new JLabel();
			l_seccion.setBounds(new Rectangle(20, 25, 53, 25));
			l_seccion.setText("Seccion:");
			p_seccion = new JPanel();
			p_seccion.setLayout(null);
			p_seccion.setBounds(new Rectangle(115, 30, 307, 99));
			p_seccion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 3), "Datos de Seccion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			p_seccion.add(l_seccion, null);
			p_seccion.add(l_periodo, null);
			p_seccion.add(getC_seccion(), null);
			p_seccion.add(getC_periodo(), null);
			p_seccion.add(getB_crear_seccion(), null);
		}
		return p_seccion;
	}

	/**
	 * This method initializes c_seccion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getC_seccion() {
		if (c_seccion == null) {
			c_seccion = new JComboBox(secciones);
			c_seccion.setBounds(new Rectangle(73, 25, 71, 25));
		}
		return c_seccion;
	}

	/**
	 * This method initializes c_periodo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	@SuppressWarnings("deprecation")
	private JComboBox getC_periodo() {
		int year_begin = 0;
		java.util.Date date = new java.util.Date();
		year_begin = date.getYear() + 1900;
		if (c_periodo == null) {
			c_periodo = new JComboBox();
			c_periodo.setBounds(new Rectangle(206, 25, 81, 25));
			for(int i = 0; i<10;i++){
				c_periodo.addItem(year_begin);
				year_begin = year_begin + 1;
			}
		}
		return c_periodo;
	}

	/**
	 * This method initializes b_crear_seccion	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_crear_seccion() {
		if (b_crear_seccion == null) {
			b_crear_seccion = new JButton("Crear Seccion");
			b_crear_seccion.setBounds(new Rectangle(89, 60, 145, 30));
			b_crear_seccion.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			b_crear_seccion.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
							int estado = gestorSecciones.guardarSeccion(c_seccion.getItemAt(c_seccion.getSelectedIndex()).toString(), 
								c_periodo.getItemAt(c_periodo.getSelectedIndex()).toString());
							//-------------------------------------------------
							setTipoInicio(estado);
								if(estado == 2 ){
									paneles[0].cargarDatos(getIdSeccion());	
									paneles[1].cargarDatos(getIdSeccion());	
									paneles[2].cargarDatos(getIdSeccion());	
									paneles[3].cargarDatos(getIdSeccion());	
									paneles[4].cargarDatos(getIdSeccion());	
									paneles[5].cargarDatos(getIdSeccion());	
									b_ver_horario.setEnabled(true);
									setEstadoVentana(1);
								}
							//-------------------------------------------------
					
				}
			});
		}
		return b_crear_seccion;
	}

	/**
	 * This method initializes tp_horario	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTp_horario() {
		paneles = new controlesHorario[6];
		paneles[0] = new controlesHorario(this.gestor,1);
		paneles[1] = new controlesHorario(this.gestor,2);
		paneles[2] = new controlesHorario(this.gestor,3);
		paneles[3] = new controlesHorario(this.gestor,4);
		paneles[4] = new controlesHorario(this.gestor,5);
		paneles[5] = new controlesHorario(this.gestor,6);				
		
		if (tp_horario == null) {
			tp_horario = new JTabbedPane();
			tp_horario.setBounds(new Rectangle(12, 136, 500, 300));
			tp_horario.add("Lunes", paneles[0]);
			tp_horario.add("Martes", paneles[1]);
			tp_horario.add("Miercoles", paneles[2]);
			tp_horario.add("Jueves", paneles[3]);
			tp_horario.add("Viernes", paneles[4]);
			tp_horario.add("Sabado", paneles[5]);			
		}
		return tp_horario;
	}

	/**
	 * This method initializes b_guardar_horario	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_guardar_horario() {
		if (b_guardar_horario == null) {
			b_guardar_horario = new JButton("Guardar");
			b_guardar_horario.setBounds(new Rectangle(247, 450, 130, 30));
			b_guardar_horario.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			b_guardar_horario.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if(getIdSeccion() != -1){
					//--------------------------------------------------------------------	
					if(gestorSecciones.confirmarGuardarHorario()){
						paneles[0].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
						paneles[1].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
						paneles[2].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
						paneles[3].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
						paneles[4].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
						paneles[5].guardarDatosdeHorario(getIdSeccion(),getTipoInicio());
							setEstadoVentana(1);
							b_ver_horario.setEnabled(true);
					}
					//--------------------------------------------------------------------
					}
				}
			
			});
		}
		return b_guardar_horario;
	}

	/**
	 * This method initializes b_ver_horario	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_ver_horario() {
		if (b_ver_horario == null) {
			b_ver_horario = new JButton("Ver Horario");
			b_ver_horario.setBounds(new Rectangle(381, 450, 130, 30));
			b_ver_horario.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen004.png")));
			b_ver_horario.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				//	System.out.println(getIdSeccion()); // TODO Auto-generated Event stub actionPerformed()
					new VentanaHorario(
	c_seccion.getItemAt(c_seccion.getSelectedIndex()).toString(),c_periodo.getItemAt(c_periodo.getSelectedIndex()).toString()						
					,autenticacion, ventana.getDesktopPane(),getIdSeccion());
					
				}
			});
		}
		return b_ver_horario;
	}
	
	public void activarControles(boolean flag){
			this.tp_horario.setEnabled(flag);
			paneles[0].activarControles(flag);
			paneles[1].activarControles(flag);
			paneles[2].activarControles(flag);
			paneles[3].activarControles(flag);
			paneles[4].activarControles(flag);
			paneles[5].activarControles(flag);
			b_crear_seccion.setEnabled(!flag);
			b_guardar_horario.setEnabled(flag);
			b_ver_horario.setEnabled(!flag);
	}
}
