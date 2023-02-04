package handlers;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import threads.GameLoop;

public class KeyHandler {

	private GameLoop loop;
	private Canvas c;
	
	public KeyHandler(Canvas c, GameLoop loop) {
		if(loop == null || c == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
		this.c = c;

		c.setFocusTraversable(true);
		c.requestFocus();
		
		c.setOnKeyPressed(this::onKeyPressed);
		c.setOnKeyReleased(this::onKeyReleased);
	
	}
	
	public void onKeyPressed(KeyEvent e) {
		KeyCode code = e.getCode();
		switch(code) {
		case W: loop.upPressed = true; break;
		case A: loop.leftPressed = true; break;
		case S: loop.downPressed = true; break;
		case D: loop.rightPressed = true; break;
		case UP: loop.upPressed = true; break;
		case DOWN: loop.downPressed = true; break;
		case LEFT: loop.leftPressed = true; break;
		case RIGHT: loop.rightPressed = true; break;
		default: break;
		}
		
	}
	
	public void onKeyReleased(KeyEvent e) {
		KeyCode code = e.getCode();
		switch(code) {
		case W: loop.upPressed = false; break;
		case A: loop.leftPressed = false; break;
		case S: loop.downPressed = false; break;
		case D: loop.rightPressed = false; break;
		case UP: loop.upPressed = false; break;
		case DOWN: loop.downPressed = false; break;
		case LEFT: loop.leftPressed = false; break;
		case RIGHT: loop.rightPressed = false; break;
		default: break;
		}
	}
	
}
