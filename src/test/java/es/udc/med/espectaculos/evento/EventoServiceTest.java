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
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.ConvertidorFechas;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class EventoServiceTest {

	private static EventoService eventService = null;
	private static EventoDao eventoDao = null;
	private static GrupoDao grupoDao = null;
	private static GrupoEventoDao grupoEventoDao = null;

	@BeforeClass
	public static void init() {
		eventService = new EventoServiceImpl();
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

	private Evento createEventoInDate(Calendar fecha)
			throws InputValidationException {
		return eventService.crearEvento("Fiestas A Laracha", fecha,
				"Calle Falsa 123");

	}

	private Evento createEvento() throws InputValidationException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.HOUR_OF_DAY, 0);
		fechaInicio.set(Calendar.MINUTE, 0);
		fechaInicio.set(Calendar.SECOND, 0);
		fechaInicio.set(Calendar.MILLISECOND, 0);
		return eventService.crearEvento("Fiestas A Laracha", fechaInicio,
				"Calle Falsa 123");
	}

	private Grupo createGrupoWithName(String nombre) {
		return eventService.crearGrupo(nombre, 3000);
	}

	/*
	 * Caso exitoso en el que se crea un evento y se busca por nombre
	 */
	@Test
	public void createAndFindEvent() throws InstanceNotFoundException,
			InputValidationException {
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

	/******** Probamos a crear eventos con nombre/localidad nulo/a o vacio/a ********/

	@Test(expected = InputValidationException.class)
	public void createEventWithNullName() throws InputValidationException {
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
	public void createEventWithEmptyName() throws InputValidationException {
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
	public void createEventWithNullPlace() throws InputValidationException {
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
	public void createEventWithEmptyPlace() throws InputValidationException {
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

	/******************************************************************************/

	/*
	 * Probamos el caso de crear un evento con una fecha del mes pasado
	 */
	@Test(expected = InputValidationException.class)
	public void createEventWithInvalidMonthOfDate()
			throws InputValidationException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.add(Calendar.MONTH, -1);
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
			throws InputValidationException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.add(Calendar.DAY_OF_MONTH, -1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/******** Probamos a crear eventos con un día mayor que 31 o menor que 1 *********/

	@Test(expected = InputValidationException.class)
	public void createEventWithInvalidDayOfDate2()
			throws InputValidationException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.DAY_OF_MONTH, 32);
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
			throws InputValidationException {
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(Calendar.DAY_OF_MONTH, -1);
		Evento event = eventService.crearEvento("Nombre actuacion",
				fechaInicio, "Localidad");

		// Clean data base
		try {
			eventService.borrarEvento(event.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}
	}

	/*********************************************************************************/

	/*
	 * Buscamos un evento con un año pasado
	 */
	@Test(expected = InputValidationException.class)
	public void createEventWithAPastYear() throws InputValidationException {
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
			AsignarGrupoEventoException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento eventInMonth = createEventoInDate(fecha);
		Calendar fechaMesSiguiente = Calendar.getInstance();
		fechaMesSiguiente.add(Calendar.MONTH, 1);
		String fechaMesSiguienteString = ConvertidorFechas
				.convertirCalendarString(fechaMesSiguiente);
		Calendar fechaDosMesesDespues = Calendar.getInstance();
		fechaDosMesesDespues.add(Calendar.MONTH, 2);
		String fechaDosmesesDespuesString = ConvertidorFechas
				.convertirCalendarString(fechaDosMesesDespues);
		Evento eventInNextMonth = createEventoInDate(fechaMesSiguiente);
		Grupo group = createGrupoWithName("Orquesta Panorama");

		eventService.asignarGrupoEvento(group, eventInMonth, fechaString);
		eventService.asignarGrupoEvento(group, eventInNextMonth,
				fechaMesSiguienteString);

		List<Evento> eventosMes = eventService.filtrarEventosGrupo(group,
				fecha, fechaMesSiguiente);
		List<Evento> eventosMesSiguiente = eventService.filtrarEventosGrupo(
				group, fechaMesSiguiente, fechaDosMesesDespues);

		assertEquals(eventosMes.size(), 2);
		assertEquals(eventosMesSiguiente.size(), 1);

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

		assertEquals(eventosMesSiguiente.get(0).getIdEvento(),
				eventInNextMonth.getIdEvento());
		assertEquals(eventosMesSiguiente.get(0).getNombreEvento(),
				eventInNextMonth.getNombreEvento());
		assertEquals(eventosMesSiguiente.get(0).getLocalidad(),
				eventInNextMonth.getLocalidad());
		assertEquals(eventosMesSiguiente.get(0).getFechaInicioEvento(),
				eventInNextMonth.getFechaInicioEvento());

		// Clean data base
		try {
			eventService.borrarEvento(eventInMonth.getIdEvento());
			eventService.borrarEvento(eventInNextMonth.getIdEvento());
		} catch (InstanceNotFoundException e) {
		}

	}

	/**
	 * Test asignarGrupoEvento
	 * 
	 * @throws AsignarGrupoEventoException
	 */
	@Test
	public void asignarGrupoEventoTest() throws InputValidationException,
			AsignarGrupoEventoException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento1",
				Calendar.getInstance(), "direccion evento 1");
		Grupo grupo = eventService.crearGrupo("grupo1", Float.valueOf(5000));
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
			eventService.borrarGrupo(grupo.getIdGrupo());
			eventService.borrarGrupoEvento(grupoEvento.getIdGrupoEvento());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Test asignarGrupoEventoNulosException
	 */
	@Test(expected = InputValidationException.class)
	public void asignarGrupoEventoGrupoNuloTest()
			throws InputValidationException, AsignarGrupoEventoException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento1",
				Calendar.getInstance(), "direccion evento 1");
		Grupo grupo = null;
		eventService.asignarGrupoEvento(grupo, evento, fechaString);
		try {
			eventService.borrarEvento(evento.getIdEvento());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test asignarGrupoEventoNulosException
	 * 
	 * @throws InputValidationException
	 * @throws AsignarGrupoEventoException
	 */
	@Test(expected = InputValidationException.class)
	public void asignarGrupoEventoEventoNuloTest()
			throws InputValidationException, AsignarGrupoEventoException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = null;
		Grupo grupo = eventService.crearGrupo("grupo1", Float.valueOf(5000));
		eventService.asignarGrupoEvento(grupo, evento, fechaString);

		try {
			eventService.borrarGrupo(grupo.getIdGrupo());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = AsignarGrupoEventoException.class)
	public void asignarGrupoVariosEventosTest()
			throws InputValidationException, AsignarGrupoEventoException {
		Calendar fecha = Calendar.getInstance();
		String fechaString = ConvertidorFechas.convertirCalendarString(fecha);

		Evento evento = eventService.crearEvento("evento1",
				Calendar.getInstance(), "direccion evento 1");
		Grupo grupo = eventService.crearGrupo("grupo1", Float.valueOf(5000));
		GrupoEvento ge = eventService.asignarGrupoEvento(grupo, evento,
				fechaString);
		GrupoEvento ge2 = eventService.asignarGrupoEvento(grupo, evento,
				fechaString);
	}
}
