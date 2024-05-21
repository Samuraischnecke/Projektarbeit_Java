package Game;

import java.util.List;

import util.FileReader;

public class InteractionHandler {

	private static String actionName = "";
	private static String itemName = "";
	private static List<List<String>> script = FileReader.getScript();

	// Interaction logic will happen here with combinations of:
	// gameState: NPCs may change to say something different after progressing) (not
	// in, yet)
	// scene: different locations, items may move with player character, NPC may
	// reappear in another place (not in, yet)
	// action: inspect, take, use, talk, scratch, sniff
	// object: NPCs, items, interactables of a scene (will not be saved, only
	// trigger processInteraction)

	// action to be saved on every action button toggle
	public static void setAction(String action) {
		actionName = action;
	}

	public static void startInteraction(Object object) {
		if (actionName.isEmpty()) {
			Main.write("Was soll ich mit " + object.getTitle() + " machen?", false);
		} else {
			processInteraction(object);
		}
		actionName = ""; // reset action
	}

	// processInteraction always triggers from a game object trigger, other required
	// data needs to be collected
	private static void processInteraction(Object object) {
		String currentScene = String.valueOf(Main.getGameContent().getScene());
		String currentObjectName = object.getName();
		String currentObjectState = String.valueOf(object.getState());
		String currentActionName = actionName;
		String currentItemName = itemName;

		for (List<String> line : script) {
			if ((line.get(0).equals(currentScene) && line.get(1).equals(currentObjectName)
					&& line.get(2).equals(currentObjectState) && line.get(4).equals(currentActionName))
					&& (currentActionName == "item" && line.get(5).equals(currentItemName)
							|| currentActionName != "item")) {
				// Action results in game progress:
				if (!line.get(6).isEmpty()) {
					progressGame(line.get(6), object);
					Main.write(line.get(7), true);
				// Action has no result but flavour text:
				} else {
					Main.write(line.get(7), false);
				}
				break;
			}
		}
	}

	private static void progressGame(String result_action, Object object) {
		switch (result_action) {
		default:
			Main.write("ERROR: result_action nicht bekannt!", true);
			break;
		case "drop_wool":
			Main.getGameContent().changeObjectState(object, 2);
			Main.getGameContent().getWool().setState(2);
			Main.getGameContent().getWool().setBounds(400, 300, 90, 90);
			// ToDo: Move wool object
			break;
		case "take_wool":
			itemName = "wool";
			Main.getGameContent().getWool().setState(0);
			Main.getItemButton().setEnabled(true);
			break;
		case "attach_wool":
			itemName = "";
			Main.getGameContent().changeObjectState(object, 2);
			Main.getItemButton().setEnabled(false);
			break;
		case "open_box":
			Main.getGameContent().changeObjectState(object, 3);
			Main.getGameContent().getMouse().setState(2);
			Main.getGameContent().getMouse().setBounds(600, 300, 90, 90);
			Main.getGameContent().getCheese().setState(1);
			break;
		case "start_dialog":
			Main.write("Hallo Herr Katzi.", false);
			Main.write("Hallo Herr Mausi.", false);
			Main.write("Blablabla, so traurig hier eingesperrt zu sein...", false);
			Main.write("Oh nein, Mitleid! Hier Schlüssel!.", false);
			Main.getGameContent().changeObjectState(object, 3);
			itemName = "key";
			Main.getItemButton().setEnabled(true);
			break;
		case "open_door":
			Main.getGameContent().changeObjectState(object, 2);
			Main.getItemButton().setEnabled(false);
			break;
		case "leave_room":
			// ToDo: Change scene to 2
			break;
		}
	}

	public static String getItemName() {
		return itemName;
	}
}
