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

@SuppressWarnings("serial")
public class MonatsFeld extends JPanel {
	
	private GregorianCalendar heute = new GregorianCalendar();
	private DatumFeld[][] datumFeld = new DatumFeld[6][7];
	private int tag;
	private int monat;
	private int jahr;
	private GregorianCalendar cal;
	private String[] wochentage = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
	private JLabel txt;
	
	public MonatsFeld() {
		this.tag =  heute.get(Calendar.DAY_OF_MONTH);
		this.monat = heute.get(Calendar.MONTH);
		this.jahr = heute.get(Calendar.YEAR);
		this.cal = new GregorianCalendar(2018,0,1);
		cal.set(jahr,  monat, tag);
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
					if(heute.get(Calendar.YEAR) == jahr && heute.get(Calendar.MONTH) == monat && datumFeld[i][j].getMonatsTag() == heute.get(Calendar.DAY_OF_MONTH)) {
						datumFeld[i][j].setBackground(Color.BLUE);
						
					}	
					add(datumFeld[i][j]);
				}else {
					add(new JButton()).setEnabled(false);
				}
			}
		}
	}
	
	public void createWidgets() {
		
		@SuppressWarnings("static-access")
		int tage_Monat = cal.getActualMaximum(cal.DATE);
		
		for(int t=0; t<7; t++) {
			txt = new JLabel();
			txt.setText(wochentage[t]);
			txt.setHorizontalAlignment(SwingConstants.CENTER);
			txt.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			add(txt);
		}
		
		auffuellen(tage_Monat);		
	}
	
	public String getMonth(Calendar c) {
		switch(c.get(GregorianCalendar.MONTH)) {
			case 0: return "Januar";
				
			case 1: return "Februar";
				
			case 2: return "M�rz";
				
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
				
			case "M�rz": return 2;
				
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
		int tag = 1;
		
		if(cal2.get(Calendar.DAY_OF_WEEK) == 1) {
			datumFeld[0][6] = new DatumFeld(tag);
			tag++;
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<7; j++) {
					datumFeld[i][j] = new DatumFeld(tag);
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
					if(tag >= anzTage) {
						return;
					}
					tag++;
	
				}
			}
		}else {
			for(int j = cal2.get(Calendar.DAY_OF_WEEK)-2; j<7; j++) {
				datumFeld[0][j] = new DatumFeld(tag);
				tag++;
				
			}
		
			for(int i = 1; i<6; i++) {
				for(int j = 0; j<7; j++) {
					datumFeld[i][j] = new DatumFeld(tag);
					if(tag >= anzTage) {
						return;
					}
					tag++;
	
				}
			}
		}	
	}

	public void setMonat(String monat) {
		this.monat = getMonth(monat);
		cal.set(this.jahr, this.monat, tag);
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
		cal.set(this.jahr, this.monat, tag);
	}

	public GregorianCalendar getHeute() {
		return heute;
	}
}
