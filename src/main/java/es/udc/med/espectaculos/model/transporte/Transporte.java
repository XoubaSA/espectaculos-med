package es.udc.med.espectaculos.model.transporte;

public class Transporte {

	private long idTransporte;
	private String nombreTransporte;
	private String matricula;
	private String descripcion;

	public Transporte(String nombre, String matricula, String descripcion) {
		this.nombreTransporte = nombre;
		this.matricula = matricula;
		this.descripcion = descripcion;
	}

	public long getIdTransporte() {
		return idTransporte;
	}

	public void setIdTransporte(long idTransporte) {
		this.idTransporte = idTransporte;
	}

	public String getNombreTransporte() {
		return nombreTransporte;
	}

	public void setNombreTransporte(String nombreTransporte) {
		this.nombreTransporte = nombreTransporte;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
