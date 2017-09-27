package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class MonatsFeld extends JPanel {
	
	private GregorianCalendar cal;
	private DatumFeld[][] datumFeld = new DatumFeld[6][7];
	private int monat;
	private int jahr;
	private String[] wochentage = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
	private JLabel txt;
	
	
	public MonatsFeld() {
		cal = new GregorianCalendar();
		setLayout(new GridLayout(7, 7, 5, 5));
		
		createWidgets();
		addWidgets();		
	}
	
	public MonatsFeld(String monat) {
		this.monat = getMonth(monat);
		cal = new GregorianCalendar(2017, this.monat, Calendar.DAY_OF_MONTH);
		setLayout(new GridLayout(7, 7, 5, 5));
		
		createWidgets();
		addWidgets();		
	}
	
	public MonatsFeld(String monat, int jahr) {
		this.monat = getMonth(monat);
		this.jahr = jahr;
		cal = new GregorianCalendar(jahr, this.monat, Calendar.DAY_OF_MONTH);
		setLayout(new GridLayout(7, 7, 5, 5));
		
		createWidgets();
		addWidgets();		
	}
	

	
	public void addWidgets() {
		for(int i=0; i<datumFeld.length; i++) {
			for(int j=0; j<datumFeld[i].length; j++) {
				if(!(datumFeld[i][j]==null)) {
					add(datumFeld[i][j]);
				}else {
					add(new JButton()).setEnabled(false);
				}
			}
		}
	}
	
	public void createWidgets() {
		
		int monat_Zahl = cal.get(GregorianCalendar.MONTH);
		
		int tage_Monat = cal.getActualMaximum(cal.DATE);
		int tag_des_Monats = cal.get(GregorianCalendar.DAY_OF_MONTH);
		
		for(int t=0; t<7; t++) {
			txt = new JLabel();
			txt.setText(wochentage[t]);
			txt.setHorizontalAlignment(SwingConstants.CENTER);
//			txt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			txt.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			add(txt);
		}
		
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
	
	public int getMonth(String monat) {
		switch(monat) {
			case "Januar": return 0;
				
			case "Februar": return 1;
				
			case "März": return 2;
				
			case "April": return 3;
				
			case "Mai": return 4;
				
			case "Juni": return 5;
				
			case "Juli": return 6;
				
			case "August": return 7;
				
			case "September": return 8;
				
			case "Oktober": return 9;
				
			case "November": return 10;
			
			case "Dezember": return 11;
			
			default: return -1;
		
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
//		cal2.setFirstDayOfWeek(Calendar.SUNDAY);
		
		int tag = 1;
		System.out.println(getWeekDay(cal2));
		System.out.println(cal2.get(GregorianCalendar.DAY_OF_WEEK));
		
		
		if(cal2.get(Calendar.DAY_OF_WEEK) == 1) {
			datumFeld[0][6] = new DatumFeld(tag);
			tag++;
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<7; j++) {
					datumFeld[i][j] = new DatumFeld(tag);
					
					System.out.println(j);
					if(tag >= anzTage) {
						return;
					}
					tag++;
	
				}
			}
		}else if(cal2.get(Calendar.DAY_OF_WEEK) == 2) {
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<7; j++) {
					datumFeld[i][j] = new DatumFeld(tag);
					
					System.out.println(j);
					if(tag >= anzTage) {
						return;
					}
					tag++;
	
				}
			}
		}else {
			for(int j = cal2.get(Calendar.DAY_OF_WEEK)-2; j<7; j++) {
				System.out.println(j);
				datumFeld[0][j] = new DatumFeld(tag);
				tag++;
				
			}
		
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<7; j++) {
					datumFeld[i][j] = new DatumFeld(tag);
					System.out.println(j);
					if(tag >= anzTage) {
						return;
					}
					tag++;
	
				}
			}
		}	
	}
}
