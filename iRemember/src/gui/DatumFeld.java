package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DatumFeld extends JPanel {

	private JButton anzeige;
	private int monatsTag;


	public DatumFeld(int monatsTag) {
		this.monatsTag = monatsTag;
		setLayout(new BorderLayout(5, 5));
		
		createWidgets();
		addWidgets();

	}
	
	public void createWidgets() {
		
		anzeige = new JButton("" + monatsTag);
		
	}
	
	public void addWidgets() {
		
		this.add(BorderLayout.CENTER, anzeige);
		
	}
	
	public int getMonatsTag() {
		return monatsTag;
	}

}
