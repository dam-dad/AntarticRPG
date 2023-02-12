package engine.tiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

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
		
		intiStaticEntities();
		
		File mapFile = Paths.get("src/main/resources/maps/Map0.tmj").toFile();
		loadMap(mapFile);
	}

	private void intiStaticEntities() {
		for (int i = 0; i < GameVariables.MAX_WORLD_COL; i++) {
			tiles[i] = new Tile();
			if(i == 0)
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/blueSnow.png"));
			else if(i == 1)
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/snow.png"));
			else if(i == 5) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeCenter.png"));
				tiles[i].colision = true;
			} else if(i == 6) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopLeft.png"));
				tiles[i].colision = true;
			} else if(i == 7) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomLeft.png"));
				tiles[i].colision = true;
			} else if(i == 8) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomRight.png"));
				tiles[i].colision = true;
			} else if(i == 9) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopRight.png"));
				tiles[i].colision = true;
			}
			else 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/snow.png"));
		}
	}

	public void loadMap(File mapFile) {
		String linea;	
		String nums[] = null;
		
		int col = 0;
		int row = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(mapFile))) {
			while((linea = reader.readLine()) != null) {
				linea = linea.replace(",", "");
				nums = linea.trim().split(" ");
				for(int i = 0; i < nums.length && i < GameVariables.MAX_WORLD_COL; i++) {
					mapNum[col][row] = Integer.parseInt(nums[i]);
					col++;
				}
				col = 0;
				row++;
				
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

//		            System.out.println(tileNum);
//		            System.out.println(worldRow);
//		            System.out.println(worldCol);
		            
		            int worldX = worldCol * GameVariables.TILE_SIZE;
		            int worldY = worldRow * GameVariables.TILE_SIZE;
		            
		            int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
		            int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();
		            
		            context.drawImage(tiles[tileNum].img, screenX, screenY, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
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
	