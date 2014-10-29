package es.udc.med.espectaculos.model.musicogrupo;

public class MusicoGrupo {

	private Long idMusicoGrupo;
	private Long idMusico;
	private Long idGrupo;

	public MusicoGrupo(Long idMusicoGrupo, Long idMusico, Long idGrupo) {
		super();
		this.idMusicoGrupo = idMusicoGrupo;
		this.idMusico = idMusico;
		this.idGrupo = idGrupo;
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

}
