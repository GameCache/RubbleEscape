package game.generators;

import game.Game;
import game.objects.Block;
import java.awt.Color;
import java.util.Random;

/**
 * Generates the game's falling blocks.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class BlockGenerator {
	
	/** The average number of frames for a single block to be generated. */
	private static final int BLOCK_CHANCE = 20;
	
	/** Block generation property. */
	private static final int MIN_WIDTH = 40, VARIABLE_WIDTH = 40, MIN_HEIGHT = 40,
			VARIABLE_HEIGHT = 20, MIN_FALL = 5, VARIABLE_FALL = 3;
	
	/** Controls all the randomization for the block generation. */
	private static final Random RANDOMIZER = new Random();
	
	/**
	 * Generator is a utility class and cannot be instantiated.
	 */
	private BlockGenerator() {}
	
	/**
	 * Determines if a block should be generated.
	 * 
	 * @return Whether or not a block should be generated.
	 */
	public static boolean shouldGenerateBlock() {
		return RANDOMIZER.nextInt(BLOCK_CHANCE) == 0;
	}
	
	/**
	 * Generates a block object.
	 * 
	 * @param highestPoint
	 *            The highest altitude of the game.
	 * @return The generated block.
	 */
	public static Block generateBlock(int highestPoint) {
		final Color color = new Color(RANDOMIZER.nextInt());
		final int width = RANDOMIZER.nextInt(VARIABLE_WIDTH) + MIN_WIDTH;
		final int height = RANDOMIZER.nextInt(VARIABLE_HEIGHT) + MIN_HEIGHT;
		final int falling = RANDOMIZER.nextInt(VARIABLE_FALL) + MIN_FALL;
		final int x = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int y = highestPoint + Game.GAME_HEIGHT;
		
		return new Block(color, x, y, width, height, falling);
	}
	
	/**
	 * Generates the starting block that acts as a floor.
	 * 
	 * @param blockHeight
	 *            The height of the starting block.
	 * @return The starter block.
	 */
	public static Block generateStartBlock(int blockHeight) {
		return new Block(Color.BLACK, 0, 0, Game.GAME_WIDTH - 1, blockHeight - 1, 0);
	}
	
	/**
	 * Generates a block object for the title screen.
	 * 
	 * @return The generated block.
	 */
	public static Block generateTitleBlock() {
		final Color color = new Color(RANDOMIZER.nextInt());
		final int width = RANDOMIZER.nextInt(VARIABLE_WIDTH) + MIN_WIDTH;
		final int height = RANDOMIZER.nextInt(VARIABLE_HEIGHT) + MIN_HEIGHT;
		final int falling = RANDOMIZER.nextInt(VARIABLE_FALL) + MIN_FALL;
		final int x = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int y = height + Game.GAME_HEIGHT;
		
		return new Block(color, x, y, width, height, falling);
	}
	
}
