package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import gui.Hauptfenster;

public class Demo {
	
	public static void main(String[] args) {
	
	
		SwingUtilities.invokeLater(new Runnable() {
		@Override
		public void run () {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		JFrame frame = new Hauptfenster();
				
		frame.setVisible(true);
	
	}
});
		
	}
}
