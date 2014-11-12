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
	private static List<Musico> musicos = new ArrayList<Musico>();
	
	// TODO en la clase músico, añadir atributo TELEFONO
	private static final Map<String, List<Musico>> asociacion;
	static {
		musicos.add(new Musico("Pepe Pérez", "Calle Falsa 123", "Guitarra"));
		musicos.add(new Musico("Manolo Gómez", "Calle Falsa 321", "Batería"));
		musicos.add(new Musico("Juan González", "Calle Falsa 231", "Bajo"));
		musicos.add(new Musico("Pedro García", "Calle Falsa 696", "Voz"));
		asociacion = new HashMap<String, List<Musico>>();
		asociacion.put("Orquesta Musical", null);
		asociacion.put("Los Chichos", null);
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
		if (asociacion.containsKey(grupo.getNombreOrquesta())) {
			List<Musico> formacionFinal = filtrarAsignacionMusicosGrupo(
					musicos, asociacion.get(grupo.getNombreOrquesta()));
			asociacion.put(grupo.getNombreOrquesta(), formacionFinal);
		} else {
			throw new InputValidationException("El grupo no existe");
		}
	}

	private List<Musico> filtrarAsignacionMusicosGrupo(
			List<Musico> musicosAsignar, List<Musico> musicosAsignados) {

		if (musicosAsignados == null) {
			musicosAsignados = musicosAsignar;
		} else {
			for (Musico musicoAsignar : musicosAsignar) {
				if (!musicosAsignados.contains(musicoAsignar)) {
					musicosAsignados.add(musicoAsignar);
				}
			}
		}
		return musicosAsignados;
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
	public List<Musico> getFormacion(Grupo grupo)
			throws InputValidationException, InstanceNotFoundException {
		if (grupo == null)
			throw new InputValidationException("El grupo no debe ser null");

		if (asociacion.containsKey(grupo.getNombreOrquesta())) {
			return asociacion.get(grupo.getNombreOrquesta());
		} else {
			throw new InstanceNotFoundException(grupo, "Grupo");
		}
	}

	@Override
	public List<Musico> getMusicos() {
		return musicos;
	}

	@Override
	public List<Grupo> getGrupos() {
		return null;
	}

}
