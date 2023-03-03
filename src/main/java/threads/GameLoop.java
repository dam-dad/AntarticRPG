package threads;

import java.util.ArrayList;

import engine.AssetSetter;
import engine.CollisionChecker;
import engine.EventHandler;
import engine.GameVariables;
import engine.UserInterface;
import engine.entity.Npc;
import engine.entity.Player;
import engine.tiles.TileHandler;
import handlers.KeyHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import object.SuperObject;

/*
 * Gameloop
 */
 
 /**
 * <h1> GameLoop <h1>
 * En la clase
 */

public class GameLoop extends AnimationTimer {

	private Canvas canvas;
	private GraphicsContext context;
	private int fps = 0;

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean inGame = true;
	public ArrayList<SuperObject> superObjects = new ArrayList<>();
	public UserInterface ui;

	public Player player;
	public Npc npc;
	public ArrayList<Npc> npcs = new ArrayList<>();

	private long lastTime = 0;
	private long accumulatedTime = 0;

	private KeyHandler keyHandler;
	private TileHandler tileHandler;

	private CollisionChecker checker;
	private EventHandler eHandler = new EventHandler(this);

//	private Light light;
	private AssetSetter aSetter;

	public GameLoop(Canvas canvas) {
		
		if (canvas == null)
			throw new NullPointerException("El canvas es nulo");
		this.canvas = canvas;
		this.context = canvas.getGraphicsContext2D();

		canvas.requestFocus();
		canvas.setFocusTraversable(true);

		player = new Player(canvas, this);
		keyHandler = new KeyHandler(canvas, this);
		tileHandler = new TileHandler(this, player, npcs);
		checker = new CollisionChecker(this);
		ui = new UserInterface(this);
		ui.setContext(context);
		aSetter = new AssetSetter(this, player);
		aSetter.setNpc();
		for (Npc npc : npcs) {
			npc.setContext(context);

//		light = new Light(this, 150);

//		tileHandler.setLight(light);

			context.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/dogicapixel.ttf"), 12));
		}

	}

	public void update(long currentNanoTime) {
		player.update(currentNanoTime);
		for (Npc npc : npcs) {
			npc.update();
		}
	}

	// Actuan como capas, se llama primero a TileHandler para dibujar la capa del
	// suelo
	// Y después se llama al player para que se dibuje por encima de la capa del
	// suelo

	public void paint() {

		tileHandler.paint();
		ui.paint();
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void handle(long currentNanoTime) {

		if (lastTime == 0) {
			lastTime = currentNanoTime;
			return;
		}

		long elapsedTime = currentNanoTime - lastTime;
		lastTime = currentNanoTime;

		if (elapsedTime > GameVariables.MAX_ELAPSED_NANOSECONDS)
			elapsedTime = (long) (GameVariables.MAX_ELAPSED_SECONDS * GameVariables.MAX_ELAPSED_NANOSECONDS);

		accumulatedTime += elapsedTime;

		if (accumulatedTime > GameVariables.MAX_ELAPSED_SECONDS * GameVariables.MAX_ELAPSED_NANOSECONDS)
			accumulatedTime = (long) (GameVariables.MAX_ELAPSED_SECONDS * GameVariables.MAX_ELAPSED_NANOSECONDS);

		while (accumulatedTime >= (long) (1.0 / 60 * GameVariables.MAX_ELAPSED_NANOSECONDS)) {
			update((long) (1.0 / 60 * 1000));
			accumulatedTime -= (long) (long) (1.0 / 60 * GameVariables.MAX_ELAPSED_NANOSECONDS);
		}

		paint();

	}

	public TileHandler getTileHandler() {
		return tileHandler;
	}

	public void setTileHandler(TileHandler tileHandler) {
		this.tileHandler = tileHandler;
	}

	public EventHandler geteHandler() {
		return eHandler;
	}

	public void seteHandler(EventHandler eHandler) {
		this.eHandler = eHandler;
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

	public CollisionChecker getCollisionChecker() {
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
