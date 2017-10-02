package DataStructures;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gui.MonatsFeld;

public class Notiz {

	private long timestamp;
	private String zeitstempel;
	private String notiz;
	private Date date;
	private GregorianCalendar cal;
	private String name;

	public Notiz(String notiztext) {
		this.setNotiz(notiztext);
	}

	public void setTimestamp(long currentTimeMillis) {
		this.timestamp = currentTimeMillis;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public GregorianCalendar getCal() {
		return cal;
	}

	public void setCal(GregorianCalendar cal) {
		this.cal = cal;
	}

	public String checkTime(int time) {
		if (time < 10) {
			return "" + 0 + time;
		} else {
			return "" + time;
		}
	}


	@Override
	public String toString() {
		return cal.get(Calendar.DAY_OF_MONTH) + ". " + new MonatsFeld().getMonth(cal) + ", " + checkTime(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ checkTime(cal.get(Calendar.MINUTE));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setErstellzeit() {
		setTimestamp(System.currentTimeMillis());
		setDate(new Date(getTimestamp()));
		setCal(new GregorianCalendar());
		cal.setTime(date);
		System.out.println(toString());
		this.setZeitstempel(toString());
	}

	public String getZeitstempel() {
		return zeitstempel;
	}

	public void setZeitstempel(String zeitstempel) {
		this.zeitstempel = zeitstempel;
	}
	
	
}
