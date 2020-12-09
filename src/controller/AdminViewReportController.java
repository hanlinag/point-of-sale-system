package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import functs.ReportGenerator;
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
        
        assert bt_daily_re != null : Messages.getString("AdminViewReportController.0"); //$NON-NLS-1$
        assert bt_month_re != null : Messages.getString("AdminViewReportController.1"); //$NON-NLS-1$
        assert bt_popu_re != null : Messages.getString("AdminViewReportController.2"); //$NON-NLS-1$

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
