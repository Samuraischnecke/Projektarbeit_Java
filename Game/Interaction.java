package Game;

public class Interaction {
	
	private static String actionName = "";
	
	// Interaction logic will happen here with combinations of:
	// gameState: NPCs may change to say something different after progressing) (not in, yet)
	// scene: different locations, items may move with player character, NPC may reappear in another place (not in, yet)
	// action: inspect, take, use, talk, chew, sniff
	// object: NPCs, items, interactables of a scene (will not be saved, only trigger processInteraction)
	
	// action to be saved on every action button toggle
	public static void setAction(String action) {
		actionName = action;
	}

	public static void startInteraction(Object object) {
		if (actionName.isEmpty()) {
			System.out.println("Was soll ich damit machen?");
		} else {
			processInteraction(object);
		}
		actionName = ""; // reset action
	}
	
	// processInteraction always triggers from a game object trigger
	private static void processInteraction(Object object) {
		System.out.println("Szene:" + Scene.getScene());
		System.out.println("Aktion: " + actionName);
		System.out.println("Object: " + object.getTitle());
		System.out.println("Objekt Status: " + object.getState());
	}
}
