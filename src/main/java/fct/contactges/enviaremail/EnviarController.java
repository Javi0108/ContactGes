package fct.contactges.enviaremail;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import fct.contactges.App;
import fct.contactges.contacto.ContactosController;
import fct.contactges.model.Contacto;
import fct.contactges.model.EnviarModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EnviarController implements Initializable {

	private EnviarModel model = new EnviarModel();
	private Contacto contacto = new Contacto();

	@FXML
	private BorderPane view;

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
	private Button enviarButton;

	@FXML
	private Button cerrarButton;

	// Conexión
	public static String url = "jdbc:mysql://localhost:3306/gescon";
	public static String usr = "root";
	public static String pswd = "";
	public static Connection con;
	
	public Stage stage;
	
	int codUsuario;
	String email;
	String emailPass;

	public EnviarController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EnviarEmailView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			con = DriverManager.getConnection(url, usr, pswd);
			System.out.println("Connected to Database.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		Bindings.bindBidirectional(model.remitenteProperty(), txtRemitente.textProperty());
		Bindings.bindBidirectional(model.contraseñaProperty(), txtPass.textProperty());
		Bindings.bindBidirectional(model.destinatarioProperty(), txtDestinatario.textProperty());
		Bindings.bindBidirectional(model.asuntoProperty(), txtAsunto.textProperty());
		Bindings.bindBidirectional(model.mensajeProperty(), txtMensaje.textProperty());

		enviarButton.setOnAction(e -> {
			try {
				OnActionEnviar(e);
			} catch (EmailException e1) {
				e1.printStackTrace();
			}
		});
		cerrarButton.setOnAction(e -> OnActionCerrar(e));
	}

	@FXML
	public void OnActionEnviar(ActionEvent e) throws EmailException {
		Email email = new SimpleEmail();

		try {
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setSSLOnConnect(true);
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getContraseña()));
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
	public void OnActionCerrar(ActionEvent e) {
		stage.close();
	}

	public void show(Stage parentStage, Contacto enviado) {
		contacto = enviado;
		
		txtRemitente.setText(getEmail());
		txtPass.setText(getEmailPass());
		txtDestinatario.setText(contacto.getEmail());

		stage = new Stage();
		if (parentStage != null) {
			stage.initOwner(parentStage);
			stage.getIcons().setAll(parentStage.getIcons());
		}
		stage.setTitle("ContactGes - Enviar Email");
		stage.setScene(new Scene(getView()));
		stage.initOwner(ContactosController.stage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	
	private String getEmail() {
		codUsuario = ContactosController.getCodUsuario();

		try {
			PreparedStatement visualiza = con.prepareStatement("SELECT email FROM usuario WHERE codUsuario = ?");
			visualiza.setInt(1, codUsuario);
			ResultSet resultado = visualiza.executeQuery();

			while (resultado.next()) {
				email = resultado.getString(1);
			}
			return email;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getEmailPass() {
		codUsuario = ContactosController.getCodUsuario();

		try {
			PreparedStatement visualiza = con.prepareStatement("SELECT emailPass FROM usuario WHERE codUsuario = ?");
			visualiza.setInt(1, codUsuario);
			ResultSet resultado = visualiza.executeQuery();

			while (resultado.next()) {
				emailPass = resultado.getString(1);
			}
			return emailPass;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BorderPane getView() {
		return view;
	}

	public void setView(BorderPane view) {
		this.view = view;
	}
}
