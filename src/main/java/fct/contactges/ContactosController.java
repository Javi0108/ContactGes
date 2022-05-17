package fct.contactges;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import fct.contactges.model.Agenda;
import fct.contactges.model.Contacto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContactosController implements Initializable {
	
	// modelo
	private Agenda agenda = new Agenda();
	private ObjectProperty<Contacto> seleccionado = new SimpleObjectProperty<>(this, "seleccionado");
	
	// vista

    @FXML
    private BorderPane view;

    @FXML
    private TableView<Contacto> contactosTable;

    @FXML
    private TableColumn<Contacto, String> nombreColumn;
    
    @FXML
    private TableColumn<Contacto, LocalDate> fechaColumn;
    
    @FXML
    private TableColumn<Contacto, Number> telefonoColumn;
    
    @FXML
    private TableColumn<Contacto, String> emailColumn;

    @FXML
    private Button nuevoButton;
    
    @FXML
    private Button editarButton;
    
	@FXML
    private Button borrarButton;
	
    @FXML
    private Button enviarButton;
	
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
		// Bindings
		seleccionado.bind(contactosTable.getSelectionModel().selectedItemProperty());
		
		contactosTable.itemsProperty().bind(agenda.contactosProperty());
		
		editarButton.disableProperty().bind(seleccionado.isNull());
		borrarButton.disableProperty().bind(seleccionado.isNull());
		
		// Factories 
		nombreColumn.setCellValueFactory(value -> value.getValue().nombreProperty());
		fechaColumn.setCellValueFactory(value -> value.getValue().fechaNacimientoProperty());
		emailColumn.setCellValueFactory(value -> value.getValue().emailProperty());
		telefonoColumn.setCellValueFactory(value -> value.getValue().telefonoProperty());
		
		// Listeners
		nuevoButton.setOnAction(e -> onNuevoButtonAction(e));
		editarButton.setOnAction(e -> onEditarButtonAction(e));
		borrarButton.setOnAction(e -> onBorrarButtonAction(e));
		enviarButton.setOnAction(e -> onEnviarButtonAction(e));
		
	}
	
	@FXML
    void onNuevoButtonAction(ActionEvent event) {

    }

	@FXML
    void onEditarButtonAction(ActionEvent event) {

    }
	
    @FXML
    void onBorrarButtonAction(ActionEvent event) {

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

//	private void onNuevoButtonAction(ActionEvent e) {
//		NuevoController controller = new NuevoController();
//		Contacto nuevo = controller.show(ContactosApp.getPrimaryStage());
//		if (nuevo != null) {
//			agenda.getContactos().add(nuevo);
//		}
//	}
	
//	private void onEditarButtonAction(ActionEvent e) {
//		EditarController controller = new EditarController();
//		controller.show(ContactosApp.getPrimaryStage(), seleccionado.get());
//	}
	
	public BorderPane getView() {
		return view;
	}
	
	public TableView<Contacto> getContactosTable() {
		return contactosTable;
	}

	public TableColumn<Contacto, String> getNombreColumn() {
		return nombreColumn;
	}

	public TableColumn<Contacto, LocalDate> getFechaColumn() {
		return fechaColumn;
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
