package br.com.rodrigoamora.eventosti.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {
	public static String converterDateWithMonthName(String date) {
		String pattern = "dd/MMM/yyy";
		
		String dateVireiyed = verifyMonthName(date);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, new Locale("pt","BR"));
		LocalDate localDate = LocalDate.parse(dateVireiyed, formatter);
		
		return localDate.toString();
	}
	
	private static String verifyMonthName(String date) {
		String [] monthNames = {"Janeiro", "Feveiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		String [] monthsNames2 = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
		
		for (int i = 0; i < monthNames.length; i++) {
			if (date.equalsIgnoreCase(monthNames[i]) ||
				date.equalsIgnoreCase(monthsNames2[i])) {
				return date.replace(monthNames[i], monthNames[i].toLowerCase());
			}
		}
		
		return date;
	}
}
