package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CardUser;

public class AdminCardController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<CardUser> tb_card;

	@FXML
	private JFXTextField tf_card_no;

	@FXML
	private JFXTextField tf_pin;

	@FXML
	private JFXButton bt_new;

	@FXML
	private JFXButton bt_add;

	private TableColumn<CardUser, String> col_item_card_no;
	private TableColumn<CardUser, String> col_item_amount;
	private TableColumn<CardUser, String> col_item_pin;
	private TableColumn<CardUser, String> col_item_customer;
	private TableColumn<CardUser, String> col_item_lastused_date;
	private TableColumn<CardUser, String> col_item_registerdate;
	private TableColumn<CardUser, String> col_item_expiredate;

	private ObservableList<CardUser> cardData = FXCollections.observableArrayList();

	@FXML
	void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		assert tb_card != null : Messages.getString("AdminCardController.0"); //$NON-NLS-1$
		assert tf_card_no != null : Messages.getString("AdminCardController.1"); //$NON-NLS-1$
		assert tf_pin != null : Messages.getString("AdminCardController.2"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("AdminCardController.3"); //$NON-NLS-1$
		assert bt_add != null : Messages.getString("AdminCardController.4"); //$NON-NLS-1$
		
		tf_card_no.setEditable(false);
		tf_pin.setEditable(false);

		col_item_card_no = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.5")); //$NON-NLS-1$
		col_item_amount = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.6")); //$NON-NLS-1$
		col_item_pin = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.7")); //$NON-NLS-1$
		col_item_customer = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.8")); //$NON-NLS-1$
		col_item_lastused_date = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.9")); //$NON-NLS-1$
		col_item_registerdate = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.10")); //$NON-NLS-1$
		col_item_expiredate = new TableColumn<CardUser, String>(Messages.getString("AdminCardController.11")); //$NON-NLS-1$

		col_item_card_no.setMinWidth(200.0);
		col_item_amount.setMinWidth(200.0);
		col_item_pin.setMinWidth(200.0);
		col_item_customer.setMinWidth(200.0);
		col_item_lastused_date.setMinWidth(200.0);
		col_item_registerdate.setMinWidth(200.0);
		col_item_expiredate.setMinWidth(200.0);

		col_item_card_no.setStyle(Messages.getString("AdminCardController.12")); //$NON-NLS-1$
		col_item_amount.setStyle(Messages.getString("AdminCardController.13")); //$NON-NLS-1$
		col_item_pin.setStyle(Messages.getString("AdminCardController.14")); //$NON-NLS-1$
		col_item_customer.setStyle(Messages.getString("AdminCardController.15")); //$NON-NLS-1$
		col_item_lastused_date.setStyle(Messages.getString("AdminCardController.16")); //$NON-NLS-1$
		col_item_registerdate.setStyle(Messages.getString("AdminCardController.17")); //$NON-NLS-1$
		col_item_expiredate.setStyle(Messages.getString("AdminCardController.18")); //$NON-NLS-1$

		col_item_card_no.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.19"))); //$NON-NLS-1$
		col_item_amount.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.20"))); //$NON-NLS-1$
		col_item_pin.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.21"))); //$NON-NLS-1$
		col_item_customer.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.22"))); //$NON-NLS-1$
		col_item_lastused_date.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.23"))); //$NON-NLS-1$
		col_item_registerdate.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.24"))); //$NON-NLS-1$
		col_item_expiredate.setCellValueFactory(new PropertyValueFactory<CardUser, String>(Messages.getString("AdminCardController.25"))); //$NON-NLS-1$

		tb_card.getColumns().addAll(col_item_card_no, col_item_customer, col_item_amount, col_item_pin,
				col_item_lastused_date, col_item_registerdate, col_item_expiredate);

		// get data from db
		String query = Messages.getString("AdminCardController.26"); //$NON-NLS-1$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while(rs.next()) {
			CardUser c = new CardUser();
			c.setCardno(rs.getString(1));
			c.setCustomrid(rs.getString(2));
			c.setAmount(rs.getString(3));
			c.setLastdateused(rs.getString(4));
			c.setRegisterdate(rs.getString(5));
			c.setExpireddate(rs.getString(6));
			c.setPin(Messages.getString("AdminCardController.27")+rs.getInt(7)); //$NON-NLS-1$
			
			
			cardData.add(c);
		}
		
		//set data to table
		tb_card.setItems(cardData);
		
		//row double click action
        tb_card.setRowFactory(t -> {
    		TableRow<CardUser> row = new TableRow<>();
    		row.setOnMouseClicked(e -> {
    			//get data from selected row
    	
    	        if (e.getClickCount() == 2 && (! row.isEmpty()) ) {
    	        	CardUser ca = tb_card.getSelectionModel().getSelectedItem();
    	            System.out.println(Messages.getString("AdminCardController.28")+ca.getCardno()); //$NON-NLS-1$
    	    
    	            //set data to tf
    	            tf_card_no.setText(ca.getCardno());
    	            tf_pin.setText(ca.getPin());
    
    	        }
    		});
    		return row;
    		
		});

	}

	@FXML
	void onAddAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_card_no.getText().equals(Messages.getString("AdminCardController.29")) || tf_pin.getText().equals(Messages.getString("AdminCardController.30"))) { //$NON-NLS-1$ //$NON-NLS-2$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCardController.31")); //$NON-NLS-1$
			al.showAndWait();
		}
		else {
			

		
		String cardno = tf_card_no.getText().toString();
		String getpin = tf_pin.getText().toString();
		
		try {
		String queryAdd = Messages.getString("AdminCardController.32") //$NON-NLS-1$
				+ Messages.getString("AdminCardController.33")+cardno+Messages.getString("AdminCardController.34")+getpin+Messages.getString("AdminCardController.35"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		new DBInitialize().DBInitialize();
    	new DBInitialize();
    	DBInitialize.statement.executeUpdate(queryAdd);
    	
    	//clear data array
    	cardData.clear();
    	
    	//get data set set  to db
    	// get data from db
    			String query = Messages.getString("AdminCardController.36"); //$NON-NLS-1$
    			new DBInitialize().DBInitialize();
    			new DBInitialize();
    			ResultSet rs = DBInitialize.statement.executeQuery(query);
    			while(rs.next()) {
    				CardUser c = new CardUser();
    				c.setCardno(rs.getString(1));
    				c.setCustomrid(rs.getString(2));
    				c.setAmount(rs.getString(3));
    				c.setLastdateused(rs.getString(4));
    				c.setRegisterdate(rs.getString(5));
    				c.setExpireddate(rs.getString(6));
    				c.setPin(Messages.getString("AdminCardController.37")+rs.getInt(7)); //$NON-NLS-1$
    				
    				
    				cardData.add(c);
    			}
    			
    			//set data to table
    			tb_card.refresh();
    			
    			//clear tf
    			tf_card_no.clear();
    			tf_pin.clear();
    			
    			
    			//show alert
    			Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCardController.38")); //$NON-NLS-1$
    			al.showAndWait();
		
		}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCardController.39")+ex.getMessage()); //$NON-NLS-1$
    		al.showAndWait();
    	}
		
		}//end of else
	}

	@FXML
	void onNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		tf_card_no.clear();
    	tf_pin.clear();
    	
    	String query = Messages.getString("AdminCardController.40"); //$NON-NLS-1$
    	
    	String oldid = Messages.getString("AdminCardController.41"); //$NON-NLS-1$
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rsold = DBInitialize.statement.executeQuery(query);
    	while(rsold.next()) {
    		oldid = Messages.getString("AdminCardController.42")+rsold.getString(1); //$NON-NLS-1$
    	}
    	
    	//count +1 new Id
    	String newId = Messages.getString("AdminCardController.43")+(Long.parseLong(oldid) + 1 ); //$NON-NLS-1$
    	tf_card_no.setText(newId);
    	
    	//generate a 4 digit integer 1000 <10000
    	int pin  = (int)(Math.random()*9000)+1000;
    	tf_pin.setText(Messages.getString("AdminCardController.44")+pin); //$NON-NLS-1$
	}
}
