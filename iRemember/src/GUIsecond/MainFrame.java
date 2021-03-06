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
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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

	private NotizListe<String, Notiz> notizListe = new NotizListe<String, Notiz>();

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

	Color panels = new Color(209, 209, 209);
	Color headerc = new Color(28, 134, 238);

	public MainFrame() {

		// setEnabled(false);
		setHeight_Width_Location();
		setLayout(new BorderLayout(2, 2));
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

		new aktualisieren1().execute();
		addWindowListener(new TrayListener(this));

		setSize(width, height);
		loadNotes();
//		changeProgressBar();

	}

	private void loadNotes() {

		File directory = new File((System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\"));

		if(directory.list().length > 0) {
			int value = Integer.parseInt(directory.list()[directory.list().length - 1].charAt(0) + "");
	
		for (int i = value; i >= 0; i--) {

			try {
				if (System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt" != null) {
					FileReader fr = new FileReader(
							System.getProperty("user.home") + "\\AppData\\Roaming\\iReminder\\Notes\\" + i + ".txt");
					BufferedReader br = new BufferedReader(fr);

					StringBuilder sb = new StringBuilder();

					String str = br.readLine();

					String line = null;
					while ((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("\n");
					}

					Notiz n = new Notiz(sb.toString());
					n.setName(String.valueOf(i));
					n.setZeitstempel(str);

					notizListe.put("" + i, n);
					br.close();
					fr.close();
				}
			} catch (Exception e) {

			}
		}
		notizenEinfügen();
		}
	}

	private void changeProgressBar() {
		new Thread() {

			@Override
			public void run() {
				int j;
				int zaehler = 0;
				if (notizListe.size() > 0) {
					j = 100 / notizListe.size();
					notizenEinfügen(zaehler);
					// zaehler++;
				} else {
					j = 100;
				}

				for (int i = 0; i <= 100; i++) {
					einlesenProgBar.setValue(i);
					einlesenProgBar.setString("Lesevorgang bei: " + i + "%");
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (i == j && notizListe.size() > 0 && zaehler < notizListe.size() - 1) {
						zaehler++;
						notizenEinfügen(zaehler);
						System.out.println(zaehler);
						j = j + j;

					}
				}
				zaehler++;
				// if(zaehler == notizListe.size()-1) {
				// notizenEinfügen(zaehler);
				// }
				// for(int temp = zaehler+1; temp < notizListe.size(); temp++) {
				//
				// }

				setEnabled(true);
			}
		}.start();

	}

	private void setHeight_Width_Location() {
		try {
			@SuppressWarnings("resource")
			BufferedReader bufr = new BufferedReader(new FileReader(Main.properties));
			width = Integer.parseInt(bufr.readLine().substring(14));
			height = Integer.parseInt(bufr.readLine().substring(15));
			Window_Location_X = Integer.parseInt(bufr.readLine().substring(20));
			Window_Location_Y = Integer.parseInt(bufr.readLine().substring(20));
			list_sorting = bufr.readLine().substring(14);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void createWidgets() {

		mainView = new JPanel();
		mainView.setLayout(mainLayout);

		mainViewNotizen = new JPanel();
		mainViewNotizen.setLayout(new BoxLayout(mainViewNotizen, BoxLayout.Y_AXIS));
		mainViewNotizen.setBackground(panels);

		mainViewKalender = new JPanel();
		mainViewKalender.setLayout(new BoxLayout(mainViewKalender, BoxLayout.Y_AXIS));
		mainViewKalender.setBackground(panels);

		sideBar = new JPanel();
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
		sideBar.setOpaque(true);
		sideBar.setBackground(panels);

		header = new JLabel("iRemember");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(header.getFont().deriveFont(Font.BOLD + Font.ITALIC, 30));
		header.setOpaque(true);
		header.setBackground(headerc);
		header.setForeground(Color.WHITE);

		notizenBtn = new JButton();
		notizenBtn.setIcon(notizIcon);
		notizenBtn.setHorizontalAlignment(SwingConstants.CENTER);
		notizenBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		notizenBtn.setForeground(Color.GREEN);
		notizenBtn.setAlignmentX(CENTER_ALIGNMENT);

		kalenderBtn = new JButton();
		kalenderBtn.setIcon(kalenderIcon);
		kalenderBtn.setHorizontalAlignment(SwingConstants.CENTER);
		kalenderBtn.setFont(header.getFont().deriveFont(Font.BOLD, 20));
		kalenderBtn.setForeground(Color.GREEN);
		kalenderBtn.setAlignmentX(CENTER_ALIGNMENT);

		notizenTop = new JPanel();
		notizenTop.setLayout(new BoxLayout(notizenTop, BoxLayout.X_AXIS));
		notizenTop.setMaximumSize(new Dimension(1920, 50));
		notizenTop.setBackground(panels);

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
		notizenBottom.setBackground(panels);

		beenden = new JButton();
		beenden.setIcon(exitIcon);
		beenden.setAlignmentX(CENTER_ALIGNMENT);

		kalender = new MonatsFeld();

		jahre = new JComboBox<Integer>(new Integer[] { 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
				2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027,
				2028, 2029, 2030 });
		jahre.setSelectedItem(new GregorianCalendar().get(Calendar.YEAR));

		monate = new JComboBox<String>(new String[] { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli",
				"August", "September", "Oktober", "November", "Dezember" });

		monate.setSelectedItem(new MonatsFeld().getMonth(new GregorianCalendar()));

		notizEingabe = new JTextArea(1, 1);
		notizEingabe.setLineWrap(true);
		notizEingabe.setWrapStyleWord(true);

		notizEingabe.setDocument(new PlainDocument() {

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null || notizEingabe.getText().length() >= 500) {
					JOptionPane.showMessageDialog(null, "Warnung: Maximal 500 Zeichen", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				super.insertString(offs, str, a);
			}

		});

		notizAnzeige = new JList<Notiz>();
		notizAnzeige.setCellRenderer(new NotizListRenderer());

		notizScrollBar = new JScrollPane(notizAnzeige);

		untereKnoepfe = new JPanel();
		untereKnoepfe.setLayout(new BoxLayout(untereKnoepfe, BoxLayout.X_AXIS));
		erstellen = new JButton("Speichern");
		erstellen.setEnabled(false);
		erstellen.setName("Speichern");
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

		status = new JLabel();
		status.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		status.setAlignmentX(CENTER_ALIGNMENT);
		status.setForeground(Color.BLACK);
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
		notizEingabe.addCaretListener(new speichernFreigeben());

	}

	@SuppressWarnings("unchecked")
	public void notizenEinfügen() {

		listModel.clear();
		notizAnzeige.setCellRenderer(new NotizListRenderer());
		if (sortierung.getSelectedItem().equals("nach neu")) {
			for (int i = 0; i <= Integer.parseInt(notizListe.lastKey()); i++) {
				if (notizListe.containsKey("" + i)) {
					listModel.addElement(notizListe.get("" + i));
				}
			}
		} else if (sortierung.getSelectedItem().equals("nach alt")) {
			for (int i = Integer.parseInt(notizListe.lastKey()); i >= 0; i--) {
				if (notizListe.containsKey("" + i)) {
					listModel.addElement(notizListe.get("" + (i - 1)));
				}
			}
		}
		notizAnzeige.setModel(listModel);
	}

	@SuppressWarnings("unchecked")
	public void notizenEinfügen(int index) {

		notizAnzeige.setCellRenderer(new NotizListRenderer());
		if (sortierung.getSelectedItem().equals("nach neu")) {

			listModel.addElement(notizListe.get("" + index));

		} else if (sortierung.getSelectedItem().equals("nach alt")) {

			listModel.addElement(notizListe.get("" + (notizListe.size() - 1 - index)));

		}
		notizAnzeige.setModel(listModel);
	}

	// Listener
	public class speichernFreigeben implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			if (notizEingabe.getText().trim().length() > 0) {
				erstellen.setEnabled(true);
			}

		}

	}

	public class listeFiltern implements CaretListener {

		@SuppressWarnings("unchecked")
		@Override
		public void caretUpdate(CaretEvent e) {
			String filtertxt = filter.getText().toLowerCase();
			if (filtertxt.length() > 0) {

				listModel.clear();
				notizAnzeige.setCellRenderer(new NotizListRenderer());

				for (int i = 0; i < notizListe.size(); i++) {
					if (notizListe.containsKey("" + i)) {
						String notiztxt = notizListe.get("" + i).getNotiz().toLowerCase();
						if (notiztxt.contains(filtertxt)) {
							listModel.addElement(notizListe.get("" + i));
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
			loeschen.setEnabled(true);
			abbrechen.setEnabled(true);
			erstellen.setEnabled(true);
			erstellen.setName("Bearbeiten");
			notizEingabe.setText(notizAnzeige.getModel().getElementAt(index).getNotiz());
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

			notizListe.remove(listModel.getElementAt(index).getName());
			if (notizListe.size() > 0) {
				notizenEinfügen();
			}else {
				listModel.clear();
			}
			notizEingabe.setText("");
			erstellen.setEnabled(false);
			loeschen.setEnabled(false);
			abbrechen.setEnabled(false);
			erstellen.setName("Speichern");
		}

	}

	public class notizAbbrechen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			abbrechen.setEnabled(false);
			loeschen.setEnabled(false);
			erstellen.setEnabled(false);
			notizEingabe.setText("");
			erstellen.setName("Speichern");
		}

	}

	public class notizErstellen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (notizEingabe.getText().length() > 500) {
				JOptionPane.showMessageDialog(null, "Nicht mehr als 500 Zeichen", "Hinweis",
						JOptionPane.INFORMATION_MESSAGE);
			}

			if (erstellen.getName().equals("Speichern")) {
				String text = notizEingabe.getText();
				if (text.trim().length() > 0) {
					Notiz n = new Notiz(text.trim());
					n.setErstellzeit();

					if (notizListe.size() > 0) {
						int intIndex = Integer.parseInt(notizListe.lastKey()) + 1;
						n.setName("" + intIndex);
						notizListe.put("" + intIndex, n);
					} else if (notizListe.size() == 0) {
						n.setName("0");
						notizListe.put("0", n);
					}

					notizenEinfügen();
					notizEingabe.setText("");
					erstellen.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Die Notiz benötigt eine Eingabe", "Hinweis",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (erstellen.getName().equals("Bearbeiten")) {
				erstellen.setName("Speichern");
				loeschen.setEnabled(false);
				abbrechen.setEnabled(false);

				String text2 = notizEingabe.getText();
				if (!text2.trim().equals("")) {
					notizListe.get("" + index).setNotiz(text2);
					notizenEinfügen();
					notizEingabe.setText("");
					erstellen.setEnabled(false);
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
				status.setText("<html><body>Status:<br>Notizen: " + notizListe.size() + "<br>Angezeigt: "
						+ notizAnzeige.getModel().getSize() + "</body></html>");
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
				status.setText("<html><body>Status:<br>Notizen   : " + notizListe.size() + "<br>Angezeigt: "
						+ notizAnzeige.getModel().getSize() + "</body></html>");
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
				new aktualisieren1().execute();
			}
		}
	}
}
