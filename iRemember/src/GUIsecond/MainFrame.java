package GUIsecond;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public MainFrame() {
		setLayout(new FlowLayout());
		setTitle("iRemember");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(300, 200);
		try {
			setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/taskBarImg2.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addWindowListener(new TrayListener(this));
		setSize(800, 600);
		// pack();
	}

	private class TrayListener extends WindowAdapter {

		JFrame frame;

		@Override
		public void windowClosing(WindowEvent e) {

			frame.dispose();
			SystemTray systemTray = SystemTray.getSystemTray();
			Image pic = null;
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("iRemember anzeigen");
			MenuItem exitItem = new MenuItem("Remember beenden");

			exitItem.addActionListener(new ExitTrayListener());

			try {
				pic = ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/taskBarImg2.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			popup.add(defaultItem);
			popup.add(exitItem);
			TrayIcon icon = new TrayIcon(pic, "Öffen...", popup);
			defaultItem.addActionListener(new TrayActionListener(frame, systemTray, icon));

			try {
				systemTray.add(icon);
			} catch (AWTException awtException) {
				awtException.printStackTrace();
			}

		}

		public TrayListener(JFrame frame) {
			this.frame = frame;
		}
	}

	private class TrayActionListener implements ActionListener {

		JFrame frame;
		SystemTray st;
		TrayIcon icon;

		@Override
		public void actionPerformed(ActionEvent e) {

			frame.setVisible(true);
			st.remove(icon);
		}

		public TrayActionListener(JFrame f, SystemTray st, TrayIcon icon) {
			frame = f;
			this.st = st;
			this.icon = icon;
		}
	}

	private class ExitTrayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unused")
			FileWriter fw;
			try {
				fw = new FileWriter(Main.datei);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.exit(0);

		}

	}

}
