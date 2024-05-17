package Game;

import java.util.ArrayList;

import javax.swing.JButton;

public class SystemButton extends JButton {

	private static ArrayList<SystemButton> buttons = new ArrayList<>();

	public SystemButton(String title, String name, int x, int y, int width, int height) {
		this.setText(title);
		this.setName(name);
		this.setBounds(x, y, width, height);
		buttons.add(this);
	}

	public static ArrayList<SystemButton> getButtons() {
		return buttons;
	}
}