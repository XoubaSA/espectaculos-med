package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface MusicoGrupoDao {

	public MusicoGrupo create(Connection conexion, MusicoGrupo musicoGrupo);
	
	public void remove(Connection connection, Integer idMusicoGrupo) throws InstanceNotFoundException;
	
	public boolean musicoAsignadoGrupo(Connection conexion, Grupo grupo, Musico musico);
	
}
