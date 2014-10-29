package es.udc.med.espectaculos.model.musicogrupotemporal;

import java.sql.Connection;

public interface MusicoGrupoTemporalDao {

	public MusicoGrupoTemporal create(Connection conexion,
			MusicoGrupoTemporal musicoGrupoTemporal);

}
