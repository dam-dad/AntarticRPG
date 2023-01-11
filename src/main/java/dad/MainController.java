package dad;

import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{

    @FXML private AnchorPane view;

    @FXML private ImageView mediaView;

    public MainController(){
        try{
            FXMLLoader l = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            l.setController(this);
            l.load();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public AnchorPane getView(){
        return view;
    }

    public void initialize(URL location, ResourceBundle resources){

        mediaView.setImage(new Image(getClass().getResourceAsStream("/assets/osoPolar.gif")));

    }

}
