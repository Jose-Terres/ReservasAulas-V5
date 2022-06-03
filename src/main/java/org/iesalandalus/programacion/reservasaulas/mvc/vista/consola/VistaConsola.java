package org.iesalandalus.programacion.reservasaulas.mvc.vista.consola;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

// creamos la clase vista
public class VistaConsola implements IVista {

	private IControlador controlador;

	// constructor
	public VistaConsola() {
		Opcion.setVista(this);
	}

	// creamso setControlador con el null y su error
	public void setControlador(IControlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}

	// creamos comenzar
	public void comenzar() {
		Consola.mostrarCabecera("Programa para la gestión de reservas de espacios del IES Al-Ándalus");
		int opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			Opcion opcionElegida = Opcion.getOpcionSegunOrdinal(opcion);
			opcionElegida.ejecutar();
		} while (opcion != Opcion.SALIR.ordinal());
	}

	// cremos salir
	public void salir() {
		controlador.terminar();
		System.out.println("ADIOS");
	}

	// creamos insertarAula
	public void insertarAula() {
		Consola.mostrarCabecera("INSERTAR AULA");
		// intenta llamando al controlador insertar aula con el paramentro de la consola
		// LeerAula y si todo es correcto muestra mensaje
		try {
			controlador.insertarAula(Consola.leerAula());
			System.out.println("Aula Insertada Correctamente");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// creamos borrarAula
	public void borrarAula() {
		Consola.mostrarCabecera("BORRAR AULA");
		try {
			controlador.borrarAula(Consola.leerAulaFicticia());
			System.out.println("Aula borrada correctamente");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// creamos buscarAula
	public void buscarAula() {
		Consola.mostrarCabecera("BUSCAR AULA");
		Aula aula;
		try {
			aula = controlador.buscarAula(Consola.leerAulaFicticia());
			if (aula == null) {
				System.out.println("\nEl aula no existe");
			} else {
				System.out.println(aula.toString());
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// mostramos listarAulas
	public void listarAulas() {
		Consola.mostrarCabecera("LISTADO DE AULAS");
		List<String> listaAulas = controlador.representarAulas();
		if (!listaAulas.isEmpty()) {
			Iterator<String> it = listaAulas.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("No hay aulas que mostrar");
		}
	}

	// creamos insertarProfesor
	public void insertarProfesor() {
		Consola.mostrarCabecera("INSERTAR PROFESOR");
		try {
			controlador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Profesor Insertado Correctamente");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// creamos borrarProfesor
	public void borrarProfesor() {
		Consola.mostrarCabecera("BORRAR PROFESOR");
		try {
			controlador.borrarProfesor(Consola.leerProfesorFicticio());
			System.out.println("Profesor borrado correctamente");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// creamos buscarProfesor
	public void buscarProfesor() {
		Consola.mostrarCabecera("BUSCAR PROFESOR");
		Profesor profesor;
		try {
			profesor = controlador.buscarProfesor(Consola.leerProfesorFicticio());
			if (profesor == null) {
				System.out.println("\nEl profesor no existe");
			} else {
				System.out.println(profesor.toString());
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// listwar profesores
	public void listarProfesores() {
		Consola.mostrarCabecera("LISTADO DE PROFESORES");
		List<String> listaProfesores = controlador.representarProfesores();
		if (!listaProfesores.isEmpty()) {
			Iterator<String> it = listaProfesores.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("No hay profesores que mostrar");
		}
	}

	// cramos realizarReserva
	public void realizarReserva() {
		Consola.mostrarCabecera("REALIZAR RESERVA");
		try {
			Reserva reserva = Consola.leerReserva();
			Profesor profesor = controlador.buscarProfesor(/*PROFESOR FICTICIO*/reserva.getProfesor());
			Aula aula = controlador.buscarAula(reserva.getAula());
			controlador.realizarReserva(new Reserva(profesor, aula, reserva.getPermanencia()));
			System.out.println("Reserva realizada con éxito.");
		} catch (OperationNotSupportedException | NullPointerException e){
			System.out.println(e.getMessage());
		}
	}

// creamos anularReserva
	public void anularReserva() {
		try {
			System.out.println("ANULAR RESERVA");
			controlador.anularReserva(Consola.leerReservaFicticia());
			System.out.println("Reserva eliminda con éxito");
		} catch (IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	// creamos listar Reservas
	public void listarReservas() {
		Consola.mostrarCabecera("LISTADO DE RESERVAS");
		List<String> reservas = controlador.representarReservas();
		if (!reservas.isEmpty()) {
			Iterator<String> it = reservas.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("No hay reservas para mostrar");
		}
	}

	// cremos listarReservasAulas
	public void listarReservasAula() {
		Consola.mostrarCabecera("LISTADO DE RESERVAS POR AULA");
		List<Reserva> reservas = controlador.getReservasAula(Consola.leerAulaFicticia());
		if (!reservas.isEmpty()) {
			Iterator<Reserva> it = reservas.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("No hay reservas para mostrar");
		}
	}

	// creamos listarReservasProfesor
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("LISTADO DE RESERVAS POR PROFESOR");
		List<Reserva> reservas = controlador.getReservasProfesor(Consola.leerProfesorFicticio());
		if (!reservas.isEmpty()) {
			Iterator<Reserva> it = reservas.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("No hay reservas para mostrar");
		}
	}

	// Creamos consultarDisponibilidad
	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("CONSULTAR DISPONIBILIDAD");
		Aula aula = Consola.leerAulaFicticia();
		if (controlador.buscarAula(aula) == null) {
			System.out.println("El aula no esta en el listado");
		} else {
			if (controlador.consultarDisponibilidad(aula, Consola.leerPermanencia())) {
				System.out.println("El aula esta disponible");
			} else {
				System.out.println("El aula no esta disponible");
			}
		}

	}
}
