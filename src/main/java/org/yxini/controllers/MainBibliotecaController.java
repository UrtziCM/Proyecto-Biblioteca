package org.yxini.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.yxini.controllers.db.ConnectionDB;
import org.yxini.controllers.db.DBManager;
import org.yxini.models.Alumno;
import org.yxini.models.Devolucion;
import org.yxini.models.Libro;
import org.yxini.models.Prestamo;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainBibliotecaController implements Initializable {

    @FXML
    private TextField autorLibros;

    @FXML
    private CheckBox bajaLibros;

    @FXML
    private TextField dniAlumnos;

    @FXML
    private ComboBox<String> dniPrestamos;

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
    private ComboBox<String> estadoDevolucion;

    @FXML
    private TextField nombreAlumnos;

    @FXML
    private TextField primerAlumnos;

    @FXML
    private TextField segundoAlumnos;

    @FXML
    private TableView<Alumno> tablaAlumnos;

    @FXML
    private TableView<Devolucion> tablaHistorico;
    @FXML
    private TableView<Libro> tablaPrestamos;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TextField tituloLibros;

    @FXML
    private DatePicker devolverLibroDate;
    @FXML
    private TableView<Prestamo> tablaDevoluciones;

    private final DBManager gestor = new DBManager();


    /**
     * Abre un informe de jasper reports.
     * @param event Evento que se realiza al clicar en la opcion 1 del menu archivo.
     */
    @FXML
    void abrirInformeDos(ActionEvent event) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/reporte2.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, null, new ConnectionDB().getConexion());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Ha ocurrido un error");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Abre un informe de jasper reports.
     * @param event Evento que se realiza al clicar en la opcion 2 del menu archivo.
     */
    @FXML
    void abrirInforme3(ActionEvent event) {
        mostrarVentanaEmergente("Funcion en desarrollo", "Esta funcion está todavía en desarrollo, se implementará en la siguiente actualizacion.", AlertType.INFORMATION);
    }

    /**
     * Abre un informe de jasper reports.
     * @param event Evento que se realiza al clicar en la opcion 3 del menu archivo.
     */
    @FXML
    void abrirInforme4(ActionEvent event) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/reporte4.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, null, new ConnectionDB().getConexion());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Ha ocurrido un error");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Añade un alumno a la base de datos usando los parámetros de lso campos anteriores.
     * @param event Evento realizado al clickar el boton alñadir en alumnos.
     */
    @FXML
    void anadirAlumno(ActionEvent event) {
        Alumno alumnoNuevo = new Alumno(dniAlumnos.getText(), nombreAlumnos.getText(), primerAlumnos.getText(), segundoAlumnos.getText());
        try {
            if (gestor.existsAlumno(alumnoNuevo)) {
                mostrarVentanaEmergente("DNI duplicado", "Ya existe un alumno con ese DNI", AlertType.ERROR);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gestor.addAlumno(alumnoNuevo);

        tablaAlumnos.setItems(gestor.cargarAlumnos());
        limpiarCampos();
    }
    /**
     * Añade un prestamo a la base de datos usando los parámetros de lso campos anteriores.
     * @param event Evento realizado al clickar el boton prestar en prestamos.
     */
    @FXML
    void anadirPrestamo(ActionEvent event) throws SQLException {
        String selectedDni = dniPrestamos.getSelectionModel().getSelectedItem();
        Libro selectedBook = libroPrestamos.getSelectionModel().getSelectedItem();
        int codigoLibro = gestor.getLibroByName(selectedBook.getTitulo()).getCodigo();
        selectedBook.setCodigoLibro(codigoLibro);
        Prestamo p = new Prestamo(selectedDni, selectedBook.getCodigo(), new Date(fechaPrestamos.getValue().toEpochDay() * 24 * 3600 * 1000));
        selectedBook.setDeBaja(true);
        gestor.modificarLibro(selectedBook, selectedBook);
        gestor.addPrestamo(p);
        selectPrestamos(null);

        HashMap<String, Object> params = new HashMap<>();
        Alumno alumnoPrestamo = gestor.getAlumnoByID(dniPrestamos.getSelectionModel().getSelectedItem());

        params.put("nombre", alumnoPrestamo.getNombre());
        params.put("apellido1", alumnoPrestamo.getApellido1());
        params.put("apellido2", alumnoPrestamo.getApellido2());
        params.put("dni", dniPrestamos.getSelectionModel().getSelectedItem());
        params.put("codigo", selectedBook.getCodigo());
        params.put("titulo", selectedBook.getTitulo());
        params.put("autor", selectedBook.getAutor());
        params.put("editor", selectedBook.getEditorial());
        params.put("estado", selectedBook.getEstado());
        params.put("fechaEntrega", new Date(fechaPrestamos.getValue().toEpochDay() * 24 * 3600 * 1000));
        LocalDate postDate = fechaPrestamos.getValue().plusDays(15);
        params.put("fechaPrestamo", new Date(postDate.toEpochDay() * 24 * 3600 * 1000));
        abrirReporte1(params);
        limpiarCampos();
    }

    /**
     * Abre un informe de jasper reports.
     * @param params Parámetros del infrome que contienen todos los datos de un alumno, los datos del libro y las fechas de prestamo y entrega.
     */
    private void abrirReporte1(HashMap<String, Object> params) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/reporte1.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("ERROR");
            alert.setContentText("Ha ocurrido un error");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Añade un libro a la base de datos.
     * @param event Evento generado al clicar en la opcion añadir en la pestaña libros
     */
    @FXML
    void anadirLibros(ActionEvent event) {
        gestor.addLibro(new Libro(tituloLibros.getText(), autorLibros.getText(), editorialLibros.getText(), estadoLibros.getSelectionModel().getSelectedItem(), bajaLibros.isSelected()));
        tablaLibros.setItems(gestor.cargarLibros());
        limpiarCampos();
    }

    /**
     * Borra un alumno de la base de datos.
     * @param event Evento generado al clickar el boton borrar en la pestaña alumnos.
     */
    @FXML
    void borrarAlumno(ActionEvent event) {
        Alumno alumnoABorrar = tablaAlumnos.getSelectionModel().getSelectedItem();
        if (alumnoABorrar != null) {
            try {
                gestor.borrarAlumno(alumnoABorrar);
            } catch (SQLException e) {
                mostrarVentanaEmergente("Error", "No se puede borrar un alumno con datos en el registro histórico.", AlertType.ERROR);
            }
        }
        selectAlumnos(null);
        limpiarCampos();
    }
    /**
     * Borra un prestamo de la base de datos.
     * @param event Evento generado al clickar el boton borrar en la pestaña devovler.
     */
    @FXML
    void borrarPrestamo(ActionEvent event) {
        Prestamo prestamoParaBorrar = tablaDevoluciones.getSelectionModel().getSelectedItem();
        if (prestamoParaBorrar != null) {
            gestor.borrarPrestamo(prestamoParaBorrar);
            try {
                Libro libroDeBaja = gestor.getLibroByCode(prestamoParaBorrar.getCodigoLibro());
                libroDeBaja.setDeBaja(false);
                gestor.modificarLibro(libroDeBaja, libroDeBaja);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        selectDevoluciones(null);
    }
    /**
     * Borra un libro de la base de datos.
     * @param event Evento generado al clickar el boton borrar en la pestaña libros.
     */
    @FXML
    void borrarLibros(ActionEvent event) throws SQLException {
        Libro libroABorrar = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroABorrar != null) {
            libroABorrar.setCodigoLibro(gestor.getLibroByName(libroABorrar.getTitulo()).getCodigo());
            gestor.borrarLibro(libroABorrar);
        }
        selectLibros(null);
        limpiarCampos();
    }

    /**
     * Modifica un alumno cambiando los datos a los parámetros en los campos anteriores.
     * @param event Evento generado al clicar en el boton modificar en la pestaña alumnos.
     */
    @FXML
    void modficarAlumno(ActionEvent event) {
        Alumno alumnoAModificar = tablaAlumnos.getSelectionModel().getSelectedItem();
        if (alumnoAModificar == null) return;
        gestor.actualizarAlumno(alumnoAModificar, new Alumno(dniAlumnos.getText(), nombreAlumnos.getText(), primerAlumnos.getText(), segundoAlumnos.getText()));
        selectAlumnos(null);
        limpiarCampos();
    }

    /**
     * Limpia los campos de todos las pestañas para evitar repeticiones de datos.
     */
    private void limpiarCampos() {
        dniAlumnos.setText("");
        nombreAlumnos.setText("");
        primerAlumnos.setText("");
        segundoAlumnos.setText("");
        tituloLibros.setText("");
        autorLibros.setText("");
        editorialLibros.setText("");
        estadoLibros.getSelectionModel().clearSelection();
        dniPrestamos.getSelectionModel().clearSelection();
        libroPrestamos.getSelectionModel().clearSelection();
        fechaPrestamos.setValue(null);
        bajaLibros.setSelected(false);
    }

    /**
     * Modifica un libro con los datos dados anteriormente.
     * @param event Evento generado al clicar en el boton modificar en la pestaña libros.
     */
    @FXML
    void modificarLibros(ActionEvent event) {
        Libro libroAModificar = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroAModificar == null) return;
        try {
            libroAModificar.setCodigoLibro(gestor.getLibroByName(libroAModificar.getTitulo()).getCodigo());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Libro libroModificado = new Libro(libroAModificar.getCodigo(), tituloLibros.getText(), autorLibros.getText(), editorialLibros.getText(), estadoLibros.getSelectionModel().getSelectedItem(), bajaLibros.isSelected());
        gestor.modificarLibro(libroAModificar, libroModificado);
        selectLibros(null);
        limpiarCampos();
    }

    @FXML
    void selectAlumnos(Event event) {
        tablaAlumnos.setItems(gestor.cargarAlumnos());
    }

    @FXML
    void selectDevoluciones(Event event) {
        tablaDevoluciones.setItems(gestor.cargarPrestamosActivos());
    }

    @FXML
    void selectHistorico(Event event) {
        tablaHistorico.setItems(gestor.cargarHistorico());
    }

    @FXML
    void selectLibros(Event event) {
        tablaLibros.setItems(gestor.cargarLibros());
    }

    @FXML
    void selectPrestamos(Event event) {
        dniPrestamos.setItems(gestor.getDNIs());
        libroPrestamos.setItems(gestor.cargarLibros());
        tablaPrestamos.setItems(gestor.cargarLibrosSinBaja());
    }

    @FXML
    void filterSelected(ActionEvent event) {
        String state = ((ComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString();
        switch (state) {
            case "Restaurados":
                state = "Restaurado";
                break;
            case "Nuevos":
                state = "Nuevo";
                break;
            case "Usados nuevos":
                state = "Usado nuevo";
                break;
            case "Usados seminuevos":
                state = "Usado seminuevo";
                break;
            case "Usados estropeados":
                state = "Usado estropeado";
                break;
        }
        tablaHistorico.setItems(gestor.getPrestamosByState(state));
    }

    @FXML
    void devolverLibro(ActionEvent event) throws SQLException {
        if (tablaDevoluciones.getSelectionModel().getSelectedItem() == null) {
            mostrarVentanaEmergente("Entrada no seleccionada", "Debe seleccionar una entrada para devolver un libro", AlertType.ERROR);
        } else if (devolverLibroDate.getValue() == null) {
            mostrarVentanaEmergente("Fecha no seleccionada", "El campo fecha está vacío, rellénelo", AlertType.ERROR);
        } else if (estadoDevolucion.getSelectionModel().getSelectedItem().equalsIgnoreCase("estado")) {
            mostrarVentanaEmergente("Estado no seleccionado", "El campo estado está vacío, rellénelo", AlertType.ERROR);
        } else {
            Prestamo prestamo = tablaDevoluciones.getSelectionModel().getSelectedItem();
            prestamo.updateCode(prestamo.getTituloLibro());
            Libro oldLibro = gestor.getLibroByCode(prestamo.getCodigoLibro());
            Libro newLibro = gestor.getLibroByCode(prestamo.getCodigoLibro());
            newLibro.setDeBaja(false);
            newLibro.setEstado(estadoDevolucion.getSelectionModel().getSelectedItem());
            gestor.modificarLibro(oldLibro, newLibro);
            gestor.borrarPrestamo(prestamo);
            gestor.addFinPrestamo(new Devolucion(prestamo.getDniAlumno(), prestamo.getCodigoLibro(), prestamo.getFechaPrestamo(), new Date(devolverLibroDate.getValue().toEpochDay() * 24 * 3600 * 1000)));
            selectDevoluciones(null);
        }
    }

    /**
     * Metodo que se ejecuta al iniciar el controlador.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareHistoricoFilters();

        prepareTablesForDataHandling();
        ObservableList<String> estadosList = FXCollections.observableArrayList();
        estadosList.addAll("Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado");
        estadoLibros.setItems(estadosList);
        estadoDevolucion.setItems(estadosList);
    }

    private void prepareHistoricoFilters() {
        ObservableList<String> filtros = FXCollections.observableArrayList();
        filtros.addAll("Restaurados", "Nuevos", "Usados nuevos", "Usados seminuevos", "Usados estropeados");
        filtroHistorico.setItems(filtros);
    }


    @FXML
    void populateFieldsAlumno(MouseEvent event) {
        Alumno clickedAlumno = tablaAlumnos.getSelectionModel().getSelectedItem();
        if (clickedAlumno == null) return;
        dniAlumnos.setText(clickedAlumno.getDni());
        nombreAlumnos.setText(clickedAlumno.getNombre());
        primerAlumnos.setText(clickedAlumno.getApellido1());
        segundoAlumnos.setText(clickedAlumno.getApellido2());
    }

    @FXML
    void populateFieldsLibro(MouseEvent event) {
        Libro clickedLibro = tablaLibros.getSelectionModel().getSelectedItem();
        if (clickedLibro == null) return;
        tituloLibros.setText(clickedLibro.getTitulo());
        autorLibros.setText(clickedLibro.getAutor());
        editorialLibros.setText(clickedLibro.getEditorial());
        estadoLibros.getSelectionModel().select(clickedLibro.getEstado());
        bajaLibros.setSelected(clickedLibro.isDeBaja());
    }

    @FXML
    void populateFieldsPrestamo(MouseEvent event) {
        Libro clickedPrestamo = tablaPrestamos.getSelectionModel().getSelectedItem();
        if (clickedPrestamo == null) return;
        libroPrestamos.getSelectionModel().select(clickedPrestamo);

    }

    @FXML
    void setToday(ActionEvent event) {
        fechaPrestamos.setValue(LocalDate.now());
    }

    /**
     * Añade PropertyValueFactory's a todas las tablas para poder visualizar los datos.
     */
    private void prepareTablesForDataHandling() {
        for (TableColumn<Alumno, ?> tc : tablaAlumnos.getColumns()) {
            if (!tc.getText().contains(" "))
                tc.setCellValueFactory(new PropertyValueFactory<>(tc.getText().toLowerCase()));
            else if (tc.getText().equalsIgnoreCase("primer apellido")) {
                tc.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
            } else {
                tc.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
            }

        }
        for (TableColumn<Libro, ?> tc : tablaLibros.getColumns()) {
            switch (tc.getText()) {
                case "Título":
                    tc.setCellValueFactory(new PropertyValueFactory<>("titulo"));
                    break;
                case "Autor":
                    tc.setCellValueFactory(new PropertyValueFactory<>("autor"));
                    break;
                case "Editorial":
                    tc.setCellValueFactory(new PropertyValueFactory<>("editorial"));
                    break;
                case "Estado":
                    tc.setCellValueFactory(new PropertyValueFactory<>("estado"));
                    break;
                case "Baja":
                    tc.setCellValueFactory(new PropertyValueFactory<>("deBaja"));
                    break;
            }

        }
        for (TableColumn<Libro, ?> tc : tablaPrestamos.getColumns()) {
            switch (tc.getText()) {
                case "Título":
                    tc.setCellValueFactory(new PropertyValueFactory<>("titulo"));
                    break;
                case "Autor":
                    tc.setCellValueFactory(new PropertyValueFactory<>("autor"));
                    break;
                case "Editorial":
                    tc.setCellValueFactory(new PropertyValueFactory<>("editorial"));
                    break;
                case "Estado":
                    tc.setCellValueFactory(new PropertyValueFactory<>("estado"));
                    break;
            }

        }

        for (TableColumn<Prestamo, ?> tc : tablaDevoluciones.getColumns()) {
            switch (tc.getText()) {
                case "DNI":
                    tc.setCellValueFactory(new PropertyValueFactory<>("dniAlumno"));
                    break;
                case "Libro":
                    tc.setCellValueFactory(new PropertyValueFactory<>("tituloLibro"));
                    break;
                case "Fecha préstamo":
                    tc.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
                    break;
            }

        }
        for (TableColumn<Devolucion, ?> tc : tablaHistorico.getColumns()) {
            switch (tc.getText()) {
                case "DNI":
                    tc.setCellValueFactory(new PropertyValueFactory<>("dniAlumno"));
                    break;
                case "Libro":
                    tc.setCellValueFactory(new PropertyValueFactory<>("tituloLibro"));
                    break;
                case "Fecha préstamo":
                    tc.setCellValueFactory(new PropertyValueFactory<>("fechaPrestamo"));
                    break;
                case "Fecha devolución":
                    tc.setCellValueFactory(new PropertyValueFactory<>("fechaDevolucion"));
                    break;
            }

        }


    }

    /**
     * Muestra una ventana emergente con los datos pasados como parametros.
     * @param titulo El titulo de la ventana emergente
     * @param content El texto de informacion en al ventana
     * @param tipo El tipo de ventana emergente.
     * @see AlertType
     */
    private static void mostrarVentanaEmergente(String titulo, String content, AlertType tipo) {
        Alert anadidoAnimal = new Alert(tipo);
        anadidoAnimal.setTitle(titulo);
        anadidoAnimal.setHeaderText(null);
        anadidoAnimal.setContentText(content);
        anadidoAnimal.showAndWait();
    }
}
