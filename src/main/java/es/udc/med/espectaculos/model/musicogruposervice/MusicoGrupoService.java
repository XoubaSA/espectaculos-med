package es.udc.med.espectaculos.model.musicogruposervice;

import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupo;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;

public interface MusicoGrupoService {
	
	public MusicoGrupo asignarMusicoGrupo(Musico musico, Grupo grupo) 
			throws InputValidationException, MusicoAsignadoException;
	
	public Musico crearMusico(String nombreMusico, String direccion, String instrumento);
	
	public Musico obtenerMusicoPorNombre(String nombreMusico) throws InstanceNotFoundException;

	public void borrarMusico(Integer idMusico) throws InstanceNotFoundException;

	public List<Musico> getMusicos();
	
	public List<Grupo> getGrupos();
	
	//TODO Falta implementar un metodo que obtenga los musicos de un grupo GetFormacion() que devuelva List<Musico>
	
	//TODO Falta implementar borrado de un musico en un grupo concreto

}