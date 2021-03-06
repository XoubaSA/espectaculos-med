package es.udc.med.espectaculos.model.grupoevento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.management.Query;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlGrupoEventoDao implements GrupoEventoDao {

	public void remove(Connection connection, Integer idGrupoEvento)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM GRUPO_EVENTO WHERE"
				+ " ID_GRUPO_EVENTO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setLong(i++, idGrupoEvento);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(idGrupoEvento,
						GrupoEvento.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Calendar> getDates(Connection connection, String mes,
			Integer idGrupo) {

		/* Create "queryString". */
		String queryString = "SELECT FECHA_ACTUACION, FROM EVENTO "
				+ "WHERE ID_GRUPO = ? AND FECHA_ACTUACION LIKE ?";
		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			int i = 1;
			preparedStatement.setInt(i++, idGrupo);
			String mesActuacion = "_____" + mes + "%";
			preparedStatement.setString(i++, mesActuacion);

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read eventos. */
			List<Calendar> fechas = new ArrayList<>();

			while (resultSet.next()) {

				int j = 1;
				Calendar fecha = Calendar.getInstance();
				fecha.setTime(resultSet.getTimestamp(j++));

				fechas.add(fecha);
			}

			/* Return eventos. */
			return fechas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Evento> obtenerEventosGrupoFecha(Connection conexion,
			Grupo grupo, Calendar fechaInicio, Calendar fechaFin) {

		List<Evento> eventos = new ArrayList<Evento>();
		String strDateIni = ConvertidorFechas
				.convertirCalendarString(fechaInicio);
		String strDateFin = ConvertidorFechas.convertirCalendarString(fechaFin);

		String queryString = "SELECT e.ID_EVENTO, e.NOMBRE_EVENTO, e.FECHA_INICIO_EVENTO, e.LOCALIDAD "
				+ "FROM EVENTO e JOIN GRUPO_EVENTO g ON g.ID_EVENTO = e.ID_EVENTO "
				+ "WHERE g.ID_GRUPO = ? AND e.FECHA_INICIO_EVENTO >= ? AND e.FECHA_INICIO_EVENTO <= ?";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, grupo.getIdGrupo());
			preparedStatement.setString(i++, strDateIni);
			preparedStatement.setString(i++, strDateFin);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				i = 1;
				Integer id = resultSet.getInt(i++);
				String nombre = resultSet.getString(i++);
				String fecha = resultSet.getString(i++);
				Calendar calendar = ConvertidorFechas
						.convertirStringCalendar(fecha);
				String localidad = resultSet.getString(i++);
				Evento evento = new Evento(id, nombre, calendar, localidad);
				eventos.add(evento);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return eventos;
	}

	@Override
	public List<Evento> obtenerEventosGrupoDia(Connection conexion,
			Grupo grupo, Calendar dia) {

		List<Evento> eventos = new ArrayList<Evento>();
		String strDateIni = ConvertidorFechas.convertirCalendarString(dia);

		String queryString = "SELECT e.ID_EVENTO, e.NOMBRE_EVENTO, e.FECHA_INICIO_EVENTO, e.LOCALIDAD "
				+ "FROM EVENTO e JOIN GRUPO_EVENTO g ON g.ID_EVENTO = e.ID_EVENTO "
				+ "WHERE g.ID_GRUPO = ? AND e.FECHA_INICIO_EVENTO = ?";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, grupo.getIdGrupo());
			preparedStatement.setString(i++, strDateIni);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				i = 1;
				Integer id = resultSet.getInt(i++);
				String nombre = resultSet.getString(i++);
				String fecha = resultSet.getString(i++);
				Calendar calendar = ConvertidorFechas
						.convertirStringCalendar(fecha);
				String localidad = resultSet.getString(i++);
				Evento evento = new Evento(id, nombre, calendar, localidad);
				eventos.add(evento);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return eventos;
	}

	public List<Grupo> obtenerGruposEvento(Connection conexion, Evento evento) {

		String queryString = "SELECT g.ID_GRUPO, g.NOMBRE_ORQUESTA, g.SALARIO_ACTUACION "
				+ "FROM GRUPO_EVENTO ge INNER JOIN GRUPO g ON ge.ID_GRUPO = g.ID_GRUPO "
				+ "WHERE ge.ID_EVENTO = ?";

		List<Grupo> grupos = new ArrayList<Grupo>();

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, evento.getIdEvento());

			ResultSet rs = preparedStatement.executeQuery();
			int results = 0;
			while (rs.next()) {
				i = 1;
				int id = rs.getInt(i++);
				String nombre = rs.getString(i++);
				float salario = rs.getFloat(i++);
				Grupo grupo = new Grupo(id, nombre, salario);
				grupos.add(grupo);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return grupos;
	}

	public boolean grupoAsignadoFecha(Connection conexion, Grupo grupo,
			String fecha) {

		String fechaSoloDia = fecha.substring(0, 10);

		String queryString = "SELECT count(*)" + " FROM GRUPO_EVENTO ge"
				+ " WHERE (ge.ID_GRUPO = ?)"
				+ " AND (substr(ge.FECHA_ACTUACION,1,10) LIKE ?) ";
		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, grupo.getIdGrupo());
			preparedStatement.setString(i++, fechaSoloDia);

			ResultSet rs = preparedStatement.executeQuery();
			int results = 0;
			while (rs.next()) {
				results = rs.getInt(1);
			}

			if (results == 0)
				return false;
			else
				return true;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public GrupoEvento obtenerGrupoEvento(Connection conexion, Grupo grupo, Evento evento) {

		String queryString = "SELECT ge.ID_GRUPO_EVENTO "
				+ "FROM GRUPo_EVENTO ge WHERE ge.ID_EVENTO = ? AND ge.ID_GRUPO = ?";		

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, evento.getIdEvento());
			preparedStatement.setInt(i++, grupo.getIdGrupo());

			ResultSet rs = preparedStatement.executeQuery();
			int results = 0;
			while (rs.next()) {
				i = 1;
				int id = rs.getInt(i++);
				GrupoEvento grupoEvento = new GrupoEvento();
				grupoEvento.setIdGrupoEvento(id);
				return grupoEvento;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		return null;
	}

}
