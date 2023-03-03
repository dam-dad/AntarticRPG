package engine.entity;

import engine.Direction;
import engine.GameVariables;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import threads.GameLoop;

public abstract class Entity {

	GameLoop gp;
	public int worldX, worldY;
	public int speed;
	
	public Image up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3, upIdle, downIdle, leftIdle, rightIdle;
	public Direction direction;
	
	public int contImages = 0;
	public int spriteNum = 0;
	
	public Rectangle areaSolid = new Rectangle(0, 0, GameVariables.ORIGINAL_TILE_SIZE, GameVariables.ORIGINAL_TILE_SIZE);
	public boolean colision = false;
	
	public Entity(GameLoop gl) {
		
	}
	
	public Rectangle getAreaSolid() {
		return areaSolid;
	}
	
	public int getWorldX() {
		return worldX;
	}

	public int getWorldY() {
		return worldY;
	}
	
}
