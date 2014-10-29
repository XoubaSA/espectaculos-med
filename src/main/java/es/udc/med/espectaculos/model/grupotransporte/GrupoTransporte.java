package es.udc.med.espectaculos.model.grupotransporte;

import java.util.Calendar;

public class GrupoTransporte {

	private Long idGrupoTransporte;
	private Long idTransporte;
	private Long idGrupo;
	private Calendar fechaInicio;
	private Calendar fechaFin;

	public GrupoTransporte(Long idGrupoTransporte, Long idTransporte,
			Long idGrupo, Calendar fechaInicio, Calendar fechaFin) {
		super();
		this.idGrupoTransporte = idGrupoTransporte;
		this.idTransporte = idTransporte;
		this.idGrupo = idGrupo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public Long getIdGrupoTransporte() {
		return idGrupoTransporte;
	}

	public void setIdGrupoTransporte(Long idGrupoTransporte) {
		this.idGrupoTransporte = idGrupoTransporte;
	}

	public Long getIdTransporte() {
		return idTransporte;
	}

	public void setIdTransporte(Long idTransporte) {
		this.idTransporte = idTransporte;
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
