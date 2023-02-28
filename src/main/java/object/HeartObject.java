package object;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class HeartObject extends SuperObject {
	
	public HeartObject() {
		
		name="Heart";
		image = createImage(new Image(getClass().getResourceAsStream("/assets/objects/corazon.png")));
		image2 = createImage(new Image(getClass().getResourceAsStream("/assets/objects/medio_corazon.png")));
		image3 = createImage(new Image(getClass().getResourceAsStream("/assets/objects/corazon_vacio.png")));
		
	}
	
	public WritableImage createImage(Image img) {		
		WritableImage image = new WritableImage(
				(int) img.getWidth(),
				(int) img.getHeight());
		
		PixelWriter pixelWriter = image.getPixelWriter();
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				pixelWriter.setArgb(x, y, img.getPixelReader().getArgb(x, y));
			}
		}
		return image;
	}
	
}
