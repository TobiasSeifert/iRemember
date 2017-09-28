package GUIsecond;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import DataStructures.Notiz;
import DataStructures.NotizListRenderer;
import DataStructures.NotizListe;
import gui.MonatsFeld;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private int index;

	private NotizListe<Notiz> notizListe = new NotizListe<Notiz>();

	private DefaultListModel<Notiz> listModel = new DefaultListModel<Notiz>();

	private CardLayout mainLayout = new CardLayout();

	private JLabel header;

	private JPanel mainView;

	private JPanel mainViewNotizen;

	private JPanel notizenTop;
	private JTextField filter;
	private JComboBox<String> sortierung;

	private JScrollPane notizScrollBar;
	private JList<Notiz> notizAnzeige;

	private JProgressBar einlesenProgBar;

	private JPanel notizenBottom;

	private JTextField notizEingabe;

	private JPanel untereKnoepfe;
	private JButton erstellen;
	private JButton loeschen;
	private JButton abbrechen;

	// TODO Bearbeiten und Erstellen

	private JPanel mainViewKalender;
	private MonatsFeld kalender;
	private JComboBox<Integer> jahre;
	private JComboBox<String> monate;

	private JPanel sideBar;
	private JButton notizenBtn;
	private JButton kalenderBtn;
	private JLabel status;
	private JButton beenden;

	private int width, height;

	public MainFrame() {
		setHeight_Width();
		setLayout(new BorderLayout(5, 5));
		setTitle("iRemember");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(300, 200);

		try {
			setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/taskBarImg2.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		createWidgets();
		addWidgets();
		setupInteractions();

		addWindowListener(new TrayListener(this));

		setSize(height, width);
		// pack();

	}

	private void setHeight_Width() {
		try {
			BufferedReader bufr = new BufferedReader(new FileReader(Main.properties));
			width = Integer.parseInt(bufr.readLine());
			height = Integer.parseInt(bufr.readLine());

			System.out.println(bufr.readLine());
			System.out.println(bufr.readLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createWidgets() {

		mainView = new JPanel();
		mainView.setLayout(mainLayout);

		mainViewNotizen = new JPanel();
		mainViewNotizen.setLayout(new BoxLayout(mainViewNotizen, BoxLayout.Y_AXIS));

		mainViewKalender = new JPanel();
		mainViewKalender.setLayout(new BoxLayout(mainViewKalender, BoxLayout.Y_AXIS));

		sideBar = new JPanel();
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

		header = new JLabel("iRemember");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
		header.setOpaque(true);
		header.setBackground(Color.BLACK);
		header.setForeground(Color.WHITE);

		notizenBtn = new JButton("Notizen");
		notizenBtn.setHorizontalAlignment(SwingConstants.CENTER);
		notizenBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		notizenBtn.setForeground(Color.GREEN);

		kalenderBtn = new JButton("Kalender");
		kalenderBtn.setHorizontalAlignment(SwingConstants.CENTER);
		kalenderBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		kalenderBtn.setForeground(Color.GREEN);

		notizenTop = new JPanel();
		notizenTop.setLayout(new BoxLayout(notizenTop, BoxLayout.X_AXIS));

		einlesenProgBar = new JProgressBar(0, 100);

		filter = new JTextField();

		sortierung = new JComboBox<String>(new String[] { "nach neu", "nach alt" });

		notizenBottom = new JPanel();
		notizenBottom.setLayout(new BoxLayout(notizenBottom, BoxLayout.Y_AXIS));

		beenden = new JButton("Exit");

		status = new JLabel("Status");

		kalender = new MonatsFeld();

		jahre = new JComboBox<Integer>(new Integer[] { 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021 });
		jahre.setSelectedItem(new GregorianCalendar().get(Calendar.YEAR));
		
		monate = new JComboBox<String>(new String[] { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli",
				"August", "September", "Oktober", "November", "Dezember" });
		
		monate.setSelectedItem(new MonatsFeld().getMonth(new GregorianCalendar()));

		notizEingabe = new JTextField();
		notizEingabe.setEnabled(false);

		notizAnzeige = new JList<Notiz>();
		notizAnzeige.setCellRenderer(new NotizListRenderer());

		untereKnoepfe = new JPanel();
		untereKnoepfe.setLayout(new BoxLayout(untereKnoepfe, BoxLayout.X_AXIS));
		erstellen = new JButton("Erstellen");
		erstellen.setName("Erstellen");
		loeschen = new JButton("Löschen");
		loeschen.setEnabled(false);
		abbrechen = new JButton("Abbrechen");
		abbrechen.setEnabled(false);

		notizScrollBar = new JScrollPane(notizAnzeige);
	}

	public void addWidgets() {

		getContentPane().add(BorderLayout.PAGE_START, header);
		getContentPane().add(BorderLayout.CENTER, mainView);
		getContentPane().add(BorderLayout.WEST, sideBar);

		sideBar.add(notizenBtn);
		sideBar.add(kalenderBtn);
		sideBar.add(Box.createVerticalGlue());
		sideBar.add(status);
		sideBar.add(beenden);

		mainView.add(mainViewNotizen, "notizen");
		mainView.add(mainViewKalender, "kalender");

		mainViewNotizen.add(notizenTop);
		mainViewNotizen.add(notizScrollBar);
		mainViewNotizen.add(einlesenProgBar);
		mainViewNotizen.add(notizenBottom);

		notizenTop.add(filter);
		notizenTop.add(sortierung);

		mainViewKalender.add(jahre);
		mainViewKalender.add(monate);
		mainViewKalender.add(kalender);

		notizenBottom.add(notizEingabe);
		notizenBottom.add(untereKnoepfe);

		untereKnoepfe.add(erstellen);
		untereKnoepfe.add(loeschen);
		untereKnoepfe.add(abbrechen);

	}

	public void setupInteractions() {
		notizenBtn.addActionListener(new notizenÖffnen());
		kalenderBtn.addActionListener(new kalenderÖffnen());
		beenden.addActionListener(new ExitTrayListener());
		erstellen.addActionListener(new notizErstellen());
		notizAnzeige.addMouseListener(new notizAnwaehlen());
		loeschen.addActionListener(new notizLöschen());
		abbrechen.addActionListener(new notizAbbrechen());
		monate.addActionListener(new monthDropDownListener());
		jahre.addActionListener(new monthDropDownListener());
		
	}

	// Listener
	public void notizenEinfügen() {

		listModel.clear();
		notizAnzeige.setCellRenderer(new NotizListRenderer());
		for (int i = 0; i < notizListe.size(); i++) {
			listModel.addElement(notizListe.get(i));
		}
		notizAnzeige.setModel(listModel);
	}

	public class notizAnwaehlen implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			index = notizAnzeige.locationToIndex(e.getPoint());
			if (notizListe.get(index).getNotiz() != null) {
				loeschen.setEnabled(true);
				abbrechen.setEnabled(true);
				notizEingabe.setEnabled(true);
				erstellen.setText("Bearbeiten");
				notizEingabe.setText(notizListe.get(index).getNotiz());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	public class notizLöschen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			notizListe.remove(index);
			notizenEinfügen();
			notizEingabe.setText("");
			notizEingabe.setEnabled(false);
			loeschen.setEnabled(false);
			abbrechen.setEnabled(false);
			erstellen.setText("Erstellen");
		}

	}

	public class notizAbbrechen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			abbrechen.setEnabled(false);
			loeschen.setEnabled(false);
			notizEingabe.setEnabled(false);
			notizEingabe.setText("");
			erstellen.setText("Erstellen");
		}

	}

	public class notizErstellen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (erstellen.getText().equals("Erstellen")) {
				erstellen.setText("Speichern");
				abbrechen.setEnabled(true);
				notizEingabe.setEnabled(true);
				System.out.println("Erstellen");
			} else if (erstellen.getText().equals("Speichern")) {
				erstellen.setText("Erstellen");
				abbrechen.setEnabled(false);
				notizEingabe.setEnabled(false);

				String text = notizEingabe.getText();
				if (!text.trim().equals("")) {
					Notiz n = new Notiz(text.trim());
					System.out.println(text);
					notizListe.add(n);
					notizenEinfügen();
					notizEingabe.setText("");
					System.out.println("Speichern");
				}
			} else if (erstellen.getText().equals("Bearbeiten")) {
				erstellen.setText("Erstellen");
				loeschen.setEnabled(false);
				abbrechen.setEnabled(false);
				notizEingabe.setEnabled(false);

				String text2 = notizEingabe.getText();
				if (!text2.trim().equals("")) {
					notizListe.get(index).setNotiz(text2);
					notizenEinfügen();
					notizEingabe.setText("");
				}
			}

		}
	}

	public class notizenÖffnen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainLayout.show(mainView, "notizen");
		}

	}

	public class kalenderÖffnen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainLayout.show(mainView, "kalender");
		}

	}

	// Tray-Funktion
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
				fw = new FileWriter(Main.SIA);
				fw.flush();
				fw.close();
				fw = new FileWriter(Main.properties);
				fw.write(getContentPane().getHeight() + System.lineSeparator() + getContentPane().getWidth());

				fw.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.exit(0);

		}

	}
	
	private class monthDropDownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String monat = (String) monate.getSelectedItem();
			int jahr = (int)jahre.getSelectedItem();
//			System.out.println(monat);
//			System.out.println(jahr);
			mainViewKalender.remove(kalender);
			kalender = new MonatsFeld(monat, jahr);
//			kalender.createWidgets();
//			kalender.addWidgets();
//			kalender.validate();
//			kalender.repaint();
//			kalender.getM
			mainViewKalender.add(kalender);
			mainLayout.show(mainView, "notizen");
			mainLayout.show(mainView, "kalender");
		}
		
	}
	
}
