package gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Hauptfenster extends JFrame {

	public Hauptfenster() {
		setLayout(new BorderLayout());
				
		setTitle("iRemember");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocation(300, 200);
		setSize(800, 600);
		try {
			setIconImage(ImageIO.read(Hauptfenster.class.getClassLoader().getResourceAsStream("gui/Icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
public class Test {
			  Image image = Toolkit.getDefaultToolkit().getImage("gui/Icon.png");

			  TrayIcon trayIcon = new TrayIcon(image, "Tester2");

			  public void main(String[] a) throws Exception {
			    if (SystemTray.isSupported()) {
			      SystemTray tray = SystemTray.getSystemTray();

			      trayIcon.setImageAutoSize(true);
			      trayIcon.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			          System.out.println("In here");
			          trayIcon.displayMessage("Tester!", "Some action performed", TrayIcon.MessageType.INFO);
			        }
			      });

			      try {
			        tray.add(trayIcon);
			      } catch (AWTException e) {
			        System.err.println("TrayIcon could not be added.");
			      }
			    }
			  }
			}
		
	
}