package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;
import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface MusicoGrupoDao {

	public MusicoGrupo create(Connection conexion, MusicoGrupo musicoGrupo);
	
	public void remove(Connection connection, Integer idMusicoGrupo) throws InstanceNotFoundException;
	
	public boolean musicoAsignadoGrupo(Connection conexion, Grupo grupo, Musico musico);
	
	public List<Musico> getFormacion(Connection conexion, Grupo grupo);
	
	public void removeMember(Connection conexion, Integer idGrupo, Integer idMusico) throws InstanceNotFoundException;
	
}
