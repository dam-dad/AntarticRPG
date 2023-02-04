package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import engine.GameVariables;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import threads.GameLoop;

/**
 * Controlador de la vista del juego
 */
public class GameController implements Initializable {

	@FXML
	private BorderPane view;

	@FXML
	private Canvas canvas;
	
	private MediaPlayer musicPlayer;
	private MediaPlayer sfxPlayer;
	
	private GameLoop gameLoop;
	
	public GameController() {
		try { 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/GameView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		canvas.setWidth(GameVariables.SCREEN_WIDTH);
		canvas.setHeight(GameVariables.SCREEN_HEIGHT);

		view.setPrefSize(canvas.getWidth(), canvas.getHeight());
		startGame();
	}
	
	public void startGame() {
		gameLoop = new GameLoop(canvas);
		gameLoop.start();
	}
	
	public BorderPane getView() {
		return view;
	}

	public void setSfxPlayer(MediaPlayer sfxPlayer) {
		this.sfxPlayer = sfxPlayer;
	}
	
	public void setMusicPlayer(MediaPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
	}
	
	public void playMusic() {
		musicPlayer.play();
	}

	
}
