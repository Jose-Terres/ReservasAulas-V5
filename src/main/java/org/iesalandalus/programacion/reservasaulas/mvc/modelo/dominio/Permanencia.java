package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// cremos los atributos
	private LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Creamos constructor e ingresamos los datos con los set
	public Permanencia(LocalDate dia) {
		setDia(dia);
	}

	// Cremos el constructor copia
	public Permanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		} else {
			setDia(permanencia.getDia());
		}
	}

	// Creamps los getters y setters
	public LocalDate getDia() {
		return dia;
	}

	public void setDia(LocalDate dia) {
		if (dia == null) {
			throw new NullPointerException("ERROR: El día de una permanencia no puede ser nulo.");
		} else {
			this.dia = dia;
		}
	}
//getPuntos
	public abstract int getPuntos();
	
	// Creamos hashCode y equals
	public abstract int hashCode();

	public abstract boolean equals(Object objeto);

	// creamos metodo toString
	@Override
	public String toString() {
		return "día=" +this.dia.format(FORMATO_DIA);
	}

}
