package DataStructures;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class NotizListRenderer extends JLabel implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Notiz notiz = (Notiz) value;

		Border auﬂen = new TitledBorder(LineBorder.createGrayLineBorder(), notiz.toString());
		
		this.setBorder(auﬂen);
		this.setText(notiz.getNotiz());

		return this;
	}

}