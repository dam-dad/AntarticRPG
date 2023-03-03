package threads;

import controllers.GameController;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class StartGameTask<T> extends Task<Scene>{
	
	private GameController controller;
	
	@Override
	protected Scene call() throws Exception {
		return new Scene(controller.getView());
	}
	
	public void setController(GameController controller) {
		this.controller = controller;
	}
	
}
