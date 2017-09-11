package game.generators;

import java.awt.Color;

/**
 * Generates the game's background color.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class BackgroundGenerator {
	
	/** The maximum saturation of a color for the background. */
	private static final int MAX_COLOR_SATURATION = 190;
	
	/** The base saturation for generating the color for the background. */
	private static final int BASE_COLOR_SATURATION = 60;
	
	/** How fast the background color changes, smaller is faster. */
	private static final int COLOR_PHASE = 5;
	
	/** How many color shifts the background goes through. */
	private static final int COLOR_SHIFTS = 6;
	
	/**
	 * BackgroundGenerator is a utility class and cannot be instantiated.
	 */
	private BackgroundGenerator() {}
	
	/**
	 * Generates a nice background color based upon altitude.
	 * 
	 * @param altitude
	 *            The height to generate the background color.
	 * @return The generated background color.
	 */
	public static Color generateBackgroundColor(int altitude) {
		int red = 0;
		int blue = 0;
		int green = 0;
		
		blue =
				(Math.abs(altitude) / COLOR_PHASE + BASE_COLOR_SATURATION)
						% (MAX_COLOR_SATURATION * COLOR_SHIFTS + 1);
		
		if(blue > MAX_COLOR_SATURATION) {
			green = blue - MAX_COLOR_SATURATION;
			blue = MAX_COLOR_SATURATION;
			if(green > MAX_COLOR_SATURATION) {
				red = green - MAX_COLOR_SATURATION;
				green = MAX_COLOR_SATURATION;
				if(red > MAX_COLOR_SATURATION) {
					blue -= red - MAX_COLOR_SATURATION;
					red = MAX_COLOR_SATURATION;
				}
			}
		}
		
		if(blue < 0) {
			green += blue;
			blue = 0;
			if(green < 0) {
				red += green;
				green = 0;
				if(red <= 0) {
					red = 0;
				}
			}
		}
		
		return new Color(red, green, blue);
	}
	
}
