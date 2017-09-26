package gui;

import java.awt.GridLayout;
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
		
		
		
		switch(monat_Zahl) {
		case 0: 
			monat = "Januar";
			break;
		case 1: 
			monat = "Februar";
			break;
		case 2: 
			monat = "März";
			break;
		case 3: 
			monat = "April";
			break;
		case 4: 
			monat = "Mai";
			break;
		case 5: 
			monat = "Juni";
			break;
		case 6: 
			monat = "Juli";
			break;
		case 7: 
			monat = "August";
			break;
		case 8: 
			monat = "September";
			break;
		case 9: 
			monat = "Oktober";
			break;
		case 10: 
			monat = "November";
			break;
		case 11: 
			monat = "Dezember";
			break;
		}
		
	}
}
