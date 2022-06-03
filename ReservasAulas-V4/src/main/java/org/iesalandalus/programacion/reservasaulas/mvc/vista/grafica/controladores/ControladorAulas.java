package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.*;

public class ControladorAulas {

	private IControlador controlador;
	
	@FXML private Button btnInsertarAula;
	@FXML private Button btnBuscarAula;
	@FXML private Button btnBorrarAula;
	@FXML private Button btnVolver;
	
	@FXML private TableView<Aula> tableListarAulas;
	@FXML private TableColumn<Aula, String> columnNombreAula;
	@FXML private TableColumn<Aula, String> columnPuestosAula;
	
	@FXML private TextField inpNombreAula;
	@FXML private TextField inpNumeroPuestos;
	@FXML private TextField nombreBuscar;
	
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}
	
	public void inicializa() {
		actualizarAulas();
		inpNombreAula.setText("");
		inpNumeroPuestos.setText("");
		nombreBuscar.setText("");
	}
	
	private void actualizarAulas() {
		aulas.clear();
		tableListarAulas.getSelectionModel().clearSelection();
		aulas.setAll(controlador.getAulas());
	}

	@FXML
	private void initialize() {
		columnNombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		columnPuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(Integer.toString(aula.getValue().getPuestos())));
		tableListarAulas.setItems(aulas);
	}
	
	@FXML
	void buscar() {
		try {
			String nombre = nombreBuscar.getText().trim();
			Aula aula = controlador.buscarAula(new Aula(nombre, 50));
			if (aula == null) {
				Dialogos.mostrarDialogoInformacion("BUSQUEDA", "No hay ningún aula con ese nombre");
			} else {
				tableListarAulas.getSelectionModel().select(aula);
				nombreBuscar.setText("");
				tableListarAulas.scrollTo(aula);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void insertar() {
		try  {
			controlador.insertarAula(new Aula(inpNombreAula.getText().trim(), Integer.parseInt(inpNumeroPuestos.getText())));
			actualizarAulas();
			Dialogos.mostrarDialogoInformacion("INSERCIÓN", "Inserción realizada con éxito");
			inpNombreAula.setText("");
			inpNumeroPuestos.setText("");
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}
	
	@FXML
	void borrar() {
		Aula aula = null;
		try  {
			aula = tableListarAulas.getSelectionModel().getSelectedItem(); 
			if (aula != null) {
				controlador.borrarAula(aula);
				actualizarAulas();
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
