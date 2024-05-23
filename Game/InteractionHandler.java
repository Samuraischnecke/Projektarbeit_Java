package Game;

import java.util.List;

import util.FileReader;

public class InteractionHandler {

	private static String actionName = "";
	private static String itemName = "";
	private static List<List<String>> script = FileReader.getScript();

	// Interaction logic will happen here with combinations of:
	// scene: different locations, NPCs and Items may move to different scenes
	// action: inspect, take, use, talk, scratch, sniff
	// object: NPCs, items, all interactables of a scene

	public static void startInteraction(ImageButton button) {
		// Asks question if no action is active before clicking a scene object:
		if (actionName.isEmpty()) {
			Main.write("Was soll ich mit " + button.getTitle() + " machen?", false);
		} else {
			// continue interaction if action is set and object clicked
			processInteraction(button);
		}
		actionName = ""; // reset action after completing interaction
	}

	// processInteraction() always triggers from a game object button trigger.
	// Other required game state data needs to be collected to search
	// the result of an interaction by combination in the script.csv
	private static void processInteraction(ImageButton button) {
		String currentScene = String.valueOf(Main.getGameContent().getScene());
		String currentObjectName = button.getName();
		String currentObjectState = String.valueOf(button.getState());
		String currentActionName = actionName;
		String currentItemName = itemName;

		for (List<String> line : script) {
			// The script uses columns for each search property.
			// Every Interaction looks for the line that equals all current properties:
			if ((line.get(0).equals(currentScene) && line.get(1).equals(currentObjectName)
					&& line.get(2).equals(currentObjectState) && line.get(4).equals(currentActionName))
					&& (currentActionName == "item" && line.get(5).equals(currentItemName)
							|| currentActionName != "item")) {
				// Trigger state change, move things, ... when action results in progress:
				// (Also output connected flavor text as a green success message)
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

	// When an action results in progress, some things need to be changed.
	// An Object State may change, an Object may move, an Item may be picked up, ...
	// This is handled here by using the "result_action" from the script.csv:
	private static void progressGame(String result_action) {
		switch (result_action) {
		default:
			// result_action is not known - Display Error:
			Main.write("ERROR: 404 Meow (^w^)", true);
			break;
		case "drop_wool":
			Main.getGameContent().getTree().setState(2); // Change tree to scratched tree
			Main.getGameContent().getWool().setState(1); // Spawn wool item on ground
			break;
		case "take_wool":
			itemName = "wool";
			Main.getItemButton().setIcon(itemName); // Set wool to active item
			Main.getGameContent().getWool().setState(0); // Remove wool from scene
			break;
		case "attach_wool":
			clearItem(); // Clear active item slot after using item
			Main.getGameContent().getBox().setState(2); // Change box to box with string attached
			break;
		case "open_box":
			Main.getGameContent().getBox().setState(3); // Change box with string to open box
			// Remove box as clickable item from world to not confuse with overlapping objects:
			Main.getGameContent().getBox().remove(Main.getGameContent().getBox().getButton());
			
			Main.getGameContent().getCheese().setState(1); // Spawn cheese
			Main.getGameContent().getMouse().setState(2); // Change mouse image to awake
			Main.getGameContent().getMouse().setBounds(660, 260, 189, 222); // Move mouse object to cheese
			// Mouse also has a new hitbox since she was wider when asleep and higher when awake:
			Main.getGameContent().getMouse().getButton().setBounds(0, 0, 189, 222);
			break;
		case "start_dialog":
			// Write multiple lines of dialogue between protagonist and NPC
			// This could be made prettier, like showing a short video sequence later
			Main.write("Mausi: Schönen guten Tag, Herr Katzi.", false);
			Main.write("„Hollerö! Weißt du, wie ich hier wieder rauskomme? Die Tür ist zugesperrt!“", false);
			Main.write("Mausi: „Hm, mal schauen, ob der Schlüssel hier wieder irgendwo steckt...“", false);
			// Change mouse state to not trigger the "start_dialog" result action again when talking more:
			Main.getGameContent().getMouse().setState(3);
			itemName = "key";
			Main.getItemButton().setIcon(itemName); // Set key to active item
			break;
		case "open_door":
			clearItem(); // Clear active item slot after using item
			Main.getGameContent().getDoor().setState(2); // Replace closed door with open door
			break;
		case "leave_room":
			// Show end screen after leaving room
			// ToDo: Change to setScene(2) when adding to the game
			Main.getEndScreen().setVisible(true);
			break;
		}
	}

	// action to be saved on every ActionButton toggle
	public static void setAction(String action) {
		actionName = action;
	}

	// clear currently held item (after using item or restarting the game)
	public static void clearItem() {
		itemName = "";
		Main.getItemButton().setEnabled(false);
	}
}
