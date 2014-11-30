package es.udc.med.espectaculos.grupo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.grupoevento.GrupoEvento;
import es.udc.med.espectaculos.model.musico.Jdbc3CcSqlMusicoDao;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musico.MusicoDao;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupo;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;

public class MusicoGrupoServiceTest {
	
	private static MusicoGrupoService musicoGrupoService = null;
	private static EventoService eventService = null;
	
	@BeforeClass
	public static void init() {
		musicoGrupoService = new MusicoGrupoServiceImpl();
		eventService = new EventoServiceImpl();
	}
	
	private Musico createMusico(String nombre) throws InputValidationException {
		return musicoGrupoService.crearMusico(nombre, "Calle Venecia", "Bateria");
	}
	
	private Grupo createGrupo(String nombre) {
		return eventService.crearGrupo(nombre, 3000);
	}
	

	// Caso exitoso en el que se crea un musico y se busca por nombre
	@Test
	public void createAndFindMusico() throws InstanceNotFoundException,
			InputValidationException {

		Musico musico = createMusico("Menganito");

		Musico musicoFound = musicoGrupoService.obtenerMusicoPorNombre("Menganito");

		assertEquals(musico.getIdMusico(), musicoFound.getIdMusico());
		assertEquals(musico.getNombreMusico(), musicoFound.getNombreMusico());
		assertEquals(musico.getDireccion(), musicoFound.getDireccion());
		assertEquals(musico.getInstrumento(), musicoFound.getInstrumento());

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	/******** Probamos a crear musicos con datos nulos o vacios ********/
	
	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullName() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico(null, "Calle Venecia", "Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyName() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico("", "Calle Venecia", "Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullDirection() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", null, "Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyDirection() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", "", "Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullInstrument() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", "Calle Venecia", null);

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyInstrument() throws InputValidationException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", "Calle Venecia", "");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}
	
	// Buscamos un musico que no existe
	@Test(expected = InstanceNotFoundException.class)
	public void findNonExistentMusico() throws InstanceNotFoundException {
		musicoGrupoService.obtenerMusicoPorNombre("NoExisto :( ");
	}
	
	//Probamos caso exitoso de asignar musico a grupo
	@Test
	public void asignarMusicoGrupoTest() throws InputValidationException,
			MusicoAsignadoException {
		Grupo grupo = createGrupo("Grupo Test");
		Musico musico = createMusico("Menganito");
		
		MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico, grupo);
		assertNotNull(musicoGrupo);
		assertEquals(musico.getIdMusico(), musicoGrupo.getIdMusico());
		assertEquals(grupo.getIdGrupo(), musicoGrupo.getIdGrupo());

		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
			eventService.borrarGrupo(grupo.getIdGrupo());
			musicoGrupoService.borrarMusicoGrupo(musicoGrupo.getIdMusicoGrupo());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	//Probamos que no se pueda insertar un miembro que ya este dentro del grupo
	@Test(expected = MusicoAsignadoException.class)
	public void asignarMusicoRepetidoTest() throws InputValidationException,
			MusicoAsignadoException {
		Grupo grupo = createGrupo("Grupo Test");
		Musico musico = createMusico("Menganito");
			
		MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico, grupo);
			
		MusicoGrupo musicoGrupo2 = musicoGrupoService.asignarMusicoGrupo(musico, grupo);
	}
		
	//Probamos que no se pueda insertar un musico nulo
	@Test(expected = MusicoAsignadoException.class)
	public void asignarMusicoNuloTest() throws InputValidationException,
			MusicoAsignadoException {
		Grupo grupo = createGrupo("Grupo Test");
		Musico musico = null;
			
		MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico, grupo);
		
		try {
			eventService.borrarGrupo(grupo.getIdGrupo());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Probamos que no se pueda insertar en un grupo nulo
	@Test(expected = MusicoAsignadoException.class)
	public void asignarGrupoNuloTest() throws InputValidationException,
			MusicoAsignadoException {
		Grupo grupo = null;
		Musico musico = createMusico("Menganito");
			
		MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico, grupo);
		
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

}
