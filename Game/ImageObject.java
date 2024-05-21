package Game;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageObject extends JPanel {
	
	private ImageButton button;
	private JLabel imageSpace;

	private String title; // Readable object name in game
	private String name; // Identifier that will be used for logic
	private int state, width, height; // modifier for current object state

	public ImageObject(String title, String name, int state, int x, int y, int width, int height) {
		this.title = title;
		this.name = name;
		this.state = state;
		this.width = width;
		this.height = height;
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		if (state == 0) {
			this.setVisible(false);
		}		
		imageSpace = new JLabel();
		this.add(imageSpace);
		
		setIcon();
		
//		this.setBorder(BorderFactory.createEmptyBorder());
		
		button = new ImageButton(title, name, state, 10, 10, 100, 100);
		button.setName(name);
		this.add(button);
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

	public void setState(int state) {
		button.setState(state);
		if (this.state != 0 && state == 0) {
			// Hide previously visible object
			this.setVisible(false);
		} else if (this.state == 0 && state != 0) {
			// Show previously hidden object
			this.setVisible(true);
		}
		this.state = state;
		this.setIcon();
	}

	public Object getObjectByName(String objectName) {
		return this;
	}

	private void setIcon() {
		if (state != 0) {
			imageSpace.setBounds(0, 0, width, height);
			imageSpace.setIcon(new ImageIcon(this.getClass().getResource("/images/" + name + state + ".png")));
		}
	}

}