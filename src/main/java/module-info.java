module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jasperreports;
    requires java.sql;
    opens org.israeldelamo to javafx.fxml;
    exports org.israeldelamo;
}