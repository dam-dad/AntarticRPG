package dad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
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

//        Game game = new Game(canvas);
        
    	canvas.setOnMouseClicked(e -> {
    		System.out.println("Se ha hecho click en el canvas!");
    	});
    	
        canvas.widthProperty().bind(view.widthProperty());
        canvas.heightProperty().bind(view.heightProperty());
    	canvas.setStyle("-fx-background-color: black;");
    	canvas.setStyle("-fx-z-Index: 1000");
    	
//        game.init();

    }

}
