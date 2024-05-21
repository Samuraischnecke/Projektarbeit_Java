package Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

public class Scene extends JPanel implements MouseListener {
	private int scene = 0;
	private ArrayList<ImageObject> currentSceneObjects = new ArrayList<>();
	private ArrayList<ImageObject> previousSceneObjects = new ArrayList<>();
	private ImageObject box, mouse, tree, wool, door, cheese;

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
		box = new ImageObject("Box", "box", currentBoxState, 700, 120, 525, 513);
		mouse = new ImageObject("Maus", "mouse", currentMouseState, 200, 200, 90, 90);
		tree = new ImageObject("Baum", "tree", currentTreeState, 200, 300, 90, 90);
		wool = new ImageObject("Wollknäul", "wool", currentWoolState, 200, 400, 90, 90);
		door = new ImageObject("Tür", "door", currentDoorState, 300, 200, 90, 90);
		cheese = new ImageObject("Käse", "cheese", currentCheeseState, 300, 300, 90, 90);
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
				object.setEnabled(false);
			}
		} else {
			for (ImageObject object : getCurrentSceneObjects()) {
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
		for (ImageObject object : getCurrentSceneObjects()) {
			previousSceneObjects.add(object);
		}

		currentSceneObjects.clear();
		calculateScene();
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
		Main.gameActionButtonGroup.clearSelection();
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
		Main.getGameObjectText().setText(((ImageButton) e.getSource()).getTitle());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Main.getGameObjectText().setText("");
	}

}
