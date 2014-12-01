package es.udc.med.espectaculos.utils;

import java.util.Calendar;

import es.udc.med.espectaculos.model.evento.Evento;
import es.udc.med.espectaculos.model.musico.Musico;

public final class PropertyValidator {

	public static void validateEvent(Evento event)
			throws InputValidationException {
		if (event.getNombreEvento() == null
				|| event.getNombreEvento().equals("")) {
			throw new InputValidationException("Nombre de evento no válido");
		}
		if (event.getLocalidad() == null || event.getLocalidad().equals("")) {
			throw new InputValidationException("Localidad no válida");
		}
		Calendar fechaActual = Calendar.getInstance();
		if (event.getFechaInicioEvento().get(Calendar.YEAR) < fechaActual
				.get(Calendar.YEAR)) {
			throw new InputValidationException("Año anterior al actual");
		}
		if ((event.getFechaInicioEvento().get(Calendar.MONTH) < fechaActual
				.get(Calendar.MONTH))
				&& (event.getFechaInicioEvento().get(Calendar.YEAR) <= fechaActual
						.get(Calendar.YEAR))) {
			throw new InputValidationException("Mes anterior al actual");
		}
		if ((event.getFechaInicioEvento().get(Calendar.DAY_OF_MONTH) < fechaActual
				.get(Calendar.DAY_OF_MONTH))
				&& (event.getFechaInicioEvento().get(Calendar.MONTH) <= fechaActual
						.get(Calendar.MONTH))
				&& (event.getFechaInicioEvento().get(Calendar.YEAR) <= fechaActual
						.get(Calendar.YEAR))) {
			throw new InputValidationException("Día anterior al actual");
		}
		if ((event.getFechaInicioEvento().get(Calendar.MONTH) > 11)
				| (event.getFechaInicioEvento().get(Calendar.DAY_OF_MONTH) > 31)) {
			throw new InputValidationException("Mes o dia incorrecto");
		}

	}

	public static void validateMusico(Musico musico)
			throws InputValidationException {
		if (musico.getNombreMusico() == null
				|| musico.getNombreMusico().equals("")) {
			throw new InputValidationException("Nombre de músico no válido");
		}
		if (musico.getDireccion() == null || musico.getDireccion().equals("")) {
			throw new InputValidationException("Direccióm no válida");
		}
		if (musico.getInstrumento() == null
				|| musico.getInstrumento().equals("")) {
			throw new InputValidationException("Instrumento no válido");
		}
	}

}
