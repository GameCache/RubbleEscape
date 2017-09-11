package game.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import game.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for {@link game.objects.Player}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/02/11
 */
public final class PlayerTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 100000;
	
	/** Controls all the randomization for the tests. */
	private static final Random RANDOMIZER = new Random();
	
	/**
	 * Test method for {@link game.objects.Player#Player(int, int)}.
	 */
	@Test
	public void testPlayer() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt()));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#reset(int, int)}.
	 */
	@Test
	public void testReset() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			testPlayer.reset(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#step(java.util.List, game.InputReceiver)}.
	 */
	@Test
	public void testStep_Runs() {}
	
	/**
	 * Test method for {@link game.objects.Player#suicide()}.
	 */
	@Test
	public void testSuicide() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			testPlayer.suicide();
			assertFalse("Player is still alive after suicide.", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#drown(int)}.
	 */
	@Test
	public void testDrown_BelowWater() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			testPlayer.drown(testPlayer.getAltitude() + Game.GAME_HEIGHT);
			assertFalse("Player is still alive after drowning.", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#drown(int)}.
	 */
	@Test
	public void testDrown_AboveWater() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			testPlayer.drown(testPlayer.getAltitude() - Game.GAME_HEIGHT);
			assertTrue("Player is drowned above the water.", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#isAlive()}.
	 */
	@Test
	public void testIsAlive() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			assertTrue("New player isn't alive.", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#getAltitude()}.
	 */
	@Test
	public void testGetAltitude() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testY = RANDOMIZER.nextInt();
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), testY);
			
			assertEquals("Game critical logic not working as expected.", testY,
					testPlayer.getAltitude());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Player#paint(java.awt.Graphics, int)}.
	 */
	@Test
	public void testPaint() {
		final Graphics testG = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			testPlayer.paint(testG, RANDOMIZER.nextInt());
		}
	}
	
}
