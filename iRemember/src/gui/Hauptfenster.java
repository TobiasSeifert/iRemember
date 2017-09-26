package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFrame;

public class Hauptfenster extends JFrame{
	
	private BufferedImage icon;
	
	public Hauptfenster() {
		
		setTitle("IRemember");
		try {
			icon = ImageIO.read(Hauptfenster.class.getClassLoader().getResourceAsStream("gui/Icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setIconImage(icon);
		setLocationRelativeTo(null);
		
	}
	
}
