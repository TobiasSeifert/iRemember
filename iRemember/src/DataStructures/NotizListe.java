package DataStructures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotizListe<E extends Notiz> extends ArrayList<E> {
	File note_save;

	public NotizListe() {

		// createNoteFiles();
	}

	private void setNoteNames() {

		for (int i = 0; i < this.size(); i++) {
			this.get(i).setName(String.valueOf(i));
		}
	}

	private void createNoteFiles() {
		for (int i = 0; i < this.size(); i++) {
			File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\"
					+ this.get(i).getName() + ".txt");
			try {
				FileWriter fw = new FileWriter(f);
				fw.write(this.get(i).getNotiz());
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public E remove(int index) {
		File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + index + ".txt");
		f.delete();

		return super.remove(index);

	}

	@Override
	public boolean add(E e) {

		super.add(e);
		setNoteNames();
		createNoteFiles();

		return true;

	}

	public void saveNotes() {

	}

}
