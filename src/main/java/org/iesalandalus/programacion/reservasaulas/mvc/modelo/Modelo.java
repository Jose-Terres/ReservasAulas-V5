package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.*;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo {
	// creamos la clase y le asignamos los atributos, en cantidad constante  le pongo 10 
		private IProfesores profesores;
		private IAulas aulas;
		private IReservas reservas;
		
		// constructor
		public Modelo(IFuenteDatos fuenteDatos) {
			profesores = fuenteDatos.crearProfesores();
			aulas = fuenteDatos.crearAulas();
			reservas = fuenteDatos.crearReservas();
		}
		
		@Override
		public void comenzar() {
			profesores.comenzar();
			aulas.comenzar();
			reservas.comenzar();			
		}
		@Override
		public void terminar() {
			profesores.terminar();
			aulas.terminar();
			reservas.terminar();
		}
		
		// llamamos a los metos que nos indica en el diagrama de clases
		public List<Aula> getAulas() {
			return aulas.getAulas();
		}
		// llamamos a getNumAulas
		public int getNumAulas() {
			return aulas.getNumAulas();
		}
		
		public List<String> representarAulas() {
			return aulas.representar();
		}
		
		public Aula buscarAula(Aula aula) {
			return aulas.buscar(aula);
		}
		
		public void insertarAula(Aula aula) throws OperationNotSupportedException {
			aulas.insertar(aula);
		}
		
		public void borrarAula(Aula aula) throws OperationNotSupportedException {
			aulas.borrar(aula);
		}
		
		public List<Profesor> getProfesores() {
			return profesores.getProfesores();
		}
		
		public int getNumProfesores() {
			return profesores.getNumProfesores();
		}
		
		public List<String> representarProfesores() {
			return profesores.representar();
		}
		
		public Profesor buscarProfesor(Profesor profesor) {
			return profesores.buscar(profesor);
		}
		
		public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
			profesores.insertar(profesor);
		}
		
		public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
			profesores.borrar(profesor);
		}
		
		public List<Reserva> getReservas() {
			return reservas.getReservas();
		}
		
		public int getNumReservas() {
			return reservas.getNumReservas();
		}
		
		public List<String> representarReservas() {
			return reservas.representar();
		}
		
		public Reserva buscarReserva(Reserva reserva) {
			return reservas.buscar(reserva);
		}
		
		public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
			reservas.insertar(reserva);
		}
		
		public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
			reservas.borrar(reserva);
		}
		
		public List<Reserva> getReservaAula(Aula aula) {
			return reservas.getReservasAula(aula);
		}
		
		public List<Reserva> getReservasProfesor(Profesor profesor) {
			return reservas.getReservasProfesor(profesor);
		}
		
		public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
			return reservas.getReservasPermanencia(permanencia);
		}
		
		public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
			return reservas.consultarDisponibilidad(aula, permanencia);
		}
}

