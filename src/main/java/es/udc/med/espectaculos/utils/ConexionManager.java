package es.udc.med.espectaculos.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionManager {

	public static Connection conexion = null;

	public static Connection getConnection() {

		if (conexion == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conexion = DriverManager
						.getConnection("jdbc:sqlite:espectaculos.db");
				conexion.setAutoCommit(false);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return conexion;
	}

}
