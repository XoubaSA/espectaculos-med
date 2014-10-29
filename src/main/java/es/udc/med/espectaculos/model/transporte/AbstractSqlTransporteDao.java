package es.udc.med.espectaculos.model.transporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public abstract class AbstractSqlTransporteDao implements TransporteDao {

	protected AbstractSqlTransporteDao() {
	}
	
	@Override
	public void update(Connection connection, Transporte transporte)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "UPDATE TRANSPORTE"
                + " SET NOMBRE_TRANSPORTE = ?, MATRICULA = ?, DESCRIPCION = ?"
                + " WHERE ID_TRANSPORTE = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, transporte.getNombreTransporte());
            preparedStatement.setString(i++, transporte.getMatricula());
            preparedStatement.setString(i++, transporte.getDescripcion());
            preparedStatement.setLong(i++, transporte.getIdTransporte());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(transporte.getIdTransporte(),
                        Transporte.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}

	@Override
	public void remove(Connection connection, Long IdTransporte)
			throws InstanceNotFoundException {
		
		/* Create "queryString". */
        String queryString = "DELETE FROM TRANSPORTE WHERE" + " ID_TRANSPORTE = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, IdTransporte);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(IdTransporte,
                        Transporte.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

	}
	
}
