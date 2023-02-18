package engine.light;

import engine.GameVariables;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import threads.GameLoop;

public class Light {

	private GameLoop loop;
	private WritableImage darkImage;
	private GraphicsContext context;
	private WritableImage lightImage;
	
	private int circleSize;
	
	public Light(GameLoop loop, int circleSize) {
		if(loop == null) 
			throw new NullPointerException("Valor nulo");
		this.loop = loop;
		this.context = loop.getCanvas().getGraphicsContext2D();
		this.circleSize = circleSize;
		
		int centerX = loop.getPlayer().screenX + (GameVariables.TILE_SIZE) / 2;
		int centerY = loop.getPlayer().screenX + (GameVariables.TILE_SIZE) / 2;
		
		darkImage = new WritableImage(GameVariables.SCREEN_WIDTH, GameVariables.SCREEN_HEIGHT);
		
		for (int dx = 0; dx < darkImage.getWidth(); dx++) {
			for (int dy = 0; dy < darkImage.getHeight(); dy++) {
				double distance = Math.sqrt(Math.pow(dx - centerX, 2) + Math.pow(dy - centerY, 2));
				if (distance > circleSize / 2) {
					darkImage.getPixelWriter().setColor(dx, dy, Color.rgb(0, 0, 0, 0.75));
				}
			}
		}
		
	}
	
	public void paint() {
		int centerX = loop.getPlayer().screenX + (GameVariables.TILE_SIZE) / 2;
		int centerY = loop.getPlayer().screenY + (GameVariables.TILE_SIZE) / 2;
		
		double x = centerX - (circleSize / 2);
		double y = centerY - (circleSize / 2);
		
		context.drawImage(darkImage, 0, 0);
		
		Circle lightArea = new Circle(x, y, circleSize);
		RadialGradient gradient = new RadialGradient(0, 0, x, y, circleSize, false, CycleMethod.NO_CYCLE, 
				new Stop(0, Color.rgb(255, 255, 255, 0)), 
				new Stop(1, Color.rgb(255, 255, 255, 1)));
		lightArea.setFill(gradient);
		
		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(lightArea.opacityProperty(), 0.5, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(circleSize / 10), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		context.setFill(lightArea.getFill());
		context.fillOval(x, y, circleSize, circleSize);
	}
	
}
