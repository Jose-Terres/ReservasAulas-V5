package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IControlador {

	public void comenzar();

	public void terminar();

	public void insertarAula(Aula aula) throws OperationNotSupportedException;

	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	public void borrarAula(Aula aula) throws OperationNotSupportedException;

	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	public Aula buscarAula(Aula aula);
	
	public List<Aula> getAulas();
	
	public List<Profesor> getProfesores();

	public Profesor buscarProfesor(Profesor profesor);

	public List<String> representarAulas();

	public List<String> representarProfesores();

	public List<String> representarReservas();
	
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException;
	
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException;
	
	public List<Reserva> getReservas();
	
	public List<Reserva> getReservasAula(Aula aula);
	
	public List<Reserva> getReservasProfesor(Profesor profesor);
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia);
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanecia);
}
