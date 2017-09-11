package gui;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 * The paused screen for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/15/11
 */
public final class PausedScreen {
	
	/** The text for the paused message. */
	private static final String PAUSED_MESSAGE = "Paused";
	
	/** The position for the paused message (game width / 2 - x, y). */
	private static final Point PAUSED_COORDINATES = new Point(45, 80);
	
	/** Font of the instructions. */
	private static final Font PAUSED_FONT = new Font("Serif", Font.BOLD, 30);
	
	/**
	 * PausedScreen is a utility class and cannot be instantiated.
	 */
	private PausedScreen() {}
	
	/**
	 * Draws the paused screen onto an image.
	 * 
	 * @param g
	 *            The graphics for the image.
	 */
	public static void paint(Graphics g) {
		g.setFont(PAUSED_FONT);
		g.setColor(Color.BLACK);
		
		g.drawString(PAUSED_MESSAGE, Game.GAME_WIDTH / 2 - PAUSED_COORDINATES.x - 1,
				PAUSED_COORDINATES.y - 1);
		g.drawString(PAUSED_MESSAGE, Game.GAME_WIDTH / 2 - PAUSED_COORDINATES.x - 1,
				PAUSED_COORDINATES.y + 1);
		g.drawString(PAUSED_MESSAGE, Game.GAME_WIDTH / 2 - PAUSED_COORDINATES.x + 1,
				PAUSED_COORDINATES.y - 1);
		g.drawString(PAUSED_MESSAGE, Game.GAME_WIDTH / 2 - PAUSED_COORDINATES.x + 1,
				PAUSED_COORDINATES.y + 1);
		
		g.setColor(Color.WHITE);
		g.drawString(PAUSED_MESSAGE, Game.GAME_WIDTH / 2 - PAUSED_COORDINATES.x,
				PAUSED_COORDINATES.y);
	}
	
}
