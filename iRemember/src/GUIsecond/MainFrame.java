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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import DataStructures.Notiz;
import gui.MonatsFeld;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private CardLayout mainLayout = new CardLayout();

	private JLabel header;

	private JPanel mainView;

	private JPanel mainViewNotizen;
	private JPanel notizenTop;
	private JTextField filter;
	private JComboBox<String> sortierung;
	private JList<Notiz> notizenListe;
	private JProgressBar einlesenProgBar;
	private JPanel notizenBottom;
	// TODO Bearbeiten und Erstellen

	private JPanel mainViewKalender;
	private MonatsFeld kalender;

	private JPanel sideBar;
	private JButton notizenBtn;
	private JButton kalenderBtn;
	private JLabel status;
	private JButton beenden;

	public MainFrame() {
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

		// setSize(800, 600);
		pack();

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

		notizenListe = new JList<Notiz>();

		einlesenProgBar = new JProgressBar(0,100);

		filter = new JTextField();

		sortierung = new JComboBox<String>(new String[] {"nach neu", "nach alt"});
		
		notizenBottom = new JPanel();
		
		beenden = new JButton("Exit");
		
		status = new JLabel("Status");
		
		kalender = new MonatsFeld();
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
		mainViewNotizen.add(notizenListe);
		mainViewNotizen.add(einlesenProgBar);
		mainViewNotizen.add(notizenBottom);

		notizenTop.add(filter);
		notizenTop.add(sortierung);
		
		mainViewKalender.add(kalender);

	}
	
	public void setupInteractions() {
		notizenBtn.addActionListener(new notizen÷ffnen());
		kalenderBtn.addActionListener(new kalender÷ffnen());
		beenden.addActionListener(new beenden());
	}
	
	//Listener
	
	public class notizen÷ffnen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			mainLayout.show(mainView, "notizen");	
		}
		
	}
	
	public class kalender÷ffnen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			mainLayout.show(mainView, "kalender");	
		}
		
	}
	
	public class beenden implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(-2);	
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
			TrayIcon icon = new TrayIcon(pic, "÷ffen...", popup);
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
