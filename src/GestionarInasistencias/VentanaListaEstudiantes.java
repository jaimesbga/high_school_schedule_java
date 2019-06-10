package GestionarInasistencias;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import Consultas.EstudiantesDAO;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.MostrarMensajes;
import Utilitario.Tablas.JTableListado;
import Utilitario.Tablas.ModeloTablaListado;

public class VentanaListaEstudiantes {
	private JInternalFrame ventana = null;  //  @jve:decl-index=0:visual-constraint="10,54"
	private Autenticacion autenticacion = null;
	private JDesktopPane desktopPane = null;
	private int id_seccion = -1;
	private JScrollPane jScrollPane = null;
	private JTableListado tablaListado = null;
	private JPanel p_listado = null;
	private JButton b_procesar = null;
	private GestorInasistencias gestorInasistencias = null;
	
	public VentanaListaEstudiantes(Autenticacion autenticacion, JDesktopPane desktopPane,
			int id_seccion,String stringNumero){
		this.id_seccion  = id_seccion;
		this.autenticacion = autenticacion;
		this.desktopPane = desktopPane;		
		getVentana();
		int x = Math.max(0, (desktopPane.getWidth() - ventana.getWidth()-15) / 2);
        int y = Math.max(0, (desktopPane.getHeight() - ventana.getHeight() -70) / 2);
        ventana.setLocation(new Point(x, y)); 
        
	
		this.prepararListar(stringNumero.split("/"));
	}
	
	public JInternalFrame getVentana(){
		if(ventana == null){
			ventana = new JInternalFrame("Lista de Estudiantes");	
			ventana.setVisible(true);
			ventana.setSize(new Dimension(413, 482));			
			ventana.setIconifiable(true);
			ventana.setResizable(false);
			ventana.setClosable(true);
			ventana.setMaximizable(false);			
			ventana.setContentPane(getP_listado());
			ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);			
			ventana.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {   
				public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {    
					boolean tip = MostrarMensajes.mostrarMensaje(getVentana(),
							"Esta seguro de cerra esta ventana? " +
							"\nAl cerrarla no se agregaran los inasistentes al registro.",
							MostrarMensajes.MENSAJE_PREGUNTA);
					
						if(tip){
							getVentana().dispose();
						}
				}
		
			});
			
			desktopPane.add(ventana);
			ventana.toFront();
			
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
			jScrollPane = new JScrollPane();
			jScrollPane.setSize(new Dimension(401, 411));
			jScrollPane.setViewportView(getTablaListado());
		}
		return jScrollPane;
	}
	
	public JTableListado getTablaListado() {
		
		
		if (tablaListado == null) {
			tablaListado = new JTableListado(
					new String[]{"","N°", "Nombre", "Sección"},
					new int[]{10,60,30},
					true,
					true,
					true,
					jScrollPane);
		}		
		
		return tablaListado;
	}
	
	public void prepararListar(String[] datos){
		getVentana();
		try{
			EstudiantesDAO dao = new EstudiantesDAO(autenticacion);
			ResultSet res = dao.getListadoEstudiantes(id_seccion);
			ModeloTablaListado modelo = this.getTablaListado().getModel();
			modelo.setRowCount(0);
			
			int cont = 0;
			while(res.next()){
				modelo.setRowCount(cont+1);
				Item itm = new Item();			
				
				itm.setValorBoolean(false);
				
					if(datos.length>0){
						for (int i = 0; i < datos.length; i++) {
							if(datos[i].compareToIgnoreCase(res.getString("numero_lista"))==0){
								itm.setValorBoolean(true);
								break;
							}
						}
					}//fin if
					
				itm.setValorInt(res.getInt("id_persona"));				
				modelo.setValueAt(itm, cont, 0);
				modelo.setValueAt(res.getString("numero_lista"), cont, 1);
				modelo.setValueAt(res.getString("nombre"), cont, 2);
				modelo.setValueAt(res.getString("seccion"), cont, 3);
				cont++;
			}
			
			res.close();
			dao.cerrarConexion();
			tablaListado.actualizarAnchoColumnas();
		}
		catch(Exception e){			
		}		
	}

	/**
	 * This method initializes p_listado	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_listado() {
		if (p_listado == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			p_listado = new JPanel();
			p_listado.setLayout(null);
			p_listado.add(getJScrollPane(), gridBagConstraints);
			p_listado.add(getB_procesar(), null);
		}
		return p_listado;
	}

	/**
	 * This method initializes b_procesar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_procesar() {
		if (b_procesar == null) {
			b_procesar = new JButton("Procesar");
			b_procesar.setBounds(new Rectangle(106, 413, 184, 31));
			b_procesar.setMnemonic(KeyEvent.VK_M);
			b_procesar.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Imagen011.png")));
			b_procesar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					gestorInasistencias.ProcesarInasistentes(getTablaListado().getModel());
					getVentana().dispose();
				}
			});
		}
		return b_procesar;
	}
	
	public void setGestorInasistencias(GestorInasistencias gestorInasistencias){
		this.gestorInasistencias = gestorInasistencias;		
	}
	
}
