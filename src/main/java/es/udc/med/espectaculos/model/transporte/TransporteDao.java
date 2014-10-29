package es.udc.med.espectaculos.model.transporte;

import java.sql.Connection;
import java.sql.SQLException;

import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public interface TransporteDao {

	public void update(Connection connection, Transporte transporte)
			throws InstanceNotFoundException;

	public void remove(Connection connection, Long transporteId)
			throws InstanceNotFoundException;

	public Transporte create(Connection connection, Transporte transporte) 
			throws SQLException;
	
}
