package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainMenuController implements Initializable{

    @FXML 
    private BorderPane view;

    @FXML 
    private Button jugarButton;
    
    @FXML
    private Button optionsButton;
    
    @FXML 
    private Button salirButton;
    
    private MediaPlayer backgroundPlayer = getMediaPlayer("/sounds/background-theme.mp3");
    
    
    public MainMenuController() {
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
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
    	
    	backgroundPlayer.setOnEndOfMedia(() -> backgroundPlayer.play());
    	backgroundPlayer.setVolume(0.1);
    	backgroundPlayer.play();
    	
    }
    	
    private MediaPlayer getMediaPlayer(String path) {
    	return new MediaPlayer(new Media(getClass().getResource(path).toString()));
    }
    
    @FXML
    private void onMouseEnter(MouseEvent event) {
    	getMediaPlayer("/sounds/whoosh.mp3").play();
    }
    
    @FXML
    private void onJugarAction(ActionEvent event) {
    	//code
    }

    @FXML
    private void onOptionsAction(ActionEvent event) {
    	//code	
    }

    @FXML
    private void onSalirAction(ActionEvent event) {
    	App.primaryStage.close();
    }

}
