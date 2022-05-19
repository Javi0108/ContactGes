package fct.contactges.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {
	private StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private StringProperty telefono = new SimpleStringProperty(this, "telefono");
	private StringProperty email = new SimpleStringProperty(this, "email");
	private StringProperty sexo = new SimpleStringProperty(this, "sexo");
	private StringProperty direccion = new SimpleStringProperty(this, "direccion");


	public Contacto(String nombre, String telefono, String email, String sexo, String direccion) {
		this.nombre = new SimpleStringProperty(this, "nombre", nombre);
		this.telefono = new SimpleStringProperty(this, "telefono", telefono);
		this.email = new SimpleStringProperty(this, "email", email);
		this.sexo = new SimpleStringProperty(this, "sexo", sexo);
		this.direccion = new SimpleStringProperty(this, "direccion", direccion);
	}
	
	public Contacto() {
		
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty telefonoProperty() {
		return this.telefono;
	}

	public final String getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final String telefono) {
		this.telefonoProperty().set(telefono);
	}
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public String getEmail() {
		return this.emailProperty().get();
	}

	public void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
	public final StringProperty sexoProperty() {
		return this.sexo;
	}
	
	public final String getSexo() {
		return this.sexoProperty().get();
	}
	
	public final void setSexo(final String sexo) {
		this.sexoProperty().set(sexo);
	}
	
	public final StringProperty direccionProperty() {
		return this.direccion;
	}
	
	public final String getDireccion() {
		return this.direccionProperty().get();
	}
	
	public final void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}
	
	public static void copiar(Contacto origen, Contacto destino) {
		destino.setNombre(origen.getNombre());
		destino.setSexo(origen.getSexo());
		destino.setTelefono(origen.getTelefono());	
		destino.setEmail(origen.getEmail());
		destino.setDireccion(origen.getDireccion());
	}


	
}
