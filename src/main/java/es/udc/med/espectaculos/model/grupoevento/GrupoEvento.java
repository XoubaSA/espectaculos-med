package es.udc.med.espectaculos.model.grupoevento;

import java.util.Calendar;

public class GrupoEvento {

	private Integer idGrupoEvento;
	private Calendar fechaActuacion;
	private Integer idEvento;
	private Integer idGrupo;

	public GrupoEvento(Calendar fechaActuacion, Integer idEvento, Integer idGrupo) {
		this.fechaActuacion = fechaActuacion;
		this.idEvento = idEvento;
		this.idGrupo = idGrupo;
	}

	public Integer getIdGrupoEvento() {
		return idGrupoEvento;
	}

	public void setIdGrupoEvento(Integer idGrupoEvento) {
		this.idGrupoEvento = idGrupoEvento;
	}

	public Calendar getFechaActuacion() {
		return fechaActuacion;
	}

	public void setFechaActuacion(Calendar fechaActuacion) {
		this.fechaActuacion = fechaActuacion;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
