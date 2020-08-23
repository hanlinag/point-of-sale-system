package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
		assert tb_card != null : "fx:id=\"tb_card\" was not injected: check your FXML file 'Admin_card.fxml'.";
		assert tf_card_no != null : "fx:id=\"tf_card_no\" was not injected: check your FXML file 'Admin_card.fxml'.";
		assert tf_pin != null : "fx:id=\"tf_pin\" was not injected: check your FXML file 'Admin_card.fxml'.";
		assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_card.fxml'.";
		assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'Admin_card.fxml'.";
		
		tf_card_no.setEditable(false);
		tf_pin.setEditable(false);

		col_item_card_no = new TableColumn<CardUser, String>("Card Number");
		col_item_amount = new TableColumn<CardUser, String>("Balance Amount");
		col_item_pin = new TableColumn<CardUser, String>("PIN");
		col_item_customer = new TableColumn<CardUser, String>("Customer ID");
		col_item_lastused_date = new TableColumn<CardUser, String>("Last Used Date");
		col_item_registerdate = new TableColumn<CardUser, String>("Register Date");
		col_item_expiredate = new TableColumn<CardUser, String>("Expired Date");

		col_item_card_no.setMinWidth(200.0);
		col_item_amount.setMinWidth(200.0);
		col_item_pin.setMinWidth(200.0);
		col_item_customer.setMinWidth(200.0);
		col_item_lastused_date.setMinWidth(200.0);
		col_item_registerdate.setMinWidth(200.0);
		col_item_expiredate.setMinWidth(200.0);

		col_item_card_no.setStyle("-fx-font-size: 18");
		col_item_amount.setStyle("-fx-font-size: 18");
		col_item_pin.setStyle("-fx-font-size: 18");
		col_item_customer.setStyle("-fx-font-size: 18");
		col_item_lastused_date.setStyle("-fx-font-size: 18");
		col_item_registerdate.setStyle("-fx-font-size: 18");
		col_item_expiredate.setStyle("-fx-font-size: 18");

		col_item_card_no.setCellValueFactory(new PropertyValueFactory<CardUser, String>("cardno"));
		col_item_amount.setCellValueFactory(new PropertyValueFactory<CardUser, String>("amount"));
		col_item_pin.setCellValueFactory(new PropertyValueFactory<CardUser, String>("pin"));
		col_item_customer.setCellValueFactory(new PropertyValueFactory<CardUser, String>("customrid"));
		col_item_lastused_date.setCellValueFactory(new PropertyValueFactory<CardUser, String>("lastdateused"));
		col_item_registerdate.setCellValueFactory(new PropertyValueFactory<CardUser, String>("registerdate"));
		col_item_expiredate.setCellValueFactory(new PropertyValueFactory<CardUser, String>("expireddate"));

		tb_card.getColumns().addAll(col_item_card_no, col_item_customer, col_item_amount, col_item_pin,
				col_item_lastused_date, col_item_registerdate, col_item_expiredate);

		// get data from db
		String query = "SELECT * FROM `Card`";
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
			c.setPin(""+rs.getInt(7));
			
			
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
    	            System.out.println("Double click is: "+ca.getCardno());
    	    
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

		if(tf_card_no.getText().equals("") || tf_pin.getText().equals("")) {
			Alert al = new Alert(AlertType.ERROR, "Invalid Input or Data Missing!");
			al.showAndWait();
		}
		else {
			

		
		String cardno = tf_card_no.getText().toString();
		String getpin = tf_pin.getText().toString();
		
		try {
		String queryAdd = "INSERT INTO `Card`(`cardnumber`, `customerid`, `amount`, `lastuseddate`, `registerdate`, `expireddate`, `pin`) "
				+ "VALUES ('"+cardno+"','','0','','','',"+getpin+")";
		
		new DBInitialize().DBInitialize();
    	new DBInitialize();
    	DBInitialize.statement.executeUpdate(queryAdd);
    	
    	//clear data array
    	cardData.clear();
    	
    	//get data set set  to db
    	// get data from db
    			String query = "SELECT * FROM `Card`";
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
    				c.setPin(""+rs.getInt(7));
    				
    				
    				cardData.add(c);
    			}
    			
    			//set data to table
    			tb_card.refresh();
    			
    			//clear tf
    			tf_card_no.clear();
    			tf_pin.clear();
    			
    			
    			//show alert
    			Alert al = new Alert(AlertType.INFORMATION, "Item added!");
    			al.showAndWait();
		
		}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
    		al.showAndWait();
    	}
		
		}//end of else
	}

	@FXML
	void onNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		tf_card_no.clear();
    	tf_pin.clear();
    	
    	String query = "SELECT Card.cardnumber FROM `Card` ORDER BY Card.cardnumber DESC LIMIT 1";
    	
    	String oldid = "";
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rsold = DBInitialize.statement.executeQuery(query);
    	while(rsold.next()) {
    		oldid = ""+rsold.getString(1);
    	}
    	
    	//count +1 new Id
    	String newId = ""+(Long.parseLong(oldid) + 1 );
    	tf_card_no.setText(newId);
    	
    	//generate a 4 digit integer 1000 <10000
    	int pin  = (int)(Math.random()*9000)+1000;
    	tf_pin.setText(""+pin);
	}
}
