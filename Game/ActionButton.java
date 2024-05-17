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
			Main.write("Was soll ich tun?");
			break;
		case "inspect":
			Main.write("Was soll ich mir näher anschauen?");
			break;
		case "take":
			Main.write("Was soll ich aufheben?");
			break;
		case "use":
			Main.write("Was soll ich benutzen?");
			break;
		case "talk":
			Main.write("Mit wem soll ich sprechen?");
			break;
		case "chew":
			Main.write("Woran soll ich knabbern?");
			break;
		case "sniff":
			Main.write("Woran soll ich schnüffeln?");
			break;
		case "item":
			Main.write("ITEM BENUTZEN WIP");
			if (Main.getGameContent().getScene() == 1) {
				Main.getGameContent().setScene(2);
			} else {
				Scene.currentBoxState = 2;
				Main.getGameContent().setScene(1);
			}
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
