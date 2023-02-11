package engine.tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import engine.GameVariables;
import engine.entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import threads.GameLoop;

public class TileHandler {

	private GameLoop loop;
	private Tile[] tiles;
	private GraphicsContext context;
	private Player p;
	
	private Image water;
	
	int mapNum[][];
	
	public TileHandler(GameLoop loop, Player p) {
		if (loop == null || p == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
		tiles = new Tile[GameVariables.MAX_WORLD_COL];
		mapNum = new int[GameVariables.MAX_WORLD_COL][GameVariables.MAX_WORLD_ROW];
		this.context = loop.getCanvas().getGraphicsContext2D();
		this.p = p;
		
		water = new Image(getClass().getResourceAsStream("/assets/textureImages/water.png"));
		
		loadImages();
		loadMap("/maps/Test.txt");
	}

	private void loadImages() {
		for (int i = 0; i < GameVariables.MAX_SCREEN_COL; i++) {
			tiles[i] = new Tile();
			if (i == 0) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/floor.png"));
			} else if (i == 1) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/piedra.png"));
				tiles[i].colision = true;
			} else if (i == 2) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/hielo/hielo0.png"));
			} else if (i == 3) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/hielo/hielo1.png"));
			} else if (i == 4) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/hielo/hielo2.png"));
			} 
		}
	}

	public void loadMap(String map) {
		
		try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linea;
			String[] nums = null; 
			
			int col = 0;
			int row = 0;
			
			while(col < GameVariables.MAX_WORLD_COL && row < GameVariables.MAX_WORLD_ROW) {
				linea = br.readLine();
				while(col < GameVariables.MAX_WORLD_COL) {
					nums = linea.split(" ");
					mapNum[col][row] = Integer.parseInt(nums[col]);
					col++;
				}
				if(col == GameVariables.MAX_WORLD_COL) {
					col = 0;
					row++;
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void paint() {
		
		if(!p.isIdle()) {
			
			context.drawImage(water, 0, 0, GameVariables.SCREEN_WIDTH, GameVariables.SCREEN_HEIGHT);
			
			for (int worldRow = 0; worldRow < GameVariables.MAX_WORLD_ROW; worldRow++) {
				for (int worldCol = 0; worldCol < GameVariables.MAX_WORLD_COL; worldCol++) {
					int tileNum = mapNum[worldCol][worldRow];

					int worldX = worldCol * GameVariables.TILE_SIZE;
					int worldY = worldRow * GameVariables.TILE_SIZE;

					int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
					int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();

					if (screenX >= 0 && screenX + GameVariables.TILE_SIZE <= GameVariables.SCREEN_WIDTH && 
						screenY >= 0 && screenY + GameVariables.TILE_SIZE <= GameVariables.SCREEN_HEIGHT) {
						context.drawImage(tiles[tileNum].img, screenX, screenY, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
					}
				}
			}
			
		}
		
	}
	
	public int[][] getMapNum() {
		return mapNum;
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
	
}
	