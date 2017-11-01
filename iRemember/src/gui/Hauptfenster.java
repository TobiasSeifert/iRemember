package gui;

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
import java.io.IOException;
import java.net.ServerSocket;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Hauptfenster extends JFrame {

	public Hauptfenster() {
		setLayout(new FlowLayout());
		setTitle("iRemember");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(300, 200);
		try {
			setIconImage(ImageIO.read(Hauptfenster.class.getClassLoader().getResourceAsStream("gui/appdefault.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addWindowListener(new TrayListener(this));
		setSize(800, 600);

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
			defaultItem.addActionListener(new TrayActionListener(frame));
			exitItem.addActionListener(new ExitTrayListener());

			try {
				pic = ImageIO.read(Hauptfenster.class.getClassLoader().getResourceAsStream("gui/appdefault.png"));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			popup.add(defaultItem);
			popup.add(exitItem);
			TrayIcon icon = new TrayIcon(pic, "Öffen...", popup);

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

		@Override
		public void actionPerformed(ActionEvent e) {

			frame.setVisible(true);
		}

		public TrayActionListener(JFrame f) {
			frame = f;
		}
	}

	private class ExitTrayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}

	
}
