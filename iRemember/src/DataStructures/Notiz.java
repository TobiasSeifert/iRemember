package DataStructures;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gui.MonatsFeld;

public class Notiz {

	private long timestamp;
	private String notiz;
	private Date date;
	private GregorianCalendar cal;

	public Notiz(String notiztext) {
		setTimestamp(System.currentTimeMillis());
		this.setNotiz(notiztext);
		setDate(new Date(timestamp));
		setCal(new GregorianCalendar());

	}

	private void setTimestamp(long currentTimeMillis) {
		// TODO Auto-generated method stub
		this.timestamp = currentTimeMillis;
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
		cal.setTime(date);
	}

	public String checkTime(int time) {
		if (time < 10) {
			return "" + 0 + time;
		} else {
			return "" + time;
		}
	}

	public String getMonth(Calendar c) {

		switch (c.get(GregorianCalendar.MONTH)) {
		case 0:
			return "Januar";

		case 1:
			return "Februar";

		case 2:
			return "März";

		case 3:
			return "April";

		case 4:
			return "Mai";

		case 5:
			return "Juni";

		case 6:
			return "Juli";

		case 7:
			return "August";

		case 8:
			return "September";

		case 9:
			return "Oktober";

		case 10:
			return "November";

		case 11:
			return "Dezember";

		default:
			return "Falsche Eingabe";

		}
	}

	@Override
	public String toString() {
		return cal.get(Calendar.DAY_OF_MONTH) + ". " + getMonth(cal) + ", " + checkTime(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ checkTime(cal.get(Calendar.MINUTE));
	}

}
