package gui.title;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 * The credit screen for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/17/11
 */
public final class CreditScreen {
	
	/** Text used for a credit element. */
	private static final String MUSIC_TEXT = "Music: Kevin MacLeod",
			PROGRAM_TEXT = "Programmer: Jesse Brent";
	
	/** Position for a credit element (x, game height - y). */
	private static final Point MUSIC_SPOT = new Point(10, 35), PROGRAM_SPOT = new Point(10, 10);
	
	/** Font used for the credits. */
	private static final Font CREDIT_FONT = new Font("Serif", Font.PLAIN, 24);
	
	/**
	 * CreditScreen is a utility class and cannot be instantiated.
	 */
	private CreditScreen() {}
	
	/**
	 * Draws the credit screen onto an image.
	 * 
	 * @param g
	 *            The graphics for the image.
	 */
	public static void paint(Graphics g) {
		g.setFont(CREDIT_FONT);
		drawOutlinedString(g, PROGRAM_TEXT, PROGRAM_SPOT.x, Game.GAME_HEIGHT - PROGRAM_SPOT.y);
		drawOutlinedString(g, MUSIC_TEXT, MUSIC_SPOT.x, Game.GAME_HEIGHT - MUSIC_SPOT.y);
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
	 */
	private static void drawOutlinedString(Graphics g, String text, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawString(text, x - 1, y - 1);
		g.drawString(text, x - 1, y + 1);
		g.drawString(text, x + 1, y - 1);
		g.drawString(text, x + 1, y + 1);
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
}
