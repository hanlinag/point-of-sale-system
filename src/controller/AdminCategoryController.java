package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Category;

public class AdminCategoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Category> tb_category;
    
    private TableColumn<Category, String> col_item_id;

	private TableColumn<Category, String> col_item_name;
	
	private TableColumn<Category, String> col_item_date_created;

	private ObservableList<Category> categoryData = FXCollections.observableArrayList();
	

    @FXML
    private JFXTextField tf_id;

    @FXML
    private JFXTextField tf_name;

    @FXML
    private JFXTextField tf_date_created;

    @FXML
    private JFXButton bt_new;


    @FXML
    private JFXButton bt_add;

    @FXML
    private JFXButton bt_update;

    

    @FXML
    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        assert tb_category != null : "fx:id=\"tb_category\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert tf_date_created != null : "fx:id=\"tf_date_created\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert bt_update != null : "fx:id=\"bt_update\" was not injected: check your FXML file 'Admin_category.fxml'.";
        assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_category.fxml'.";

        
        tf_date_created.setEditable(true);

        //current date
        String pattern = "dd/MM/yyyy";
		String datecreated =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("last date use is "+datecreated);
		tf_date_created.setText(datecreated);
        
        col_item_id = new TableColumn<Category, String>("ID");
        col_item_name = new TableColumn<Category, String>("Name");
        col_item_date_created = new TableColumn<Category, String>("Date Created");
        
        col_item_id.setMinWidth(150.0);
        col_item_name.setMinWidth(280.0);
        col_item_date_created.setMinWidth(200.0);
        
        
        col_item_id.setStyle("-fx-font-size: 18");
        col_item_name.setStyle("-fx-font-size: 18");
        col_item_date_created.setStyle("-fx-font-size: 18");


        col_item_id.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>("id"));
        col_item_name.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>("name"));
        col_item_date_created.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>("dateCreated"));


        tb_category.getColumns().addAll(col_item_id
        		,col_item_name
        		,col_item_date_created
        		);
        
        
        //setup
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	String querygetId = "SELECT `id` FROM `productcategory` ORDER BY productcategory.id DESC LIMIT 1";
    	
    	String oldid = "";
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rss = DBInitialize.statement.executeQuery(querygetId);
    	while(rss.next()) {
    		oldid = ""+rss.getInt(1);
    	}
    	
    	//count +1 new Id
    	String newId = ""+(Integer.parseInt(oldid) + 1 );
    	tf_id.setText(newId);
    	
    	//create today date
    	String pattern1 = "dd/MM/yyyy";
		String todaydate =new SimpleDateFormat(pattern1).format(new Date());
		System.out.println("today is "+todaydate);
		tf_date_created.setText(todaydate);
        //---------------------------------------------------------------------
        
        //get data from db and add to category model
        String query= "SELECT * FROM `productcategory`";
        new DBInitialize().DBInitialize();
        new DBInitialize();
        ResultSet rs = DBInitialize.statement.executeQuery(query);
        
        while (rs.next()) {
        	Category ca = new Category();
        	ca.setId(""+rs.getInt(1));
        	ca.setName(rs.getString(2));
        	ca.setDateCreated(rs.getString(3));
        	
        	categoryData.add(ca);
        }
        
        //add data to table
        tb_category.setItems(categoryData);
        
        //row double click action
        tb_category.setRowFactory(t -> {
    		TableRow<Category> row = new TableRow<>();
    		row.setOnMouseClicked(e -> {
    			//get data from selected row
    	
    	        if (e.getClickCount() == 2 && (! row.isEmpty()) ) {
    	        	Category category = tb_category.getSelectionModel().getSelectedItem();
    	            System.out.println("Double click is: "+category.getName());
    	    
    	            //set data to tf
    	            tf_id.setText(category.getId());
    	            tf_name.setText(category.getName());
    	            tf_date_created.setText(category.getDateCreated());
    
    	        }
    		});

            final ContextMenu rowMenu = new ContextMenu();
            
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                	Category ca = tb_category.getSelectionModel().getSelectedItem();

                	Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + ca.getName() + " ?", ButtonType.YES, ButtonType.NO);
                	alert.showAndWait();

                	
                	if (alert.getResult() == ButtonType.YES) {
                		
                		//check if this current category is used in product items
                    	//get count 
                		int cateCount = 0;
                    	String getCateUsedCount = "SELECT COUNT(*) FROM productitems, productcategory WHERE productcategory.id = productitems.categoryid AND productcategory.id = '"+ca.getId()+"'";
                    	try {
							new DBInitialize().DBInitialize();
							new DBInitialize();
	                    	ResultSet rsCateCount = DBInitialize.statement.executeQuery(getCateUsedCount);
	                    	
	                    	while(rsCateCount.next()) {
	                    		cateCount = rsCateCount.getInt(1);
	                    	}
	                    	
	                    	
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	
                    	
                    	
                    	if(cateCount > 0 ) {
                    		//show can't delete Alert 
                    		Alert aal = new Alert(AlertType.ERROR, "Cannot Delete! This Category has been used in product items. You can delete the product items that link with this category and try again. Thanks!");
                    		aal.showAndWait();
                    		
                    	}else {
                	    //do stuff
                     	String removequery = "DELETE FROM `productcategory` WHERE  productcategory.id = '"+ca.getId()+"';";
                    	try {
    						new DBInitialize().DBInitialize();
    						new DBInitialize();
    	                	DBInitialize.statement.executeUpdate(removequery);
    	                	
    	                	//update table
    	            		//update table data
    	           		 new DBInitialize().DBInitialize();
    	           	        String queryupdatetable = "SELECT * FROM `productcategory`;";
    	           	        
    	           	        new DBInitialize();
    	           			ResultSet rsss = DBInitialize.statement.executeQuery(queryupdatetable);
    	           			categoryData.clear();
    	           			
    	           			while(rsss.next()) {
    	           				Category caa = new Category();
    	           				caa.setId(""+rsss.getInt(1));
    	           				caa.setName(rsss.getString(2));
    	           				caa.setDateCreated(rsss.getString(3));
    	           				
    	           				
    	           				categoryData.add(caa);
    	           				
    	           			}
    	           			//tb_product_item.getItems().clear();
    	           			tb_category.refresh();
    	           			
    	           			
    	                	
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
    void onbtnewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
	
    	
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	String query = "SELECT `id` FROM `productcategory` ORDER BY productcategory.id DESC LIMIT 1";
    	
    	String oldid = "";
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rs = DBInitialize.statement.executeQuery(query);
    	while(rs.next()) {
    		oldid = ""+rs.getInt(1);
    	}
    	
    	//count +1 new Id
    	String newId = ""+(Integer.parseInt(oldid) + 1 );
    	tf_id.setText(newId);
    	
    	//create today date
    	String pattern = "dd/MM/yyyy";
		String todaydate =new SimpleDateFormat(pattern).format(new Date());
		System.out.println("today is "+todaydate);
		tf_date_created.setText(todaydate);
    	
    }
    	
    
    @FXML
    void onAddAction(ActionEvent event) throws SQLException {

    	if(tf_id.getText().equals("") ||  tf_name.getText().equals("") || tf_date_created.getText().equals("") ) {
    		Alert al = new Alert(AlertType.ERROR, "Invaild Input or Data Missing!");
    		al.showAndWait();
    	}
    	else {
    	String id = tf_id.getText().toString();
    	String name = tf_name.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	try {
    	String addQuery = "INSERT INTO `productcategory`(`id`, `name`, `datecreated`) VALUES ("+Integer.parseInt(id)+", '"+name+"', '"+datecreated+"')";
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(addQuery);
    	
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	try {
    	//get tabe data
    	String getTableDataQuery = "SELECT * FROM `productcategory`;";
    	categoryData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Category ca = new Category();
    		ca.setId(""+getrs.getInt(1));
    		ca.setName(getrs.getString(2));
    		ca.setDateCreated(getrs.getString(3));
    		categoryData.add(ca);
    	}
    	//set to table
    	tb_category.refresh();
    	//tb_category.setItems(categoryData);
    	//show alert
    	Alert al = new Alert(AlertType.INFORMATION, "Item added!");
		al.showAndWait();
		
    	}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
    		al.showAndWait();
    	}
    	}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
    		al.showAndWait();
    	}
    	}//end of else of if
    }

    @FXML
    void onUpdateAction(ActionEvent event) throws SQLException {
    	if(tf_id.getText().equals("") ||  tf_name.getText().equals("") || tf_date_created.getText().equals("") ) {
    		Alert al = new Alert(AlertType.ERROR, "Invaild Input or Data Missing!");
    		al.showAndWait();
    	}
    	else {
    	String id = tf_id.getText().toString();
    	String name = tf_name.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	String updateQuery = "UPDATE `productcategory` SET `name`='"+name+"',`datecreated`='"+datecreated+"' WHERE `id`="+id+";";
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(updateQuery);
    	
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	//get tabe data
    	String getTableDataQuery = "SELECT * FROM `productcategory`;";
    	categoryData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Category ca = new Category();
    		ca.setId(""+getrs.getInt(1));
    		ca.setName(getrs.getString(2));
    		ca.setDateCreated(getrs.getString(3));
    		categoryData.add(ca);
    	}
    	//set to table
    	tb_category.refresh();
    	//show alert
    	Alert al = new Alert(AlertType.INFORMATION, "Item updated!");
		al.showAndWait();
    }
    }//end of first if
}
