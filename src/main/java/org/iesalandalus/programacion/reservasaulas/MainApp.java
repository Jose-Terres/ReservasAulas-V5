package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class MainApp {

  public static void main(String[] args) {
    IModelo modelo = new Modelo(procesarArgumentosDatos(args));
    IVista vista = procesarArgumentosVista(args);
    IControlador controlador = new Controlador(modelo, vista);
    controlador.comenzar();
  }

  private static IVista procesarArgumentosVista(String[] args) {
    IVista vista = FactoriaVista.GRAFICA.crear();
    for (String argumento : args) {
      if (argumento.equalsIgnoreCase("-vgrafica") ) {
        vista = FactoriaVista.GRAFICA.crear();
      } else if (argumento.equalsIgnoreCase("-vconsola")) {
        vista = FactoriaVista.TEXTO.crear();
      }
    }
    return vista;
  }
  
  private static IFuenteDatos procesarArgumentosDatos(String[] args) {
    IFuenteDatos fuenteDatos = FactoriaFuenteDatos.FICHEROS.crear();
    for (String argumento : args) {
      if (argumento.equalsIgnoreCase("-fdmemoria")) {
        fuenteDatos = FactoriaFuenteDatos.MEMORIA.crear();
      } else if (argumento.equalsIgnoreCase("-fdficheros")) {
        fuenteDatos = FactoriaFuenteDatos.FICHEROS.crear();
      } else if (argumento.equalsIgnoreCase("-fdmongo")) {
        fuenteDatos = FactoriaFuenteDatos.MONGODB.crear();
      }
    }
    return fuenteDatos;
  }
}
