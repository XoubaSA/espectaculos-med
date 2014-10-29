package es.udc.med.espectaculos.model.grupotransporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Jdbc3CcSqlGrupoTransporteDao extends AbstractSqlGrupoTransporteDao {

	@Override
	public GrupoTransporte create(Connection conexion,
			GrupoTransporte grupoTransporte) {
		
		String queryString = "INSERT INTO GRUPO_TRANSPORTE"
				+ " (ID_GRUPO, ID_TRANSPORTE, FECHA_INICIO, FECHA_FIN)"
				+ " VALUES (?, ?, ?, ?)";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setLong(i++, grupoTransporte.getIdGrupo());
			preparedStatement.setLong(i++, grupoTransporte.getIdTransporte());
			preparedStatement.setTimestamp(i++, new Timestamp(grupoTransporte
					.getFechaInicio().getTime().getTime()));
			preparedStatement.setTimestamp(i++, new Timestamp(grupoTransporte
					.getFechaFin().getTime().getTime()));

			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException(
						"No se puede asignar el Grupo al Transporte");

			if (filasInsertadas > 1)
				throw new SQLException(
						"Filas duplicadas en la tabla GrupoTransporte");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Long idGrupoTransporte = resultados.getLong(1);
			grupoTransporte.setIdGrupoTransporte(idGrupoTransporte);

			return grupoTransporte;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
