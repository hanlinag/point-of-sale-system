package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import model.Cashier;

public class AdminCashierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Cashier> tb_cashier;
    
    private TableColumn<Cashier, String> col_item_id;

	private TableColumn<Cashier, String> col_item_name;

	private TableColumn<Cashier, String> col_item_age;

	private TableColumn<Cashier, String> col_item_gender;

	private TableColumn<Cashier, String> col_item_addr;

	private TableColumn<Cashier, String> col_item_phone;

	private TableColumn<Cashier, String> col_item_email;

	private TableColumn<Cashier, String> col_item_password;
	
	private TableColumn<Cashier, String> col_item_date_created;

	private ObservableList<Cashier> cashierData = FXCollections.observableArrayList();
	

    @FXML
    private JFXButton bt_cashier_add;

    @FXML
    private JFXButton bt_cashier_update;

    @FXML
    private JFXTextField tf_id;

    @FXML
    private JFXTextField tf_name;

    @FXML
    private JFXTextField tf_age;

    @FXML
    private JFXRadioButton rdo_male;

    @FXML
    private ToggleGroup gender;

    @FXML
    private JFXRadioButton rdo_female;

    @FXML
    private JFXTextField tf_addr;

    @FXML
    private JFXTextField tf_phone;

    @FXML
    private JFXTextField tf_email;

    @FXML
    private JFXTextField tf_password;

    @FXML
    private JFXTextField tf_desc;
    
    @FXML
    private JFXTextField tf_date_created;
    
    @FXML
    private JFXButton bt_new;

    @FXML
    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        assert tb_cashier != null : "fx:id=\"tb_cashier\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert bt_cashier_add != null : "fx:id=\"bt_cashier_add\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert bt_cashier_update != null : "fx:id=\"bt_cashier_update\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_age != null : "fx:id=\"tf_age\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert rdo_male != null : "fx:id=\"rdo_male\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert rdo_female != null : "fx:id=\"rdo_female\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_addr != null : "fx:id=\"tf_addr\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_phone != null : "fx:id=\"tf_phone\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_email != null : "fx:id=\"tf_email\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_password != null : "fx:id=\"tf_password\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert tf_date_created != null : "fx:id=\"tf_date_created\" was not injected: check your FXML file 'Admin_cashier.fxml'.";
        assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_cashier.fxml'.";


        tf_date_created.setEditable(true);
        
        //set today date
        String pattern = "dd/MM/yyyy";
		String datecreated =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("last date use is "+datecreated);
		tf_date_created.setEditable(false);
		tf_date_created.setText(datecreated);

        col_item_id = new TableColumn<Cashier, String>("ID");
        col_item_name = new TableColumn<Cashier, String>("Name");
        col_item_age = new TableColumn<Cashier, String>("Age");
        col_item_gender = new TableColumn<Cashier, String>("Gender");
        col_item_addr = new TableColumn<Cashier, String>("Address");
        col_item_phone = new TableColumn<Cashier, String>("Phone");
        col_item_email = new TableColumn<Cashier, String>("Email");
        col_item_password = new TableColumn<Cashier, String>("Password");
        col_item_date_created = new TableColumn<Cashier, String>("Date Created");
        
        col_item_id.setMinWidth(90.0);
        col_item_name.setMinWidth(180.0);
        col_item_age.setMinWidth(50.0);
        col_item_gender.setMinWidth(70.0);
        col_item_addr.setMinWidth(230.0);
        col_item_phone.setMinWidth(200.0);
        col_item_email.setMinWidth(280.0);
        col_item_password.setMinWidth(150.0);
        col_item_date_created.setMinWidth(120.0);
        
        col_item_id.setStyle("-fx-font-size: 18");
        col_item_name.setStyle("-fx-font-size: 18");
        col_item_age.setStyle("-fx-font-size: 18");
        col_item_gender.setStyle("-fx-font-size: 18");
        col_item_addr.setStyle("-fx-font-size: 18");
        col_item_phone.setStyle("-fx-font-size: 18");
        col_item_email.setStyle("-fx-font-size: 18");
        col_item_password.setStyle("-fx-font-size: 18");
        col_item_date_created.setStyle("-fx-font-size: 18");
        
        
        col_item_id.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("id"));
        col_item_name.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("name"));
        col_item_age.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("age"));
        col_item_gender.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("gender"));
        col_item_addr.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("address"));
        col_item_phone.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("phone"));
        col_item_email.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("email"));
        col_item_password.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("password"));
        col_item_date_created.setCellValueFactory(
        	    new PropertyValueFactory<Cashier, String>("dateCreated"));
        
        tb_cashier.getColumns().addAll(col_item_id
        		,col_item_name
        		,col_item_age
        		,col_item_gender
        		,col_item_addr
        		,col_item_phone
        		,col_item_email
        		,col_item_password
        		,col_item_date_created);
        
        
        //get data in cashier table from database
        String query = "SELECT * FROM `cashier` ORDER BY cashier.id DESC;";
        new DBInitialize().DBInitialize();
        
        new DBInitialize();
        ResultSet rs = DBInitialize.statement.executeQuery(query);
        while (rs.next()) {
        	Cashier cashier = new Cashier();
        	cashier.setId(""+rs.getInt(1));
        	cashier.setName(rs.getString(2));
        	cashier.setAge(""+rs.getString(3));
        	cashier.setGender(rs.getString(4));
        	cashier.setAddress(rs.getString(5));
        	cashier.setPhone(rs.getString(6));
        	cashier.setEmail(rs.getString(7));
        	cashier.setPassword(rs.getString(8));
        	cashier.setDateCreated(rs.getString(9));
        	
        	cashierData.add(cashier);
        }
        
        //set data to table
        tb_cashier.setItems(cashierData);
        
        
        tb_cashier.setRowFactory(t -> {
    		TableRow<Cashier> row = new TableRow<>();
    		row.setOnMouseClicked(e -> {
    			//get data from selected row
    	
    	        if (e.getClickCount() == 2 && (! row.isEmpty()) ) {
    	            Cashier cashier = tb_cashier.getSelectionModel().getSelectedItem();
    	            System.out.println("Double click is: "+cashier.getName());
    	    
    	            //get gender data 
    	            String gender = cashier.getGender();
    	            if(gender.equals("male")) {
    	            	rdo_male.setSelected(true);
    	            	rdo_female.setSelected(false);
    	            }else {
    	            	rdo_female.setSelected(true);
    	            	rdo_male.setSelected(false);
    	            }
    	            
    	            //add data to the text field
    	            tf_id.setText(cashier.getId());
    	            tf_name.setText(cashier.getName());
    	            tf_age.setText(cashier.getAge());
    	            tf_addr.setText(cashier.getAddress());
    	            tf_phone.setText(cashier.getPhone());
    	            tf_email.setText(cashier.getEmail());
    	            tf_password.setText(cashier.getPassword());
    	            tf_date_created.setText(cashier.getDateCreated());
    	            
    	        }
    		});
    		
    		final ContextMenu rowMenu = new ContextMenu();
            
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                	Cashier ca = tb_cashier.getSelectionModel().getSelectedItem();

                	Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + ca.getName() + " ?", ButtonType.YES, ButtonType.NO);
                	alert.showAndWait();

                	if (alert.getResult() == ButtonType.YES) {
                	    //do stuff
                     	String removequery = "DELETE FROM `cashier` WHERE  cashier.id = '"+ca.getId()+"';";
                    	try {
    						new DBInitialize().DBInitialize();
    						new DBInitialize();
    	                	DBInitialize.statement.executeUpdate(removequery);
    	                	
    	                	//update table
    	            		//update table data
    	           		 new DBInitialize().DBInitialize();
    	           	        String queryupdatetable = "SELECT * FROM `cashier` ORDER BY cashier.id DESC;";
    	           	        
    	           	        new DBInitialize();
    	           			ResultSet rsss = DBInitialize.statement.executeQuery(queryupdatetable);
    	           			cashierData.clear();
    	           			
    	           			while(rsss.next()) {
    	           				
    	           				Cashier cashier = new Cashier();
    	           	        	cashier.setId(""+rsss.getInt(1));
    	           	        	cashier.setName(rsss.getString(2));
    	           	        	cashier.setAge(""+rsss.getString(3));
    	           	        	cashier.setGender(rsss.getString(4));
    	           	        	cashier.setAddress(rsss.getString(5));
    	           	        	cashier.setPhone(rsss.getString(6));
    	           	        	cashier.setEmail(rsss.getString(7));
    	           	        	cashier.setPassword(rsss.getString(8));
    	           	        	cashier.setDateCreated(rsss.getString(9));
    	           				
    	           				cashierData.add(cashier);
    	           				
    	           			}
    	           			//tb_product_item.getItems().clear();
    	           			tb_cashier.refresh();
    	           			
    	           			
    	                	
    	                	/*//show alert
    	        			Alert al = new Alert(AlertType.INFORMATION, "Item removed!");
    	        			al.showAndWait();*/
    	           			
    	           		//show alert
    	           	    	Alert al = new Alert(AlertType.INFORMATION, "Item deleted!");
    	           			al.showAndWait();
    	                	
    	                	
    					} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                    
                	}
                	
           	
                }
            });
            rowMenu.getItems().addAll( removeItem);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
              Bindings.when(Bindings.isNotNull(row.itemProperty()))
              .then(rowMenu)
              .otherwise((ContextMenu)null));
            

    		return row;
    		
    		});
    }

    

    @FXML
    void onbtNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
    	

    	
    	//clear
    	tf_id.clear();
        tf_name.clear();
        tf_age.clear();
        tf_addr.clear();
        tf_phone.clear();
        tf_email.clear();
        tf_password.clear();
        tf_date_created.clear();
        
        String getoldidquery = "SELECT cashier.id FROM cashier ORDER BY cashier.id DESC LIMIT 1";
    	
    	String olCadid = "";
    	System.out.println("old id is "+olCadid); 
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rsgetid = DBInitialize.statement.executeQuery(getoldidquery);
    	while(rsgetid.next()) {
    		olCadid = ""+rsgetid.getInt(1);
    	}
    	
    	System.out.println("old id is "+olCadid); 
    	
    	//count +1 new Id
    	String newCaId = ""+(Integer.parseInt(olCadid) + 1 );
    	tf_id.setText(newCaId);
    	
    	System.out.println("new id is "+newCaId); 
    	
    	//create today date
    	String pattern = "dd/MM/yyyy";
		String todaydate =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("today is "+todaydate);
		tf_date_created.setText(todaydate);

    }
    

    @FXML
    void onBtAddAction(ActionEvent event) throws SQLException {

    	if(tf_name.getText().equals("") || tf_id.getText().equals("") || tf_age.getText().equals("") || tf_addr.getText().equals("") || tf_phone.getText().equals("") || tf_email.getText().equals("") || tf_password.getText().equals("") 
    			|| tf_date_created.getText().equals("")  || tf_age.getText().matches(".*[a-zA-Z]+.*")   || Integer.parseInt(tf_age.getText()) < 18 || tf_phone.getText().matches(".*[a-zA-Z]+.*") || tf_phone.getText().length() < 9 || 	!tf_email.getText().contains("mail.com") || !tf_email.getText().contains("@"))  {
    	
    		 Alert al = new Alert(AlertType.ERROR, "Invalid Input or Data Missing!");
    		 al.showAndWait();
    	}
    	else {
    		
    		
    	int id = Integer.parseInt(tf_id.getText().toString());
    	String name = tf_name.getText().toString();
    	String age = tf_age.getText().toString();
    	String addr = tf_addr.getText().toString();
    	String ph = tf_phone.getText().toString();
    	String mail = tf_email.getText().toString();
    	String pw = tf_password.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	String gender = "";
    	if(rdo_male.isSelected()) {
    		gender = "male";
    	}else {
    		gender = "female";
    	}
    	
    	try {
    	String addQuery = "INSERT INTO `cashier` (`id`, `name`, `age`, `gender`, `address`, `phone`, `email`, `password`, `date created`) VALUES ( "+id+", '"+name+"', "+age+", '"+gender+"','"+addr+"','"+ph+"','"+mail+"','"+pw+"','"+datecreated+"')";
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(addQuery);
    	
    	//clear
    	tf_id.clear();
        tf_name.clear();
        tf_age.clear();
        tf_addr.clear();
        tf_phone.clear();
        tf_email.clear();
        tf_password.clear();
        tf_date_created.clear();
    	
    	//get tabe data
    	String getTableDataQuery = "SELECT * FROM `cashier` ORDER BY cashier.id DESC;";
    	cashierData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Cashier ca = new Cashier();
    		ca.setId(getrs.getString(1));
    		ca.setName(getrs.getString(2));
    		ca.setAge(getrs.getString(3));
    		ca.setGender(getrs.getString(4));
    		ca.setAddress(getrs.getString(5));
    		ca.setPhone(getrs.getString(6));
    		ca.setEmail(getrs.getString(7));
    		ca.setPassword(getrs.getString(8));
    		ca.setDateCreated(getrs.getString(9));
    		
    		cashierData.add(ca);
    	}
    	//set to table
    	tb_cashier.refresh();
    	
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
    void onbtUpdateAction(ActionEvent event) throws SQLException {

    	if(tf_name.getText().equals("") || tf_id.getText().equals("") || tf_age.getText().equals("") || tf_addr.getText().equals("") || tf_phone.getText().equals("") || tf_email.getText().equals("") || tf_password.getText().equals("") 
    			|| tf_date_created.getText().equals("")  || tf_age.getText().matches(".*[a-zA-Z]+.*")   || Integer.parseInt(tf_age.getText()) < 18 || tf_phone.getText().matches(".*[a-zA-Z]+.*") || tf_phone.getText().length() < 9 || 	!tf_email.getText().contains("mail.com") || !tf_email.getText().contains("@"))  {
    	
    		 Alert al = new Alert(AlertType.ERROR, "Invalid Input or Data Missing!");
    		 al.showAndWait();
    	}
    	else {
    		
    		
    		
    	int id = Integer.parseInt(tf_id.getText().toString());
    	String name = tf_name.getText().toString();
    	String age = tf_age.getText().toString();
    	String addr = tf_addr.getText().toString();
    	String ph = tf_phone.getText().toString();
    	String mail = tf_email.getText().toString();
    	String pw = tf_password.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	String gender = "";
    	if(rdo_male.isSelected()) {
    		gender = "male";
    	}else {
    		gender = "female";
    	}
    	
    	
    	String updateQuery = "UPDATE `cashier` SET `name`='"+name+"',`age`='"+age+"',`gender`='"+gender+"',"
    			+ "`address`='"+addr+"',`phone`='"+ph+"',`email`='"+mail+"',`password`='"+pw+"',`date created`='"+datecreated+"' WHERE `id`= "+id+";";
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(updateQuery);
    	
    	//clear
    	tf_id.clear();
        tf_name.clear();
        tf_age.clear();
        tf_addr.clear();
        tf_phone.clear();
        tf_email.clear();
        tf_password.clear();
        tf_date_created.clear();
    	
    	//get tabe data
    	String getTableDataQuery = "SELECT * FROM `cashier` ORDER BY cashier.id DESC;";
    	cashierData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Cashier ca = new Cashier();
    		ca.setId(getrs.getString(1));
    		ca.setName(getrs.getString(2));
    		ca.setAge(getrs.getString(3));
    		ca.setGender(getrs.getString(4));
    		ca.setAddress(getrs.getString(5));
    		ca.setPhone(getrs.getString(6));
    		ca.setEmail(getrs.getString(7));
    		ca.setPassword(getrs.getString(8));
    		ca.setDateCreated(getrs.getString(9));
    		
    		cashierData.add(ca);
    	}
    	//set to table
    	tb_cashier.refresh();
    	
    	//show alert
    	Alert al = new Alert(AlertType.INFORMATION, "Item updated!");
		al.showAndWait();
    }//end of if
    	}

    
}
