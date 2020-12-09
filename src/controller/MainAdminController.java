package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainAdminController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton bt_cashier;

	@FXML
	private JFXButton bt_report;

	@FXML
	private JFXButton bt_popular;

	@FXML
	private JFXButton bt_chart;

	@FXML
	private JFXButton bt_product;
	
	 @FXML
	 private JFXButton bt_card;

	@FXML
	private JFXButton bt_promotion;

	@FXML
	private JFXButton bt_category;

	@FXML
	private JFXButton bt_customer;

	@FXML
	private JFXButton bt_logout;

	@FXML
	private AnchorPane common_pane;
	
	@FXML
    private JFXButton bt_supplier;







	@FXML
	void initialize() {
		assert bt_cashier != null : Messages.getString("MainAdminController.0"); //$NON-NLS-1$
		assert bt_report != null : Messages.getString("MainAdminController.1"); //$NON-NLS-1$
		assert bt_popular != null : Messages.getString("MainAdminController.2"); //$NON-NLS-1$
		assert bt_chart != null : Messages.getString("MainAdminController.3"); //$NON-NLS-1$
		assert bt_product != null : Messages.getString("MainAdminController.4"); //$NON-NLS-1$
		assert bt_promotion != null : Messages.getString("MainAdminController.5"); //$NON-NLS-1$
		assert bt_category != null : Messages.getString("MainAdminController.6"); //$NON-NLS-1$
		assert bt_customer != null : Messages.getString("MainAdminController.7"); //$NON-NLS-1$
		assert bt_logout != null : Messages.getString("MainAdminController.8"); //$NON-NLS-1$
		assert common_pane != null : Messages.getString("MainAdminController.9"); //$NON-NLS-1$
		assert bt_supplier != null : Messages.getString("MainAdminController.10"); //$NON-NLS-1$
		assert bt_card != null : Messages.getString("MainAdminController.11"); //$NON-NLS-1$
        

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.12"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_product.setDisable(true);
	}

	// for screen transaction from login to admin panel
	public class LoginPg extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.13"))); //$NON-NLS-1$

			Scene scene = new Scene(root, 1320, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle(Messages.getString("MainAdminController.14")); //$NON-NLS-1$
			//primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.show();
		}
	}

	@FXML
	void onCashierAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.15"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_cashier.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_product.setDisable(false);
		bt_promotion.setDisable(false);
		bt_category.setDisable(false);
		bt_customer.setDisable(false);
		bt_logout.setDisable(false);
		bt_supplier.setDisable(false);
		bt_card.setDisable(false);
	}
	
	@FXML
	void onProductAction(ActionEvent event) {
		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.16"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_product.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_promotion.setDisable(false);
		bt_category.setDisable(false);
		bt_customer.setDisable(false);
		bt_logout.setDisable(false);
		bt_supplier.setDisable(false);
		bt_card.setDisable(false);
	}
	
	@FXML
	void onCategoryAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.17"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_category.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_promotion.setDisable(false);
		bt_product.setDisable(false);
		bt_customer.setDisable(false);
		bt_logout.setDisable(false);
		bt_supplier.setDisable(false);
		bt_card.setDisable(false);
	}
	
	@FXML
	void onCustomerAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.18"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_customer.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_promotion.setDisable(false);
		bt_product.setDisable(false);
		bt_category.setDisable(false);
		bt_logout.setDisable(false);
		bt_supplier.setDisable(false);
		bt_card.setDisable(false);
	}
	
	@FXML
	void onPromotionAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.19"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_promotion.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_customer.setDisable(false);
		bt_product.setDisable(false);
		bt_category.setDisable(false);
		bt_logout.setDisable(false);
		bt_supplier.setDisable(false);
		bt_card.setDisable(false);
		
	}
	
	@FXML
    void onSupplierAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.20"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_supplier.setDisable(true);
		bt_report.setDisable(false);
		bt_popular.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_customer.setDisable(false);
		bt_product.setDisable(false);
		bt_promotion.setDisable(false);
		bt_category.setDisable(false);
		bt_logout.setDisable(false);
		bt_card.setDisable(false);
    }
	
	@FXML
	void onPopularAction(ActionEvent event) {

		AnchorPane pane = null;
		try {
			pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.21"))); //$NON-NLS-1$
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		common_pane.getChildren().clear();
		common_pane.getChildren().add(pane);

		// bt_product.setStyle("-fx-background-color : #6e6e6e");
		bt_popular.setDisable(true);
		bt_report.setDisable(false);
		bt_supplier.setDisable(false);
		bt_chart.setDisable(false);
		bt_cashier.setDisable(false);
		bt_customer.setDisable(false);
		bt_product.setDisable(false);
		bt_promotion.setDisable(false);
		bt_category.setDisable(false);
		bt_logout.setDisable(false);
		bt_card.setDisable(false);
	}

	

	   @FXML
	    void onManageCardAction(ActionEvent event) {

		   AnchorPane pane = null;
			try {
				pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.22"))); //$NON-NLS-1$
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			common_pane.getChildren().clear();
			common_pane.getChildren().add(pane);

			// bt_product.setStyle("-fx-background-color : #6e6e6e");
			bt_card.setDisable(true);
			bt_report.setDisable(false);
			bt_supplier.setDisable(false);
			bt_chart.setDisable(false);
			bt_cashier.setDisable(false);
			bt_customer.setDisable(false);
			bt_product.setDisable(false);
			bt_promotion.setDisable(false);
			bt_category.setDisable(false);
			bt_logout.setDisable(false);
			bt_popular.setDisable(false);
	    }
	   
		@FXML
		void onReportAction(ActionEvent event) {

			AnchorPane pane = null;
			try {
				pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.23"))); //$NON-NLS-1$
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			common_pane.getChildren().clear();
			common_pane.getChildren().add(pane);

			// bt_product.setStyle("-fx-background-color : #6e6e6e");
			bt_report.setDisable(true);
			bt_card.setDisable(false);
			bt_supplier.setDisable(false);
			bt_chart.setDisable(false);
			bt_cashier.setDisable(false);
			bt_customer.setDisable(false);
			bt_product.setDisable(false);
			bt_promotion.setDisable(false);
			bt_category.setDisable(false);
			bt_logout.setDisable(false);
			bt_popular.setDisable(false);
		}
		

		@FXML
		void onChartAction(ActionEvent event) {
			
			AnchorPane pane = null;
			try {
				pane = FXMLLoader.load(getClass().getResource(Messages.getString("MainAdminController.24"))); //$NON-NLS-1$
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			common_pane.getChildren().clear();
			common_pane.getChildren().add(pane);

			// bt_product.setStyle("-fx-background-color : #6e6e6e");
			bt_chart.setDisable(true);
			bt_card.setDisable(false);
			bt_supplier.setDisable(false);
			bt_report.setDisable(false);
			bt_cashier.setDisable(false);
			bt_customer.setDisable(false);
			bt_product.setDisable(false);
			bt_promotion.setDisable(false);
			bt_category.setDisable(false);
			bt_logout.setDisable(false);
			bt_popular.setDisable(false);

		}
		
		@FXML
		void onLogoutAction(ActionEvent event) {
			// scene transaction
			try {
				new LoginPg().start((Stage) bt_logout.getScene().getWindow());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
