package game;

import game.generators.BackgroundGenerator;
import game.generators.BlockGenerator;
import game.objects.Block;
import game.objects.Player;
import game.objects.RisingWater;
import gui.GameManager;
import gui.PausedScreen;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * The game mechanics.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class Game {
	
	/** Dimension of the game screen. */
	public static final int GAME_WIDTH = 640, GAME_HEIGHT = 480;
	
	/** What screen portion is below the player. */
	private static final double FRACTION_SCREEN_BELOW_PLAYER = 0.25;
	
	/** Amount of space below the player to the bottom of the screen. */
	private final int spaceBelowPlayer;
	
	/** The player of the game. */
	private final Player player;
	
	/** The block objects in the game. */
	private final List<Block> blocks;
	
	/** The rising water in the game. */
	private final RisingWater water;
	
	/** Keeps track of the score. */
	private final Scoring score;
	
	/** The background music playing during the game. */
	private final AudioClip backgroundMusic;
	
	/** Controls the input received by the player. */
	private final InputReceiver input;
	
	/** If the game has been paused. */
	private boolean isPaused;
	
	/**
	 * Readies the game.
	 */
	public Game() {
		backgroundMusic = GameManager.loadSound("BumblyMarch.wav");
		spaceBelowPlayer = (int)(GAME_HEIGHT * FRACTION_SCREEN_BELOW_PLAYER);
		
		player = new Player(GAME_WIDTH / 2, spaceBelowPlayer);
		score = new Scoring(spaceBelowPlayer);
		water = new RisingWater();
		input = new InputReceiver();
		blocks = new LinkedList<Block>();
		blocks.add(BlockGenerator.generateStartBlock(spaceBelowPlayer));
	}
	
	/**
	 * Resets the game to the initial state.
	 */
	public void reset() {
		player.reset(GAME_WIDTH / 2, spaceBelowPlayer);
		water.reset();
		input.reset();
		score.reset(spaceBelowPlayer);
		isPaused = false;
		
		blocks.clear();
		blocks.add(BlockGenerator.generateStartBlock(spaceBelowPlayer));
	}
	
	/**
	 * Starts running the game.
	 */
	public void start() {
		backgroundMusic.loop();
	}
	
	/**
	 * Advances the game by one frame.
	 */
	public void step() {
		
		if(!isAlive() && water.shouldStopSimulating(player.getAltitude())) {
			blocks.clear();
			return;
		}
		
		if(input.isSuicide()) {
			player.suicide();
		}
		
		if(input.isPaused() && !isPaused) {
			isPaused = true;
			backgroundMusic.stop();
		}
		
		if(isPaused && isAlive()) {
			if(input.isPaused()) {
				return;
			} else {
				isPaused = false;
				backgroundMusic.loop();
			}
		}
		
		// Action order: Blocks, player, water, then block generation.
		Block.step(blocks);
		player.step(blocks, input);
		water.step(blocks, player, spaceBelowPlayer);
		
		if(BlockGenerator.shouldGenerateBlock()) {
			int highestPoint = player.getAltitude();
			
			for(final Block block : blocks) {
				if(!block.isFalling() && block.getYOfTop() > highestPoint) {
					highestPoint = block.getYOfTop();
				}
			}
			
			blocks.add(BlockGenerator.generateBlock(highestPoint));
		}
		
		score.updateScore(player.getAltitude());
	}
	
	/**
	 * Returns whether or not the player is still alive and the game should keep playing.
	 * 
	 * @return True if the player is alive and the game should continue.
	 */
	public boolean isAlive() {
		return player.isAlive();
	}
	
	/**
	 * Stops the game.
	 */
	public void stop() {
		score.updateHighScore();
		backgroundMusic.stop();
	}
	
	/**
	 * Draws the game onto an image.
	 * 
	 * @param g
	 *            The graphics object of the image.
	 */
	public void paint(Graphics g) {
		
		if(!isAlive() && water.shouldStopSimulating(player.getAltitude())) {
			return;
		}
		
		if(isPaused && isAlive()) {
			PausedScreen.paint(g);
			return;
		}
		
		final int altitude = player.getAltitude();
		final int topScreen = altitude + (GAME_HEIGHT - spaceBelowPlayer);
		final int bottomScreen = altitude - spaceBelowPlayer;
		
		g.setColor(BackgroundGenerator.generateBackgroundColor(altitude));
		g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		
		for(final Block block : blocks) {
			block.paint(g, topScreen, bottomScreen);
		}
		
		if(player.isAlive()) {
			player.paint(g, topScreen);
			water.paint(g, topScreen, bottomScreen);
		} else {
			water.paint(g, topScreen, bottomScreen);
			player.paint(g, topScreen);
		}
		
		score.paint(g, player.getAltitude(), spaceBelowPlayer);
	}
	
	/**
	 * Returns the listener object used to record key input for the game.
	 * 
	 * @return The key listener.
	 */
	public KeyListener getKeyListener() {
		return input;
	}
	
}
