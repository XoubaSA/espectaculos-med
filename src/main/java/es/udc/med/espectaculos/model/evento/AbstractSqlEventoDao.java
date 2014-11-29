package es.udc.med.espectaculos.model.evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlEventoDao implements EventoDao {

	protected AbstractSqlEventoDao() {
	}

	@Override
	public List<Evento> getEventoByDate(Connection connection, Calendar fecha)
			throws InstanceNotFoundException {

		String strDate = ConvertidorFechas.convertirCalendarString(fecha);

		/* Create "queryString". */
		String queryString = "SELECT DISTINCT ID_EVENTO, NOMBRE_EVENTO, FECHA_INICIO_EVENTO,"
				+ " LOCALIDAD FROM EVENTO WHERE FECHA_INICIO_EVENTO = ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			int i = 1;
			preparedStatement.setString(i++, strDate);
			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read eventos. */
			List<Evento> eventos = new ArrayList<Evento>();

			while (resultSet.next()) {

				int j = 1;
				Integer eventoId = new Integer(resultSet.getInt(j++));
				String nombre = resultSet.getString(j++);
				Calendar fechaInicioEvento = ConvertidorFechas
						.convertirStringCalendar(resultSet.getString(j++));
				String localidad = resultSet.getString(j++);

				eventos.add(new Evento(eventoId, nombre, fechaInicioEvento,
						localidad));
			}

			/* Return eventos. */
			return eventos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(Connection connection, Evento evento)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "UPDATE EVENTO"
				+ " SET NOMBRE_EVENTO = ?, FECHA_INICIO_EVENTO = ?,"
				+ " LOCALIDAD = ?" + " WHERE ID_EVENTO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, evento.getNombreEvento());
			preparedStatement.setString(i++, ConvertidorFechas
					.convertirCalendarString(evento.getFechaInicioEvento()));
			preparedStatement.setString(i++, evento.getLocalidad());
			preparedStatement.setLong(i++, evento.getIdEvento());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(evento.getIdEvento(),
						Evento.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, Integer idEvento)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM EVENTO WHERE" + " ID_EVENTO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setInt(i++, idEvento);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(idEvento,
						Evento.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Evento buscarEventoPorNombre(Connection conexion, String nombreEvento)
			throws InstanceNotFoundException {

		String queryString = "SELECT ID_EVENTO, NOMBRE_EVENTO, FECHA_INICIO_EVENTO, LOCALIDAD FROM EVENTO WHERE NOMBRE_EVENTO = ?";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {

			int i = 1;
			preparedStatement.setString(i++, nombreEvento);

			ResultSet resultados = preparedStatement.executeQuery();
			if (!resultados.next())
				throw new InstanceNotFoundException(nombreEvento,
						Evento.class.getName());

			i = 1;
			Integer idEvento = resultados.getInt(i++);
			String nombre = resultados.getString(i++);
			Calendar fechaInicioEvento = ConvertidorFechas
					.convertirStringCalendar(resultados.getString(i++));
			String localidad = resultados.getString(i++);

			return new Evento(idEvento, nombre, fechaInicioEvento, localidad);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Evento> findAllEvents(Connection conexion) {

		List<Evento> eventos = new ArrayList<Evento>();
		String queryString = "SELECT ID_EVENTO, NOMBRE_EVENTO, FECHA_INICIO_EVENTO, LOCALIDAD FROM EVENTO";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {

			ResultSet resultados = preparedStatement.executeQuery();
			while (resultados.next()) {
				int i = 1;
				Integer idEvento = resultados.getInt(i++);
				String nombre = resultados.getString(i++);
				Calendar fechaInicioEvento = ConvertidorFechas
						.convertirStringCalendar(resultados.getString(i++));
				String localidad = resultados.getString(i++);

				eventos.add(new Evento(idEvento, nombre, fechaInicioEvento,
						localidad));
			}
			return eventos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
