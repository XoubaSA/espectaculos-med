package es.udc.med.espectaculos.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneradorBD {	
	
	public static void crearBaseDeDatos(Connection conexion, String rutaFichero) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					rutaFichero));
			String linea = null;
			String sentencia = "";
			List<String> sentencias = new ArrayList<String>();
			while ((linea = reader.readLine()) != null) {
				sentencia += linea;
				if (linea.endsWith(";")) {
					sentencias.add(sentencia);
					sentencia = "";
				}
			}
			reader.close();

			for (String s : sentencias) {
				conexion.createStatement().executeUpdate(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		}

	}
	
	public static void insertarDatosPrueba(Connection conexion, String rutaFichero) {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					rutaFichero));
			String linea = null;
			String sentencia = "";
			List<String> sentencias = new ArrayList<String>();
			while ((linea = reader.readLine()) != null) {
				sentencia += linea;
				if (linea.endsWith(";")) {
					sentencias.add(sentencia);
					sentencia = "";
				}
			}
			reader.close();

			for (String s : sentencias) {
				conexion.createStatement().executeUpdate(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		
	}

}
