package DataStructures;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class NotizListRenderer extends JTextArea implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Notiz notiz = (Notiz) value;

		Border auﬂen = new TitledBorder(LineBorder.createGrayLineBorder(), notiz.toString());
		
		this.setLayout(new GridLayout());
		this.setBorder(auﬂen);
        this.setEditable(false);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        this.setOpaque(false);
		this.setText(notiz.getNotiz());

		return this;
	}

}