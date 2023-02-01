package engine;

/**
 *  Clase que genera tile de ice
 */
public class Ice extends CollidableEntity {
	
	public Ice(double x, double y) {
		super(ImageUtils.getIceRandomImagen(), x, y);
	}

}
