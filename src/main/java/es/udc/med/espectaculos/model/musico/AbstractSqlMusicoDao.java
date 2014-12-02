package es.udc.med.espectaculos.model.musico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlMusicoDao implements MusicoDao {

	protected AbstractSqlMusicoDao() {
	}

	@Override
	public List<Musico> getMusicos(Connection connection) {

		/* Create "queryString". */
		String queryString = "SELECT ID_MUSICO, NOMBRE_MUSICO, DIRECCION, INSTRUMENTO"
				+ " FROM MUSICO";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read eventos. */
			List<Musico> musicos = new ArrayList<Musico>();

			while (resultSet.next()) {

				int i = 1;
				Integer musicoId = new Integer(resultSet.getInt(i++));
				String nombre = resultSet.getString(i++);
				String direccion = resultSet.getString(i++);
				String instrumento = resultSet.getString(i++);

				musicos.add(new Musico(musicoId, nombre, direccion, instrumento));

			}

			/* Return eventos. */
			return musicos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void update(Connection connection, Musico musico)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "UPDATE MUSICO"
				+ " SET NOMBRE_MUSICO = ?, DIRECCION = ?, INSTRUMENTO = ?"
				+ " WHERE ID_MUSICO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setString(i++, musico.getNombreMusico());
			preparedStatement.setString(i++, musico.getDireccion());
			preparedStatement.setString(i++, musico.getInstrumento());
			preparedStatement.setInt(i++, musico.getIdMusico());

			/* Execute query. */
			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(musico.getIdMusico(),
						Musico.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void remove(Connection connection, Integer IdMusico)
			throws InstanceNotFoundException {

		/* Create "queryString". */
		String queryString = "DELETE FROM MUSICO WHERE" + " ID_MUSICO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setInt(i++, IdMusico);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(IdMusico,
						Musico.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Musico obtenerMusicoPorNombre(Connection conexion,
			String nombreMusico) throws InstanceNotFoundException {
		String queryString = "SELECT ID_MUSICO, NOMBRE_MUSICO, DIRECCION, INSTRUMENTO FROM MUSICO WHERE NOMBRE_MUSICO LIKE ?";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {

			int i = 1;
			preparedStatement.setString(i++, nombreMusico);

			ResultSet resultados = preparedStatement.executeQuery();
			if (!resultados.next())
				throw new InstanceNotFoundException(nombreMusico,
						Musico.class.getName());

			i = 1;
			Integer idMusico = resultados.getInt(i++);
			String nombre = resultados.getString(i++);
			String direccion = resultados.getString(i++);
			String instrumento = resultados.getString(i++);
			
			
			return new Musico(idMusico, nombre, direccion, instrumento);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
