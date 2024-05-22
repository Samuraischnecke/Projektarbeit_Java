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

	public static void startInteraction(ImageButton button) {
		if (actionName.isEmpty()) {
			Main.write("Was soll ich mit " + button.getTitle() + " machen?", false);
		} else {
			processInteraction(button);
		}
		actionName = ""; // reset action
	}

	// processInteraction always triggers from a game object trigger, other required
	// data needs to be collected
	private static void processInteraction(ImageButton button) {
		String currentScene = String.valueOf(Main.getGameContent().getScene());
		String currentObjectName = button.getName();
		String currentObjectState = String.valueOf(button.getState());
		String currentActionName = actionName;
		String currentItemName = itemName;

		for (List<String> line : script) {
			if ((line.get(0).equals(currentScene) && line.get(1).equals(currentObjectName)
					&& line.get(2).equals(currentObjectState) && line.get(4).equals(currentActionName))
					&& (currentActionName == "item" && line.get(5).equals(currentItemName)
							|| currentActionName != "item")) {
				// Action results in game progress:
				if (!line.get(6).isEmpty()) {
					progressGame(line.get(6));
					Main.write(line.get(7), true);
				// Action has no result but flavor text:
				} else {
					Main.write(line.get(7), false);
				}
				break;
			}
		}
	}

	private static void progressGame(String result_action) {
		switch (result_action) {
		default:
			Main.write("ERROR: 404 Meow (^w^)", true);
			break;
		case "drop_wool":
			Main.getGameContent().getTree().setState(2);	// Tree has been scratched
			Main.getGameContent().getWool().setState(1);	// Wool has been dropped
			break;
		case "take_wool":
			itemName = "wool";
			Main.getItemButton().setIcon(itemName);
			Main.getGameContent().getWool().setState(0);	// Wool has been picked up
			break;
		case "attach_wool":
			itemName = "";
			Main.getGameContent().getBox().setState(2);		// Wool has been attached to box
			Main.getItemButton().setEnabled(false);
			break;
		case "open_box":
			Main.getGameContent().getBox().setState(3);		// Box has opened
			Main.getGameContent().getBox().remove(Main.getGameContent().getBox().getButton());	// Box is not clickable anymore
			Main.getGameContent().getCheese().setState(1);	// The cheese has spawned
			Main.getGameContent().getMouse().setState(2);	// Mouse is awake
			Main.getGameContent().getMouse().setBounds(660, 260, 189, 222);	// Mouse runs to the cheese
			Main.getGameContent().getMouse().getButton().setBounds(0, 0, 189, 222);	// Mouse has new hitbox
			break;
		case "start_dialog":
			Main.write("Mausi: Schönen guten Tag, Herr Katzi.", false);
			Main.write("„Hollerö! Weißt du, wie ich hier wieder rauskomme? Die Tür ist zugesperrt!“", false);
			Main.write("Mausi: „Hm, mal schauen, ob der Schlüssel hier wieder irgendwo steckt...“", false);
			Main.getGameContent().getMouse().setState(3);	// Mouse has completed dialog
			itemName = "key";
			Main.getItemButton().setIcon(itemName);
			break;
		case "open_door":
			Main.getGameContent().getDoor().setState(2);	// Door has been opened
			Main.getItemButton().setEnabled(false);
			break;
		case "leave_room":
			Main.write("Katzi entschwindet durch die Tür.", true);
			Main.showEndScreen();
			break;
		}
	}

	public static String getItemName() {
		return itemName;
	}
}
