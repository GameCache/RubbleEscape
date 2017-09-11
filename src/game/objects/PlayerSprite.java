package game.objects;

import gui.GameManager;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Images to represent the player object. Loads images specifically for the format used by Rubble
 * Escape: [Base name][#][Right/Left].png where [#] is the frame number.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/10/10
 */
public enum PlayerSprite {
	
	/** States of the sprite. */
	IDLE("Idle"), RUN("Run"), JUMP("Jump"), FALL("Fall"), DIED("Died");
	
	/** The frame count that changes the animation. */
	private static final int ANIMATION_CHANGE = 12;
	
	/** Sprite images for the direction. */
	private final List<Image> rightImages, leftImages;
	
	/** Counts how many frames the sprite has gone through since animation change. */
	private int frameCount;
	
	/**
	 * Loads the images for the sprite animation.
	 * 
	 * @param file
	 *            The base name for the image.
	 */
	private PlayerSprite(String file) {
		rightImages = new ArrayList<Image>();
		leftImages = new ArrayList<Image>();
		Image loaded;
		
		for(int i = 1; (loaded = GameManager.loadImage(file + i + "Right.png")) != null; i++) {
			rightImages.add(loaded);
		}
		
		for(int i = 1; (loaded = GameManager.loadImage(file + i + "Left.png")) != null; i++) {
			leftImages.add(loaded);
		}
	}
	
	/**
	 * Returns the next sprite image for the facing direction.
	 * 
	 * @param isFacingRight
	 *            If the sprite is facing to the right.
	 * @return The sprite image.
	 */
	public final Image getNextImage(boolean isFacingRight) {
		if(isFacingRight) {
			frameCount = (frameCount + 1) % (ANIMATION_CHANGE * rightImages.size());
			return rightImages.get(frameCount / ANIMATION_CHANGE);
		} else {
			frameCount = (frameCount + 1) % (ANIMATION_CHANGE * leftImages.size());
			return leftImages.get(frameCount / ANIMATION_CHANGE);
		}
	}
	
	/**
	 * Returns the last returned sprite image for the facing direction.
	 * 
	 * @param isFacingRight
	 *            If the sprite is facing to the right.
	 * @return The sprite image.
	 */
	public final Image getPreviousImage(boolean isFacingRight) {
		if(isFacingRight) {
			return rightImages.get(frameCount / ANIMATION_CHANGE);
		} else {
			return leftImages.get(frameCount / ANIMATION_CHANGE);
		}
	}
	
}
