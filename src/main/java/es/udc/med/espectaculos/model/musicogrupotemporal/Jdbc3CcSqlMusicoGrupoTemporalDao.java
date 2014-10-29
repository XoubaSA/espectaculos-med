package es.udc.med.espectaculos.model.musicogrupotemporal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Jdbc3CcSqlMusicoGrupoTemporalDao extends
		AbstractSqlMusicoGrupoTemporalDao {

	@Override
	public MusicoGrupoTemporal create(Connection conexion,
			MusicoGrupoTemporal musicoGrupoTemporal) {

		String queryString = "INSERT INTO MUSICO_GRUPO_TEMPORAL"
				+ " (ID_MUSICO, ID_GRUPO, FECHA_INICIO, FECHA_FIN)"
				+ " VALUES (?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, musicoGrupoTemporal.getIdMusico());
			preparedStatement.setLong(i++, musicoGrupoTemporal.getIdGrupo());
			preparedStatement.setTimestamp(i++, new Timestamp(musicoGrupoTemporal
					.getFechaInicio().getTime().getTime()));
			preparedStatement.setTimestamp(i++, new Timestamp(musicoGrupoTemporal
					.getFechaFin().getTime().getTime()));

			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException(
						"No se puede asignar el Musico al Grupo temporal");

			if (filasInsertadas > 1)
				throw new SQLException(
						"Filas duplicadas en la tabla MusicoGrupoTemporal");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Long idMusicoGrupo = resultados.getLong(1);
			musicoGrupoTemporal.setIdMusicoGrupo(idMusicoGrupo);

			return musicoGrupoTemporal;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
