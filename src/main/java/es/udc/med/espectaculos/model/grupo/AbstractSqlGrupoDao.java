package es.udc.med.espectaculos.model.grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlGrupoDao implements GrupoDao{

	protected AbstractSqlGrupoDao() {
	}
	
	@Override
	public List<Grupo> getGrupos(Connection connection) {

		/* Create "queryString". */
		String queryString = "SELECT ID_GRUPO, NOMBRE_ORQUESTA, SALARIO_ACTUACION"
				+ " FROM GRUPO";

		try (PreparedStatement preparedStatement = connection
				.prepareStatement(queryString)) {

			/* Execute query. */
			ResultSet resultSet = preparedStatement.executeQuery();

			/* Read eventos. */
			List<Grupo> grupos = new ArrayList<Grupo>();

			while (resultSet.next()) {

				int i = 1;
				Integer grupoId = new Integer(resultSet.getInt(i++));
				String nombre = resultSet.getString(i++);
				float salario = resultSet.getFloat(i++);

				grupos.add(new Grupo(grupoId, nombre, salario));

			}

			/* Return eventos. */
			return grupos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void update(Connection connection, Grupo grupo)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "UPDATE GRUPO"
                + " SET NOMBRE_ORQUESTA = ?, SALARIO_ACTUACION = ?,"
                + " WHERE ID_GRUPO = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, grupo.getNombreOrquesta());
            preparedStatement.setFloat(i++, grupo.getSalarioActuacion());
            preparedStatement.setInt(i++, grupo.getIdGrupo());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(grupo.getIdGrupo(),
                        Grupo.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public void remove(Connection connection, Integer IdGrupo)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "DELETE FROM GRUPO WHERE" + " ID_GRUPO = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setInt(i++, IdGrupo);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(IdGrupo,
                        Grupo.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public List<Grupo> obtenerGrupos(Connection conexion) {
		List<Grupo> grupos = new ArrayList<Grupo>();
		String queryString = "SELECT ID_GRUPO, NOMBRE_ORQUESTA, SALARIO_ACTUACION FROM GRUPO";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {

			ResultSet resultados = preparedStatement.executeQuery();
			while (resultados.next()) {
				int i = 1;
				Integer idGrupo = resultados.getInt(i++);
				String nombre = resultados.getString(i++);
				float salario = resultados.getFloat(i++);

				grupos.add(new Grupo(idGrupo, nombre, salario));
			}
			return grupos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Grupo obtenerGrupoPorNombre(Connection conexion, String nombreGrupo) throws InstanceNotFoundException {
		String queryString = "SELECT ID_GRUPO, NOMBRE_ORQUESTA, SALARIO_ACTUACION FROM GRUPO WHERE NOMBRE_ORQUESTA = ?";

		try (PreparedStatement preparedStatement = conexion
				.prepareStatement(queryString)) {

			int i = 1;
			preparedStatement.setString(i++, nombreGrupo);

			ResultSet resultados = preparedStatement.executeQuery();
			if (!resultados.next())
				throw new InstanceNotFoundException(nombreGrupo,
						Grupo.class.getName());

			i = 1;
			Integer idGrupo = resultados.getInt(i++);
			String nombre = resultados.getString(i++);		
			Float salario = resultados.getFloat(i++);

			return new Grupo(idGrupo, nombre, salario);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
