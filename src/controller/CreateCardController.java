package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
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
        assert tf_phone != null : Messages.getString("CreateCardController.0"); //$NON-NLS-1$
        assert bt_create != null : Messages.getString("CreateCardController.1"); //$NON-NLS-1$
        assert bt_cancel != null : Messages.getString("CreateCardController.2"); //$NON-NLS-1$
        assert tf_name != null : Messages.getString("CreateCardController.3"); //$NON-NLS-1$
        assert tf_address != null : Messages.getString("CreateCardController.4"); //$NON-NLS-1$
        assert tf_email != null : Messages.getString("CreateCardController.5"); //$NON-NLS-1$
        assert tf_pin != null : Messages.getString("CreateCardController.6"); //$NON-NLS-1$
        assert tf_amount != null : Messages.getString("CreateCardController.7"); //$NON-NLS-1$
        assert tf_top_up != null : Messages.getString("CreateCardController.8"); //$NON-NLS-1$
        assert bt_redeem != null : Messages.getString("CreateCardController.9"); //$NON-NLS-1$
        assert tf_age != null : Messages.getString("CreateCardController.10"); //$NON-NLS-1$
        assert rdo_male != null : Messages.getString("CreateCardController.11"); //$NON-NLS-1$
        assert gender != null : Messages.getString("CreateCardController.12"); //$NON-NLS-1$
        assert rdo_female != null : Messages.getString("CreateCardController.13"); //$NON-NLS-1$
        assert tf_expired_date != null : Messages.getString("CreateCardController.14"); //$NON-NLS-1$
        assert tf_date_created != null : Messages.getString("CreateCardController.15"); //$NON-NLS-1$
        assert tf_card_number != null : Messages.getString("CreateCardController.16"); //$NON-NLS-1$
        assert lb_new_id != null : Messages.getString("CreateCardController.17"); //$NON-NLS-1$
        assert bt_new != null : Messages.getString("CreateCardController.18"); //$NON-NLS-1$

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
		String previousgetcustomerid = Messages.getString("CreateCardController.19"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(previousgetcustomerid);
		String previousid = Messages.getString("CreateCardController.20"); //$NON-NLS-1$
		while(rs.next()) {
			previousid = rs.getString(Messages.getString("CreateCardController.21")); //$NON-NLS-1$
		}
		int nowid = Integer.parseInt(previousid) + 1;
		carduser.setCustomrid(nowid+Messages.getString("CreateCardController.22")); //$NON-NLS-1$
		lb_new_id.setText(nowid+Messages.getString("CreateCardController.23")); //$NON-NLS-1$
		System.out.println(Messages.getString("CreateCardController.24")+previousid +Messages.getString("CreateCardController.25")+nowid); //$NON-NLS-1$ //$NON-NLS-2$
		
		carduser.setGender(Messages.getString("CreateCardController.26")); //$NON-NLS-1$
        
        rdo_male.setOnAction(e->{
        	 if(rdo_male.isSelected()) {
        		 rdo_male.setSelected(true);
        		 rdo_female.setSelected(false);
        	 }else {
        		 rdo_male.setSelected(true);
        		 rdo_female.setSelected(false);
        	 }
        	 carduser.setGender(Messages.getString("CreateCardController.27")); //$NON-NLS-1$
        });
        
        rdo_female.setOnAction(e->{
       	 if(rdo_female.isSelected()) {
       		 rdo_male.setSelected(false);
       		 rdo_female.setSelected(true);
       	 }else {
       		rdo_male.setSelected(false);
       		rdo_female.setSelected(true);
       	 }
       	 carduser.setGender(Messages.getString("CreateCardController.28")); //$NON-NLS-1$
       });
       
        String pattern = Messages.getString("CreateCardController.29"); //$NON-NLS-1$
		String lastdateused =new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("CreateCardController.30")+lastdateused); //$NON-NLS-1$
		carduser.setLastdateused(lastdateused);
		carduser.setRegisterdate(lastdateused);
		tf_date_created.setText(carduser.getRegisterdate());
		
		String[] expireAry = lastdateused.split(Messages.getString("CreateCardController.31")); //$NON-NLS-1$
		String  day = expireAry[0];
		String month = expireAry[1];
		String year = expireAry[2];
		String expireYear = Messages.getString("CreateCardController.32")+(Integer.parseInt(year) + 3); //$NON-NLS-1$
		tf_expired_date.setText(day+Messages.getString("CreateCardController.33")+month+Messages.getString("CreateCardController.34")+expireYear); //$NON-NLS-1$ //$NON-NLS-2$
		
		
		tf_amount.setText(Messages.getString("CreateCardController.35")); //$NON-NLS-1$
    }
    
    @FXML
    void onBtCancelAction(ActionEvent event) {

    	((Stage)bt_cancel.getScene().getWindow()).close();
    }

    @FXML
    void onBtCreateAction(ActionEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

    	
		if(tf_card_number.getText().equals(Messages.getString("CreateCardController.36")) || tf_name.getText().equals(Messages.getString("CreateCardController.37")) || tf_phone.getText().equals(Messages.getString("CreateCardController.38")) || tf_address.getText().equals(Messages.getString("CreateCardController.39")) || tf_email.getText().equals(Messages.getString("CreateCardController.40")) || tf_pin.getText().equals(Messages.getString("CreateCardController.41")) || tf_amount.getText().equals(Messages.getString("CreateCardController.42")) || tf_age.getText().equals(Messages.getString("CreateCardController.43")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
				|| !tf_phone.getText().contains(Messages.getString("CreateCardController.44")) || tf_phone.getText().length() < 10 || tf_phone.getText().matches(Messages.getString("CreateCardController.45"))  || !tf_email.getText().contains(Messages.getString("CreateCardController.46")) || !tf_email.getText().contains(Messages.getString("CreateCardController.47")) || tf_age.getText().matches(Messages.getString("CreateCardController.48")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				|| Integer.parseInt(tf_age.getText()) < 13 ) {
		
			Alert alert = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.49")); //$NON-NLS-1$
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
    	    	String pattern = Messages.getString("CreateCardController.50"); //$NON-NLS-1$
    			String todaydate =new SimpleDateFormat(pattern).format(new Date());
    			
    			String addCustomerQuery = Messages.getString("CreateCardController.51") //$NON-NLS-1$
    	    			+ Messages.getString("CreateCardController.52")+carduser.getCustomrid()+Messages.getString("CreateCardController.53")+carduser.getName()+Messages.getString("CreateCardController.54")+carduser.getAge()+Messages.getString("CreateCardController.55")+carduser.getGender()+Messages.getString("CreateCardController.56")+carduser.getAddress()+Messages.getString("CreateCardController.57")+carduser.getPhone()+Messages.getString("CreateCardController.58")+carduser.getEmail()+Messages.getString("CreateCardController.59"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
    	    	String updateCardQuery = Messages.getString("CreateCardController.60")+carduser.getCustomrid()+Messages.getString("CreateCardController.61")+carduser.getAmount()+Messages.getString("CreateCardController.62")+todaydate+Messages.getString("CreateCardController.63")+todaydate+Messages.getString("CreateCardController.64")+carduser.getExpireddate()+Messages.getString("CreateCardController.65")+carduser.getCardno()+Messages.getString("CreateCardController.66"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
    	    	
    	    	new DBInitialize();
    	    	DBInitialize.statement.executeUpdate(addCustomerQuery);
    	    	DBInitialize.statement.executeUpdate(updateCardQuery);
    			
				
			((Stage)bt_create.getScene().getWindow()).close();
			
				//show alert
				Alert alert = new Alert(AlertType.INFORMATION, Messages.getString("CreateCardController.67")); //$NON-NLS-1$
	   		 	alert.showAndWait();
    			
		}
		
    }

    @FXML
    void onBtRedeemAction(ActionEvent event) {
    	
    	if( tf_top_up.getText().equals(Messages.getString("CreateCardController.68")) || tf_top_up.getText().matches(Messages.getString("CreateCardController.69"))) { //$NON-NLS-1$ //$NON-NLS-2$
    	
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.70")); //$NON-NLS-1$
			al.showAndWait();
    	}

    else if (Double.parseDouble(tf_top_up.getText().toString()) < 500 ) {
			Alert al = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.71")); //$NON-NLS-1$
			al.showAndWait();
		} else {
    	try{
    		double topupamount = Double.parseDouble(tf_top_up.getText().toString());
        	double existingamount = Double.parseDouble(tf_amount.getText().toString());
        	
        	topupamount = topupamount + existingamount; 
    	
    	
    	carduser.setAmount(topupamount+Messages.getString("CreateCardController.72")); //$NON-NLS-1$
    	tf_amount.setText(carduser.getAmount());
    	
    	System.out.println(Messages.getString("CreateCardController.73")+carduser.getAmount()); //$NON-NLS-1$
    	
    	}
    	catch(Exception ex) {
    		System.out.println(Messages.getString("CreateCardController.74")+ex.getMessage()); //$NON-NLS-1$
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.75")+ex.getMessage()); //$NON-NLS-1$
			al.showAndWait();
    	}
    	
    	
		}
    	
    	tf_top_up.clear();
    }
    
    @FXML
    void onNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

    	new DBInitialize().DBInitialize();
		String previousgetcustomerid = Messages.getString("CreateCardController.76"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(previousgetcustomerid);
		String previousid = Messages.getString("CreateCardController.77"); //$NON-NLS-1$
		while(rs.next()) {
			previousid = rs.getString(Messages.getString("CreateCardController.78")); //$NON-NLS-1$
		}
		int nowid = Integer.parseInt(previousid) + 1;
		carduser.setCustomrid(nowid+Messages.getString("CreateCardController.79")); //$NON-NLS-1$
		lb_new_id.setText(nowid+Messages.getString("CreateCardController.80")); //$NON-NLS-1$
		System.out.println(Messages.getString("CreateCardController.81")+previousid +Messages.getString("CreateCardController.82")+nowid); //$NON-NLS-1$ //$NON-NLS-2$
		
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
		String searchCardQuery = Messages.getString("CreateCardController.83")+cardno+Messages.getString("CreateCardController.84"); //$NON-NLS-1$ //$NON-NLS-2$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rssearch = DBInitialize.statement.executeQuery(searchCardQuery);
		
		
		
		String datecreated = Messages.getString("CreateCardController.85"); //$NON-NLS-1$
		String expireddate = Messages.getString("CreateCardController.86"); //$NON-NLS-1$
		String lastDateUsed = Messages.getString("CreateCardController.87"); //$NON-NLS-1$
		String customerid = Messages.getString("CreateCardController.88"); //$NON-NLS-1$
		String pin =Messages.getString("CreateCardController.89"); //$NON-NLS-1$
		
		if(rssearch.next()) {
			
			expireddate = rssearch.getString(Messages.getString("CreateCardController.90")); //$NON-NLS-1$
			//tf_amount.setText(rssearch.getString("amount"));
			lastDateUsed = rssearch.getString(Messages.getString("CreateCardController.91")); //$NON-NLS-1$
			datecreated = rssearch.getString(Messages.getString("CreateCardController.92")); //$NON-NLS-1$
			pin = Messages.getString("CreateCardController.93")+rssearch.getInt(Messages.getString("CreateCardController.94")); //$NON-NLS-1$ //$NON-NLS-2$
			customerid = rssearch.getString(Messages.getString("CreateCardController.95")); //$NON-NLS-1$
			
		}else {
			//show alert
   	    	Alert al = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.96")); //$NON-NLS-1$
   			al.showAndWait();
		}
		
		if(customerid.equals(Messages.getString("CreateCardController.97"))) { //$NON-NLS-1$
			tf_pin.setText(pin);
   			
		}else {
			//show alert
   	    	Alert al = new Alert(AlertType.ERROR, Messages.getString("CreateCardController.98")); //$NON-NLS-1$
   			al.showAndWait();
		}
    }
    
}
