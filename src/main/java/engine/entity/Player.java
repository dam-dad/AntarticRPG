package engine.entity;


import engine.Direction;
import engine.GameVariables;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import threads.GameLoop;

public class Player extends Entity {

	private Canvas c;
	private GameLoop loop;
	private GraphicsContext context;
	private boolean idle;
	
	public final int screenX;
	public final int screenY;
	
	public Player(Canvas c, GameLoop loop) {
		super();
		if(c == null || loop == null)
			throw new NullPointerException("Valor nulo.");
		this.c = c;
		this.context = c.getGraphicsContext2D();
		this.loop = loop;
		
		screenX = GameVariables.SCREEN_WIDTH / 2;
		screenY = GameVariables.SCREEN_HEIGHT / 2;
		
		areaSolid = new Rectangle(0, 0, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
		
		setValoresPorDefecto();
		loadImages();
	}
	
	public void setValoresPorDefecto() {
		worldX = GameVariables.TILE_SIZE * 23;
		worldY = GameVariables.TILE_SIZE * 21;

		speed = GameVariables.SPEED;
		direction = Direction.DOWN;

	}
	
	public void update() {
		idle = (!loop.upPressed && !loop.downPressed && !loop.leftPressed && !loop.rightPressed);
		colision = false;

		if(loop.upPressed && worldY > 0) {
			direction = Direction.UP;
			loop.getChecker().checkTile(this);
			if(!colision)
				worldY -= speed;		
			
		} else if(loop.downPressed && worldY < (GameVariables.SCREEN_HEIGHT - GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER)) {
			direction = Direction.DOWN;
			loop.getChecker().checkTile(this);
			if(!colision)
				worldY += speed;			
			
		} else if(loop.leftPressed && worldX > 0) {
			direction = Direction.LEFT;
			loop.getChecker().checkTile(this);
			if(!colision)
				worldX -= speed;
			
		} else if(loop.rightPressed && worldX < GameVariables.SCREEN_WIDTH - GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER) {
			direction = Direction.RIGHT;
			loop.getChecker().checkTile(this);
			if(!colision)
				worldX += speed;
		}
		
	    contImages++;
	    if(contImages > 12) {
    	  if(spriteNum == 0)
    	    spriteNum = 1;
    	  else if(spriteNum == 1)
    	    spriteNum = 2;
    	  else if(spriteNum == 2)
    	    spriteNum = 1;
    	  else if(spriteNum == 1)
    		  spriteNum = 0;
    	  contImages = 0;
	    }
	    
	}
	
	public void paint() {
		drawPlayer();
	}
	
	private void drawPlayer() {
		
		Image img = null;
		
		switch(direction) {
		case UP:
			if(spriteNum == 1)
				img = up1;
			else if(spriteNum == 2)
				img = up2;
			else if(spriteNum == 3)
				img = up3;
			else
				img = up1;
			break;
		case DOWN:
			if(spriteNum == 1)
				img = down1;
			else if(spriteNum == 2)
				img = down2;
			else if(spriteNum == 3)
				img = down3;
			else
				img = down1;
			break;
		case LEFT:
			if(spriteNum == 1)
				img = left1;
			else if(spriteNum == 2)
				img = left2;
			else if(spriteNum == 3)
				img = left3;
			else 
				img = left1;
			break;
		case RIGHT:
			if(spriteNum == 1)
				img = right1;
			else if(spriteNum == 2)
				img = right2;
			else if(spriteNum == 3)
				img = right3;
			else
				img = right1;
			break;
		}

		if(idle) {
			if(direction == Direction.UP)
				img = upIdle;
			else if(direction == Direction.DOWN)
				img = downIdle;
			else if(direction == Direction.LEFT)
				img = leftIdle;
			else if(direction == Direction.RIGHT)
				img = rightIdle;
		}
		
		context.drawImage(img, screenX, screenY, GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER, GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER);

	}
	
	private void loadImages() {
		up1 = new Image(getClass().getResourceAsStream("/assets/player/upEsquimal.png"));
		up2 = new Image(getClass().getResourceAsStream("/assets/player/up1Esquimal.png"));
		up3 = new Image(getClass().getResourceAsStream("/assets/player/up2Esquimal.png"));
		down1 = new Image(getClass().getResourceAsStream("/assets/player/downEsquimal.png"));
		down2 = new Image(getClass().getResourceAsStream("/assets/player/down1Esquimal.png"));
		down3 = new Image(getClass().getResourceAsStream("/assets/player/down2Esquimal.png"));
		left1 = new Image(getClass().getResourceAsStream("/assets/player/leftEsquimal.png"));
		left2 = new Image(getClass().getResourceAsStream("/assets/player/left1Esquimal.png"));
		left3 = new Image(getClass().getResourceAsStream("/assets/player/left2Esquimal.png"));
		right1 = new Image(getClass().getResourceAsStream("/assets/player/rightEsquimal.png"));
		right2 = new Image(getClass().getResourceAsStream("/assets/player/right1Esquimal.png"));
		right3 = new Image(getClass().getResourceAsStream("/assets/player/right2Esquimal.png"));
		upIdle = new Image(getClass().getResourceAsStream("/assets/player/upIdle.gif"));
		downIdle = new Image(getClass().getResourceAsStream("/assets/player/downIdle.gif"));
		leftIdle = new Image(getClass().getResourceAsStream("/assets/player/leftIdle.gif"));
		rightIdle = new Image(getClass().getResourceAsStream("/assets/player/rightIdle.gif"));
	}

	public int getWorldX() {
		return worldX;	
	}

	public int getWorldY() {
		return worldY;
	}

	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public boolean isIdle() {
		return idle;
	}
	
}
