package threads;

import engine.GameVariables;
import engine.SnowParticleEmitter;
import engine.entity.Player;
import engine.tiles.TileHandler;
import handlers.KeyHandler;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/*
 * Gameloop
 */

public class GameLoop extends Thread {
	
	private Canvas canvas;
	private GraphicsContext context;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean inGame = true;	

	public Player player;

	private KeyHandler keyHandler;
	private TileHandler tileHandler;
	
	public GameLoop(Canvas canvas) {
		if(canvas == null)
			throw new NullPointerException("El canvas es nulo");
		this.canvas = canvas;
		this.context = canvas.getGraphicsContext2D();
		
		canvas.requestFocus();
		canvas.setFocusTraversable(true);
		
		player = new Player(canvas, this);
		keyHandler = new KeyHandler(canvas, this);
		tileHandler = new TileHandler(this, player);
		
	}
	
	public void update() { 
		player.update();
		
	}
	
	//Actuan como capas, se llama primero a TileHandler para dibujar la capa del suelo
	//Y después se llama al player para que se dibuje por encima de la capa del suelo
	public void paint() { 
		Platform.runLater(() -> {
			tileHandler.paint();
			player.paint();
		});
	}

	@Override
	public void run() {
		
		double tiempoPasado = 0;
		double ultimaVezPintada = System.nanoTime();
		long ahora;
		long temp = 0;
		int pintarFPS = 0;
		
		while(inGame) {
			//Guarda el tiempo actual
			ahora = System.nanoTime();
			
			//Representa la cantidad de tiempo que ha pasado desde la última actualición en segundos.
			tiempoPasado += (ahora - ultimaVezPintada) / GameVariables.INTERVALO;
			
			//Representa el tiempo que ha pasado desde que se mostraron los FPS
			temp += (ahora - ultimaVezPintada);
			
			//Se actualiza la variable ultimaVezPintada para que la última vez pintada, sea ahora.
			ultimaVezPintada = ahora;
			
			if(tiempoPasado >= 1) {
				update();
				paint();
				tiempoPasado--;
				pintarFPS++;
			}
			
			//Si el tiempo acumulado es mayor o igual a 1 segundo (1.000.000.000 nanosegundos)
			if(temp >= 1000000000) {
				System.out.println("FPS: "+ pintarFPS);
				//Se reinician para calcularlos en el próximo segundo
				pintarFPS = 0;
				temp = 0;
			}
		}
		
	}
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isUpPressed() {
		return upPressed;
	}

	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	public boolean isDownPressed() {
		return downPressed;
	}

	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
}
