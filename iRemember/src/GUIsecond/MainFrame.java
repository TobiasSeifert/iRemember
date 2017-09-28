package GUIsecond;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import DataStructures.Notiz;
import DataStructures.NotizListRenderer;
import DataStructures.NotizListe;
import gui.MonatsFeld;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private ImageIcon kalenderIcon;
	private ImageIcon notizIcon;
	private ImageIcon exitIcon;
	private ImageIcon rightIcon;
	private ImageIcon leftIcon;

	private int index;

	private List<Notiz> notizListe = new NotizListe<Notiz>();
	private List<Notiz> flüchtigeListe = new NotizListe<Notiz>();

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

	private JTextArea notizEingabe;

	private JPanel untereKnoepfe;
	private JButton erstellen;
	private JButton loeschen;
	private JButton abbrechen;

	private JPanel mainViewKalender;
	private MonatsFeld kalender;
	private JComboBox<Integer> jahre;
	private JPanel jahrPnl;
	private JButton jahrLeft;
	private JButton jahrRight;
	private JComboBox<String> monate;
	private JPanel monatePnl;
	private JButton monatLeft;
	private JButton monatRight;

	private JPanel sideBar;
	private JButton notizenBtn;
	private JButton kalenderBtn;
	private JLabel status;
	private JButton beenden;

	private int width, height, Window_Location_X, Window_Location_Y;
	private String list_sorting;

	public MainFrame() {

		setHeight_Width_Location();
		setLayout(new BorderLayout(5, 5));
		setTitle("iRemember");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(Window_Location_X, Window_Location_Y);

		try {
			setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/taskBarImg2.png")));
			kalenderIcon = new ImageIcon(
					ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/kalender.png")));
			notizIcon = new ImageIcon(
					ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/notiz.png")));
			exitIcon = new ImageIcon(
					ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/exit.png")));
			rightIcon = new ImageIcon(
					ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/arrowRight.png")));
			leftIcon = new ImageIcon(
					ImageIO.read(MainFrame.class.getClassLoader().getResourceAsStream("Images/arrowLeft.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}

		createWidgets();
		addWidgets();
		setupInteractions();
		loadNotes();

		new aktualisieren1().execute();
		addWindowListener(new TrayListener(this));

		setSize(width, height);
		// pack();

	}

	private void loadNotes() {

		int value;

		File directory = new File((System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes"));

		try {
			value = directory.listFiles().length;

		} catch (NullPointerException e) {
			value = 0;
			System.out.println("value = 0");
		}

		for (int i = 0; i <= value; i++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(
						System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt"));

				Notiz n = new Notiz(br.readLine());
				notizListe.add(n);

			} catch (FileNotFoundException e) {
				System.out.println(i + " = Ende der Notiz-Liste");
				// e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notizenEinfügen();

	}

	private void setHeight_Width_Location() {
		try {
			BufferedReader bufr = new BufferedReader(new FileReader(Main.properties));
			// width = Integer.parseInt(bufr.readLine());
			width = Integer.parseInt(bufr.readLine().substring(14));
			height = Integer.parseInt(bufr.readLine().substring(15));
			Window_Location_X = Integer.parseInt(bufr.readLine().substring(20));
			Window_Location_Y = Integer.parseInt(bufr.readLine().substring(20));
			list_sorting = bufr.readLine().substring(14);
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

		notizenBtn = new JButton();
		notizenBtn.setIcon(notizIcon);
		notizenBtn.setHorizontalAlignment(SwingConstants.CENTER);
		notizenBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		notizenBtn.setForeground(Color.GREEN);

		kalenderBtn = new JButton();
		kalenderBtn.setIcon(kalenderIcon);
		kalenderBtn.setHorizontalAlignment(SwingConstants.CENTER);
		kalenderBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		kalenderBtn.setForeground(Color.GREEN);

		notizenTop = new JPanel();
		notizenTop.setLayout(new BoxLayout(notizenTop, BoxLayout.X_AXIS));
		notizenTop.setMaximumSize(new Dimension(1920, 50));

		einlesenProgBar = new JProgressBar(0, 100);

		filter = new JTextField();
		filter.setText("");
		filter.setPreferredSize(new Dimension(250, 40));
		filter.setMaximumSize(new Dimension(250, 40));

		sortierung = new JComboBox<String>(new String[] { "nach neu", "nach alt" });
		sortierung.setPreferredSize(new Dimension(100, 40));
		sortierung.setMaximumSize(new Dimension(250, 40));
		sortierung.setSelectedItem(list_sorting);

		notizenBottom = new JPanel();
		notizenBottom.setLayout(new BoxLayout(notizenBottom, BoxLayout.Y_AXIS));
		notizenBottom.setMaximumSize(new Dimension(1920, 200));
		notizenBottom.setPreferredSize(new Dimension(1920, 200));

		beenden = new JButton();
		beenden.setIcon(exitIcon);

		status = new JLabel("Status");

		kalender = new MonatsFeld();

		jahre = new JComboBox<Integer>(new Integer[] { 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
				2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027,
				2028, 2029, 2030 });
		jahre.setSelectedItem(new GregorianCalendar().get(Calendar.YEAR));

		monate = new JComboBox<String>(new String[] { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli",
				"August", "September", "Oktober", "November", "Dezember" });

		monate.setSelectedItem(new MonatsFeld().getMonth(new GregorianCalendar()));

		notizEingabe = new JTextArea();
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

		jahrPnl = new JPanel();
		jahrPnl.setLayout(new BoxLayout(jahrPnl, BoxLayout.X_AXIS));
		jahrPnl.setMaximumSize(new Dimension(1920, 30));
		jahrLeft = new JButton();
		jahrLeft.setIcon(leftIcon);
		jahrRight = new JButton();
		jahrRight.setIcon(rightIcon);

		monatePnl = new JPanel();
		monatePnl.setLayout(new BoxLayout(monatePnl, BoxLayout.X_AXIS));
		monatePnl.setMaximumSize(new Dimension(1920, 30));
		monatRight = new JButton();
		monatRight.setIcon(rightIcon);
		monatLeft = new JButton();
		monatLeft.setIcon(leftIcon);
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

		notizenTop.add(Box.createHorizontalGlue());
		notizenTop.add(filter);
		notizenTop.add(sortierung);

		mainViewKalender.add(jahrPnl);
		mainViewKalender.add(monatePnl);
		mainViewKalender.add(kalender);

		jahrPnl.add(jahrLeft);
		jahrPnl.add(jahre);
		jahrPnl.add(jahrRight);

		monatePnl.add(monatLeft);
		monatePnl.add(monate);
		monatePnl.add(monatRight);

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
		monatRight.addActionListener(new plusMonthButtonListener());
		monatLeft.addActionListener(new minusMonthButtonListener());
		jahrRight.addActionListener(new plusYearButtonListener());
		jahrLeft.addActionListener(new minusYearButtonListener());
		sortierung.addActionListener(new sortierungEinstellen());
		filter.addCaretListener(new listeFiltern());

	}

	public void notizenEinfügen() {

		listModel.clear();
		notizAnzeige.setCellRenderer(new NotizListRenderer());
		if (sortierung.getSelectedItem().equals("nach neu")) {
			for (int i = 0; i < notizListe.size(); i++) {
				listModel.addElement(notizListe.get(i));
			}
		} else if (sortierung.getSelectedItem().equals("nach alt")) {
			for (int i = notizListe.size(); i > 0; i--) {
				listModel.addElement(notizListe.get(i - 1));
			}
		}
		notizAnzeige.setModel(listModel);
	}

	// Listener
	public class listeFiltern implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {

			String filtertxt = filter.getText();
			char[] filterarray = filtertxt.toCharArray();
			int filterLänge = filterarray.length;
			int notizPosition = 1;

			if (filtertxt.length() > 0) {

				listModel.clear();
				notizAnzeige.setCellRenderer(new NotizListRenderer());

				for (int i = 0; i < notizListe.size(); i++) {

					String notiztxt = notizListe.get(i).getNotiz();
					char[] notizarray = notiztxt.toCharArray();

					if (notiztxt.length() >= filtertxt.length()) {

						notizSchleife: { // dieses dumme CodeLabel ist essentiell!
							for (int j = 0; j < notizarray.length; j++) {

								if (filterarray[0] == notizarray[j]) {

									if (filtertxt.length() == 1) {

										listModel.addElement(notizListe.get(i));
										break;

									}
									if (filtertxt.length() > 1) {
										for (int k = 0; k < filterLänge - 1; k++) {
											if (notizarray[notizPosition + k] != filterarray[k + 1]) {
												break notizSchleife;

											}
											System.out.println(k);
											if (k == filterLänge - 2) {
												listModel.addElement(notizListe.get(i));
												break notizSchleife;

											}

										}

										notizPosition++;

									}
								}
							}
						}
					}
				}

				notizAnzeige.setModel(listModel);

			} else if (filtertxt.length() <= 0) {

				notizenEinfügen();

			}

		}

	}

	public class sortierungEinstellen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			notizenEinfügen();
		}

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
				notizEingabe.setText(notizAnzeige.getModel().getElementAt(index).getNotiz());
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
			} else if (erstellen.getText().equals("Speichern")) {
				erstellen.setText("Erstellen");
				abbrechen.setEnabled(false);
				notizEingabe.setEnabled(false);

				String text = notizEingabe.getText();
				if (!text.trim().equals("")) {
					Notiz n = new Notiz(text.trim());
					notizListe.add(n);
					notizenEinfügen();
					notizEingabe.setText("");
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
				fw.write("Window_Width: " + getWidth() + System.lineSeparator() + "Window_Height: " + getHeight()
						+ System.lineSeparator() + "Window_Locastion_X: " + (int) getLocation().getX()
						+ System.lineSeparator() + "Window_Locastion_Y: " + (int) getLocation().getY()
						+ System.lineSeparator() + "List_Sorting: " + sortierung.getSelectedItem());

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

			int jahr = (int) jahre.getSelectedItem();

			mainViewKalender.remove(kalender);
			kalender = new MonatsFeld(monat, jahr);
			mainViewKalender.add(kalender);
			mainLayout.show(mainView, "notizen");
			mainLayout.show(mainView, "kalender");
		}

	}

	private class plusMonthButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(monate.getSelectedIndex() + 1 >= monate.getItemCount())) {
				String monat = monate.getItemAt(monate.getSelectedIndex() + 1);
				monate.setSelectedIndex(monate.getSelectedIndex() + 1);

				int jahr = (int) jahre.getSelectedItem();

				mainViewKalender.remove(kalender);
				kalender = new MonatsFeld(monat, jahr);

				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");

			} else {
				String monat = monate.getItemAt(0);
				monate.setSelectedIndex(0);

				int jahr = (int) jahre.getSelectedItem() + 1;
				jahre.setSelectedIndex(jahre.getSelectedIndex() + 1);
				mainViewKalender.remove(kalender);
				kalender = new MonatsFeld(monat, jahr);

				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");

			}
		}

	}

	private class minusMonthButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(monate.getSelectedIndex() - 1 < 0)) {
				String monat = monate.getItemAt(monate.getSelectedIndex() - 1);
				monate.setSelectedIndex(monate.getSelectedIndex() - 1);

				int jahr = (int) jahre.getSelectedItem();

				mainViewKalender.remove(kalender);
				kalender = new MonatsFeld(monat, jahr);

				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");

			} else {
				String monat = monate.getItemAt(11);
				monate.setSelectedIndex(11);

				int jahr = (int) jahre.getSelectedItem() - 1;
				jahre.setSelectedIndex(jahre.getSelectedIndex() - 1);

				mainViewKalender.remove(kalender);
				kalender = new MonatsFeld(monat, jahr);

				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");

			}

		}

	}

	private class plusYearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String monat = (String) monate.getSelectedItem();
			int jahr = (int) jahre.getSelectedItem() + 1;
			jahre.setSelectedIndex(jahre.getSelectedIndex() + 1);

			mainViewKalender.remove(kalender);
			kalender = new MonatsFeld(monat, jahr);
			mainViewKalender.add(kalender);
			mainLayout.show(mainView, "notizen");
			mainLayout.show(mainView, "kalender");

		}

	}

	private class minusYearButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String monat = (String) monate.getSelectedItem();

			int jahr = (int) jahre.getSelectedItem() - 1;
			jahre.setSelectedIndex(jahre.getSelectedIndex() - 1);

			mainViewKalender.remove(kalender);
			kalender = new MonatsFeld(monat, jahr);
			mainViewKalender.add(kalender);
			mainLayout.show(mainView, "notizen");
			mainLayout.show(mainView, "kalender");

		}

	}

	private class aktualisieren1 extends SwingWorker<MonatsFeld, Integer> {

		private GregorianCalendar gc;

		@Override
		protected MonatsFeld doInBackground() throws Exception {
			while (true) {
				gc = new GregorianCalendar();
				if (!(gc.get(Calendar.DAY_OF_MONTH) == kalender.getHeute().get(Calendar.DAY_OF_MONTH))) {

					mainViewKalender.remove(kalender);
					kalender = new MonatsFeld();
					jahre.setSelectedItem(kalender.getHeute().get(Calendar.YEAR));
					monate.setSelectedItem(kalender.getHeute().get(Calendar.MONTH));
					return kalender;
				}
			}
		}

		protected void done() {
			try {
				kalender = get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			} finally {
				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");
				new aktualisieren2().execute();
			}
		}
	}

	private class aktualisieren2 extends SwingWorker<MonatsFeld, Integer> {

		private GregorianCalendar gc;

		@Override
		protected MonatsFeld doInBackground() throws Exception {
			while (true) {
				gc = new GregorianCalendar();
				if (!(gc.get(Calendar.DAY_OF_MONTH) == kalender.getHeute().get(Calendar.DAY_OF_MONTH))) {
					mainViewKalender.remove(kalender);
					kalender = new MonatsFeld();
					jahre.setSelectedItem(kalender.getHeute().get(Calendar.YEAR));
					monate.setSelectedItem(kalender.getHeute().get(Calendar.MONTH));
					return kalender;
				}
			}
		}

		protected void done() {
			try {
				kalender = get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				mainViewKalender.add(kalender);
				mainLayout.show(mainView, "notizen");
				mainLayout.show(mainView, "kalender");
				new aktualisieren1().execute();
			}
		}
	}
}
