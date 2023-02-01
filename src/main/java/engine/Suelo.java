package engine;

import javafx.scene.shape.Shape;

/**
 *  Clase que genera tile de hierba
 */
public class Suelo extends StaticEntity<Shape> {
	
	public Suelo(double x, double y) {
		super(ImageUtils.getGroundRandomImage(), x, y);
	}
	
}
