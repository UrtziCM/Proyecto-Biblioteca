package org.yxini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;


public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        setRoot();
    }

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
