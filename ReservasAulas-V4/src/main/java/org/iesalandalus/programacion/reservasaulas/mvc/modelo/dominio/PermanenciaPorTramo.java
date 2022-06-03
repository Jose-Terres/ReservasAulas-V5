package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;

public class PermanenciaPorTramo extends Permanencia{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PUNTOS = 10;
	private Tramo tramo;
	
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		//Llamamos al constructos de la clase padre, en este caso permanencia
		super (dia);
		//asignamos el atributo de esta clase
		setTramo(tramo);
		
	}
	// permanenciPorTramo
	public PermanenciaPorTramo(PermanenciaPorTramo permanenciaPorTramo) {
		super (permanenciaPorTramo);
		setTramo(permanenciaPorTramo.getTramo());
	}
//getTramo
	public Tramo getTramo() {
		return tramo;
	}
//setTramo
	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}
	
	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tramo == null) ? 0 : tramo.hashCode());
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
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		if (tramo == null) {
			if (other.tramo != null)
				return false;
		} else if (!tramo.equals(other.tramo))
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
		return super.toString() + ", tramo=" + tramo;
	}
}
