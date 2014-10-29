package es.udc.med.espectaculos.model.grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc3CcSqlGrupoDao extends AbstractSqlGrupoDao{

	@Override
	public Grupo create(Connection connection, Grupo grupo) {

		String queryString = "INSERT INTO GRUPO (NOMBRE_ORQUESTA, SALARIO_ACTUACION) VALUES (?, ?)";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, grupo.getNombreOrquesta());
			preparedStatement.setFloat(i++, grupo.getSalarioActuacion());

			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException("No se puede crear el Grupo");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla Grupo");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Integer idGrupo = resultados.getInt(1);
			grupo.setIdGrupo(idGrupo);
			
			return grupo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
}
