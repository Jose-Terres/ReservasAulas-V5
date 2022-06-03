package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.consola.VistaConsola;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.VistaGrafica;

public enum FactoriaVista {
	
	TEXTO{
		
		@Override
		public IVista crear () {
			return new VistaConsola();
		}
	},
	
	GRAFICA{
		
		@Override
		public IVista crear () {
			return new VistaGrafica();
		}
	};
	
	public abstract IVista crear ();
}
