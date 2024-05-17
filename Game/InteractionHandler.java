package Game;

import java.util.ArrayList;
import java.util.List;

import util.FileReader;

public class InteractionHandler {

	private static String actionName = "";
	private static String itemName = ""; // ToDo: implement active item
	private static List<List<String>> script = new ArrayList<>();

	// Interaction logic will happen here with combinations of:
	// gameState: NPCs may change to say something different after progressing) (not
	// in, yet)
	// scene: different locations, items may move with player character, NPC may
	// reappear in another place (not in, yet)
	// action: inspect, take, use, talk, chew, sniff
	// object: NPCs, items, interactables of a scene (will not be saved, only
	// trigger processInteraction)

	// action to be saved on every action button toggle
	public static void setAction(String action) {
		actionName = action;
	}

	public static void startInteraction(Object object) {
		if (actionName.isEmpty()) {
			Main.write("Was soll ich damit machen?");
		} else {
			processInteraction(object);
		}
		actionName = ""; // reset action
	}

	// processInteraction always triggers from a game object trigger, other required data needs to be collected
	private static void processInteraction(Object object) {

		script = FileReader.getScript();

		String currentScene = String.valueOf(Main.getGameContent().getScene());
		String currentObjectName = object.getName();
		String currentObjectState = String.valueOf(object.getState());
		String currentActionName = actionName;

		for (List<String> line : script) {
			if (line.get(0).contains(currentScene) && line.get(1).contains(currentObjectName)
					&& line.get(2).contains(currentObjectState) && line.get(4).contains(currentActionName)) {
				if (!line.get(6).isEmpty()) {
					progressGame(line.get(6), object);
				}
				Main.write(line.get(7));
			}
		}
	}

	private static void progressGame(String result_action, Object object) {
		switch (result_action) {
		default:
			Main.write("ERR: result_action nicht bekannt!");
			break;
		case "attach_wool":
			Main.getGameContent().changeObjectState(object, 2);
			break;
		case "open_box":
			Main.getGameContent().changeObjectState(object, 3);
			break;
		}
	}
}
