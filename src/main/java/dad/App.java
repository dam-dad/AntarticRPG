package dad;

import controllers.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class App extends Application{

    @Override public void start(Stage stage) throws Exception {

        MainMenuController mc = new MainMenuController();
        stage.setTitle("ANTARTIC RPG");
        stage.setScene(new Scene(mc.getView()));
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        
    }
    public static void main(String[] args){
        launch(args);
    }
}
