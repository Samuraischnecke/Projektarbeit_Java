package Game;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ImageButton extends JButton {
	
	// Some of these properties are duplicates from ImageObject
	// They are mostly required in both classes, because of functionality

	private String title; // Readable object name in game
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state
	
	public ImageButton(String title, String name) {
		this.title = title;
		this.name = name;
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createEmptyBorder());
	}
	
	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		if (state == 0) {
			// Hide previously visible object
			this.setVisible(false);
		} else if (state != 0) {
			// Show previously hidden object
			this.setVisible(true);
		}
		this.state = state;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.revalidate();
		this.repaint();
	}
}
