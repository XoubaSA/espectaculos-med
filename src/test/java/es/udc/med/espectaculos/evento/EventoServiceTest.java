package es.udc.med.espectaculos.evento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.evento.EventoDao;
import es.udc.med.espectaculos.model.evento.Jdbc3CcSqlEventoDao;
import es.udc.med.espectaculos.model.eventoservice.EventoService;
import es.udc.med.espectaculos.model.eventoservice.EventoServiceImpl;
import es.udc.med.espectaculos.model.grupo.Grupo;
import es.udc.med.espectaculos.model.grupo.GrupoDao;
import es.udc.med.espectaculos.model.grupo.Jdbc3CcSqlGrupoDao;
import es.udc.med.espectaculos.model.grupoevento.GrupoEvento;
import es.udc.med.espectaculos.model.grupoevento.GrupoEventoDao;
import es.udc.med.espectaculos.model.grupoevento.Jdbc3CcSqlGrupoEventoDao;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.EventoExisteException;
import es.udc.med.espectaculos.utils.GrupoExisteException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class EventoServiceTest {

	private static EventoService eventService = null;
	private static MusicoGrupoService musicoGrupoService = null;
	private static EventoDao eventoDao = null;
	private static GrupoDao grupoDao = null;
	private static GrupoEventoDao grupoEventoDao = null;

	@BeforeClass
	public static void init() {
		eventService = new EventoServiceImpl();
		musicoGrupoService = new MusicoGrupoServiceImpl();
		eventoDao = new Jdbc3CcSqlEventoDao();
		grupoDao = new Jdbc3CcSqlGrupoDao();
		grupoEventoDao = new Jdbc3CcSqlGrupoEventoDao();
	}

	@AfterClass
	public static void clean() {
		for (int i = 0; i < 10; i++) {
			try {
				Evento evento = eventService
						.obtenerEventoPorNombre("Fiestas A Laracha");
				eventService
						.borrarEvento(Integer.valueOf(evento.getIdEvento()));
			} catch (InstanceNotFoundException e) {
				i = 11;
			}
		}
	}

	public static void cleanAfterException(Evento evento, Grupo grupo,
			GrupoEvento grupoEvento) {
		try {
			if (evento != null)
				eventService.borrarEvento(evento.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
		try {
			if (grupo != null)
				musicoGrupoService.borrarGrupo(grupo.getIdGrupo());
		} catch (InstanceNotFoundException e) {
		}
		try {
			if (grupoEvento != null)
				eventService.borrarGrupoEvento(grupoEvento.getIdGrupoEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	private Evento createEventoInDate(Calendar fecha)
			throws InputValidationException, EventoExisteException {
		// lo del gettimeinmmillis es para evitarse problemas con la restriccion
		// unique
		return eventService.crearEvento(
				"Fiestas A Laracha " + fecha.getTimeInMillis(), fecha,
				"Calle Falsa 123");

	}

	private Evento createEvento() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.HOUR_OF_DAY, 0);
		fechaInicio.set(Calendar.MINUTE, 0);
		fechaInicio.set(Calendar.SECOND, 0);
		fechaInicio.set(Calendar.MILLISECOND, 0);
		return eventService.crearEvento("Fiestas A Laracha", fechaInicio,
				"Calle Falsa 123");
	}

	private Grupo createGrupoWithName(String nombre)
			throws InputValidationException, GrupoExisteException {
		return musicoGrupoService.crearGrupo(nombre, 3000);
	}

	/*
	 * Caso exitoso en el que se crea un evento y se busca por nombre
	 */
	@Test
	public void createAndFindEvent() throws InstanceNotFoundException,
			InputValidationException, EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MILLISECOND, 0);

		Evento event = createEvento();

		Evento eventFound = eventService
				.obtenerEventoPorNombre("Fiestas A Laracha");

		assertEquals(event.getIdEvento(), eventFound.getIdEvento());
		assertEquals(event.getFechaInicioEvento(),
				eventFound.getFechaInicioEvento());
		assertEquals(event.getNombreEvento(), eventFound.getNombreEvento());
		assertEquals(event.getLocalidad(), eventFound.getLocalidad());

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/********
	 * Probamos a crear eventos con nombre/localidad nulo/a o vacio/a
	 * 
	 * @throws EventoExisteException
	 ********/

	@Test(expected = InputValidationException.class)
	public void createEventWithNullName() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MILLISECOND, 0);
		Evento event = eventService.crearEvento(null, fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createEventWithEmptyName() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MILLISECOND, 0);
		Evento event = eventService.crearEvento("", fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createEventWithNullPlace() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MILLISECOND, 0);
		Evento event = eventService.crearEvento("Nombre evento", fechaInicio,
				null);

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createEventWithEmptyPlace() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MILLISECOND, 0);
		Evento event = eventService.crearEvento("Nombre evento", fechaInicio,
				"");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/**
	 * @throws EventoExisteException
	 ****************************************************************************/

	/*
	 * Probamos el caso de crear un evento con una fecha del mes pasado
	 */
	@Test(expected = InputValidationException.class)
	public void createEventWithInvalidMonthOfDate()
			throws InputValidationException, EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.MONTH, fechaInicio.get(Calendar.MONTH) - 1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/*
	 * Probamos el caso de crear un evento con una fecha del mismo mes, pero de
	 * un dia anterior
	 */
	@Test(expected = InputValidationException.class)
	public void createEventWithInvalidDayOfDate()
			throws InputValidationException, EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.DAY_OF_MONTH,
				fechaInicio.get(Calendar.DAY_OF_MONTH) - 1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	@Test(expected = InputValidationException.class)
	public void createEventWithInvalidDayOfDate3()
			throws InputValidationException, EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.DAY_OF_MONTH,
				fechaInicio.get(Calendar.DAY_OF_MONTH) - 1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/**
	 * @throws EventoExisteException
	 *******************************************************************************/

	/*
	 * Buscamos un evento con un año pasado
	 */
	@Test(expected = InputValidationException.class)
	public void createEventWithAPastYear() throws InputValidationException,
			EventoExisteException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.add(Calendar.YEAR, -1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/*
	 * Buscamos un evento por nombre que no existe
	 */
	@Test(expected = InstanceNotFoundException.class)
	public void findNonExistentEvent() throws InstanceNotFoundException {
		eventService.obtenerEventoPorNombre("NonExistentEvent");
	}

	@Test
	public void filterEventsByGroup() throws InputValidationException,
			AsignarGrupoEventoException, GrupoExisteException,
			EventoExisteException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento eventInMonth = createEventoInDate(fecha);
		Calendar fechaMesSiguiente = Calendar.getInstance();
		fechaMesSiguiente.set(Calendar.MONTH,
				fechaMesSiguiente.get(Calendar.MONTH) + 1);
		String fechaMesSiguienteString = ConvertidorFechas
				.convertirCalendarString(fechaMesSiguiente);
		Calendar fechaDosMesesDespues = Calendar.getInstance();
		fechaDosMesesDespues.set(Calendar.MONTH,
				fechaMesSiguiente.get(Calendar.MONTH) + 2);
		String fechaDosmesesDespuesString = ConvertidorFechas
				.convertirCalendarString(fechaDosMesesDespues);
		Evento eventInNextMonth = createEventoInDate(fechaMesSiguiente);
		Grupo group = createGrupoWithName("Orquesta Panorama");

		GrupoEvento grupoEvento = eventService.asignarGrupoEvento(group,
				eventInMonth, fechaString);
		GrupoEvento grupoEvento2 = eventService.asignarGrupoEvento(group,
				eventInNextMonth, fechaMesSiguienteString);

		List<Evento> eventosMes = eventService.filtrarEventosGrupo(group,
				fecha, fechaMesSiguiente);
		List<Evento> eventosMesSiguiente = eventService.filtrarEventosGrupo(
				group, fechaMesSiguiente, fechaDosMesesDespues);

		assertEquals(eventosMes.size(), 2);
		assertEquals(eventosMesSiguiente.size(), 0);

		assertEquals(eventosMes.get(0).getIdEvento(),
				eventInMonth.getIdEvento());
		assertEquals(eventosMes.get(0).getNombreEvento(),
				eventInMonth.getNombreEvento());
		assertEquals(eventosMes.get(0).getLocalidad(),
				eventInMonth.getLocalidad());
		String fechaInicio = ConvertidorFechas
				.convertirCalendarString(eventosMes.get(0)
						.getFechaInicioEvento());
		String fechaEventoMes = ConvertidorFechas
				.convertirCalendarString(eventInMonth.getFechaInicioEvento());
		assertTrue(fechaInicio.equalsIgnoreCase(fechaEventoMes));

		// Clean data base
		try {
			eventService.borrarEvento(eventInMonth.getIdEvento());
			eventService.borrarEvento(eventInNextMonth.getIdEvento());
			musicoGrupoService.borrarGrupo(group.getIdGrupo());
			eventService.borrarGrupoEvento(grupoEvento.getIdGrupoEvento());
			eventService.borrarGrupoEvento(grupoEvento2.getIdGrupoEvento());
		} catch (InstanceNotFoundException e) {
		}

	}

	/**
	 * Test asignarGrupoEvento
	 * 
	 * @throws AsignarGrupoEventoException
	 * @throws GrupoExisteException
	 * @throws EventoExisteException
	 */
	@Test
	public void asignarGrupoEventoTest() throws InputValidationException,
			AsignarGrupoEventoException, GrupoExisteException,
			EventoExisteException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento10",
				Calendar.getInstance(), "direccion evento 10");
		Grupo grupo = musicoGrupoService.crearGrupo("grupo10",
				Float.valueOf(5000));
		GrupoEvento grupoEvento = eventService.asignarGrupoEvento(grupo,
				evento, fechaString);
		assertNotNull(grupoEvento);
		assertEquals(evento.getIdEvento(), grupoEvento.getIdEvento());
		assertTrue(ConvertidorFechas.convertirCalendarString(
				evento.getFechaInicioEvento()).equalsIgnoreCase(
				grupoEvento.getFechaActuacion()));
		assertEquals(Long.valueOf(grupo.getIdGrupo()),
				Long.valueOf(grupoEvento.getIdGrupo().toString()));

		try {
			eventService.borrarEvento(evento.getIdEvento());
			musicoGrupoService.borrarGrupo(grupo.getIdGrupo());
			eventService.borrarGrupoEvento(grupoEvento.getIdGrupoEvento());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test asignarGrupoEventoNulosException
	 * 
	 * @throws EventoExisteException
	 */
	@Test(expected = InputValidationException.class)
	public void asignarGrupoEventoGrupoNuloTest()
			throws InputValidationException, AsignarGrupoEventoException,
			EventoExisteException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento1",
				Calendar.getInstance(), "direccion evento 1");
		Grupo grupo = null;
		try {
			eventService.asignarGrupoEvento(grupo, evento, fechaString);
		} finally {
			cleanAfterException(evento, grupo, null);
		}
	}

	/**
	 * Test asignarGrupoEventoNulosException
	 * 
	 * @throws InputValidationException
	 * @throws AsignarGrupoEventoException
	 * @throws GrupoExisteException
	 */
	@Test(expected = InputValidationException.class)
	public void asignarGrupoEventoEventoNuloTest()
			throws InputValidationException, AsignarGrupoEventoException,
			GrupoExisteException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = null;
		Grupo grupo = musicoGrupoService.crearGrupo("grupo1",
				Float.valueOf(5000));
		try {
			eventService.asignarGrupoEvento(grupo, evento, fechaString);
		} finally {
			cleanAfterException(evento, grupo, null);
		}
	}

	@Test(expected = AsignarGrupoEventoException.class)
	public void asignarGrupoVariosEventosTest()
			throws InputValidationException, AsignarGrupoEventoException,
			GrupoExisteException, EventoExisteException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento11",
				Calendar.getInstance(), "direccion evento 11");
		Grupo grupo = musicoGrupoService.crearGrupo("grupo11",
				Float.valueOf(5000));
		GrupoEvento ge = eventService.asignarGrupoEvento(grupo, evento,
				fechaString);
		try {
			GrupoEvento ge2 = eventService.asignarGrupoEvento(grupo, evento,
					fechaString);
		} finally {
			cleanAfterException(evento, grupo, ge);
		}
	}
}
