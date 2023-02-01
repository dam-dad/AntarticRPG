package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;

import config.UserConfig;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
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

public class OptionsMenuController implements Initializable {

	private DoubleProperty musicVolume = new SimpleDoubleProperty();
	private DoubleProperty sfxVolume = new SimpleDoubleProperty();

	private BooleanProperty sfxMuted = new SimpleBooleanProperty();
	private BooleanProperty musicMuted = new SimpleBooleanProperty();
	
	private MainMenuController mainMenuController;
	private UserConfig userConfig = new UserConfig(new File("user.cfg"));
		
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

		//listeners
		sfxMuted.addListener((o, ov, nv) -> {
			if(nv != ov)
				userConfig.getConfig().setProperty("sfxMuted", nv + "");
			if(String.valueOf(nv.booleanValue()).equalsIgnoreCase("true"))
				sfxIcon.setImage(new Image(getClass().getResourceAsStream("/assets/mute-sfx.png")));
			else
				sfxIcon.setImage(new Image(getClass().getResourceAsStream("/assets/sfx.png")));
		});
		
		musicMuted.addListener((o, ov, nv) -> {
			if(nv != ov) 
				userConfig.getConfig().setProperty("musicMuted", nv + "");
			if(String.valueOf(nv.booleanValue()).equalsIgnoreCase("true"))
				musicIcon.setImage(new Image(getClass().getResourceAsStream("/assets/mute-music.png")));
			else
				musicIcon.setImage(new Image(getClass().getResourceAsStream("/assets/music.png")));
		});
		
		sfxVolume.addListener((o, ov, nv) -> {
			if(nv != ov) 
				userConfig.getConfig().setProperty("sfxVolume", String.format("%1.1f", nv.doubleValue() * 100).replace(',', '.'));
		});
		
		musicVolume.addListener((o, ov, nv) -> {
			if(nv != ov) 
				userConfig.getConfig().setProperty("musicVolume", String.format("%1.1f", nv.doubleValue() * 100).replace(',', '.'));
		});

		// bindings
		sfxLabel.textProperty().bind(sfxVolume.multiply(100).asString("%.0f").concat("%"));
		musicLabel.textProperty().bind(musicVolume.multiply(100).asString("%.0f").concat("%"));

		sfxMuted.bind(sfxVolume.isEqualTo(0));
		musicMuted.bind(musicVolume.isEqualTo(0));
		
		sfxVolume.set(Double.parseDouble(userConfig.getConfig().get("sfxVolume").toString()) / 100);
		musicVolume.set(Double.parseDouble(userConfig.getConfig().get("musicVolume").toString()) / 100);

		sfxSlider.valueProperty().set(Double.parseDouble(userConfig.getConfig().get("sfxVolume").toString()));
		musicSlider.valueProperty().set(Double.parseDouble(userConfig.getConfig().get("musicVolume").toString()));
		
		sfxVolume.bind(sfxSlider.valueProperty().divide(100));
		musicVolume.bind(musicSlider.valueProperty().divide(100));
		
	}

	@FXML
	private void onAceptarAction(ActionEvent e) {
		userConfig.saveConfig();
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
    		setMusicVolume(0);
    	} else 
    		musicVolume.bind(musicSlider.valueProperty().divide(100));
    }

    @FXML
    private void onSFXClick(MouseEvent event) {
    	if(!isSfxMuted()) {
    		sfxVolume.unbind();
    		setSfxVolume(0);
    	} else 
    		sfxVolume.bind(sfxSlider.valueProperty().divide(100));
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

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}
	
	public UserConfig getUserConfig() {
		return userConfig;
	}
	
}
