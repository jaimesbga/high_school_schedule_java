package Entidades;

public class Bloque {
	private int id_bloque;
	private int numero;
	private String descripcion;
	private int estado;
	
	public Bloque() {
		super();
	}
	
	public int getId_bloque() {
		return id_bloque;
	}
	
	public void setId_bloque(int id_bloque) {
		this.id_bloque = id_bloque;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}	
}
