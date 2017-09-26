package gui;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class MonatsFeld extends JPanel {
	
	private GregorianCalendar cal = new GregorianCalendar();

	public MonatsFeld() {

		setLayout(new GridLayout(7, 5, 5, 5));

		createWidgets();

	}

	public void createWidgets() {
		
		String monat;
		int monat_Zahl = cal.get(GregorianCalendar.MONTH);
		
		int tage_Monat = cal.getActualMaximum(monat_Zahl);
		
		int tag_des_Monats = cal.get(GregorianCalendar.DAY_OF_MONTH);
		
		Calendar cal2 = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
//		System.out.println(getWeekDay(cal2));
		
		
		for(int i = 0; i<=6; i++) {
			for(int j = cal2.get(Calendar.DAY_OF_WEEK); j<7; j++) {
				
			}
		}
		
		
		
		
//		switch(monat_Zahl) {
//		case 0: 
//			monat = "Januar";
//			break;
//		case 1: 
//			monat = "Februar";
//			break;
//		case 2: 
//			monat = "März";
//			break;
//		case 3: 
//			monat = "April";
//			break;
//		case 4: 
//			monat = "Mai";
//			break;
//		case 5: 
//			monat = "Juni";
//			break;
//		case 6: 
//			monat = "Juli";
//			break;
//		case 7: 
//			monat = "August";
//			break;
//		case 8: 
//			monat = "September";
//			break;
//		case 9: 
//			monat = "Oktober";
//			break;
//		case 10: 
//			monat = "November";
//			break;
//		case 11: 
//			monat = "Dezember";
//			break;
//		}
		
	}
	
	public static String getMonth(Calendar c) {
		switch(c.get(GregorianCalendar.MONTH)) {
			case 0: return "Januar";
				
			case 1: return "Februar";
				
			case 2: return "März";
				
			case 3: return "April";
				
			case 4: return "Mai";
				
			case 5: return "Juni";
				
			case 6: return "Juli";
				
			case 7: return "August";
				
			case 8: return "September";
				
			case 9: return "Oktober";
				
			case 10: return "November";
			
			case 11: return "Dezember";
			
			default: return "Falsche Eingabe";
		
		}
	}
	
	public static String getWeekDay(Calendar c) {
		switch(c.get(GregorianCalendar.DAY_OF_WEEK)) {
			case 1: return "Sonntag";
				
			case 2: return "Montag";
				
			case 3: return "Dienstag";
				
			case 4: return "Mittwoch";
				
			case 5: return "Donnerstag";
				
			case 6: return "Freitag";
				
			case 7: return "Samstag";
				
			default: return "Falsche Eingabe";
		
		}
	}
}
