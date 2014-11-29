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
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;

public class MusicoGrupoServiceImpl implements MusicoGrupoService {

	private GrupoDao grupoDao = new Jdbc3CcSqlGrupoDao();
	private MusicoDao musicoDao = new Jdbc3CcSqlMusicoDao();
	private MusicoGrupoDao musicoGrupoDao = new Jdbc3CcSqlMusicoGrupoDao();
	private Connection conexion = ConexionManager.getConnection();

	
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

		MusicoGrupo musicoGrupo = new MusicoGrupo(musico.getIdMusico(), grupo.getIdGrupo());
		return musicoGrupoDao.create(conexion, musicoGrupo);
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
		return musicoDao.getMusicos(conexion);
	}
}
