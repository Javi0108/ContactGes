package fct.contactges.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Agenda {
	private ListProperty<Contacto> contactos;

	public Agenda() {
		contactos = new SimpleListProperty<>(this, "contactos", FXCollections.observableArrayList());
	}

	public final ListProperty<Contacto> contactosProperty() {
		return this.contactos;
	}

	public final ObservableList<Contacto> getContactos() {
		return this.contactosProperty().get();
	}

	public final void setContactos(final ObservableList<Contacto> contactos) {
		this.contactosProperty().set(contactos);
	}

}
