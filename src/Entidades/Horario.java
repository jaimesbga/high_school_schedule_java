package Entidades;

public class Horario {
	public int id_horario;
	public int id_materia;
	public int id_bloque;
	public int id_seccion;
	public int id_persona;
	public int dia_semana;
	public String aula;
	
	public Horario(){
		super();
	}
	
	
	public Horario(int id_horario, int id_materia, int id_bloque,
			int id_seccion, int id_persona, int dia_semana, String aula) {
		super();
		this.id_horario = id_horario;
		this.id_materia = id_materia;
		this.id_bloque = id_bloque;
		this.id_seccion = id_seccion;
		this.id_persona = id_persona;
		this.dia_semana = dia_semana;
		this.aula = aula;
	}


	public int getId_horario() {
		return id_horario;
	}
	public void setId_horario(int id_horario) {
		this.id_horario = id_horario;
	}
	public int getId_materia() {
		return id_materia;
	}
	public void setId_materia(int id_materia) {
		this.id_materia = id_materia;
	}
	public int getId_bloque() {
		return id_bloque;
	}
	public void setId_bloque(int id_bloque) {
		this.id_bloque = id_bloque;
	}
	public int getId_seccion() {
		return id_seccion;
	}
	public void setId_seccion(int id_seccion) {
		this.id_seccion = id_seccion;
	}
	public int getId_persona() {
		return id_persona;
	}
	public void setId_persona(int id_persona) {
		this.id_persona = id_persona;
	}
	public int getDia_semana() {
		return dia_semana;
	}
	public void setDia_semana(int dia_semana) {
		this.dia_semana = dia_semana;
	}
	public String getAula() {
		return aula;
	}
	public void setAula(String aula) {
		this.aula = aula;
	}
	
	
}
