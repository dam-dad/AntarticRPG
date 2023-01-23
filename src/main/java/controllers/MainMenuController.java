package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.App;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
    private MediaPlayer sfxPlayer = new MediaPlayer(new Media(getClass().getResource("/sounds/whoosh.mp3").toString()));
    
    private OptionsMenuController optionsMenuController = new OptionsMenuController();
    
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
    	musicPlayer.setOnEndOfMedia(() -> musicPlayer.play());
    	musicPlayer.setVolume(musicVolume.multiply(100).divide(1).get());
    	musicPlayer.play();
    	
    	optionsMenuController.setMainMenuController(this);
    	
    }
    
	private void refreshSFXVolume(ObservableValue<? extends Number> o, Number ov, Number nv) {
		sfxPlayer.setVolume(sfxVolume.get() != 0 ? sfxVolume.divide(100).get() : 0);
	}

	private void refreshMasterVolume(ObservableValue<? extends Number> o, Number ov, Number nv) {
		musicPlayer.setVolume(musicVolume.get() != 0 ? musicVolume.divide(100).get() : 0);
	}

	private MediaPlayer getMediaPlayer(String path) {
    	return new MediaPlayer(new Media(getClass().getResource(path).toString()));
    }
    
    @FXML
    private void onMouseEntered(MouseEvent event) {
    	sfxPlayer.stop();
    	sfxPlayer.play();
    }
    
    @FXML
    private void onJugarAction(ActionEvent event) {
    	//code
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
    }
    
    public MediaPlayer getSfxPlayer() {
		return sfxPlayer;
	}
    
    public StackPane getPane() {
		return pane;
	}
    
}
