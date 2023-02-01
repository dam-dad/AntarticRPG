package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ImageUtils {

	private static List<Image> iceImages;
	private static List<Image> groundImages;
	
	static {
		String iceDfPath = "/assets/images/hielo/";
		String groundDfPath = "/assets/images/";

		iceImages = new ArrayList<>();
		iceImages.addAll(Arrays.asList(new Image(ImageUtils.class.getResourceAsStream(iceDfPath + "hielo0.png")),
				new Image(ImageUtils.class.getResourceAsStream(iceDfPath + "hielo1.png")), 
				new Image(ImageUtils.class.getResourceAsStream(iceDfPath + "hielo2.png")),
				new Image(ImageUtils.class.getResourceAsStream(iceDfPath + "hielo3.png"))));

		groundImages = new ArrayList<>();
		groundImages.addAll(Arrays.asList(new Image(ImageUtils.class.getResourceAsStream(groundDfPath + "floor.png")),
				new Image(ImageUtils.class.getResourceAsStream(groundDfPath + "floor2.png"))));
	}
	
	public static List<Image> getSubImages(int x, int y, int width, int height, int diff, String path) {
		if(Image.class.getResource(path) == null) 
			throw new NullPointerException();
		Image img = new Image(ImageUtils.class.getResourceAsStream(path));
		for(int w = 0; w < width; w+=diff) 
			iceImages.add(new WritableImage(img.getPixelReader(), x, y));
		
		return iceImages;
	}
	
	public static Image getSubImage(int width, int height, String path) {
		if(Image.class.getResource(path) == null) 
			throw new NullPointerException();
		Image img = new Image(ImageUtils.class.getResourceAsStream(path));
		WritableImage wimg = new WritableImage(width, height);
		wimg.getPixelWriter().setPixels(0, 0, width, height, img.getPixelReader(), 0, 0);
		return wimg;
	}
	
	public static Image getSubImageFromImage(int width, int height, Image img) {
		if(img == null) 
			throw new NullPointerException();
		WritableImage wimg = new WritableImage(width, height);
		wimg.getPixelWriter().setPixels(0, 0, width, height, img.getPixelReader(), 0, 0);
		return wimg;
	}
	
	public static void addAll(Collection<? extends Image> images) {
		ImageUtils.iceImages.addAll(images);
	}
	
	public static Image getIceRandomImagen() {
		return iceImages.get(new Random().nextInt(iceImages.size()));
	}
	
	public static Image getGroundRandomImage() {
		return groundImages.get(new Random().nextInt(groundImages.size()));
	}
	
	
	public static List<Image> getImages() {
		return iceImages;
	}
	
	
}
