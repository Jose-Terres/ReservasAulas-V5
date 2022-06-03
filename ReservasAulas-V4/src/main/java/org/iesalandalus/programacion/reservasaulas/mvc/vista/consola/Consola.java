package org.iesalandalus.programacion.reservasaulas.mvc.vista.consola;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;
// creamos clase consola
public class Consola {

	static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	// constructor
	private  Consola(){
		
	}
	// mostrarMenu
	public static void mostrarMenu() {
		System.out.println("OPCIONES:");
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public  static void mostrarCabecera(String mensaje) {
		int longitudmensaje = mensaje.length();
		System.out.println(mensaje);
		for (int i = 0; i<longitudmensaje; i++ ) {
			System.out.print("-");
		}
		System.out.println();
	}
	// opcion que nos da empezaria por 1 al ponerle el -1 en opcion leng asi que ahora tenemos que ponerle tambien -1 a la entrada entero para que conincida con el valor que nso introducen
	public static int elegirOpcion() {
		int opcion;
		do {
			System.out.print("Elige una opción: ");
			opcion = Entrada.entero();
		}while (!Opcion.esOrdinalValido(opcion));
		return opcion;
	}
	
	public static Aula leerAula() {
		boolean centinela = false;
		Aula aula= null;
		// NOMBRE
		do {
			try {
				aula = new Aula(leerNombreAula(), leerNumeroPuestos());
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}	
		} while (!centinela);
		return aula;
	}
	
	public static int leerNumeroPuestos() {
		int puestos = 0;
		do {
			System.out.print("Introduzca el número de puestos del aula (Min 10 - Max 100): ");
			puestos = Entrada.entero();
		} while (puestos < 10 || puestos > 100);
		return puestos;
	}
	
	public static Aula leerAulaFicticia() {
		boolean centinela = false;
		Aula aula = null;
		do {
			try {
				aula = Aula.getAulaFicticia(leerNombreAula());
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return aula;
	}
	
	public static String leerNombreAula() {
		System.out.println("introduce el nombre del aula");
		String nombre = Entrada.cadena();
		return nombre;
	}
	
	public static Profesor leerProfesor() {
		boolean centinela = false;
		Profesor profesor = null;
		do {
			try {
				String nombre = leerNombreProfesor();
				System.out.println("Introduce el correo del profesor");
				String correo = Entrada.cadena();
				System.out.println("Introduzca el numero de télefono del profesor (Pulse Enter si no desea introducirlo");
				String telefono = Entrada.cadena();
				//validamos siguiendo las indicaciones si nos meten solo nombre y correo es valido 
				if (telefono.trim().equals("")) {
					profesor = new Profesor(nombre, correo);
				} else {
					profesor = new Profesor(nombre, correo, telefono);
				}
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} 
		} while (!centinela);
		return profesor;
	}
	
	public static String leerNombreProfesor() {
		System.out.println("introduce el nombre del profesor");
		String nombre = Entrada.cadena();
		return nombre;
	}
	
	public static Profesor leerProfesorFicticio() {
		boolean centinela = false;
		Profesor profesor = null;
		do {
			try {
				System.out.print("Introduzca el correo del profesor: ");
				String correo = Entrada.cadena();
				profesor = Profesor.getProfesorFicticio(correo);
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				// TODO: handle exception
			}
		} while (!centinela);
		return profesor;
	}
	
	public static Tramo leerTramo() {
		int opcion = 0;
		do {
			System.out.println("introduce el tramo horarior");
			System.out.println("1 - " + Tramo.MANANA.toString());
			System.out.println("2 - " + Tramo.TARDE.toString());
			System.out.print("ELECCION: ");
			opcion = Entrada.entero();
		} while (opcion != 1 && opcion != 2);
		if (opcion == 1) {
			return Tramo.MANANA;
		} else {
			return Tramo.TARDE;
		}
	}
	
	public static LocalDate leerDia() {
		boolean centinela = false;
		LocalDate fecha = null;
		do {
			System.out.print("Introduce la fecha con formato dd/mm/yyyy: ");
			String cadena = Entrada.cadena();
			try {
				fecha = LocalDate.parse(cadena, FORMATO_DIA);
				centinela = true;
			} catch (DateTimeParseException e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return fecha;
	}
	
	public static int elegirPermanencia() {
		int opcion = 0;
		do {
			System.out.println("Que tipo de reserva desea hacer:");
			System.out.println("1 - TRAMO COMPLETO");
			System.out.println("2 - TRAMO HORARIO");
			System.out.print("Opción elegida: ");
			opcion = Entrada.entero();
		} while (opcion != 1 && opcion != 2);
		return opcion;
	}
	
	public static Permanencia leerPermanencia() {
		boolean centinela = false;
		if (elegirPermanencia() == 1) {
			PermanenciaPorTramo permanencia = null;
			do {
				try {
					permanencia = new PermanenciaPorTramo(leerDia(), leerTramo());
					centinela = true;
				} catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			} while (!centinela);	
			return permanencia;
		} else {
			PermanenciaPorHora permanencia = null;
			do {
				try {
					permanencia = new PermanenciaPorHora(leerDia(), leerHora());
					centinela = true;
				} catch (NullPointerException | IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			} while (!centinela);	
			return permanencia;
		}
	}
	
	private static LocalTime leerHora() {
		boolean centinela = false;
		LocalTime hora = null;
		do {
			System.out.print("Introduce la hora (Min 8 - Max 22):");
			try {
				hora = LocalTime.of(Entrada.entero(), 0);
				centinela = true;
			} catch (DateTimeException e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return hora;
	}
	
	public static Reserva leerReserva() {
		boolean centinela = false;
		Reserva reserva = null;
		do {
			try {
				reserva = new Reserva(leerProfesorFicticio(), leerAulaFicticia(), leerPermanencia());
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return reserva;
	}
	
	public static Reserva leerReservaFicticia() {
		boolean centinela = false;
		Reserva reserva = null;
		do {
			try {
				reserva = Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());
				centinela = true;
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return reserva;
	}
	
}
