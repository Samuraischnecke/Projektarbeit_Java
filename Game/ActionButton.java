package Game;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import java.util.ArrayList;

public class ActionButton extends JToggleButton {

	private GameActionListener gameActionListener;
	private static ArrayList<ActionButton> buttons = new ArrayList<>();

	public ActionButton(String title, String name, int x, int y, int width, int height) {
		gameActionListener = new GameActionListener();
		this.setText(title);
		this.setName(name);
		this.setBounds(x, y, width, height);
		this.setMargin(new Insets(0, 0, 0, 0));
		this.addActionListener(gameActionListener);
		buttons.add(this);
		setIcon("paw");
	}

	// Output a horizontal line to signal start of new action
	// Then ask question related to each action.
	private void processAction(String action) {
		Main.write("-------------------", false);
		switch (action) {
		default:
			// Unknown action has been performed somehow:
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
		}
		// sets currently toggled action button in InteractionHandler
		InteractionHandler.setAction(action);
	}

	// Constructor and InteractionHandler can use this to change active item icon
	public void setIcon(String item) {
		if (this.getName() == "item") {
			this.setEnabled(true);
			this.setIcon(new ImageIcon(this.getClass().getResource("/images/" + item + ".png")));
			this.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/paw.png")));
			this.setDisabledSelectedIcon(new ImageIcon(this.getClass().getResource("/images/paw.png")));
		}
	}

	// used in Main to fill game menu with action buttons
	public static ArrayList<ActionButton> getButtons() {
		return buttons;
	}

	// on ActionButton toggle, write context to log
	// and save selection in InteractionHandler
	public class GameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			processAction(((JToggleButton) e.getSource()).getName());
		}
	}
}
