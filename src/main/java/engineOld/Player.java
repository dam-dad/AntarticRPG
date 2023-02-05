package engineOld;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player extends Entity<Rectangle> {
	
	private static final long ANIMATION_SPEED = 125000; 
	private String path = "/assets/player/";
	
	@SuppressWarnings("serial")
	private final Map<Direction, Animation> idle = new HashMap<>() {{
		put(Direction.NORTH, new Animation("/assets/player/upIdle.gif"));
		put(Direction.SOUTH, new Animation("/assets/player/downIdle.gif")); 
		put(Direction.EAST, new Animation("/assets/player/leftIdle.gif"));
		put(Direction.WEST, new Animation("/assets/player/rightIdle.gif"));
	}};
	
	@SuppressWarnings("serial")
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.NORTH, new Animation(ANIMATION_SPEED, path + "up1Esquimal.png", path + "upEsquimal.png", path + "up2Esquimal.png", path + "upEsquimal.png"));
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED, path + "down1Esquimal.png", path + "downEsquimal.png", path + "down2Esquimal.png", path + "downEsquimal.png"));
		put(Direction.EAST, new Animation(ANIMATION_SPEED, path + "left1Esquimal.png", path + "leftEsquimal.png", path + "left2Esquimal.png", path + "leftEsquimal.png"));
		put(Direction.WEST, new Animation(ANIMATION_SPEED, path + "right1Esquimal.png", path + "rightEsquimal.png", path + "right2Esquimal.png", path + "rightEsquimal.png"));
	}};
	
	public double xSpeed;
	public double ySpeed;
	private boolean isWalking = false;
	private Direction direction;
	private Animation animation;
	private Stack<Action> actions = new Stack<>();

	public Player(double posX, double posY, double speed) {
		super();
		
		// variables of movement speed
		this.xSpeed = speed;
		this.ySpeed = speed;

		// variables of starting position
		this.posX = posX;
		this.posY = posY;

		// default direction when starting
		this.direction = Direction.SOUTH;

		// variables of character size
		this.width = (int) (100 * SCALE);
		this.height = (int) (150 * SCALE);
	}

	public void move(Direction direction) {
		this.actions.add(new Action(direction));
	}
	
	// methods for movement
	private void moveLeft() {
		isWalking = true;
		direction = Direction.EAST;
		posX -= xSpeed;
	}

	private void moveRight() {
		isWalking = true;
		direction = Direction.WEST;
		posX += xSpeed;
	}

	private void moveUp() {
		isWalking = true;
		direction = Direction.NORTH;
		posY -= ySpeed;
	}

	private void moveDown() {
		isWalking = true;
		direction = Direction.SOUTH;
		posY += ySpeed;
	}
	
	private void idle() {
		isWalking = false;
	}
	
	public void render(GraphicsContext gc) {
				
		gc.drawImage(animation.getCurrentFrame(), posX, posY, width, height);
//		Rectangle shape = getCollisionShape();
//		gc.setFill(Color.YELLOW);
//		gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		
	}
	
	private void apply(Action action) {
		switch (action.getDirection()) {
		case SOUTH: moveDown(); break;
		case NORTH: moveUp(); break;
		case EAST: moveLeft(); break;
		case WEST: moveRight(); break;
		}		
	}
	
	public void update(long timeDifference) {
		if (actions.isEmpty()) {
			idle();
		} else {
			isWalking = true;
			while (!actions.isEmpty()) {
				apply(actions.pop());
			}
		}
		if (!isWalking && animation != idle.get(direction)) {
			animation = idle.get(direction);
			animation.reset();
		} 
		if (isWalking && animation != walk.get(direction)) {
			animation = walk.get(direction);
			animation.reset();
		}
		animation.update(timeDifference);
	}

	private Rectangle getCollisionShape(Action action) {
		double shapeX = posX;
		double shapeY = posY + height / 2;
		if (action != null) {
			switch (action.getDirection()) {
			case SOUTH: shapeY += ySpeed; break;
			case NORTH: shapeY -= ySpeed; break;
			case EAST: shapeX -= xSpeed; break;
			case WEST: shapeX += xSpeed; break;
			}
		}
		return new Rectangle(shapeX, shapeY, width, height / 2);
	}
	
	@Override
	public boolean checkCollision(Entity<? extends Shape> entity) {
		
		return super.checkCollision(entity);
	}
	
	@Override
	public Rectangle getCollisionShape() {
		double shapeX = posX;
		double shapeY = posY + height / 2;
		for (Action current : actions) {
			switch (current.getDirection()) {
			case SOUTH: shapeY += ySpeed; break;
			case NORTH: shapeY -= ySpeed; break;
			case EAST: shapeX -= xSpeed; break;
			case WEST: shapeX += xSpeed; break;
			}
		}
		return new Rectangle(shapeX, shapeY, width, height / 2);
	}

	public void stop() {
		this.actions.clear();
	}

}