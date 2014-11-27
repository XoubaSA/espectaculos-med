package es.udc.med.espectaculos.model.grupoevento;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;
import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface GrupoEventoDao {

	public GrupoEvento create(Connection conexion, GrupoEvento grupoEvento);
	
	public void remove(Connection connection, Integer idGrupoEvento) throws InstanceNotFoundException;
	
	public List<Calendar> getDates(Connection connection, String mes, Integer grupo_id);
	
	/**
	 * Se utiliza para recuparar los eventos de un grupo en un mes 
	 * @param grupo
	 * @param fecha 
	 * @return
	 */
	public List<Evento> obtenerEventosGrupoFecha(Connection conexion, Grupo grupo, Calendar fechaInicio, Calendar fechaFin);
	
	public List<Grupo> obtenerGruposEvento(Connection conexion, Evento evento);
	
	public boolean grupoAsignadoFecha(Connection conexion, Grupo grupo, String fecha);
}

