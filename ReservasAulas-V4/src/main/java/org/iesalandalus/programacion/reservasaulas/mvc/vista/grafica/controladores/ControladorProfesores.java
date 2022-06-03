package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorProfesores {
	
	private IControlador controlador;
	
	@FXML private Button btnInsertarProfesor;
	@FXML private Button btnBuscarProfesor;
	@FXML private Button btnBorrarProfesor;
	@FXML private Button btnVolver;
	
	@FXML private TableView<Profesor> tableListarProfesores;
	@FXML private TableColumn<Profesor, String> columNombreProfesor;
	@FXML private TableColumn<Profesor, String> columnEmailProfesor;
	@FXML private TableColumn<Profesor, String> columnTelefonoProfesor;
	
	@FXML private TextField inpInsertarNombreProfesor;
	@FXML private TextField inpInsertarEmailProfesor;
	@FXML private TextField inpInsertarTelefonoProfesor;
	@FXML private TextField inpBuscarNombreProfesor;
	
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}
	
	public void inicializa() {
		actualizarProfesores();
		inpBuscarNombreProfesor.setText("");
		inpInsertarEmailProfesor.setText("");
		inpInsertarNombreProfesor.setText("");
		inpInsertarTelefonoProfesor.setText("");
	}
	
	private void actualizarProfesores() {
		profesores.clear();
		tableListarProfesores.getSelectionModel().clearSelection();
		profesores.setAll(controlador.getProfesores());
	}

	@FXML
	private void initialize() {
		columNombreProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		columnEmailProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		columnTelefonoProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
		tableListarProfesores.setItems(profesores);
	}
	
	@FXML
	void buscar() {
		String correo = inpBuscarNombreProfesor.getText().trim();
		try {
			Profesor profesor = controlador.buscarProfesor(new Profesor("AS", correo));
			if (profesor == null) {
				Dialogos.mostrarDialogoInformacion("BUSQUEDA", "No hay ningún profesor con ese nombre");
			} else {
				tableListarProfesores.getSelectionModel().select(profesor);
				inpBuscarNombreProfesor.setText("");
				tableListarProfesores.scrollTo(profesor);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
		
	}
	
	@FXML
	void insertar() {
		try  {
			Profesor profesor = null;
			if (inpInsertarTelefonoProfesor.getText().trim().equals("")) {
				profesor = new Profesor(inpInsertarNombreProfesor.getText(), inpInsertarEmailProfesor.getText());
			} else {
				profesor = new Profesor(inpInsertarNombreProfesor.getText(), inpInsertarEmailProfesor.getText(), inpInsertarTelefonoProfesor.getText());
			}
			controlador.insertarProfesor(profesor);
			actualizarProfesores();
			Dialogos.mostrarDialogoInformacion("INSERCIÓN", "Inserción realizada con éxito");
			inpInsertarEmailProfesor.setText("");
			inpInsertarNombreProfesor.setText("");
			inpInsertarTelefonoProfesor.setText("");
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void borrar() {
		Profesor profesor = null;
		try  {
			profesor = tableListarProfesores.getSelectionModel().getSelectedItem(); 
			if (profesor != null) {
				controlador.borrarProfesor(profesor);
				actualizarProfesores();
				Dialogos.mostrarDialogoInformacion("BORRADO", "Borrado realizado con éxito");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void salir() {
		((Stage) btnVolver.getScene().getWindow()).close();
	}
}
