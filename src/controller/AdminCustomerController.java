package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CardUser;

public class AdminCustomerController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<CardUser> tb_customer;

	@FXML
	private JFXTextField tf_id;

	@FXML
	private JFXTextField tf_name;

	@FXML
	private JFXTextField tf_addr;

	@FXML
	private JFXTextField tf_phone;

	@FXML
	private JFXTextField tf_mail;

	@FXML
	private JFXTextField tf_card_no;

	@FXML
	private JFXTextField tf_amount;

	@FXML
	private JFXTextField tf_last_date_used;

	@FXML
	private JFXTextField tf_date_created;

	@FXML
	private JFXTextField tf_pin;

	@FXML
	private JFXTextField tf_age;

	@FXML
	private JFXButton bt_search;

	@FXML
	private JFXRadioButton rdo_male;

	@FXML
	private ToggleGroup gender;

	@FXML
	private JFXRadioButton rdo_female;

	@FXML
	private JFXButton bt_new;

	@FXML
	private JFXTextField tf_expired_date;

	@FXML
	private JFXButton bt_add;

	@FXML
	private JFXButton bt_update;

	private TableColumn<CardUser, String> col_item_id;

	private TableColumn<CardUser, String> col_item_name;

	private TableColumn<CardUser, String> col_item_age;

	private TableColumn<CardUser, String> col_item_gender;

	private TableColumn<CardUser, String> col_item_addr;

	private TableColumn<CardUser, String> col_item_phone;

	private TableColumn<CardUser, String> col_item_email;

	private TableColumn<CardUser, String> col_item_card_no;

	private TableColumn<CardUser, String> col_item_amount;

	private TableColumn<CardUser, String> col_item_last_date_used;

	private TableColumn<CardUser, String> col_item_expired_date;

	private TableColumn<CardUser, String> col_item_date_created;

	private TableColumn<CardUser, String> col_item_pin;

	private ObservableList<CardUser> customerData = FXCollections.observableArrayList();

	@FXML
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		assert tb_customer != null : "fx:id=\"tb_customer\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_age != null : "fx:id=\"tf_age\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_addr != null : "fx:id=\"tf_addr\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_phone != null : "fx:id=\"tf_phone\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_mail != null : "fx:id=\"tf_mail\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_card_no != null : "fx:id=\"tf_card_no\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_amount != null : "fx:id=\"tf_amount\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_last_date_used != null : "fx:id=\"tf_last_date_used\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_pin != null : "fx:id=\"tf_pin\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_date_created != null : "fx:id=\"tf_date_created\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert rdo_male != null : "fx:id=\"rdo_male\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert rdo_female != null : "fx:id=\"rdo_female\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert bt_update != null : "fx:id=\"bt_update\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert bt_search != null : "fx:id=\"bt_search\" was not injected: check your FXML file 'Admin_customer.fxml'.";
		assert tf_expired_date != null : "fx:id=\"tf_expired_date\" was not injected: check your FXML file 'Admin_customer.fxml'.";

		tf_pin.setEditable(false);

		String pattern = "dd/MM/yyyy";
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("last date use is " + todaydate);
		tf_date_created.setText(todaydate);

		// count expired date to 3 years
		String[] todaydateAry = todaydate.split("/");
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = "" + (Integer.parseInt(year) + 3);
		tf_expired_date.setText(day+"/"+month+"/" + year);

		col_item_id = new TableColumn<CardUser, String>("ID");
		col_item_name = new TableColumn<CardUser, String>("Name");
		col_item_age = new TableColumn<CardUser, String>("Age");
		col_item_gender = new TableColumn<CardUser, String>("Gender");
		col_item_addr = new TableColumn<CardUser, String>("Address");
		col_item_phone = new TableColumn<CardUser, String>("Phone");
		col_item_email = new TableColumn<CardUser, String>("Email");
		col_item_card_no = new TableColumn<CardUser, String>("Card Number");
		col_item_amount = new TableColumn<CardUser, String>("Amount");
		col_item_last_date_used = new TableColumn<CardUser, String>("Last Date Used");
		col_item_expired_date = new TableColumn<CardUser, String>("Expired Date");
		col_item_date_created = new TableColumn<CardUser, String>("Date Created");
		col_item_pin = new TableColumn<CardUser, String>("PIN");

		col_item_id.setMinWidth(90.0);
		col_item_name.setMinWidth(200.0);
		col_item_age.setMinWidth(75.0);
		col_item_gender.setMinWidth(90.0);
		col_item_addr.setMinWidth(130.0);
		col_item_phone.setMinWidth(150.0);
		col_item_email.setMinWidth(200.0);
		col_item_card_no.setMinWidth(200.0);
		col_item_amount.setMinWidth(110.0);
		col_item_last_date_used.setMinWidth(110.0);
		col_item_expired_date.setMinWidth(110.0);
		col_item_date_created.setMinWidth(110.0);
		col_item_pin.setMinWidth(90.0);

		col_item_id.setStyle("-fx-font-size: 18");
		col_item_name.setStyle("-fx-font-size: 18");
		col_item_age.setStyle("-fx-font-size: 18");
		col_item_gender.setStyle("-fx-font-size: 18");
		col_item_addr.setStyle("-fx-font-size: 18");
		col_item_phone.setStyle("-fx-font-size: 18");
		col_item_email.setStyle("-fx-font-size: 18");
		col_item_card_no.setStyle("-fx-font-size: 18");
		col_item_amount.setStyle("-fx-font-size: 18");
		col_item_last_date_used.setStyle("-fx-font-size: 18");
		col_item_expired_date.setStyle("-fx-font-size: 18");
		col_item_date_created.setStyle("-fx-font-size: 18");
		col_item_pin.setStyle("-fx-font-size: 18");

		col_item_id.setCellValueFactory(new PropertyValueFactory<CardUser, String>("customrid"));
		col_item_name.setCellValueFactory(new PropertyValueFactory<CardUser, String>("name"));
		col_item_age.setCellValueFactory(new PropertyValueFactory<CardUser, String>("age"));
		col_item_gender.setCellValueFactory(new PropertyValueFactory<CardUser, String>("gender"));
		col_item_addr.setCellValueFactory(new PropertyValueFactory<CardUser, String>("address"));
		col_item_phone.setCellValueFactory(new PropertyValueFactory<CardUser, String>("phone"));
		col_item_email.setCellValueFactory(new PropertyValueFactory<CardUser, String>("email"));
		col_item_card_no.setCellValueFactory(new PropertyValueFactory<CardUser, String>("cardno"));
		col_item_amount.setCellValueFactory(new PropertyValueFactory<CardUser, String>("amount"));
		col_item_last_date_used.setCellValueFactory(new PropertyValueFactory<CardUser, String>("lastdateused"));
		col_item_expired_date.setCellValueFactory(new PropertyValueFactory<CardUser, String>("expireddate"));
		col_item_date_created.setCellValueFactory(new PropertyValueFactory<CardUser, String>("registerdate"));
		col_item_pin.setCellValueFactory(new PropertyValueFactory<CardUser, String>("pin"));

		tb_customer.getColumns().addAll(col_item_id, col_item_name, col_item_age, col_item_gender, col_item_addr,
				col_item_phone, col_item_email, col_item_card_no, col_item_amount, col_item_last_date_used,
				col_item_date_created, col_item_expired_date, col_item_pin);

		// get data from db and add into array
		String query = "SELECT Customer.id, Customer.name, Customer.age, Customer.gender, Customer.address, Customer.phone, Customer.email, Card.cardnumber, Card.amount, Card.lastuseddate, Card.registerdate, Card.expireddate, Card.pin FROM Customer, Card WHERE Customer.id = Card.customerid ORDER BY Customer.id DESC;";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			CardUser cu = new CardUser();

			cu.setCustomrid(rs.getString(1));
			cu.setName(rs.getString(2));
			cu.setAge(rs.getString(3));
			cu.setGender(rs.getString(4));
			cu.setAddress(rs.getString(5));
			cu.setPhone(rs.getString(6));
			cu.setEmail(rs.getString(7));
			cu.setCardno(rs.getString(8));
			cu.setAmount(rs.getString(9));
			cu.setLastdateused(rs.getString(10));
			cu.setRegisterdate(rs.getString(11));
			cu.setExpireddate(rs.getString(12));
			cu.setPin(rs.getString(13));

			customerData.add(cu);
		}

		// set data to table
		tb_customer.setItems(customerData);

		// row action
		tb_customer.setRowFactory(t -> {
			TableRow<CardUser> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				// get data from selected row

				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					CardUser cu = tb_customer.getSelectionModel().getSelectedItem();
					System.out.println("Double click is: " + cu.getName());

					// set data to tf
					tf_id.setText(cu.getCustomrid());
					tf_name.setText(cu.getName());
					tf_age.setText(cu.getAge());
					tf_addr.setText(cu.getAddress());
					tf_phone.setText(cu.getPhone());
					tf_mail.setText(cu.getEmail());
					tf_card_no.setText(cu.getCardno());
					tf_amount.setText(cu.getAmount());
					tf_last_date_used.setText(cu.getLastdateused());
					tf_expired_date.setText(cu.getExpireddate());
					tf_pin.setText(cu.getPin());
					tf_date_created.setText(cu.getRegisterdate());

					// rdo
					String gender = cu.getGender();
					if (gender.equals("male")) {
						rdo_male.setSelected(true);
						rdo_female.setSelected(false);
					} else {
						rdo_male.setSelected(false);
						rdo_female.setSelected(true);
					}

				}
			});
			final ContextMenu rowMenu = new ContextMenu();

			MenuItem removeItem = new MenuItem("Delete");
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					CardUser ca = tb_customer.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION,
							"Are U Sure You Want To Delete " + ca.getName() + " from database including card ?", ButtonType.YES,
							ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff
						String removeCustomerquery = "DELETE FROM `Customer` WHERE Customer.id = '" + ca.getCustomrid()
								+ "';";
						
						String removeCardquery = "DELETE FROM `Card` WHERE Card.cardnumber = '" + ca.getCardno() + "';";
						try {
							new DBInitialize().DBInitialize();
							new DBInitialize();
							DBInitialize.statement.executeUpdate(removeCustomerquery);
							DBInitialize.statement.executeUpdate(removeCardquery);

							// update table
							// get tabe data
							String getTableDataQuery = "SELECT Customer.id, Customer.name, Customer.age, Customer.gender, Customer.address, Customer.phone, Customer.email, Card.cardnumber, Card.amount, Card.lastuseddate, Card.registerdate, Card.expireddate, Card.pin FROM Customer, Card WHERE Customer.id = Card.customerid ORDER BY Customer.id DESC;";
							customerData.clear();// clear category data
							new DBInitialize();
							ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
							while (getrs.next()) {
								CardUser cu = new CardUser();
								cu.setCustomrid(getrs.getString(1));
								cu.setName(getrs.getString(2));
								cu.setAge(getrs.getString(3));
								cu.setGender(getrs.getString(4));
								cu.setAddress(getrs.getString(5));
								cu.setPhone(getrs.getString(6));
								cu.setEmail(getrs.getString(7));
								cu.setCardno(getrs.getString(8));
								cu.setAmount(getrs.getString(9));
								cu.setLastdateused(getrs.getString(10));
								cu.setRegisterdate(getrs.getString(11));
								cu.setExpireddate(getrs.getString(12));
								cu.setPin("" + getrs.getInt(13));

								customerData.add(cu);
							}
							// set to table
							tb_customer.refresh();

							/*
							 * //show alert Alert al = new Alert(AlertType.INFORMATION, "Item removed!");
							 * al.showAndWait();
							 */

							// show alert
							Alert al = new Alert(AlertType.INFORMATION, "Item deleted!");
							al.showAndWait();

						} catch (ClassNotFoundException | SQLException | InstantiationException
								| IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			});
			rowMenu.getItems().addAll(removeItem);

			// only display context menu for non-null items:
			row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));

			return row;

		});

	}

	// search card no info

	@FXML
	void onBtSearchAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		String cardno = tf_card_no.getText().toString();

		// search card by card no and add info to the tf
		String searchCardQuery = "SELECT * FROM `Card` WHERE Card.cardnumber = '" + cardno + "';";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rssearch = DBInitialize.statement.executeQuery(searchCardQuery);

		String datecreated = "";
		String expireddate = "";
		String lastDateUsed = "";
		String customerid = "";

		if (rssearch.next()) {

			expireddate = rssearch.getString("expireddate");
			tf_amount.setText(rssearch.getString("amount"));
			lastDateUsed = rssearch.getString("lastuseddate");
			datecreated = rssearch.getString("registerdate");
			tf_pin.setText("" + rssearch.getInt("pin"));
			customerid = rssearch.getString("customerid");

		} else {
			// show alert
			Alert al = new Alert(AlertType.ERROR, " Invalid Card!");
			al.showAndWait();
		}

		System.out.println("expiredate" + expireddate);

		/*
		 * if(datecreated.equals("") || expireddate.equals("") ||
		 * lastDateUsed.equals("") ) {
		 * 
		 * String tdy =new SimpleDateFormat(pattern).format(new Date());
		 * System.out.println("today tdy is "+tdy);
		 * 
		 * 
		 * tf_date_created.setText(tdy); tf_expired_date.setValue(LocalDate.parse(tdy,
		 * formatter)); tf_last_date_used.setText(tdy);
		 * 
		 * }else { //tf_date_created.setText(datecreated);
		 * tf_expired_date.setValue(LocalDate.parse(expireddate, formatter));
		 * //tf_last_date_used.setText(lastDateUsed);
		 * 
		 * }
		 */

		// create today date
		String pattern = "dd/MM/yyyy";
		
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("today is " + todaydate);
		
		// count expired date to 3 years
		String[] todaydateAry = todaydate.split("/");
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = "" + (Integer.parseInt(year) + 3);
		

		if (customerid.equals("")) {
			// tf_id.clear();
			tf_name.clear();
			tf_addr.clear();
			tf_phone.clear();
			tf_mail.clear();
			tf_age.clear();

			tf_date_created.setText(todaydate);
			// tf_expired_date.setValue(LocalDate.parse(todaydate, formatter));
			tf_expired_date.setText(day+"/"+month+"/" + year);
			tf_last_date_used.setText(todaydate);

		} else {

			String name = "";
			String age = "";
			String gender = "";
			String addr = "";
			String ph = "";
			String mail = "";

			String searchCustomerQuery = "SELECT  `name`, `age`, `gender`, `address`, `phone`, `email` FROM `Customer` WHERE Customer.id = '"
					+ customerid + "';";
			new DBInitialize();
			ResultSet cusrs = DBInitialize.statement.executeQuery(searchCustomerQuery);
			while (cusrs.next()) {
				name = cusrs.getString(1);
				age = cusrs.getString(2);
				gender = cusrs.getString(3);
				addr = cusrs.getString(4);
				ph = cusrs.getString(5);
				mail = cusrs.getString(6);

			}

			if (gender.equals("male")) {
				rdo_male.setSelected(true);
				rdo_female.setSelected(false);
			} else {
				rdo_male.setSelected(false);
				rdo_female.setSelected(true);
			}

			tf_id.setText(customerid);
			tf_name.setText(name);
			tf_age.setText(age);
			tf_addr.setText(addr);
			tf_phone.setText(ph);
			tf_mail.setText(mail);

			// DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			System.out.println("exipredddddddateeee: " + expireddate);
			tf_date_created.setText(datecreated);

			tf_expired_date.getText().toString();
			tf_expired_date.clear();
			tf_expired_date.setText(expireddate);

			tf_last_date_used.setText(lastDateUsed);

		}

	}

	@FXML
	void onbtNewAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		// set data to tf
		tf_id.clear();
		tf_name.clear();
		tf_age.clear();
		tf_addr.clear();
		tf_phone.clear();
		tf_mail.clear();
		tf_card_no.clear();
		tf_amount.clear();
		tf_last_date_used.clear();
		tf_expired_date.clear();
		tf_pin.clear();
		tf_date_created.clear();

		String query = "SELECT `id` FROM Customer ORDER BY Customer.id DESC LIMIT 1";

		String oldid = "";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldid = "" + rs.getString(1);
		}

		// count +1 new Id
		String newId = "" + (Integer.parseInt(oldid) + 1);
		tf_id.setText(newId);

		// create today date
		String pattern = "dd/MM/yyyy";
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("today is " + todaydate);
		tf_date_created.setText(todaydate);
		tf_last_date_used.setText(todaydate);

		// count expired date to 3 years
		String[] todaydateAry = todaydate.split("/");
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = "" + (Integer.parseInt(year) + 3);
		tf_expired_date.setText(day+"/"+month+"/" + year);
	}

	@FXML
	void onAddAction(ActionEvent event) throws SQLException {

		if (tf_id.getText().equals("") || tf_name.getText().equals("") || tf_age.getText().equals("")
				|| tf_addr.getText().equals("") || tf_phone.getText().equals("") || tf_mail.getText().equals("")
				|| tf_card_no.getText().equals("") || tf_amount.getText().equals("") || tf_pin.getText().equals("")
				|| tf_age.getText().matches(".*[a-zA-Z]+.*") || Integer.parseInt(tf_age.getText()) < 13
				|| tf_phone.getText().matches(".*[a-zA-Z]+.*") || tf_phone.getText().length() < 10
				|| !tf_mail.getText().contains("mail.com") || !tf_mail.getText().contains("@")
				|| tf_amount.getText().matches(".*[a-zA-Z]+.*") || Double.parseDouble(tf_amount.getText()) < 50000) {
			Alert al = new Alert(AlertType.ERROR, "Invaild Input or Data Missing!");
			al.showAndWait();
		} else {

			// create today date
			String pattern = "dd/MM/yyyy";
			String todaydate = new SimpleDateFormat(pattern).format(new Date());

			// formatter for datepicker
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			String id = tf_id.getText().toString();
			String name = tf_name.getText().toString();
			String age = tf_age.getText().toString();
			String addr = tf_addr.getText().toString();
			String ph = tf_phone.getText().toString();
			String mail = tf_mail.getText().toString();
			String cardno = tf_card_no.getText().toString();
			String amount = tf_amount.getText().toString();
			// String lastdateused = tf_last_date_used.getText().toString();
			String expireddate = tf_expired_date.getText().toString();
			String pin = tf_pin.getText().toString();
			String datecreated = tf_date_created.getText().toString();
			System.out.println("expired date is ssssss::::: " + expireddate);
			String gender = "";
			if (rdo_male.isSelected()) {
				gender = "male";
			} else {
				gender = "female";
			}

			try {
			String addCustomerQuery = "INSERT INTO `Customer`(`id`, `name`, `age`, `gender`, `address`, `phone`, `email`) "
					+ "VALUES ('" + id + "','" + name + "','" + age + "','" + gender + "','" + addr + "','" + ph + "','"
					+ mail + "')";
			String updateCardQuery = "UPDATE `Card` SET `customerid`='" + id + "',`amount`='" + amount
					+ "',`lastuseddate`='" + todaydate + "',`registerdate`='" + datecreated + "',`expireddate`='"
					+ expireddate + "',`pin`=" + pin + " WHERE `cardnumber`='" + cardno + "'";

			new DBInitialize();
			DBInitialize.statement.executeUpdate(addCustomerQuery);
			DBInitialize.statement.executeUpdate(updateCardQuery);

			// clear
			tf_id.clear();
			tf_name.clear();
			tf_age.clear();
			tf_addr.clear();
			tf_phone.clear();
			tf_mail.clear();
			tf_card_no.clear();
			tf_amount.clear();
			tf_last_date_used.clear();
			tf_expired_date.clear();
			tf_pin.clear();
			tf_date_created.clear();

			// get tabe data
			String getTableDataQuery = "SELECT Customer.id, Customer.name, Customer.age, Customer.gender, Customer.address, Customer.phone, Customer.email, Card.cardnumber, Card.amount, Card.lastuseddate, Card.registerdate, Card.expireddate, Card.pin FROM Customer, Card WHERE Customer.id = Card.customerid ORDER BY Customer.id DESC;";
			customerData.clear();// clear category data
			new DBInitialize();
			ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
			while (getrs.next()) {
				CardUser cu = new CardUser();
				cu.setCustomrid(getrs.getString(1));
				cu.setName(getrs.getString(2));
				cu.setAge(getrs.getString(3));
				cu.setGender(getrs.getString(4));
				cu.setAddress(getrs.getString(5));
				cu.setPhone(getrs.getString(6));
				cu.setEmail(getrs.getString(7));
				cu.setCardno(getrs.getString(8));
				cu.setAmount(getrs.getString(9));
				cu.setLastdateused(getrs.getString(10));
				cu.setRegisterdate(getrs.getString(11));
				cu.setExpireddate(getrs.getString(12));
				cu.setPin("" + getrs.getInt(13));

				customerData.add(cu);
			}
			// set to table
			tb_customer.refresh();
			// tb_category.setItems(categoryData);
			// show alert
			Alert al = new Alert(AlertType.INFORMATION, "Item added!");
			al.showAndWait();
			
			}//end of try
	    	catch(Exception ex) {
	    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
	    		al.showAndWait();
	    	}
		} // end of else
	}

	@FXML
	void onUpdateAction(ActionEvent event) throws SQLException {

		if (tf_id.getText().equals("") || tf_name.getText().equals("") || tf_age.getText().equals("")
				|| tf_addr.getText().equals("") || tf_phone.getText().equals("") || tf_mail.getText().equals("")
				|| tf_card_no.getText().equals("") || tf_amount.getText().equals("") || tf_pin.getText().equals("")
				|| tf_age.getText().matches(".*[a-zA-Z]+.*") || Integer.parseInt(tf_age.getText()) < 13
				|| tf_phone.getText().matches(".*[a-zA-Z]+.*") || tf_phone.getText().length() < 10
				|| !tf_mail.getText().contains("mail.com") || !tf_mail.getText().contains("@")
				|| tf_amount.getText().matches(".*[a-zA-Z]+.*") || Double.parseDouble(tf_amount.getText()) < 3000) {
			Alert al = new Alert(AlertType.ERROR, "Invaild Input or Data Missing!");
			al.showAndWait();
		} else {

			// create today date
			String pattern = "dd/MM/yyyy";
			String todaydate = new SimpleDateFormat(pattern).format(new Date());

			// formatter for datepicker
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			String id = tf_id.getText().toString();
			String name = tf_name.getText().toString();
			String age = tf_age.getText().toString();
			String addr = tf_addr.getText().toString();
			String ph = tf_phone.getText().toString();
			String mail = tf_mail.getText().toString();
			String cardno = tf_card_no.getText().toString();
			String amount = tf_amount.getText().toString();
			// String lastdateused = tf_last_date_used.getText().toString();
			String expireddate = tf_expired_date.getText().toString();
			String pin = tf_pin.getText().toString();
			String datecreated = tf_date_created.getText().toString();

			String gender = "";
			if (rdo_male.isSelected()) {
				gender = "male";
			} else {
				gender = "female";
			}

			String updateCustomerQuery = "UPDATE `Customer` SET `name`='" + name + "',`age`='" + age + "',`gender`='"
					+ gender + "',`address`='" + addr + "',`phone`='" + ph + "',`email`='" + mail + "' WHERE `id` = '"
					+ id + "'";
			String updateCardQuery = "UPDATE `Card` SET `customerid`='" + id + "',`amount`='" + amount
					+ "',`lastuseddate`='" + todaydate + "',`registerdate`='" + datecreated + "',`expireddate`='"
					+ expireddate + "',`pin`=" + pin + " WHERE `cardnumber`='" + cardno + "'";

			new DBInitialize();
			DBInitialize.statement.executeUpdate(updateCustomerQuery);
			DBInitialize.statement.executeUpdate(updateCardQuery);

			// clear
			tf_id.clear();
			tf_name.clear();
			tf_age.clear();
			tf_addr.clear();
			tf_phone.clear();
			tf_mail.clear();
			tf_card_no.clear();
			tf_amount.clear();
			tf_last_date_used.clear();
			tf_expired_date.clear();
			tf_pin.clear();
			tf_date_created.clear();

			// get tabe data
			String getTableDataQuery = "SELECT Customer.id, Customer.name, Customer.age, Customer.gender, Customer.address, Customer.phone, Customer.email, Card.cardnumber, Card.amount, Card.lastuseddate, Card.registerdate, Card.expireddate, Card.pin FROM Customer, Card WHERE Customer.id = Card.customerid ORDER BY Customer.id DESC;";
			customerData.clear();// clear category data
			new DBInitialize();
			ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
			while (getrs.next()) {
				CardUser cu = new CardUser();
				cu.setCustomrid(getrs.getString(1));
				cu.setName(getrs.getString(2));
				cu.setAge(getrs.getString(3));
				cu.setGender(getrs.getString(4));
				cu.setAddress(getrs.getString(5));
				cu.setPhone(getrs.getString(6));
				cu.setEmail(getrs.getString(7));
				cu.setCardno(getrs.getString(8));
				cu.setAmount(getrs.getString(9));
				cu.setLastdateused(getrs.getString(10));
				cu.setRegisterdate(getrs.getString(11));
				cu.setExpireddate(getrs.getString(12));
				cu.setPin("" + getrs.getInt(13));

				customerData.add(cu);
			}
			// set to table
			tb_customer.refresh();
			// show alert
			Alert al = new Alert(AlertType.INFORMATION, "Item updated!");
			al.showAndWait();
		}
	}// end of first else
}
