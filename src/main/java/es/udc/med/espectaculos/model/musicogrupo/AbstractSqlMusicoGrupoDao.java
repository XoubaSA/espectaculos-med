package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlMusicoGrupoDao implements MusicoGrupoDao{

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
	
}
