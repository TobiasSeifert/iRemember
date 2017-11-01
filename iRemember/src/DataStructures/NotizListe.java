package DataStructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class NotizListe<K extends String, E extends Notiz> extends TreeMap<K, E> {
	File note_save;

	public NotizListe() {

	}

//	@SuppressWarnings("unused")
//	private void setNoteNames(E notiz) {
//
//		for (int i = 0; i < Integer.parseInt(this.lastKey()); i++) {
//			if (System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt" == null) {
//				try {
//					@SuppressWarnings("resource")
//					BufferedReader br = new BufferedReader(new FileReader(
//							System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt"));
//
//				} catch (IOException e1) {
//					notiz.setName(String.valueOf(i));
//					System.out.println(String.valueOf(i));
//				}
//			}
//		}
//	}

	private void createNoteFiles(E e) {

		try {
			File f = new File(
					System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + e.getName() + ".txt");
			FileWriter fw = new FileWriter(f);
			fw.write(e.getZeitstempel());
			fw.write(System.lineSeparator());
			fw.write(e.getNotiz().replaceAll("\n", System.lineSeparator()));
			fw.flush();
			fw.close();
		} catch (IOException es) {
			es.printStackTrace();
		}

	}

	@Override
	public E remove(Object key) {
		File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + key + ".txt");
		f.delete();

		return super.remove(key);

	}

	@Override
	public E put(K k, E e) {

		super.put(k, e);
//		setNoteNames(e);
		createNoteFiles(e);

		return e;

	}

}
