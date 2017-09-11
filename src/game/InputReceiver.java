package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Registers input from the player.
 * 
 * @author Jesse Brent
 * @version 1.0, 10/01/10
 */
public final class InputReceiver implements KeyListener {
	
	/** If the given key is being pressed. */
	private boolean upDown, rightDown, leftDown, wDown, aDown, dDown, zDown;
	
	/** If the given key is typed. */
	private boolean spacePressed, escPressed, enterPressed;
	
	/**
	 * Constructs the input receiver.
	 */
	public InputReceiver() {
		reset();
	}
	
	/**
	 * Resets the input receiver with initial values.
	 */
	public void reset() {
		upDown = false;
		rightDown = false;
		leftDown = false;
		wDown = false;
		aDown = false;
		dDown = false;
		zDown = false;
		spacePressed = false;
		enterPressed = false;
		escPressed = false;
	}
	
	/**
	 * If input is being received to move to the right.
	 * 
	 * @return If moving right.
	 */
	public boolean isMovingRight() {
		return rightDown || dDown;
	}
	
	/**
	 * If input is being received to move to the left.
	 * 
	 * @return If moving left.
	 */
	public boolean isMovingLeft() {
		return leftDown || aDown;
	}
	
	/**
	 * If input is being received to jump.
	 * 
	 * @return If jumping.
	 */
	public boolean isJumping() {
		return upDown || wDown || zDown;
	}
	
	/**
	 * If input is being received to pause.
	 * 
	 * @return If paused.
	 */
	public boolean isPaused() {
		return spacePressed ^ enterPressed;
	}
	
	/**
	 * If input is being received to suicide.
	 * 
	 * @return If suicided.
	 */
	public boolean isSuicide() {
		return escPressed;
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_LEFT: {
				leftDown = true;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rightDown = true;
				break;
			}
			case KeyEvent.VK_UP: {
				upDown = true;
				break;
			}
			case KeyEvent.VK_A: {
				aDown = true;
				break;
			}
			case KeyEvent.VK_D: {
				dDown = true;
				break;
			}
			case KeyEvent.VK_W: {
				wDown = true;
				break;
			}
			case KeyEvent.VK_Z: {
				zDown = true;
				break;
			}
			default: {
				break;
			}
		}
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()) {
			case KeyEvent.VK_ENTER: {
				enterPressed = !enterPressed;
				break;
			}
			case KeyEvent.VK_SPACE: {
				spacePressed = !spacePressed;
				break;
			}
			case KeyEvent.VK_ESCAPE: {
				escPressed = !escPressed;
				break;
			}
			case KeyEvent.VK_LEFT: {
				leftDown = false;
				break;
			}
			case KeyEvent.VK_RIGHT: {
				rightDown = false;
				break;
			}
			case KeyEvent.VK_UP: {
				upDown = false;
				break;
			}
			case KeyEvent.VK_A: {
				aDown = false;
				break;
			}
			case KeyEvent.VK_D: {
				dDown = false;
				break;
			}
			case KeyEvent.VK_W: {
				wDown = false;
				break;
			}
			case KeyEvent.VK_Z: {
				zDown = false;
				break;
			}
			default: {
				break;
			}
		}
	}
	
	/*
	 * {@inheritDoc}
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent key) {}
	
}
