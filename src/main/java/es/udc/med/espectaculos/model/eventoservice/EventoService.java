package es.udc.med.espectaculos.model.eventoservice;

import java.util.Calendar;
import java.util.List;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupoevento.GrupoEvento;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.EventoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface EventoService {

	public Evento crearEvento(String nombreEvento, Calendar fechaInicio,
			String direccion) throws InputValidationException,
			EventoExisteException;

	public List<Evento> findAllEvents();

	public Evento obtenerEventoPorNombre(String nombreEvento)
			throws InstanceNotFoundException;

	public GrupoEvento asignarGrupoEvento(Grupo grupo, Evento evento,
			String fecha) throws InputValidationException,
			AsignarGrupoEventoException;

	public GrupoEvento obtenerGrupoEvento(Grupo grupo, Evento evento);
	
	public List<Grupo> obtenerGruposEvento(Evento evento);

	public List<Evento> obtenerEventosFecha(Calendar fecha);

	public List<Calendar> getDates(String mes, Integer grupo_id);

	public List<Evento> filtrarEventosGrupo(Grupo grupo, Calendar fechaInicio,
			Calendar fechaFin) throws InputValidationException;

	public List<Evento> obtenerEventosDeGrupoDia(Grupo grupo, Calendar fecha)
			throws InputValidationException;

	public void borrarEvento(Integer idEvento) throws InstanceNotFoundException;

	public void borrarGrupoEvento(Integer idGrupoEvento)
			throws InstanceNotFoundException;

}
