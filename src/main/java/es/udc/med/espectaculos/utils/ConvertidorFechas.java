package es.udc.med.espectaculos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConvertidorFechas {

	public static String convertirCalendarString(Calendar fecha) {
		int year, month, day;
		year = fecha.get(Calendar.YEAR);
		month = fecha.get(Calendar.MONTH) + 1;
		day = fecha.get(Calendar.DATE);

		String strDate = Integer.toString(year) + "-";
		strDate += (month < 10) ? "0" + month + "-" : month + "-";
		strDate += (day < 10) ? "0" + day : day;
		
		return strDate;
	}
	
	public static Calendar convertirStringCalendar(String strDate){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar fecha = Calendar.getInstance();
		try {
			fecha.setTime(df.parse(strDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fecha;
	}
	
	private ConvertidorFechas(){}
	
}
