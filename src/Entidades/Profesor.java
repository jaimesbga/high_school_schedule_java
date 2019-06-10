package Entidades;

public class Profesor {
	private int titulo;
	private Persona persona;
	
	public Profesor() {
		super();
	}
	
	public int getTitulo() {
		return titulo;
	}

	public void setTitulo(int titulo) {
		this.titulo = titulo;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}
