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

public class Main {

	static FileWriter fw;
	static File datei;
	static File dir;

	static BufferedReader br = null;

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

				directoryCheck();

			}

		});

	}

	private static void directoryCheck() {
		dir = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder");
		datei = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\LockFile.txt");

		if (dir.exists()) {
			createData();
		} else {
			dir.mkdirs();
			try {
				fw = new FileWriter(datei);
				fw.write("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			createData();

		}

	}

	private static void createData() {
		// TODO Auto-generated method stub
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
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
