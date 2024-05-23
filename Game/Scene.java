package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;

import java.util.ArrayList;

public class Scene extends JLayeredPane implements MouseListener {
	private int scene = 0;
	private ArrayList<ImageObject> currentSceneObjects = new ArrayList<>();
	private ArrayList<ImageObject> previousSceneObjects = new ArrayList<>();
	private ImageObject box, cheese, door, mouse, tree, wool;

	// Initial states of each item
	// ToDo: When more scenes are added to the game, find a way to refactor each
	// scene logic in own class?
	private static final int initialBoxState = 1;
	private static final int initialCheeseState = 0; // cheese has not spawned while in the box
	private static final int initialDoorState = 1;
	private static final int initialMouseState = 1;
	private static final int initialTreeState = 1;
	private static final int initialWoolState = 0; // wool has not spawned while it's on tree

	public Scene() {
		this.setOpaque(false);
		this.setBounds(0, 0, 1280, 660);
		this.setLayout(null);
	}

	private void calculateScene() {
		if (scene == 1) {
			currentSceneObjects.add(box);
			currentSceneObjects.add(cheese);
			currentSceneObjects.add(door);
			currentSceneObjects.add(mouse);
			currentSceneObjects.add(tree);
			currentSceneObjects.add(wool);
		} else if (scene == 2) {
			// ToDo: Add scene2 objects and so on...
//			currentSceneObjects.add(outside-stuff);
		}
		for (ImageObject object : previousSceneObjects) {
			// Remove old ImageObjects AND all contained functionality like the button area
			object.removeAll();
			this.remove(object);
		}
		for (ImageObject object : currentSceneObjects) {
			object.getButton().addMouseListener(this);
			this.add(object);
		}
	}

	// creates objects with initial states
	// ToDo: Needs to be adjusted after adding database functionality to create
	// objects from saved state instead
	private void createAllObjects() {
		box = new ImageObject("Box", "box", initialBoxState, 550, 100, 525, 513);
		box.getButton().setBounds(150, 50, 250, 250);
		cheese = new ImageObject("Käse", "cheese", initialCheeseState, 615, 55, 525, 513);
		cheese.getButton().setBounds(248, 190, 103, 108);
		door = new ImageObject("Tür", "door", initialDoorState, 20, 100, 411, 371);
		door.getButton().setBounds(0, 0, 411, 371);
		mouse = new ImageObject("Maus", "mouse", initialMouseState, 1100, 400, 231, 137);
		mouse.getButton().setBounds(0, 0, 231, 137);
		tree = new ImageObject("Baum", "tree", initialTreeState, 350, -100, 275, 565);
		tree.getButton().setBounds(0, 0, 275, 565);
		wool = new ImageObject("Wollknäul", "wool", initialWoolState, 500, 390, 100, 100);
		wool.getButton().setBounds(0, 0, 100, 100);
	}

	// Called from startNewGame(), restartGame() or InteractionHandler
	// to change scene
	public void setScene(int newScene) {
		scene = newScene;

		previousSceneObjects.clear();
		for (ImageObject object : currentSceneObjects) {
			previousSceneObjects.add(object);
		}
		currentSceneObjects.clear();

		createAllObjects();
		calculateScene();

		// ToDo: Instead of adding more if-cases, move each scene to own class maybe?
		if (scene == 1) {
			// Scene1 object order - lowest number will be rendered last / displayed on top
			this.setComponentZOrder(box, 3);
			this.setComponentZOrder(door, 3);
			this.setComponentZOrder(cheese, 0);
			this.setComponentZOrder(mouse, 0);
			this.setComponentZOrder(tree, 2);
			this.setComponentZOrder(wool, 1);
		}
	}

	// Called from InteractionHandler to trigger object change and repaint scene
	public void changeObjectState(ImageObject object, int state) {
		object.setState(state);
		this.revalidate();
		this.repaint();
	}

	public int getScene() {
		return scene;
	}

	// get objects from scene (used by InteractionHandler):

	public ImageObject getBox() {
		return box;
	}

	public ImageObject getCheese() {
		return cheese;
	}

	public ImageObject getDoor() {
		return door;
	}

	public ImageObject getMouse() {
		return mouse;
	}

	public ImageObject getTree() {
		return tree;
	}

	public ImageObject getWool() {
		return wool;
	}

	@Override
	// When a scene object is clicked,
	// clear toggled action button and start interaction
	public void mouseClicked(MouseEvent e) {
		Main.getGameActionButtonGroup().clearSelection();
		InteractionHandler.startInteraction(((ImageButton) e.getSource()));
	}

	@Override
	// show label on hover
	public void mouseEntered(MouseEvent e) {
		Main.getGameObjectLabel().setText(((ImageButton) e.getSource()).getTitle());
	}

	@Override
	// clear label on unhover
	public void mouseExited(MouseEvent e) {
		Main.getGameObjectLabel().setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
