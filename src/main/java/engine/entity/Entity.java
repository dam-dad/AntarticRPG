package engine.entity;

import engine.Direction;
import javafx.scene.image.Image;

public abstract class Entity {

	public int x,y;
	public int speed;
	
	public Image up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3, upIdle, downIdle, leftIdle, rightIdle;
	public Direction direction;
	
	public int contImages = 0;
	public int spriteNum = 0;
}
