package es.udc.med.espectaculos.model.musicogruposervice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.ConexionManager;
import es.udc.med.espectaculos.utils.InputValidationException;

public class MusicoGrupoServiceImpl implements MusicoGrupoService {

	private GrupoDao grupoDao = new Jdbc3CcSqlGrupoDao();
	private Connection conexion = ConexionManager.getConnection();

	//TODO en la clase músico, añadir atributo TELEFONO
	private static final Map<Grupo, List<Musico>> asociacion;
	static {
		List<Musico> musicosOrquestaMusical = new ArrayList<Musico>();
		List<Musico> musicosOrquestaTemporal = new ArrayList<Musico>();
		musicosOrquestaMusical.add(new Musico("Pepe Pérez", "Calle Falsa 123",
				"Guitarra"));
		musicosOrquestaMusical.add(new Musico("Manolo Gómez",
				"Calle Falsa 321", "Batería"));
		musicosOrquestaMusical.add(new Musico("Juan González",
				"Calle Falsa 231", "Bajo"));
		musicosOrquestaMusical.add(new Musico("Pedro García",
				"Calle Falsa 696", "Voz"));
		asociacion = new HashMap<Grupo, List<Musico>>();
		asociacion.put(new Grupo("Orquesta Musical", 3000.0F),
				musicosOrquestaMusical);
		asociacion.put(new Grupo("Orquesta Temporal", 1000.0F),
				musicosOrquestaTemporal);
	}

	//TODO los datos se guardan en variables en tiempo de ejecución, una vez
	// 	   el cliente de el visto bueno, se cambiará el Map asociación
	//	   por persistencia en BD
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
}
