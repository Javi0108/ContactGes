package fct.contactges.contacto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import fct.contactges.App;
import fct.contactges.model.Agenda;
import fct.contactges.model.Contacto;
import fct.contactges.nuevocontacto.NuevoController;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class ContactosController implements Initializable {

	// Model
	private Agenda agenda = new Agenda();
	private ObjectProperty<Contacto> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");
//	private ObservableList<Contacto> contactosList = FXCollections.observableArrayList();
	private static ListProperty<Contacto> contactosList = new SimpleListProperty<>(FXCollections.observableArrayList());

	// View

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Contacto> contactosTable;

	@FXML
	private TableColumn<Contacto, String> nombreColumn;

	@FXML
	private TableColumn<Contacto, Number> telefonoColumn;

	@FXML
	private TableColumn<Contacto, String> emailColumn;
	
    @FXML
    private TableColumn<Contacto, String> sexoColumn;

	@FXML
	private TableColumn<Contacto, String> direccionColumn;

	@FXML
	private Button nuevoButton;

	@FXML
	private Button editarButton;

	@FXML
	private Button borrarButton;

	@FXML
	private Button enviarButton;

	private Stage stage;

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;

	public ContactosController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactosView.fxml"));
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
		
		// Bindings
		seleccionado.bind(contactosTable.getSelectionModel().selectedItemProperty());

		contactosTable.itemsProperty().bind(agenda.contactosProperty());

		editarButton.disableProperty().bind(seleccionado.isNull());
		borrarButton.disableProperty().bind(seleccionado.isNull());
		enviarButton.disableProperty().bind(seleccionado.isNull());

		// Factories
		nombreColumn.setCellValueFactory(value -> value.getValue().nombreProperty());
		telefonoColumn.setCellValueFactory(value -> value.getValue().telefonoProperty());
		emailColumn.setCellValueFactory(value -> value.getValue().emailProperty());
		sexoColumn.setCellValueFactory(value -> value.getValue().sexoProperty());
		direccionColumn.setCellValueFactory(value -> value.getValue().direccionProperty());
		
		try {
			llenarTabla();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Listeners
		nuevoButton.setOnAction(e -> onNuevoButtonAction(e));
		editarButton.setOnAction(e -> onEditarButtonAction(e));
		borrarButton.setOnAction(e -> onBorrarButtonAction(e));
		enviarButton.setOnAction(e -> onEnviarButtonAction(e));

		// Vista
		stage = new Stage();
		stage.setTitle("Agenda");
		stage.setScene(new Scene(getView()));
		stage.initOwner(App.primaryStage);
		stage.initModality(Modality.APPLICATION_MODAL);
	}

	public static void llenarTabla() throws SQLException {
		PreparedStatement visualiza = con.prepareStatement(
				"SELECT contacto.nomContacto, contacto.numTelefono, contacto.eMail, contacto.sexo, direccion.calle FROM contacto INNER JOIN direccion ON direccion.codDireccion = contacto.codDireccion");
		ResultSet resultado = visualiza.executeQuery();

		Agenda a = new Agenda();
		while (resultado.next()) {
			Contacto c = new Contacto(resultado.getString(1), resultado.getLong(2), resultado.getString(3), resultado.getString(4), resultado.getString(5));
			System.out.println(c.getNombre());
			contactosList.add(c);
			a.getContactos().add(c);
		}
	}

	@FXML
	void onNuevoButtonAction(ActionEvent event) {
		NuevoController controller = new NuevoController();
		Contacto nuevo = controller.show(stage);
		if (nuevo != null) {
			agenda.getContactos().add(nuevo);
		}
	}

	@FXML
	void onEditarButtonAction(ActionEvent event) {

	}

	@FXML
	void onBorrarButtonAction(ActionEvent event) {
		String nombre = seleccionado.get().getNombre();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar contacto");
		alert.setHeaderText("Se dispone a eliminar al contacto '" + nombre + "'.");
		alert.setContentText("�Desea eliminar el contacto?");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> resultado = alert.showAndWait();
		if (ButtonType.YES.equals(resultado.get())) {
			//TODO Hacer consulta
			agenda.getContactos().remove(seleccionado.get());
		}
	}

	@FXML
	void onEnviarButtonAction(ActionEvent event) {

	}

//	private void onEliminarButtonAction(ActionEvent e) {
//		String nombre = seleccionado.get().getNombre();
//		
//		Alert alert = new Alert(AlertType.CONFIRMATION);
//		alert.setTitle("Eliminar contacto");
//		alert.setHeaderText("Se dispone a eliminar al contacto '" + nombre + "'.");
//		alert.setContentText("�Desea eliminar el contacto?");
//		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
//		Optional<ButtonType> resultado = alert.showAndWait();
//		if (ButtonType.YES.equals(resultado.get())) {
//			agenda.getContactos().remove(seleccionado.get());
//		}
//	}

//	private void onEditarButtonAction(ActionEvent e) {
//		EditarController controller = new EditarController();
//		controller.show(ContactosApp.getPrimaryStage(), seleccionado.get());
//	}

	public BorderPane getView() {
		return view;
	}

	public void show() {
		stage.showAndWait();
	}
	
	public TableView<Contacto> getContactosTable() {
		return contactosTable;
	}

	public TableColumn<Contacto, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Contacto, String> getSexoColumn() {
		return sexoColumn;
	}

	public TableColumn<Contacto, Number> getTelefonoColumn() {
		return telefonoColumn;
	}

	public Button getNuevoButton() {
		return nuevoButton;
	}

	public Button getEditarButton() {
		return editarButton;
	}

	public Button getBorrarButton() {
		return borrarButton;
	}

}
