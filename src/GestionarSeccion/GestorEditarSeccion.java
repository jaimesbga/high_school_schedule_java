package GestionarSeccion;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableModel;

import ConexionBD.Conexion;
import Utilitario.Autenticacion;
import Utilitario.ModeloTablaHorario;

public class GestorEditarSeccion {
	private Autenticacion autenticacion = null;
	private List<String> nombreProfesores = null;
	private List<String>  idProfesores = null;
	private List<String>  nombreMateria = null;
	private List<String>  idMateria = null;
	private List<String>  descripBloque = null;
	private List<String>  idBloque = null;
	private String query = "";
	private Conexion conexion = null;
	
	public GestorEditarSeccion(Autenticacion a){
		this.autenticacion = a;
		conexion = new Conexion(autenticacion);
		this.inicializar();
	}
	
	public void inicializar(){
	ResultSet resultSet = null;			
		//inicializaciones de las lista
			this.nombreProfesores =  new ArrayList<String>();
			this.idProfesores = new ArrayList<String>();
			nombreProfesores.add("SIN PROFESOR");
			idProfesores.add("0");	
			this.nombreMateria = new ArrayList<String>();
			this.idMateria = new ArrayList<String>();
			nombreMateria.add("LIBRE");
			idMateria.add("0");
			this.descripBloque = new ArrayList<String>();
			this.idBloque = new ArrayList<String>();			
		//fin
		if(conexion.conectar()){			
			query = "SELECT pr.id_persona,pe.nombre from profesor pr, persona pe" +
					" WHERE pr.id_persona = pe.id_persona " +
					"ORDER BY pe.nombre";
			resultSet = conexion.consultar(query);
		///-----------------------CARGA DE DATOS DE PROFESORES----------------------------
			try{
				while(resultSet.next()){
					nombreProfesores.add(resultSet.getString(2).toUpperCase());
					idProfesores.add(resultSet.getString(1));
				}//fin while 				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		///-------------------------------------------------------------
			query = "SELECT * FROM materia";
			resultSet = conexion.consultar(query);
		///-----------------------CARGA DE DATOS DE Materias----------------------------
			try{
				while(resultSet.next()){
					nombreMateria.add(resultSet.getString(2).toUpperCase());
					idMateria.add(resultSet.getString(1));
				}//fin while 				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		///-------------------------------------------------------------	
			query = "SELECT a.id_bloque,a.descripcion FROM bloque a WHERE a.estado = 1 " +
					" ORDER BY a.numero";
			resultSet = conexion.consultar(query);
			///-----------------------CARGA DE DATOS DE Bloques----------------------------
			try{
				while(resultSet.next()){
					descripBloque.add(resultSet.getString(2).toUpperCase());
					idBloque.add(resultSet.getString(1));
				}//fin while 				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		///-------------------------------------------------------------
			conexion.desconectar();
		}//fin if
	}

	public String nombreAId_Profesor(String foundString){
			
			/*	for (int i = 0; i < idProfesores.size(); i++) {
					System.out.println(nombreProfesores.get(i).toString());
				}*/
				
			return idProfesores.get(nombreProfesores.indexOf(foundString.toUpperCase()));
	}
	
	public String nombreAId_Materia(String foundString){
			return idMateria.get(nombreMateria.indexOf(foundString.toUpperCase()));
	}
	
	public String nombreAId_Bloque(String foundString){
			return idBloque.get(descripBloque.indexOf(foundString.toUpperCase()));
	}
	
	public boolean procesarDatos(TableModel modelo, int dia,int idSeccion){
		int maxIdHorario = 0;
		String idMateria = null
		, idProfesor = null
		, idBloque = null
		, aula = null;
		
			maxIdHorario = this.getMaxIdHorario();
	if(conexion.conectar()){
		for(int i = 0; i< modelo.getRowCount();i++){
			idBloque = this.nombreAId_Bloque(modelo.getValueAt(i, 0).toString());
			idMateria = this.nombreAId_Materia(modelo.getValueAt(i, 1).toString());
			idProfesor = this.nombreAId_Profesor(modelo.getValueAt(i, 2).toString());
			aula = modelo.getValueAt(i, 3).toString();
			///-------Insertando Materias----------
				if(idMateria.compareTo("0")!=0){
					if(aula.compareTo("SIN AULA") == 0){
						aula = "null";
					}else{
						aula = "'"+ aula + "'";
					}
					if(idProfesor.compareTo("0")==0){
						idProfesor = "null";
					}
					query = "INSERT INTO horario VALUES(" + maxIdHorario + 
							","+idMateria+","+idBloque+","+idSeccion+","+idProfesor+
							","+dia+","+aula+")";
					System.out.println(query);
					conexion.actualizar(query);
						maxIdHorario++;
				}//fin if 
			///-----------------------------------------
		}// fin for 
		conexion.desconectar();
		return true;
	}else{
		return false;
	}
			
	}
	
	public int getMaxIdHorario(){
		int value = -1;
		if(conexion.conectar()){
			query = "SELECT IFNULL(MAX(horario.id_horario)+1,1) as ID FROM horario WHERE 1";
		ResultSet resultSet = conexion.consultar(query);
				try {
					if(resultSet.next()){
							value = Integer.parseInt(resultSet.getString("ID"));							
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}
		conexion.desconectar();
		return value;
	}
	
	public Autenticacion getAutenticacion() {
		return autenticacion;
	}

	public List<String> getNombreProfesores() {
		return nombreProfesores;
	}

	public List<String> getIdProfesores() {
		return idProfesores;
	}

	public List<String> getNombreMateria() {
		return nombreMateria;
	}

	public List<String> getIdMateria() {
		return idMateria;
	}

	public List<String> getDescripBloque() {
		return descripBloque;
	}

	public List<String> getIdBloque() {
		return idBloque;
	}

	public String getQuery() {
		return query;
	}	
	
	public ModeloTablaHorario cargarHorario(int seccion, int dia){
		ModeloTablaHorario model = new ModeloTablaHorario(descripBloque);
		/*query = "SELECT (b.numero-1) AS num_bloque, m.nombre AS nombre_materia, pe.nombre AS nombre_profe, "+
				"IF(h.aula = 'null','SIN AULA',h.aula) AS aula "+
				"FROM horario h,materia m, bloque b, persona pe, profesor pr WHERE "+
				"m.id_materia = h.id_materia AND "+
				"b.id_bloque = h.id_bloque AND pr.id_persona = h.id_persona AND pe.id_persona = pr.id_persona "+ 
				"AND b.estado =1 AND h.id_seccion = "+ seccion +" AND h.dia_semana ="+ dia +
				" ORDER BY b.numero";*/
		
		query = "SELECT (b.numero-1) AS num_bloque, UCASE(m.nombre) AS nombre_materia," +
		" UCASE(IFNULL((SELECT pe.nombre FROM persona pe WHERE pe.id_persona = h.id_persona),'SIN PROFESOR'))  AS nombre_profe," +
		" IFNULL(h.aula,'SIN AULA') AS aula FROM horario h"+
		" RIGHT JOIN (bloque b,materia m) ON ( b.id_bloque = h.id_bloque"+
		" AND m.id_materia = h.id_materia) WHERE h.id_seccion = "+ seccion +" AND h.dia_semana ="+ dia + 
		" ORDER BY num_bloque";
		
			if(conexion.conectar()){
					ResultSet resultSet = conexion.consultar(query);
					
					try {
			//---------------------------------------------------------
						while(resultSet.next()){
							int num_bloque = resultSet.getInt("num_bloque");
								model.setValueAt(resultSet.getString("nombre_materia"),num_bloque ,1);
								model.setValueAt(resultSet.getString("nombre_profe"),num_bloque ,2);
								model.setValueAt(resultSet.getString("aula"),num_bloque ,3);
						}//fin while  
			//---------------------------------------------------------
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}//fin if 
			
			}//fin if 
				return model;
	}
	
	public boolean actualizarDatos(TableModel modelo, int dia,int idSeccion){
		int maxIdHorario = 0;
		
		String idMateria = null
		, idProfesor = null
		, idBloque = null
		, aula = null;
		
		
			maxIdHorario = this.getMaxIdHorario();
			if(conexion.conectar()){
	//------------------------------------------------------------
			for(int i = 0; i < modelo.getRowCount(); i++){
				idBloque = this.nombreAId_Bloque(modelo.getValueAt(i, 0).toString());
				idMateria = this.nombreAId_Materia(modelo.getValueAt(i, 1).toString());
				idProfesor = this.nombreAId_Profesor(modelo.getValueAt(i, 2).toString());
				aula = modelo.getValueAt(i, 3).toString();
			///------ Seleccion de Actualizacion o de insercion 
				
				boolean flagIn = false;
				
					if(existeCombinacion(conexion, idSeccion, idBloque, dia)){
							if(idMateria.compareTo("0")==0){
								idMateria = "null";
								idProfesor = "null";
							}
							if(idProfesor.compareTo("0")==0){
								idProfesor =  "null";
							}
							if(aula.compareTo("SIN AULA") == 0){
								aula = "null";
							}else{
								aula = "'"+ aula + "'";
							}
								flagIn = true;
	query = "UPDATE horario SET horario.id_materia = "+idMateria+" , horario.id_persona="+idProfesor+
			", aula ="+ aula +
			" WHERE horario.id_bloque = "+idBloque+" AND horario.id_seccion =" +idSeccion +
					" AND horario.dia_semana = " + dia;
	
					}else{
						if(idMateria.compareTo("0")!=0){
								if(idProfesor.compareTo("0")==0){
									idProfesor = "null";
								}
								if(aula.compareTo("SIN AULA") == 0){
									aula = "null";
								}else{
									aula = "'"+ aula + "'";
								}
								flagIn = true;
							query = "INSERT INTO horario VALUES(" + maxIdHorario + 
							","+idMateria+","+idBloque+","+idSeccion+","+idProfesor+
							","+dia+","+aula+")";
							maxIdHorario++;
						}
					}
					
					//---------------------------------
						if(flagIn){
						conexion.actualizar(query);
						}
				///------ Seleccion de Actualizacion o de insercion
			}
	//------------------------------------------------------------
			
			return true;
		}else{
			return false;
		}// if if - else 	
	}
	
	public boolean  existeCombinacion(Conexion conexion,int idSeccion
			, String idBloque, int dia) {				
		query = "SELECT * FROM horario WHERE id_bloque = "+idBloque+
			    " AND id_seccion = "+ idSeccion + " AND dia_semana = "+dia+
			    " LIMIT 1";
		ResultSet	resultSet = conexion.consultar(query);
			try {
				if(resultSet.next()){
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				return true;
	}
}
