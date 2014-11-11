package es.udc.med.espectaculos.model.musicogruposervice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.musico.Jdbc3CcSqlMusicoDao;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musico.MusicoDao;
import es.udc.med.espectaculos.utils.ConexionManager;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class MusicoGrupoServiceImpl implements MusicoGrupoService {

	private GrupoDao grupoDao = new Jdbc3CcSqlGrupoDao();
	private MusicoDao musicoDao = new Jdbc3CcSqlMusicoDao();
	private Connection conexion = ConexionManager.getConnection();
	private static List<Musico> musicos;

	// TODO en la clase músico, añadir atributo TELEFONO
	private static final Map<Grupo, List<Musico>> asociacion;
	static {
		musicos = new ArrayList<Musico>();
		musicos.add(new Musico("Pepe Pérez", "Calle Falsa 123", "Guitarra"));
		musicos.add(new Musico("Manolo Gómez", "Calle Falsa 321", "Batería"));
		musicos.add(new Musico("Juan González", "Calle Falsa 231", "Bajo"));
		musicos.add(new Musico("Pedro García", "Calle Falsa 696", "Voz"));
		asociacion = new HashMap<Grupo, List<Musico>>();
	}

	// TODO los datos se guardan en variables en tiempo de ejecución, una vez
	// el cliente de el visto bueno, se cambiará el Map asociación
	// por persistencia en BD
	@Override
	public void asociarMusicoGrupo(List<Musico> musicos, Grupo grupo)
			throws InputValidationException {

		if (musicos.isEmpty() || (grupo == null)) {
			throw new InputValidationException("Datos no válidos");
		}
		if (grupoDao.getGrupos(conexion).contains(grupo)) {
			List<Musico> formacionFinal = filtrarAsignacionMusicosGrupo(
					musicos, asociacion.get(grupo));
			asociacion.put(grupo, formacionFinal);
		} else {
			throw new InputValidationException("El grupo no existe");
		}
	}

	private List<Musico> filtrarAsignacionMusicosGrupo(
			List<Musico> musicosAsignar, List<Musico> musicosAsignados) {

		List<Musico> formacionFinal = musicosAsignados;
		for (Musico musicoAsignar : musicosAsignar) {
			if (!musicosAsignados.contains(musicoAsignar)) {
				formacionFinal.add(musicoAsignar);
			}
		}
		return formacionFinal;
	}

	@Override
	public Musico crearMusico(String nombreMusico, String direccion,
			String instrumento) {
		Musico musico = new Musico(nombreMusico, direccion, instrumento);
		try {
			try {
				musico = musicoDao.create(conexion, musico);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return musico;
	}

	@Override
	public void borrarMusico(Integer idMusico) throws InstanceNotFoundException {
		try {
			try {
				musicoDao.remove(conexion, idMusico);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Grupo> getGrupos() {
		return grupoDao.getGrupos(conexion);
	}
	
	@Override
	public List<Musico> getMusicos() {
		return this.musicos;
	}

	@Override
	public List<Musico> getFormacion(Grupo grupo)
			throws InputValidationException, InstanceNotFoundException {
		if (grupo == null)
			throw new InputValidationException("El grupo no debe ser null");

		if (asociacion.containsKey(grupo)) {
			return asociacion.get(grupo);
		} else {
			throw new InstanceNotFoundException(grupo, "Grupo");
		}
	}
}
