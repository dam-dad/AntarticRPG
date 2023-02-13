package engine.tiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	int objectNum[][];

	private List<int[][]> layers = new ArrayList<>();
	private boolean painted = false;
	
	public TileHandler(GameLoop loop, Player p) {
		if (loop == null || p == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
		tiles = new Tile[GameVariables.MAX_TILES_CONT];
		mapNum = new int[GameVariables.MAX_WORLD_COL][GameVariables.MAX_WORLD_ROW];
		objectNum = new int[GameVariables.MAX_WORLD_COL][GameVariables.MAX_WORLD_ROW];
		this.context = loop.getCanvas().getGraphicsContext2D();
		this.p = p;

		water = new Image(getClass().getResourceAsStream("/assets/textureImages/water.png"));

		intiStaticEntities();

		File mapFile = new File("src/main/resources/maps/map2.tmj");
		loadLayers(mapFile);
	}

	private void intiStaticEntities() {
		for (int i = 0; i < GameVariables.MAX_TILES_CONT; i++) {
			tiles[i] = new Tile();

			switch (i) {
			case 0: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/blueSnow.png"));
				break;
			case 1: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/blueSnow.png"));
				break;
			case 5: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeCenter.png"));
				tiles[i].colision = true;
				break;
			case 6: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopLeft.png"));
				tiles[i].colision = true;
				break;
			case 7: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomLeft.png"));
				tiles[i].colision = true;
				break;
			case 8: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeBottomRight.png"));
				tiles[i].colision = true;
				break;
			case 9: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/LakeTopRight.png"));
				tiles[i].colision = true;
				break;
			case 10: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/grass.png"));
				break;
			case 11: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/dirt.png"));
				break;
			case 12: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeLogMedium.png"));
				break;
			case 13: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeLogRight.png"));
				break;
			case 14: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeBottomRight.png"));
				break;
			case 15: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeMediumRight.png"));
				break;
			case 16: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeTop.png"));
				break;
			case 17: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeMediumLeft.png"));
				break;
			case 18: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeBottomLeft.png"));
				break;
			case 19: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeLogLeft.png"));
				break;
			case 20: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeMedium.png"));
				break;
			case 21: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeBottomMedium.png"));
				break;
			case 22: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/iceReflex.png"));
				break;
			case 23: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/waterHole.png"));
				break;
			case 28: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeShadowLeft.png"));
				break;
			case 29: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeShadowMedium.png"));
				break;
			case 30: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/treeShadowRight.png"));
				break;
			case 149: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/blueSnow.png"));
				tiles[i].colision = true;
				break;
			case 150: 
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/air.png"));
				break;
			}

		}
	}

	public void loadLayers(File mapFile) {
		String linea;
		String nums[] = null;

		int col = 0;
		int row = 0;
		int currentLayer = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(mapFile))) {
			layers.add(mapNum);
			while ((linea = reader.readLine()) != null) {
				if (linea.isBlank()) {
					col = 0;
					row = 0;
					mapNum = new int[GameVariables.MAX_SCREEN_COL][GameVariables.MAX_SCREEN_ROW];
					layers.add(mapNum);
					currentLayer++;
					continue;
				}
				linea = linea.replace(",", "");
				nums = linea.trim().split(" ");
				for (int i = 0; i < nums.length && i < GameVariables.MAX_WORLD_COL; i++) {
					layers.get(currentLayer)[col][row] = Integer.parseInt(nums[i]);
					col++;
				}
				col = 0;
				row++;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void paint() {

		if (!p.isIdle() || !painted) {

			context.drawImage(water, 0, 0, GameVariables.SCREEN_WIDTH, GameVariables.SCREEN_HEIGHT);
			
			for (int worldRow = 0; worldRow < GameVariables.MAX_WORLD_ROW; worldRow++) {
				for (int worldCol = 0; worldCol < GameVariables.MAX_WORLD_COL; worldCol++) {
					int tileNum = layers.get(0)[worldCol][worldRow];

					int worldX = worldCol * GameVariables.TILE_SIZE;
					int worldY = worldRow * GameVariables.TILE_SIZE;

					int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
					int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();
					
					context.drawImage(tiles[tileNum].img, screenX, screenY, GameVariables.TILE_SIZE,
							GameVariables.TILE_SIZE);
					
				}

			}

			for (int worldRow = 0; worldRow < GameVariables.MAX_WORLD_ROW; worldRow++) {
				for (int worldCol = 0; worldCol < GameVariables.MAX_WORLD_COL; worldCol++) {
					int tileNum2 = layers.get(1)[worldCol][worldRow];

					int worldX = worldCol * GameVariables.TILE_SIZE;
					int worldY = worldRow * GameVariables.TILE_SIZE;

					int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
					int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();
					
					context.drawImage(tiles[tileNum2].img, screenX, screenY, GameVariables.TILE_SIZE,
							GameVariables.TILE_SIZE);
					
				}

			}
			p.paint();
			
			for (int worldRow = 0; worldRow < GameVariables.MAX_WORLD_ROW; worldRow++) {
				for (int worldCol = 0; worldCol < GameVariables.MAX_WORLD_COL; worldCol++) {
					int tileNum3 = layers.get(2)[worldCol][worldRow]; //perfe, cualquier petada avísame perfe

					int worldX = worldCol * GameVariables.TILE_SIZE;
					int worldY = worldRow * GameVariables.TILE_SIZE;

					int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
					int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();
					
					context.drawImage(tiles[tileNum3].img, screenX, screenY, GameVariables.TILE_SIZE,
							GameVariables.TILE_SIZE);
					
				}

			}
			
			painted = true;

		}
	}

	public List<int[][]> getLayers() {
		return layers;
	}

	public Tile[] getTiles() {
		return tiles;
	}

}
