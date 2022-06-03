package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores{

	private static final String NOMBRE_FICHERO_PROFESORES = "datos/profesores.dat";
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
	
	// llama a leer y este accede a los ficheros
		public void comenzar() {
			leer();
		}
		// Leer leera los ficheros
		private void leer() {
			File ficheroProfesores = new File (NOMBRE_FICHERO_PROFESORES);
			//excepciones
			try (ObjectInputStream entrada = new ObjectInputStream (new FileInputStream(ficheroProfesores))) {
				Profesor profesor = null;
				do {
					// asignamos el profesor que viene del imputo string y le asignamos (csateamos), cuando lee el objetos le decimos que es de tipo profesor
					profesor = (Profesor) entrada.readObject();
					// insertamos los profesores 
					insertar(profesor);
				}while(profesor != null);			
				// ordenamos los catch para que salten y no den errores
				// si ponemos IOException nos dara error pq salta en un orden que no es el adecuado
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: Fichero no encontrado.");
				e.printStackTrace();
			} catch (EOFException e) {
				System.out.println("Profesores leidos con éxito.");
			} catch (IOException e) {
				System.out.println("ERROR: Algo salió mal.");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("ERROR: No se ha encontrado la clase leer.");
				e.printStackTrace();
			} catch (OperationNotSupportedException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} 
		}
		// creamos terminar
		@Override
		public void terminar() {
			escribir();
		}
		//Creamos escribir
		private void escribir() {
			File ficheroProfesores = new File (NOMBRE_FICHERO_PROFESORES);
			try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
				for (Profesor profesor : coleccionProferores) {
					salida.writeObject(profesor);
				}
				System.out.println("El fichero profesores fue creado correctamente.");			
			}catch (FileSystemNotFoundException e){
				System.out.println("ERROR: No se puede crear el fichero profesores");
			}catch (IOException e) {
				System.out.println("ERROR: Algo salió mal");
			}
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
			Profesor profeBuscado = it.next();
			if (profeBuscado.equals(profesor)) {
				return new Profesor(profeBuscado);
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
		List<String>  cadena = new ArrayList<>();;
		Iterator<Profesor> it = coleccionProferores.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}
}