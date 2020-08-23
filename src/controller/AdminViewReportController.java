package controller;

import com.jfoenix.controls.JFXButton;

import functs.ReportGenerator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.JRException;

public class AdminViewReportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton bt_daily_re;

    @FXML
    private JFXButton bt_month_re;

    @FXML
    private JFXButton bt_popu_re;

    
    @FXML
    void initialize() {
        
        assert bt_daily_re != null : "fx:id=\"bt_daily_re\" was not injected: check your FXML file 'Admin_view_report.fxml'.";
        assert bt_month_re != null : "fx:id=\"bt_month_re\" was not injected: check your FXML file 'Admin_view_report.fxml'.";
        assert bt_popu_re != null : "fx:id=\"bt_popu_re\" was not injected: check your FXML file 'Admin_view_report.fxml'.";

    }
    
    @FXML
    void onDailyAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException {

    	new ReportGenerator().generateDailyReport();
    }

    @FXML
    void onMonthlyAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, SQLException {
    	new ReportGenerator().generateMonthlyReport();
    	
    }

    @FXML
    void onPopularAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, JRException, SQLException {

    	new ReportGenerator().generatePopularItem();
    }


}
