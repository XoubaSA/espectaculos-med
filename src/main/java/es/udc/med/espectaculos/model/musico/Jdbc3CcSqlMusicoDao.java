package es.udc.med.espectaculos.model.musico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc3CcSqlMusicoDao extends AbstractSqlMusicoDao {
	
	@Override
	public Musico create(Connection connection, Musico musico) {

		String queryString = "INSERT INTO MUSICO (NOMBRE_MUSICO, DIRECCION, INSTRUMENTO) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, musico.getNombreMusico());
			preparedStatement.setString(i++, musico.getDireccion());
			preparedStatement.setString(i++, musico.getInstrumento());
			
			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException("No se puede crear el Musico");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla Musico");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Long idMusico = resultados.getLong(1);
			musico.setIdMusico(idMusico);
			
			return musico;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
