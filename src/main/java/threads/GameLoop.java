package threads;

import java.util.ArrayList;

import engine.AssetSetter;
import engine.CollisionChecker;
import engine.GameVariables;
import engine.entity.Entity;
import engine.entity.Npc;
import engine.entity.Player;
import engine.light.Light;
import engine.tiles.TileHandler;
import handlers.KeyHandler;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/*
 * Gameloop
 */

public class GameLoop extends Thread {
	
	private Canvas canvas;
	private GraphicsContext context;
	private int fps = 0;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean inGame = true;	

	public Player player;
	public ArrayList<Npc> npcs = new ArrayList<>();

	private KeyHandler keyHandler;
	private TileHandler tileHandler;
	private CollisionChecker checker;


	private Light light;
	private AssetSetter aSetter;
	
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
		checker = new CollisionChecker(this);
		aSetter = new AssetSetter(this);
		aSetter.setNpc();
//		light = new Light(this, 150);
		
//		tileHandler.setLight(light);
		
		context.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/dogicapixel.ttf"), 12));
	}
	
	public void update() { 
		player.update();
	}
	
	//Actuan como capas, se llama primero a TileHandler para dibujar la capa del suelo
	//Y después se llama al player para que se dibuje por encima de la capa del suelo
	
	public void paint() { 
		Platform.runLater(() -> {
			tileHandler.paint();
			context.setFill(Color.BLACK);
			context.clearRect(GameVariables.SCREEN_WIDTH - 75, 15, 65, 20);
			context.fillText("FPS: " + fps, GameVariables.SCREEN_WIDTH - 75, 30);
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
				this.fps = pintarFPS;
				//Se reinician para calcularlos en el próximo segundo
				pintarFPS = 0;
				temp = 0;
			}
		}
		
	}
	public ArrayList<Npc> getNpcs() {
		return npcs;
	}

	public void setNpcs(ArrayList<Npc> npcs) {
		this.npcs = npcs;
	}
	
	public int getFps() {
		return fps;
	}
	
	public TileHandler getHandler() {
		return tileHandler;
	}
	
	public CollisionChecker getChecker() {
		return checker;
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
