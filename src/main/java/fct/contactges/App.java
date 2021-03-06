package fct.contactges;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;
	MainController mainController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;
		mainController = new MainController();
		
		Scene scene = new Scene(mainController.getView(), 500, 230);
		scene.getStylesheets().add("/css/inicioSesionStyle.css");
						
		primaryStage.setTitle("ContactGes");
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("/img/logo.png")));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static boolean confirm(String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("ContactGes - Confirm");
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait().get().equals(ButtonType.OK);
	}

	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("ContactGes - Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void info(String header) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("ContactGes - Info");
		alert.setHeaderText(header);
		alert.initOwner(primaryStage);
		alert.showAndWait();
	}
}
