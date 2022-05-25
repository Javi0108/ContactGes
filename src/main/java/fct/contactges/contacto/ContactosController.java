package fct.contactges.contacto;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import fct.contactges.App;
import fct.contactges.MainController;
import fct.contactges.editarcontacto.EditarController;
import fct.contactges.enviaremail.EnviarController;
import fct.contactges.model.Agenda;
import fct.contactges.model.Contacto;
import fct.contactges.nuevocontacto.NuevoController;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
	private static Agenda agenda = new Agenda();
	public static Contacto contacto = new Contacto();
	public ObjectProperty<Contacto> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");
	private static ListProperty<Contacto> contactoList = new SimpleListProperty<>(FXCollections.observableArrayList());

	// View
	@FXML
	private BorderPane view;

	@FXML
	private TableView<Contacto> contactosTable;

	@FXML
	private TableColumn<Contacto, String> idColumn;

	@FXML
	private TableColumn<Contacto, String> nombreColumn;

	@FXML
	private TableColumn<Contacto, String> telefonoColumn;

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

	public static Stage stage;

	private static int codUsuario;

	MainController mainController;

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
		agenda.contactosProperty().bind(contactoList);

		editarButton.disableProperty().bind(seleccionado.isNull());
		borrarButton.disableProperty().bind(seleccionado.isNull());
		enviarButton.disableProperty().bind(seleccionado.isNull());

		// Factories
		idColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCodContacto()));
		nombreColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getNombre()));
		emailColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmail()));
		telefonoColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getTelefono()));
		sexoColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getSexo()));
		direccionColumn.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDireccion()));

		// Listeners
		nuevoButton.setOnAction(e -> onNuevoButtonAction(e));
		editarButton.setOnAction(e -> onEditarButtonAction(e));
		borrarButton.setOnAction(e -> onBorrarButtonAction(e));
		enviarButton.setOnAction(e -> onEnviarButtonAction(e));

	}

	public static List<Contacto> llenarTabla() throws SQLException {
		PreparedStatement selectContactos = con.prepareStatement(
				"SELECT contacto.codContacto ,contacto.nomContacto, contacto.numTelefono, contacto.eMail, contacto.sexo, municipio.nomMunicipio FROM contacto "
						+ "INNER JOIN direccion ON direccion.codDireccion = contacto.codDireccion "
						+ "INNER JOIN municipio ON municipio.codMunicipio = direccion.codMunicipio "
						+ "WHERE codUsuario = ?");

		selectContactos.setInt(1, setCodUsuario(getCodUsuario()));
		ResultSet resultado = selectContactos.executeQuery();

		while (resultado.next()) {
			Contacto c = new Contacto();
			c.setCodContacto(String.valueOf(resultado.getInt(1)));
			c.setNombre(resultado.getString(2));
			c.setTelefono(resultado.getString(3));
			c.setEmail(resultado.getString(4));
			c.setSexo(resultado.getString(5));
			c.setDireccion(resultado.getString(6));
			contactoList.add(c);
		}
		return contactoList;
	}

	@FXML
	void onNuevoButtonAction(ActionEvent event) {
		NuevoController controller = new NuevoController();
		controller.show(App.getPrimaryStage());
		if (controller != null) {
			try {
				contactoList.add(setContacto(NuevoController.getContacto()));
				contactoList.clear();
				llenarTabla();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void onBorrarButtonAction(ActionEvent event) {
		try {
			String nombre = seleccionado.get().getNombre();
			int codContacto = Integer.parseInt(seleccionado.get().getCodContacto());

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar contacto");
			alert.setHeaderText("Se dispone a eliminar a " + nombre + ".");
			alert.setContentText("¿Desea eliminar el contacto?");
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> resultado = alert.showAndWait();
			if (ButtonType.YES.equals(resultado.get())) {
				PreparedStatement borrarContacto;
				borrarContacto = con.prepareStatement("DELETE FROM contacto WHERE codContacto = ?");
				borrarContacto.setInt(1, codContacto);
				borrarContacto.execute();
				contactoList.remove(seleccionado.get());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			App.error("ERROR", "Error al eliminar el contacto.");
		}
	}

	@FXML
	void onEnviarButtonAction(ActionEvent event) {
		try {
			EnviarController email = new EnviarController();
			email.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onEditarButtonAction(ActionEvent e) {
		EditarController controller = new EditarController();
		controller.show(App.getPrimaryStage(), seleccionado.get());
		try {
			contactoList.clear();
			llenarTabla();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}

	public void show(Stage parentStage) {
		try {
			stage = new Stage();
			if (parentStage != null) {
				stage.initOwner(parentStage);
				stage.getIcons().setAll(parentStage.getIcons());
			}
			stage.setTitle("Agenda");
			stage.setScene(new Scene(getView(), 680, 480));
			stage.initOwner(App.getPrimaryStage());
			stage.initModality(Modality.APPLICATION_MODAL);
			llenarTabla();
			stage.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Contacto getContacto() {
		return contacto;
	}

	public static Contacto setContacto(Contacto contacto) {
		return ContactosController.contacto = contacto;
	}

	public static int setCodUsuario(int codUsuario) {
		return ContactosController.codUsuario = codUsuario;
	}

	public static int getCodUsuario() {
		return codUsuario;
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

	public TableColumn<Contacto, String> getTelefonoColumn() {
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
