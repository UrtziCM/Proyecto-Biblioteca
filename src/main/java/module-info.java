module org.yxini {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jasperreports;
    requires java.sql;

    opens org.yxini.models to javafx.base;
    opens org.yxini to javafx.fxml;
    opens org.yxini.controllers to javafx.fxml;
    exports org.yxini;
}