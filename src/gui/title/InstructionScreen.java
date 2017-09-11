package gui.title;

import game.Game;
import gui.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * The instruction screen for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/16/11
 */
public final class InstructionScreen {
	
	/** Text used for a instruction element. */
	private static final String JUMP_TEXT = "Jump:", JUMP2_TEXT = "up / w / z",
			LEFT_TEXT = "Move Left:", LEFT2_TEXT = "left / a", RIGHT_TEXT = "Move Right:",
			RIGHT2_TEXT = "right / d", PAUSE_TEXT = "Pause / Confirm:",
			PAUSE2_TEXT = "space / enter", SUICIDE_TEXT = "Suicide / Title:",
			SUICID2_TEXT = "esc";
	
	/** Position for a instruction element. */
	private static final Point BOX_SPOT = new Point(144, 150), BOX_SIZE = new Point(288, 310),
			JUMP_I_SPOT = new Point(124, 160), LEFT_I_SPOT = new Point(124, 280),
			RIGHT_I_SPOT = new Point(100, 220), DEATH_I_SPOT = new Point(100, 340),
			JUMP_SPOT = new Point(90, 180), JUMP2_SPOT = new Point(90, 200),
			LEFT_SPOT = new Point(90, 300), LEFT2_SPOT = new Point(90, 320),
			RIGHT_SPOT = new Point(4, 240), RIGHT2_SPOT = new Point(44, 260),
			PAUSE_SPOT = new Point(56, 420), PAUSE2_SPOT = new Point(39, 440),
			SUICIDE_SPOT = new Point(-6, 360), SUICID2_SPOT = new Point(70, 380);
	
	/** Font used for a instruction element. */
	private static final Font INSTRUCTION_FONT = new Font("Serif", Font.BOLD, 16),
			INSTRUCTION2_FONT = new Font("Serif", Font.PLAIN, 16);
	
	/** Images for instructions. */
	private static final Image JUMP_IMAGE, LEFT_IMAGE, RIGHT_IMAGE, DEATH_IMAGE;
	
	static {
		JUMP_IMAGE = GameManager.loadImage("Jump1Left.png");
		LEFT_IMAGE = GameManager.loadImage("Run1Left.png");
		RIGHT_IMAGE = GameManager.loadImage("Run1Right.png");
		DEATH_IMAGE = GameManager.loadImage("Died1Left.png");
	}
	
	/**
	 * InstructionScreen is a utility class and cannot be instantiated.
	 */
	private InstructionScreen() {}
	
	/**
	 * Draws the instruction screen onto an image.
	 * 
	 * @param g
	 *            The graphics for the image.
	 * @param background
	 *            The color for the background.
	 */
	public static void paint(Graphics g, Color background) {
		g.setColor(background);
		g.fillRect(Game.GAME_WIDTH / 2 - BOX_SPOT.x, BOX_SPOT.y, BOX_SIZE.x, BOX_SIZE.y);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(Game.GAME_WIDTH / 2 - BOX_SPOT.x, BOX_SPOT.y, BOX_SIZE.x, BOX_SIZE.y);
		
		g.drawImage(JUMP_IMAGE, Game.GAME_WIDTH / 2 - JUMP_I_SPOT.x, JUMP_I_SPOT.y, null);
		g.drawImage(RIGHT_IMAGE, Game.GAME_WIDTH / 2 + RIGHT_I_SPOT.x, RIGHT_I_SPOT.y, null);
		g.drawImage(LEFT_IMAGE, Game.GAME_WIDTH / 2 - LEFT_I_SPOT.x, LEFT_I_SPOT.y, null);
		g.drawImage(DEATH_IMAGE, Game.GAME_WIDTH / 2 + DEATH_I_SPOT.x, DEATH_I_SPOT.y, null);
		
		g.setFont(INSTRUCTION_FONT);
		drawOutlinedString(g, JUMP_TEXT, Game.GAME_WIDTH / 2 - JUMP_SPOT.x, JUMP_SPOT.y,
				Color.LIGHT_GRAY);
		drawOutlinedString(g, RIGHT_TEXT, Game.GAME_WIDTH / 2 + RIGHT_SPOT.x, RIGHT_SPOT.y,
				Color.LIGHT_GRAY);
		drawOutlinedString(g, LEFT_TEXT, Game.GAME_WIDTH / 2 - LEFT_SPOT.x, LEFT_SPOT.y,
				Color.LIGHT_GRAY);
		drawOutlinedString(g, SUICIDE_TEXT, Game.GAME_WIDTH / 2 + SUICIDE_SPOT.x, SUICIDE_SPOT.y,
				Color.LIGHT_GRAY);
		drawOutlinedString(g, PAUSE_TEXT, Game.GAME_WIDTH / 2 - PAUSE_SPOT.x, PAUSE_SPOT.y,
				Color.LIGHT_GRAY);
		
		g.setFont(INSTRUCTION2_FONT);
		drawOutlinedString(g, JUMP2_TEXT, Game.GAME_WIDTH / 2 - JUMP2_SPOT.x, JUMP2_SPOT.y,
				Color.WHITE);
		drawOutlinedString(g, RIGHT2_TEXT, Game.GAME_WIDTH / 2 + RIGHT2_SPOT.x, RIGHT2_SPOT.y,
				Color.WHITE);
		drawOutlinedString(g, LEFT2_TEXT, Game.GAME_WIDTH / 2 - LEFT2_SPOT.x, LEFT2_SPOT.y,
				Color.WHITE);
		drawOutlinedString(g, SUICID2_TEXT, Game.GAME_WIDTH / 2 + SUICID2_SPOT.x, SUICID2_SPOT.y,
				Color.WHITE);
		drawOutlinedString(g, PAUSE2_TEXT, Game.GAME_WIDTH / 2 - PAUSE2_SPOT.x, PAUSE2_SPOT.y,
				Color.WHITE);
	}
	
	/**
	 * Draws a given string outlined at a position on an image.
	 * 
	 * @param g
	 *            The graphics object for the image.
	 * @param text
	 *            The string to display.
	 * @param x
	 *            The X coordinate to draw the text.
	 * @param y
	 *            The Y coordinate to draw the text.
	 * @param color
	 *            The color of the outlined text.
	 */
	private static void drawOutlinedString(Graphics g, String text, int x, int y, Color color) {
		g.setColor(Color.BLACK);
		g.drawString(text, x - 1, y - 1);
		g.drawString(text, x - 1, y + 1);
		g.drawString(text, x + 1, y - 1);
		g.drawString(text, x + 1, y + 1);
		g.setColor(color);
		g.drawString(text, x, y);
	}
	
}
