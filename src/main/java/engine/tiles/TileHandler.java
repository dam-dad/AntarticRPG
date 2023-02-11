package engine.tiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import engine.GameVariables;
import engine.entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
		
		File mapFile = new File(TileHandler.class.getResource("/maps/Map1.tmj").getFile());
		loadMap(mapFile);
	}

	private void loadImages() {
		for (int i = 0; i < GameVariables.MAX_SCREEN_COL; i++) {
			tiles[i] = new Tile();
			if (i == 25 || i == 26) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/blueSnow.png"));
			} else if (i == 10) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/Snow.png"));
			} else if (i == 15) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/snowedCube.png"));
			} else if (i == 59) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomLeft.png"));
			} else if (i == 60) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomRight.png"));
			} else if (i == 49) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopLeft.png"));
			} else if (i == 50) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopRight.png"));
			} else if (i == 40) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeCenter.png"));
			} 
		}
	}

	public void loadMap(File mapFile) {//String map) {
		String linea;	
		String nums[] = null;
		
		int col = 0;
		int row = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(mapFile))) {
			while(col < GameVariables.MAX_WORLD_COL && row < GameVariables.MAX_WORLD_ROW) {
				linea = reader.readLine();
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
		} catch(IOException ex) {
			ex.printStackTrace();
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
	