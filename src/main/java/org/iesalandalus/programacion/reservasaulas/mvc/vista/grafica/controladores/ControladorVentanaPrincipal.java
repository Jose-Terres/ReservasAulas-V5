package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.io.IOException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {
	
	private IControlador controlador;
	
	private ControladorAulas controladorAulas;
	private Stage escenarioAulas = null;
	private ControladorProfesores controladorProfesores;
	private Stage escenarioProfesores = null;
	private ControladorReservas controladorReservas;
	private Stage escenarioReservas = null;
	
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}
	
	public void irPaginaAulas(ActionEvent e) throws IOException {
		if (escenarioAulas == null) {
			escenarioAulas = new Stage();
			FXMLLoader cargardorAulas = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/Aulas.fxml"));
			AnchorPane raizAulas = cargardorAulas.load();
			controladorAulas = cargardorAulas.getController();
			controladorAulas.setControlador(controlador);
			
			Scene escenaAulas = new Scene(raizAulas);
			escenarioAulas.setTitle("Gestión de Aulas");
			escenarioAulas.initModality(Modality.APPLICATION_MODAL);
			escenarioAulas.setScene(escenaAulas);
		}
		controladorAulas.inicializa();
		escenarioAulas.showAndWait();
	}
	
	public void irPaginaProfesores(ActionEvent e) throws IOException {
		if (escenarioProfesores == null) {
			escenarioProfesores = new Stage();
			FXMLLoader cargardorProfesores = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/Profesores.fxml"));
			AnchorPane raizProfesores = cargardorProfesores.load();
			controladorProfesores = cargardorProfesores.getController();
			controladorProfesores.setControlador(controlador);
			
			Scene escenaProfesores = new Scene(raizProfesores);
			escenarioProfesores.setTitle("Gestión de Profesores");
			escenarioProfesores.initModality(Modality.APPLICATION_MODAL);
			escenarioProfesores.setScene(escenaProfesores);
		}
		controladorProfesores.inicializa();
		escenarioProfesores.showAndWait();
	}
	
	public void irPaginaReservas(ActionEvent e) throws IOException {
		if (escenarioReservas == null) {
			escenarioReservas = new Stage();
			FXMLLoader cargardorReservas = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/Reservas.fxml"));
			AnchorPane raizReservas = cargardorReservas.load();
			controladorReservas = cargardorReservas.getController();
			controladorReservas.setControlador(controlador);
			
			Scene escenaReservas = new Scene(raizReservas);
			escenarioReservas.setTitle("Gestión de Reservas");
			escenarioReservas.initModality(Modality.APPLICATION_MODAL);
			escenarioReservas.setScene(escenaReservas);
		}
		controladorReservas.inicializa();
		escenarioReservas.showAndWait();
	}
	
	@FXML
	void acercaDe() throws IOException {
		AnchorPane acerca = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Acerca De", acerca);
	}
	
	@FXML 
	void salir(ActionEvent e) throws Throwable {
		if (Dialogos.mostrarDialogoConfirmacion("SALIR", "¿Desea salir de la aplicación?", escenarioAulas)) {
			controlador.terminar();
			Platform.exit();
		}
	}
	
}
