package es.udc.med.espectaculos.model.musicogruposervice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.musico.Jdbc3CcSqlMusicoDao;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musico.MusicoDao;
import es.udc.med.espectaculos.model.musicogrupo.Jdbc3CcSqlMusicoGrupoDao;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupo;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupoDao;
import es.udc.med.espectaculos.utils.ConexionManager;
import es.udc.med.espectaculos.utils.GrupoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;
import es.udc.med.espectaculos.utils.MusicoExisteException;
import es.udc.med.espectaculos.utils.PropertyValidator;

public class MusicoGrupoServiceImpl implements MusicoGrupoService {

	private GrupoDao grupoDao;
	private MusicoDao musicoDao;
	private MusicoGrupoDao musicoGrupoDao;

	private Connection conexion = ConexionManager.getConnection();

	public MusicoGrupoServiceImpl() {
		this.grupoDao = new Jdbc3CcSqlGrupoDao();
		this.musicoDao = new Jdbc3CcSqlMusicoDao();
		this.musicoGrupoDao = new Jdbc3CcSqlMusicoGrupoDao();
	}

	@Override
	public Musico crearMusico(String nombreMusico, String direccion,
			String instrumento) throws InputValidationException,
			MusicoExisteException {
		try {
			obtenerMusicoPorNombre(nombreMusico);
			throw new MusicoExisteException(
					"Ya existe un músico con ese nombre");
		} catch (InstanceNotFoundException ex) {
			Musico musico = new Musico(nombreMusico, direccion, instrumento);
			PropertyValidator.validateMusico(musico);
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
	}

	@Override
	public MusicoGrupo asignarMusicoGrupo(Musico musico, Grupo grupo)
			throws InputValidationException, MusicoAsignadoException {

		if ((musico == null) || (grupo == null))
			throw new InputValidationException("Musico o Grupo nulo");

		if (musicoGrupoDao.musicoAsignadoGrupo(conexion, grupo, musico)) {
			throw new MusicoAsignadoException("El musico "
					+ musico.getNombreMusico()
					+ " ya esta asignado a esta orquesta");
		}

		MusicoGrupo musicoGrupo = new MusicoGrupo(musico.getIdMusico(),
				grupo.getIdGrupo());
		return musicoGrupoDao.create(conexion, musicoGrupo);
	}

	@Override
	public Musico obtenerMusicoPorNombre(String nombreMusico)
			throws InstanceNotFoundException {
		return musicoDao.obtenerMusicoPorNombre(conexion, nombreMusico);
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
	public void borrarMiembro(Integer idGrupo, Integer idMusico)
			throws InstanceNotFoundException {
		try {
			try {
				musicoGrupoDao.removeMember(conexion, idGrupo, idMusico);
				conexion.commit();
			} catch (SQLException e) {
				conexion.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void borrarMusicoGrupo(Integer idMusicoGrupo)
			throws InstanceNotFoundException {
		try {
			try {
				musicoGrupoDao.remove(conexion, idMusicoGrupo);
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
		return musicoDao.getMusicos(conexion);
	}

	@Override
	public List<Musico> getFormacion(Grupo grupo) {
		return musicoGrupoDao.getFormacion(conexion, grupo);
	}

	@Override
	public Grupo crearGrupo(String nombreOrquesta, float salarioActuacion)
			throws InputValidationException, GrupoExisteException {
		if (nombreOrquesta == null || nombreOrquesta.equals("")) {
			throw new InputValidationException("Nombre de grupo no válido");
		}
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
	public List<Grupo> obtenerGrupos() {
		return grupoDao.obtenerGrupos(conexion);
	}

	@Override
	public Grupo obtenerGrupoPorNombre(String nombreGrupo)
			throws InstanceNotFoundException {
		return grupoDao.obtenerGrupoPorNombre(conexion, nombreGrupo);
	}
}
