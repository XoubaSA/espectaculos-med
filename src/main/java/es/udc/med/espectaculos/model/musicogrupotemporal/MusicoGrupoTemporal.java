package es.udc.med.espectaculos.model.musicogrupotemporal;

import java.util.Calendar;

public class MusicoGrupoTemporal {

	private Long idMusicoGrupo;
	private Long idMusico;
	private Long idGrupo;
	private Calendar fechaInicio;
	private Calendar fechaFin;

	public MusicoGrupoTemporal(Long idMusicoGrupo, Long idMusico, Long idGrupo,
			Calendar fechaInicio, Calendar fechaFin) {
		super();
		this.idMusicoGrupo = idMusicoGrupo;
		this.idMusico = idMusico;
		this.idGrupo = idGrupo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Long getIdMusicoGrupo() {
		return idMusicoGrupo;
	}

	public void setIdMusicoGrupo(Long idMusicoGrupo) {
		this.idMusicoGrupo = idMusicoGrupo;
	}

	public Long getIdMusico() {
		return idMusico;
	}

	public void setIdMusico(Long idMusico) {
		this.idMusico = idMusico;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Calendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Calendar fechaFin) {
		this.fechaFin = fechaFin;
	}

}
