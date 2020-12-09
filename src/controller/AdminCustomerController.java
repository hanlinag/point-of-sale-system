package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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
		assert tb_customer != null : Messages.getString("AdminCustomerController.0"); //$NON-NLS-1$
		assert tf_id != null : Messages.getString("AdminCustomerController.1"); //$NON-NLS-1$
		assert tf_name != null : Messages.getString("AdminCustomerController.2"); //$NON-NLS-1$
		assert tf_age != null : Messages.getString("AdminCustomerController.3"); //$NON-NLS-1$
		assert tf_addr != null : Messages.getString("AdminCustomerController.4"); //$NON-NLS-1$
		assert tf_phone != null : Messages.getString("AdminCustomerController.5"); //$NON-NLS-1$
		assert tf_mail != null : Messages.getString("AdminCustomerController.6"); //$NON-NLS-1$
		assert tf_card_no != null : Messages.getString("AdminCustomerController.7"); //$NON-NLS-1$
		assert tf_amount != null : Messages.getString("AdminCustomerController.8"); //$NON-NLS-1$
		assert tf_last_date_used != null : Messages.getString("AdminCustomerController.9"); //$NON-NLS-1$
		assert tf_pin != null : Messages.getString("AdminCustomerController.10"); //$NON-NLS-1$
		assert tf_date_created != null : Messages.getString("AdminCustomerController.11"); //$NON-NLS-1$
		assert rdo_male != null : Messages.getString("AdminCustomerController.12"); //$NON-NLS-1$
		assert gender != null : Messages.getString("AdminCustomerController.13"); //$NON-NLS-1$
		assert rdo_female != null : Messages.getString("AdminCustomerController.14"); //$NON-NLS-1$
		assert bt_add != null : Messages.getString("AdminCustomerController.15"); //$NON-NLS-1$
		assert bt_update != null : Messages.getString("AdminCustomerController.16"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("AdminCustomerController.17"); //$NON-NLS-1$
		assert bt_search != null : Messages.getString("AdminCustomerController.18"); //$NON-NLS-1$
		assert tf_expired_date != null : Messages.getString("AdminCustomerController.19"); //$NON-NLS-1$

		tf_pin.setEditable(false);

		String pattern = Messages.getString("AdminCustomerController.20"); //$NON-NLS-1$
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminCustomerController.21") + todaydate); //$NON-NLS-1$
		tf_date_created.setText(todaydate);

		// count expired date to 3 years
		String[] todaydateAry = todaydate.split(Messages.getString("AdminCustomerController.22")); //$NON-NLS-1$
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = Messages.getString("AdminCustomerController.23") + (Integer.parseInt(year) + 3); //$NON-NLS-1$
		tf_expired_date.setText(day+Messages.getString("AdminCustomerController.24")+month+Messages.getString("AdminCustomerController.25") + year); //$NON-NLS-1$ //$NON-NLS-2$

		col_item_id = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.26")); //$NON-NLS-1$
		col_item_name = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.27")); //$NON-NLS-1$
		col_item_age = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.28")); //$NON-NLS-1$
		col_item_gender = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.29")); //$NON-NLS-1$
		col_item_addr = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.30")); //$NON-NLS-1$
		col_item_phone = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.31")); //$NON-NLS-1$
		col_item_email = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.32")); //$NON-NLS-1$
		col_item_card_no = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.33")); //$NON-NLS-1$
		col_item_amount = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.34")); //$NON-NLS-1$
		col_item_last_date_used = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.35")); //$NON-NLS-1$
		col_item_expired_date = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.36")); //$NON-NLS-1$
		col_item_date_created = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.37")); //$NON-NLS-1$
		col_item_pin = new TableColumn<CardUser, String>(Messages.getString("AdminCustomerController.38")); //$NON-NLS-1$

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

		col_item_id.setStyle(Messages.getString("AdminCustomerController.39")); //$NON-NLS-1$
		col_item_name.setStyle(Messages.getString("AdminCustomerController.40")); //$NON-NLS-1$
		col_item_age.setStyle(Messages.getString("AdminCustomerController.41")); //$NON-NLS-1$
		col_item_gender.setStyle(Messages.getString("AdminCustomerController.42")); //$NON-NLS-1$
		col_item_addr.setStyle(Messages.getString("AdminCustomerController.43")); //$NON-NLS-1$
		col_item_phone.setStyle(Messages.getString("AdminCustomerController.44")); //$NON-NLS-1$
		col_item_email.setStyle(Messages.getString("AdminCustomerController.45")); //$NON-NLS-1$
		col_item_card_no.setStyle(Messages.getString("AdminCustomerController.46")); //$NON-NLS-1$
		col_item_amount.setStyle(Messages.getString("AdminCustomerController.47")); //$NON-NLS-1$
		col_item_last_date_used.setStyle(Messages.getString("AdminCustomerController.48")); //$NON-NLS-1$
		col_item_expired_date.setStyle(Messages.getString("AdminCustomerController.49")); //$NON-NLS-1$
		col_item_date_created.setStyle(Messages.getString("AdminCustomerController.50")); //$NON-NLS-1$
		col_item_pin.setStyle(Messages.getString("AdminCustomerController.51")); //$NON-NLS-1$

		col_item_id.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.52"))); //$NON-NLS-1$
		col_item_name.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.53"))); //$NON-NLS-1$
		col_item_age.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.54"))); //$NON-NLS-1$
		col_item_gender.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.55"))); //$NON-NLS-1$
		col_item_addr.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.56"))); //$NON-NLS-1$
		col_item_phone.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.57"))); //$NON-NLS-1$
		col_item_email.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.58"))); //$NON-NLS-1$
		col_item_card_no.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.59"))); //$NON-NLS-1$
		col_item_amount.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.60"))); //$NON-NLS-1$
		col_item_last_date_used.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.61"))); //$NON-NLS-1$
		col_item_expired_date.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.62"))); //$NON-NLS-1$
		col_item_date_created.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.63"))); //$NON-NLS-1$
		col_item_pin.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCustomerController.64"))); //$NON-NLS-1$

		tb_customer.getColumns().addAll(col_item_id, col_item_name, col_item_age, col_item_gender, col_item_addr,
				col_item_phone, col_item_email, col_item_card_no, col_item_amount, col_item_last_date_used,
				col_item_date_created, col_item_expired_date, col_item_pin);

		// get data from db and add into array
		String query = Messages.getString("AdminCustomerController.65"); //$NON-NLS-1$
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
					System.out.println(Messages.getString("AdminCustomerController.66") + cu.getName()); //$NON-NLS-1$

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
					if (gender.equals(Messages.getString("AdminCustomerController.67"))) { //$NON-NLS-1$
						rdo_male.setSelected(true);
						rdo_female.setSelected(false);
					} else {
						rdo_male.setSelected(false);
						rdo_female.setSelected(true);
					}

				}
			});
			final ContextMenu rowMenu = new ContextMenu();

			MenuItem removeItem = new MenuItem(Messages.getString("AdminCustomerController.68")); //$NON-NLS-1$
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					CardUser ca = tb_customer.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION,
							Messages.getString("AdminCustomerController.69") + ca.getName() + Messages.getString("AdminCustomerController.70"), ButtonType.YES, //$NON-NLS-1$ //$NON-NLS-2$
							ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff
						String removeCustomerquery = Messages.getString("AdminCustomerController.71") + ca.getCustomrid() //$NON-NLS-1$
								+ Messages.getString("AdminCustomerController.72"); //$NON-NLS-1$
						
						String removeCardquery = Messages.getString("AdminCustomerController.73") + ca.getCardno() + Messages.getString("AdminCustomerController.74"); //$NON-NLS-1$ //$NON-NLS-2$
						try {
							new DBInitialize().DBInitialize();
							new DBInitialize();
							DBInitialize.statement.executeUpdate(removeCustomerquery);
							DBInitialize.statement.executeUpdate(removeCardquery);

							// update table
							// get tabe data
							String getTableDataQuery = Messages.getString("AdminCustomerController.75"); //$NON-NLS-1$
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
								cu.setPin(Messages.getString("AdminCustomerController.76") + getrs.getInt(13)); //$NON-NLS-1$

								customerData.add(cu);
							}
							// set to table
							tb_customer.refresh();

							/*
							 * //show alert Alert al = new Alert(AlertType.INFORMATION, "Item removed!");
							 * al.showAndWait();
							 */

							// show alert
							Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCustomerController.77")); //$NON-NLS-1$
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
		String searchCardQuery = Messages.getString("AdminCustomerController.78") + cardno + Messages.getString("AdminCustomerController.79"); //$NON-NLS-1$ //$NON-NLS-2$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rssearch = DBInitialize.statement.executeQuery(searchCardQuery);

		String datecreated = Messages.getString("AdminCustomerController.80"); //$NON-NLS-1$
		String expireddate = Messages.getString("AdminCustomerController.81"); //$NON-NLS-1$
		String lastDateUsed = Messages.getString("AdminCustomerController.82"); //$NON-NLS-1$
		String customerid = Messages.getString("AdminCustomerController.83"); //$NON-NLS-1$

		if (rssearch.next()) {

			expireddate = rssearch.getString(Messages.getString("AdminCustomerController.84")); //$NON-NLS-1$
			tf_amount.setText(rssearch.getString(Messages.getString("AdminCustomerController.85"))); //$NON-NLS-1$
			lastDateUsed = rssearch.getString(Messages.getString("AdminCustomerController.86")); //$NON-NLS-1$
			datecreated = rssearch.getString(Messages.getString("AdminCustomerController.87")); //$NON-NLS-1$
			tf_pin.setText(Messages.getString("AdminCustomerController.88") + rssearch.getInt(Messages.getString("AdminCustomerController.89"))); //$NON-NLS-1$ //$NON-NLS-2$
			customerid = rssearch.getString(Messages.getString("AdminCustomerController.90")); //$NON-NLS-1$

		} else {
			// show alert
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCustomerController.91")); //$NON-NLS-1$
			al.showAndWait();
		}

		System.out.println(Messages.getString("AdminCustomerController.92") + expireddate); //$NON-NLS-1$

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
		String pattern = Messages.getString("AdminCustomerController.93"); //$NON-NLS-1$
		
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminCustomerController.94") + todaydate); //$NON-NLS-1$
		
		// count expired date to 3 years
		String[] todaydateAry = todaydate.split(Messages.getString("AdminCustomerController.95")); //$NON-NLS-1$
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = Messages.getString("AdminCustomerController.96") + (Integer.parseInt(year) + 3); //$NON-NLS-1$
		

		if (customerid.equals(Messages.getString("AdminCustomerController.97"))) { //$NON-NLS-1$
			// tf_id.clear();
			tf_name.clear();
			tf_addr.clear();
			tf_phone.clear();
			tf_mail.clear();
			tf_age.clear();

			tf_date_created.setText(todaydate);
			// tf_expired_date.setValue(LocalDate.parse(todaydate, formatter));
			tf_expired_date.setText(day+Messages.getString("AdminCustomerController.98")+month+Messages.getString("AdminCustomerController.99") + year); //$NON-NLS-1$ //$NON-NLS-2$
			tf_last_date_used.setText(todaydate);

		} else {

			String name = Messages.getString("AdminCustomerController.100"); //$NON-NLS-1$
			String age = Messages.getString("AdminCustomerController.101"); //$NON-NLS-1$
			String gender = Messages.getString("AdminCustomerController.102"); //$NON-NLS-1$
			String addr = Messages.getString("AdminCustomerController.103"); //$NON-NLS-1$
			String ph = Messages.getString("AdminCustomerController.104"); //$NON-NLS-1$
			String mail = Messages.getString("AdminCustomerController.105"); //$NON-NLS-1$

			String searchCustomerQuery = Messages.getString("AdminCustomerController.106") //$NON-NLS-1$
					+ customerid + Messages.getString("AdminCustomerController.107"); //$NON-NLS-1$
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

			if (gender.equals(Messages.getString("AdminCustomerController.108"))) { //$NON-NLS-1$
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
			System.out.println(Messages.getString("AdminCustomerController.109") + expireddate); //$NON-NLS-1$
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

		String query = Messages.getString("AdminCustomerController.110"); //$NON-NLS-1$

		String oldid = Messages.getString("AdminCustomerController.111"); //$NON-NLS-1$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldid = Messages.getString("AdminCustomerController.112") + rs.getString(1); //$NON-NLS-1$
		}

		// count +1 new Id
		String newId = Messages.getString("AdminCustomerController.113") + (Integer.parseInt(oldid) + 1); //$NON-NLS-1$
		tf_id.setText(newId);

		// create today date
		String pattern = Messages.getString("AdminCustomerController.114"); //$NON-NLS-1$
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminCustomerController.115") + todaydate); //$NON-NLS-1$
		tf_date_created.setText(todaydate);
		tf_last_date_used.setText(todaydate);

		// count expired date to 3 years
		String[] todaydateAry = todaydate.split(Messages.getString("AdminCustomerController.116")); //$NON-NLS-1$
		String day = todaydateAry[0];
		String month = todaydateAry[1];
		String year = todaydateAry[2];
		year = Messages.getString("AdminCustomerController.117") + (Integer.parseInt(year) + 3); //$NON-NLS-1$
		tf_expired_date.setText(day+Messages.getString("AdminCustomerController.118")+month+Messages.getString("AdminCustomerController.119") + year); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@FXML
	void onAddAction(ActionEvent event) throws SQLException {

		if (tf_id.getText().equals(Messages.getString("AdminCustomerController.120")) || tf_name.getText().equals(Messages.getString("AdminCustomerController.121")) || tf_age.getText().equals(Messages.getString("AdminCustomerController.122")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_addr.getText().equals(Messages.getString("AdminCustomerController.123")) || tf_phone.getText().equals(Messages.getString("AdminCustomerController.124")) || tf_mail.getText().equals(Messages.getString("AdminCustomerController.125")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_card_no.getText().equals(Messages.getString("AdminCustomerController.126")) || tf_amount.getText().equals(Messages.getString("AdminCustomerController.127")) || tf_pin.getText().equals(Messages.getString("AdminCustomerController.128")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_age.getText().matches(Messages.getString("AdminCustomerController.129")) || Integer.parseInt(tf_age.getText()) < 13 //$NON-NLS-1$
				|| tf_phone.getText().matches(Messages.getString("AdminCustomerController.130")) || tf_phone.getText().length() < 10 //$NON-NLS-1$
				|| !tf_mail.getText().contains(Messages.getString("AdminCustomerController.131")) || !tf_mail.getText().contains(Messages.getString("AdminCustomerController.132")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_amount.getText().matches(Messages.getString("AdminCustomerController.133")) || Double.parseDouble(tf_amount.getText()) < 500) { //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCustomerController.134")); //$NON-NLS-1$
			al.showAndWait();
		} else {

			// create today date
			String pattern = Messages.getString("AdminCustomerController.135"); //$NON-NLS-1$
			String todaydate = new SimpleDateFormat(pattern).format(new Date());

			// formatter for datepicker
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Messages.getString("AdminCustomerController.136")); //$NON-NLS-1$

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
			System.out.println(Messages.getString("AdminCustomerController.137") + expireddate); //$NON-NLS-1$
			String gender = Messages.getString("AdminCustomerController.138"); //$NON-NLS-1$
			if (rdo_male.isSelected()) {
				gender = Messages.getString("AdminCustomerController.139"); //$NON-NLS-1$
			} else {
				gender = Messages.getString("AdminCustomerController.140"); //$NON-NLS-1$
			}

			try {
			String addCustomerQuery = Messages.getString("AdminCustomerController.141") //$NON-NLS-1$
					+ Messages.getString("AdminCustomerController.142") + id + Messages.getString("AdminCustomerController.143") + name + Messages.getString("AdminCustomerController.144") + age + Messages.getString("AdminCustomerController.145") + gender + Messages.getString("AdminCustomerController.146") + addr + Messages.getString("AdminCustomerController.147") + ph + Messages.getString("AdminCustomerController.148") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
					+ mail + Messages.getString("AdminCustomerController.149"); //$NON-NLS-1$
			String updateCardQuery = Messages.getString("AdminCustomerController.150") + id + Messages.getString("AdminCustomerController.151") + amount //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("AdminCustomerController.152") + todaydate + Messages.getString("AdminCustomerController.153") + datecreated + Messages.getString("AdminCustomerController.154") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ expireddate + Messages.getString("AdminCustomerController.155") + pin + Messages.getString("AdminCustomerController.156") + cardno + Messages.getString("AdminCustomerController.157"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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
			String getTableDataQuery = Messages.getString("AdminCustomerController.158"); //$NON-NLS-1$
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
				cu.setPin(Messages.getString("AdminCustomerController.159") + getrs.getInt(13)); //$NON-NLS-1$

				customerData.add(cu);
			}
			// set to table
			tb_customer.refresh();
			// tb_category.setItems(categoryData);
			// show alert
			Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCustomerController.160")); //$NON-NLS-1$
			al.showAndWait();
			
			}//end of try
	    	catch(Exception ex) {
	    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCustomerController.161")+ex.getMessage()); //$NON-NLS-1$
	    		al.showAndWait();
	    	}
		} // end of else
	}

	@FXML
	void onUpdateAction(ActionEvent event) throws SQLException {

		if (tf_id.getText().equals(Messages.getString("AdminCustomerController.162")) || tf_name.getText().equals(Messages.getString("AdminCustomerController.163")) || tf_age.getText().equals(Messages.getString("AdminCustomerController.164")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_addr.getText().equals(Messages.getString("AdminCustomerController.165")) || tf_phone.getText().equals(Messages.getString("AdminCustomerController.166")) || tf_mail.getText().equals(Messages.getString("AdminCustomerController.167")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_card_no.getText().equals(Messages.getString("AdminCustomerController.168")) || tf_amount.getText().equals(Messages.getString("AdminCustomerController.169")) || tf_pin.getText().equals(Messages.getString("AdminCustomerController.170")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| tf_age.getText().matches(Messages.getString("AdminCustomerController.171")) || Integer.parseInt(tf_age.getText()) < 13 //$NON-NLS-1$
				|| tf_phone.getText().matches(Messages.getString("AdminCustomerController.172")) || tf_phone.getText().length() < 10 //$NON-NLS-1$
				|| !tf_mail.getText().contains(Messages.getString("AdminCustomerController.173")) || !tf_mail.getText().contains(Messages.getString("AdminCustomerController.174")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_amount.getText().matches(Messages.getString("AdminCustomerController.175")) || Double.parseDouble(tf_amount.getText()) < 3000) { //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCustomerController.176")); //$NON-NLS-1$
			al.showAndWait();
		} else {

			// create today date
			String pattern = Messages.getString("AdminCustomerController.177"); //$NON-NLS-1$
			String todaydate = new SimpleDateFormat(pattern).format(new Date());

			// formatter for datepicker
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Messages.getString("AdminCustomerController.178")); //$NON-NLS-1$

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

			String gender = Messages.getString("AdminCustomerController.179"); //$NON-NLS-1$
			if (rdo_male.isSelected()) {
				gender = Messages.getString("AdminCustomerController.180"); //$NON-NLS-1$
			} else {
				gender = Messages.getString("AdminCustomerController.181"); //$NON-NLS-1$
			}

			String updateCustomerQuery = Messages.getString("AdminCustomerController.182") + name + Messages.getString("AdminCustomerController.183") + age + Messages.getString("AdminCustomerController.184") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ gender + Messages.getString("AdminCustomerController.185") + addr + Messages.getString("AdminCustomerController.186") + ph + Messages.getString("AdminCustomerController.187") + mail + Messages.getString("AdminCustomerController.188") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ id + Messages.getString("AdminCustomerController.189"); //$NON-NLS-1$
			String updateCardQuery = Messages.getString("AdminCustomerController.190") + id + Messages.getString("AdminCustomerController.191") + amount //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("AdminCustomerController.192") + todaydate + Messages.getString("AdminCustomerController.193") + datecreated + Messages.getString("AdminCustomerController.194") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ expireddate + Messages.getString("AdminCustomerController.195") + pin + Messages.getString("AdminCustomerController.196") + cardno + Messages.getString("AdminCustomerController.197"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

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
			String getTableDataQuery = Messages.getString("AdminCustomerController.198"); //$NON-NLS-1$
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
				cu.setPin(Messages.getString("AdminCustomerController.199") + getrs.getInt(13)); //$NON-NLS-1$

				customerData.add(cu);
			}
			// set to table
			tb_customer.refresh();
			// show alert
			Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCustomerController.200")); //$NON-NLS-1$
			al.showAndWait();
		}
	}// end of first else
}
