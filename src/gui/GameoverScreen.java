package gui;

import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The gameover screen for the game.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/03/10
 */
public final class GameoverScreen implements KeyListener {
	
	/** The text for the continue message. */
	private static final String CONTINUE_TEXT = "Press space to play again.";
	
	/** The position for the continue message (game width / 2 - x, y). */
	private static final Point CONTINUE_COORDINATES = new Point(161, 80);
	
	/** Font of the instructions. */
	private static final Font CONTINUE_FONT = new Font("Serif", Font.BOLD, 30);
	
	/** If the user restarts the game. */
	private boolean restartGame;
	
	/** If the user returns to title. */
	private boolean returnTitle;
	
	/**
	 * Constructs the title screen for the game.
	 */
	public GameoverScreen() {
		reset();
	}
	
	/** Resets the title screen with initial values. */
	public void reset() {
		restartGame = false;
		returnTitle = false;
	}
	
	/**
	 * Returns whether the user has restarted the game.
	 * 
	 * @return If the user has started the game.
	 */
	public boolean canRestartGame() {
		return restartGame;
	}
	
	/**
	 * Returns whether the user has returned to the title.
	 * 
	 * @return If the user has returned to the title.
	 */
	public boolean canReturnTitle() {
		return returnTitle;
	}
	
	/**
	 * Draws the gameover screen onto an image.
	 * 
	 * @param g
	 *            The graphics for the image.
	 */
	public void paint(Graphics g) {
		g.setFont(CONTINUE_FONT);
		g.setColor(Color.BLACK);
		
		g.drawString(CONTINUE_TEXT, Game.GAME_WIDTH / 2 - CONTINUE_COORDINATES.x - 1,
				CONTINUE_COORDINATES.y - 1);
		g.drawString(CONTINUE_TEXT, Game.GAME_WIDTH / 2 - CONTINUE_COORDINATES.x - 1,
				CONTINUE_COORDINATES.y + 1);
		g.drawString(CONTINUE_TEXT, Game.GAME_WIDTH / 2 - CONTINUE_COORDINATES.x + 1,
				CONTINUE_COORDINATES.y - 1);
		g.drawString(CONTINUE_TEXT, Game.GAME_WIDTH / 2 - CONTINUE_COORDINATES.x + 1,
				CONTINUE_COORDINATES.y + 1);
		
		g.setColor(Color.WHITE);
		g.drawString(CONTINUE_TEXT, Game.GAME_WIDTH / 2 - CONTINUE_COORDINATES.x,
				CONTINUE_COORDINATES.y);
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent key) {}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_SPACE || key.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = !restartGame;
		} else if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
			returnTitle = !returnTitle;
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
