package es.udc.med.espectaculos.model.grupoevento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc3CcSqlGrupoEventoDao extends AbstractSqlGrupoEventoDao {

	@Override
	public GrupoEvento create(Connection conexion, GrupoEvento grupoEvento) {
		
		String queryString = "INSERT INTO GRUPO_EVENTO (FECHA_ACTUACION, ID_EVENTO, ID_GRUPO) VALUES (?, ?, ?)";
		
		try(PreparedStatement preparedStatement = conexion.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, grupoEvento
					.getFechaActuacion());
			preparedStatement.setLong(i++, grupoEvento.getIdEvento());
			preparedStatement.setLong(i++, grupoEvento.getIdGrupo());
			
			int filasInsertadas = preparedStatement.executeUpdate();
			
			if (filasInsertadas == 0)
				throw new SQLException("No se puede asignar el Grupo al Evento");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla GrupoEvento");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Integer idGrupoEvento = resultados.getInt(1);
			grupoEvento.setIdGrupoEvento(idGrupoEvento);

			return grupoEvento;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
