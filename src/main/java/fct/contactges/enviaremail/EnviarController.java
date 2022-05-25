package fct.contactges.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import fct.contactges.App;
import fct.contactges.MainController;
import fct.contactges.model.EnviarModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class EnviarController implements Initializable {

	private EnviarModel model = new EnviarModel();

	@FXML
	private BorderPane view;

	@FXML
	private TextField txtSMTP;

	@FXML
	private TextField txtPuerto;

	@FXML
	private CheckBox checkSLL;

	@FXML
	private TextField txtRemitente;

	@FXML
	private PasswordField txtPass;

	@FXML
	private TextField txtDestinatario;

	@FXML
	private TextField txtAsunto;

	@FXML
	private TextArea txtMensaje;

	@FXML
	private Button btEnviar;

	@FXML
	private Button btVaciar;

	@FXML
	private Button btCerrar;

	public static Stage stage;

	public EnviarController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmailView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Bindings.bindBidirectional(txtSMTP.textProperty(), model.smtpProperty());
		Bindings.bindBidirectional(txtPuerto.textProperty(), model.puertoProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(model.sslProperty(), checkSLL.selectedProperty());
		Bindings.bindBidirectional(model.remitenteProperty(), txtRemitente.textProperty());
		Bindings.bindBidirectional(model.contraseñaProperty(), txtPass.textProperty());
		Bindings.bindBidirectional(model.destinatarioProperty(), txtDestinatario.textProperty());
		Bindings.bindBidirectional(model.asuntoProperty(), txtAsunto.textProperty());
		Bindings.bindBidirectional(model.mensajeProperty(), txtMensaje.textProperty());

		txtSMTP.setEditable(false);
		txtSMTP.setText("smtp.gmail.com");
		txtPuerto.setEditable(false);
		txtPuerto.setText("465");
		checkSLL.setSelected(true);
		txtRemitente.setText("gardojavi@gmail.com");
		txtPass.setText("JavierGardi1a13");
		txtDestinatario.setText("gardojavi@gmail.com");
		txtAsunto.setText("Saludo");
		txtMensaje.setText("Hola que tal andas");

		btEnviar.setOnAction(e -> {
			try {
				OnActionEnviar(e);
			} catch (EmailException e1) {
				e1.printStackTrace();
			}
		});
		btVaciar.setOnAction(e -> OnActionCerrar(e));
		btCerrar.setOnAction(e -> OnActionCerrar(e));
	}

	@FXML
	public void OnActionEnviar(ActionEvent e) throws EmailException {
		Email email = new SimpleEmail();

		try {
			email.setHostName(model.getSmtp());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getContraseña()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();
			App.info("Mensaje enviado a '" + model.getDestinatario() + "'.");
			stage.close();
		} catch (Exception ex) {
			App.error("Error", "No se pudo enviar el email: " + ex);
		}
	}

	@FXML
	public void OnActionVaciar(ActionEvent v) {
		model.setSmtp("");
		model.setPuerto(0);
		model.setSsl(false);
		model.setRemitente("");
		model.setContraseña("");
		model.setDestinatario("");
		model.setAsunto("");
		model.setMensaje("");
	}

	@FXML
	public void OnActionCerrar(ActionEvent c) {
		Platform.exit();
	}

	public void show(Stage parentStage) {
		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.setTitle("Agenda");
		stage.setScene(new Scene(getView()));
		stage.initOwner(MainController.stage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	public BorderPane getView() {
		return view;
	}

	public void setView(BorderPane view) {
		this.view = view;
	}
}
