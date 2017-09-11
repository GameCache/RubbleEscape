package gui.title;

import game.Game;
import game.generators.BlockGenerator;
import game.objects.Block;
import gui.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The title screen for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/03/10
 */
public final class TitleScreen implements KeyListener {
	
	/**
	 * Possible title menu selections.
	 * 
	 * @author Jesse Brent
	 * @version 1.0, 01/16/11
	 */
	private static enum SelectionState {
		/** A state of the title menu. */
		PLAY, INSTRUCTION, CREDIT;
	}
	
	/** Text used for a title element. */
	private static final String TITLE_TEXT = "Rubble Escape", START_TEXT = "Play",
			BLINK_TEXT = "Press Space to Select", HELP_TEXT = "Instructions",
			CREDIT_TEXT = "Credits";
	
	/** Position for a title element (game width / 2 - x, y). */
	private static final Point TITLE_SPOT = new Point(204, 90), BLINK_SPOT = new Point(133, 130),
			MENU_SPOT = new Point(90, 280), START_SPOT = new Point(22, 305),
			HELP_SPOT = new Point(56, 340), CREDIT_SPOT = new Point(35, 375),
			SELECT_MOD = new Point(5, 22), SELECT_SCALE = new Point(22, 30);
	
	/** Font used for a title element. */
	private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 64),
			BLINK_FONT = new Font("Serif", Font.BOLD, 30), MENU_FONT = new Font("Serif",
					Font.BOLD, 24);
	
	/** Color used for the background. */
	private static final Color BACKGROUND = Color.DARK_GRAY;
	
	/** The height of the menu box. */
	private static final int MENU_HEIGHT = 105;
	
	/** How many frames for the blink effect. */
	private static final int BLINK_FRAMES = 25;
	
	/** Images for instructions. */
	private static final Image SELECTION_IMAGE = GameManager.loadImage("Jump1Right.png");
	
	/** The block objects for the title. */
	private final List<Block> blocks;
	
	/** The current selection in the menu. */
	private SelectionState selection;
	
	/** Select-able options. */
	private boolean canPlayGame, showCredits, showInstructions;
	
	/** Controls if the start text is to be shown. */
	private int blinkTimer;
	
	/**
	 * Constructs the title screen for the game.
	 */
	public TitleScreen() {
		blocks = new LinkedList<Block>();
		reset();
	}
	
	/**
	 * Resets the title screen with initial values.
	 */
	public void reset() {
		blocks.clear();
		selection = SelectionState.PLAY;
		showInstructions = false;
		showCredits = false;
		canPlayGame = false;
		blinkTimer = 0;
	}
	
	/**
	 * Advances the title screen animation by one frame.
	 */
	public void step() {
		Block.step(blocks);
		
		if(BlockGenerator.shouldGenerateBlock() || BlockGenerator.shouldGenerateBlock()) {
			blocks.add(BlockGenerator.generateTitleBlock());
		}
		
		final Iterator<Block> itr = blocks.iterator();
		while(itr.hasNext()) {
			if(itr.next().getYOfTop() < 0) {
				itr.remove();
			}
		}
		
		blinkTimer = (blinkTimer + 1) % (2 * BLINK_FRAMES);
	}
	
	/**
	 * Returns whether the user has started the game.
	 * 
	 * @return If the user has started the game.
	 */
	public boolean canPlayGame() {
		return canPlayGame;
	}
	
	/**
	 * Draws the title screen onto an image.
	 * 
	 * @param g
	 *            The graphics for the image.
	 */
	public void paint(Graphics g) {
		// Background.
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		for(final Block block : blocks) {
			block.paint(g, Game.GAME_HEIGHT, 0);
		}
		
		// Title.
		g.setFont(TITLE_FONT);
		g.setColor(Color.BLACK);
		g.drawString(TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x - 2, TITLE_SPOT.y - 2);
		g.drawString(TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x - 2, TITLE_SPOT.y + 2);
		g.drawString(TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x + 2, TITLE_SPOT.y - 2);
		g.drawString(TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x + 2, TITLE_SPOT.y + 2);
		drawStringOutline(g, TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x, TITLE_SPOT.y);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(TITLE_TEXT, Game.GAME_WIDTH / 2 - TITLE_SPOT.x, TITLE_SPOT.y);
		
		// Start message.
		if(blinkTimer > BLINK_FRAMES) {
			g.setFont(BLINK_FONT);
			g.setColor(Color.BLACK);
			drawStringOutline(g, BLINK_TEXT, Game.GAME_WIDTH / 2 - BLINK_SPOT.x, BLINK_SPOT.y);
			g.setColor(Color.WHITE);
			g.drawString(BLINK_TEXT, Game.GAME_WIDTH / 2 - BLINK_SPOT.x, BLINK_SPOT.y);
		}
		
		// Menu box.
		g.setColor(BACKGROUND);
		g.fillRect(Game.GAME_WIDTH / 2 - MENU_SPOT.x, MENU_SPOT.y, MENU_SPOT.x * 2, MENU_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(Game.GAME_WIDTH / 2 - MENU_SPOT.x, MENU_SPOT.y, MENU_SPOT.x * 2, MENU_HEIGHT);
		
		// Default menu setup.
		g.setFont(MENU_FONT);
		g.setColor(Color.BLACK);
		drawStringOutline(g, START_TEXT, Game.GAME_WIDTH / 2 - START_SPOT.x, START_SPOT.y);
		drawStringOutline(g, HELP_TEXT, Game.GAME_WIDTH / 2 - HELP_SPOT.x, HELP_SPOT.y);
		drawStringOutline(g, CREDIT_TEXT, Game.GAME_WIDTH / 2 - CREDIT_SPOT.x, CREDIT_SPOT.y);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(START_TEXT, Game.GAME_WIDTH / 2 - START_SPOT.x, START_SPOT.y);
		g.drawString(HELP_TEXT, Game.GAME_WIDTH / 2 - HELP_SPOT.x, HELP_SPOT.y);
		g.drawString(CREDIT_TEXT, Game.GAME_WIDTH / 2 - CREDIT_SPOT.x, CREDIT_SPOT.y);
		
		// Menu selection.
		g.setColor(Color.WHITE);
		switch(selection) {
			case PLAY: {
				g.drawImage(SELECTION_IMAGE, Game.GAME_WIDTH / 2 - MENU_SPOT.x + SELECT_MOD.x,
						START_SPOT.y - SELECT_MOD.y, SELECT_SCALE.x, SELECT_SCALE.y, null);
				g.drawString(START_TEXT, Game.GAME_WIDTH / 2 - START_SPOT.x, START_SPOT.y);
				break;
			}
			case INSTRUCTION: {
				g.drawImage(SELECTION_IMAGE, Game.GAME_WIDTH / 2 - MENU_SPOT.x + SELECT_MOD.x,
						HELP_SPOT.y - SELECT_MOD.y, SELECT_SCALE.x, SELECT_SCALE.y, null);
				g.drawString(HELP_TEXT, Game.GAME_WIDTH / 2 - HELP_SPOT.x, HELP_SPOT.y);
				break;
			}
			case CREDIT: {
				g.drawImage(SELECTION_IMAGE, Game.GAME_WIDTH / 2 - MENU_SPOT.x + SELECT_MOD.x,
						CREDIT_SPOT.y - SELECT_MOD.y, SELECT_SCALE.x, SELECT_SCALE.y, null);
				g.drawString(CREDIT_TEXT, Game.GAME_WIDTH / 2 - CREDIT_SPOT.x, CREDIT_SPOT.y);
				break;
			}
			default: {
				break;
			}
		}
		
		// Credits.
		if(showCredits) {
			CreditScreen.paint(g);
		}
		
		// Instructions.
		if(showInstructions) {
			InstructionScreen.paint(g, BACKGROUND);
		}
	}
	
	/**
	 * Draws a given string outline at a position on an image.
	 * 
	 * @param g
	 *            The graphics object for the image.
	 * @param text
	 *            The string to display.
	 * @param x
	 *            The X coordinate to draw the text.
	 * @param y
	 *            The Y coordinate to draw the text.
	 */
	private static void drawStringOutline(Graphics g, String text, int x, int y) {
		g.drawString(text, x - 1, y - 1);
		g.drawString(text, x - 1, y + 1);
		g.drawString(text, x + 1, y - 1);
		g.drawString(text, x + 1, y + 1);
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W: {
				if(!showInstructions) {
					switch(selection) {
						case PLAY: {
							selection = SelectionState.CREDIT;
							break;
						}
						case INSTRUCTION: {
							selection = SelectionState.PLAY;
							break;
						}
						case CREDIT: {
							selection = SelectionState.INSTRUCTION;
							break;
						}
						default: {
							break;
						}
					}
				}
				break;
			}
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S: {
				if(!showInstructions) {
					switch(selection) {
						case PLAY: {
							selection = SelectionState.INSTRUCTION;
							break;
						}
						case INSTRUCTION: {
							selection = SelectionState.CREDIT;
							break;
						}
						case CREDIT: {
							selection = SelectionState.PLAY;
							break;
						}
						default: {
							break;
						}
					}
				}
				break;
			}
			default: {
				break;
			}
		}
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_ENTER: {
				switch(selection) {
					case PLAY: {
						canPlayGame = !canPlayGame;
						break;
					}
					case INSTRUCTION: {
						showInstructions = !showInstructions;
						break;
					}
					case CREDIT: {
						showCredits = !showCredits;
						break;
					}
					default: {
						break;
					}
				}
				break;
			}
			case KeyEvent.VK_ESCAPE: {
				selection = SelectionState.PLAY;
				showInstructions = false;
				showCredits = false;
				break;
			}
			default: {
				break;
			}
		}
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent key) {}
	
}
