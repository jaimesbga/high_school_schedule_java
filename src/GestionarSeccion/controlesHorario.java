package GestionarSeccion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import Utilitario.ModeloTablaHorario;

public class controlesHorario extends JScrollPane {
	private int numero_controles = 0;
	private JLabel[] bloques = null;
	private JComboBox[] materias = null;
	private JComboBox[] profesores = null;
	private JTextField[] aulas = null;
	private String[] s_profesores = {"SIN PROFESOR"};
	private String[] s_materias = {"LIBRE","MATEMATICA","FISICA","QUIMICA","INFORMATICA","TURISMO","EDU.FISICA","CIENCIA DE LA TIERRA"};
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;  //  @jve:decl-index=0:visual-constraint="514,109"
	private JXTable table = null;
	private JComboBox c_materias = null;
	private JComboBox c_profesores = null;
	private GestorEditarSeccion gestor = null;
	private int dia = 0;	
	/**
	 * This is the default constructor
	 */
	public controlesHorario(GestorEditarSeccion gestor, int dia) {
		super();		
		this.gestor = gestor;
		this.dia = dia;		
		numero_controles = gestor.getIdBloque().size();
		this.setDoubleBuffered(true);
		initialize();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {	
		this.setSize(480, 239);		
		this.setViewportView(this.getJPanel());	
		this.table.setEnabled(false);
		this.setEnabled(false);		
	}
	
	public void activarControles(boolean flag){
		this.table.setEnabled(flag);		
		this.setEnabled(flag);
	}
	
	
	@SuppressWarnings("unused")
	private void inicializarControlesDinamicos(){
	int posy = 30;	
		bloques = new JLabel[numero_controles]; 
		materias = new JComboBox[numero_controles];
		profesores = new JComboBox[numero_controles];
		aulas = new JTextField[numero_controles];
			
		for(int i = 0; i< numero_controles;i++){
			this.bloques[i] = new JLabel("07:00 - 08:00");
			this.bloques[i].setLocation(10, posy);
			this.bloques[i].setSize(80, 20);	
			this.materias[i] = new JComboBox(this.s_materias);
			this.materias[i].setLocation(100, posy);
			this.materias[i].setSize(150, 20);
			this.profesores[i] = new JComboBox(s_profesores);
			this.profesores[i].setLocation(250, posy);
			this.profesores[i].setSize(150, 20);
			this.aulas[i] = new JTextField("S/A");
			this.aulas[i].setLocation(400, posy);
			this.aulas[i].setSize(50, 20);
			this.aulas[i].setFont(new Font("Dialog", Font.BOLD, 10));
			posy = posy + 20;			
			jPanel.add(this.bloques[i],null);
			jPanel.add(this.profesores[i],null);
			jPanel.add(this.materias[i],null);
			jPanel.add(this.aulas[i],null);		
		}			
		
		jPanel.setPreferredSize(new Dimension(250,posy + 20));
	}//fin inicializar controles

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(0, 0, 460, 20*numero_controles);
			jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("sansserif", Font.BOLD, 12), new Color(59, 59, 59)), " Datos por Bloque ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.ABOVE_TOP, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			jPanel.add(getJxtable(), null);
			jPanel.setPreferredSize(new Dimension(460,18*(numero_controles) + 50));
		}
		return jPanel;
	}
	

	private JXTable getJxtable(){
		if(table == null){
			c_materias = new JComboBox(gestor.getNombreMateria().toArray());
			c_materias.setFont(new Font("Dialog", Font.PLAIN, 10));
			c_profesores = new JComboBox(gestor.getNombreProfesores().toArray());
			c_profesores.setFont(new Font("Dialog", Font.PLAIN, 10));
				table = new JXTable(new ModeloTablaHorario(gestor.getDescripBloque()));
				table.setAutoCreateColumnsFromModel(true);
				table.getColumn(1).setCellEditor(new DefaultCellEditor(c_materias));
				table.getColumn(2).setCellEditor(new DefaultCellEditor(c_profesores));
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				table.setVisible(true);
				table.setBackground(Color.white);
				table.setBounds(new Rectangle(14, 32, 440, 18*(numero_controles)+5));				
				table.setColumnControlVisible(true);
				table.setShowGrid(true);
				table.setVisibleColumnCount(4);
				table.setRolloverEnabled(true);
				table.setHorizontalScrollEnabled(true);
				table.setGridColor(Color.darkGray);
				table.setShowGrid(true, true);
				table.setShowHorizontalLines(true);	
				//table.setHighlighters(HighlighterFactory.createAlternateStriping(Color.YELLOW ,Color.WHITE));
				table.setHighlighters(HighlighterFactory.createAlternateStriping(new Color(255,249,128) ,Color.WHITE));			
				table.packAll();
				
		}
			return table;
	}
	
	public void guardarDatosdeHorario(int idSeccion,int tipo){
		if(tipo == 1){
			gestor.procesarDatos(table.getModel(), dia, idSeccion);			
		}else if(tipo == 2){
			gestor.actualizarDatos(table.getModel(), dia, idSeccion);
		}
	}
	public void cargarDatos(int idSeccion){
			this.table.setModel(gestor.cargarHorario(idSeccion, dia));
			table.getColumn(1).setCellEditor(new DefaultCellEditor(c_materias));
			table.getColumn(2).setCellEditor(new DefaultCellEditor(c_profesores));
	}
}  //  @jve:decl-index=0:visual-constraint="18,14"
