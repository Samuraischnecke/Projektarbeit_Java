package Game;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ImageButton extends JButton {
	
	// This button is bound to an ImageObject
	// It handles the logic and interactability

	private String title; // Readable object name in game
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state

	public ImageButton(String title, String name) {
		this.title = title;
		this.name = name;
		// Make button square invisible
		this.setContentAreaFilled(false);
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	// Title is being used to display the object name in text boxes
	public String getTitle() {
		return title;
	}

	// ToDo: Find out why method required for functionally
	//even if not directly called
	public String getName() {
		return name;
	}

	// Used by InteractionHandler
	public int getState() {
		return state;
	}

	// Handle object visibility and track current state
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
	// revalidate and repaint after setting new hitbox, because it would cause
	// errors
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.revalidate();
		this.repaint();
	}
}
