package engineOld;

import java.util.ArrayList;
import java.util.List;

/**
 *   Clase que contiene los tilemap y contiene el metodo que carga los tiles que se pintan 
 *   del juego segun el numero de cada posicion del tilemap
 */
public class Tile {
	
	public static String map = """
		######   ######
		#             #
		#       O     #
		#  O        O #
		#ooo          #
		#     O  O    #
		#   o      oo #
		#   O       oo# 
		#      O      #
		#  o  o       #
		#    O        #
		#  o          #
		#          ooo#
		#   O         #
		###############""";

	private static double tileLength = 53;
	private static double tileWidth = 53;

	
	/**
	 * Recorremos el tilemap pasado y cargamos un tile según el número que detecta dentro del tilemap
	 * @param Le pasamos por parametro el tilemap y lo recorremos
	 * @return
	 */
	
	public static List<Entity<?>> loadTile(String map) {
		List<Entity<?>> entities = new ArrayList<>();
		String [] lines = map.split("\n");
		double y = 0.0;
		for (String line : lines) {
			double x = 0.0;  
			for (int pos = 0; pos < line.length(); pos++) {
				Entity<?> entity = null;
				switch (line.charAt(pos)) {
				case ' ': entity = new Suelo(x, y); break;
				case 'O': entity = new Piedra(x, y); break;
				case 'o': entity = new Ice(x, y); break;
				case '#': entity = new Tree(x, y); break;
				}
				entities.add(entity);
				x += tileWidth;				
			}
			y += tileLength;			
		}
		return entities;
	}

	public static double getTileLength() {
		return tileLength;
	}

	public static double getTileWidth() {
		return tileWidth;
	}		
}
