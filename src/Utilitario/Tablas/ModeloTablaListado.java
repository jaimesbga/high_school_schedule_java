package Utilitario.Tablas;

import javax.swing.table.DefaultTableModel;

import Utilitario.Item;

public class ModeloTablaListado extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	public ModeloTablaListado(Object[][] data, Object[] ColumnNames){
	      super(data, ColumnNames);   	      
	}
	
	public boolean isCellEditable (int row, int column){
		//return column == 0;
		return false;
	}
	
	public Class<?> getColumnClass(int column) {		
		return column == 0 ? Boolean.class : String.class;
    }
	
	public Object getValueAt(int row, int column){
		Object value = super.getValueAt(row, column);
		if(column == 0){
			Item itm = (Item)value;
			return itm.getValorBoolean();
		}
		else{
			return value;
		}
	}
	
	public Item getItem(int row){
		Item itm = (Item)super.getValueAt(row, 0);		
		return itm;
	}
}
