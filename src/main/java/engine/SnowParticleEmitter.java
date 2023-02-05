package engine;

import java.util.ArrayList;
import java.util.Random;

import controllers.GameController;
import controllers.MainMenuController;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/*
 * Autor: Dónovan Castro Fariña
 */

public class SnowParticleEmitter {
	
	private IntegerProperty width = new SimpleIntegerProperty();
	private IntegerProperty height = new SimpleIntegerProperty();
	
	private ArrayList<Circle> circles = new ArrayList<>();
	private ArrayList<Circle> inactiveCircles = new ArrayList<>();
	
	private MainMenuController mainMenuController;
	private GameController gameController;
	private Random r;
	
	private Thread mainThread;
	private Thread gameThread;
	
	private int particles;
	
	public SnowParticleEmitter() {
		r = new Random();
		particles = 1000;
	}
	
	public void initMain() {
		for(int i = 0; i < particles; i++) {
			inactiveCircles.add(crearCirculo());
		}
		
		mainThread = new Thread(() -> {
			while(true) {
				if(circles.size() < particles) {
					Circle c = inactiveCircles.remove(0);
					Platform.runLater(() -> {
						mainMenuController.getView().getChildren().add(c);
						animar(c);
					});
					circles.add(c);
				}
				try {
					Thread.sleep(5);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		mainThread.start();
		
	}
	
	public void initGame() {
		for(int i = 0; i < particles; i++) {
			inactiveCircles.add(crearCirculo());
		}
		
		gameThread = new Thread(() -> {
			while(true) {
				if(circles.size() < particles) {
					Circle c = inactiveCircles.remove(0);
					Platform.runLater(() -> {
						gameController.getView().getChildren().add(c);
						animar(c);
					});
					circles.add(c);
				}
				try {
					Thread.sleep(5);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		gameThread.start();
	}

	public void stopMainThread() {
		mainThread.interrupt();
	}
	
	public void stopGameThread() {
		gameThread.interrupt();
	}
	
	private Circle crearCirculo() {
		Circle c = new Circle(1,1,1);
		c.setRadius(r.nextDouble() * 3);
		c.setFill(Color.rgb(255, 255, 255, r.nextDouble()));
		return c;
	}
	
	private void animar(Circle c) {
		c.setCenterX(r.nextInt(width.get()));
		c.setCenterY(-c.getRadius());
		TranslateTransition anim = new TranslateTransition();
		anim.setNode(c);
		anim.setDuration(Duration.millis(5000));
		anim.setCycleCount(Timeline.INDEFINITE);
		anim.setFromX(r.nextInt(getWidth()) - 10);
		anim.setFromY(0);
		anim.setByX(r.nextInt(getWidth()) - 350);
		anim.setByY(r.nextInt((int) (getHeight() + 2 * c.getRadius())));
		anim.setOnFinished(e -> {
			circles.remove(c);
			inactiveCircles.add(c);
		}); 
		anim.play();
	}

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void setParticles(int particles) {
		this.particles = particles;
	}
	
	public void setMainMenuController(MainMenuController mainMenuController) {
		this.mainMenuController = mainMenuController;
	}
	
	public final IntegerProperty widthProperty() {
		return this.width;
	}
	
	public final int getWidth() {
		return this.widthProperty().get();
	}

	public final void setWidth(final int width) {
		this.widthProperty().set(width);
	}
	
	public final IntegerProperty heightProperty() {
		return this.height;
	}
	
	public final int getHeight() {
		return this.heightProperty().get();
	}
	
	public final void setHeight(final int height) {
		this.heightProperty().set(height);
	}
	
}
