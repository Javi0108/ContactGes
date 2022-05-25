package fct.contactges.editarcontacto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import fct.contactges.contacto.ContactosController;
import fct.contactges.model.Contacto;
import fct.contactges.nuevocontacto.NuevoController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditarController implements Initializable {

	// model
	private Contacto contacto = new Contacto();

	// Vista
	@FXML
	private BorderPane view;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField telefonoText;

	@FXML
	private TextField emailText;

	@FXML
	private ComboBox<String> sexoCombo;

	@FXML
	private ComboBox<String> direccionCombo;

	@FXML
	private Button crearButton;

	@FXML
	private Button cancelarButton;

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;
	
	public Stage stage;
	
	public EditarController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditarContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DriverManager.getConnection(url, usr, pswd);
			System.out.println("Connected to Database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		nombreText.textProperty().bindBidirectional(contacto.nombreProperty());
		telefonoText.textProperty().bindBidirectional(contacto.telefonoProperty());
		emailText.textProperty().bindBidirectional(contacto.emailProperty());

		sexoCombo.getItems().addAll("H", "M", "X");
		sexoCombo.valueProperty().bindBidirectional(contacto.sexoProperty());

		direccionCombo.getItems().addAll(NuevoController.obtenerCodigosDireccion());
		direccionCombo.valueProperty().bindBidirectional(contacto.direccionProperty());
		
		crearButton.setText("Editar");
		crearButton.setOnAction(e -> onCrearButtonAction(e));
		cancelarButton.setOnAction(e -> onCancelarButtonAction(e));
	}

	private void onCancelarButtonAction(ActionEvent e) {
		try {
			con.close();
			System.exit(0);
//			stage.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void onCrearButtonAction(ActionEvent e) {
//		PreparedStatement habitacion = con.prepareStatement(
//				"SELECT * FROM habitaciones WHERE habitaciones.codHotel = ? AND habitaciones.numHabitacion = ?;");
//		habitacion.setString(1, );
	}
	
	public Contacto show(Stage parentStage) {
		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.setTitle("Nuevo contacto");
		stage.setScene(new Scene(getView(), 320, 200));
		stage.setResizable(false);
		stage.initOwner(ContactosController.stage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.showAndWait();
		return contacto;
	}

	public BorderPane getView() {
		return view;
	}
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
}
