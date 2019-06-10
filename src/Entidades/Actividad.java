package Entidades;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Actividad {
	private int id_actividad ;
	private String fecha = null;
	private int dia_semana;
	private int id_seccion;
	private int tipo_actividad;
	private String observaciones;
	
	public Actividad(){
			super();
	}

	public int getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(int id_actividad) {
		this.id_actividad = id_actividad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getDia_semana() {
		return dia_semana;
	}

	public void setDia_semana(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		this.dia_semana = c.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	public void setDia_semana(int dia_semana){
		this.dia_semana = dia_semana;
	}

	public int getId_seccion() {
		return id_seccion;
	}

	public void setId_seccion(int id_seccion) {
		this.id_seccion = id_seccion;
	}

	public int getTipo_actividad() {
		return tipo_actividad;
	}

	public void setTipo_actividad(int tipo_actividad) {
		this.tipo_actividad = tipo_actividad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
