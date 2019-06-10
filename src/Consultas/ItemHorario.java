package Consultas;

public class ItemHorario {
	private int id_horario;
	private int posicion;
	private String nombre_bloque = null;
	private String nombre_materia = null;
	
	
	public ItemHorario(){
		super();
	}	
	
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public int getId_horario() {
		return id_horario;
	}
	public void setId_horario(int id_horario) {
		this.id_horario = id_horario;
	}
	public String getNombre_bloque() {
		return nombre_bloque;
	}
	public void setNombre_bloque(String nombre_bloque) {
		this.nombre_bloque = nombre_bloque;
	}
	public String getNombre_materia() {
		return nombre_materia;
	}
	public void setNombre_materia(String nombre_materia) {
		this.nombre_materia = nombre_materia;
	}
	
}
