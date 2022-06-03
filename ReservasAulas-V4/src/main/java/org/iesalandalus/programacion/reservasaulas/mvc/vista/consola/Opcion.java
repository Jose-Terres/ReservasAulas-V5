package org.iesalandalus.programacion.reservasaulas.mvc.vista.consola;

//creamos el enum opcion con sus opciones
public enum Opcion {
// salir llamamos a ejecutar , vista salir
	SALIR("Salir") {
		public void ejecutar() {
			vista.salir();
		}
	},
	// salir llamamos a ejecutar , vista insertarAula
	INSERTAR_AULA("Insertar aula") {
		public void ejecutar() {
			vista.insertarAula();
		}
	},
	// salir llamamos a ejecutar , vista borrarAula
	BORRAR_AULA("Borrar aula") {
		public void ejecutar() {
			vista.borrarAula();
		}
	}, 
	// salir llamamos a ejecutar , vista buscarAula
	BUSCAR_AULA("Buscar aula") {
		public void ejecutar() {
			vista.buscarAula();
		}
	},
	// salir llamamos a ejecutar , vista listarAulas
	LISTAR_AULAS("Listar aulas") {
		public void ejecutar() {
			vista.listarAulas();
		}
	},
	// salir llamamos a ejecutar , vista insertarProfesor
	INSERTAR_PROFESOR("Insertar profesor") {
		public void ejecutar() {
			vista.insertarProfesor();
		}
	},
	BORRAR_PROFESOR("Borrar profesor") {
		public void ejecutar() {
			vista.borrarProfesor();
		}
	},
	BUSCAR_PROFESOR("Buscar profesor") {
		public void ejecutar() {
			vista.buscarProfesor();
		}
	},
	// salir llamamos a ejecutar , vista listarProfesores
	LISTAR_PROFRESORES("Listar profesor") {
		public void ejecutar() {
			vista.listarProfesores();
		}
	},
	INSERTAR_RESERVA("Insertar reserva") {
		public void ejecutar() {
			vista.realizarReserva();
		}
	},
	// salir llamamos a ejecutar , vista anularReserva
	BORRAR_RESERVA("Borrar reserva") {
		public void ejecutar() {
			vista.anularReserva();
		}
	},
	// salir llamamos a ejecutar , vista listarReservas
	LISTAR_RESERVAS("Listar reservas") {
		public void ejecutar() {
			vista.listarReservas();
		}
	},
	// salir llamamos a ejecutar , vista listarReservasAula
	LISTAR_RESERVAS_AULA("Listar reservas por aula") {
		public void ejecutar() {
			vista.listarReservasAula();
		}
	},
	LISTAR_RESERVAS_PROFESOR("Listar reservas por profesor") {
		public void ejecutar() {
			vista.listarReservasProfesor();
		}
	},
	// salir llamamos a ejecutar , vista consultarDIsponibilidad
	CONSULTAR_DISPONIBILIDAD("Consultar disponibilidad") {
		public void ejecutar() {
			vista.consultarDisponibilidad();
		}
	};
	
	
	private String mensaje;
	private static VistaConsola vista;
	
	private Opcion(String opcion) {
		this.mensaje = opcion;
	}
	
	public abstract void ejecutar();
	
	protected static void setVista(VistaConsola vista) {
		Opcion.vista = vista;
	}
	
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensaje);
	}

	public static Opcion getOpcionSegunOrdinal(int opcion) {
		// validamos
		if (esOrdinalValido(opcion))
			return values()[opcion];
		else
			throw new IllegalArgumentException("Ordinal de la opción no válido");
	}
	// cambiamos la longitid a el resultado -1 para que no empiece en 0
	public static boolean esOrdinalValido(int opcion) {
		return opcion <= values().length -1 && opcion >= 0;
	}
}