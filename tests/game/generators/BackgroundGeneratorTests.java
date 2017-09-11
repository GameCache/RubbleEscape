package game.generators;

import java.util.Random;
import org.junit.Test;

/**
 * Tests for {@link game.generators.BackgroundGenerator}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/31/11
 */
public final class BackgroundGeneratorTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 1000000;
	
	/** Controls all the randomization for the tests. */
	private static final Random RANDOMIZER = new Random();
	
	/**
	 * Test method for {@link game.generators.BackgroundGenerator#generateBackgroundColor(int)}.
	 */
	@Test
	public void testGenerateBackgroundColor() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			BackgroundGenerator.generateBackgroundColor(RANDOMIZER.nextInt());
		}
	}
	
}
