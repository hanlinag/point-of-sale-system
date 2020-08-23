package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
		assert tf_qr_search != null : "fx:id=\"tf_qr_search\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_customer_name != null : "fx:id=\"tf_customer_name\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert bt_apply != null : "fx:id=\"bt_apply\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert bt_cancel != null : "fx:id=\"bt_cancel\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_phone != null : "fx:id=\"tf_phone\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_address != null : "fx:id=\"tf_address\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_pin != null : "fx:id=\"tf_pin\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_amount != null : "fx:id=\"tf_amount\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_top_up != null : "fx:id=\"tf_top_up\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert bt_redeem != null : "fx:id=\"bt_redeem\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_age != null : "fx:id=\"tf_age\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_date_created != null : "fx:id=\"tf_date_created\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_expired_date != null : "fx:id=\"tf_expired_date\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert tf_last_used != null : "fx:id=\"tf_last_used\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert rdo_male != null : "fx:id=\"rdo_male\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'Card_redeem.fxml'.";
		assert rdo_female != null : "fx:id=\"rdo_female\" was not injected: check your FXML file 'Card_redeem.fxml'.";

		rdo_male.setSelected(true);

		if (!tf_customer_name.getText().equals("")) {
			rdo_male.setOnAction(e -> {
				if (rdo_male.isSelected()) {
					rdo_male.setSelected(true);
					rdo_female.setSelected(false);
				} else {
					rdo_male.setSelected(true);
					rdo_female.setSelected(false);
				}
				carduser.setGender("male");
			});

			rdo_female.setOnAction(e -> {
				if (rdo_male.isSelected()) {
					rdo_male.setSelected(false);
					rdo_female.setSelected(true);
				} else {
					rdo_male.setSelected(false);
					rdo_female.setSelected(true);
				}
				carduser.setGender("female");
			});

		} else {
			System.out.println("no action! cause nothing is how edit");
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
		 * MainCashierController.thcashier.sleep(100000); } catch (InterruptedException
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
		if( tf_top_up.getText().equals("") || tf_top_up.getText().matches(".*[a-zA-Z]+.*")) {
	    	
    		Alert al = new Alert(AlertType.ERROR, "Invalid input");
			al.showAndWait();
    	}

    else if (Double.parseDouble(tf_top_up.getText().toString()) < 50000 ) {
			Alert al = new Alert(AlertType.ERROR, "Amount Is Less than 50000");
			al.showAndWait();
		} else {
			
			try {
				double topupamount = Double.parseDouble(tf_top_up.getText().toString());
				double existingamount = Double.parseDouble(tf_amount.getText().toString());

				topupamount = topupamount + existingamount;

				carduser.setAmount(topupamount + "");
				tf_amount.setText(carduser.getAmount());

				System.out.println("Do redeem " + carduser.getAmount());

			} catch (Exception ex) {
				System.out.println("exception on topup : " + ex.getMessage());
				Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
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
		String query = "SELECT `cardnumber`, `customerid`, `amount`, `lastuseddate`, `registerdate`, `expireddate`, `pin` FROM `card` WHERE `cardnumber`='"
				+ cardno + "';";

		System.out.println("card no is : " + cardno);

		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);

		if (rs.next()) {

			carduser.setCardno(rs.getString("cardnumber"));
			carduser.setCustomrid(rs.getString("customerid"));
			carduser.setAmount(rs.getString("amount"));
			carduser.setLastdateused(rs.getString("lastuseddate"));
			carduser.setRegisterdate(rs.getString("registerdate"));
			carduser.setExpireddate(rs.getString("expireddate"));
			carduser.setPin(rs.getInt("pin") + "");
			System.out.println("working");

			// link customer and card (finding customer info)
			String linkquery = "SELECT `name`, `age`, `gender`, `address`, `phone`, `email` FROM `Customer` WHERE `id` = '"
					+ carduser.getCustomrid() + "';";
			new DBInitialize();
			ResultSet resultset = DBInitialize.statement.executeQuery(linkquery);
			if (resultset.next()) {
				carduser.setName(resultset.getString("name"));
				carduser.setAge(resultset.getString("age"));
				carduser.setGender(resultset.getString("gender"));
				carduser.setAddress(resultset.getString("address"));
				carduser.setPhone(resultset.getString("phone"));
				carduser.setEmail(resultset.getString("email"));
				System.out.println("working");

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

				if (carduser.getGender().equals("male")) {
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

				Alert alert = new Alert(AlertType.ERROR, "No user is found!");
				alert.showAndWait();

				carduser = new CardUser();

			}

		} // end of first query if
		else {

			Alert alert = new Alert(AlertType.ERROR, "No card is found!");
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
				System.out.println("okay is working ....");
			}

			carduser = new CardUser();

		}

		System.out.println("card ower is : " + carduser.getName() + carduser.getCustomrid() + " last used: "
				+ carduser.getLastdateused());

	}

	@FXML
	void onBtApplyAction(ActionEvent event) throws SQLException {
		

		if (!tf_customer_name.getText().equals("") && tf_phone.getText().contains("09") && tf_phone.getText().length() > 10 && !tf_phone.getText().matches(".*[a-zA-Z]+.*")
				&& !tf_address.getText().equals("") && tf_email.getText().contains("mail.com") && tf_email.getText().contains("@") && !tf_age.getText().matches(".*[a-zA-Z]+.*")
				&& Integer.parseInt(tf_age.getText()) >= 13 ) {

			String pattern = "dd/MM/yyyy";
			String lastdateused = new SimpleDateFormat(pattern).format(new Date());
			System.out.println("last date use is " + lastdateused);

			

			String gender = "";
			if (rdo_male.isSelected()) {
				gender = "male";
			} else {
				gender = "female";
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
			String updatecardquery = " UPDATE `Card` SET `amount`='" + carduser.getAmount() + "',`lastuseddate`='"
					+ carduser.getLastdateused() + "' WHERE `cardnumber` = '" + carduser.getCardno() + "' ";
			new DBInitialize();
			DBInitialize.statement.executeUpdate(updatecardquery);

			// update customer data to db
			String updatecustomerquery = " UPDATE `Customer` SET `name`='" + carduser.getName() + "',`age`='"
					+ carduser.getAge() + "',`gender`='" + gender + "',`address`='" + carduser.getAddress()
					+ "',`phone`='" + carduser.getPhone() + "',`email`='" + carduser.getEmail() + "' WHERE `id`= '"
					+ carduser.getCustomrid() + "' ";
			new DBInitialize();
			DBInitialize.statement.executeUpdate(updatecustomerquery);

			((Stage) bt_apply.getScene().getWindow()).close();
			;

			Alert alert = new Alert(AlertType.INFORMATION, "Information updated.");
			alert.showAndWait();

		} else {
			Alert alert = new Alert(AlertType.ERROR, "Error.");
			alert.showAndWait();
		}
	}

}
