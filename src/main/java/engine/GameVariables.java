package engine;

public class GameVariables {
	
	//Opciones de la pantalla
	
	public static final int ORIGINAL_TILE_SIZE = 8; //Valor inicial del tile 8x8
	public static final int ESCALADO = 4; //Valor del escalado del tile
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * ESCALADO; //valor del tile real (tileOriginal * escalado) 42x42
	
	public static final int MAX_SCREEN_COL = 25;  //el juego consta de 25 columnas y filas
	public static final int MAX_SCREEN_ROW = 25;
	
	public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //800px
	public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; //800px

	public static final int FPS = 60; //FPS deseados
	
	//Intervalo de actualización en nanosegundos (tiempo mínimo que debe transcurrir entre actualizaciones consecutivas)
	public static final int INTERVALO = 1000000000/FPS;
	
	//Opciones del jugador
	public static int playerX = 100;
	public static int playerY = 100;
	public static final int SPEED = 3;
	
}
