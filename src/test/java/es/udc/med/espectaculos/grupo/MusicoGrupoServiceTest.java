package es.udc.med.espectaculos.grupo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musicogrupo.MusicoGrupo;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.GrupoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;
import es.udc.med.espectaculos.utils.MusicoAsignadoException;
import es.udc.med.espectaculos.utils.MusicoExisteException;

public class MusicoGrupoServiceTest {

	private static MusicoGrupoService musicoGrupoService = null;	

	@BeforeClass
	public static void init() {
		musicoGrupoService = new MusicoGrupoServiceImpl();		
	}

	public static void cleanAfterException(Musico musico, Grupo grupo,
			MusicoGrupo musicoGrupo) {
		try {
			if (musico != null)
				musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
		try {
			if (grupo != null)
				musicoGrupoService.borrarGrupo(grupo.getIdGrupo());
		} catch (InstanceNotFoundException e) {
		}
		try {
			if (musicoGrupo != null)
				musicoGrupoService.borrarMusicoGrupo(musicoGrupo
						.getIdMusicoGrupo());
		} catch (InstanceNotFoundException e) {
		}
	}

	private Musico createMusico(String nombre) throws InputValidationException,
			MusicoExisteException {
		return musicoGrupoService.crearMusico(nombre, "Calle Venecia",
				"Bateria");
	}

	private Grupo createGrupo(String nombre) throws InputValidationException,
			GrupoExisteException {
		return musicoGrupoService.crearGrupo(nombre, 3000);
	}

	// Caso exitoso en el que se crea un musico y se busca por nombre
	@Test
	public void createAndFindMusico() throws InstanceNotFoundException,
			InputValidationException, MusicoExisteException {

		Musico musico = createMusico("Menganito");

		Musico musicoFound = musicoGrupoService
				.obtenerMusicoPorNombre("Menganito");

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

	/********
	 * Probamos a crear musicos con datos nulos o vacios
	 * 
	 * @throws GrupoExisteException
	 ********/

	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullName() throws InputValidationException,
			MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico(null, "Calle Venecia",
				"Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyName() throws InputValidationException,
			MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico("", "Calle Venecia",
				"Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullDirection()
			throws InputValidationException, MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", null,
				"Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyDirection()
			throws InputValidationException, MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico("Menganito", "",
				"Bateria");

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithNullInstrument()
			throws InputValidationException, MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico("Menganito",
				"Calle Venecia", null);

		// Clean data base
		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createMusicoWithEmptyInstrument()
			throws InputValidationException, MusicoExisteException {
		Musico musico = musicoGrupoService.crearMusico("Menganito",
				"Calle Venecia", "");

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

	// Probamos caso exitoso de asignar musico a grupo
	@Test
	public void asignarMusicoGrupoTest() throws InputValidationException,
			MusicoAsignadoException, GrupoExisteException,
			MusicoExisteException {
		Grupo grupo = createGrupo("Grupo Test");
		Musico musico = createMusico("Menganito");

		MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico,
				grupo);
		assertNotNull(musicoGrupo);
		assertEquals(musico.getIdMusico(), musicoGrupo.getIdMusico());
		assertEquals(grupo.getIdGrupo(), musicoGrupo.getIdGrupo());

		try {
			musicoGrupoService.borrarMusico(musico.getIdMusico());
			musicoGrupoService.borrarGrupo(grupo.getIdGrupo());
			musicoGrupoService
					.borrarMusicoGrupo(musicoGrupo.getIdMusicoGrupo());
		} catch (InstanceNotFoundException e) {
		}

	}

	// Probamos que no se pueda insertar un miembro que ya este dentro del grupo
	@Test(expected = MusicoAsignadoException.class)
	public void asignarMusicoRepetidoTest() throws InputValidationException,
			MusicoAsignadoException, GrupoExisteException,
			MusicoExisteException {
		Grupo grupo = null;
		Musico musico = null;
		MusicoGrupo musicoGrupo = null;

		try {
			grupo = createGrupo("Grupo Test");
			musico = createMusico("Menganito");

			musicoGrupo = musicoGrupoService.asignarMusicoGrupo(musico, grupo);

			MusicoGrupo musicoGrupo2 = musicoGrupoService.asignarMusicoGrupo(
					musico, grupo);
		} finally {
			cleanAfterException(musico, grupo, musicoGrupo);
		}
	}

	// Probamos que no se pueda insertar un musico nulo
	@Test(expected = InputValidationException.class)
	public void asignarMusicoNuloTest() throws InputValidationException,
			MusicoAsignadoException, GrupoExisteException {
		Grupo grupo = createGrupo("Grupo Test2");
		Musico musico = null;

		try {
			MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(
					musico, grupo);
		} finally {
			cleanAfterException(musico, grupo, null);
		}
	}

	// Probamos que no se pueda insertar en un grupo nulo
	@Test(expected = InputValidationException.class)
	public void asignarGrupoNuloTest() throws InputValidationException,
			MusicoAsignadoException, MusicoExisteException {
		Grupo grupo = null;
		Musico musico = createMusico("Menganito2");

		try {
			MusicoGrupo musicoGrupo = musicoGrupoService.asignarMusicoGrupo(
					musico, grupo);
		} finally {
			cleanAfterException(musico, grupo, null);
		}
	}

}
