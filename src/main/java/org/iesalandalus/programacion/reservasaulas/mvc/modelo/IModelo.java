package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IModelo {
	
	public void comenzar();
	
	public void terminar();
	
	public List<Aula> getAulas();
	
	public List<String> representarAulas();
	
	public Aula buscarAula(Aula aula);
	
	public void insertarAula(Aula aula) throws OperationNotSupportedException;
	
	public void borrarAula(Aula aula) throws OperationNotSupportedException;
	
	public List<Profesor> getProfesores();
	
	public List<String> representarProfesores();
	
	public Profesor buscarProfesor(Profesor profesor);
	
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;
	
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;
	
	public List<Reserva> getReservas();
	
	public int getNumReservas();
	
	public List<String> representarReservas();
	
	public Reserva buscarReserva(Reserva reserva);
	
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException;
	
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException;
	
	public List<Reserva> getReservaAula(Aula aula);
	
	public List<Reserva> getReservasProfesor(Profesor profesor);
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia);
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);
}
