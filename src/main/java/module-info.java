module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jasperreports;
    requires java.sql;
    opens org.example to javafx.fxml;
    exports org.example;
}