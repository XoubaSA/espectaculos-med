package es.udc.med.espectaculos.model.grupo;

public class Grupo {

	private Integer idGrupo;
	private String nombreOrquesta;
	private float salarioActuacion;

	public Grupo(Integer idGrupo, String nombreOrquesta, float salarioActuacion) {
		super();
		this.idGrupo = idGrupo;
		this.nombreOrquesta = nombreOrquesta;
		this.salarioActuacion = salarioActuacion;
	}

	public Grupo(String nombre, float salario) {
		this.nombreOrquesta = nombre;
		this.salarioActuacion = salario;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreOrquesta() {
		return nombreOrquesta;
	}

	public void setNombreOrquesta(String nombreOrquesta) {
		this.nombreOrquesta = nombreOrquesta;
	}

	public float getSalarioActuacion() {
		return salarioActuacion;
	}

	public void setSalarioActuacion(float salarioActuacion) {
		this.salarioActuacion = salarioActuacion;
	}

}
