package org.yxini.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.yxini.models.Alumno;
import org.yxini.models.Libro;
import org.yxini.models.Prestamo;

public class MainBibliotecaController {

    @FXML
    private TextField autorLibros;

    @FXML
    private CheckBox bajaLibros;

    @FXML
    private HBox containerHistorico;

    @FXML
    private DatePicker devolPrestamos;

    @FXML
    private TextField dniAlumnos;

    @FXML
    private ComboBox<?> dniPrestamos;

    @FXML
    private TextField editorialLibros;

    @FXML
    private ComboBox<String> estadoLibros;

    @FXML
    private DatePicker fechaPrestamos;

    @FXML
    private ComboBox<String> filtroHistorico;

    @FXML
    private ComboBox<Libro> libroPrestamos;

    @FXML
    private TextField nombreAlumnos;

    @FXML
    private TextField primerAlumnos;

    @FXML
    private TextField segundoAlumnos;

    @FXML
    private TableView<Alumno> tablaAlumnos;

    @FXML
    private TableView<Prestamo> tablaHistorico;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableView<Prestamo> tablaPrestamos;

    @FXML
    private ComboBox<String> tipoPrestamos;

    @FXML
    private TextField tituloLibros;

    @FXML
    void abrirInforme3(ActionEvent event) {

    }

    @FXML
    void abrirInforme4(ActionEvent event) {

    }

    @FXML
    void abrirInformeDos(ActionEvent event) {

    }

    @FXML
    void anadirAlumno(ActionEvent event) {

    }

    @FXML
    void anadirPrestamo(ActionEvent event) {

    }

    @FXML
    void anadirLibros(ActionEvent event) {

    }

    @FXML
    void borrarAlumno(ActionEvent event) {

    }

    @FXML
    void borrarPrestamo(ActionEvent event) {

    }

    @FXML
    void borrarLibros(ActionEvent event) {

    }

    @FXML
    void changeVerPrestados(ActionEvent event) {

    }

    @FXML
    void modficarAlumno(ActionEvent event) {

    }

    @FXML
    void modificarPrestamo(ActionEvent event) {

    }

    @FXML
    void modificarLibros(ActionEvent event) {

    }

    @FXML
    void selectAlumnos(Event event) {

    }

    @FXML
    void selectHistorico(Event event) {

    }

    @FXML
    void selectLibros(Event event) {

    }

    @FXML
    void selectPrestamos(Event event) {

    }

    @FXML
    void tablaPrestamos(ActionEvent event) {

    }

}
