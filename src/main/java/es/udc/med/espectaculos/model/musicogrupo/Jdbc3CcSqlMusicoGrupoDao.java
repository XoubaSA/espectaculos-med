package es.udc.med.espectaculos.model.musicogrupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc3CcSqlMusicoGrupoDao extends AbstractSqlMusicoGrupoDao {

	@Override
	public MusicoGrupo create(Connection conexion, MusicoGrupo musicoGrupo) {
String queryString = "INSERT INTO MUSICO_GRUPO (ID_MUSICO, ID_GRUPO) VALUES (?, ?)";
		
		try(PreparedStatement preparedStatement = conexion.prepareStatement(queryString)) {
			int i = 1;
			preparedStatement.setInt(i++, musicoGrupo.getIdMusico());
			preparedStatement.setInt(i++, musicoGrupo.getIdGrupo());
			
			int filasInsertadas = preparedStatement.executeUpdate();
			
			if (filasInsertadas == 0)
				throw new SQLException("No se puede asignar el Musico al Grupo");

			if (filasInsertadas > 1)
				throw new SQLException("Filas duplicadas en la tabla MusicoGrupo");

			ResultSet resultados = preparedStatement.getGeneratedKeys();
			if (!resultados.next())
				throw new SQLException("JDBC no ha devuelto ninguna clave");
			Integer idMusicoGrupo = resultados.getInt(1);
			musicoGrupo.setIdMusicoGrupo(idMusicoGrupo);

			return musicoGrupo;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
