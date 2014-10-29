package es.udc.med.espectaculos.model.musico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlMusicoDao implements MusicoDao{

	protected AbstractSqlMusicoDao() {
	}
	
	@Override
	public void update(Connection connection, Musico musico)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "UPDATE MUSICO"
                + " SET NOMBRE_MUSICO = ?, DIRECCION = ?, INSTRUMENTO = ?"
                + " WHERE ID_MUSICO = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, musico.getNombreMusico());
            preparedStatement.setString(i++, musico.getDireccion());
            preparedStatement.setString(i++, musico.getInstrumento());
            preparedStatement.setLong(i++, musico.getIdMusico());

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
	public void remove(Connection connection, Long IdMusico)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "DELETE FROM MUSICO WHERE" + " ID_MUSICO = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, IdMusico);

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
	
}
