package Game;

import javax.swing.JButton;

public class Object extends JButton {
	
	private String title; // Readable object name ingame
	private String name; // Identifier that will be used for logic
	private int state; // modifier for current object state
	private int x, y, width, height;
	
	public Object(String title, String name, int state, int x, int y, int width, int height) {
		this.title = title;
		this.name = name;
		this.state = state;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
}
