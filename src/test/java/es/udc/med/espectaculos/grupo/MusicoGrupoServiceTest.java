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
	
	//TODO falta hacer los test de asignar musico a grupo (necesitamos el getFormacion)

}
