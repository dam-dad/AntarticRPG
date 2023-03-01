package engine;

public class GameVariables {
	
	//Opciones de la pantalla
	
	public static final int ORIGINAL_TILE_SIZE = 12; //Valor inicial del tile 12x12
	public static final int ESCALADO = 3; //Valor del escalado del tile
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * ESCALADO; //valor del tile real (tileOriginal * escalado) 36x36
	
	public static final int MAX_SCREEN_COL = 25;  //el juego consta de 25 columnas y filas
	public static final int MAX_SCREEN_ROW = 25;
	
	public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //800px
	public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; //800px

	public static final int FPS = 60; //FPS deseados
	
	//Intervalo de actualización en nanosegundos (tiempo mínimo que debe transcurrir entre actualizaciones consecutivas)
	public static final int INTERVALO = 1000000000;
	public static final long TARGET_FPS = 60;
	public static final long TARGET_FRAME_TIME = 1_000_000_000 / TARGET_FPS; //60 FPS
	public static final double MAX_ELAPSED_SECONDS = 0.1;
	public static final long MAX_ELAPSED_NANOSECONDS = 1_000_000_000;
	
	//Opciones del jugador
	public static int playerX = SCREEN_WIDTH / 2;
	public static int playerY = SCREEN_HEIGHT / 2;
	public static final int SPEED = 4;
	public static final double ESCALADO_PLAYER = 1.5;
	
	//opciones del mapa
	public static final int MAX_WORLD_COL = 25;
	public static final int MAX_WORLD_ROW = 25;
	public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
	public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
	public static final int MAX_TILES_CONT = 151;
	
	public static final int MAX_HEARTS = 3;
}
