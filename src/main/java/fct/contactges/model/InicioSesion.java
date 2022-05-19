package fct.contactges.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;

public class InicioSesion {
	
	private StringProperty usuario = new SimpleStringProperty(this, "usuario");
	private StringProperty password = new SimpleStringProperty(this, "password");
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
}
