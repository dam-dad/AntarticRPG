package engine.entity;

import threads.GameLoop;

import java.util.Random;

import engine.Direction;
import engine.GameVariables;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Npc extends Entity {

	private GraphicsContext context;
	private GameLoop loop;
	private int contador = 0;
	private Direction nueva = Direction.DOWN;
	private Player p;
	
	public Npc(GameLoop loop, Player p) {
		super(loop);
		this.loop = loop;
		this.p = p;

		direction = Direction.DOWN;
		speed = 1;
		loadImages();
	}

	public void loadImages() {
		up1 = new Image(getClass().getResourceAsStream("/assets/npc/upOso.png"));
		up2 = new Image(getClass().getResourceAsStream("/assets/npc/upOso2.png"));
		up3 = new Image(getClass().getResourceAsStream("/assets/npc/upOso3.png"));
		down1 = new Image(getClass().getResourceAsStream("/assets/npc/downOso.png"));
		down2 = new Image(getClass().getResourceAsStream("/assets/npc/downOso2.png"));
		down3 = new Image(getClass().getResourceAsStream("/assets/npc/downOso3.png"));
		left1 = new Image(getClass().getResourceAsStream("/assets/npc/leftOso.png"));
		left2 = new Image(getClass().getResourceAsStream("/assets/npc/leftOso2.png"));
		left3 = new Image(getClass().getResourceAsStream("/assets/npc/leftOso3.png"));
		right1 = new Image(getClass().getResourceAsStream("/assets/npc/rightOso.png"));
		right2 = new Image(getClass().getResourceAsStream("/assets/npc/rightOso2.png"));
		right3 = new Image(getClass().getResourceAsStream("/assets/npc/rightOso3.png"));
	}

	public void paint() {
		drawNpc();
	}

	private void drawNpc() {
		int screenX = worldX - loop.getPlayer().getWorldX() + loop.getPlayer().getScreenX();
		int screenY = worldY - loop.getPlayer().getWorldY() + loop.getPlayer().getScreenY();
		
		Image img = null;
		
		switch(nueva) {
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
		context.drawImage(img, screenX, screenY, GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER,
				GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER);

	}

	public void setValoresPorDefecto() {
		worldX = GameVariables.TILE_SIZE * 23;
		worldY = GameVariables.TILE_SIZE * 21;

		speed = GameVariables.SPEED;
		direction = Direction.DOWN;

	}

	public void update() {
		contador++;
		if (contador == 200) {
			checkDamage(p);
			
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = Direction.UP;
				nueva = Direction.UP;
			}
			if (i > 25 && i <= 50) {
				direction = Direction.DOWN;
				nueva = Direction.DOWN;
				
			}
			if (i > 50 && i <= 75) {
				direction = Direction.LEFT;
				nueva = Direction.LEFT;
				
			}
			if (i > 75 && i <= 100) {
				direction = Direction.RIGHT;
				nueva = Direction.RIGHT;
			}
			contador = 0;
		}
		
		if(nueva == Direction.UP && worldY  > 0) {
			loop.getCollisionChecker().checkTile(this);
			if (!colision)
				worldY -= speed;
		}
		if(nueva == Direction.DOWN && worldY < GameVariables.SCREEN_HEIGHT - GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER) {
			loop.getCollisionChecker().checkTile(this);
			if (!colision)
				worldY += speed;
		}
		
		if(nueva == Direction.LEFT && worldX > 0) {
			loop.getCollisionChecker().checkTile(this);
			if (!colision)
				worldX -= speed;
		}
		if(nueva == Direction.RIGHT && worldX < GameVariables.SCREEN_WIDTH - GameVariables.TILE_SIZE * GameVariables.ESCALADO_PLAYER) {
			loop.getCollisionChecker().checkTile(this);
			if (!colision)
				worldX += speed;
		}
		
		contImages++;
		
		if (contImages > 12) {
			if (spriteNum == 0)
				spriteNum = 1;
			else if (spriteNum == 1)
				spriteNum = 2;
			else if (spriteNum == 2)
				spriteNum = 1;
			else if (spriteNum == 1)
				spriteNum = 0;
			contImages = 0;
		}


	}

	public void setContext(GraphicsContext context) {
		this.context = context;
	}

	private void checkDamage(Player p) {
		if (this.getAreaSolid().intersects(p.getAreaSolid().getBoundsInLocal())) {
	        String npcPosition = String.format("(%d, %d)", this.getWorldX(), this.getWorldY());
	        String playerPosition = String.format("(%d, %d)", p.getWorldX(), p.getWorldY());
	        System.out.println("Colisiono en player: " + playerPosition);
	        System.out.println("Colisiono en oso: " + npcPosition);
	    }
//			p.damage(0.5);
	}
	
}
