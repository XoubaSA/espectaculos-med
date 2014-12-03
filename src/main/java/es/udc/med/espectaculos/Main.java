package es.udc.med.espectaculos;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import es.udc.med.espectaculos.utils.ConexionManager;
import es.udc.med.espectaculos.utils.GeneradorBD;
import es.udc.med.espectaculos.vista.VentanaPrincipal;

/**
 * Hello world!
 * 
 * 
 */
public class Main {

	public static void main(String[] args) {

		Connection conn = null;

		try {
			File f = new File("espectaculos.db");
			if (!f.exists()) {
				conn = ConexionManager.getConnection();
				GeneradorBD.crearBaseDeDatos(conn,
						"src/main/resources/CrearTablas.sql");
				conn.commit();

				GeneradorBD.insertarDatosPrueba(conn,
						"src/main/resources/InsertarDatos.sql");
				conn.commit();
				
			}

			VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
			ventanaPrincipal.open();
		} catch (SQLException e) {
			e.printStackTrace();			
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
