package threads;

import controllers.GameController;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class StartGameTask extends Task<Scene>{

	@Override
	protected Scene call() throws Exception {
		return new Scene(new GameController().getView());
	}

}
