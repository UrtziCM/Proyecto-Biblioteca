package org.example;
/*
Put header here


 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import javafx.scene.control.Alert;


import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLController implements Initializable {
    
    @FXML
    private Label lblOut;
    
    @FXML
    private void btnClickAction(ActionEvent event) {

        try {

            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource("/informes/informe.jasper"));
            JasperPrint jprint = JasperFillManager.fillReport(report, null, new JREmptyDataSource());
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
        lblOut.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
