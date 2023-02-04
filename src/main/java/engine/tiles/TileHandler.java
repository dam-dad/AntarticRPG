package engine.tiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	int mapNum[][];
	
	public TileHandler(GameLoop loop, Player p) {
		if (loop == null || p == null)
			throw new NullPointerException("Valor nulo.");
		this.loop = loop;
		tiles = new Tile[GameVariables.MAX_SCREEN_COL];
		mapNum = new int[GameVariables.MAX_SCREEN_COL][GameVariables.MAX_SCREEN_ROW];
		this.context = loop.getCanvas().getGraphicsContext2D();
		this.p = p;
		loadImages();
		loadMap();
	}

	private void loadImages() {
		for (int i = 0; i < GameVariables.MAX_SCREEN_COL; i++) {
			tiles[i] = new Tile();
			if (i == 0) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/floor.png"));
			} else if (i == 1) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/piedra.png"));
			} else if (i == 2) {
				tiles[i].img = new Image(getClass().getResourceAsStream("/assets/images/piedra.png"));
			} 
		}
	}

	public void loadMap() {
		
		try {
			InputStream is = getClass().getResourceAsStream("/maps/Test.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linea;
			String[] nums = null; 
			
			int col = 0;
			int row = 0;
			
			while(col < GameVariables.MAX_SCREEN_COL && row < GameVariables.MAX_SCREEN_ROW) {
				linea = br.readLine();
				while(col < GameVariables.MAX_SCREEN_COL) {
					nums = linea.split(" ");
					mapNum[col][row] = Integer.parseInt(nums[col]);
					col++;
				}
				if(col == GameVariables.MAX_SCREEN_COL) {
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
			int x = 0;
			int y = 0;
			
			for(int row = 0; row < GameVariables.MAX_SCREEN_ROW; row++) {
				for(int col = 0; col < GameVariables.MAX_SCREEN_COL; col++) {
					int num = mapNum[col][row];
					context.drawImage(tiles[num].img, x, y, GameVariables.TILE_SIZE, GameVariables.TILE_SIZE);
					x += GameVariables.TILE_SIZE;
				}
				x = 0;
				y += GameVariables.TILE_SIZE;
			}
			
		}
		
	}
}
	