package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;

public class Profesor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ER_TELEFONO = "[69][0-9]{8}";
	private static final String ER_CORREO = "([a-zA-z0-9.-_]{1,})(\\@[a-zA-z]{1,})(\\.[a-z]{1,3})";
	private String nombre;
	private String correo;
	private String telefono;

// Creo el contructor por defecto con los datos obligarorios que son nombre correo y telefono
	public Profesor(String nombre, String correo, String telefono) {
		// pedimos los datos con los setter
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);

	}

// Creo constuctor copia con los dos datos que son obligatorios nombre y telefono
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}

	// Creo constructor
	public Profesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		setNombre(profesor.getNombre());
		setCorreo(profesor.getCorreo());
		setTelefono(profesor.getTelefono());
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		} else if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		}
		this.nombre = formateaNombre(nombre);
	}

	public String getCorreo() {
		return correo;
	}
	
	private String formateaNombre(String nombre) {
		/*
		 * Comprobamos que existe y si no lanzamos excepcion trim() Quite los espacios
		 * en blanco de ambos lados de una cuerda
		 */
		// Con replaceAll, cambiamos "{2, }" minimo dos espacios seguidos, maximo los
		// que sean por " " 1 solo espacio.
		// Luego hacemos trim() para quitar los espacios del inicio y el final
		nombre = nombre.replaceAll("\\s{2,}", " ").trim();
		// Con la funcion split creamos un array desde un string, el parametro es lo que
		// usamos de divisor, que no sera incluido en el array
		String[] palabras = nombre.split(" ");
		String nuevoNombre = "";
		for (int i = 0; i <= palabras.length - 1; i++) {
			// EL primer elemento coge solo la primera letra y la pone en mayuscula, luego
			// suma el resto de la palabra
			palabras[i] = palabras[i].substring(0, 1).toUpperCase() + palabras[i].substring(1).toLowerCase();
			// Añade cada palabra al nuevo string
			nuevoNombre += palabras[i] + " ";
		}
		// Quita el espacio final de añadir palabra + " "
		nombre = nuevoNombre.trim();
		return nombre;
	}

	public void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if (correo.trim().equals("") || !correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null) {
			this.telefono = null;
		} else if (telefono.trim().equals("")  || !telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
		}
		this.telefono = telefono;
	}
	
	public static Profesor getProfesorFicticio(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		return new Profesor("Jose Terres", correo, "666555444");
	}
	
// creamos hashCode y equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Profesor other = (Profesor) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}

// Cremos el metodo toString
	@Override
	public String toString() {
		if (telefono == null) {
			return "nombre=" + this.nombre + ", correo=" + this.correo + "";
		} else {
			return "nombre=" + this.nombre + ", correo=" + this.correo + ", teléfono=" + this.telefono + "";

		}
	}

}
