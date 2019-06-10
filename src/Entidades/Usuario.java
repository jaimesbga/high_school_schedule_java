package Entidades;

public class Usuario {
	private int id_usuario;
	private String nombre;	
	private String usuario;
	private String clave;
	
	public Usuario() {
		id_usuario = 0;
		nombre = "";
		usuario = "";
		clave = "";
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}	
}