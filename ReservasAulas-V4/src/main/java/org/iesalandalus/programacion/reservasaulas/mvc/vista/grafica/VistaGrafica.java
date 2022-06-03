package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores.ControladorVentanaPrincipal;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaGrafica extends Application implements IVista{

	private static IControlador controlador;
	
	@Override
	public void setControlador(IControlador contro) {
		controlador = contro;		
	}

	@Override
	public void comenzar() {
		launch(this.getClass());		
		
	}

	@Override
	public void salir() {
		controlador.terminar();
	}
	
	@Override
	  public void start(Stage escenarioPrincipal) {
	    try {
	      FXMLLoader cargadorVentanaPrincipal = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/VentanaPrincipal.fxml"));
	      AnchorPane raiz = cargadorVentanaPrincipal.load();  
	      ControladorVentanaPrincipal cVentanaPrincipal = cargadorVentanaPrincipal.getController();
	      cVentanaPrincipal.setControlador(controlador);
	      
	      Scene escena = new Scene(raiz);
	      escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
	      escenarioPrincipal.setTitle("Reservas Aula - V4");
	      escenarioPrincipal.setScene(escena);
	      escenarioPrincipal.setResizable(false);
	      escenarioPrincipal.show();
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
	  }
	  
	  private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
	    if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
	      controlador.terminar();
	      escenarioPrincipal.close();
	    }
	    else {
	      e.consume();  
	    }
	  }

}
