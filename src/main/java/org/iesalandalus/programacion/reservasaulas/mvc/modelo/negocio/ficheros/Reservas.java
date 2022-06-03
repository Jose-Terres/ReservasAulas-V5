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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;
// creamos reservas con los atributos y la clase  reserva
public class Reservas implements IReservas{
	
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.f;
	private static final String NOMBRE_FICHERO_RESERVAS="datos/reservas.dat";
	private List<Reserva> coleccionReservas;
	
	// Creamos el constructor 
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}
	
	public Reservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		setReservas(reservas);
	}
	// llama a leer y este accede a los ficheros
	public void comenzar() {
		leer();
	}
	// Leer leera los ficheros
	private void leer() {
		File ficheroReservas = new File (NOMBRE_FICHERO_RESERVAS);
		//excepciones
		try (ObjectInputStream entrada = new ObjectInputStream (new FileInputStream(ficheroReservas))) {
			Reserva reserva = null;
			do {
				// asignamos el profesor que viene del imputo string y le asignamos (csateamos), cuando lee el objetos le decimos que es de tipo profesor
				reserva = (Reserva) entrada.readObject();
				// insertamos los profesores 
				insertar(reserva);
			}while(reserva != null);			
			// ordenamos los catch para que salten y no den errores
			// si ponemos IOException nos dara error pq salta en un orden que no es el adecuado
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Fichero no encontrado.");
			e.printStackTrace();
		} catch (EOFException e) {
			System.out.println("Reservas leidas con éxito");
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
		File ficheroProfesores = new File (NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
			for (Reserva reserva : coleccionReservas) {
				salida.writeObject(reserva);
			}
			System.out.println("El fichero reservas fue creado correctamente.");			
		}catch (FileSystemNotFoundException e){
			System.out.println("ERROR: No se puede crear el fichero reservas");
		}catch (IOException e) {
			System.out.println("ERROR: Algo salió mal");
		}
	}
	
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		this.coleccionReservas = reservas.getReservas();
	}

	
	// creamos copia profunda
	private List<Reserva> copiaProfundaReservas() {
		List<Reserva> copiaReservas = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			copiaReservas.add(new Reserva(it.next()));
		}
		return copiaReservas;
	}
	
	public List<Reserva> getReservas() {
		List<Reserva> reservasOrdenadas = copiaProfundaReservas();
	    Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
	    Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
	      int comparacion = -1;
	      if (p1.getDia().equals(p2.getDia())) {
	        if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
	          comparacion = Integer.compare(((PermanenciaPorTramo)p1).getTramo().ordinal(), ((PermanenciaPorTramo)p2).getTramo().ordinal());
	        } else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
	          comparacion = ((PermanenciaPorHora)p1).getHora().compareTo(((PermanenciaPorHora)p2).getHora());
	        }
	      } else {
	        comparacion = p1.getDia().compareTo(p2.getDia());
	      }
	      return comparacion;
	    };
	    reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
	    return reservasOrdenadas;
	}
	
	public int getNumReservas() {
		return coleccionReservas.size();
	}
// cramos metodo insertar
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		Reserva reservaBuscada = getReservasAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaBuscada != null) {
			if (reservaBuscada.getPermanencia() instanceof PermanenciaPorHora && reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
			if (reservaBuscada.getPermanencia() instanceof PermanenciaPorTramo && reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if (getPuntosGastadosReserva(reserva) + reserva.getPuntos() > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("ERROR: Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		}
		coleccionReservas.add(reserva);
	}
	
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		LocalDate reservaDia = reserva.getPermanencia().getDia();
		LocalDate mesSiguiente = LocalDate.now().plusMonths(1);
		LocalDate diaPrimeroMesSiguiente = LocalDate.of(mesSiguiente.getYear(), mesSiguiente.getMonth(), 1);
		return reservaDia.isAfter(diaPrimeroMesSiguiente.plusDays(-1));
	}
	
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntos = 0;
		for (Reserva reserv : getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia())) {
			puntos += reserv.getPuntos();
		}
		return puntos;
	}
	
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate fecha) {
		List<Reserva> coleccion = new ArrayList<>();
		for (Reserva reserva : getReservasProfesor(profesor)) {
			if (reserva.getPermanencia().getDia().getMonth().equals(fecha.getMonth())) {
				if (reserva.getPermanencia().getDia().getYear() == fecha.getYear()) {
					coleccion.add(reserva);
				}
			}
		}
		return coleccion;
	}
	
	private Reserva getReservasAulaDia(Aula aula, LocalDate fecha) {
		for (Reserva reserva : getReservasAula(aula)) {
			if (reserva.getPermanencia().getDia().equals(fecha)) {
				return reserva;
			}
		}
		return null;
	}
	
	// creamos buscar
	public Reserva buscar(Reserva reserva) throws IllegalArgumentException, NullPointerException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			Reserva reservaBuscada = it.next();
			if (reservaBuscada.equals(reserva)) {
				return new Reserva(reservaBuscada);
			}
		}
		return null;

	}
	// creamos borrar
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una reserva nula.");
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		}
		boolean borrado = false;
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			if (it.next().equals(reserva)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		} 
	}

	// creamos representar
	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		Iterator<Reserva> it = coleccionReservas.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}
	
	// getReservasProfesor
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> reservasOrdenadas = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if(reserva.getProfesor().equals(profesor)) {
				reservasOrdenadas.add(new Reserva(reserva));
			}
		}
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getCorreo);
	    Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
	    Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
	      int comparacion = -1;
	      if (p1.getDia().equals(p2.getDia())) {
	        if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
	          comparacion = Integer.compare(((PermanenciaPorTramo)p1).getTramo().ordinal(), ((PermanenciaPorTramo)p2).getTramo().ordinal());
	        } else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
	          comparacion = ((PermanenciaPorHora)p1).getHora().compareTo(((PermanenciaPorHora)p2).getHora());
	        }
	      } else {
	        comparacion = p1.getDia().compareTo(p2.getDia());
	      }
	      return comparacion;
	    };
	    reservasOrdenadas.sort(Comparator.comparing(Reserva::getProfesor, comparadorProfesor).thenComparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
	    return reservasOrdenadas;
	}
	// getReservasAula
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> reservasOrdenadas = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if(reserva.getAula().equals(aula)) {
				reservasOrdenadas.add(new Reserva(reserva));
			}
		}
	    Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
	    Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
	      int comparacion = -1;
	      if (p1.getDia().equals(p2.getDia())) {
	        if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
	          comparacion = Integer.compare(((PermanenciaPorTramo)p1).getTramo().ordinal(), ((PermanenciaPorTramo)p2).getTramo().ordinal());
	        } else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
	          comparacion = ((PermanenciaPorHora)p1).getHora().compareTo(((PermanenciaPorHora)p2).getHora());
	        }
	      } else {
	        comparacion = p1.getDia().compareTo(p2.getDia());
	      }
	      return comparacion;
	    };
	    reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
	    return reservasOrdenadas;
	}
	// Creamos getReservasPermanencia
	public List<Reserva> getReservasPermanencia(Permanencia permanecia) {
		if (permanecia == null) {
			throw new NullPointerException("ERROR: La permanencia no puede ser nula.");
		}
		List<Reserva> copiaReservas = new ArrayList<>();
		for (Reserva reserva : coleccionReservas) {
			if(reserva.getPermanencia().equals(permanecia)) {
				copiaReservas.add(reserva);
			}
		}
		return copiaReservas;
	}
	// Creamos consultarDisponibilidad

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		} 
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		List<Reserva> copiaReservas = getReservasAula(aula);
		if (copiaReservas.isEmpty()) {
			return true;
		}  else {
			Iterator<Reserva> it = copiaReservas.iterator();
			while (it.hasNext()) {
				if (it.next().getPermanencia().equals(permanencia)) {
					return false;
				}
			}
		}
		return true;
	}
}
