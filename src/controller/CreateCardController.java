package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CardUser;

public class CreateCardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField tf_phone;

    @FXML
    private JFXButton bt_create;

    @FXML
    private JFXButton bt_cancel;
    
    @FXML
    private JFXButton bt_new;


    @FXML
    private JFXTextField tf_expired_date;
    
    @FXML
    private JFXTextField tf_name;

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
    private JFXRadioButton rdo_male;

    @FXML
    private ToggleGroup gender;
    
    @FXML
    private Label lb_new_id;


    @FXML
    private JFXRadioButton rdo_female;

    
    @FXML
    private JFXTextField tf_date_created;

    @FXML
    private JFXTextField tf_card_number;

    private CardUser carduser = new CardUser();

    @FXML
    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        assert tf_phone != null : "fx:id=\"tf_phone\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert bt_create != null : "fx:id=\"bt_create\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert bt_cancel != null : "fx:id=\"bt_cancel\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_address != null : "fx:id=\"tf_address\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_pin != null : "fx:id=\"tf_pin\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_amount != null : "fx:id=\"tf_amount\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_top_up != null : "fx:id=\"tf_top_up\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert bt_redeem != null : "fx:id=\"bt_redeem\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_age != null : "fx:id=\"tf_age\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert rdo_male != null : "fx:id=\"rdo_male\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert rdo_female != null : "fx:id=\"rdo_female\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_expired_date != null : "fx:id=\"tf_expired_date\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_date_created != null : "fx:id=\"tf_date_created\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert tf_card_number != null : "fx:id=\"tf_card_number\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert lb_new_id != null : "fx:id=\"lb_new_id\" was not injected: check your FXML file 'Create_card.fxml'.";
        assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Create_card.fxml'.";

        tf_expired_date.setEditable(false);

       /* new Thread(() -> {
        	try {
				ServerSocket ss = new ServerSocket(5000);
			    System.out.println("Server is running at port : 5000");
			    
        		while(true) {
        			Socket s = ss.accept();
        			DataInputStream inputFromClient = new DataInputStream( s.getInputStream());
    				
        			String message =inputFromClient.readUTF();
                    System.out.println("Received from android: " + message);
                   // inputFromClient.close();
                   // s.close();
    				
    				
    				Platform.runLater(()->
    				tf_card_number.setText(""+message));

        		}
        	}
	        	catch(Exception ex) {
	        		
	        	}
        	
	        }).start();*/
        
       // tf_expired_date.setEditable(false);

      //get previous id and create now id
		new DBInitialize().DBInitialize();
		String previousgetcustomerid = " SELECT `id` FROM `Customer` ORDER BY `id` DESC LIMIT 1 ";
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(previousgetcustomerid);
		String previousid = "";
		while(rs.next()) {
			previousid = rs.getString("id");
		}
		int nowid = Integer.parseInt(previousid) + 1;
		carduser.setCustomrid(nowid+"");
		lb_new_id.setText(nowid+"");
		System.out.println("previous id is : "+previousid +" now id is : "+nowid);
		
		carduser.setGender("male");
        
        rdo_male.setOnAction(e->{
        	 if(rdo_male.isSelected()) {
        		 rdo_male.setSelected(true);
        		 rdo_female.setSelected(false);
        	 }else {
        		 rdo_male.setSelected(true);
        		 rdo_female.setSelected(false);
        	 }
        	 carduser.setGender("male");
        });
        
        rdo_female.setOnAction(e->{
       	 if(rdo_female.isSelected()) {
       		 rdo_male.setSelected(false);
       		 rdo_female.setSelected(true);
       	 }else {
       		rdo_male.setSelected(false);
       		rdo_female.setSelected(true);
       	 }
       	 carduser.setGender("female");
       });
       
        String pattern = "dd/MM/yyyy";
		String lastdateused =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("last date use is "+lastdateused);
		carduser.setLastdateused(lastdateused);
		carduser.setRegisterdate(lastdateused);
		tf_date_created.setText(carduser.getRegisterdate());
		
		String[] expireAry = lastdateused.split("/");
		String  day = expireAry[0];
		String month = expireAry[1];
		String year = expireAry[2];
		String expireYear = ""+(Integer.parseInt(year) + 3);
		tf_expired_date.setText(day+"/"+month+"/"+expireYear);
		
		
		tf_amount.setText("0");
    }
    
    @FXML
    void onBtCancelAction(ActionEvent event) {

    	((Stage)bt_cancel.getScene().getWindow()).close();
    }

    @FXML
    void onBtCreateAction(ActionEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

    	
		if(tf_card_number.getText().equals("") || tf_name.getText().equals("") || tf_phone.getText().equals("") || tf_address.getText().equals("") || tf_email.getText().equals("") || tf_pin.getText().equals("") || tf_amount.getText().equals("") || tf_age.getText().equals("")
				|| !tf_phone.getText().contains("09") || tf_phone.getText().length() < 10 || tf_phone.getText().matches(".*[a-zA-Z]+.*")  || !tf_email.getText().contains("mail.com") || !tf_email.getText().contains("@") || tf_age.getText().matches(".*[a-zA-Z]+.*")
				|| Integer.parseInt(tf_age.getText()) < 13 ) {
		
			Alert alert = new Alert(AlertType.ERROR, "Error!");
   		 	alert.showAndWait();
		}
		else {
				//carduser.setCustomrid(nowid+"");
    		 	carduser.setCardno(tf_card_number.getText().toString());
    			carduser.setName(tf_name.getText().toString());
    			carduser.setPhone(tf_phone.getText().toString());
    			carduser.setAddress(tf_address.getText().toString());
    			carduser.setEmail(tf_email.getText().toString());
    			carduser.setPin(tf_pin.getText().toString());
    			carduser.setAmount(tf_amount.getText().toString());
    			carduser.setAge(tf_age.getText().toString());
    			
    			//System.out.println("workkkkkkk");
    			//add data to db
/*    			String querycard = " INSERT INTO `Card`(`cardnumber`, `customerid`, `amount`, `lastuseddate`, `registerdate`, `expireddate`, `pin`)"
    					+ " VALUES ('"+carduser.getCardno()+"', '"+carduser.getCustomrid()+"', '"+carduser.getAmount()+"', '"+carduser.getLastdateused()+"', '"+carduser.getRegisterdate()+"', '"+carduser.getExpireddate()+"', "+carduser.getPin()+") ";
    			System.out.println("querycard : "+querycard);
    			new DBInitialize().DBInitialize();
    			new DBInitialize();
				DBInitialize.statement.executeUpdate(querycard);
				
	   		 	
	   		 	String querycustomer = " INSERT INTO `Customer`(`id`, `name`, `age`, `gender`, `address`, `phone`, `email`) "
	   		 			+ "VALUES ('"+carduser.getCustomrid()+"', '"+carduser.getName()+"', '"+carduser.getAge()+"', '"+carduser.getGender()+"', '"+carduser.getAddress()+"', '"+carduser.getPhone()+"', '"+carduser.getEmail()+"') ";
	   		 	
	   		 System.out.println("querycard : "+querycard);
	   		new DBInitialize().DBInitialize();
	   		new DBInitialize();
			DBInitialize.statement.executeUpdate(querycustomer);*/
    			//create today date
    	    	String pattern = "dd/MM/yyyy";
    			String todaydate =new SimpleDateFormat(pattern).format(new Date());
    			
    			String addCustomerQuery = "INSERT INTO `Customer`(`id`, `name`, `age`, `gender`, `address`, `phone`, `email`) "
    	    			+ "VALUES ('"+carduser.getCustomrid()+"','"+carduser.getName()+"','"+carduser.getAge()+"','"+carduser.getGender()+"','"+carduser.getAddress()+"','"+carduser.getPhone()+"','"+carduser.getEmail()+"')";
    	    	String updateCardQuery = "UPDATE `Card` SET `customerid`='"+carduser.getCustomrid()+"',`amount`='"+carduser.getAmount()+"',`lastuseddate`='"+todaydate+"',`registerdate`='"+todaydate+"',`expireddate`='"+carduser.getExpireddate()+"' WHERE `cardnumber`='"+carduser.getCardno()+"'";
    	    	
    	    	new DBInitialize();
    	    	DBInitialize.statement.executeUpdate(addCustomerQuery);
    	    	DBInitialize.statement.executeUpdate(updateCardQuery);
    			
				
			((Stage)bt_create.getScene().getWindow()).close();
			
				//show alert
				Alert alert = new Alert(AlertType.INFORMATION, "Successful!! Card is created.");
	   		 	alert.showAndWait();
    			
		}
		
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
    	try{
    		double topupamount = Double.parseDouble(tf_top_up.getText().toString());
        	double existingamount = Double.parseDouble(tf_amount.getText().toString());
        	
        	topupamount = topupamount + existingamount; 
    	
    	
    	carduser.setAmount(topupamount+"");
    	tf_amount.setText(carduser.getAmount());
    	
    	System.out.println("Do redeem "+carduser.getAmount());
    	
    	}
    	catch(Exception ex) {
    		System.out.println("exception on topup : "+ex.getMessage());
    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
			al.showAndWait();
    	}
    	
    	
		}
    	
    	tf_top_up.clear();
    }
    
    @FXML
    void onNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

    	new DBInitialize().DBInitialize();
		String previousgetcustomerid = " SELECT `id` FROM `Customer` ORDER BY `id` DESC LIMIT 1 ";
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(previousgetcustomerid);
		String previousid = "";
		while(rs.next()) {
			previousid = rs.getString("id");
		}
		int nowid = Integer.parseInt(previousid) + 1;
		carduser.setCustomrid(nowid+"");
		lb_new_id.setText(nowid+"");
		System.out.println("previous id is : "+previousid +" now id is : "+nowid);
		
		tf_phone.clear();
		tf_name.clear();
		tf_address.clear();
		tf_email.clear();
		tf_pin.clear();
		tf_amount.clear();
		tf_top_up.clear();
		tf_age.clear();
		tf_card_number.clear();
    }
    
    @FXML
    void tfCardNumberAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

    	String cardno = tf_card_number.getText().toString();
		
		//search card by card no and add info to the tf
		String searchCardQuery = "SELECT * FROM `Card` WHERE Card.cardnumber = '"+cardno+"';";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rssearch = DBInitialize.statement.executeQuery(searchCardQuery);
		
		
		
		String datecreated = "";
		String expireddate = "";
		String lastDateUsed = "";
		String customerid = "";
		String pin ="";
		
		if(rssearch.next()) {
			
			expireddate = rssearch.getString("expireddate");
			//tf_amount.setText(rssearch.getString("amount"));
			lastDateUsed = rssearch.getString("lastuseddate");
			datecreated = rssearch.getString("registerdate");
			pin = ""+rssearch.getInt("pin");
			customerid = rssearch.getString("customerid");
			
		}else {
			//show alert
   	    	Alert al = new Alert(AlertType.ERROR, " Invalid Card!");
   			al.showAndWait();
		}
		
		if(customerid.equals("")) {
			tf_pin.setText(pin);
   			
		}else {
			//show alert
   	    	Alert al = new Alert(AlertType.ERROR, " Card Already Used!");
   			al.showAndWait();
		}
    }
    
}
