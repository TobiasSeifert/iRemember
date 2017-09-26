package gui;

import java.awt.GridLayout;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

public class MonatsFeld extends JPanel {

	public MonatsFeld() {

		setLayout(new GridLayout(7, 5, 5, 5));

		addWidgets();

	}

	public void addWidgets() {

		int row = 1;
		int column = 1;
		int day = 1;
		while (day < GregorianCalendar.AUGUST) {

		}

	}
}
