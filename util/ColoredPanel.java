package util;

import java.awt.Color;

import javax.swing.JPanel;

public class ColoredPanel {
	
	public static JPanel create(Color color, int x, int y, int width, int height) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		panel.setBounds(x, y, width, height);
		panel.setLayout(null);
		return panel;
	}

}
