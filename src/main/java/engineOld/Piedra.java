package engineOld;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class Piedra extends StaticEntity<Shape> {
	
	public Piedra(double x, double y) {
		super(new Image(Piedra.class.getResourceAsStream("/assets/images/hielo/hielo0.png")), x, y);
	}
}
