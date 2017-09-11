package game;

import game.generators.AllGeneratorsTests;
import game.objects.AllObjectsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests for {@link game}.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/29/11
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AllGeneratorsTests.class, AllObjectsTests.class })
public class AllGameTests {}
