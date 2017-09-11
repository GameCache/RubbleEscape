package game.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for {@link game.objects.RisingWater}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/31/11
 */
public final class RisingWaterTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 25000;
	
	/** Controls all the randomization for the tests. */
	private static final Random RANDOMIZER = new Random();
	
	/** Field that represents the water altitude for the RisingWater class. */
	private static Field altitude;
	
	/**
	 * Prepares the tests by enabling access to the water altitude.
	 * 
	 * @throws Exception
	 *             If the water altitude field cannot be identified or reflection is disabled.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		altitude = RisingWater.class.getDeclaredField("altitude");
		
		// Setting access using reflection is require privileges.
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				altitude.setAccessible(true);
				return null;
			}
		});
	}
	
	/**
	 * Reverts access privileges after the tests are done.
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		// Setting access using reflection is require privileges.
		AccessController.doPrivileged(new PrivilegedAction<Object>() {
			public Object run() {
				altitude.setAccessible(false);
				return null;
			}
		});
	}
	
	/**
	 * Test method for {@link game.objects.RisingWater#RisingWater()}.
	 */
	@Test
	public void testRisingWater() {}
	
	/**
	 * Test method for {@link game.objects.RisingWater#reset()}.
	 */
	@Test
	public void testReset() {}
	
	/**
	 * Test method for
	 * {@link game.objects.RisingWater#step(java.util.List, game.objects.Player, int)}.
	 */
	@Test
	public void testStep_WaterRises() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final RisingWater testWater = new RisingWater();
			setWaterAltitude(testWater, RANDOMIZER.nextInt());
			final double testAltitude = getWaterAltitude(testWater);
			
			testWater.step(Collections.<Block> emptyList(), new Player(RANDOMIZER.nextInt(),
					RANDOMIZER.nextInt()), RANDOMIZER.nextInt());
			
			assertTrue("Water isn't rising.", getWaterAltitude(testWater) > testAltitude);
		}
	}
	
	/**
	 * Test method for
	 * {@link game.objects.RisingWater#step(java.util.List, game.objects.Player, int)}.
	 */
	@Test
	public void testStep_PlayerBelowWaterDrown() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testAltitude = RANDOMIZER.nextInt();
			final RisingWater testWater = new RisingWater();
			setWaterAltitude(testWater, testAltitude);
			final Player testPlayer =
					new Player(RANDOMIZER.nextInt(), testAltitude - Game.GAME_HEIGHT);
			
			testWater.step(Collections.<Block> emptyList(), testPlayer, RANDOMIZER.nextInt());
			
			assertFalse("Sunken player didn't drown", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for
	 * {@link game.objects.RisingWater#step(java.util.List, game.objects.Player, int)}.
	 */
	@Test
	public void testStep_PlayerAboveWaterNoDrown() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testAltitude = RANDOMIZER.nextInt();
			final RisingWater testWater = new RisingWater();
			setWaterAltitude(testWater, testAltitude - 1);
			final Player testPlayer = new Player(RANDOMIZER.nextInt(), testAltitude);
			
			testWater.step(Collections.<Block> emptyList(), testPlayer, RANDOMIZER.nextInt());
			
			assertTrue("Player drowned above water.", testPlayer.isAlive());
		}
	}
	
	/**
	 * Test method for
	 * {@link game.objects.RisingWater#step(java.util.List, game.objects.Player, int)}.
	 */
	@Test
	public void testStep_BlockRemoval() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final RisingWater testWater = new RisingWater();
			final int testSpaceBelowPlayer = RANDOMIZER.nextInt(Game.GAME_HEIGHT);
			final Block testBlock =
					new Block(Color.BLACK, RANDOMIZER.nextInt(), RANDOMIZER.nextInt(),
							RANDOMIZER.nextInt(), RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
			
			final List<Block> testList = new ArrayList<Block>();
			testList.add(testBlock);
			setWaterAltitude(testWater, RANDOMIZER.nextInt());
			
			testWater.step(testList, new Player(RANDOMIZER.nextInt(), RANDOMIZER.nextInt()),
					testSpaceBelowPlayer);
			
			if((int)getWaterAltitude(testWater) > testBlock.getYOfTop() + testSpaceBelowPlayer) {
				assertEquals(0, testList.size());
			} else {
				assertEquals(1, testList.size());
			}
		}
	}
	
	/**
	 * Test method for {@link game.objects.RisingWater#shouldStopSimulating(int)}.
	 */
	@Test
	public void testShouldStopSimulating_WaterHigherThanScreen() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final RisingWater testWater = new RisingWater();
			final double testAltitude = RANDOMIZER.nextInt();
			
			setWaterAltitude(testWater, testAltitude + Game.GAME_HEIGHT * 2);
			
			assertTrue(testWater.shouldStopSimulating((int)testAltitude));
		}
	}
	
	/**
	 * Test method for {@link game.objects.RisingWater#shouldStopSimulating(int)}.
	 */
	@Test
	public void testShouldStopSimulating_WaterLowerThanScreen() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final RisingWater testWater = new RisingWater();
			final double testAltitude = RANDOMIZER.nextInt();
			
			setWaterAltitude(testWater, testAltitude);
			
			assertFalse(testWater.shouldStopSimulating((int)testAltitude));
		}
	}
	
	/**
	 * Test method for {@link game.objects.RisingWater#paint(java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaint_SkipsWhenBelowScreen() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final RisingWater testWater = new RisingWater();
			final double screenBottom = RANDOMIZER.nextInt();
			
			setWaterAltitude(testWater, screenBottom - Game.GAME_HEIGHT);
			
			testWater.paint(null, (int)(screenBottom + 1), (int)screenBottom);
		}
	}
	
	/**
	 * Test method for {@link game.objects.RisingWater#paint(java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaint_Runs() {
		final Graphics testG = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		
		for(int i = 0; i < TIMES_TO_RUN_TESTS / 2; i++) {
			final RisingWater testWater = new RisingWater();
			
			setWaterAltitude(testWater, RANDOMIZER.nextInt());
			
			testWater.paint(testG, RANDOMIZER.nextInt(), RANDOMIZER.nextInt());
		}
	}
	
	/**
	 * Grabs the water altitude field from a RisingWater object.
	 * 
	 * @param water
	 *            The RisingWater object.
	 * @return The water altitude.
	 */
	private static double getWaterAltitude(RisingWater water) {
		double waterAltitude = 0;
		
		try {
			waterAltitude = altitude.getDouble(water);
		} catch(IllegalArgumentException e) {
			fail(e.toString());
		} catch(IllegalAccessException e) {
			fail(e.toString());
		}
		
		return waterAltitude;
	}
	
	/**
	 * Sets the water altitude field in a RisingWater object.
	 * 
	 * @param water
	 *            The water altitude.
	 * @param waterAltitude
	 *            The RisingWater object.
	 */
	private static void setWaterAltitude(RisingWater water, double waterAltitude) {
		try {
			altitude.setDouble(water, waterAltitude);
		} catch(IllegalArgumentException e) {
			fail(e.toString());
		} catch(IllegalAccessException e) {
			fail(e.toString());
		}
	}
}
