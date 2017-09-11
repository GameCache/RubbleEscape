import game.AllGameTests;
import gui.AllGuiTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Tests for the project.
 * 
 * @author Jesse Brent
 * @version 1.0, 01/29/11
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ AllGameTests.class, AllGuiTests.class })
public final class RunAllTests {}
