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
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas{
	private static final String NOMBRE_FICHERO_AULAS="datos/aulas.dat";
	private List<Aula> coleccionAulas;

	// Creamos constructor
	// el cosntructor lanza tipos de excpcion illegal y null
	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}

	public Aulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		setAulas(aulas);
	}
	// llama a leer y este accede a los ficheros
	public void comenzar() {
		leer();
	}
	
	// Leer leera los ficheros
	private void leer() {
		File ficheroProfesores = new File (NOMBRE_FICHERO_AULAS);
		//excepciones
		try (ObjectInputStream entrada = new ObjectInputStream (new FileInputStream(ficheroProfesores))) {
			Aula aula = null;
			do {
				// asignamos el profesor que viene del imputo string y le asignamos (csateamos), cuando lee el objetos le decimos que es de tipo profesor
				aula = (Aula) entrada.readObject();
				// insertamos los profesores 
				insertar(aula);
			}while(aula != null);			
			// ordenamos los catch para que salten y no den errores
			// si ponemos IOException nos dara error pq salta en un orden que no es el adecuado
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Fichero no encontrado.");
			e.printStackTrace();
		} catch (EOFException e) {
			System.out.println("Aulas leidas con éxito.");
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
				File ficheroProfesores = new File (NOMBRE_FICHERO_AULAS);
				try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
					for (Aula aula : coleccionAulas) {
						salida.writeObject(aula);
					}
					System.out.println("El fichero aulas fue creado correctamente.");			
				}catch (FileSystemNotFoundException e){
					System.out.println("ERROR: No se puede crear el fichero aulas");
				}catch (IOException e) {
					System.out.println("ERROR: Algo salió mal");
				}
			}
	
	// creamos SetAulas
	private void setAulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		this.coleccionAulas = aulas.getAulas();
	}
	
	// Creamos copia profunda Aulas
	private List<Aula> copiaProfundaAulas() throws IllegalArgumentException, NullPointerException {
		List<Aula> copiaAulas = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			copiaAulas.add(new Aula(it.next()));
		}
		return copiaAulas;
	}
	
	public List<Aula> getAulas() {
		List<Aula> copiaAulasOrdenadas = copiaProfundaAulas();
		copiaAulasOrdenadas.sort(Comparator.comparing(Aula::getNombre));
		return copiaAulasOrdenadas;
	}
	
	public int getNumAulas() {
		return coleccionAulas.size();
	}
// creamos insrtar
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		if (coleccionAulas.contains(aula)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		} else {
			coleccionAulas.add(aula);
		}

	}

	// Buscar(Aula)
	public Aula buscar(Aula aula) throws IllegalArgumentException, NullPointerException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			Aula aulaBuscada = it.next();
			if (aulaBuscada.equals(aula)) {
				return new Aula(aulaBuscada);
			}
		}
		return null;

	}

	// creamos Borrar

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		boolean borrado = false;
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			if (it.next().equals(aula)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} 
	}

	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}

	
}
