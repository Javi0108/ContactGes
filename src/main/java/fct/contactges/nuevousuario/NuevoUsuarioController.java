package fct.contactges.nuevousuario;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fct.contactges.App;
import fct.contactges.model.InicioSesionModel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NuevoUsuarioController implements Initializable {

	// Model
	private InicioSesionModel inicioSesion = new InicioSesionModel();

	@FXML
	private BorderPane view;

	@FXML
	private TextField usuarioText;

	@FXML
	private TextField emailText;

//    @FXML
//    private PasswordField passwordText;

//    @FXML
//    private TextField seePasswordText;

	@FXML
	private HBox passwordHBox;

	@FXML
	private Button seePasswordButton;

	@FXML
	private PasswordField confirmPassText;

	@FXML
	private Button singUpButton;

	@FXML
	private Button cancelButton;

	TextField seePasswordText;
	PasswordField passwordText;

	private Stage stage;

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;

	String usuario, email, password;
	Boolean visible;

	public NuevoUsuarioController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoUsuarioView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DriverManager.getConnection(url, usr, pswd);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		passwordText = new PasswordField();
		passwordText.setPrefWidth(250);
		passwordText.setVisible(true);
		passwordText.setDisable(false);
		seePasswordText = new TextField();
		seePasswordText.setPrefWidth(250);
		seePasswordText.setVisible(false);
		seePasswordText.setDisable(true);
		passwordHBox.getChildren().add(passwordText);
		visible = true;

		usuarioText.textProperty().bindBidirectional(inicioSesion.usuarioProperty());
		emailText.textProperty().bindBidirectional(inicioSesion.emailProperty());
		passwordText.textProperty().bindBidirectional(inicioSesion.passwordProperty());
		seePasswordText.setPrefWidth(0);

		seePasswordButton.setOnAction(e -> onSeePasswordButtonAction(e));
		singUpButton.setOnAction(e -> onSingUpButtonAction(e));
		cancelButton.setOnAction(e -> onCancelButtonAction(e));
	}

	@FXML
	void onCancelButtonAction(ActionEvent event) {
		stage.close();
	}

	@FXML
	void onSeePasswordButtonAction(ActionEvent event) {
		String password;
		if(visible) {
			password = passwordText.getText();
			seePasswordText.setText(password);
			passwordHBox.getChildren().clear();
			passwordHBox.getChildren().add(seePasswordText);
			seePasswordText.setPrefWidth(250);
			seePasswordText.setVisible(true);
			seePasswordText.setDisable(false);
			visible = false;
		} else if(!visible) {
    		password = seePasswordText.getText();
    		passwordText.setText(password);
    		passwordHBox.getChildren().clear();
    		passwordHBox.getChildren().add(passwordText);
    		visible = true;
		}
	}

	@FXML
	void onSingUpButtonAction(ActionEvent event) {
		usuario = usuarioText.getText();
		email = emailText.getText();
		password = passwordText.getText();
		Pattern emailPattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		try {
			PreparedStatement inserta = con
					.prepareStatement("INSERT INTO usuario(usuario, password, email) VALUES (?, ?, ?)");
			inserta.setString(1, usuario);
			inserta.setString(2, password);

			Matcher matcher = emailPattern.matcher(email);
			if (matcher.find() == true) {
				inserta.setString(3, email);
			} else {
				App.error("Error en el email", "Inserte bien el email");
			}

			inserta.execute();
			App.info("Inserción Realizada");
			inicioSesion = new InicioSesionModel(usuario, email, password);
			stage.close();
			con.close();
		} catch (Exception e2) {
			e2.getStackTrace();
			App.error("Error al crear el usuario", "" + e2);
		}
	}

	public void show(Stage parentStage) {
		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.setTitle("ContactGes - Nuevo usuario");
		stage.setScene(new Scene(getView(), 600, 230));
		stage.setResizable(false);
		stage.initOwner(App.getPrimaryStage());
		stage.initModality(Modality.WINDOW_MODAL);
		stage.showAndWait();
	}

	public BorderPane getView() {
		return view;
	}

}
