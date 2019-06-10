package Entidades;

public class Estudiante {
	private int numero_lista;
	private int estado;
	private String nombre_representante;
	private String cedula_representante;
	private String telefono_representante;
	private Persona persona;
	private Seccion seccion;;
	
	public Estudiante() {
		super();
	}
	
	public int getNumero_lista() {
		return numero_lista;
	}
	
	public void setNumero_lista(int numero_lista) {
		this.numero_lista = numero_lista;
	}
	
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String getNombre_representante() {
		return nombre_representante;
	}
	
	public void setNombre_representante(String nombre_representante) {
		this.nombre_representante = nombre_representante;
	}
	
	public String getCedula_representante() {
		return cedula_representante;
	}
	
	public void setCedula_representante(String cedula_representante) {
		this.cedula_representante = cedula_representante;
	}
	
	public String getTelefono_representante() {
		return telefono_representante;
	}
	
	public void setTelefono_representante(String telefono_representante) {
		this.telefono_representante = telefono_representante;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}
}
