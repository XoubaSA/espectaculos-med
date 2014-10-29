package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;

public interface MusicoGrupoDao {

	public MusicoGrupo create(Connection conexion, MusicoGrupo musicoGrupo);
	
}
