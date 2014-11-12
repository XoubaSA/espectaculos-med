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
import es.udc.med.espectaculos.model.musico.Jdbc3CcSqlMusicoDao;
import es.udc.med.espectaculos.model.musico.Musico;
import es.udc.med.espectaculos.model.musico.MusicoDao;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoService;
import es.udc.med.espectaculos.model.musicogruposervice.MusicoGrupoServiceImpl;
import es.udc.med.espectaculos.utils.AsignarGrupoEventoException;
import es.udc.med.espectaculos.utils.InputValidationException;
import es.udc.med.espectaculos.utils.InstanceNotFoundException;

public class MusicoGrupoServiceTest {
	
	private static MusicoGrupoService musicoGrupoService = null;
	private static EventoService eventService = null;
	private static MusicoDao musicoDao = null;
	private static GrupoDao grupoDao = null;
	
	@BeforeClass
	public static void init() {
		musicoGrupoService = new MusicoGrupoServiceImpl();
		eventService = new EventoServiceImpl();
		musicoDao = new Jdbc3CcSqlMusicoDao();
		grupoDao = new Jdbc3CcSqlGrupoDao();
	}
	
	@Test
	public void asociarMusicoGrupo() throws InputValidationException, InstanceNotFoundException {
		Musico musico1 = new Musico("paco", "coruña", "bateria");
		Musico musico2 = new Musico("Pepe", "lugo", "guitarra");
		Musico musico3 = new Musico("manolo", "ourense", "bajo");
		List<Musico> musicos = new ArrayList<Musico>();
		musicos.add(musico1);
		musicos.add(musico2);
		musicos.add(musico3);
		
		Grupo grupo = new Grupo("Orquesta Musical", 3000.0F);
		musicoGrupoService.asociarMusicoGrupo(musicos, grupo);
		
		List<Musico> m = musicoGrupoService.getFormacion(grupo);
		
		assertEquals(3, m.size());
		assertTrue(m.containsAll(musicos));
	}
	
	@Test
	public void asociarOtroMusicoGrupo() throws InputValidationException, InstanceNotFoundException {
		Musico musico1 = new Musico("paco", "coruña", "bateria");
		Musico musico2 = new Musico("Pepe", "lugo", "guitarra");
		Musico musico3 = new Musico("manolo", "ourense", "bajo");
		List<Musico> musicos = new ArrayList<Musico>();
		musicos.add(musico1);
		musicos.add(musico2);
		musicos.add(musico3);
		
		Grupo grupo = new Grupo("Los Chichos" , 2000.0F);
		musicoGrupoService.asociarMusicoGrupo(musicos, grupo);
		
		List<Musico> m = musicoGrupoService.getFormacion(grupo);
		
		assertEquals(3, m.size());
		
		Musico musico4 = new Musico("uxia", "coruña", "guitarra");
		musicos.add(musico4);
		
		musicoGrupoService.asociarMusicoGrupo(musicos, grupo);
		
		List<Musico> m2 = musicoGrupoService.getFormacion(grupo);
		
		assertEquals(4, m2.size());
		assertTrue(m.containsAll(musicos));
	}
	
	@Test(expected = InputValidationException.class)
	public void nombreGrupoNulo() throws InputValidationException {
		Musico musico1 = new Musico("paco", "coruña", "bateria");
		Musico musico2 = new Musico("Pepe", "lugo", "guitarra");
		Musico musico3 = new Musico("manolo", "ourense", "bajo");
		List<Musico> musicos = new ArrayList<Musico>();
		musicos.add(musico1);
		musicos.add(musico2);
		musicos.add(musico3);
		
		Grupo grupo = new Grupo(null , 2000.0F);
		musicoGrupoService.asociarMusicoGrupo(musicos, grupo);
	}
	
	@Test(expected = InputValidationException.class)
	public void grupoNulo() throws InputValidationException {
		Musico musico1 = new Musico("paco", "coruña", "bateria");
		Musico musico2 = new Musico("Pepe", "lugo", "guitarra");
		Musico musico3 = new Musico("manolo", "ourense", "bajo");
		List<Musico> musicos = new ArrayList<Musico>();
		musicos.add(musico1);
		musicos.add(musico2);
		musicos.add(musico3);
		
		musicoGrupoService.asociarMusicoGrupo(musicos, null);
	}
	
	@Test(expected = InputValidationException.class)
	public void musicosVacio() throws InputValidationException {
		List<Musico> musicos = new ArrayList<Musico>();
		Grupo grupo = new Grupo("Los Chichos" , 2000.0F);
		musicoGrupoService.asociarMusicoGrupo(musicos, grupo);
	}

}
