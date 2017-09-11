package game.objects;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests for {@link game.objects}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/29/11
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ BlockTests.class, PlayerSpriteTests.class, PlayerTests.class,
		RisingWaterTests.class })
public final class AllObjectsTests {}
