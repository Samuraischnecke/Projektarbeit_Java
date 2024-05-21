package Game;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Object extends JButton {
	
	private String title; // Readable object name ingame
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state

	public Object(String title, String name, int state, int x, int y, int width, int height) {
		this.title = title;
		this.name = name;
		this.state = state;
		this.setBounds(x, y, width, height);	// needs to be set to image dimensions
		if (state == 0) {
			this.setVisible(false);
		}
//		setIcon();
		this.setBorder(BorderFactory.createEmptyBorder());
//		this.setContentAreaFilled(false);
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
//		this.setIcon();
	}
	
	public Object getObjectByName(String objectName) {
		return this;
	}
	
	private void setIcon() {
		this.setIcon(new ImageIcon(this.getClass().getResource("/images/" + name + state + ".png")));
		// The disabled state of the Object (JButton) button does not impact functionality,
		// so I can swap to a highlighted image by using setEnabled(false)
		this.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/" + name + state + "highlight" + ".png")));
	}
}
