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
import model.Supplier;

public class AdminSupplierController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField tf_id;

	@FXML
	private JFXButton bt_add;

	@FXML
	private JFXButton bt_update;

    @FXML
    private JFXButton bt_new;

	@FXML
	private TableView<Supplier> tb_supplier;

	private TableColumn<Supplier, String> col_item_id;

	private TableColumn<Supplier, String> col_item_name;

	private TableColumn<Supplier, String> col_item_supplied_last;

	private ObservableList<Supplier> supplierData = FXCollections.observableArrayList();

	@FXML
	private JFXTextField tf_name;

	@FXML
	private JFXTextField tf_supplied_date;


	@FXML
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		assert tb_supplier != null : "fx:id=\"tb_supplier\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert tb_supplier != null : "fx:id=\"tb_supplier\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert tf_supplied_date != null : "fx:id=\"tf_supplied_date\" was not injected: check your FXML file 'Admin_supplier.fxml'.";
		assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_supplier.fxml'.";


		tf_id.setEditable(false);
		//tf_supplied_date.setEditable(false);
		
		col_item_id = new TableColumn<Supplier, String>("ID");
		col_item_name = new TableColumn<Supplier, String>("Compant Name");
		col_item_supplied_last = new TableColumn<Supplier, String>("Last Supplied Date");

		col_item_id.setMinWidth(110.0);
		col_item_name.setMinWidth(380.0);
		col_item_supplied_last.setMinWidth(180.0);

		col_item_id.setStyle("-fx-font-size: 18");
		col_item_name.setStyle("-fx-font-size: 18");
		col_item_supplied_last.setStyle("-fx-font-size: 18");

		col_item_id.setCellValueFactory(new PropertyValueFactory<Supplier, String>("id"));
		col_item_name.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
		col_item_supplied_last.setCellValueFactory(new PropertyValueFactory<Supplier, String>("lastSupplied"));

		tb_supplier.getColumns().addAll(col_item_id, col_item_name, col_item_supplied_last);

		// get data from db
		String query = "SELECT * FROM `supplier`;";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			Supplier su = new Supplier();
			su.setId(rs.getString(1));
			su.setName(rs.getString(2));
			su.setLastSupplied(rs.getString(3));

			supplierData.add(su);
		}

		// set data to table
		tb_supplier.setItems(supplierData);

		// row action
		tb_supplier.setRowFactory(t -> {
			TableRow<Supplier> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				// get data from selected row

				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					Supplier su = tb_supplier.getSelectionModel().getSelectedItem();
					System.out.println("Double click is: " + su.getName());

					// set to tf
					tf_id.setText(su.getId());
					tf_name.setText(su.getName());
					tf_supplied_date.setText(su.getLastSupplied());

				}
			});
			
			  final ContextMenu rowMenu = new ContextMenu();
	            
	            MenuItem removeItem = new MenuItem("Delete");
	            removeItem.setOnAction(new EventHandler<ActionEvent>() {

	                @Override
	                public void handle(ActionEvent event) {
	                	Supplier su = tb_supplier.getSelectionModel().getSelectedItem();

	                	Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + su.getName() + " ?", ButtonType.YES, ButtonType.NO);
	                	alert.showAndWait();

	                	if (alert.getResult() == ButtonType.YES) {
	                		
	                		//check if this current category is used in product items
	                    	//get count 
	                		int cateCount = 0;
	                    	String getSupplierUsedCount = "SELECT COUNT(*) FROM productitems, supplier WHERE supplier.id = productitems.supplierid AND supplier.id = '"+su.getId()+"'";
	                    	try {
								new DBInitialize().DBInitialize();
								new DBInitialize();
		                    	ResultSet rsSupCount = DBInitialize.statement.executeQuery(getSupplierUsedCount);
		                    	
		                    	while(rsSupCount.next()) {
		                    		cateCount = rsSupCount.getInt(1);
		                    	}
		                    	
		                    	
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                    	
	                    	if(cateCount > 0 ) {
	                    		//show can't delete Alert 
	                    		Alert aal = new Alert(AlertType.ERROR, "Cannot Delete! This Supplier has been used in product items. You can delete the product items that link with this category and try again. Thanks!");
	                    		aal.showAndWait();
	                    		
	                    	}else {
	                    	
	                	    //do stuff
	                     	String removequery = "DELETE FROM `supplier` WHERE supplier.id = '"+su.getId()+"';";
	                    	try {
	    						new DBInitialize().DBInitialize();
	    						new DBInitialize();
	    	                	DBInitialize.statement.executeUpdate(removequery);
	    	                	
	    	                	//update table
	    	            		//update table data
	    	           		 new DBInitialize().DBInitialize();
	    	           	        String queryupdatetable = "SELECT * FROM `supplier`;";
	    	           	        
	    	           	    	// get data from db
	    	           	 		String query = "SELECT * FROM `supplier`;";
	    	           	 		new DBInitialize().DBInitialize();
	    	           	 		new DBInitialize();
	    	           	 		supplierData.clear();//clear previous data
	    	           	 		ResultSet rs = DBInitialize.statement.executeQuery(queryupdatetable);
	    	           	 		while (rs.next()) {
	    	           	 			Supplier s = new Supplier();
	    	           	 			s.setId(rs.getString(1));
	    	           	 			s.setName(rs.getString(2));
	    	           	 			s.setLastSupplied(rs.getString(3));

	    	           	 			supplierData.add(s);
	    	           	 		}

	    	           	 		// set data to table
	    	           	 		tb_supplier.refresh();
	    	           			
	    	           			
	    	                	
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
	    void onNewAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		 	tf_id.clear();
	    	tf_name.clear();
	    	tf_supplied_date.clear();
	    	
	    	String query = "SELECT supplier.id FROM `supplier` ORDER BY supplier.id DESC LIMIT 1";
	    	
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
			tf_supplied_date.setText(todaydate);
			
	    }
	 
	@FXML
	void onAddAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals("") || tf_name.getText().equals("") || tf_supplied_date.getText().equals("") ||tf_supplied_date.getText().length() < 10 ) {
			Alert al = new Alert(AlertType.ERROR, "Invalid input or data missing!");
			al.showAndWait();
		}
		else {
			

		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String lastsupplied = tf_supplied_date.getText().toString();
    	
		try {
    	String addquery = "INSERT INTO `supplier`(`id`, `companyname`, `lastdatesupplied`) VALUES ("+id+",'"+name+"','"+lastsupplied+"');";

    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	 DBInitialize.statement.executeUpdate(addquery);
    	
    	
    	//update table
    	// get data from db
 		String query = "SELECT * FROM `supplier`;";
 		new DBInitialize().DBInitialize();
 		new DBInitialize();
 		supplierData.clear();
 		ResultSet rs = DBInitialize.statement.executeQuery(query);
 		while (rs.next()) {
 			Supplier su = new Supplier();
 			su.setId(rs.getString(1));
 			su.setName(rs.getString(2));
 			su.setLastSupplied(rs.getString(3));

 			supplierData.add(su);
 		}

 		// set data to table
 		tb_supplier.refresh();
    	
 		tf_id.clear();
 		tf_name.clear();
 		tf_supplied_date.clear();
 		
    	//Alert
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
	void onUpdateAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals("") || tf_name.getText().equals("") || tf_supplied_date.getText().equals("") ||tf_supplied_date.getText().length() < 10 ) {
			Alert al = new Alert(AlertType.ERROR, "Invalid input or data missing!");
			al.showAndWait();
		}
		else {
			
		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String lastsupplied = tf_supplied_date.getText().toString();
    	
    	String upquery = "UPDATE `supplier` SET `companyname`='"+name+"',`lastdatesupplied`='"+lastsupplied+"' WHERE `id`='"+id+"'";

    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	 DBInitialize.statement.executeUpdate(upquery);
		
    	//update table
    	// get data from db
 		String query = "SELECT * FROM `supplier`;";
 		new DBInitialize().DBInitialize();
 		new DBInitialize();
 		supplierData.clear();
 		ResultSet rs = DBInitialize.statement.executeQuery(query);
 		while (rs.next()) {
 			Supplier su = new Supplier();
 			su.setId(rs.getString(1));
 			su.setName(rs.getString(2));
 			su.setLastSupplied(rs.getString(3));

 			supplierData.add(su);
 		}

 		// set data to table
 		tb_supplier.refresh();
    	
 		tf_id.clear();
 		tf_name.clear();
 		tf_supplied_date.clear();
 		
    	//Alert
    	 Alert al = new Alert(AlertType.INFORMATION, "Item updated!");
 		al.showAndWait();
	}
	}//end of else
}
