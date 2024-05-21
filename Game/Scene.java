package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Scene extends JPanel implements MouseListener {
	private int scene = 0;
	private ArrayList<Object> currentSceneObjects = new ArrayList<>();
	private ArrayList<Object> previousSceneObjects = new ArrayList<>();
	private Object box, mouse, tree, wool, door, cheese;

	public static int currentBoxState = 1;
	public static int currentMouseState = 1;
	public static int currentTreeState = 1;
	public static int currentWoolState = 1;
	public static int currentDoorState = 1;
	public static int currentCheeseState = 0;

	public Scene() {
		this.setOpaque(false);
		this.setBounds(10, 10, 1260, 650);
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
		for (Object object : getPreviousSceneObjects()) {
			this.remove(object);
		}
		for (Object object : getCurrentSceneObjects()) {
			object.addMouseListener(this);
			this.add(object);
		}
		this.revalidate();
		this.repaint();
	}

	private void createAllObjects() {
		box = new Object("Box", "box", currentBoxState, 100, 200, 90, 90);
		mouse = new Object("Maus", "mouse", currentMouseState, 200, 200, 90, 90);
		tree = new Object("Baum", "tree", currentTreeState, 300, 200, 90, 90);
		wool = new Object("Wollknäul", "wool", currentWoolState, 400, 200, 90, 90);
		door = new Object("Tür", "door", currentDoorState, 500, 200, 90, 90);
		cheese = new Object("Käse", "cheese", currentCheeseState, 600, 200, 90, 90);
	}

	public void changeObjectState(Object object, int state) {
		object.setState(state);
		this.revalidate();
		this.repaint();
	}

	public void highlightCurrentObjects(boolean highlightObject) {
		if (highlightObject) {
			for (Object object : getCurrentSceneObjects()) {
				object.setEnabled(false);
			}
		} else {
			for (Object object : getCurrentSceneObjects()) {
				object.setEnabled(true);
			}
		}
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int newScene) {
		scene = newScene;

		previousSceneObjects.clear();
		for (Object object : getCurrentSceneObjects()) {
			previousSceneObjects.add(object);
		}

		currentSceneObjects.clear();
		calculateScene();
	}

	public ArrayList<Object> getPreviousSceneObjects() {
		return previousSceneObjects;
	}

	public ArrayList<Object> getCurrentSceneObjects() {
		return currentSceneObjects;
	}

	public Object getMouse() {
		return mouse;
	}

	public Object getWool() {
		return wool;
	}

	public Object getCheese() {
		return cheese;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Main.gameActionButtonGroup.clearSelection();
		InteractionHandler.startInteraction(((Object) e.getSource()));
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
		Main.getGameObjectText().setText(((Object) e.getSource()).getTitle());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Main.getGameObjectText().setText("");
	}

}
