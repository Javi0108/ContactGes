package fct.contactges;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;
	MainController mainController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;

		mainController = new MainController();
				
		primaryStage.setTitle("Agenda");
		primaryStage.setScene(new Scene(mainController.getView(), 320, 240));
		primaryStage.show();

	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static boolean confirm(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait().get().equals(ButtonType.OK);
	}

	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
