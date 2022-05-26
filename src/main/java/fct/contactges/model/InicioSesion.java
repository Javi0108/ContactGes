package fct.contactges.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InicioSesion {
	
	private StringProperty usuario = new SimpleStringProperty(this, "usuario");
	private StringProperty password = new SimpleStringProperty(this, "password");
	private StringProperty email = new SimpleStringProperty(this, "email");
//	private StringProperty emailPass = new SimpleStringProperty(this, "emailPass");
	
	public InicioSesion(String usuario, String password, String email) {
		this.usuario = new SimpleStringProperty(this, "usuario", usuario);
		this.password = new SimpleStringProperty(this, "password", password);
		this.email = new SimpleStringProperty(this, "email", email);
	}
	
	public InicioSesion() {

	}
	
	public final StringProperty usuarioProperty() {
		return this.usuario;
	}
	
	public final String getUsuario() {
		return this.usuarioProperty().get();
	}
	
	public final void setUsuario(final String usuario) {
		this.usuarioProperty().set(usuario);
	}
	
	public final StringProperty passwordProperty() {
		return this.password;
	}
	
	public final String getPassword() {
		return this.passwordProperty().get();
	}
	
	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}

	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
//	public final StringProperty emailPassProperty() {
//		return this.emailPass;
//	}
	
//	public final String getEmailPass() {
//		return this.emailPassProperty().get();
//	}
	
//	public final void setEmailPass(final String emailPass) {
//		this.emailPassProperty().set(emailPass);
//	}
}
