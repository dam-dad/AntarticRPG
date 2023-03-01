package engine.tiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import engine.GameVariables;
import engine.entity.Npc;
import engine.entity.Player;
import engine.light.Light;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import threads.GameLoop;

public class TileHandler extends Thread {

	private GameLoop loop;
	private Tile[] tiles;
	private GraphicsContext context;
	private Player p;
	private Npc npc;

	private Image water;

	int mapNum[][];

	private List<int[][]> layers = new ArrayList<>();
	private boolean painted = false;

	private Light light;
	private Thread paintThread;

	public TileHandler(GameLoop loop, Player p, Npc npc) {
		if (loop == null || p == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
		tiles = new Tile[GameVariables.MAX_TILES_CONT];
		mapNum = new int[GameVariables.MAX_WORLD_COL][GameVariables.MAX_WORLD_ROW];
		this.context = loop.getCanvas().getGraphicsContext2D();
		this.p = p;
		this.npc = npc;

		water = new Image(getClass().getResourceAsStream("/assets/textureImages/water.png"));

		intiStaticEntities();

		File mapFile = new File("src/main/resources/maps/mapa4.tmj");
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
//			case 31: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof1.png"));
//				break;
//			case 32: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof2.png"));
//				break;
//			case 33: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof3.png"));
//				break;
//			case 34: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof4.png"));
//				break;
//			case 35: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof5.png"));
//				break;
//			case 36: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof6.png"));
//				break;
//			case 37: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof7.png"));
//				break;
//			case 38: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof8.png"));
//				break;
//			case 39: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof9.png"));
//				break;
//			case 40: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof10.png"));
//				break;
//			case 41: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof11.png"));
//				break;
//			case 42: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof12.png"));
//				break;
//			case 43: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof13.png"));
//				break;
//			case 44: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof14.png"));
//				break;
//			case 45: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof15.png"));
//				break;
//			case 46: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof16.png"));
//				break;
//			case 47: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof17.png"));
//				break;
//			case 48: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/roof18.png"));
//				break;
//			case 50: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house0.png"));
//				break;
//			case 51: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house1.png"));
//				break;
//			case 52: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house2.png"));
//				break;
//			case 53: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house3.png"));
//				break;
//			case 54: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house4.png"));
//				break;
//			case 55: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house5.png"));
//				break;
//			case 56: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house6.png"));
//				break;
//			case 57: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house7.png"));
//				break;
//			case 58: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house8.png"));
//				break;
//			case 59: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house9.png"));
//				break;
//			case 60: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house10.png"));
//				break;
//			case 61: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house11.png"));
//				break;
//			case 62: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house12.png"));
//				break;
//			case 63: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house13.png"));
//				break;
//			case 64: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house14.png"));
//				break;
//			case 65: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house15.png"));
//				break;
//			case 66: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house16.png"));
//				break;
//			case 67: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house17.png"));
//				break;
//			case 68: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house18.png"));
//				break;
//			case 69: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house19.png"));
//				break;
//			case 70: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house20.png"));
//				break;
//			case 71: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house21.png"));
//				break;
//			case 72: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house22.png"));
//				break;
//			case 73: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house23.png"));
//				break;
//			case 74: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house24.png"));
//				break;
//			case 75: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house25.png"));
//				break;
//			case 76: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house26.png"));
//				break;
//			case 77: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house27.png"));
//				break;
//			case 78: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house28.png"));
//				break;
//			case 79: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house29.png"));
//				break;
//			case 80: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house30.png"));
//				break;
//			case 81: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house31.png"));
//				break;
//			case 82: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house32.png"));
//				break;
//			case 83: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house33.png"));
//				break;
//			case 84: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house34.png"));
//				break;
//			case 85: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house35.png"));
//				break;
//			case 86: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house36.png"));
//				break;
//			case 87: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house37.png"));
//				break;
//			case 88: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house38.png"));
//				break;
//			case 89: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house39.png"));
//				break;
//			case 90: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house40.png"));
//				break;
//			case 91: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house41.png"));
//				break;
//			case 92: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house42.png"));
//				break;
//			case 93: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house43.png"));
//				break;
//			case 94: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house44.png"));
//				break;
//			case 95: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house45.png"));
//				break;
//			case 96: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house46.png"));
//				break;
//			case 97: 
//				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/mapTextures/house47.png"));
//				break;
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
			npc.paint();
			
			for (int worldRow = 0; worldRow < GameVariables.MAX_WORLD_ROW; worldRow++) {
				for (int worldCol = 0; worldCol < GameVariables.MAX_WORLD_COL; worldCol++) {
					int tileNum3 = layers.get(2)[worldCol][worldRow]; 
					
					int worldX = worldCol * GameVariables.TILE_SIZE;
					int worldY = worldRow * GameVariables.TILE_SIZE;

					int screenX = worldX - loop.player.getWorldX() + loop.player.getScreenX();
					int screenY = worldY - loop.player.getWorldY() + loop.player.getScreenY();
					
					context.drawImage(tiles[tileNum3].img, screenX, screenY, GameVariables.TILE_SIZE,
							GameVariables.TILE_SIZE);
					
				}
			}
			
//			light.paint();
//			
			painted = true;
//
		}
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public List<int[][]> getLayers() {
		return layers;
	}

	public Tile[] getTiles() {
		return tiles;
	}

}
