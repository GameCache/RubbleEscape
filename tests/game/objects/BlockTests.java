package game.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for {@link game.objects.Block}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/29/11
 */
public final class BlockTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 50000;
	
	/** The range of values to generate using the random value generator. */
	private static final int VALUE_RANGE = 100000000;
	
	/** Controls all the randomization for the tests. */
	private static final Random RANDOMIZER = new Random();
	
	/**
	 * Test method for {@link game.objects.Block#Block(java.awt.Color, int, int, int, int, int)} .
	 */
	@Test
	public void testBlock() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(createBlock(randVal(), randVal(), randVal(), randVal(), randVal()));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#drift(int)}.
	 */
	@Test
	public void testDrift_WhenFalling() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock =
					createBlock(randVal(), randVal(), randDim(), randDim(), randVal());
			
			if(testBlock.isFalling()) {
				final int testWater = randVal();
				
				testBlock.drift(testWater);
				
				if(testWater > testBlock.getYOfTop()) {
					assertFalse("Block continued falling when water > top of block.",
							testBlock.isFalling());
				} else if(testWater < testBlock.getYOfBottom()) {
					assertTrue("Block stopped falling when water < bottom of block.",
							testBlock.isFalling());
				}
			}
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#drift(int)}.
	 */
	@Test
	public void testDrift_WhenNotFalling() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock = createBlock(randVal(), randVal(), randDim(), randDim(), 0);
			
			testBlock.drift(randVal());
			
			assertFalse("Block started falling after drift.", testBlock.isFalling());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#isFalling()}.
	 */
	@Test
	public void testIsFalling_WhenFalling() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testFalling = randVal();
			final Block testBlock =
					createBlock(randVal(), randVal(), randDim(), randDim(), testFalling);
			
			if(testFalling != 0) {
				assertTrue("Block should be falling.", testBlock.isFalling());
			}
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#isFalling()}.
	 */
	@Test
	public void testIsFalling_WhenNotFalling() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock = createBlock(randVal(), randVal(), randDim(), randDim(), 0);
			assertFalse("Block shouldn't be falling.", testBlock.isFalling());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#collides(int, int, int, int)}.
	 */
	@Test
	public void testCollides_SameResultUsingBlockVsValues() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testX = randVal(), testY = randVal();
			final int testWidth = randDim(), testHeight = randDim();
			final Block testBlock2 = createBlock(testX, testY, testWidth, testHeight, randVal());
			final Block testBlock1 =
					createBlock(randVal(), randVal(), randDim(), randDim(), randVal());
			
			assertEquals("The collision methods do not return the same result.",
					testBlock1.collides(testX, testY, testWidth, testHeight),
					testBlock1.collides(testBlock2));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#collides(game.objects.Block)}.
	 */
	@Test
	public void testCollides_CollidingBlocks() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoCollidingBlocks();
			assertTrue("Colliding Blocks should be colliding.",
					testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#collides(game.objects.Block)}.
	 */
	@Test
	public void testCollides_NoncollidingBlocksOnX() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoNoncollidingBlocksOnX();
			assertFalse("Non-colliding Blocks shouldn't be colliding.",
					testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#collides(game.objects.Block)}.
	 */
	@Test
	public void testCollides_NoncollidingBlocksOnY() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoNoncollidingBlocksOnY();
			assertFalse("Non-colliding Blocks shouldn't be colliding.",
					testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#getYOfTop()}.
	 */
	@Test
	public void testGetYOfTop() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testY = randVal();
			final int testHeight = randDim();
			final Block testBlock = createBlock(randVal(), testY, randDim(), testHeight, randVal());
			
			assertEquals("Game critical logic not working as expected.", testY + testHeight,
					testBlock.getYOfTop());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#getYOfBottom()}.
	 */
	@Test
	public void testGetYOfBottom() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testY = randVal();
			final Block testBlock = createBlock(randVal(), testY, randDim(), randDim(), randVal());
			
			assertEquals("Game critical logic not working as expected.", testY,
					testBlock.getYOfBottom());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#getXOfLeft()}.
	 */
	@Test
	public void testGetXOfLeft() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testX = randVal();
			final Block testBlock = createBlock(testX, randVal(), randDim(), randDim(), randVal());
			
			assertEquals("Game critical logic not working as expected.", testX,
					testBlock.getXOfLeft());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#getXOfRight()}.
	 */
	@Test
	public void testGetXOfRight() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testX = randVal();
			final int testWidth = randDim();
			final Block testBlock = createBlock(testX, randVal(), testWidth, randDim(), randVal());
			
			assertEquals("Game critical logic not working as expected.", testX + testWidth,
					testBlock.getXOfRight());
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#step(java.util.List)}.
	 */
	@Test
	public void testStep_BlocksFall() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final int testY = randVal();
			final int testFall = randVal();
			
			final List<Block> testList = new ArrayList<Block>();
			testList.add(createBlock(randVal(), testY, randDim(), randDim(), testFall));
			
			Block.step(testList);
			
			if(testFall != 0) {
				assertTrue("Block didn't fall.", testY != testList.get(0).getYOfBottom());
			}
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#step(java.util.List)}.
	 */
	@Test
	public void testStep_FixesCollisions() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = new ArrayList<Block>();
			testList.add(createBlock(randVal(), randVal(), randDim(), randDim(), randVal()));
			testList.add(createBlock(randVal(), randVal(), randDim(), randDim(), randVal()));
			
			Block.step(testList);
			
			assertFalse("Collisions after step.", testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#step(java.util.List)}.
	 */
	@Test
	public void testStep_FixesCollisions_StartingCollisions() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoCollidingBlocks();
			
			Block.step(testList);
			
			assertFalse("Collisions after step.", testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#step(java.util.List)}.
	 */
	@Test
	public void testStep_FixesCollisions_NoStartingCollisionsOnX() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoNoncollidingBlocksOnX();
			
			Block.step(testList);
			
			assertFalse("Collisions after step.", testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#step(java.util.List)}.
	 */
	@Test
	public void testStep_FixesCollisions_NoStartingCollisionsOnY() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final List<Block> testList = createTwoNoncollidingBlocksOnY();
			
			Block.step(testList);
			
			assertFalse("Collisions after step.", testList.get(0).collides(testList.get(1)));
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#paint(java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaint_SkipsWhenAboveScreen() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock =
					createBlock(randVal(), randVal(), randDim(), randDim(), randVal());
			testBlock.paint(null, testBlock.getYOfBottom() - 1, testBlock.getYOfBottom() - 2);
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#paint(java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaint_SkipsWhenBelowScreen() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock =
					createBlock(randVal(), randVal(), randDim(), randDim(), randVal());
			testBlock.paint(null, testBlock.getYOfTop() + 2, testBlock.getYOfTop() + 1);
		}
	}
	
	/**
	 * Test method for {@link game.objects.Block#paint(java.awt.Graphics, int, int)}.
	 */
	@Test
	public void testPaint_Runs() {
		final Graphics testG = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB).getGraphics();
		
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			final Block testBlock =
					createBlock(randVal(), randVal(), randDim(), randDim(), randVal());
			testBlock.paint(testG, randVal(), randVal());
		}
	}
	
	/**
	 * Creates a safe random value.
	 * 
	 * @return The random value.
	 */
	private static int randVal() {
		return RANDOMIZER.nextInt(VALUE_RANGE) - VALUE_RANGE / 2;
	}
	
	/**
	 * Creates a safe random dimensional value, logically must be positive.
	 * 
	 * @return The random value.
	 */
	private static int randDim() {
		return Math.abs(randVal()) + 1;
	}
	
	/**
	 * Creates and returns a block object.
	 * 
	 * @param x
	 *            The x coordinate of the block.
	 * @param y
	 *            The y coordinate of the block.
	 * @param width
	 *            The width of the block.
	 * @param height
	 *            The height of the block.
	 * @param fallingSpeed
	 *            The falling speed of the block.
	 * @return The created block.
	 */
	private static Block createBlock(int x, int y, int width, int height, int fallingSpeed) {
		return new Block(Color.BLACK, x, y, width, height, fallingSpeed);
	}
	
	/**
	 * Creates a List containing two colliding blocks.
	 * 
	 * @return An ArrayList containing two colliding blocks.
	 */
	private static List<Block> createTwoCollidingBlocks() {
		final int testX = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int testY = randVal();
		final int testWidth = RANDOMIZER.nextInt(Game.GAME_WIDTH) + 1;
		final int testHeight = randDim();
		
		final int testX2 = testX + RANDOMIZER.nextInt(testWidth);
		final int testY2 = testY + RANDOMIZER.nextInt(testHeight);
		final int testWidth2 = RANDOMIZER.nextInt(Game.GAME_WIDTH) + 1;
		final int testHeight2 = randDim();
		
		final List<Block> testList = new ArrayList<Block>();
		testList.add(createBlock(testX, testY, testWidth, testHeight, randVal()));
		testList.add(createBlock(testX2, testY2, testWidth2, testHeight2, randVal()));
		
		return testList;
	}
	
	/**
	 * Creates a List containing two non-colliding blocks with respect to x.
	 * 
	 * @return An ArrayList containing two non-colliding blocks.
	 */
	private static List<Block> createTwoNoncollidingBlocksOnX() {
		final int testX = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int testY = randVal();
		final int testWidth = RANDOMIZER.nextInt(Game.GAME_WIDTH - 2) + 1;
		final int testHeight = randDim();
		
		final int testWidth2 = RANDOMIZER.nextInt(Game.GAME_WIDTH - testWidth) + 1;
		final int testHeight2 = randDim();
		final int testY2 = randVal();
		final int testX2 =
				((testX + testWidth + RANDOMIZER.nextInt(Game.GAME_WIDTH - testWidth - testWidth2
						+ 1)) % Game.GAME_WIDTH);
		
		final List<Block> testList = new ArrayList<Block>();
		testList.add(createBlock(testX, testY, testWidth, testHeight, randVal()));
		testList.add(createBlock(testX2, testY2, testWidth2, testHeight2, randVal()));
		
		return testList;
	}
	
	/**
	 * Creates a List containing two non-colliding blocks with respect to y.
	 * 
	 * @return An ArrayList containing two non-colliding blocks.
	 */
	private static List<Block> createTwoNoncollidingBlocksOnY() {
		final int testX = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int testY = randVal();
		final int testWidth = RANDOMIZER.nextInt(Game.GAME_WIDTH - 1) + 1;
		final int testHeight = randDim();
		
		final int testWidth2 = RANDOMIZER.nextInt(Game.GAME_WIDTH - 1) + 1;
		final int testHeight2 = randDim();
		final int testX2 = RANDOMIZER.nextInt(Game.GAME_WIDTH);
		final int testY2 = testY + testHeight + randDim();
		
		final List<Block> testList = new ArrayList<Block>();
		testList.add(createBlock(testX, testY, testWidth, testHeight, randVal()));
		testList.add(createBlock(testX2, testY2, testWidth2, testHeight2, randVal()));
		
		return testList;
	}
	
}
