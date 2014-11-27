package es.udc.med.espectaculos.model.eventoservice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.evento.EventoDao;
import es.udc.med.espectaculos.model.evento.Jdbc3CcSqlEventoDao;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.grupoevento.GrupoEvento;
import es.udc.med.espectaculos.model.grupoevento.GrupoEventoDao;
import es.udc.med.espectaculos.model.grupoevento.Jdbc3CcSqlGrupoEventoDao;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.ConexionManager;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.PropertyValidator;

public class EventoServiceImpl implements EventoService {

	private EventoDao eventoDao;
	private GrupoDao grupoDao;
	private GrupoEventoDao grupoEventoDao;

	private Connection conexion = ConexionManager.getConnection();

	public EventoServiceImpl() {
		this.eventoDao = new Jdbc3CcSqlEventoDao();
		this.grupoDao = new Jdbc3CcSqlGrupoDao();
		this.grupoEventoDao = new Jdbc3CcSqlGrupoEventoDao();
	}

	@Override
	public Evento crearEvento(String nombreEvento, Calendar fechaInicio,
			String localidad) throws InputValidationException {
		Evento evento = new Evento(nombreEvento, fechaInicio, localidad);
		PropertyValidator.validateEvent(evento);
		try {
			try {
				evento = eventoDao.create(conexion, evento);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return evento;
	}

	@Override
	public Grupo crearGrupo(String nombreOrquesta, float salarioActuacion) {
		Grupo grupo = new Grupo(nombreOrquesta, salarioActuacion);
		try {
			try {
				grupo = grupoDao.create(conexion, grupo);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return grupo;
	}

	@Override
	public Evento obtenerEventoPorNombre(String nombreEvento)
			throws InstanceNotFoundException {
		return eventoDao.buscarEventoPorNombre(conexion, nombreEvento);
	}

	@Override
	public GrupoEvento asignarGrupoEvento(Grupo grupo, Evento evento,
			String fecha) throws InputValidationException,
			AsignarGrupoEventoException {

		if ((grupo == null) || (evento == null))
			throw new InputValidationException("Evento o Grupo nulo");

		if (grupoEventoDao.grupoAsignadoFecha(conexion, grupo, fecha)) {
			throw new AsignarGrupoEventoException("El grupo "
					+ grupo.getNombreOrquesta()
					+ " ya esta asignado en otro lugar la misma fecha");
		}

		GrupoEvento grupoEvento = new GrupoEvento(fecha, evento.getIdEvento(),
				grupo.getIdGrupo());
		return grupoEventoDao.create(conexion, grupoEvento);
	}

	@Override
	public List<Grupo> obtenerGruposEvento(Evento evento) {
		return grupoEventoDao.obtenerGruposEvento(conexion, evento);
	}
	
	@Override
	public List<Evento> obtenerEventosFecha(Calendar fecha) {
		try {
			return eventoDao.getEventoByDate(conexion, fecha);
		} catch (InstanceNotFoundException e) {
			return new ArrayList<Evento>();
		}
	}

	@Override
	public List<Calendar> getDates(String mes, Integer grupo_id) {
		return grupoEventoDao.getDates(conexion, mes, grupo_id);
	}

	@Override
	public List<Evento> filtrarEventosGrupo(Grupo grupo, Calendar fechaInicio,
			Calendar fechaFin) throws InputValidationException {
		if ((grupo == null) || (fechaInicio == null) || (fechaFin == null))
			throw new InputValidationException(
					"Grupo y fecha no pueden ser nulos");

		return grupoEventoDao.obtenerEventosGrupoFecha(conexion, grupo,
				fechaInicio, fechaFin);
	}

	@Override
	public void borrarEvento(Integer idEvento) throws InstanceNotFoundException {
		try {
			try {
				eventoDao.remove(conexion, idEvento);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void borrarGrupo(Integer idGrupo) throws InstanceNotFoundException {
		try {
			try {
				grupoDao.remove(conexion, idGrupo);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void borrarGrupoEvento(Integer idGrupoEvento)
			throws InstanceNotFoundException {
		try {
			try {
				grupoEventoDao.remove(conexion, idGrupoEvento);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

