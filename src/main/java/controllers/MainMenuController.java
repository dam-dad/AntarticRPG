package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import config.UserConfig;
import dad.App;
import engine.SnowParticleEmitter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import threads.StartGameTask;

public class MainMenuController implements Initializable{

	private DoubleProperty musicVolume = new SimpleDoubleProperty();
	private DoubleProperty sfxVolume = new SimpleDoubleProperty();
	
    @FXML 
    private BorderPane view;

    @FXML
    private ImageView titleView;
    
    @FXML
    private StackPane pane;
    
    @FXML 
    private Button jugarButton;
    
    @FXML
    private Button optionsButton;
    
    @FXML 
    private Button salirButton;
    
    private MediaPlayer musicPlayer = getMediaPlayer("/sounds/background-theme.mp3");
    private MediaPlayer sfxPlayer = getMediaPlayer("/sounds/whoosh.mp3");
    
    private OptionsMenuController optionsMenuController = new OptionsMenuController();
    private SnowParticleEmitter snowEmitter = new SnowParticleEmitter();
    private UserConfig userConfig = optionsMenuController.getUserConfig();
    
    public MainMenuController() {
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/mainMenu/MainMenuView.fxml"));
            l.setController(this);
            l.load();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BorderPane getView(){
        return view;
    }

    public void initialize(URL location, ResourceBundle resources){
    	
    	//listeners
    	musicVolume.addListener(this::refreshMasterVolume);
    	sfxVolume.addListener(this::refreshSFXVolume);
    	
    	sfxVolume.bind(optionsMenuController.sfxVolumeProperty());
    	musicVolume.bind(optionsMenuController.musicVolumeProperty());
    	
    	//actions
    	
    	musicPlayer.setVolume(musicVolume.get());
    	sfxPlayer.setVolume(sfxVolume.get());
    	
    	musicPlayer.setOnEndOfMedia(() -> musicPlayer.play());
    	musicPlayer.play();
    	
    	optionsMenuController.setMainMenuController(this);
    	snowEmitter.setMainMenuController(this);
		snowEmitter.crearStage();
    	
    }
    
	private void refreshSFXVolume(ObservableValue<? extends Number> o, Number ov, Number nv) {
		sfxPlayer.setVolume(sfxVolume.get());
	}

	private void refreshMasterVolume(ObservableValue<? extends Number> o, Number ov, Number nv) {
		musicPlayer.setVolume(musicVolume.get());
	}

	public MediaPlayer getMediaPlayer(String path) {
    	return new MediaPlayer(new Media(getClass().getResource(path).toString()));
    }
    
    @FXML
    private void onMouseEntered(MouseEvent event) {
    	sfxPlayer.stop();
    	sfxPlayer.play();
    }
    
    @FXML
    private void onJugarAction(ActionEvent event) {
    	StackPane sp = new StackPane();
    	Task<Scene> cargarEscena = new StartGameTask();
    	
    	VBox vbox = new VBox();
    	Label l = new Label("Cargando...");
    	Label l2 = new Label();
    	
    	l.setStyle("""
    			-fx-text-fill: white;
    			-fx-font-size: 24;
    			""");
    	
    	l2.setStyle("""
    			-fx-text-fill: white;
    			-fx-font-size: 24;
    			""");

    	DoubleBinding abs = Bindings.createDoubleBinding(() -> Math.abs(cargarEscena.progressProperty().get() * 100), cargarEscena.progressProperty());
    	l2.textProperty().bind(abs.asString("%1.0f").concat("%"));
    	
    	sp.setStyle("-fx-background-color: black;");
    	vbox.getChildren().addAll(l,l2);
    	vbox.setSpacing(5);
    	
    	vbox.setAlignment(Pos.CENTER);
    	sp.getChildren().add(vbox);
    	
    	App.primaryStage.setScene(new Scene(sp, getView().getWidth(), getView().getHeight()));
    	
    	GameController gc = new GameController();
    	gc.setMusicPlayer(musicPlayer);
    	gc.setSfxPlayer(sfxPlayer);
    	
    	
    	cargarEscena.setOnSucceeded(e -> {
    		App.primaryStage.setScene(new Scene(gc.getView()));
    		gc.playMusic();
    	});    	
    	
    	new Thread(cargarEscena).start();

    }

    @FXML
    private void onOptionsAction(ActionEvent event) {
    	pane.getChildren().add(optionsMenuController.getView());
    	
    	StackPane.setMargin(optionsMenuController.getView(), new Insets(-150, 0,0,0));
    	optionsMenuController.getView().setPrefSize(600, BorderPane.USE_COMPUTED_SIZE);
    	optionsMenuController.getView().toFront();
    }

    @FXML
    private void onSalirAction(ActionEvent event) {
    	App.primaryStage.close();
    	System.exit(0);
    }
    
    public MediaPlayer getSfxPlayer() {
		return sfxPlayer;
	}
    
    public StackPane getPane() {
		return pane;
	}
    
    public SnowParticleEmitter getSnowEmitter() {
		return snowEmitter;
	}
    
}
