package es.udc.med.espectaculos.model.musicogruposervice;

import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface MusicoGrupoService {
	
	public void asociarMusicoGrupo(List<Musico> musicos, Grupo grupo) throws InputValidationException;
	
	public Musico crearMusico(String nombreMusico, String direccion, String instrumento);

	public void borrarMusico(Integer idMusico) throws InstanceNotFoundException;

	public List<Musico> getMusicos();
	
	public List<Grupo> getGrupos();
	
	public List<Musico> getFormacion(Grupo grupo) throws InputValidationException, InstanceNotFoundException;
	
}