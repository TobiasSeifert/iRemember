package DataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotizListe<E extends Notiz> extends ArrayList<E> {
	File note_save;
	private int zuLöschen;

	public NotizListe() {

	}

	@SuppressWarnings("unused")
	private void setNoteNames(E e) {

		for (int i = 0; i < this.size(); i++) {
				try {
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(
							System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt"));

				} catch (IOException e1) {
					e.setName(String.valueOf(i));
				}
		}
	}

	private void createNoteFiles(E e) {

		try {
			File f = new File(
					System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + e.getName() + ".txt");
			FileWriter fw = new FileWriter(f);
			fw.write(e.getDate().toString());
			fw.write(System.lineSeparator());
			fw.write(e.getNotiz());
			
			
			fw.flush();
			fw.close();
		} catch (IOException es) {
			es.printStackTrace();
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
		setNoteNames(e);
		createNoteFiles(e);

		return true;

	}

	public void saveNotes() {

	}

	public int getZuLöschen() {
		return zuLöschen;
	}

	public void setZuLöschen(int zuLöschen) {
		this.zuLöschen = zuLöschen;
	}

}
