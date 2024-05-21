package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JToggleButton;

public class ActionButton extends JToggleButton {

	private GameActionListener gameActionListener;
	private static ArrayList<ActionButton> buttons = new ArrayList<>();

	public ActionButton(String title, String name, int x, int y, int width, int height) {
		gameActionListener = new GameActionListener();
		this.setText(title);
		this.setName(name);
		this.setBounds(x, y, width, height);
		this.addActionListener(gameActionListener);
		buttons.add(this);
	}

	public static ArrayList<ActionButton> getButtons() {
		return buttons;
	}

	public void setIcon() {

	}

	private void processAction(String action) {
		switch (action) {
		default:
			Main.write("Was soll ich tun?", false);
			break;
		case "inspect":
			Main.write("Was soll ich mir näher anschauen?", false);
			break;
		case "take":
			Main.write("Was soll ich aufheben?", false);
			break;
		case "use":
			Main.write("Was soll ich benutzen?", false);
			break;
		case "talk":
			Main.write("Mit wem soll ich sprechen?", false);
			break;
		case "scratch":
			Main.write("Woran soll ich meine Krallen wetzen?", false);
			break;
		case "sniff":
			Main.write("Woran soll ich schnüffeln?", false);
			break;
		case "item":
			Main.write("Womit soll ich das verwenden?", false);
			break;
		case "highlight":
			// Highlight Action will be handled seperately in Main Action Listener
			break;
		}
		InteractionHandler.setAction(action);
	}

	public class GameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processAction(((JToggleButton) e.getSource()).getName());
		}
	}
}
