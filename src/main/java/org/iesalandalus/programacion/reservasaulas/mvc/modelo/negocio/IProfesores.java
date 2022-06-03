package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public interface IProfesores {
	public void comenzar();
	public void terminar();

	List<Profesor> getProfesores();
	
	public int getNumProfesores();
	
	void insertar(Profesor profesor) throws OperationNotSupportedException;
	
	Profesor buscar(Profesor profesor);
	
	void borrar(Profesor profesor) throws OperationNotSupportedException;
	
	List<String> representar();
	
}
