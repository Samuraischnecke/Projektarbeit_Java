package Game;

import javax.swing.JButton;

public class ImageButton extends JButton {
	
	private String title; // Readable object name in game
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state
	
	public ImageButton(String title, String name, int state, int x, int y, int width, int height) {
		this.title = title;
		this.name = name;
		this.state = state;
		this.setBounds(x, y, width, height);
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
		if (this.state != 0 && state == 0) {
			// Hide previously visible object
			this.setVisible(false);
		} else if (this.state == 0 && state != 0) {
			// Show previously hidden object
			this.setVisible(true);
		}
		this.state = state;
	}

}
