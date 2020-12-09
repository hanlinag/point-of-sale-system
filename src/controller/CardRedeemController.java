package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.CardUser;

public class CardRedeemController {

	@FXML
	private JFXTextField tf_qr_search;

	@FXML
	private JFXTextField tf_customer_name;

	@FXML
	private JFXButton bt_apply;

	@FXML
	private JFXButton bt_cancel;

	@FXML
	private JFXTextField tf_phone;

	@FXML
	private JFXTextField tf_address;

	@FXML
	private JFXTextField tf_email;

	@FXML
	private JFXTextField tf_pin;

	@FXML
	private JFXTextField tf_amount;

	@FXML
	private JFXTextField tf_top_up;

	@FXML
	private JFXButton bt_redeem;

	@FXML
	private JFXTextField tf_age;

	@FXML
	private JFXTextField tf_date_created;

	@FXML
	private JFXTextField tf_expired_date;

	@FXML
	private JFXTextField tf_last_used;

	@FXML
	private JFXRadioButton rdo_male;

	@FXML
	private ToggleGroup gender;

	@FXML
	private JFXRadioButton rdo_female;

	private ServerSocket ss;
	private Socket s;
	private DataInputStream inputFromClient;
	private DataOutputStream outputToClient;

	String datafromandroid;

	private CardUser carduser = new CardUser();

	@FXML
	void initialize() {
		assert tf_qr_search != null : Messages.getString("CardRedeemController.0"); //$NON-NLS-1$
		assert tf_customer_name != null : Messages.getString("CardRedeemController.1"); //$NON-NLS-1$
		assert bt_apply != null : Messages.getString("CardRedeemController.2"); //$NON-NLS-1$
		assert bt_cancel != null : Messages.getString("CardRedeemController.3"); //$NON-NLS-1$
		assert tf_phone != null : Messages.getString("CardRedeemController.4"); //$NON-NLS-1$
		assert tf_address != null : Messages.getString("CardRedeemController.5"); //$NON-NLS-1$
		assert tf_email != null : Messages.getString("CardRedeemController.6"); //$NON-NLS-1$
		assert tf_pin != null : Messages.getString("CardRedeemController.7"); //$NON-NLS-1$
		assert tf_amount != null : Messages.getString("CardRedeemController.8"); //$NON-NLS-1$
		assert tf_top_up != null : Messages.getString("CardRedeemController.9"); //$NON-NLS-1$
		assert bt_redeem != null : Messages.getString("CardRedeemController.10"); //$NON-NLS-1$
		assert tf_age != null : Messages.getString("CardRedeemController.11"); //$NON-NLS-1$
		assert tf_date_created != null : Messages.getString("CardRedeemController.12"); //$NON-NLS-1$
		assert tf_expired_date != null : Messages.getString("CardRedeemController.13"); //$NON-NLS-1$
		assert tf_last_used != null : Messages.getString("CardRedeemController.14"); //$NON-NLS-1$
		assert rdo_male != null : Messages.getString("CardRedeemController.15"); //$NON-NLS-1$
		assert gender != null : Messages.getString("CardRedeemController.16"); //$NON-NLS-1$
		assert rdo_female != null : Messages.getString("CardRedeemController.17"); //$NON-NLS-1$

		rdo_male.setSelected(true);

		if (!tf_customer_name.getText().equals(Messages.getString("CardRedeemController.18"))) { //$NON-NLS-1$
			rdo_male.setOnAction(e -> {
				if (rdo_male.isSelected()) {
					rdo_male.setSelected(true);
					rdo_female.setSelected(false);
				} else {
					rdo_male.setSelected(true);
					rdo_female.setSelected(false);
				}
				carduser.setGender(Messages.getString("CardRedeemController.19")); //$NON-NLS-1$
			});

			rdo_female.setOnAction(e -> {
				if (rdo_male.isSelected()) {
					rdo_male.setSelected(false);
					rdo_female.setSelected(true);
				} else {
					rdo_male.setSelected(false);
					rdo_female.setSelected(true);
				}
				carduser.setGender(Messages.getString("CardRedeemController.20")); //$NON-NLS-1$
			});

		} else {
			System.out.println(Messages.getString("CardRedeemController.21")); //$NON-NLS-1$
		}

		/*
		 * Thread thr = new Thread(() -> { try { ss = new ServerSocket(5000);
		 * System.out.println("Server is running at port : 5000");
		 * 
		 * while(true) { s = ss.accept(); inputFromClient = new DataInputStream(
		 * s.getInputStream()); outputToClient = new
		 * DataOutputStream(s.getOutputStream());
		 * 
		 * 
		 * datafromandroid = inputFromClient.readUTF(); //A8:81:95:8B:1C:AC
		 * if(datafromandroid.contains("mac")) {
		 * System.out.println("Mac address of andriod is :"+datafromandroid);
		 * if(datafromandroid.equals("macA8:81:95:8B:1C:AC")) {
		 * System.out.println("mac is working"); outputToClient.writeUTF("Yes"); }else {
		 * outputToClient.writeUTF("No"); }
		 * 
		 * } else {
		 * 
		 * System.out.println("Received from android: " + datafromandroid); //
		 * inputFromClient.close(); // s.close();
		 * 
		 * 
		 * 
		 * 
		 * Platform.runLater(()-> tf_qr_search.setText(""+datafromandroid));
		 * 
		 * 
		 * //search by qr Platform.runLater(()-> tf_customer_name.clear());
		 * 
		 * Platform.runLater(()->tf_phone.clear());
		 * 
		 * Platform.runLater(()->tf_email.clear());
		 * Platform.runLater(()->tf_address.clear());
		 * Platform.runLater(()->tf_pin.clear()); Platform.runLater(()->
		 * tf_amount.clear()); Platform.runLater(()->tf_age.clear());
		 * Platform.runLater(()->tf_date_created.clear());
		 * Platform.runLater(()->tf_last_used.clear());
		 * Platform.runLater(()->tf_expired_date.clear());
		 * 
		 * 
		 * String cardno = tf_qr_search.getText().toString();
		 * carduser.setCardno(cardno);
		 * 
		 * new DBInitialize().DBInitialize(); String query =
		 * "SELECT `card number`, `type`, `customer id`, `amount`, `lastuseddate`, `registerdate`, `expired date`, `pin`, `description` FROM `card` WHERE `card number`='"
		 * +cardno+"';";
		 * 
		 * System.out.println("card no is : "+cardno);
		 * 
		 * new DBInitialize(); ResultSet rs =
		 * DBInitialize.statement.executeQuery(query);
		 * 
		 * 
		 * 
		 * if(rs.next()) {
		 * 
		 * carduser.setCardno(rs.getString("card number"));
		 * carduser.setCustomrid(rs.getString("customer id"));
		 * carduser.setAmount(rs.getString("amount"));
		 * carduser.setLastdateused(rs.getString("lastuseddate"));
		 * carduser.setRegisterdate(rs.getString("registerdate"));
		 * carduser.setExpireddate(rs.getString("expired date"));
		 * carduser.setPin(rs.getInt("pin")+""); System.out.println("working");
		 * 
		 * 
		 * //link customer and card (finding customer info) String linkquery =
		 * "SELECT `name`, `age`, `gender`, `address`, `phone`, `email` FROM `Customer` WHERE `id` = '"
		 * +carduser.getCustomrid()+"';"; new DBInitialize(); ResultSet resultset =
		 * DBInitialize.statement.executeQuery(linkquery); if(resultset.next()) {
		 * carduser.setName(resultset.getString("name"));
		 * carduser.setAge(resultset.getString("age"));
		 * carduser.setGender(resultset.getString("gender"));
		 * carduser.setAddress(resultset.getString("address"));
		 * carduser.setPhone(resultset.getString("phone"));
		 * carduser.setEmail(resultset.getString("email"));
		 * System.out.println("working");
		 * 
		 * 
		 * 
		 * //set data to ui
		 * 
		 * tf_customer_name.setText(carduser.getName());
		 * tf_phone.setText(carduser.getPhone()); tf_email.setText(carduser.getEmail());
		 * tf_address.setText(carduser.getAddress()); tf_pin.setText(carduser.getPin());
		 * tf_amount.setText(carduser.getAmount()); tf_age.setText(carduser.getAge());
		 * tf_date_created.setText(carduser.getRegisterdate());
		 * tf_last_used.setText(carduser.getLastdateused());
		 * tf_expired_date.setText(carduser.getExpireddate());
		 * 
		 * if(carduser.getGender().equals("male")) { Platform.runLater(()->
		 * rdo_male.setSelected(true));
		 * Platform.runLater(()->rdo_female.setSelected(false)); } else {
		 * Platform.runLater(()->rdo_female.setSelected(true));
		 * Platform.runLater(()->rdo_male.setSelected(false)); }
		 * 
		 * 
		 * 
		 * 
		 * }//end of second query if else {
		 * 
		 * Platform.runLater(()->tf_customer_name.clear());
		 * Platform.runLater(()->tf_phone.clear());
		 * Platform.runLater(()->tf_email.clear());
		 * Platform.runLater(()->tf_address.clear());
		 * Platform.runLater(()->tf_pin.clear());
		 * Platform.runLater(()->tf_amount.clear());
		 * Platform.runLater(()->tf_age.clear());
		 * Platform.runLater(()->tf_date_created.clear());
		 * Platform.runLater(()->tf_last_used.clear());
		 * Platform.runLater(()->tf_expired_date.clear());
		 * 
		 * 
		 * Alert alert = new Alert(AlertType.ERROR, "No user is found!");
		 * Platform.runLater(()->alert.showAndWait());
		 * 
		 * carduser = new CardUser();
		 * 
		 * }
		 * 
		 * }//end of first query if else {
		 * 
		 * 
		 * Alert alert = new Alert(AlertType.ERROR, "No card is found!");
		 * Optional<ButtonType> result = alert.showAndWait(); if (result.get() ==
		 * ButtonType.OK) { // delete user
		 * Platform.runLater(()->tf_customer_name.clear());
		 * Platform.runLater(()->tf_phone.clear());
		 * Platform.runLater(()->tf_email.clear());
		 * Platform.runLater(()->tf_address.clear());
		 * Platform.runLater(()->tf_pin.clear());
		 * Platform.runLater(()->tf_amount.clear());
		 * Platform.runLater(()->tf_age.clear());
		 * Platform.runLater(()->tf_date_created.clear());
		 * Platform.runLater(()->tf_last_used.clear());
		 * Platform.runLater(()->tf_expired_date.clear());
		 * System.out.println("okay is working ...."); }
		 * 
		 * carduser = new CardUser();
		 * 
		 * }
		 * 
		 * System.out.println("card ower is : "+carduser.getName() +
		 * carduser.getCustomrid()+" last used: "+carduser.getLastdateused());
		 * 
		 * 
		 * }
		 * 
		 * }} catch(Exception ex) {
		 * 
		 * } }); thr.setPriority(Thread.MAX_PRIORITY); try {
		 * MainCashierController.thcashier.sleep(1000); } catch (InterruptedException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); } thr.start();
		 */
	}

	@FXML
	void onBtCancelAction(ActionEvent event) {

		// get a handle to the stage
		Stage stage = (Stage) bt_cancel.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	@FXML
	void onBtRedeemAction(ActionEvent event) {
		if( tf_top_up.getText().equals(Messages.getString("CardRedeemController.22")) || tf_top_up.getText().matches(Messages.getString("CardRedeemController.23"))) { //$NON-NLS-1$ //$NON-NLS-2$
	    	
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.24")); //$NON-NLS-1$
			al.showAndWait();
    	}

    else if (Double.parseDouble(tf_top_up.getText().toString()) < 500 ) {
			Alert al = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.25")); //$NON-NLS-1$
			al.showAndWait();
		} else {
			
			try {
				double topupamount = Double.parseDouble(tf_top_up.getText().toString());
				double existingamount = Double.parseDouble(tf_amount.getText().toString());

				topupamount = topupamount + existingamount;

				carduser.setAmount(topupamount + Messages.getString("CardRedeemController.26")); //$NON-NLS-1$
				tf_amount.setText(carduser.getAmount());

				System.out.println(Messages.getString("CardRedeemController.27") + carduser.getAmount()); //$NON-NLS-1$

			} catch (Exception ex) {
				System.out.println(Messages.getString("CardRedeemController.28") + ex.getMessage()); //$NON-NLS-1$
				Alert al = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.29")+ex.getMessage()); //$NON-NLS-1$
				al.showAndWait();
			}

		} // end of else

		tf_top_up.clear();

	}

	@FXML
	void onTfQRSearchAction(ActionEvent event)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		tf_customer_name.clear();
		tf_phone.clear();
		tf_email.clear();
		tf_address.clear();
		tf_pin.clear();
		tf_amount.clear();
		tf_age.clear();
		tf_date_created.clear();
		tf_last_used.clear();
		tf_expired_date.clear();

		String cardno = tf_qr_search.getText().toString();
		carduser.setCardno(cardno);

		new DBInitialize().DBInitialize();
		String query = Messages.getString("CardRedeemController.30") //$NON-NLS-1$
				+ cardno + Messages.getString("CardRedeemController.31"); //$NON-NLS-1$

		System.out.println(Messages.getString("CardRedeemController.32") + cardno); //$NON-NLS-1$

		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);

		if (rs.next()) {

			carduser.setCardno(rs.getString(Messages.getString("CardRedeemController.33"))); //$NON-NLS-1$
			carduser.setCustomrid(rs.getString(Messages.getString("CardRedeemController.34"))); //$NON-NLS-1$
			carduser.setAmount(rs.getString(Messages.getString("CardRedeemController.35"))); //$NON-NLS-1$
			carduser.setLastdateused(rs.getString(Messages.getString("CardRedeemController.36"))); //$NON-NLS-1$
			carduser.setRegisterdate(rs.getString(Messages.getString("CardRedeemController.37"))); //$NON-NLS-1$
			carduser.setExpireddate(rs.getString(Messages.getString("CardRedeemController.38"))); //$NON-NLS-1$
			carduser.setPin(rs.getInt(Messages.getString("CardRedeemController.39")) + Messages.getString("CardRedeemController.40")); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(Messages.getString("CardRedeemController.41")); //$NON-NLS-1$

			// link customer and card (finding customer info)
			String linkquery = Messages.getString("CardRedeemController.42") //$NON-NLS-1$
					+ carduser.getCustomrid() + Messages.getString("CardRedeemController.43"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet resultset = DBInitialize.statement.executeQuery(linkquery);
			if (resultset.next()) {
				carduser.setName(resultset.getString(Messages.getString("CardRedeemController.44"))); //$NON-NLS-1$
				carduser.setAge(resultset.getString(Messages.getString("CardRedeemController.45"))); //$NON-NLS-1$
				carduser.setGender(resultset.getString(Messages.getString("CardRedeemController.46"))); //$NON-NLS-1$
				carduser.setAddress(resultset.getString(Messages.getString("CardRedeemController.47"))); //$NON-NLS-1$
				carduser.setPhone(resultset.getString(Messages.getString("CardRedeemController.48"))); //$NON-NLS-1$
				carduser.setEmail(resultset.getString(Messages.getString("CardRedeemController.49"))); //$NON-NLS-1$
				System.out.println(Messages.getString("CardRedeemController.50")); //$NON-NLS-1$

				// set data to ui

				tf_customer_name.setText(carduser.getName());
				tf_phone.setText(carduser.getPhone());
				tf_email.setText(carduser.getEmail());
				tf_address.setText(carduser.getAddress());
				tf_pin.setText(carduser.getPin());
				tf_amount.setText(carduser.getAmount());
				tf_age.setText(carduser.getAge());
				tf_date_created.setText(carduser.getRegisterdate());
				tf_last_used.setText(carduser.getLastdateused());
				tf_expired_date.setText(carduser.getExpireddate());

				if (carduser.getGender().equals(Messages.getString("CardRedeemController.51"))) { //$NON-NLS-1$
					rdo_male.setSelected(true);
					rdo_female.setSelected(false);
				} else {
					rdo_female.setSelected(true);
					rdo_male.setSelected(false);
				}

			} // end of second query if
			else {

				tf_customer_name.clear();
				tf_phone.clear();
				tf_email.clear();
				tf_address.clear();
				tf_pin.clear();
				tf_amount.clear();
				tf_age.clear();
				tf_date_created.clear();
				tf_last_used.clear();
				tf_expired_date.clear();

				Alert alert = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.52")); //$NON-NLS-1$
				alert.showAndWait();

				carduser = new CardUser();

			}

		} // end of first query if
		else {

			Alert alert = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.53")); //$NON-NLS-1$
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// delete user
				tf_customer_name.clear();
				tf_phone.clear();
				tf_email.clear();
				tf_address.clear();
				tf_pin.clear();
				tf_amount.clear();
				tf_age.clear();
				tf_date_created.clear();
				tf_last_used.clear();
				tf_expired_date.clear();
				System.out.println(Messages.getString("CardRedeemController.54")); //$NON-NLS-1$
			}

			carduser = new CardUser();

		}

		System.out.println(Messages.getString("CardRedeemController.55") + carduser.getName() + carduser.getCustomrid() + Messages.getString("CardRedeemController.56") //$NON-NLS-1$ //$NON-NLS-2$
				+ carduser.getLastdateused());

	}

	@FXML
	void onBtApplyAction(ActionEvent event) throws SQLException {
		

		if (!tf_customer_name.getText().equals(Messages.getString("CardRedeemController.57")) && tf_phone.getText().contains(Messages.getString("CardRedeemController.58")) && tf_phone.getText().length() > 10 && !tf_phone.getText().matches(Messages.getString("CardRedeemController.59")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				&& !tf_address.getText().equals(Messages.getString("CardRedeemController.60")) && tf_email.getText().contains(Messages.getString("CardRedeemController.61")) && tf_email.getText().contains(Messages.getString("CardRedeemController.62")) && !tf_age.getText().matches(Messages.getString("CardRedeemController.63")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				&& Integer.parseInt(tf_age.getText()) >= 13 ) {

			String pattern = Messages.getString("CardRedeemController.64"); //$NON-NLS-1$
			String lastdateused = new SimpleDateFormat(pattern).format(new Date());
			System.out.println(Messages.getString("CardRedeemController.65") + lastdateused); //$NON-NLS-1$

			

			String gender = Messages.getString("CardRedeemController.66"); //$NON-NLS-1$
			if (rdo_male.isSelected()) {
				gender = Messages.getString("CardRedeemController.67"); //$NON-NLS-1$
			} else {
				gender = Messages.getString("CardRedeemController.68"); //$NON-NLS-1$
			}

			carduser.setAmount(tf_amount.getText());
			carduser.setLastdateused(lastdateused);
			carduser.setName(tf_customer_name.getText());
			carduser.setAddress(tf_address.getText());
			carduser.setAge(tf_age.getText());
			carduser.setGender(gender);
			carduser.setEmail(tf_email.getText());
			carduser.setPhone(tf_phone.getText());

			// update card data to db
			String updatecardquery = Messages.getString("CardRedeemController.69") + carduser.getAmount() + Messages.getString("CardRedeemController.70") //$NON-NLS-1$ //$NON-NLS-2$
					+ carduser.getLastdateused() + Messages.getString("CardRedeemController.71") + carduser.getCardno() + Messages.getString("CardRedeemController.72"); //$NON-NLS-1$ //$NON-NLS-2$
			new DBInitialize();
			DBInitialize.statement.executeUpdate(updatecardquery);

			// update customer data to db
			String updatecustomerquery = Messages.getString("CardRedeemController.73") + carduser.getName() + Messages.getString("CardRedeemController.74") //$NON-NLS-1$ //$NON-NLS-2$
					+ carduser.getAge() + Messages.getString("CardRedeemController.75") + gender + Messages.getString("CardRedeemController.76") + carduser.getAddress() //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("CardRedeemController.77") + carduser.getPhone() + Messages.getString("CardRedeemController.78") + carduser.getEmail() + Messages.getString("CardRedeemController.79") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ carduser.getCustomrid() + Messages.getString("CardRedeemController.80"); //$NON-NLS-1$
			new DBInitialize();
			DBInitialize.statement.executeUpdate(updatecustomerquery);

			((Stage) bt_apply.getScene().getWindow()).close();
			;

			Alert alert = new Alert(AlertType.INFORMATION, Messages.getString("CardRedeemController.81")); //$NON-NLS-1$
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.ERROR, Messages.getString("CardRedeemController.82")); //$NON-NLS-1$
			alert.showAndWait();
		}
	}

}
