package dad;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import engine.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MainController implements Initializable{

    @FXML private BorderPane view;

    @FXML private StackPane stackPane;
    
    @FXML private Canvas menuCanvas;

    @FXML private Canvas optionsCanvas;
    
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
    	
    	menuCanvas.widthProperty().bind(stackPane.prefWidthProperty());
    	menuCanvas.heightProperty().bind(stackPane.prefHeightProperty());

    	stackPane.prefWidthProperty().bind(view.prefWidthProperty());
    	stackPane.prefHeightProperty().bind(view.prefHeightProperty());
    	
//       Game game = new Game(menuCanvas);
        
//    	ocultamos todos los hijos 
    	stackPane.getChildren().stream().forEach(i -> i.setVisible(false));
    
    	menuCanvas.setVisible(true);
    	
    	menuCanvas.setOnMouseClicked(e -> {
    		System.out.println("Se ha hecho click en el canvas!");
    	});
    	
		menuCanvas.getGraphicsContext2D().setStroke(Color.BLACK);
		menuCanvas.getGraphicsContext2D().strokeLine(0, 0, menuCanvas.getWidth(), menuCanvas.getHeight());

//		game.init();
		
    }

}
