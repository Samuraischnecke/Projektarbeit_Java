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

	private void processAction(String action) {
		switch (action) {
		default:
			System.out.println("Was soll ich tun?");
			break;
		case "inspect":
			System.out.println("Was soll ich mir naeher anschauen?");
			break;
		case "take":
			System.out.println("Was soll ich aufheben?");
			break;
		case "use":
			System.out.println("Was soll ich benutzen?");
			break;
		case "talk":
			System.out.println("Mit wem soll ich sprechen?");
			break;
		case "chew":
			System.out.println("Woran soll ich knabbern?");
			break;
		case "sniff":
			System.out.println("Woran soll ich schnueffeln?");
			break;
		case "highlight":
			// Highlight Action will be handled seperately in Main Action Listener
			break;
		}
		Interaction.setAction(action);
	}

	public class GameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processAction(((JToggleButton) e.getSource()).getName());
		}
	}
}
