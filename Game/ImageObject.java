package Game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageObject extends JPanel {
	
	// The image objects only handles displaying the correct image
	// It creates a button to handle events

	private ImageButton button; // created by each imageObject to handle events
	private JLabel imageSpace; // helper JLabel used to display image in Panel

	private String name; // used to set image icon
	private int state; // used to set image icon

	private ImageIcon image; // image currently used in imageSpace

	public ImageObject(String title, String name, int state, int x, int y, int width, int height) {
		this.name = name;
		this.state = state;
		this.setLayout(null);
		this.setOpaque(false);
		this.setBounds(x, y, width, height);

		imageSpace = new JLabel();
		this.add(imageSpace);

		button = new ImageButton(title, name);
		this.add(button);

		// Pass state to button
		setState(state);
	}

	private void setIcon() {
		if (state != 0) {
			// fetch currently used image by object name and state
			image = new ImageIcon(this.getClass().getResource("/images/" + name + state + ".png"));
			imageSpace.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
			imageSpace.setIcon(image);
		}
	}

	public void setState(int state) {
		button.setState(state);
		if (state == 0) {
			// Hide object
			this.setVisible(false);
		} else if (state != 0) {
			// Show previously hidden object
			this.setVisible(true);
		}
		this.state = state;
		this.setIcon();
	}

	// Returns button connected to the object
	// Used by Scene and InteractionHandler to set and move hitboxes
	public JButton getButton() {
		return button;
	}

}