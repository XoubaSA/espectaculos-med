package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlMusicoGrupoDao implements MusicoGrupoDao{

	@Override
	public void remove(Connection connection, Integer idMusicoGrupo) throws InstanceNotFoundException{
		/* Create "queryString". */
		String queryString = "DELETE FROM MUSICO_GRUPO WHERE"
				+ " ID_MUSICO_GRUPO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setInt(i++, idMusicoGrupo);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(idMusicoGrupo,
						MusicoGrupo.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean musicoAsignadoGrupo(Connection conexion, Grupo grupo, Musico musico) {

		String queryString = "SELECT count(*)"
				+ " FROM MUSICO_GRUPO mg"
				+ " WHERE (mg.ID_GRUPO = ?)"
				+ " AND (mg.ID_MUSICO LIKE ?) ";
		
		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, grupo.getIdGrupo());
			preparedStatement.setInt(i++, musico.getIdMusico());
			
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
	public void removeMember(Connection connection, Integer idGrupo, Integer idMusico) throws InstanceNotFoundException {
		/* Create "queryString". */
		String queryString = "DELETE FROM MUSICO_GRUPO WHERE"
				+ " ID_GRUPO = ?"
				+ " AND ID_MUSICO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Fill "preparedStatement". */
			int i = 1;
			preparedStatement.setInt(i++, idGrupo);
			preparedStatement.setInt(i++, idMusico);

			/* Execute query. */
			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(idMusico,
						MusicoGrupo.class.getName());
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Musico> getFormacion(Connection connection, Grupo grupo) {
		List<Musico> musicos = new ArrayList<Musico>();

		String queryString = "SELECT m.ID_MUSICO, m.NOMBRE_MUSICO, m.DIRECCION, m.INSTRUMENTO "
				+ "FROM MUSICO m JOIN MUSICO_GRUPO mg ON m.ID_MUSICO = mg.ID_MUSICO "
				+ "WHERE mg.ID_GRUPO = ?";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, grupo.getIdGrupo());

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				i = 1;
				Integer id = resultSet.getInt(i++);
				String nombre = resultSet.getString(i++);
				String direccion = resultSet.getString(i++);
				String instrumento = resultSet.getString(i++);

				musicos.add(new Musico(id, nombre, direccion, instrumento));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return musicos;
	}
	
}
