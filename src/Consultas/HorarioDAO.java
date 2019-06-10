package Consultas;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ConexionBD.Conexion;
import Entidades.Horario;
import Utilitario.Autenticacion;
import Utilitario.Item;
import Utilitario.Tablas.ModeloTablaInasistencias;

public class HorarioDAO {
	@SuppressWarnings("unused")
	private Autenticacion autenticacion;
	private Conexion conexion;
	//private boolean conectado = false;

		public HorarioDAO(Autenticacion autenticacion){
			super();
			conexion = new Conexion(autenticacion);
		}
		
	public Horario getHorario(int id_horario){
		Horario horario = new Horario();
		String query = "SELECT * FROM horario WHERE id_horario="+id_horario+" LIMIT 1";
			if(conexion.conectar()){
				try {
					ResultSet resultSet = conexion.consultar(query);
					if(resultSet.next()){
							horario.setId_horario(id_horario);
							horario.setId_materia(resultSet.getInt(2));
							horario.setId_bloque(resultSet.getInt(3));
							horario.setId_seccion(resultSet.getInt(4));
							horario.setId_persona(resultSet.getInt(5));
							horario.setDia_semana(resultSet.getInt(6));
							horario.setAula(resultSet.getString(7));
					}else{
						horario = null;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
				return horario;
}/// fin get_horario	

/**
 * @param id_seccion Numero de ID de la seccion a buscar el horario
 * @param dia_semana Dia de la semana a buscar (1 - Lunes,.......,6-Sabado)
 */
	public List<ItemHorario> getHorarioSeccionporDia(int id_seccion, int dia_semana){
		List<ItemHorario> itemHorario = new ArrayList<ItemHorario>();
		
String query = "SELECT h.id_horario,b.descripcion,m.nombre FROM horario h," +
		" materia m,bloque b WHERE m.id_materia =h.id_materia"+
		" AND b.id_bloque=h.id_bloque AND h.id_seccion="+id_seccion+" AND h.dia_semana=" +
		dia_semana +
		" ORDER BY b.numero";
			if(conexion.conectar()){
				try {
					ResultSet resultSet = conexion.consultar(query);
					while(resultSet.next()){
						ItemHorario horario = new ItemHorario();
						horario.setId_horario(resultSet.getInt(1));
						horario.setNombre_bloque(resultSet.getString(2));
						horario.setNombre_materia(resultSet.getString(3));
						horario.setPosicion(itemHorario.size()+1);
						itemHorario.add(horario);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}				
			}
		return itemHorario;
	}
	
	public void getFromHorarioInasistentes(int id_actividad, ModeloTablaInasistencias inasistencias){
		String query = "";
		String stringID = "";
		String stringNumero = "";
		
		for (int i = 0; i < inasistencias.getRowCount(); i++) {
			Item item = new Item();
			
	query = "SELECT e.numero_lista,e.id_persona FROM inasistencias i,estudiante e " +
			" WHERE e.id_persona=i.id_estudiante AND " +
			" i.id_actividadesdiarias = "+ id_actividad+" AND i.id_horario = "
			+inasistencias.getIdHorario(i)+" ORDER BY e.numero_lista";
	
		///---------------------------------------
				if(conexion.conectar()){
					try {
						//--------------------------------------------
						ResultSet resultSet = conexion.consultar(query);
						
						while(resultSet.next()){
							stringID = stringID + resultSet.getString(2)+"/";
							stringNumero = stringNumero + resultSet.getString(1)+"/";
						}//fin while
						
							if(!stringID.isEmpty()){
								stringID = stringID.substring(0, stringID.length()-1);
								stringNumero = stringNumero.substring(0, stringNumero.length()-1);
							}else{
								stringID = "";
								stringNumero = "";
							}
						
					item.setNombre(stringNumero);
					item.setValorString(stringID);
					inasistencias.setValueAt(item, i, 3);	
					stringID = "";
					stringNumero = "";
						//--------------------------------------------
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
		///---------------------------------------
		}// fin for
		conexion.desconectar();
	}
}
