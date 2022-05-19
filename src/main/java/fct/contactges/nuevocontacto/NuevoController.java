package fct.contactges.nuevocontacto;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fct.contactges.model.Contacto;
import javafx.beans.binding.Bindings;
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
import javafx.util.converter.NumberStringConverter;

public class NuevoController implements Initializable {

	// model
	private Contacto nuevo, devuelto;
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
	private TextField provinciaText;

	@FXML
	private Button crearButton;

	@FXML
	private Button cancelarButton;

	public Stage stage = new Stage();

	public NuevoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nombreText.textProperty().bindBidirectional(contacto.nombreProperty());
		telefonoText.textProperty().bindBidirectional(contacto.telefonoProperty(), new NumberStringConverter());
		emailText.textProperty().bindBidirectional(contacto.emailProperty());

		sexoCombo.getItems().addAll("Hombre", "Mujer", "Otro");
		sexoCombo.valueProperty().bindBidirectional(contacto.sexoProperty());

		crearButton.setOnAction(e -> onCrearButtonAction(e));
		cancelarButton.setOnAction(e -> onCancelarButtonAction(e));
	}

	@FXML
	private void onCrearButtonAction(ActionEvent e) {
		devuelto = new Contacto();
		Contacto.copiar(nuevo, devuelto);
		stage.close();
	}

	@FXML
	private void onCancelarButtonAction(ActionEvent e) {
		devuelto = null;
		stage.close();
	}

	public Contacto show(Stage parentStage) {
		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Nuevo contacto");
		stage.setResizable(false);
		stage.setScene(new Scene(getView(), 320, 200));
		stage.showAndWait();
		return devuelto;
	}

	public BorderPane getView() {
		return view;
	}

	public TextField getNombreText() {
		return nombreText;
	}

	public void setNombreText(TextField nombreText) {
		this.nombreText = nombreText;
	}

	public TextField getTelefonoText() {
		return telefonoText;
	}

	public void setTelefonoText(TextField telefonoText) {
		this.telefonoText = telefonoText;
	}

	public TextField getEmailText() {
		return emailText;
	}

	public void setEmailText(TextField emailText) {
		this.emailText = emailText;
	}

	public ComboBox<String> getSexoCombo() {
		return sexoCombo;
	}

	public void setSexoCombo(ComboBox<String> sexoCombo) {
		this.sexoCombo = sexoCombo;
	}

	public TextField getProvinciaText() {
		return provinciaText;
	}

	public void setProvinciaText(TextField provinciaText) {
		this.provinciaText = provinciaText;
	}

	public Button getCrearButton() {
		return crearButton;
	}

	public void setCrearButton(Button crearButton) {
		this.crearButton = crearButton;
	}

	public Button getCancelarButton() {
		return cancelarButton;
	}

	public void setCancelarButton(Button cancelarButton) {
		this.cancelarButton = cancelarButton;
	}
}
