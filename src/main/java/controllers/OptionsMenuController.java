package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;

public class OptionsMenuController implements Initializable {

	private DoubleProperty musicVolume = new SimpleDoubleProperty();
	private DoubleProperty sfxVolume = new SimpleDoubleProperty();
	
	private BooleanProperty sfxMuted = new SimpleBooleanProperty();
	private BooleanProperty musicMuted = new SimpleBooleanProperty();
	
	private MainMenuController mainMenuController;

	@FXML
	private ImageView sfxIcon;
	
	@FXML
	private ImageView musicIcon;
	
	@FXML
	private JFXSlider musicSlider;

	@FXML
	private JFXSlider sfxSlider;

	@FXML
	private Label musicLabel;

	@FXML
	private Label sfxLabel;

	@FXML
	private BorderPane view;

	public OptionsMenuController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu/OptionsMenuView.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		sfxVolume.bind(sfxSlider.valueProperty());
		musicVolume.bind(musicSlider.valueProperty());
		
		sfxLabel.textProperty().bind(sfxVolume.asString("%1.0f").concat("%"));
		musicLabel.textProperty().bind(musicVolume.asString("%1.0f").concat("%"));

	}

	@FXML
	private void onAceptarAction(ActionEvent e) {
		mainMenuController.getPane().getChildren().remove(view);
	}

	@FXML
	private void onMouseEntered(MouseEvent e) {
		mainMenuController.getSfxPlayer().stop();
		mainMenuController.getSfxPlayer().play();
	}

    @FXML
    private void onMusicClick(MouseEvent event) {
    	if(!isMusicMuted()) {
    		musicVolume.unbind();
    		musicIcon.setImage(new Image(getClass().getResourceAsStream("/assets/mute-music.png")));
    		setMusicVolume(0);
    		setMusicMuted(true);
    	} else {
    		musicVolume.bind(musicSlider.valueProperty());
    		musicIcon.setImage(new Image(getClass().getResourceAsStream("/assets/music.png")));
    		musicMuted.set(false);
    	}
    }

    @FXML
    private void onSFXClick(MouseEvent event) {
    	if(!isSfxMuted()) {
    		sfxVolume.unbind();
    		sfxIcon.setImage(new Image(getClass().getResourceAsStream("/assets/mute-sfx.png")));
    		setSfxVolume(0);
    		setSfxMuted(true);
    	} else {
    		sfxVolume.bind(sfxSlider.valueProperty());
    		sfxIcon.setImage(new Image(getClass().getResourceAsStream("/assets/sfx.png")));
    		sfxMuted.set(false);
    	}
    }
    
	public BorderPane getView() {
		return view;
	}

	public final DoubleProperty musicVolumeProperty() {
		return this.musicVolume;
	}

	public final double getMusicVolume() {
		return this.musicVolumeProperty().get();
	}

	public final void setMusicVolume(final double musicVolume) {
		this.musicVolumeProperty().set(musicVolume);
	}

	public final DoubleProperty sfxVolumeProperty() {
		return this.sfxVolume;
	}

	public final double getSfxVolume() {
		return this.sfxVolumeProperty().get();
	}

	public final void setSfxVolume(final double sfxVolume) {
		this.sfxVolumeProperty().set(sfxVolume);
	}

	public MainMenuController getMainMenuController() {
		return mainMenuController;
	}

	public void setMainMenuController(MainMenuController mainMenuController) {
		this.mainMenuController = mainMenuController;
	}

	public final BooleanProperty sfxMutedProperty() {
		return this.sfxMuted;
	}

	public final boolean isSfxMuted() {
		return this.sfxMutedProperty().get();
	}

	public final void setSfxMuted(final boolean sfxMuted) {
		this.sfxMutedProperty().set(sfxMuted);
	}

	public final BooleanProperty musicMutedProperty() {
		return this.musicMuted;
	}

	public final boolean isMusicMuted() {
		return this.musicMutedProperty().get();
	}

	public final void setMusicMuted(final boolean musicMuted) {
		this.musicMutedProperty().set(musicMuted);
	}

}
