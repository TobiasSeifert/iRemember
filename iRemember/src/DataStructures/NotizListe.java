package DataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotizListe<E extends Notiz> extends ArrayList<E> {
	File note_save;
	private int zuLöschen;

	public NotizListe() {

		// createNoteFiles();
	}

	private void setNoteNames(E e) {

		for (int i = 0; i < this.size(); i++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(
						System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt"));
			} catch (FileNotFoundException es) {
				e.setName(String.valueOf(i));
				es.printStackTrace();
			}
		}
	}

//	private void createNoteFiles() {
//		for (int i = 0; i < this.size(); i++) {
//			File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\"
//					+ this.get(i).getName() + ".txt");
//			try {
//				FileWriter fw = new FileWriter(f);
//				fw.write(this.get(i).getNotiz());
//				fw.write(System.lineSeparator());
//				fw.write(this.get(i).getDate().toString());
//				
//				fw.flush();
//				fw.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}
	
	private void createNoteFiles(E e) {
		
			
			try {
				File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\"
						+ e.getName() + ".txt");
				FileWriter fw = new FileWriter(f);
				fw.write(e.getNotiz());
				fw.write(System.lineSeparator());
				fw.write(e.getDate().toString());
				
				fw.flush();
				fw.close();
			} catch (IOException es) {
				// TODO Auto-generated catch block
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
