package gui;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;



public class MonatsFeld extends JPanel {
	
	private GregorianCalendar cal = new GregorianCalendar();
	private DatumFeld[][] datumFeld = new DatumFeld[6][7];
	
	public MonatsFeld() {

		setLayout(new GridLayout(7, 5, 5, 5));

		createWidgets();

	}

	public void createWidgets() {
		
		String monat;
		int monat_Zahl = cal.get(GregorianCalendar.MONTH);
		
//		int tage_Monat = cal.getActualMaximum(GregorianCalendar.MONTH);
		int tage_Monat = cal.getActualMaximum(cal.DATE);
		int tag_des_Monats = cal.get(GregorianCalendar.DAY_OF_MONTH);
//		System.out.println(tage_Monat);
		auffuellen(tage_Monat);
		
	}
	
	public String getMonth(Calendar c) {
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
	
	public String getWeekDay(Calendar c) {
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
	
	public void auffuellen(int anzTage){
		Calendar cal2 = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
		
		int tag = 1;
		
		for(int j = cal2.get(Calendar.DAY_OF_WEEK); j<7; j++) {
			datumFeld[0][j] = new DatumFeld(tag);
			System.out.println(tag);
			tag++;
			
		}
		
		for(int i = 1; i<6; i++) {
			for(int j = 0; j<7; j++) {
				datumFeld[i][j] = new DatumFeld(tag);
				System.out.println(tag);
				if(tag >= anzTage) {
					return;
				}
				tag++;

			}
		}
	}
}
