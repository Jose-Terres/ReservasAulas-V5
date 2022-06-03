package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;

public class Reserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// los atributos serian los objetos a los que señalan las flechas que son
	// Permanencia Profesor y Aula
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;

	// creamos constructor con tres parametros
	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}

	// Creamos constructor copia
	public Reserva(Reserva reserva) {
		// comprobamos que no sea null y si lo es lanzamos mensaje de error
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		} else {
			// si no es null entonces pedimos los datos con set
			setPermanencia(reserva.getPermanencia());
			setProfesor(reserva.getProfesor());
			setAula(reserva.getAula());
		}
	}

	// creamos getter y seters
	public Permanencia getPermanencia() {
		if (this.permanencia instanceof PermanenciaPorTramo) {
			return new PermanenciaPorTramo((PermanenciaPorTramo) this.permanencia);
		} else {
			return new PermanenciaPorHora((PermanenciaPorHora) this.permanencia);
		}
	}

	public void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		if (permanencia instanceof PermanenciaPorTramo) {
			 this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		} else {
			this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		}
		
	}

	public Profesor getProfesor() {
		return new Profesor(profesor);
	}

	public void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = new Profesor(profesor);
	}

	public Aula getAula() {
		return new Aula(aula);
	}

	public void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula = new Aula(aula);
	}
	
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		return new Reserva(Profesor.getProfesorFicticio("ejemplo@profesor.com"), aula, permanencia);
	}
	
	public float getPuntos() {
		return permanencia.getPuntos() + aula.getPuntos();
	}

	// creamos hasCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aula == null) ? 0 : aula.hashCode());
		result = prime * result + ((permanencia == null) ? 0 : permanencia.hashCode());
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
		Reserva other = (Reserva) obj;
		if (aula == null) {
			if (other.aula != null)
				return false;
		} else if (!aula.equals(other.aula))
			return false;
		if (permanencia == null) {
			if (other.permanencia != null)
				return false;
		} else if (!permanencia.equals(other.permanencia))
			return false;
		return true;
	}

	// creamos toString
	@Override
	public String toString() {
		return profesor + ", " + aula + ", " + permanencia + ", puntos=" + Float.toString(getPuntos()).replace(".", ",");
	}

}
