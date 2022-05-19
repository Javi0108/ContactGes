package fct.contactges.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {
	private StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private LongProperty telefono;
	private StringProperty email;
	private StringProperty sexo;
	private StringProperty direccion;


	public Contacto(String nombre, Long telefono, String email, String sexo, String direccion) {
		this.nombre = new SimpleStringProperty(this, "nombre", nombre);
		this.telefono = new SimpleLongProperty(this, "telefono", telefono);
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

	public final LongProperty telefonoProperty() {
		return this.telefono;
	}

	public final long getTelefono() {
		return this.telefonoProperty().get();
	}

	public final void setTelefono(final long telefono) {
		this.telefonoProperty().set(telefono);
	}
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public StringProperty getEmail() {
		return email;
	}

	public void setEmail(StringProperty email) {
		this.email = email;
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
