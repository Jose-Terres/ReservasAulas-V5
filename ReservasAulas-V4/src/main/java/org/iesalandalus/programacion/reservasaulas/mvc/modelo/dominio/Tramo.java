package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

public enum Tramo {
	MANANA("Mañana"), TARDE("Tarde");

	// creamos el atributo del enum
	String cadenAMostrar;

	// cremos constructor y le decimos
	private Tramo(String tramo) {
		this.cadenAMostrar = tramo;
	}

	// Cremos metodo toString
	@Override
	public String toString() {
		return cadenAMostrar;
	}

}
