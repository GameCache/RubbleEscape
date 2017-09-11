package game.objects;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Keeps track of the slowly rising water.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class RisingWater {
	
	/** Control for the water ripple effect. */
	private static final int WAVE_PERIOD = 18, WAVE_LENGTH = 14, WAVE_CHANGE = 2, WAVE_HEIGHT = 3;
	
	/** Control for the water depth effect. */
	private static final double LIGHT_DEGRADATION = 0.985;
	
	/** How far the water rises each turn. */
	private static final double WATER_RISE = 0.50;
	
	/** The initial level of the water. */
	private static final int INITIAL_ALTITUDE = -50;
	
	/** The initial color of the water. */
	private static final Color WATER_COLOR = new Color(24, 134, 127);
	
	/** The depth colors for the water. */
	private static final List<Color> DEPTH_COLORS;
	
	static {
		final List<Color> waterColors = new ArrayList<Color>();
		
		double red = WATER_COLOR.getRed();
		double green = WATER_COLOR.getGreen();
		double blue = WATER_COLOR.getBlue();
		while((int)green != 0 && (int)blue != 0 && (int)red != 0) {
			waterColors.add(new Color((int)red, (int)green, (int)blue));
			red *= LIGHT_DEGRADATION;
			green *= LIGHT_DEGRADATION;
			blue *= LIGHT_DEGRADATION;
		}
		
		DEPTH_COLORS = Collections.unmodifiableList(waterColors);
	}
	
	/** How high the water is. */
	private double altitude;
	
	/**
	 * Constructs the water.
	 */
	public RisingWater() {
		reset();
	}
	
	/**
	 * Resets the water level to the default.
	 */
	public void reset() {
		altitude = INITIAL_ALTITUDE;
	}
	
	/**
	 * Controls the water during the frame.
	 * 
	 * @param blocks
	 *            Other block objects in the area.
	 * @param player
	 *            The player.
	 * @param spaceBelowPlayer
	 *            Amount of space below the player to the bottom of the screen.
	 */
	public void step(List<Block> blocks, Player player, int spaceBelowPlayer) {
		rise();
		fixWaterBlockCollisions(blocks, player, spaceBelowPlayer);
		player.drown((int)altitude);
	}
	
	/**
	 * Causes the water to rise.
	 */
	private void rise() {
		altitude += WATER_RISE;
	}
	
	/**
	 * Determines if the water is higher than the top of the screen and thus no need to continue
	 * simulating the game.
	 * 
	 * @param playerAltitude
	 *            The altitude of the player.
	 * @return Whether or not the game should stop simulating.
	 */
	public boolean shouldStopSimulating(int playerAltitude) {
		return (altitude > (double)playerAltitude + Game.GAME_HEIGHT + DEPTH_COLORS.size());
	}
	
	/**
	 * Draws this object on an image.
	 * 
	 * @param g
	 *            The graphics object for the image.
	 * @param screenTop
	 *            The altitude of the top of the screen.
	 * @param screenBottom
	 *            The altitude of the bottom of the screen.
	 */
	public void paint(Graphics g, int screenTop, int screenBottom) {
		if(altitude + WAVE_HEIGHT < screenBottom) {
			return;
		}
		
		final int waterTop = screenTop - (int)altitude;
		
		// Slowly darken water.
		for(int i = waterTop; i < Game.GAME_HEIGHT; i++) {
			if(DEPTH_COLORS.size() > i - waterTop) {
				g.setColor(DEPTH_COLORS.get(i - waterTop));
				g.drawLine(0, i, Game.GAME_WIDTH, i);
			} else {
				g.fillRect(0, i, Game.GAME_WIDTH, Game.GAME_HEIGHT - i);
				break;
			}
		}
		
		// Wave generation.
		g.setColor(Color.BLACK);
		g.drawLine(0, waterTop - 1, Game.GAME_WIDTH, screenTop - (int)altitude - 1);
		
		for(int i = -(int)(altitude * 2) % WAVE_PERIOD; i < Game.GAME_WIDTH; i += WAVE_PERIOD) {
			for(int j = 0; j < WAVE_HEIGHT; j++) {
				final int waveTop = waterTop - (j + 1);
				final int waveDistance = i + (WAVE_LENGTH - WAVE_CHANGE * j) - 1;
				
				g.setColor(Color.BLACK);
				g.drawLine(i + WAVE_CHANGE * j, waveTop - 1, waveDistance, waveTop - 1);
				g.setColor(WATER_COLOR);
				g.drawLine(i + WAVE_CHANGE * j, waveTop, waveDistance, waveTop);
			}
		}
	}
	
	/**
	 * Will cause blocks that hit the water to float, and will remove blocks that are so far
	 * submerged that they are not interact-able anymore.
	 * 
	 * @param blocks
	 *            Other block objects in the area.
	 * @param player
	 *            The player.
	 * @param spaceBelowPlayer
	 *            Amount of space below the player to the bottom of the screen.
	 */
	private void fixWaterBlockCollisions(List<Block> blocks, Player player, int spaceBelowPlayer) {
		final Iterator<Block> itr = blocks.iterator();
		while(itr.hasNext()) {
			final Block block = itr.next();
			if(altitude > block.getYOfTop() + spaceBelowPlayer) {
				itr.remove();
			} else {
				block.drift((int)altitude);
			}
		}
	}
	
}
