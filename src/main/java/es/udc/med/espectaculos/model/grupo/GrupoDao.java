package es.udc.med.espectaculos.model.grupo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface GrupoDao {

	public void update(Connection connection, Grupo grupo)
			throws InstanceNotFoundException;

	public void remove(Connection connection, Integer grupoId)
			throws InstanceNotFoundException;
	
	public List<Grupo> getGrupos(Connection connection);
	
	public Grupo create(Connection connection, Grupo grupo) 
			throws SQLException;
}
