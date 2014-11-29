package es.udc.med.espectaculos.model.musicogrupo;

public class MusicoGrupo {

	private Integer idMusicoGrupo;
	private Integer idMusico;
	private Integer idGrupo;

	public MusicoGrupo(Integer idMusico, Integer idGrupo) {
		this.idMusico = idMusico;
		this.idGrupo = idGrupo;
	}

	public Integer getIdMusicoGrupo() {
		return idMusicoGrupo;
	}

	public void setIdMusicoGrupo(Integer idMusicoGrupo) {
		this.idMusicoGrupo = idMusicoGrupo;
	}

	public Integer getIdMusico() {
		return idMusico;
	}

	public void setIdMusico(Integer idMusico) {
		this.idMusico = idMusico;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
