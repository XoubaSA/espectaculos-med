package es.udc.med.espectaculos.model.evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import es.udc.med.espectaculos.utils.ConvertidorFechas;

public class Jdbc3CcSqlEventoDao extends AbstractSqlEventoDao {

	public Evento create(Connection connection, Evento evento) {

		String queryString = "INSERT INTO EVENTO (NOMBRE_EVENTO, FECHA_INICIO_EVENTO, LOCALIDAD) VALUES (?, ?, ?)";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setString(i++, evento.getNombreEvento());
			preparedStatement.setString(i++, ConvertidorFechas.convertirCalendarString(evento.getFechaInicioEvento()));
			preparedStatement.setString(i++, evento.getLocalidad());

			int filasInsertadas = preparedStatement.executeUpdate();

			if (filasInsertadas == 0)
				throw new SQLException("No se puede crear el Evento");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla Evento");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Integer idEvento = resultados.getInt(1);
			evento.setIdEvento(idEvento);

			return evento;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
