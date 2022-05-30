package fct.contactges.editarcontacto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fct.contactges.App;
import fct.contactges.contacto.ContactosController;
import fct.contactges.model.ContactoModel;
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
	private static ContactoModel contacto = new ContactoModel();

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
	private Button modificarButton;

	@FXML
	private Button cancelarButton;

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;

	int codDireccion;
	static String nomMunicipio;

	public String nombre;
	public String telefono;
	public String email;
	public String sexo;
	public String direccion;
	public String codContacto;

	public Stage stage;

	public EditarController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditarContactoView.fxml"));
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		nombreText.textProperty().bindBidirectional(contacto.nombreProperty());
		telefonoText.textProperty().bindBidirectional(contacto.telefonoProperty());
		emailText.textProperty().bindBidirectional(contacto.emailProperty());

		sexoCombo.getItems().addAll("H", "M", "X");
		sexoCombo.valueProperty().bindBidirectional(contacto.sexoProperty());

		direccionCombo.getItems().addAll(obtenerMunicipios());
		direccionCombo.valueProperty().bindBidirectional(contacto.direccionProperty());

		modificarButton.setOnAction(e -> onModificarButtonAction(e));
		cancelarButton.setOnAction(e -> onCancelarButtonAction(e));
	}

	@FXML
	private void onModificarButtonAction(ActionEvent e) {
		nombre = nombreText.getText();
		telefono = telefonoText.getText();
		email = emailText.getText();
		sexo = sexoCombo.getValue();
		direccion = direccionCombo.getSelectionModel().getSelectedItem();
		codContacto = getContacto().getCodContacto();
		Pattern emailPattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		try {
			PreparedStatement inserta = con.prepareStatement(
					"UPDATE contacto set nomContacto = ?, numTelefono = ?, eMail = ?, sexo = ?, codDireccion = ? WHERE codContacto = ?");
			inserta.setString(1, nombre);
			if (telefono.length() == 9) {
				inserta.setString(2, telefono);
			} else {
				App.error("Error en el teléfono", "Inserte bien el número de teléfono");
			}
			Matcher matcher = emailPattern.matcher(email);
			if (matcher.find() == true) {
				inserta.setString(3, email);
			} else {
				App.error("Error en el email", "Inserte bien el email");
			}
			inserta.setString(4, sexo);
			inserta.setInt(5, onGetCodigoAction(e));
			inserta.setInt(6, Integer.parseInt(codContacto));
			inserta.execute();
			App.info("Modificación Realizada");
			stage.close();
			con.close();
		} catch (Exception e2) {
			e2.getStackTrace();
			App.error("Error en la modificación", "No se pudo modificar el contacto: " + e2);
		}
	}
	
	@FXML
	private void onCancelarButtonAction(ActionEvent e) {
		try {
			con.close();
			stage.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public int onGetCodigoAction(ActionEvent event) {
		nomMunicipio = direccionCombo.getSelectionModel().getSelectedItem();

		try {
			PreparedStatement visualiza = con.prepareStatement("SELECT direccion.codDireccion FROM direccion "
					+ "INNER JOIN municipio ON municipio.codMunicipio = direccion.codMunicipio "
					+ "WHERE nomMunicipio = ?");
			visualiza.setString(1, nomMunicipio);
			ResultSet resultado = visualiza.executeQuery();

			while (resultado.next()) {
				codDireccion = resultado.getInt(1);
			}
			return codDireccion;
		} catch (SQLException e) {
			e.printStackTrace();
			return codDireccion;
		}
	}

	public static ArrayList<String> obtenerMunicipios() {
		ArrayList<String> municipios = new ArrayList<String>();
		try {
			PreparedStatement visualiza = con.prepareStatement("SELECT nomMunicipio FROM municipio");
			ResultSet resultado = visualiza.executeQuery();

			while (resultado.next()) {
				nomMunicipio = resultado.getString(1);
				municipios.add(nomMunicipio);
			}
			return municipios;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ContactoModel show(Stage parentStage, ContactoModel enviado) {
		contacto = enviado;
		darValores();
		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.setTitle("ContactGes - Editar contacto");
		stage.setScene(new Scene(getView(), 320, 200));
		stage.setResizable(false);
		stage.initOwner(ContactosController.stage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.showAndWait();
		return contacto;
	}

	private void darValores() {
		nombreText.setText(contacto.getNombre());
		telefonoText.setText(contacto.getTelefono());
		emailText.setText(contacto.getEmail());
		sexoCombo.setValue(contacto.getSexo());
		direccionCombo.setValue(contacto.getDireccion());
	}

	public BorderPane getView() {
		return view;
	}

	public ContactoModel getContacto() {
		return contacto;
	}
}
