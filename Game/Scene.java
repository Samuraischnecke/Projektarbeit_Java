package Game;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Scene extends JPanel implements MouseListener {
	private int scene = 0;
	private ArrayList<Object> currentSceneObjects = new ArrayList<>();
	private ArrayList<Object> previousSceneObjects = new ArrayList<>();
	private Object box, cat;
	
	public static int currentBoxState = 1;

	public Scene() {
		this.setBackground(Color.GREEN);
		this.setBounds(10, 10, 1260, 650);
		this.setLayout(null);
		
		createAllObjects();
	}

	private void calculateScene() {
		if (scene == 1) {
			currentSceneObjects.add(box);
		} else if (scene == 2) {
			currentSceneObjects.add(cat);
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
		box = new Object("Box", "box", 1, 200, 100, 500, 500);
		cat = new Object("Cat", "cat", 1, 500, 250, 250, 250);
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
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}
