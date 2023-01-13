package dad;

import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import engine.Game;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable{

    @FXML private BorderPane view;

    @FXML private Canvas canvas;

    public MainController() {
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
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

        Game game = new Game(canvas);
        game.init();

    }

}
