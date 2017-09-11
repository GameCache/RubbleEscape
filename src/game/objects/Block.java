package game.objects;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * Represents a block object.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class Block {
	
	/** The color of the block. */
	private final Color color;
	
	/** Dimension of the block. */
	private final int width, height;
	
	/** The x coordinate of the block. */
	private final int x;
	
	/** The y coordinate of the block. */
	private int y;
	
	/** How fast the block is falling. */
	private int fallingSpeed;
	
	/** If the block has stopped falling and is stationary. */
	private boolean stationary;
	
	/**
	 * Constructs a block object.
	 * 
	 * @param blockColor
	 *            The color of the block.
	 * @param blockX
	 *            The x coordinate of the block.
	 * @param blockY
	 *            The y coordinate of the block.
	 * @param blockWidth
	 *            The width of the block.
	 * @param blockHeight
	 *            The height of the block.
	 * @param blockFallingSpeed
	 *            The falling speed of the block.
	 */
	public Block(Color blockColor, int blockX, int blockY, int blockWidth, int blockHeight,
			int blockFallingSpeed) {
		color = blockColor;
		x = blockX;
		y = blockY;
		width = blockWidth;
		height = blockHeight;
		fallingSpeed = blockFallingSpeed;
		stationary = false;
	}
	
	/**
	 * Controls the falling of the block.
	 */
	private void fall() {
		y -= fallingSpeed;
	}
	
	/**
	 * Floats the block if it is halfway submerged in water.
	 * 
	 * @param waterAltitude
	 *            The altitude of the water.
	 */
	public void drift(int waterAltitude) {
		if(waterAltitude >= y + height / 2) {
			fallingSpeed = 0;
		}
	}
	
	/**
	 * Checks if the block is currently falling.
	 * 
	 * @return If the block is falling.
	 */
	public boolean isFalling() {
		return fallingSpeed != 0;
	}
	
	/**
	 * Checks if this block collides with other blocks and fixes the position.
	 * 
	 * @param blocks
	 *            Other block objects in the area.
	 */
	private void fixCollisions(List<Block> blocks) {
		if(stationary) {
			return;
		}
		
		for(final Block otherBlock : blocks) {
			if(this.collides(otherBlock) && (otherBlock != this)) {
				if(this.y > otherBlock.y) {
					this.fallingSpeed = otherBlock.fallingSpeed;
					this.y = otherBlock.y + otherBlock.height;
					this.fixCollisions(blocks);
					break;
				} else {
					otherBlock.fallingSpeed = this.fallingSpeed;
					otherBlock.y = this.y + this.height;
					otherBlock.fixCollisions(blocks);
				}
			}
		}
		
		stationary = !isFalling();
	}
	
	/**
	 * Checks if this block is colliding with another block.
	 * 
	 * @param otherBlock
	 *            The other block.
	 * @return Whether or not the block is colliding with the block.
	 */
	public boolean collides(Block otherBlock) {
		return collides(otherBlock.x, otherBlock.y, otherBlock.width, otherBlock.height);
	}
	
	/**
	 * Checks if this block is colliding with an object.
	 * 
	 * @param objectX
	 *            The x position of the object.
	 * @param objectY
	 *            The y position of the object.
	 * @param objectWidth
	 *            The width of the object.
	 * @param objectHeight
	 *            The height of the object.
	 * @return Whether or not the block is colliding with the object.
	 */
	public boolean collides(int objectX, int objectY, int objectWidth, int objectHeight) {
		
		// They can't be colliding if the bottom of this object is higher than the top of the other.
		if(this.y >= objectY + objectHeight) {
			return false;
		}
		
		// They can't be colliding if the top of this object is lower than the bottom of the other.
		if(this.y + this.height <= objectY) {
			return false;
		}
		
		// Treated as normal if neither or both objects are splitting the unbounded screen.
		if((this.x + this.width >= Game.GAME_WIDTH) == (objectX + objectWidth >= Game.GAME_WIDTH)) {
			
			// They can't be colliding if the right of this object is to the left of the other.
			if(this.x + this.width <= objectX) {
				return false;
			}
			
			// They can't be colliding if the left of this object is to the right of the other.
			if(this.x >= objectX + objectWidth) {
				return false;
			}
			
		} else {
			
			/*
			 * With one block splitting the screen, they are colliding if the right of this block is
			 * greater than the left of the other.
			 */
			if((this.x + this.width) % Game.GAME_WIDTH > objectX) {
				return true;
			}
			
			/*
			 * With one block splitting the screen, they are colliding if the left of this block is
			 * less than the right of the other.
			 */
			if(this.x < (objectX + objectWidth) % Game.GAME_WIDTH) {
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the altitude of the top of the block.
	 * 
	 * @return The altitude of the top of the block.
	 */
	public int getYOfTop() {
		return y + height;
	}
	
	/**
	 * Returns the altitude of the bottom of the block.
	 * 
	 * @return The altitude of the bottom of the block.
	 */
	public int getYOfBottom() {
		return y;
	}
	
	/**
	 * Returns the position of the left side of the block.
	 * 
	 * @return The position of the left side of the block.
	 */
	public int getXOfLeft() {
		return x;
	}
	
	/**
	 * Returns the position of the right side of the block.
	 * 
	 * @return The position of the right side of the block.
	 */
	public int getXOfRight() {
		return x + width;
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
		if(y + height < screenBottom || y > screenTop) {
			return;
		}
		
		final int blockTop = screenTop - (y + height);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, blockTop, width, height);
		g.setColor(color);
		g.fill3DRect(x + 1, blockTop + 1, width - 1, height - 1, true);
		
		// For splitting blocks on the screen.
		if(x + width > Game.GAME_WIDTH) {
			final int splitWidth = x + width - Game.GAME_WIDTH;
			
			g.setColor(Color.BLACK);
			g.drawRect(-1, blockTop, splitWidth, height);
			g.setColor(color);
			g.fill3DRect(0, blockTop + 1, splitWidth - 1, height - 1, true);
		}
	}
	
	/**
	 * Controls the blocks during the frame.
	 * 
	 * @param blocks
	 *            The block objects in the game.
	 */
	public static void step(List<Block> blocks) {
		for(final Block block : blocks) {
			block.fall();
		}
		
		// Block collisions must be fixed after ALL the blocks have fallen to insure integrity.
		for(final Block block : blocks) {
			block.fixCollisions(blocks);
		}
	}
	
}
