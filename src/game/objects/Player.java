package game.objects;

import game.Game;
import game.InputReceiver;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

/**
 * Represents the player.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/1/10
 */
public final class Player {
	
	/** How far the player sprite moves on each step. */
	private static final int MOVE_SPEED = 5;
	
	/** The velocity of the player when starting a jump. */
	private static final int JUMP_POWER = 16;
	
	/** The acceleration due to gravity. */
	private static final int GRAVITY_ACCELERATION = -1;
	
	/** Dimension of the player. */
	private static final int WIDTH = 24, HEIGHT = 48;
	
	/** Coordinate of the player. */
	private int x, y;
	
	/** The current vertical velocity of the player. */
	private int velocity;
	
	/** Player property. */
	private boolean isFacingRight, isAirborne, isMoving, isAlive;
	
	/**
	 * Constructs the player object.
	 * 
	 * @param playerX
	 *            The x coordinate of the player.
	 * @param playerY
	 *            The y coordinate of the player.
	 */
	public Player(int playerX, int playerY) {
		reset(playerX, playerY);
	}
	
	/**
	 * Resets the location of the player.
	 * 
	 * @param playerX
	 *            The x coordinate of the player.
	 * @param playerY
	 *            The y coordinate of the player.
	 */
	public void reset(int playerX, int playerY) {
		x = playerX;
		y = playerY;
		velocity = GRAVITY_ACCELERATION;
		
		isAlive = true;
		isMoving = false;
		isAirborne = false;
		isFacingRight = true;
	}
	
	/**
	 * Controls the player during the frame.
	 * 
	 * @param blocks
	 *            The block objects in the game.
	 * @param input
	 *            The key input receiver for the game.
	 */
	public void step(List<Block> blocks, InputReceiver input) {
		
		if(!isAlive) {
			return;
		}
		
		fall();
		
		/*
		 * Collisions below is checked first resolving if the player remains airborne after the
		 * gravity is applied.
		 */
		fixCollisionsBelow(blocks);
		
		if(input.isJumping()) {
			jump();
		}
		
		// Kills the player if he's being squashed.
		if(fixCollisionsAbove(blocks)) {
			isAlive = false;
			return;
		}
		
		// Player horizontal movement must be done after vertical movement is resolved.
		if(input.isMovingLeft()) {
			if(!input.isMovingRight()) {
				move(false);
				fixCollisionsSide(blocks);
			}
		} else if(input.isMovingRight()) {
			move(true);
			fixCollisionsSide(blocks);
		}
	}
	
	/**
	 * Controls the falling of the player.
	 */
	private void fall() {
		velocity += GRAVITY_ACCELERATION;
		y += velocity;
	}
	
	/**
	 * Moves the player horizontally.
	 * 
	 * @param isToTheRight
	 *            If the movement is to the right.
	 */
	private void move(boolean isToTheRight) {
		isFacingRight = isToTheRight;
		isMoving = true;
		
		if(isFacingRight) {
			x = (x + MOVE_SPEED) % Game.GAME_WIDTH;
		} else {
			x = (x - MOVE_SPEED + Game.GAME_WIDTH) % Game.GAME_WIDTH;
		}
	}
	
	/**
	 * Causes the player to jump.
	 */
	private void jump() {
		if(!isAirborne) {
			velocity = JUMP_POWER;
			isAirborne = true;
		}
	}
	
	/**
	 * Kills the player.
	 */
	public void suicide() {
		isAlive = false;
	}
	
	/**
	 * Attempts to drown the player if submerged in water.
	 * 
	 * @param waterAltitude
	 *            The altitude of the rising water.
	 */
	public void drown(int waterAltitude) {
		if(waterAltitude > y + HEIGHT) {
			isAlive = false;
		}
	}
	
	/**
	 * Returns whether or not the player is alive.
	 * 
	 * @return Whether of not the player is alive.
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * Returns how high the player is.
	 * 
	 * @return The altitude of the player.
	 */
	public int getAltitude() {
		return y;
	}
	
	/**
	 * Draws this object on an image.
	 * 
	 * @param g
	 *            The graphics object for the image.
	 * @param screenTop
	 *            The altitude of the top of the screen.
	 */
	public void paint(Graphics g, int screenTop) {
		final Image spriteToDraw;
		
		if(isAlive) {
			if(isAirborne) {
				if(velocity >= 0) {
					spriteToDraw = PlayerSprite.JUMP.getNextImage(isFacingRight);
				} else {
					spriteToDraw = PlayerSprite.FALL.getNextImage(isFacingRight);
				}
			} else {
				if(isMoving) {
					spriteToDraw = PlayerSprite.RUN.getNextImage(isFacingRight);
				} else {
					spriteToDraw = PlayerSprite.IDLE.getNextImage(isFacingRight);
				}
			}
		} else {
			spriteToDraw = PlayerSprite.DIED.getNextImage(isFacingRight);
		}
		isMoving = false;
		
		final int playerTop = screenTop - (y + HEIGHT);
		g.drawImage(spriteToDraw, x, playerTop, WIDTH, HEIGHT, null);
		
		// For splitting the player on the screen.
		if(x + WIDTH > Game.GAME_WIDTH) {
			final int splitX = 0 - (WIDTH - (x + WIDTH) % Game.GAME_WIDTH);
			g.drawImage(spriteToDraw, splitX, playerTop, WIDTH, HEIGHT, null);
		}
	}
	
	/**
	 * Checks if this player collides with blocks above the sprite and fixes the collisions. Does
	 * not fix the collision if the player is not airborne, since the player is being squashed.
	 * Returns whether or not the player is being squashed.
	 * 
	 * @param blocks
	 *            Block objects in the area.
	 * @return Whether or not the player is being squashed.
	 */
	private boolean fixCollisionsAbove(List<Block> blocks) {
		for(final Block block : blocks) {
			/*
			 * Utilizes the top of the player with a height of zero. Thus the collision
			 * detection checks if player is hitting the block with his head.
			 */
			if(block.collides(x, y + HEIGHT, WIDTH, 0)) {
				if(!isAirborne) {
					return true;
				}
				
				if(velocity >= 0) {
					velocity = GRAVITY_ACCELERATION;
				}
				y = block.getYOfBottom() - HEIGHT;
				
				// Checks collisions below in case the position change caused a collision.
				fixCollisionsBelow(blocks);
				
				// If player is again colliding with this block, he's being squashed.
				if(block.collides(x, y + HEIGHT, WIDTH, 0)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if this player collides with blocks to the sprite's side and fixes the collisions.
	 * 
	 * @param blocks
	 *            Block objects in the area.
	 */
	private void fixCollisionsSide(List<Block> blocks) {
		for(final Block block : blocks) {
			if(block.collides(x, y, WIDTH, HEIGHT)) {
				if(isFacingRight) {
					x = block.getXOfLeft() - WIDTH;
				} else {
					x = block.getXOfRight();
				}
				isMoving = false;
			}
		}
	}
	
	/**
	 * Checks if this player collides with blocks below the sprite and fixes the collisions. Also
	 * determines and sets if the player is airborne or not.
	 * 
	 * @param blocks
	 *            Block objects in the area.
	 */
	private void fixCollisionsBelow(List<Block> blocks) {
		boolean collided = false;
		
		for(final Block block : blocks) {
			/*
			 * Utilizes the bottom of the player with a height of zero. Thus the collision
			 * detection checks if player is hitting the block with his feet.
			 */
			if(block.collides(x, y, WIDTH, 0)) {
				y = block.getYOfTop();
				velocity = 0;
				collided = true;
			}
		}
		
		isAirborne = !collided;
	}
	
}
