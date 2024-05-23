package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;

import java.util.ArrayList;

public class Scene extends JLayeredPane implements MouseListener {
	private int scene = 0;
	private ArrayList<ImageObject> currentSceneObjects = new ArrayList<>();
	private ArrayList<ImageObject> previousSceneObjects = new ArrayList<>();
	private ImageObject box, mouse, tree, wool, door, cheese;

	private static int currentBoxState = 1;
	private static int currentMouseState = 1;
	private static int currentTreeState = 1;
	private static int currentWoolState = 0;
	private static int currentDoorState = 1;
	private static int currentCheeseState = 0;

	public Scene() {
		this.setOpaque(false);
		this.setBounds(0, 0, 1280, 660);
		this.setLayout(null);

		createAllObjects();
	}

	private void calculateScene() {
		if (scene == 1) {
			currentSceneObjects.add(box);
			currentSceneObjects.add(mouse);
			currentSceneObjects.add(tree);
			currentSceneObjects.add(wool);
			currentSceneObjects.add(door);
			currentSceneObjects.add(cheese);
		} else if (scene == 2) {
//			currentSceneObjects.add(tree);
		}
		for (ImageObject object : getPreviousSceneObjects()) {
			this.remove(object);
		}
		for (ImageObject object : getCurrentSceneObjects()) {
			object.getButton().addMouseListener(this);
			this.add(object);
		}
		this.revalidate();
		this.repaint();
	}

	private void createAllObjects() {
		box = new ImageObject("Box", "box", currentBoxState, 550, 100, 525, 513);
		box.getButton().setBounds(150, 50, 250, 250);
		mouse = new ImageObject("Maus", "mouse", currentMouseState, 1100, 400, 231, 137);
		mouse.getButton().setBounds(0, 0, 231, 137);
		tree = new ImageObject("Baum", "tree", currentTreeState, 350, -100, 275, 565);
		tree.getButton().setBounds(0, 0, 275, 565);
		wool = new ImageObject("Wollknäul", "wool", currentWoolState, 500, 390, 100, 100);
		wool.getButton().setBounds(0, 0, 100, 100);
		door = new ImageObject("Tür", "door", currentDoorState, 20, 100, 411, 371);
		door.getButton().setBounds(0, 0, 411, 371);
		cheese = new ImageObject("Käse", "cheese", currentCheeseState, 615, 55, 525, 513);
		cheese.getButton().setBounds(248, 190, 103, 108);
	}

	public void changeObjectState(ImageObject object, int state) {
		object.setState(state);
		this.revalidate();
		this.repaint();
	}

	// The disabled state of the ImageObject (JButton) button does not impact
	// functionality, so we can use it to highlight an image
	public void highlightCurrentObjects(boolean highlightObject) {
		if (highlightObject) {
			for (ImageObject object : getCurrentSceneObjects()) {
				object.getButton().setEnabled(false);
			}
		} else {
			for (ImageObject object : getCurrentSceneObjects()) {
				object.getButton().setEnabled(true);
			}
		}
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int newScene) {
		scene = newScene;

		previousSceneObjects.clear();
		for (ImageObject object : getCurrentSceneObjects()) {
			previousSceneObjects.add(object);
		}

		currentSceneObjects.clear();
		calculateScene();
		
		if (scene == 1) {
			// Scene1 object order - lowest number will be rendered last / displayed on top
			this.setComponentZOrder(box, 3);
			this.setComponentZOrder(mouse, 1);
			this.setComponentZOrder(tree, 2);
			this.setComponentZOrder(wool, 1);
			this.setComponentZOrder(door, 3);
			this.setComponentZOrder(cheese, 2);
		}
	}

	public ArrayList<ImageObject> getPreviousSceneObjects() {
		return previousSceneObjects;
	}

	public ArrayList<ImageObject> getCurrentSceneObjects() {
		return currentSceneObjects;
	}

	public ImageObject getBox() {
		return box;
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

	public ImageObject getDoor() {
		return door;
	}

	public ImageObject getCheese() {
		return cheese;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Main.getGameActionButtonGroup().clearSelection();
		InteractionHandler.startInteraction(((ImageButton) e.getSource()));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Main.getGameObjectLabel().setText(((ImageButton) e.getSource()).getTitle());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Main.getGameObjectLabel().setText("");
	}

}
