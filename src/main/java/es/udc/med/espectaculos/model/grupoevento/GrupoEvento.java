package es.udc.med.espectaculos.model.grupoevento;

public class GrupoEvento {

	private Integer idGrupoEvento;
	private String fechaActuacion;
	private Integer idEvento;
	private Integer idGrupo;

	public GrupoEvento(String fecha, Integer idEvento, Integer idGrupo) {
		this.fechaActuacion = fecha;
		this.idEvento = idEvento;
		this.idGrupo = idGrupo;
	}

	public Integer getIdGrupoEvento() {
		return idGrupoEvento;
	}

	public void setIdGrupoEvento(Integer idGrupoEvento) {
		this.idGrupoEvento = idGrupoEvento;
	}

	public String getFechaActuacion() {
		return fechaActuacion;
	}

	public void setFechaActuacion(String fechaActuacion) {
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
