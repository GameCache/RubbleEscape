package game.objects;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Tests for {@link game.objects.PlayerSprite}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/31/11
 */
public final class PlayerSpriteTests {
	
	/** How many times each test will repeat with new values. */
	private static final int TIMES_TO_RUN_TESTS = 100000;
	
	/**
	 * Test method for {@link game.objects.PlayerSprite#getNextImage(boolean)}.
	 */
	@Test
	public void testGetNextImage() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			for(final PlayerSprite testSprite : PlayerSprite.values()) {
				assertNotNull("Sprite image missing.", testSprite.getNextImage(true));
				assertNotNull("Sprite image missing.", testSprite.getNextImage(false));
			}
		}
	}
	
	/**
	 * Test method for {@link game.objects.PlayerSprite#getPreviousImage(boolean)}.
	 */
	@Test
	public void testGetPreviousImage() {
		for(int i = 0; i < TIMES_TO_RUN_TESTS; i++) {
			for(final PlayerSprite testSprite : PlayerSprite.values()) {
				assertNotNull("Sprite image missing.", testSprite.getPreviousImage(true));
				assertNotNull("Sprite image missing.", testSprite.getPreviousImage(false));
			}
		}
	}
	
}
