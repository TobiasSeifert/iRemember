package GUIsecond;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import DataStructures.Notiz;
import DataStructures.NotizListe;

public class Main {

	static FileWriter fw;
	static File datei = new File("LockFile.txt");
	static BufferedReader br = null;
	private NotizListe<Notiz> notizliste = new NotizListe<Notiz>();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				readTxt(datei);

			}
		});

	}

	static void readTxt(File datei) {

		try {
			br = new BufferedReader(new FileReader(datei));
			String text = br.readLine();
			System.out.println(text);
			if (text == null) {
				MainFrame frame = new MainFrame();
				frame.setVisible(true);

				fw = new FileWriter(datei);
				fw.write("started");
				fw.flush();
				fw.close();
			} else {
				System.exit(-1);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
