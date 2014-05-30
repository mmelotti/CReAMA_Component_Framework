package com.creama.conecte.components.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormat {

	public static String parseFullDate(Date d) {
		String result = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			result += "domingo, ";
			break;
		case Calendar.MONDAY:
			result += "segunda-feira, ";
			break;
		case Calendar.TUESDAY:
			result += "terça-feira, ";
			break;
		case Calendar.WEDNESDAY:
			result += "quarta-feira, ";
			break;
		case Calendar.THURSDAY:
			result += "quinta-feira, ";
			break;
		case Calendar.FRIDAY:
			result += "sexta-feira, ";
			break;
		case Calendar.SATURDAY:
			result += "sábado, ";
			break;
		}

		result += cal.get(Calendar.DAY_OF_MONTH) + " de ";

		switch (cal.get(Calendar.MONTH)) {
		case Calendar.JANUARY:
			result += "janeiro de ";
			break;
		case Calendar.FEBRUARY:
			result += "fevereiro de ";
			break;
		case Calendar.MARCH:
			result += "março de ";
			break;
		case Calendar.APRIL:
			result += "abril de ";
			break;
		case Calendar.MAY:
			result += "maio de ";
			break;
		case Calendar.JUNE:
			result += "junho de ";
			break;
		case Calendar.JULY:
			result += "julho de ";
			break;
		case Calendar.AUGUST:
			result += "agosto de ";
			break;
		case Calendar.SEPTEMBER:
			result += "setembro de ";
			break;
		case Calendar.OCTOBER:
			result += "outubro de ";
			break;
		case Calendar.NOVEMBER:
			result += "novembro de ";
			break;
		case Calendar.DECEMBER:
			result += "dezembro de ";
			break;
		}

		result += cal.get(Calendar.YEAR);

		return result;
	}
	
	public static String parseDate(Date d) {
		String result = "";
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		
		result += new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
		
		return result;
	}
}