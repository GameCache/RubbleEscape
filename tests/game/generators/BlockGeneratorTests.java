package game.generators;

import static org.junit.Assert.assertNotNull;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for {@link game.generators.BlockGenerator}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/31/11
 */
public final class BlockGeneratorTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 100000;
	
	/** Controls all the randomization for the tests. */
	private static final Random RANDOMIZER = new Random();
	
	/**
	 * Test method for {@link game.generators.BlockGenerator#shouldGenerateBlock()}.
	 */
	@Test
	public void testShouldGenerateBlock() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(BlockGenerator.shouldGenerateBlock());
		}
	}
	
	/**
	 * Test method for {@link game.generators.BlockGenerator#generateBlock(int)}.
	 */
	@Test
	public void testGenerateBlock() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(BlockGenerator.generateBlock(RANDOMIZER.nextInt()));
		}
	}
	
	/**
	 * Test method for {@link game.generators.BlockGenerator#generateStartBlock(int)}.
	 */
	@Test
	public void testGenerateStartBlock() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(BlockGenerator.generateStartBlock(RANDOMIZER.nextInt()));
		}
	}
	
	/**
	 * Test method for {@link game.generators.BlockGenerator#generateTitleBlock()}.
	 */
	@Test
	public void testGenerateTitleBlock() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			assertNotNull(BlockGenerator.generateTitleBlock());
		}
	}
	
}
