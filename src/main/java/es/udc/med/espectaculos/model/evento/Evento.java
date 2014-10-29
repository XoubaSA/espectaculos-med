package es.udc.med.espectaculos.model.evento;

import java.util.Calendar;

public class Evento {

	private Integer idEvento;
	private String nombreEvento;
	private Calendar fechaInicioEvento;
	private String localidad;

	public Evento(String nombre, Calendar fecha_ini, String localidad) {
		this.nombreEvento = nombre;
		this.fechaInicioEvento = fecha_ini;
		this.localidad = localidad;
	}

	public Evento(Integer id, String nombre, Calendar fecha_ini,
			String localidad) {
		this.idEvento = id;
		this.nombreEvento = nombre;
		this.fechaInicioEvento = fecha_ini;
		this.localidad = localidad;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public Calendar getFechaInicioEvento() {
		return fechaInicioEvento;
	}

	public void setFechaInicioEvento(Calendar fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
