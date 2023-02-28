package engine.entity;

import threads.GameLoop;
import engine.Direction;
import engine.GameVariables;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Npc extends Entity{

	private GraphicsContext context;

	public Npc(GameLoop gl) {
		super(gl);
		
		direction = Direction.DOWN;
		speed = 1;
		loadImages();
	}
	
	
	
	public void loadImages() {
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

		
		context.drawImage(img, GameVariables.SCREEN_WIDTH/2, GameVariables.SCREEN_HEIGHT/2, GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER, GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER);

	}
	
	public void setContext(GraphicsContext context) {
		this.context = context;
	}

}
