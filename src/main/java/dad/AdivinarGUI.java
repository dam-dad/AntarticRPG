package dad;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdivinarGUI extends Application {

	private Label enunciadoLabel;
	private TextField input;
	private Button comprobarButton;
	
	private VBox rootPanel;
	private Scene escena;
	
	private Random r;
	private int res;
	private int intentos;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		r = new Random();
		res = r.nextInt(100-1) + 1;
		intentos = 0;
			
		enunciadoLabel = new Label("Introduce un número del 1 al 100.");
		
		input = new TextField();
		input.setPromptText("Introduce un número...");
		
		
		comprobarButton = new Button("Comprobar");
		comprobarButton.setOnAction(e -> comprobarNumero(e));
		
		rootPanel = new VBox();
		rootPanel.setSpacing(10);
		rootPanel.setAlignment(Pos.CENTER);
		rootPanel.setFillWidth(false);
		rootPanel.getChildren().addAll(enunciadoLabel, input, comprobarButton);
		escena = new Scene(rootPanel, 320, 200);
	
		primaryStage.setTitle("AdivinApp");
		primaryStage.setScene(escena);
		primaryStage.show();
	}

	private void comprobarNumero(ActionEvent e) {
		int num;
		Alert a = null;
		intentos++;
		if(input.getText().length() > 0)
			num = Integer.parseInt(input.getText());
		else {
			a = new Alert(AlertType.ERROR);
			a.setTitle("AdivinApp");
			a.setHeaderText("ERROR");
			a.setContentText("El número introducido no es válido.");
			num = 0;
		}
		System.out.println("A");
		if(num > res) {
			a = new Alert(AlertType.WARNING);
			a.setTitle("AdivinApp");
			a.setHeaderText("¡Has fallado!");
			a.setContentText("¡El número a adivinar es menor que " + num + "!\n"
					+ "       ¡Vuelve a intentarlo!");
		} else if(num < res) {
			a = new Alert(AlertType.WARNING);
			a.setTitle("AdivinApp");
			a.setHeaderText("¡Has fallado!");
			a.setContentText("¡El número a adivinar es mayor que " + num + "!\n"
					+ "       ¡Vuelve a intentarlo!");
		} else {
			a = new Alert(AlertType.INFORMATION);
			a.setTitle("AdivinApp");
			a.setHeaderText("¡Has Ganado!");
			a.setContentText("¡Has necesitado " + intentos + " intentos! \n"
					+ "       ¡Vuelve a jugar e intenta batir tu record!");
		}
		a.show();
			
	}	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
