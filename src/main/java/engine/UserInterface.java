package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import object.HeartObject;
import threads.GameLoop;

public class UserInterface {

	private WritableImage corazon, medio_corazon, corazon_vacio;
	private GameLoop loop;
	private GraphicsContext context;
	
	public UserInterface(GameLoop loop){
		
		this.loop = loop;
		
		HeartObject heart = new HeartObject();
		corazon = heart.image;
		medio_corazon = heart.image2;
		corazon_vacio = heart.image3;
	}
	
	public void paint() {
		
		int x = GameVariables.TILE_SIZE/2;
		int y = GameVariables.TILE_SIZE/2;
		
		
		for(int i = 0; i < loop.getPlayer().getHearts()-0.5; i++) {
			context.drawImage(corazon, x, y, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
			x += GameVariables.TILE_SIZE;
		}
		
		if(loop.getPlayer().getHearts()%1==0.5) {
			context.drawImage(medio_corazon, x, y, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
			x += GameVariables.TILE_SIZE;
		}
		
		for(int i = 0; i < GameVariables.MAX_HEARTS-loop.getPlayer().getHearts()-0.5; i++) {
			context.drawImage(corazon_vacio, x, y, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
			x += GameVariables.TILE_SIZE;
		}

	}
	
	public void setContext(GraphicsContext context) {
		this.context = context;
	}
}
