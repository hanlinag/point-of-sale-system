package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import common.Common;
import database.DBInitialize;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="tf_id"
	private JFXTextField tf_id; // Value injected by FXMLLoader

	@FXML // fx:id="tf_pass"
	private JFXPasswordField tf_pass; // Value injected by FXMLLoader

	@FXML // fx:id="bt_login"
	private JFXButton bt_login; // Value injected by FXMLLoader

	@FXML
	private JFXRadioButton bt_rdo_admin;

	@FXML
	private ToggleGroup usertype;

	@FXML
	private JFXRadioButton bt_rdo_cashier;

	// db
	// private Statement statement;
	private ResultSet resultSet;
	private String dbQuery;

	private String realId;
	private String realPw;

	// strings for data from cashier db
	private String name;
	private int age;
	private String gender;
	private String addr;
	private String ph;
	private String mail;
	private String date;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		assert tf_id != null : Messages.getString("LoginController.0"); //$NON-NLS-1$
		assert tf_pass != null : Messages.getString("LoginController.1"); //$NON-NLS-1$
		assert bt_login != null : Messages.getString("LoginController.2"); //$NON-NLS-1$
		assert bt_rdo_admin != null : Messages.getString("LoginController.3"); //$NON-NLS-1$
		assert usertype != null : Messages.getString("LoginController.4"); //$NON-NLS-1$
		assert bt_rdo_cashier != null : Messages.getString("LoginController.5"); //$NON-NLS-1$

		// database
		new DBInitialize().DBInitialize();
		;

		bt_rdo_cashier.setSelected(true);
	}

	/*
	 * private void dbInitialize() throws SQLException, ClassNotFoundException { //
	 * TODO Auto-generated method stub
	 * 
	 * // Load the JDBC driver Class.forName("com.mysql.cj.jdbc.Driver");
	 * System.out.println("Driver loaded"); // Connect to a database Connection
	 * connection = DriverManager.getConnection
	 * ("jdbc:mysql://localhost:3306/ACPCEpos","root","");
	 * System.out.println("Database connected"); // Create a statement statement =
	 * connection.createStatement(); // Execute a statement
	 * 
	 * 
	 * 
	 * }
	 */

	@FXML
	void bt_login_action(ActionEvent event) throws SQLException {
		// get dat from 2 text field
		try {
			String id = tf_id.getText().toString();
			String pw = tf_pass.getText().toString();

			if (bt_rdo_admin.isSelected()) {
				System.out.println(Messages.getString("LoginController.6")); //$NON-NLS-1$

				realId = Messages.getString("LoginController.7"); // posACPCE //$NON-NLS-1$
				realPw = Messages.getString("LoginController.8");// admin123 //$NON-NLS-1$

				if (id.equals(realId) && pw.equals(realPw)) {

					System.out.println(Messages.getString("LoginController.9")); //$NON-NLS-1$

					// scene transaction
					try {
						new MainAdmin().start((Stage) bt_login.getScene().getWindow());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// System.out.println("Login Fail! User name or password incorrect! because
					// realid is "+ realId+" and id is "+id+" real password is "+ realPw+ " password
					// is "+pw);
					Alert alert = new Alert(AlertType.ERROR, Messages.getString("LoginController.10")); //$NON-NLS-1$
					alert.showAndWait();
					tf_id.clear();
					tf_pass.clear();

					System.out.println(Messages.getString("LoginController.11")); //$NON-NLS-1$
				}

			} // end of admin selected
			else {

				System.out.println(Messages.getString("LoginController.12")); //$NON-NLS-1$
				if (!isInteger(id + Messages.getString("LoginController.13"))) { //$NON-NLS-1$
					tf_id.clear();
					tf_pass.clear();

					System.out.println(Messages.getString("LoginController.14")); //$NON-NLS-1$
					Alert all = new Alert(AlertType.ERROR, Messages.getString("LoginController.15")); //$NON-NLS-1$
					all.showAndWait();

				} else {

					// if(id.equals(int))
					// get user name and password from db
					dbQuery = Messages.getString("LoginController.16") + id + Messages.getString("LoginController.17"); //$NON-NLS-1$ //$NON-NLS-2$
					resultSet = DBInitialize.statement.executeQuery(dbQuery);

					if (resultSet.next()) {
						realId = Messages.getString("LoginController.18") //$NON-NLS-1$
								+ resultSet.getInt(Messages.getString("LoginController.19")); //$NON-NLS-1$
						realPw = resultSet.getString(Messages.getString("LoginController.20")); //$NON-NLS-1$

						name = resultSet.getString(Messages.getString("LoginController.21")); //$NON-NLS-1$
						age = resultSet.getInt(Messages.getString("LoginController.22")); //$NON-NLS-1$
						gender = resultSet.getString(Messages.getString("LoginController.23")); //$NON-NLS-1$
						addr = resultSet.getString(Messages.getString("LoginController.24")); //$NON-NLS-1$
						ph = resultSet.getString(Messages.getString("LoginController.25")); //$NON-NLS-1$
						mail = resultSet.getString(Messages.getString("LoginController.26")); //$NON-NLS-1$
						date = resultSet.getString(Messages.getString("LoginController.27")); //$NON-NLS-1$

						System.out.println(Messages.getString("LoginController.28") + id); //$NON-NLS-1$
						System.out.println(Messages.getString("LoginController.29") + pw); //$NON-NLS-1$

						if (id.equals(realId) && pw.equals(realPw)) {

							// add cashier info to temp
							Common.cashierrec.setId(Messages.getString("LoginController.30") + realId); //$NON-NLS-1$
							Common.cashierrec.setPassword(realPw);
							Common.cashierrec.setName(name);
							Common.cashierrec.setGender(gender);
							Common.cashierrec.setAddress(addr);
							Common.cashierrec.setAge(Messages.getString("LoginController.31") + age); //$NON-NLS-1$
							Common.cashierrec.setPhone(ph);
							Common.cashierrec.setEmail(mail);
							Common.cashierrec.setDateCreated(date);
							System.out.println(Messages.getString("LoginController.32")); //$NON-NLS-1$
							System.out.println(Messages.getString("LoginController.33") + Common.cashierrec.getName()); //$NON-NLS-1$

							// scene transaction
							try {
								new MainCashier().start((Stage) bt_login.getScene().getWindow());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							// System.out.println("Login Fail! User name or password incorrect! because
							// realid is "+ realId+" and id is "+id+" real password is "+ realPw+ " password
							// is "+pw);
							Alert alert = new Alert(AlertType.ERROR, Messages.getString("LoginController.34")); //$NON-NLS-1$
							alert.showAndWait();
							tf_id.clear();
							tf_pass.clear();

							System.out.println(Messages.getString("LoginController.35")); //$NON-NLS-1$
						}

					} // end of rs.next()
					else {
						Alert alert = new Alert(AlertType.ERROR, Messages.getString("LoginController.36")); //$NON-NLS-1$
						alert.showAndWait();
						tf_id.clear();
						tf_pass.clear();

						System.out.println(Messages.getString("LoginController.37")); //$NON-NLS-1$
						// System.out.println("No such user!!");
					}

				}

			} // end of check is Integer
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR, Messages.getString("LoginController.38")); //$NON-NLS-1$
			alert.showAndWait();

			System.out.println(Messages.getString("LoginController.39") + nfe.getMessage()); //$NON-NLS-1$
		}

	}

	// for screen transaction from login to cashier panel
	public class MainCashier extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource(Messages.getString("LoginController.40"))); //$NON-NLS-1$

			Scene scene = new Scene(root, 1320, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle(Messages.getString("LoginController.41")); //$NON-NLS-1$
			// primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(Messages.getString("LoginController.42"))); //$NON-NLS-1$
			primaryStage.setMaximized(false);
			primaryStage.show();
		}
	}

	// for screen transaction from login to admin panel
	public class MainAdmin extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource(Messages.getString("LoginController.43"))); //$NON-NLS-1$

			Scene scene = new Scene(root, 1320, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle(Messages.getString("LoginController.44")); //$NON-NLS-1$
			// primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(Messages.getString("LoginController.45"))); //$NON-NLS-1$
			primaryStage.setMaximized(false);
			primaryStage.show();
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

}
