package Utilitario.Tablas;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Utilitario.Item;

public class JTableListado extends JTable{	
	private static final long serialVersionUID = 1L;
	private ModeloTablaListado modeloTabla = null;
	private String[] nombreColumnas;
	private int[] anchoColumnas = null;
	private int filaSeleccionada = -1;
	private boolean selecionMultiple = false;
	private boolean controlColumnas = false;
	private JScrollPane scrollPanel = null;
	private boolean ordenamiento;
	
	private JButton btnControlColumnas = null;
	private JPopupMenu popupMenu = null;
	
	/**
	 * Constructor para inicializar el componente
	 * @param columnas Nombre de las columnas del encabezado de las tablas
	 * @param anchoColumnas Porcentaje del ancho de cada columna con respecto al total de la tabla
	 * @param seleccionMultiple Bandera que indica si se puede realizar seleccion múltiple	 
	 * @param ordenamiento Bandera que indica que si se agregara la ordenación de la tabla
	 * @param controlColumnas Bandera que indica si se agregar el control para ocultar/mostrar columnas
	 * @param scrollPanel ScrollPane donde se encuentra la tabla
	 */
	public JTableListado(String[] columnas, int[] anchoColumnas,
						boolean seleccionMultiple, boolean ordenamiento, boolean controlColumnas,
						JScrollPane scrollPanel){
		super();		
		
		this.scrollPanel = scrollPanel;
		this.nombreColumnas = columnas;
		this.anchoColumnas = anchoColumnas;		
		this.selecionMultiple = seleccionMultiple;
		this.ordenamiento = ordenamiento;
		this.controlColumnas = controlColumnas;
		
		inicializar();		
	}
	
	public int getFilaSeleccionada(){
		if(filaSeleccionada>=0 && filaSeleccionada<getModel().getRowCount()){
			return (Boolean)getValueAt(filaSeleccionada, 0) ? filaSeleccionada : -1; 
		}
		return -1;
	}
	
	private void inicializar(){
		this.getTableHeader().setReorderingAllowed(false);
		getModel();
		agregarEventos();
		
		if(ordenamiento){
			setOrdenamiento();
		}
		if(controlColumnas){
			setControlColumnas();
		}
		
		actualizarAnchoColumnas();		
	}
	
	private void setOrdenamiento(){
		TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(modeloTabla);
		setRowSorter(trs);		
	}
	
	public ModeloTablaListado getModel(){
		if(modeloTabla == null){
			modeloTabla = new ModeloTablaListado(null,
					nombreColumnas);
			this.setModel(modeloTabla);
		}
		
		return modeloTabla;
	}
	
	private void agregarEventos(){
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
					int index = getSelectedRow();
					if(index >= 0){
						Item itm = (Item)modeloTabla.getItem(index);
						itm.setValorBoolean(!itm.getValorBoolean());
						setValueAt(itm, index, 0);						
						if(!selecionMultiple){
							try{
								if(filaSeleccionada >=0 && index != filaSeleccionada){
									itm = getModel().getItem(filaSeleccionada);
									itm.setValorBoolean(false);
									setValueAt(itm, filaSeleccionada, 0);
								}
							}
							catch(Exception ex){
							}
						}
						
						filaSeleccionada = index;
					}
				}
			}
		});
	}
	
	public void actualizarAnchoColumnas(){
		try{
			getColumn(getColumnName(0)).setMaxWidth(30);
			getColumn(getColumnName(0)).setMinWidth(30);			
			
			JViewport scroll =  (JViewport) getParent();
			int ancho = scroll.getWidth();
			int anchoColumna;
			TableColumnModel modeloColumna = getColumnModel();
			TableColumn columnaTabla;
			for (int i = 1; i < getColumnCount(); i++) {
			    columnaTabla = modeloColumna.getColumn(i);
			    anchoColumna = (anchoColumnas[i-1]*ancho)/100;
			    columnaTabla.setPreferredWidth(anchoColumna);           
			}		
		}
		catch(Exception e){
		}
	}
	
	public void setControlColumnas(){		
		if(scrollPanel != null){
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPanel.setCorner(JScrollPane.UPPER_RIGHT_CORNER, getBtnControlColumnas());
		}		
	}
	
	private void mostrarOcultarColumna(JCheckBoxMenuItem item){
		if(item!=null){
			int index = -1;
			for(int i=1; i<nombreColumnas.length;i++){
				if(nombreColumnas[i].equals(item.getText())){
					index = i;
					break;
				}
			}
			if(index>=0){
				if(item.isSelected()){
					JViewport scroll =  (JViewport) getParent();
				    int ancho = scroll.getWidth();				    
					TableColumn columnaTabla = getColumnModel().getColumn(index);
					
					columnaTabla.setMaxWidth(99999);
					columnaTabla.setMinWidth(1);
			        columnaTabla.setPreferredWidth((anchoColumnas[index-1]*ancho)/100);
				}
				else{
					int vis = 0;
					for(int i=1; i<getColumnCount(); i++){
						if(getColumnModel().getColumn(i).getPreferredWidth()>0){
							vis++;
						}
					}
					if(vis > 1){
						getColumn(item.getText()).setMaxWidth(0);
						getColumn(item.getText()).setMinWidth(0);
						getColumn(item.getText()).setPreferredWidth(0);
					}else{
						item.setSelected(true);
					}
				}
			}
			
		}
	}
	
	private JButton getBtnControlColumnas(){
		if(btnControlColumnas == null){
			btnControlColumnas = new JButton(new ControlTablasIcono());
			btnControlColumnas.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)){
						getPopupMenu();
						if(!popupMenu.isVisible()){											
							popupMenu.setLocation(e.getXOnScreen(), e.getYOnScreen());
							popupMenu.setInvoker(popupMenu);
							popupMenu.setVisible(true);
						}else{
							popupMenu.setVisible(false);
						}
					}
				}
			});
		}
		
		return btnControlColumnas;
	}
	
	private JPopupMenu getPopupMenu(){
		if(popupMenu == null){
			popupMenu = new JPopupMenu();
			for(int i=1; i<nombreColumnas.length; i++){
				JCheckBoxMenuItem item = new JCheckBoxMenuItem();
				item.setSelected(true);
				item.setText(nombreColumnas[i]);
				item.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                mostrarOcultarColumna((JCheckBoxMenuItem)evt.getSource());
		            }
		        });
				popupMenu.add(item);
			}
		}
		
		return popupMenu;		
	}        
}
