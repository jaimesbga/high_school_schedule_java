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
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import Consultas.SeccionDAO;
import Utilitario.Autenticacion;
import Utilitario.ModeloTablaHorario;

public class VentanaHorario {
	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="62,8"
	private JScrollPane jScrollPane = null;
	private JPanel p_ventana = null;
	private JPanel p_opciones = null;
	private JComboBox c_seccion = null;
	private JLabel l_seccion = null;
	private JLabel l_periodo = null;
	private JTextField t_periodo = null;
	private JButton b_buscar = null;
	private JScrollPane sp_tabla = null;
	private JTable ta_horario = null;
	private String[] secciones = new String[]{
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R",
			"S","T","U","V","W","X","Y","Z"
			};
	
	@SuppressWarnings("unused")
	private int tipoInicio = 1;
	private String perido = null;  //  @jve:decl-index=0:
	private String seccion = null;
	private Autenticacion autenticacion = null;
	private JDesktopPane desktopPane = null;
	private int id_seccion = -1;
	
		//tipoInicio  (1 - Ventana Principl)(2 - ventana Secundaria)
	
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public String getPerido() {
		return perido;
	}

	public void setPerido(String perido) {
		this.perido = perido;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
//---------------Constructores----------------------
	public VentanaHorario(String seccion,String perido,Autenticacion autenticacion,JDesktopPane desktopPane,
			int id_seccion){
		this.id_seccion = id_seccion;
		this.desktopPane = desktopPane;
		this.autenticacion = autenticacion;
		getVentana();	
		setSeccion(seccion);
		setPerido(perido);
		setValoresporDefecto();			
		desktopPane.add(ventana);	
		ventana.toFront();
		ventana.show();
		this.tipoInicio = 2;
	}
	
	@SuppressWarnings("deprecation")
	public VentanaHorario(Autenticacion autenticacion,JDesktopPane desktopPane){
		this.desktopPane = desktopPane;
		this.autenticacion = autenticacion;
		java.util.Date date = new java.util.Date();			
		getVentana();		
		t_periodo.setText(String.valueOf(date.getYear()+1900));
		desktopPane.add(ventana);
		ventana.show();
	}
//---------------Constructores----------------------	
	public JInternalFrame getVentana(){
		if(ventana == null){
			ventana = new JInternalFrame();
			ventana.setTitle("Visualizacion de Horario");
			ventana.setLocation(new Point(0,0));
			ventana.setSize(new Dimension(786, 357));
			ventana.setVisible(true);
			ventana.setClosable(true);
			ventana.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			ventana.setLayout(null);
			ventana.setContentPane(getJScrollPane());
			int x = Math.max(0, (desktopPane.getWidth() - ventana.getWidth()-15) / 2);
	        int y = Math.max(0, (desktopPane.getHeight() - ventana.getHeight() -70) / 2);
	        ventana.setLocation(new Point(x, y)); 	      
		}
			return ventana;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			JScrollBar jScrollBar = new JScrollBar();
			jScrollBar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jScrollPane = new JScrollPane();		
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane.setViewportView(getP_ventana());
			jScrollPane.setHorizontalScrollBar(jScrollBar); 
			jScrollPane.getVerticalScrollBar().setUnitIncrement(20);
			
		}
		return jScrollPane;
	}

	/**
	 * This method initializes p_ventana	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_ventana() {
		if (p_ventana == null) {
			p_ventana = new JPanel();
			p_ventana.setLayout(null);
			p_ventana.setLocation(0, 0);
			p_ventana.setSize(new Dimension(jScrollPane.getWidth(),  jScrollPane.getHeight()+200));
			p_ventana.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			p_ventana.add(getP_opciones(), null);
			p_ventana.add(getSp_tabla(), null);	
			p_ventana.setPreferredSize(new Dimension(jScrollPane.getWidth(), jScrollPane.getHeight()+400));
		}
		return p_ventana;
	}

	/**
	 * This method initializes p_opciones	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_opciones() {
		if (p_opciones == null) {
			l_periodo = new JLabel();
			l_periodo.setBounds(new Rectangle(205, 25, 57, 25));
			l_periodo.setText("Periodo:");
			l_seccion = new JLabel();
			l_seccion.setBounds(new Rectangle(41, 25, 53, 25));
			l_seccion.setText("Seccion:");
			p_opciones = new JPanel();
			p_opciones.setLayout(null);
			p_opciones.setBounds(new Rectangle(135, 10, 486, 57));
			p_opciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 3), "Seleccionar Seccion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			p_opciones.add(getC_seccion(), null);
			p_opciones.add(l_seccion, null);
			p_opciones.add(l_periodo, null);
			p_opciones.add(getT_periodo(), null);
			p_opciones.add(getB_buscar(), null);
		}
		return p_opciones;
	}

	/**
	 * This method initializes c_seccion	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getC_seccion() {
		if (c_seccion == null) {
			c_seccion = new JComboBox(secciones);
			c_seccion.setBounds(new Rectangle(102, 25, 96, 25));
		}
		return c_seccion;
	}

	/**
	 * This method initializes t_periodo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	@SuppressWarnings("deprecation")
	private JTextField getT_periodo() {
		if (t_periodo == null) {
			java.util.Date date = new java.util.Date();			
			t_periodo = new JTextField(date.getYear()+1900);
			t_periodo.setBounds(new Rectangle(262, 25, 69, 25));
		}
		return t_periodo;
	}

	/**
	 * This method initializes b_buscar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_buscar() {
		if (b_buscar == null) {
			b_buscar = new JButton();
			b_buscar.setBounds(new Rectangle(337, 23, 134, 27));
			b_buscar.setToolTipText("Permite buscar el horario de la seccion seleccionada.");
			b_buscar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen015.png")));
			b_buscar.setText("Buscar");
			b_buscar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					
	if(!t_periodo.getText().isEmpty()){				
					SeccionDAO seccionDAO = new SeccionDAO(autenticacion);
		ta_horario.setModel(new ModeloTablaHorario(autenticacion,seccionDAO.getIdSeccion(
		c_seccion.getItemAt(c_seccion.getSelectedIndex()).toString()
		, t_periodo.getText())));
	}// fin if 	
		
				}
			});
		}
		return b_buscar;
	}

	/**
	 * This method initializes sp_tabla	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSp_tabla() {
		if (sp_tabla == null) {
			sp_tabla = new JScrollPane();
			sp_tabla.setBounds(new Rectangle(13, 74, 735, 281));
			sp_tabla.setViewportView(getTa_horario());
		}
		return sp_tabla;
	}

	/**
	 * This method initializes ta_horario	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTa_horario() {
		if (ta_horario == null) {
			ModeloTablaHorario horario = new ModeloTablaHorario(autenticacion,id_seccion);
			ta_horario = new JTable(horario);
			ta_horario.setFont(new Font("Dialog", Font.BOLD, 10));
		}
		return ta_horario;
	}
	
	private void setValoresporDefecto(){
		t_periodo.setText(getPerido());
			for(int i = 0; i < getC_seccion().getItemCount();i++){
					if(getC_seccion().getItemAt(i).toString().compareTo(getSeccion())==0){
						getC_seccion().setSelectedIndex(i);
							break;
					}
			}
		c_seccion.setEnabled(false);
		t_periodo.setEnabled(false);
		b_buscar.setEnabled(false);		
	}// fin funcion
}
