package Utilitario.Tablas;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import Consultas.ItemHorario;
import Utilitario.Item;

public class ModeloTablaInasistencias extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<ItemHorario> horario = null;
	
	public ModeloTablaInasistencias(List<ItemHorario> horario){			
			
		super(new Object[][]{},new String[]{"N°","Bloque","Materia","Inasistentes"});
		
		this.horario = horario;		
		
		for (int i = 0; i <= horario.size()-1; i++) {
			ItemHorario auxH = (ItemHorario) horario.get(i);
		//System.out.println(auxH.getPosicion()+"-"+auxH.getNombre_bloque()+"-"+auxH.getNombre_materia());		
		insertRow(i,new Object[]{auxH,auxH.getNombre_bloque(),
				auxH.getNombre_materia(),new Item()});
		
		
		}
		
	}		

	@Override
	public Object getValueAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if(column == 0){
			ItemHorario itm = (ItemHorario)value;
			return itm.getPosicion();
		}else if(column == 3){
			Item item = (Item) value;
			return item.getNombre();
		}else{
			return value;
		}
	}

	@Override
	public void addRow(Object[] rowData) {
		// TODO Auto-generated method stub
		super.addRow(rowData);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		if(column != 3){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public void setValueAt(Object obj, int row, int column) {
		// TODO Auto-generated method stub
		super.setValueAt(obj, row, column);
	}
	
	public String[] getItem(int row){
		Item itm = (Item)super.getValueAt(row, 3);
		String[] datos = null;
	try {
		//System.out.println(itm.getValorString());
		 datos = itm.getValorString().split("/");
	} catch (Exception e) {
		// TODO: handle exception
	}	
			
		
		
		return datos;
	}
	
	public int getIdHorario(int row){
		ItemHorario itm = (ItemHorario)super.getValueAt(row, 0);		
		return itm.getId_horario();
	}
	

}
