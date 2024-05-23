package Game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.*;

import util.ColoredPanel;
import util.CustomFont;
import util.FileReader;

public class Main extends JFrame implements ActionListener {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int OFFSET_WIDTH = 16; // some sort of border added by frame
	private static final int OFFSET_HEIGHT = 39; // title bar height added by frame
	private static final Dimension RESOLUTION = new Dimension(WIDTH, HEIGHT); // target resolution I chose
	private static final Dimension WINDOW_SIZE = new Dimension(WIDTH + OFFSET_WIDTH, HEIGHT + OFFSET_HEIGHT); // actual
																												// window
																												// size
	private static final Font menuFont = new Font("Fixedsys Excelsior 301", 0, 32); // font used for menu buttons
	private static final Font gameFont = new Font("Fixedsys Excelsior 301", 0, 22); // font used for text log

	private JLayeredPane gameSpace; // Contains all background, menus, game content space, overlays, ...
	private static Scene gameContent; // Contains currently displayed game scene and content
	private JPanel gameMenu, helpMenu, systemMenu; // the 3 topmost spaces with important functionality, info and
													// buttons
	private JLabel sceneBackground, sceneProtagonist, helpScreen; // helper JLabels to place (background) images in
																	// JPanels
	private static JLabel endScreen; // helper needs to be accessed from InteractionHandler
	private ImageIcon background, protagonist, help, end; // images to be placed inside the helper JLabels

	private JScrollPane gameTextBox; // Scrollable space for the ingame log
	private static JTextPane gameText; // Ingame text log, will be written to by InteractionHandler etc.
	private static JLabel gameObjectLabel; // Displays currently hovered object labels
	private static AttributeSet yellow, gray, white, red, green; // Styles used for game text log

	private static FileReader fr; // Helper used to read and save script.csv

	private SystemButton buttonHelp, buttonSystem, buttonContinue, buttonNew, buttonSave, buttonLoad, buttonClose;
	private ActionButton buttonInspect, buttonTake, buttonUse, buttonTalk, buttonScratch, buttonSniff;
	private static ActionButton buttonItem; // Needs to be accessed by Interaction Handler
	private static ButtonGroup gameActionButtonGroup; // group containing all action buttons

	public static void main(String[] args) {
		Main m = new Main();
		CustomFont.getCustomFont(); // load in custom font to use in menus and logs
		m.setLocationRelativeTo(null);
		m.setVisible(true);
	}

	private Main() {
		this.initComponents();
	}

	private void initComponents() {
		this.setSize(WINDOW_SIZE); // 1280 x 800 Resolution PLUS Title Bar and Borders
		this.setResizable(false);
		this.setLayout(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(("/images/logo.png"))));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		fr = new FileReader(); // read script from csv

		createGameSpace(); // creates layered pane for all game content

		getGameContent().setScene(0);
	}

	private void changeFont(Component component, Font font) {
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

		this.add(gameSpace);

		gameContent = new Scene();

		gameSpace.add(getGameContent(), JLayeredPane.PALETTE_LAYER);

		endScreen = new JLabel();
		endScreen.setSize(RESOLUTION);
		end = new ImageIcon(this.getClass().getResource("/images/end.png"));
		endScreen.setIcon(end);
		endScreen.setVisible(false);
		gameSpace.add(endScreen, JLayeredPane.DRAG_LAYER);

		createProtagonist();
		createSceneBackground();

		createSystemMenu();
	}

	private void createGameMenu() {
		gameMenu = ColoredPanel.create(Color.ORANGE, 10, 670, 1260, 120);

		gameSpace.add(gameMenu, JLayeredPane.DRAG_LAYER);

		createGameMenuButtons();
	}

	private void createHelpMenu() {
		helpMenu = ColoredPanel.create(Color.ORANGE, 10, 10, 990, 660);
		helpMenu.setVisible(false);

		gameSpace.add(helpMenu, JLayeredPane.DRAG_LAYER);

		createHelpMenuScreen();
	}

	private void createSystemMenu() {
		systemMenu = ColoredPanel.create(Color.ORANGE, 1000, 355, 270, 315);

		gameSpace.add(systemMenu, JLayeredPane.DRAG_LAYER);

		createSystemMenuButtons();
	}

	private void createGameMenuButtons() {
		buttonInspect = new ActionButton("Untersuchen", "inspect", 10, 10, 250, 45);
		buttonTake = new ActionButton("Nehmen", "take", 10, 65, 250, 45);
		buttonUse = new ActionButton("Benutzen", "use", 295, 10, 250, 45);
		buttonTalk = new ActionButton("Sprechen", "talk", 295, 65, 250, 45);
		buttonScratch = new ActionButton("Kratzen", "scratch", 580, 10, 250, 45);
		buttonSniff = new ActionButton("Schnüffeln", "sniff", 580, 65, 250, 45);
		buttonItem = new ActionButton("", "item", 865, 10, 100, 100);

		gameActionButtonGroup = new ButtonGroup();

		for (ActionButton button : ActionButton.getButtons()) {
			button.addActionListener(this);
			gameActionButtonGroup.add(button);
			gameMenu.add(button);
		}

		buttonHelp = new SystemButton("Katzenaugen", "help", 1000, 65, 250, 45);
		buttonSystem = new SystemButton("Hauptmenü", "system", 1000, 10, 250, 45);

		buttonHelp.addActionListener(this);
		buttonSystem.addActionListener(this);

		gameMenu.add(buttonHelp);
		gameMenu.add(buttonSystem);

		changeFont(gameMenu, menuFont);

		createHelpMenu();
	}

	private void createHelpMenuScreen() {
		helpScreen = new JLabel();
		helpScreen.setBounds(0, 0, 990, 660);
		help = new ImageIcon(this.getClass().getResource("/images/help.png"));
		helpScreen.setIcon(help);
		helpMenu.add(helpScreen);
	}

	private void createSystemMenuButtons() {
		buttonContinue = new SystemButton("Fortsetzen", "continue", 10, 10, 250, 45);
		buttonSave = new SystemButton("Speichern", "save", 10, 80, 250, 45);
		buttonLoad = new SystemButton("Laden", "load", 10, 135, 250, 45);
		buttonNew = new SystemButton("Neues Spiel", "new", 10, 205, 250, 45);
		buttonClose = new SystemButton("Beenden", "close", 10, 260, 250, 45);

		// Can't use continue and save game function during Intro
		buttonContinue.setEnabled(false);
		buttonSave.setEnabled(false);
		
		buttonLoad.setEnabled(false); // ToDo: Remove on database implementation

		for (SystemButton button : SystemButton.getButtons()) {
			button.addActionListener(this);
			systemMenu.add(button);
		}

		changeFont(systemMenu, menuFont);
	}

	// puts the protagonist image in place above the game menu
	private void createProtagonist() {
		sceneProtagonist = new JLabel();
		sceneProtagonist.setBounds(550, 485, 413, 317); // at center
		protagonist = new ImageIcon(this.getClass().getResource("/images/protagonist.png"));
		sceneProtagonist.setIcon(protagonist);
		sceneProtagonist.setVisible(false);

		gameSpace.add(sceneProtagonist, JLayeredPane.POPUP_LAYER);
	}

	// puts the protagonist.png in place above the game menu
	private void createSceneBackground() {
		sceneBackground = new JLabel();
		sceneBackground.setBounds(0, 0, 1280, 800);

		gameSpace.add(sceneBackground, JLayeredPane.DEFAULT_LAYER);

		setSceneBackground();
	}

	// sets the current game state environment background (or intro scene)
	private void setSceneBackground() {
		background = new ImageIcon(this.getClass().getResource("/images/scene" + getGameContent().getScene() + ".png"));
		sceneBackground.setIcon(background);
	}

	private void startNewGame() {
		// creates game menu, text log and protagonist from here
		// Should not be visible in intro screen, yet
		createGameMenu();
		createGameTextLog();
		sceneProtagonist.setVisible(true);

		// sets scene 1
		getGameContent().setScene(1);
		setSceneBackground();
		InteractionHandler.clearItem();

		// Enable continue and save game after Intro
		buttonContinue.setEnabled(true);
		// buttonSave.setEnabled(true); // ToDo: Uncomment on database implementation

		write("Neues Spiel gestartet!", true);
	}

	private void restartGame() {
		// setScene triggers a removal of previous game objects
		// and places new initial game objects
		getGameContent().setScene(1);
		setSceneBackground();

		// reset currently held item and game log
		InteractionHandler.clearItem();
		gameText.setText("");

		write("Spiel neugestartet!", true);
	}

	// ToDo: Use and check after database implementation!
	private void startFromLoad(int scene) {
		// creates game menu, text log and protagonist from here
		// Should not be visible in intro screen, yet
		createGameMenu();
		createGameTextLog();
		sceneProtagonist.setVisible(true);

		// sets scene fetched from selected database entry
		// ToDo: May need to pass more database info (object state) to SceneHandler
		getGameContent().setScene(scene);
		setSceneBackground();

		buttonContinue.setEnabled(true);
		// buttonSave.setEnabled(true); // ToDo: Uncomment on database implementation

		write("Spiel geladen!", true);
	}

	private void createGameTextLog() {
		// This is the game's persistent text log
		gameText = new JTextPane();
		gameText.setBackground(Color.BLACK);
		gameText.setForeground(Color.WHITE);
		gameText.setMargin(new Insets(0, 10, 0, 10));
		gameText.setFont(gameFont);

		// persistent text log is placed inside a scrollable pane
		gameTextBox = new JScrollPane(gameText);
		gameTextBox.setBounds(10, 475, 500, 185);
		gameTextBox.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		// remove ugly scrollbars, scroll is still functional:
		gameTextBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		gameTextBox.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// display of the currently hovered object's label
		gameObjectLabel = new JLabel();
		gameObjectLabel.setBounds(1000, 619, 270, 50);
		gameObjectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		gameObjectLabel.setForeground(Color.WHITE);
		gameObjectLabel.setBackground(Color.BLACK);
		gameObjectLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		gameObjectLabel.setOpaque(true);
		gameObjectLabel.setFont(menuFont);

		gameSpace.add(gameTextBox, JLayeredPane.MODAL_LAYER);
		gameSpace.add(gameObjectLabel, JLayeredPane.MODAL_LAYER);

		// set color styles to use in JTextPane gameTextBox log
		StyleContext sc = StyleContext.getDefaultStyleContext();
		yellow = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.YELLOW);
		gray = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.LIGHT_GRAY);
		white = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.WHITE);
		red = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.YELLOW);
		green = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.GREEN);
	}

	// ToDo: Change OK_CANCEL_OPTION to YES_NO_CANCEL_OPTION on database
	// implementation and add save functionality
	private boolean confirmedRestart() {
		String options[] = { "Ja, Spiel neu starten", "Abbrechen" };
		int optionValue = JOptionPane.showOptionDialog(this,
				"Sind Sie sicher, dass Sie das Spiel neu starten möchten?\nEs gibt noch keinen gespeicherten Spielstand!",
				"Spiel neu starten?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[1]);

		if (optionValue == JOptionPane.OK_OPTION)
			// Bestätigen - Spiel wird neu gestartet
			return true;
		else
			// Abbrechen - Zurück ins Spiel
			return false;
	}

	// ToDo: Change OK_CANCEL_OPTION to YES_NO_CANCEL_OPTION on database
	// implementation and add save functionality
	private boolean confirmedExit() {
		String options[] = { "Ja, Spiel verlassen", "Abbrechen" };
		int optionValue = JOptionPane.showOptionDialog(this,
				"Sind Sie sicher, dass Sie das Spiel beenden möchten?\nDer Spielstand kann leider noch nicht gespeichert werden!",
				"Spiel beenden?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[1]);

		if (optionValue == JOptionPane.OK_OPTION)
			// Bestätigen - Spiel wird beendet
			return true;
		else
			// Abbrechen - Zurück ins Spiel
			return false;
	}

	// Write text to persistent game log
	public static void write(String text, boolean isMessage) {
		// It's not possible to use an "append" text like in a TextArea,
		// so we're locked into "replaceSelection".
		// To prevent accidentally replacing text in the log:
		// reset the current selection to the end of the log before replacing
		gameText.select(gameText.getDocument().getLength(), gameText.getDocument().getLength());

		if (text.startsWith("„")) {
			// dialog is in quotes, will be output in green
			gameText.setCharacterAttributes(yellow, true);
		} else if (text.startsWith("Mausi:") || text.startsWith("-")) {
			// Mausi dialog will be output in lightgray
			gameText.setCharacterAttributes(gray, true);
		} else {
			// default (flavour) text will be output in white
			gameText.setCharacterAttributes(white, true);
		}
		// overwrite color if it's a system message
		if (isMessage) {
			// ERROR message:
			if (text.startsWith("ERROR")) {
				gameText.setCharacterAttributes(red, true);
				// PROGRESS message:
			} else {
				gameText.setCharacterAttributes(green, true);
			}
		}
		if (gameText.getDocument().getLength() != 0) {
			// to prevent an empty line at the bottom, add the line break BEFORE the text
			gameText.replaceSelection("\n" + text);
		} else {
			// no linebreak before text on the first line
			gameText.replaceSelection(text);
		}
		// Automatically scroll to most recent line
		gameText.setCaretPosition(gameText.getDocument().getLength());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonContinue) {
			systemMenu.setVisible(false);
		}
		if (e.getSource() == buttonSave) {
			systemMenu.setVisible(false);
			// ToDo: database implementation
		}
		if (e.getSource() == buttonLoad) {
			systemMenu.setVisible(false);
			// ToDo: database implementation
			// ToDo: load game from database info, something like:
//			startFromLoad(database.getSaveFiles());	// display all save files in a list view with info
		}
		if (e.getSource() == buttonNew) {
			// Scene is intro screen:
			if (getGameContent().getScene() == 0) {
				startNewGame();
				systemMenu.setVisible(false);
				// Scene has already started:
			} else if (confirmedRestart()) {
				restartGame();
				helpMenu.setVisible(false);
				systemMenu.setVisible(false);
			}
		}
		if (e.getSource() == buttonClose) {
			if (confirmedExit()) {
				this.dispose();
			}
		}

		if (e.getSource() == buttonSystem) {
			systemMenu.setVisible(!systemMenu.isVisible());
			gameActionButtonGroup.clearSelection();
			InteractionHandler.setAction("");
		}
		if (e.getSource() == buttonHelp) {
			helpMenu.setVisible(!helpMenu.isVisible());
			gameActionButtonGroup.clearSelection();
			InteractionHandler.setAction("");
		}

		if (e.getSource() instanceof ActionButton) {
			// system menu can only be hidden from this class
			helpMenu.setVisible(false);
			systemMenu.setVisible(false);
			// JToggleButtons now have 2 Action Listeners!
		}
	}

	// Accessed from Scene Mouse Listener to display currently hovered Object name
	public static JLabel getGameObjectLabel() {
		return gameObjectLabel;
	}

	public static Scene getGameContent() {
		return gameContent;
	}

	public static ActionButton getItemButton() {
		return buttonItem;
	}

	public static JLabel getEndScreen() {
		return endScreen;
	}

	public static ButtonGroup getGameActionButtonGroup() {
		return gameActionButtonGroup;
	}
}
