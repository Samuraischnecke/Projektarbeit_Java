package Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.*;

import util.ColoredPanel;
import util.TextAreaOutputStream;

public class Main extends JFrame implements ActionListener, MouseListener {

	private JLayeredPane gameSpace;
	private JPanel background, gameContent, gameMenu, systemMenu;
	
	private JScrollPane gameTextBox;
	private JTextArea gameText;
	
	private ButtonGroup gameActionButtonGroup;
	private ArrayList<ActionButton> gameButtons;
	private ActionButton buttonInspect, buttonTake, buttonUse, buttonTalk, buttonChew, buttonSniff, buttonItem, buttonHighlight;
	private SystemButton buttonSystem, buttonContinue, buttonNew, buttonSave, buttonLoad, buttonClose;
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int OFFSET_WIDTH = 16;
	private static final int OFFSET_HEIGHT= 39;
	private static final Dimension RESOLUTION = new Dimension(WIDTH, HEIGHT);
	private static final Dimension WINDOW_SIZE = new Dimension(WIDTH + OFFSET_WIDTH, HEIGHT + OFFSET_HEIGHT);
	

	private static final Font menuFont = new Font("Arial", 0, 28);
	private static final Font gameFont = new Font("Consola", 0, 20);

	public static void main(String[] args) {
		Main m = new Main();
		m.setLocationRelativeTo(null);
		m.setVisible(true);
	}

	private Main() {
		this.initComponents();
//		openMariaDB();
	}

	private void initComponents() {
		this.setSize(WINDOW_SIZE); // 1280 x 800 Resolution PLUS Title Bar and Borders
		this.setResizable(false);
		this.setLayout(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/cat.png"))));
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create all game components (Game Panel, Menu Panels & Buttons)
		createGameSpace();
	}

	public void changeFont(Component component, Font font) {
		component.setFont(font);
		if (component instanceof Container) {
			for (Component child : ((Container) component).getComponents()) {
				changeFont(child, font);
			}
		}
	}

	private void createGameSpace() {
		gameSpace = new JLayeredPane();
		gameSpace.setLayout(null);
		gameSpace.setSize(RESOLUTION);

		background = ColoredPanel.create(Color.CYAN, 0, 0, 1280, 800);
		
		gameSpace.add(background, JLayeredPane.DEFAULT_LAYER);

		this.add(gameSpace);
		
		createSystemMenu();
	}

	private void createGameContent() {
		gameContent = ColoredPanel.create(Color.GREEN, 10, 10, 1260, 650);

		gameSpace.add(gameContent, JLayeredPane.PALETTE_LAYER);
		
		gameText = new JTextArea();
		gameText.setBackground(Color.BLACK);
		gameText.setForeground(Color.WHITE);
		gameText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		gameText.setFont(gameFont);
		gameText.setEnabled(false);
		gameText.setLineWrap(true);
		gameText.setWrapStyleWord(true);
		
		// Redirect System.out.println() statements to the ingame textBox
		TextAreaOutputStream outputStream = new TextAreaOutputStream(gameText);
		System.setOut(new PrintStream(outputStream));
		
		gameTextBox = new JScrollPane(gameText);
		gameTextBox.setBounds(15, 15, 450, 105);		
		gameTextBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gameTextBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		gameSpace.add(gameTextBox, JLayeredPane.MODAL_LAYER);
	}

	private void createGameMenu() {
		gameMenu = ColoredPanel.create(Color.ORANGE, 10, 670, 1260, 120);

		gameSpace.add(gameMenu, JLayeredPane.PALETTE_LAYER);

		createGameMenuButtons();
	}

	private void createSystemMenu() {
		systemMenu = ColoredPanel.create(Color.ORANGE, 1000, 355, 270, 315);

		gameSpace.add(systemMenu, JLayeredPane.MODAL_LAYER);

		createSystemMenuButtons();
	}

	private void createGameMenuButtons() {		
		buttonInspect = new ActionButton("Untersuchen", "inspect", 10, 10, 250, 45);
		buttonTake = new ActionButton("Nehmen", "take", 10, 65, 250, 45);
		buttonUse = new ActionButton("Benutzen", "use", 295, 10, 250, 45);
		buttonTalk = new ActionButton("Sprechen", "talk", 295, 65, 250, 45);
		buttonChew = new ActionButton("Knabbern", "chew", 580, 10, 250, 45);
		buttonSniff = new ActionButton("Schnüffeln", "sniff", 580, 65, 250, 45);
		buttonItem = new ActionButton("ITEM", "item", 865, 10, 100, 100);

		gameActionButtonGroup = new ButtonGroup();
		
		for (ActionButton button : ActionButton.getButtons()) {
			button.addActionListener(this);
			gameActionButtonGroup.add(button);
			gameMenu.add(button);
		}
		
		buttonHighlight = new ActionButton("Katzenaugen", "highlight", 1000, 65, 250, 45);
		buttonSystem = new SystemButton("Hauptmenü", "system", 1000, 10, 250, 45);
		
		buttonHighlight.addActionListener(this);
		buttonSystem.addActionListener(this);
		
		gameMenu.add(buttonHighlight);
		gameMenu.add(buttonSystem);
		
		changeFont(gameMenu, menuFont);
	}

	private void createSystemMenuButtons() {
		buttonContinue = new SystemButton("Fortsetzen", "continue", 10, 10, 250, 45);
		buttonSave = new SystemButton("Speichern", "save", 10, 80, 250, 45);
		buttonLoad = new SystemButton("Laden", "load", 10, 135, 250, 45);
		buttonNew = new SystemButton("Neues Spiel", "new", 10, 205, 250, 45);
		buttonClose = new SystemButton("Beenden", "close", 10, 260, 250, 45);
		
		buttonContinue.setEnabled(false);
		buttonSave.setEnabled(false);
		buttonLoad.setEnabled(false);	// ToDo: Remove at database implementation
		
		for (SystemButton button : SystemButton.getButtons()) {
			button.addActionListener(this);
			systemMenu.add(button);
		}
		
		changeFont(systemMenu, menuFont);
	}
	
	private void startNewGame() {
		createGameMenu();
		createGameContent();
		
		buttonContinue.setEnabled(true);
		// buttonSave.setEnabled(true);		// ToDo: Uncomment on database implementation
		buttonNew.setEnabled(false);		// ToDo: Remove after fixing issues with calling startNewGame from running game
		
		// ToDo: Build scene 1!
		Scene.setScene(1);
		// ToDo: Move that shit where it belongs (into Scene handler for example)
		Object someObject = new Object("Box", "box", 1, 250, 250, 250, 250);
		someObject.addMouseListener(this);
		gameContent.add(someObject);		
		
		systemMenu.setVisible(false);
		System.out.println("Neues Spiel gestartet!");
	}
	
	private void startFromLoad() {
		createGameMenu();
		createGameContent();
		
		// ToDo: Check system button states and change them accordingly after loading game

		// ToDo: Build scene X! Something like this maybe:
//		Scene.buildScene(database.loadedScene);
//		Object.setObjectStates(database.getObjectStates);
		
		systemMenu.setVisible(false);
		System.out.println("Spiel geladen!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonContinue) {
			systemMenu.setVisible(false);
		}
		if (e.getSource() == buttonSave) {
			startNewGame();		// build new game
			systemMenu.setVisible(false);
			// ToDo: database implementation
		}
		if (e.getSource() == buttonLoad) {
			startFromLoad();	// open database table selection
			systemMenu.setVisible(false);
			// ToDo: database implementation
		}
		if (e.getSource() == buttonNew) {
			if (Scene.getScene() == 0) {
				startNewGame();
			} else {
				// ToDo: warning dialog before starting new game
				// Are you sure you want to start a new game?				
			}
		}
		if (e.getSource() == buttonClose) {
			// ToDo: warning dialog before closing, save game?
			this.dispose();
		}
		
		if (e.getSource() == buttonSystem) {
			systemMenu.setVisible(!systemMenu.isVisible());
			gameActionButtonGroup.clearSelection();
			Interaction.setAction("");
		}
		if (e.getSource() == buttonHighlight) {
			systemMenu.setVisible(false);
			// ToDo: trigger current game objects highlight
			// MAYBE this should also be a ToggleButton? Something like this:
			// Scene.highlightCurrentObjects();
		}
		
		if (e.getSource() instanceof ActionButton) {
			// system menu can only be hidden from this class
			systemMenu.setVisible(false);
			// JToggleButtons now have 2 Action Listeners!
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		gameActionButtonGroup.clearSelection();
		Interaction.startInteraction(((Object) e.getSource()));
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
