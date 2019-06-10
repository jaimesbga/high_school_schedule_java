package GestionarSeccion;

import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ConexionBD.Conexion;
import Utilitario.Autenticacion;

public class GestorSecciones {		
	private Autenticacion autenticacion = null;
	private String query = null;
	private Conexion conexion = null;
	private VentanaEditarSeccion ventana = null;
	
	public GestorSecciones(Autenticacion autenticacion, VentanaEditarSeccion ventana){		
		this.autenticacion = autenticacion;
		this.ventana = ventana; 
		this.conexion = new Conexion(this.autenticacion);	
	}
	
	public int guardarSeccion(String nombre, String periodo){
		int retorno = -1;
			if(!existeSeccion(nombre, periodo)){
				if(JOptionPane.showConfirmDialog(ventana.getVentana(), "Realmente desea guardar la seccion?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen009.png")))==JOptionPane.YES_OPTION){
						if(conexion.conectar()){
							query = "SELECT IFNULL(MAX(seccion.id_seccion)+1,1) FROM seccion";
							ResultSet resultSet = conexion.consultar(query);
								try {
									if(resultSet.next()){
											int max_id = Integer.parseInt(resultSet.getString(1));												
											ventana.setIdSeccion(max_id);
										query = "INSERT INTO seccion VALUES("+max_id+",'"+
											nombre+"',"+periodo+",1)";
									//System.out.println(query);
									///-------------Validacion de Insercion---------
											if(conexion.actualizar(query)){													
												ventana.setEstadoVentana(0);
		JOptionPane.showMessageDialog(ventana.getVentana(), "La seccion fue guardada con exito.", "Correcto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen017.png")));
											ventana.activarControles(true);
											retorno = 1;
											}else{
		JOptionPane.showMessageDialog(ventana.getVentana(), "Error al almacenar seccion.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen006.png")));										
											retorno  = -1;											
											}
											
									///---------------------------------------------
									}//fin if 
									conexion.desconectar();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
						}
				}//fi if 
			}else{
		//-----------------------------------------------------		
				query = "SELECT seccion.id_seccion FROM seccion WHERE seccion.nombre='"+nombre+
				"' AND seccion.periodo='"+periodo+"'";
				if(conexion.conectar()){
					ResultSet resultSet = conexion.consultar(query);	
					try {
						if(resultSet.next()){
							ventana.setIdSeccion(Integer.parseInt(resultSet.getString(1)));
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}//fin if 
		//-----------------------------------------------------------------		
//JOptionPane.showMessageDialog(ventana.getVentana(), "La seccion ya Existe en el Sistema!!!.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen006.png")));				
		if(JOptionPane.showConfirmDialog(ventana.getVentana(), "La seccion a crear ya existe en el sistema.\n" +
				" Desea cargar los datos para su edicion.?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen009.png")))==JOptionPane.YES_OPTION){
			retorno = 2;
			ventana.activarControles(true);
		}else{
			retorno = 0;
		}//fin else
	
			
			}
				return retorno;
	}
	
	public boolean existeSeccion(String nombre, String periodo){
		if(conexion.conectar()){
			query = "SELECT * FROM seccion WHERE nombre='"+nombre+
					"' AND periodo ='"+periodo+"'";
			ResultSet resultSet = conexion.consultar(query);
			try {
				if(resultSet.next()){
					conexion.desconectar();
					return true;
				}else{
					conexion.desconectar();
					return false;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}//fin if 
			conexion.desconectar();
			return true;
	}
	
	public void errorCerrarInternalFrame(){
		JOptionPane.showMessageDialog(ventana.getVentana(), "Debe asignar un horario a la seccion creada.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen006.png")));
	}
	
	public boolean confirmarGuardarHorario(){
		if(JOptionPane.showConfirmDialog(ventana.getVentana(), "Realmente desea guardar este horario?", "Informacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(ventana.getClass().getResource("/Imagenes/Imagen009.png")))==JOptionPane.YES_OPTION){
				return true;
		}else{
				return false;
		}
	}
}
