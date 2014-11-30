package es.udc.med.espectaculos.model.musico;

public class Musico {

	private Integer idMusico;
	private String nombreMusico;
	private String direccion;
	private String instrumento;
	
	public Musico(String nombre, String direccion, String instrumento) {
		this.nombreMusico = nombre;
		this.direccion = direccion;
		this.instrumento = instrumento;
	}
	
	public Musico(Integer idMusico, String nombreMusico, String direccion,
			String instrumento) {
		this.idMusico = idMusico;
		this.nombreMusico = nombreMusico;
		this.direccion = direccion;
		this.instrumento = instrumento;
	}

	public Integer getIdMusico() {
		return idMusico;
	}

	public void setIdMusico(Integer idMusico) {
		this.idMusico = idMusico;
	}

	public String getNombreMusico() {
		return nombreMusico;
	}

	public void setNombreMusico(String nombreMusico) {
		this.nombreMusico = nombreMusico;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}

}
