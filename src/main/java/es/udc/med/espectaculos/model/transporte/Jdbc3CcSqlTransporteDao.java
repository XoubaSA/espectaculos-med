package es.udc.med.espectaculos.model.transporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc3CcSqlTransporteDao extends AbstractSqlTransporteDao{

	@Override
	public Transporte create(Connection connection, Transporte transporte) {

		String queryString = "INSERT INTO TRANSPORTE (NOMBRE_TRANSPORTE, MATRICULA, DESCRIPCION) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, transporte.getNombreTransporte());
			preparedStatement.setString(i++, transporte.getMatricula());
			preparedStatement.setString(i++, transporte.getDescripcion());
			
			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException("No se puede crear el Transporte");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla Transporte");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Long idTransporte = resultados.getLong(1);
			transporte.setIdTransporte(idTransporte);
			
			return transporte;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
}
