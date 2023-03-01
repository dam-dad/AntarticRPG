package engine;


import javafx.scene.shape.Rectangle;
import threads.GameLoop;

public class EventHandler {
	
	GameLoop gl;
	Rectangle eventRect = new Rectangle(23, 23, 2, 2);
	int eventRectDefaultX, eventRectDefaultY;
	
	
	public EventHandler(GameLoop gl) {
		this.gl = gl;
		
		//eventRect = new Rectangle();
		eventRect.setX(23);
		eventRect.setY(23);
		eventRect.setWidth(2);
		eventRect.setHeight(2);
		eventRectDefaultX = (int) eventRect.getX();
		eventRectDefaultY = (int) eventRect.getY();
	}
	
	public void checkEvent() {
		
	}
	
	public boolean hit(int eventCol, int eventRow, String reqDirection) {
		boolean hit = false;
		
		gl.player.areaSolid.setX(gl.player.worldX + gl.player.areaSolid.getX());
		gl.player.areaSolid.setY(gl.player.worldY + gl.player.areaSolid.getY());
		eventRect.setX(eventCol*GameVariables.TILE_SIZE + eventRect.getX());
		eventRect.setY(eventRow*GameVariables.TILE_SIZE + eventRect.getY());
		
		if(gl.player.areaSolid.getBoundsInParent().intersects(eventRect.getBoundsInParent())) {
			if(gl.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
				hit = true;
			}
		}
		
		gl.player.areaSolid.setX(GameVariables.TILE_SIZE);
		gl.player.areaSolid.setY(GameVariables.TILE_SIZE);
		eventRect.setX(eventRectDefaultX);
		eventRect.setY(eventRectDefaultY);

		return hit;
	}

}
