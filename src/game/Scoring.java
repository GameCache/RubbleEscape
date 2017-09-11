package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Controls the scoring for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/03/10
 */
public final class Scoring {
	
	/** The position for a score. */
	private static final Point HIGH_SCORE = new Point(5, 25), CURRENT_SCORE = new Point(5, 25),
			RELATIVE_SCORE = new Point(5, 50);
	
	/** Font for the scores. */
	private static final Font SCORE_FONT = new Font("Serif", Font.BOLD, 30);
	
	/** Modifies the x of the score position for each digit. */
	private static final int X_MOD_PER_DIGIT = 15;
	
	/** What to divide the altitude by to get the score. */
	private static final int ALTITUDE_MOD = 11;
	
	/** The highest altitude the player has achieved. */
	private int highScore;
	
	/** Highest altitude achieved. */
	private int highestAltitude;
	
	/**
	 * Constructs the scoring system.
	 * 
	 * @param spaceBelowPlayer
	 *            Amount of space below the player to the bottom of the screen.
	 */
	public Scoring(int spaceBelowPlayer) {
		highScore = spaceBelowPlayer;
		highestAltitude = spaceBelowPlayer;
		reset(spaceBelowPlayer);
	}
	
	/**
	 * Resets the score to the initial state. Does not reset the high score.
	 * 
	 * @param spaceBelowPlayer
	 *            Amount of space below the player to the bottom of the screen.
	 */
	public void reset(int spaceBelowPlayer) {
		highestAltitude = spaceBelowPlayer;
	}
	
	/**
	 * Checks the score against the current altitude.
	 * 
	 * @param currentAltitude
	 *            The current altitude.
	 */
	public void updateScore(int currentAltitude) {
		if(currentAltitude > highestAltitude) {
			highestAltitude = currentAltitude;
		}
	}
	
	/**
	 * Updates the high score if the current score is greater.
	 */
	public void updateHighScore() {
		if(highestAltitude > highScore) {
			highScore = highestAltitude;
		}
	}
	
	/**
	 * Draws the score on an image.
	 * 
	 * @param g
	 *            The graphics object for the image.
	 * @param currentAltitude
	 *            The current altitude of the player.
	 * @param spaceBelowPlayer
	 *            Amount of space below the player to the bottom of the screen.
	 */
	public void paint(Graphics g, int currentAltitude, int spaceBelowPlayer) {
		final String highestScore =
				Integer.toString((highScore - spaceBelowPlayer) / ALTITUDE_MOD);
		final String currentScore =
				Integer.toString((highestAltitude - spaceBelowPlayer) / ALTITUDE_MOD);
		final String relativeScore =
				Integer.toString((currentAltitude - spaceBelowPlayer) / ALTITUDE_MOD);
		
		final int moddedCurrentScoreX =
				Game.GAME_WIDTH - currentScore.length() * X_MOD_PER_DIGIT - CURRENT_SCORE.x;
		final int moddedRelativeScoreX =
				Game.GAME_WIDTH - relativeScore.length() * X_MOD_PER_DIGIT - RELATIVE_SCORE.x;
		
		g.setFont(SCORE_FONT);
		drawOutlinedString(g, highestScore, HIGH_SCORE.x, HIGH_SCORE.y);
		drawOutlinedString(g, currentScore, moddedCurrentScoreX, CURRENT_SCORE.y);
		drawOutlinedString(g, relativeScore, moddedRelativeScoreX, RELATIVE_SCORE.y);
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
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(text, x, y);
	}
	
}
