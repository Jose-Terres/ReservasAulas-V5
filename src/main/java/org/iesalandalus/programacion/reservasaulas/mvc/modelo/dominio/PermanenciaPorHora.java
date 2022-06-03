package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PermanenciaPorHora extends Permanencia {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// atributos
	private static final int PUNTOS = 3;
	private static final LocalTime HORA_INICIO = LocalTime.of(8, 0);
	private static final LocalTime HORA_FIN= LocalTime.of(22, 0);
	protected static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("hh:mm");
	private LocalTime hora;
	// contructor
	public PermanenciaPorHora(LocalDate dia, LocalTime hora) {
		super (dia);
		setHora(hora);
	}
	// contructor
	public PermanenciaPorHora(PermanenciaPorHora permaPorHora) {
		super(permaPorHora);
		setHora(permaPorHora.getHora());
	}
	
	// getHora
	public LocalTime getHora() {
		return hora;
	}

//setHora
	private void setHora(LocalTime hora) {
		if (hora == null) {
			throw new NullPointerException("ERROR: La hora de una permanencia no puede ser nula.");
		}
		if (hora.isBefore(HORA_INICIO) || hora.isAfter(HORA_FIN)) {
			throw new IllegalArgumentException("ERROR: La hora de una permanencia no es válida.");
		}
		if (hora.getMinute() != 0) {
			throw new IllegalArgumentException("ERROR: La hora de una permanencia debe ser una hora en punto.");
		}
		this.hora = hora;
	}

//hasCode y equals
	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((super.getDia() == null) ? 0 : super.getDia().hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorHora other = (PermanenciaPorHora) obj;
		if (hora == null) {
			if (other.getHora() != null)
				return false;
		} else if (!hora.equals(other.getHora()))
			return false;
		if (super.getDia() == null) {
			if (other.getDia() != null)
				return false;
		} else if (!super.getDia().equals(other.getDia()))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return super.toString() + ", hora=" + hora;
	}
	
	

}
;