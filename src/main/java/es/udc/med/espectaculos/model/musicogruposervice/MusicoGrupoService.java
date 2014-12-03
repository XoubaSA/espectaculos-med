package es.udc.med.espectaculos.model.musicogruposervice;

import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupo;
import es.udc.med.espectaculos.utils.GrupoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;
import es.udc.med.espectaculos.utils.MusicoExisteException;

public interface MusicoGrupoService {

	public MusicoGrupo asignarMusicoGrupo(Musico musico, Grupo grupo)
			throws InputValidationException, MusicoAsignadoException;

	public Musico crearMusico(String nombreMusico, String direccion,
			String instrumento) throws InputValidationException,
			MusicoExisteException;

	public Musico obtenerMusicoPorNombre(String nombreMusico)
			throws InstanceNotFoundException;

	public void borrarMusico(Integer idMusico) throws InstanceNotFoundException;

	public void borrarMiembro(Integer idGrupo, Integer idMusico)
			throws InstanceNotFoundException;

	public void borrarMusicoGrupo(Integer idMusicoGrupo)
			throws InstanceNotFoundException;

	public List<Musico> getMusicos();

	public List<Grupo> getGrupos();

	public List<Musico> getFormacion(Grupo grupo);

	Grupo crearGrupo(String nombreOrquesta, float salarioActuacion)
			throws InputValidationException, GrupoExisteException;

	void borrarGrupo(Integer idGrupo) throws InstanceNotFoundException;

	List<Grupo> obtenerGrupos();

	Grupo obtenerGrupoPorNombre(String nombreGrupo)
			throws InstanceNotFoundException;

}