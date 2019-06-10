package Entidades;

public class Roles {
	private int id_rol;
	private String nombre;
	
	public Roles() {
		id_rol = 0;
		nombre = "";
	}

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
