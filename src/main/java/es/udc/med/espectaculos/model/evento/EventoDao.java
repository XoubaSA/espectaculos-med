package es.udc.med.espectaculos.model.evento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import es.udc.med.espectaculos.utils.EventoExisteException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface EventoDao {

	public void update(Connection connection, Evento evento)
			throws InstanceNotFoundException;

	public void remove(Connection connection, Integer eventoId)
			throws InstanceNotFoundException;

	public List<Evento> getEventoByDate(Connection connection, Calendar fecha)
			throws InstanceNotFoundException;

	public Evento create(Connection connection, Evento evento)
			throws SQLException, EventoExisteException;

	public Evento buscarEventoPorNombre(Connection conexion, String nombreEvento) throws InstanceNotFoundException;

	public List<Evento> findAllEvents(Connection conexion);	
	
}
