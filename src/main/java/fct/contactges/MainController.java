package fct.contactges;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fct.contactges.contacto.ContactosController;
import fct.contactges.model.InicioSesion;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainController implements Initializable {

	// Controller
	ContactosController contactoController = new ContactosController();

	// Model
	InicioSesion iniciosesion = new InicioSesion();

	@FXML
	private BorderPane view;

	@FXML
	private TextField usuarioText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private Button cancelButton;

	@FXML
	private Button logInButton;
	
	public Stage stage = new Stage();

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InicioSesionView.fxml"));
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
		    System.out.println("Connected to Database.");
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		usuarioText.textProperty().bindBidirectional(iniciosesion.usuarioProperty());
		passwordText.textProperty().bindBidirectional(iniciosesion.passwordProperty());

		// Listeners
		logInButton.setOnAction(e -> {
			try {
				onlogInButtonAction(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		cancelButton.setOnAction(e -> onCancelButtonAction(e));
	}

	@FXML
	void onCancelButtonAction(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void onlogInButtonAction(ActionEvent event) throws SQLException {
		String user = usuarioText.getText();
		String passw = passwordText.getText();
		PreparedStatement logIn = con
				.prepareStatement("SELECT COUNT(usuario) FROM usuario WHERE usuario = ? AND password = ?");
		logIn.setString(1, user);
		logIn.setString(2, passw);
		ResultSet rs = logIn.executeQuery();
		int codUsuario = 0;
		if (rs.next()) {
			codUsuario = rs.getInt(1);
		}
		if (codUsuario == 1) {
			App.primaryStage.close();
			contactoController.show();
		} else {
			App.error("Inicio de sesión incorrecto", "Compruebe las credenciales ingresadas");
		}
	}
	
	public BorderPane getView() {
		return view;
	}

}