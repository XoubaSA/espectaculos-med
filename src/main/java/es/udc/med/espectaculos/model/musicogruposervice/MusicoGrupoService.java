package es.udc.med.espectaculos.model.musicogruposervice;

import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InputValidationException;

public interface MusicoGrupoService {
	
	public void asociarMusicoGrupo(List<Musico> musicos, Grupo grupo) throws InputValidationException;

}
