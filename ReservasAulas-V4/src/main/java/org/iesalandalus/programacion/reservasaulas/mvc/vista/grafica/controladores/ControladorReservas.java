package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControladorReservas {
	
	private IControlador controlador;

	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	private ObservableList<String> profesores = FXCollections.observableArrayList();
	private ObservableList<String> aulas = FXCollections.observableArrayList();
	
	@FXML private Button btnInsertarReserva;
	@FXML private Button btnListarReservas;
	@FXML private Button btnBorrarReserva;
	@FXML private Button btnVolverReservas;
	@FXML private Button btnListarReservaAula;
	@FXML private Button btnListarReservaProfesor;
	@FXML private Button btnDisponibilidad;
	
	@FXML private ChoiceBox<String> dpBuscarAula;
	@FXML private ChoiceBox<String> dpAulaInsertar;
	@FXML private ChoiceBox<String> dpBuscarProfesor;
	@FXML private ChoiceBox<String> dpProfesorInsertar;
	@FXML private ChoiceBox<String> dpTramo;
	@FXML private DatePicker dpFecha;
	
	@FXML private TableView<Reserva> tableListarReservas;
	@FXML private TableColumn<Reserva, String> columnProfesorReservas;
	@FXML private TableColumn<Reserva, String> columnAulaReservas;
	@FXML private TableColumn<Reserva, String> columnFechaReservas;
	
	@FXML
	private void initialize() {
		columnProfesorReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().toString()));
		columnAulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().toString()));
		columnFechaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		tableListarReservas.setItems(reservas);
	}
	
	public void actualizarReservas() {
		reservas.clear();
		tableListarReservas.getSelectionModel().clearSelection();
		reservas.setAll(controlador.getReservas());
	}
	
	public void actualizarProfesores() {
		profesores.clear();
		List<Profesor> profes = controlador.getProfesores();
		for (Profesor profe : profes) {
			profesores.add(profe.getCorreo());
		}
	}
	
	public void actualizarAulas() {
		aulas.clear();
		List<Aula> aulas = controlador.getAulas();
		for (Aula aula : aulas) {
			this.aulas.add(aula.getNombre());
		}
	}
	
	public void setControlador(IControlador contro) {
		this.controlador = contro;
	}
	
	public void inicializa() {
		actualizarReservas();
		actualizarAulas();
		actualizarProfesores();
		dpFecha.setValue(LocalDate.now());
		dpTramo.setItems(FXCollections.observableArrayList("MAÑANA","TARDE","08:00","09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"));
		dpAulaInsertar.setItems(aulas);
		dpBuscarAula.setItems(aulas);
		dpProfesorInsertar.setItems(profesores);
		dpBuscarProfesor.setItems(profesores);
		
		dpBuscarAula.getSelectionModel().selectFirst();
		dpAulaInsertar.getSelectionModel().selectFirst();
		dpBuscarProfesor.getSelectionModel().selectFirst();
		dpProfesorInsertar.getSelectionModel().selectFirst();
		dpTramo.getSelectionModel().selectFirst();
	}
	
	@FXML
	void insertar() {
		String correoProfesor = dpProfesorInsertar.getValue();
		String nombreAula = dpAulaInsertar.getValue();
		LocalDate fecha = dpFecha.getValue();
		String tramo = dpTramo.getValue();
		Permanencia permanencia = null;
		try {
			Profesor profesor = controlador.buscarProfesor(new Profesor("Jose", correoProfesor));
			Aula aula = controlador.buscarAula(new Aula(nombreAula, 25));
			if (tramo == "MAÑANA") {
				permanencia = new PermanenciaPorTramo(fecha, Tramo.MANANA);
			} else if (tramo == "TARDE") {
				permanencia = new PermanenciaPorTramo(fecha, Tramo.TARDE);
			} else {
				permanencia = new PermanenciaPorHora(fecha, LocalTime.parse(tramo + ":00"));
			}
			controlador.realizarReserva(new Reserva(profesor, aula, permanencia));
			Dialogos.mostrarDialogoInformacion("RESERVA REALIZADA", "La reserva ha sido realizada con éxito");
			listarReservas();
	 	} catch (Exception e) {
	 		Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void listarReservas(){
		reservas.clear();
		tableListarReservas.getSelectionModel().clearSelection();
		reservas.setAll(controlador.getReservas());
	}
	
	@FXML
	void listarAula(){
		String nombreAula = dpBuscarAula.getValue();
		try {
			Aula aula = controlador.buscarAula(new Aula(nombreAula, 25));
			reservas.clear();
			tableListarReservas.getSelectionModel().clearSelection();
			reservas.setAll(controlador.getReservasAula(aula));
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void disponibilidad(){
		String nombreAula = dpAulaInsertar.getValue();
		LocalDate fecha = dpFecha.getValue();
		String tramo = dpTramo.getValue();
		Permanencia permanencia = null;
		try {
			Aula aula = controlador.buscarAula(new Aula(nombreAula, 25));
			if (tramo == "MAÑANA") {
				permanencia = new PermanenciaPorTramo(fecha, Tramo.MANANA);
			} else if (tramo == "TARDE") {
				permanencia = new PermanenciaPorTramo(fecha, Tramo.TARDE);
			} else {
				permanencia = new PermanenciaPorHora(fecha, LocalTime.parse(tramo + ":00"));
			}
			if (controlador.consultarDisponibilidad(aula, permanencia)) {
				Dialogos.mostrarDialogoInformacion("AULA DISPONIBLE", "El aula consultada esta disponible");
			} else {
				Dialogos.mostrarDialogoInformacion("AULA NO DISPONIBLE", "El aula consultada no esta disponible");
			}
	 	} catch (Exception e) {
	 		Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void listarProfesor(){
		String correoProfesor = dpBuscarProfesor.getValue();
		try {
			Profesor profesor = controlador.buscarProfesor(new Profesor("Jose", correoProfesor));
			reservas.clear();
			tableListarReservas.getSelectionModel().clearSelection();
			reservas.setAll(controlador.getReservasProfesor(profesor));
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void borrar() {
		Reserva reserva = null;
		try  {
			reserva = tableListarReservas.getSelectionModel().getSelectedItem(); 
			if (reserva != null) {
				controlador.anularReserva(reserva);
				listarReservas();
				Dialogos.mostrarDialogoInformacion("BORRADO", "Borrado realizado con éxito");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void volver() {
		((Stage) btnVolverReservas.getScene().getWindow()).close();
	}
}



	
	