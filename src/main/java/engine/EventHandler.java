package engine;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.shape.Rectangle;
import threads.GameLoop;

public class EventHandler {

	public boolean isMapChange = false;
	GameLoop gl;
	Rectangle eventRect = new Rectangle(23, 23, 2, 2);
	int eventRectDefaultX, eventRectDefaultY;
	boolean check = true;
	Timer timer = new Timer();


	public EventHandler(GameLoop gl) {
		this.gl = gl;

		// eventRect = new Rectangle();
		eventRect.setX(23);
		eventRect.setY(23);
		eventRect.setWidth(2);
		eventRect.setHeight(2);
		eventRectDefaultX = (int) eventRect.getX();
		eventRectDefaultY = (int) eventRect.getY();

	}

	public void checkEventDamage() {
		if (hit(2, 2, Direction.RIGHT)) {
			System.out.println(gl.getPlayer().getHearts());
			damagePit();
		}
	}
	
	public void checkEventChangeMap() {
		if (hit(18, 23, Direction.DOWN)) {
			File nuevoMapa = new File("src/main/resources/maps/mapa1.tmj");
			gl.getTileHandler().setMapFile(nuevoMapa);
			gl.getTileHandler().loadLayers(nuevoMapa);
			isMapChange = true;
		}
		
	}

	public void damagePit() {

		gl.player.damage(0.5);
	}

	public boolean hit(int eventCol, int eventRow, Direction reqDirection) {
		boolean hit = false;
		double defaultX = gl.player.areaSolid.getX();
		double defaultY = gl.player.areaSolid.getY();
		gl.player.areaSolid.setX(gl.player.worldX + gl.player.areaSolid.getX());
		gl.player.areaSolid.setY(gl.player.worldY + gl.player.areaSolid.getY());
		eventRect.setX(eventCol * GameVariables.TILE_SIZE + eventRect.getX());
		eventRect.setY(eventRow * GameVariables.TILE_SIZE + eventRect.getY());

		if (gl.player.areaSolid.getBoundsInParent().intersects(eventRect.getBoundsInParent())) {
			if (gl.player.direction.equalsByDireccion(reqDirection)) {
				hit = true;
			}
		}
		gl.player.areaSolid.setX(defaultX);
		gl.player.areaSolid.setY(defaultY);
		eventRect.setX(eventRectDefaultX);
		eventRect.setY(eventRectDefaultY);

		return hit;
	}

	public boolean isMapChange() {
		return isMapChange;
	}

	public void setMapChange(boolean isMapChange) {
		this.isMapChange = isMapChange;
	}

}
