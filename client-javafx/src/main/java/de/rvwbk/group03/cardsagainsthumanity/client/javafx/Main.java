package de.rvwbk.group03.cardsagainsthumanity.client.javafx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.client.GameManager;
import de.rvwbk.group03.cardsagainsthumanity.client.ViewManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting point for client javaFx
 *
 */
public class Main extends Application {
	final static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}


	private Stage mainStage;
	private ViewManager viewManager;
	private GameManager gameManager;

	
	private Main() {

	}

	@Override
	public void init() throws Exception {
		super.init();

		viewManager = new ViewManagerImpl();
		gameManager = new GameManagerImpl();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;

		Parent root = viewManager.getLoginView();

		Scene scene = new Scene(root, 500, 600);
		mainStage.setTitle("JavaFx - Cardsagainsthumaity Client");
		mainStage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

	public Stage getMainStage() {
		return mainStage;
	}
}
