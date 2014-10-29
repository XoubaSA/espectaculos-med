package es.udc.med.espectaculos.model.grupotransporte;

import java.sql.Connection;


public interface GrupoTransporteDao {
	
	public GrupoTransporte create (Connection conexion, GrupoTransporte grupoTransporte);

}
