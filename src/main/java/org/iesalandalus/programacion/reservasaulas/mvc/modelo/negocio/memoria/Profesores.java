package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores{

	private List<Profesor> coleccionProferores;

	// Creamos el constructor
	public Profesores() {
		coleccionProferores = new ArrayList<>();
	}

	public Profesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		setProfesores(profesores);
	}

	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se pueden instanciar profesores nulos.");
		}
		this.coleccionProferores = profesores.getProfesores();
	}
	// creamos copia profunda
	// Creamos copia profunda Aulas
	private List<Profesor> copiaProfundaProfesores() throws IllegalArgumentException, NullPointerException {
		List<Profesor> copiaProfesores = new ArrayList<>();
		Iterator<Profesor> it = coleccionProferores.iterator();
		while (it.hasNext()) {
			copiaProfesores.add(new Profesor(it.next()));
		}
		return copiaProfesores;
	}
	
	public List<Profesor> getProfesores() {
		List<Profesor> copiaProfesoresOrdenados = copiaProfundaProfesores();
		copiaProfesoresOrdenados.sort(Comparator.comparing(Profesor::getCorreo));
		return copiaProfesoresOrdenados;
	}
	
	public int getNumProfesores() {
		return coleccionProferores.size();
	}

	// cramos Insertar
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (coleccionProferores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		} else {
			coleccionProferores.add(profesor);
		}

	}

	// Buscar(Profesor)
	public Profesor buscar(Profesor profesor) throws IllegalArgumentException, NullPointerException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		Iterator<Profesor> it = coleccionProferores.iterator();
		while (it.hasNext()) {
			if (it.next().equals(profesor)) {
				return new Profesor(profesor);
			}
		}
		return null;

	}
	// creamos Borrar

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		boolean borrado = false;
		Iterator<Profesor> it = coleccionProferores.iterator();
		while (it.hasNext()) {
			if (it.next().equals(profesor)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		} 
	}

	public List<String> representar() {
		List<String>  cadena = new ArrayList<>();
		Iterator<Profesor> it = coleccionProferores.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}

	@Override
	public void comenzar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminar() {
		// TODO Auto-generated method stub
		
	}
}