package org.yxini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;

/**
 * Clase principal de la aplicacion.
 */
public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        setRoot();
    }

    /**
     * Establece la raíz de la ventana principal de la aplicacion.
     * @throws IOException Si no se ha podido establecer la ventana principal.
     */
    static void setRoot() throws IOException {
        Scene scene = new Scene(new FXMLLoader(MainApp.class.getResource("/fxml/biblioteca.fxml")).load());
        stage.setTitle("Gestion de biblioteca");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
