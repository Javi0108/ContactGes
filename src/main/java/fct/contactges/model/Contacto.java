package fct.contactges.model;

import java.time.LocalDate;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contacto {
	private StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private ObjectProperty<LocalDate> fechaNacimiento = new SimpleObjectProperty<>(this, "fechaNacimiento");
	private LongProperty telefono = new SimpleLongProperty(this, "telefono");
	private StringProperty email = new SimpleStringProperty(this, "email");

	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final String getNombre() {
		return this.nombreProperty().get();
	}

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final ObjectProperty<LocalDate> fechaNacimientoProperty() {
		return this.fechaNacimiento;
	}

	public final LocalDate getFechaNacimiento() {
		return this.fechaNacimientoProperty().get();
	}

	public final void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimientoProperty().set(fechaNacimiento);
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
	
	public static void copiar(Contacto origen, Contacto destino) {
		destino.setNombre(origen.getNombre());
		destino.setFechaNacimiento(origen.getFechaNacimiento());
		destino.setTelefono(origen.getTelefono());	
		destino.setEmail(origen.getEmail());
	}

}
