package Utilitario;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ConexionBD.Conexion;
import Consultas.BloquesDAO;

public class ModeloTablaHorario extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnaNombres[] = new String[]{"Bloque","Disciplina","Profesor","Aula"};
	private Object[][] filadatos= null;
	private List<String> bloque = null; 
	private Autenticacion autenticacion = null;
	private int tipoTabla = 1;	
	private int id_seccion = -1;
	public ModeloTablaHorario(List<String> bloque){
		super();
		this.bloque = bloque;
		this.setNumerodeDatos(bloque.size());		
	}
	
	public ModeloTablaHorario(Autenticacion autenticacion,int id_seccion){
		super();
		this.id_seccion = id_seccion;
		this.autenticacion = autenticacion;
		columnaNombres = new String[]{
				"Hora","Lunes","Martes", "Miercoles","Jueves","Viernes","Sabado"			
		};
		tipoTabla = 2;
		cargarBloques();
		setDatosVisualizarHorario(bloque.size());
		cargaMateriasHorario();
	}
	public void cargarBloques(){
		BloquesDAO bloquesDAO = new BloquesDAO(autenticacion);
		bloque = new ArrayList<String>();
			try {
				ResultSet resultSet = bloquesDAO.getBloques();
				while(resultSet.next()){
					bloque.add(resultSet.getString(3));
				}
			} catch (Exception e) {
				// TODO: handle exception
					e.printStackTrace();
			}
		
	}
	
	public void setDatosVisualizarHorario(int numero){
		filadatos = new Object[numero][7];
		for(int i=0;i<numero;i++){
			filadatos[i][0] = this.bloque.get(i);
			filadatos[i][1] = " ";
			filadatos[i][2] = " ";
			filadatos[i][3] = " ";
			filadatos[i][4] = " ";
			filadatos[i][5] = " ";
			filadatos[i][6] = " ";
		}
	}
	
	public void setNumerodeDatos(int numero){
		filadatos = new Object[numero][4];
			for(int i=0;i<numero;i++){
				filadatos[i][0] = this.bloque.get(i);
				filadatos[i][1] = "LIBRE";
				filadatos[i][2] = "SIN PROFESOR";
				filadatos[i][3] = "SIN AULA";
			}
	}
	
	@Override	

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnaNombres.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return filadatos.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return filadatos[arg0][arg1];
	}

    public String getColumnName(int col) {
        return columnaNombres[col];
    }
    
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
    ///----- TABLA DE CARGAR HORARIOS
   if(tipoTabla == 1){ 	
    	if (col > 0) {
            return true;
        } else {
            return false;
        }
   }else{
	   return false;
   } 	
    	
    }
    
    @SuppressWarnings("unchecked")
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
    public void setValueAt(Object value, int row, int col) {
        filadatos[row][col] = value;
        fireTableCellUpdated(row, col);
    }
    
    private void cargaMateriasHorario(){
    	Conexion conexion = new Conexion(autenticacion);
    		if(conexion.conectar()){
    			String query = ""+
    			"SELECT b.numero,CONCAT_WS(' ',m.nombre,IFNULL(CONCAT(CONCAT('(',h.aula),')'),'(S/A)'))"+
    			" AS mater,h.dia_semana"+
    			" FROM horario h RIGHT JOIN (bloque b,materia m)"+
    			" ON (b.id_bloque = h.id_bloque AND m.id_materia = h.id_materia) WHERE"+
    			" h.id_seccion = "+id_seccion+"  ORDER BY h.dia_semana,b.numero"; 
    		ResultSet resultSet = conexion.consultar(query);
    			try {
					while(resultSet.next()){
						///----------------------
						setValueAt(resultSet.getString("mater"),
								resultSet.getInt("numero")-1,
								resultSet.getInt("dia_semana"));
						//-----------------------
					}
				} catch (Exception e) {
					// TODO: handle exception
						e.printStackTrace();
				}
    		}// fin if 
    	conexion.desconectar();
    }
}
