package dad;

import controllers.MainMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

	public static Stage primaryStage;
	
    @Override public void start(Stage stage) throws Exception {
    	App.primaryStage = stage;

        MainMenuController mc = new MainMenuController();
        mc.getSnowEmitter().widthProperty().bind(stage.widthProperty());
        mc.getSnowEmitter().heightProperty().bind(stage.heightProperty());
        
        stage.setTitle("ANTARTIC RPG");
        stage.setScene(new Scene(mc.getView()));
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        
    }
    public static void main(String[] args){
        launch(args);
    }
}
