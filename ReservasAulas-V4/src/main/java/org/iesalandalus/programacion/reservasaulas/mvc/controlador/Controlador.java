package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
// creamos clse controlador cons los atributos
public class Controlador implements IControlador {

	private IVista vista;
	private IModelo modelo;
// constructor
	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
	}
// cremaos comenzar
	public void comenzar() {
		// llamamos al modelo y comenzar para que leea los datos
		modelo.comenzar();
		vista.comenzar();
	}
// creamso terminal
	public void terminar() {
		//llamamos al modelo terminar para grabar los datos antes de salir
		modelo.terminar();		
	}
// creamos insertarAula
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		modelo.insertarAula(aula);
	}
// creamos insertarProfesor
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.insertarProfesor(profesor);
	}
// creamos borrarAula
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		modelo.borrarAula(aula);
	}
	// creamos borrarProfesor
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		modelo.borrarProfesor(profesor);
	}
	// creamos buscarAula
	public Aula buscarAula(Aula aula) {
		return modelo.buscarAula(aula);
	}
	// creamos buscarProfesor
	public Profesor buscarProfesor(Profesor profesor) {
		return modelo.buscarProfesor(profesor);
	}
	// creamos representarAulas
	public List<String> representarAulas() {
		return modelo.representarAulas();
	}
	// creamos representarProfesor 
	public List<String> representarProfesores() {
		return modelo.representarProfesores();
	}
	// creamosrepresentarReservas
	public List<String> representarReservas() {
		return modelo.representarReservas();
	}
	// creamos realizarReserva
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.realizarReserva(reserva);
	}
	//creamos anularReserva
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		modelo.anularReserva(reserva);
	}
	
	public List<Reserva> getReservas() {
		return modelo.getReservas();
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		return modelo.getReservaAula(aula);
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return modelo.getReservasProfesor(profesor);
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return modelo.getReservasPermanencia(permanencia);
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanecia) {
		return modelo.consultarDisponibilidad(aula, permanecia);
	}
	@Override
	public List<Aula> getAulas() {
		return modelo.getAulas();
	}
	@Override
	public List<Profesor> getProfesores() {
		return modelo.getProfesores();
	}
}

