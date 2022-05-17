package fct.contactges.nuevocontacto;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import fct.contactges.model.Contacto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class NuevoController implements Initializable {
	
	// model
	private Contacto nuevo, devuelto;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nuevo.nombreProperty().bind(view.getNombreText().textProperty());
		nuevo.fechaNacimientoProperty().bind(view.getNacimientoDatePicker().valueProperty());
		Bindings.bindBidirectional(
				view.getTelefonoText().textProperty(), 
				nuevo.telefonoProperty(),
				new NumberStringConverter()
			);
		
		view.getCrearButton().setOnAction(e -> onCrearButtonAction(e));
		view.getCancelarButton().setOnAction(e -> onCancelarButtonAction(e));
	}

	private void onCrearButtonAction(ActionEvent e) {
		devuelto = new Contacto();
		Contacto.copiar(nuevo, devuelto);
		stage.close();
	}

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
		stage.setScene(new Scene(view, 320, 200));
		stage.showAndWait();
		return devuelto;
	}
	
	public NuevoView getView() {
		return view;
	}

}

