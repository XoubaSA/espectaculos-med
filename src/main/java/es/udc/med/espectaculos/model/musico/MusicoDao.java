package es.udc.med.espectaculos.model.musico;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface MusicoDao {
	
	public void update(Connection connection, Musico musico)
			throws InstanceNotFoundException;

	public void remove(Connection connection, Integer musicoId)
			throws InstanceNotFoundException;

	public Musico create(Connection connection, Musico musico) 
			throws SQLException;
	
	public List<Musico> getMusicos(Connection connection);

	public Musico obtenerMusicoPorNombre(Connection conexion,
			String nombreMusico) throws InstanceNotFoundException;
	
}
