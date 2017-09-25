package DataStructures;

public class Notiz {
	
	private long timestamp;
	private String notiz;
	
		public Notiz(String notiztext) {
			setTimestamp(System.currentTimeMillis());
			this.setNotiz(notiztext);
		}

		public long getTimestamp() {
			return timestamp;
		}

		private void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public String getNotiz() {
			return notiz;
		}

		public void setNotiz(String notiz) {
			this.notiz = notiz;
		}

		
}
