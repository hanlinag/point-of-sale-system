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
import javafx.scene.image.Image;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class LoginController{


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
	    
	    //db
	   // private Statement statement;
	    private ResultSet resultSet;
	    private String dbQuery;
    	
	    private String realId ;
	    private String realPw ;
	    
		//strings for data from cashier db
	    private String name;
	    private int age;
	    private String gender;
	    private String addr;
	    private String ph;
	    private String mail;
	    private String date;

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	        assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Page_login.fxml'.";
	        assert tf_pass != null : "fx:id=\"tf_pass\" was not injected: check your FXML file 'Page_login.fxml'.";
	        assert bt_login != null : "fx:id=\"bt_login\" was not injected: check your FXML file 'Page_login.fxml'.";
	        assert bt_rdo_admin != null : "fx:id=\"bt_rdo_admin\" was not injected: check your FXML file 'Page_login.fxml'.";
	        assert usertype != null : "fx:id=\"usertype\" was not injected: check your FXML file 'Page_login.fxml'.";
	        assert bt_rdo_cashier != null : "fx:id=\"bt_rdo_cashier\" was not injected: check your FXML file 'Page_login.fxml'.";

	   
	        //database
	        new DBInitialize().DBInitialize();;
	        
	        bt_rdo_cashier.setSelected(true);
	    }
	    

/*	    private void dbInitialize() throws SQLException, ClassNotFoundException {
			// TODO Auto-generated method stub
	    	
	    	  // Load the JDBC driver
	    	Class.forName("com.mysql.jdbc.Driver"); System.out.println("Driver loaded");
	    	  // Connect to a database
	    	 Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:8889/ucsmpos","root","root");
	    	System.out.println("Database connected"); // Create a statement
	    	   statement = connection.createStatement();
	    	  // Execute a statement
	    	  
	    	  
	    	 
		}*/


		@FXML
	    void bt_login_action(ActionEvent event) throws SQLException {
	    	//get dat from 2 text field
			try {
	    	String id = tf_id.getText().toString();
	    	String pw = tf_pass.getText().toString();

	    	
	    	
	    	if(bt_rdo_admin.isSelected()) {
	    		System.out.println("Admin is seleted");
		    	
		    	realId = "pos-2018-ad"; //ucsmpos-2018-ad
		    	realPw = "admin@2018";//admin@2018

        	if(id.equals(realId) && pw.equals(realPw)) {
        		
	    		System.out.println("Success!");
	    		
	    		//scene transaction
	    		try {
					new MainAdmin().start((Stage)bt_login.getScene().getWindow());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    	}
	    	else{
	    		//System.out.println("Login Fail! User name or password incorrect! because realid is "+ realId+" and id is "+id+" real password is "+ realPw+ " password is "+pw);
	    		Alert alert = new Alert(AlertType.ERROR, " Login Fail !");
				alert.showAndWait();
				tf_id.clear();
				tf_pass.clear();

				System.out.println("login fail Error showed");
	    	}
        	
	    	}//end of admin selected
	    	else {
	    		
		    		System.out.println("Cashier is seleted");
		    		if(!isInteger(id+"")) {
		    			tf_id.clear();
		    			tf_pass.clear();
		    			
		    			System.out.println("cashier id enter is String...");
		    			Alert all = new Alert(AlertType.ERROR, "Invalid input!");
		    			all.showAndWait();
		    			
		    			
		    		}
		    		else {

		    		//if(id.equals(int))
			    	//get user name and password from db
			    	dbQuery = "SELECT * from cashier where id = "+id+";";
			    	resultSet = DBInitialize.statement.executeQuery(dbQuery);
			    	 
			    	if(resultSet.next()) {
			    	realId = ""+resultSet.getInt("id");
			    	realPw = resultSet.getString("password");
			    	
			    	name = resultSet.getString("name");
			    	age = resultSet.getInt("age");
			    	gender = resultSet.getString("gender");
			    	addr = resultSet.getString("address");
			    	ph = resultSet.getString("phone");
			    	mail = resultSet.getString("email");
			    	date = resultSet.getString("date created");
			    	
			    	System.out.println("Id is "+id);
			    	System.out.println("Password is "+pw);

		        	if(id.equals(realId) && pw.equals(realPw)) {
		        		
		        		//add cashier info to temp
		        		Common.cashierrec.setId(""+realId);
		        		Common.cashierrec.setPassword(realPw);
		        		Common.cashierrec.setName(name);
		        		Common.cashierrec.setGender(gender);
		        		Common.cashierrec.setAddress(addr);
		        		Common.cashierrec.setAge(""+age);
		        		Common.cashierrec.setPhone(ph);
		        		Common.cashierrec.setEmail(mail);
		        		Common.cashierrec.setDateCreated(date);
			    		System.out.println("Success!");
			    		System.out.println("cashier name is : "+Common.cashierrec.getName());
			    		
			    		//scene transaction
			    		try {
							new MainCashier().start((Stage)bt_login.getScene().getWindow());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	}
			    	else{
			    		//System.out.println("Login Fail! User name or password incorrect! because realid is "+ realId+" and id is "+id+" real password is "+ realPw+ " password is "+pw);
			    		Alert alert = new Alert(AlertType.ERROR, "Login fail! Incorrect Password");
						alert.showAndWait();
						tf_id.clear();
						tf_pass.clear();

						System.out.println("login fail Error showed");
			    	}
			    	 
			    	}//end of rs.next()
			    	else {
			    		Alert alert = new Alert(AlertType.ERROR, "No such user");
						alert.showAndWait();
						tf_id.clear();
						tf_pass.clear();

						System.out.println("no user Error showed");
			    		//System.out.println("No such user!!");
			    	}
		    	
		    	
	    	}
	    	
	    	}//end of check is Integer
			}
			catch(NumberFormatException nfe) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid input!");
				alert.showAndWait();

				System.out.println("input Error showed"+nfe.getMessage());
			}
			
			
	    }

		//for screen transaction from login to cashier panel
		public class MainCashier extends Application{

		    @Override
		    public void start(Stage primaryStage) throws Exception {
		        Parent root = FXMLLoader.load(getClass().getResource("/ui/cashier_main.fxml"));

	
		    
		        
		        Scene scene = new Scene(root, 1320, 700);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Cashier Panel");
				//primaryStage.sizeToScene();
				primaryStage.setResizable(false);
				primaryStage.getIcons().add(new Image("graphic/poslogorect.png"));
				primaryStage.setMaximized(false);
				primaryStage.show();
		    }
		}
		
		//for screen transaction from login to admin panel
		public class MainAdmin extends Application{

		    @Override
		    public void start(Stage primaryStage) throws Exception {
		        Parent root = FXMLLoader.load(getClass().getResource("/ui/Admin_panel.fxml"));

	
		    
		        
		        Scene scene = new Scene(root,1320,700);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Admin Panel");
				//primaryStage.sizeToScene();
				primaryStage.setResizable(false);
				primaryStage.getIcons().add(new Image("graphic/poslogorect.png"));
				primaryStage.setMaximized(false);
				primaryStage.show();
		    }
		}
		
		public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    } catch(NullPointerException e) {
		        return false;
		    }
		    // only got here if we didn't return false
		    return true;
		}
		
	}
