package DataStructures;

import java.util.Date;

public class Notiz {

	private long timestamp;
	private String notiz;
	private Date date;

	public Notiz(String notiztext) {
		setTimestamp(System.currentTimeMillis());
		this.setNotiz(notiztext);
		setDate(new Date(timestamp));

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

}
