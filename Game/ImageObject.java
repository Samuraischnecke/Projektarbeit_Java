package Game;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageObject extends JPanel {

	private ImageButton button;
	private JLabel imageSpace;

	private String title; // Readable object name in game
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state
	private ImageIcon image; // image currently used in imageSpace
	private int width, height; // properties used to calculate imageSpace

	public ImageObject(String title, String name, int state, int x, int y, int width, int height) {
		this.title = title;
		this.name = name;
		this.setLayout(null);
		this.setOpaque(false);
		this.setBounds(x, y, width, height);

		imageSpace = new JLabel();
		this.add(imageSpace);

		button = new ImageButton(title, name);
		button.setName(name);
		this.add(button);

		setState(state);		
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

	private void setIcon() {
		if (state != 0) {
			// fetch currently used image by object name and state
			image = new ImageIcon(this.getClass().getResource("/images/" + name + state + ".png"));
			height = image.getIconHeight();
			width = image.getIconWidth();
			imageSpace.setBounds(0, 0, width, height);
			imageSpace.setIcon(image);
		}
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

	public JButton getButton() {
		return button;
	}

	public Object getObjectByName(String objectName) {
		return this;
	}

}