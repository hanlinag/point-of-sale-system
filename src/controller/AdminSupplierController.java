package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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
		assert tb_supplier != null : Messages.getString("AdminSupplierController.0"); //$NON-NLS-1$
		assert bt_add != null : Messages.getString("AdminSupplierController.1"); //$NON-NLS-1$
		assert tf_id != null : Messages.getString("AdminSupplierController.2"); //$NON-NLS-1$
		assert tb_supplier != null : Messages.getString("AdminSupplierController.3"); //$NON-NLS-1$
		assert tf_name != null : Messages.getString("AdminSupplierController.4"); //$NON-NLS-1$
		assert tf_supplied_date != null : Messages.getString("AdminSupplierController.5"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("AdminSupplierController.6"); //$NON-NLS-1$


		tf_id.setEditable(false);
		//tf_supplied_date.setEditable(false);
		
		col_item_id = new TableColumn<Supplier, String>(Messages.getString("AdminSupplierController.7")); //$NON-NLS-1$
		col_item_name = new TableColumn<Supplier, String>(Messages.getString("AdminSupplierController.8")); //$NON-NLS-1$
		col_item_supplied_last = new TableColumn<Supplier, String>(Messages.getString("AdminSupplierController.9")); //$NON-NLS-1$

		col_item_id.setMinWidth(110.0);
		col_item_name.setMinWidth(380.0);
		col_item_supplied_last.setMinWidth(180.0);

		col_item_id.setStyle(Messages.getString("AdminSupplierController.10")); //$NON-NLS-1$
		col_item_name.setStyle(Messages.getString("AdminSupplierController.11")); //$NON-NLS-1$
		col_item_supplied_last.setStyle(Messages.getString("AdminSupplierController.12")); //$NON-NLS-1$

		col_item_id.setCellValueFactory(new PropertyValueFactory<Supplier, String>(Messages.getString("AdminSupplierController.13"))); //$NON-NLS-1$
		col_item_name.setCellValueFactory(new PropertyValueFactory<Supplier, String>(Messages.getString("AdminSupplierController.14"))); //$NON-NLS-1$
		col_item_supplied_last.setCellValueFactory(new PropertyValueFactory<Supplier, String>(Messages.getString("AdminSupplierController.15"))); //$NON-NLS-1$

		tb_supplier.getColumns().addAll(col_item_id, col_item_name, col_item_supplied_last);

		// get data from db
		String query = Messages.getString("AdminSupplierController.16"); //$NON-NLS-1$
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
					System.out.println(Messages.getString("AdminSupplierController.17") + su.getName()); //$NON-NLS-1$

					// set to tf
					tf_id.setText(su.getId());
					tf_name.setText(su.getName());
					tf_supplied_date.setText(su.getLastSupplied());

				}
			});
			
			  final ContextMenu rowMenu = new ContextMenu();
	            
	            MenuItem removeItem = new MenuItem(Messages.getString("AdminSupplierController.18")); //$NON-NLS-1$
	            removeItem.setOnAction(new EventHandler<ActionEvent>() {

	                @Override
	                public void handle(ActionEvent event) {
	                	Supplier su = tb_supplier.getSelectionModel().getSelectedItem();

	                	Alert alert = new Alert(AlertType.CONFIRMATION, Messages.getString("AdminSupplierController.19") + su.getName() + Messages.getString("AdminSupplierController.20"), ButtonType.YES, ButtonType.NO); //$NON-NLS-1$ //$NON-NLS-2$
	                	alert.showAndWait();

	                	if (alert.getResult() == ButtonType.YES) {
	                		
	                		//check if this current category is used in product items
	                    	//get count 
	                		int cateCount = 0;
	                    	String getSupplierUsedCount = Messages.getString("AdminSupplierController.21")+su.getId()+Messages.getString("AdminSupplierController.22"); //$NON-NLS-1$ //$NON-NLS-2$
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
	                    		Alert aal = new Alert(AlertType.ERROR, Messages.getString("AdminSupplierController.23")); //$NON-NLS-1$
	                    		aal.showAndWait();
	                    		
	                    	}else {
	                    	
	                	    //do stuff
	                     	String removequery = Messages.getString("AdminSupplierController.24")+su.getId()+Messages.getString("AdminSupplierController.25"); //$NON-NLS-1$ //$NON-NLS-2$
	                    	try {
	    						new DBInitialize().DBInitialize();
	    						new DBInitialize();
	    	                	DBInitialize.statement.executeUpdate(removequery);
	    	                	
	    	                	//update table
	    	            		//update table data
	    	           		 new DBInitialize().DBInitialize();
	    	           	        String queryupdatetable = Messages.getString("AdminSupplierController.26"); //$NON-NLS-1$
	    	           	        
	    	           	    	// get data from db
	    	           	 		String query = Messages.getString("AdminSupplierController.27"); //$NON-NLS-1$
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
	    	           	    	Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminSupplierController.28")); //$NON-NLS-1$
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
	    	
	    	String query = Messages.getString("AdminSupplierController.29"); //$NON-NLS-1$
	    	
	    	String oldid = Messages.getString("AdminSupplierController.30"); //$NON-NLS-1$
	    	
	    	new DBInitialize().DBInitialize();
	    	new DBInitialize();
	    	ResultSet rs = DBInitialize.statement.executeQuery(query);
	    	while(rs.next()) {
	    		oldid = Messages.getString("AdminSupplierController.31")+rs.getInt(1); //$NON-NLS-1$
	    	}
	    	
	    	//count +1 new Id
	    	String newId = Messages.getString("AdminSupplierController.32")+(Integer.parseInt(oldid) + 1 ); //$NON-NLS-1$
	    	tf_id.setText(newId);
	    	
	    	//create today date
	    	String pattern = Messages.getString("AdminSupplierController.33"); //$NON-NLS-1$
			String todaydate =new SimpleDateFormat(pattern).format(new Date());
			System.out.println(Messages.getString("AdminSupplierController.34")+todaydate); //$NON-NLS-1$
			tf_supplied_date.setText(todaydate);
			
	    }
	 
	@FXML
	void onAddAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals(Messages.getString("AdminSupplierController.35")) || tf_name.getText().equals(Messages.getString("AdminSupplierController.36")) || tf_supplied_date.getText().equals(Messages.getString("AdminSupplierController.37")) ||tf_supplied_date.getText().length() < 10 ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminSupplierController.38")); //$NON-NLS-1$
			al.showAndWait();
		}
		else {
			

		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String lastsupplied = tf_supplied_date.getText().toString();
    	
		try {
    	String addquery = Messages.getString("AdminSupplierController.39")+id+Messages.getString("AdminSupplierController.40")+name+Messages.getString("AdminSupplierController.41")+lastsupplied+Messages.getString("AdminSupplierController.42"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	 DBInitialize.statement.executeUpdate(addquery);
    	
    	
    	//update table
    	// get data from db
 		String query = Messages.getString("AdminSupplierController.43"); //$NON-NLS-1$
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
    	 Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminSupplierController.44")); //$NON-NLS-1$
 		al.showAndWait();
		}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminSupplierController.45")+ex.getMessage()); //$NON-NLS-1$
    		al.showAndWait();
    	}
		
		}//end of else
	}

	@FXML
	void onUpdateAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals(Messages.getString("AdminSupplierController.46")) || tf_name.getText().equals(Messages.getString("AdminSupplierController.47")) || tf_supplied_date.getText().equals(Messages.getString("AdminSupplierController.48")) ||tf_supplied_date.getText().length() < 10 ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminSupplierController.49")); //$NON-NLS-1$
			al.showAndWait();
		}
		else {
			
		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String lastsupplied = tf_supplied_date.getText().toString();
    	
    	String upquery = Messages.getString("AdminSupplierController.50")+name+Messages.getString("AdminSupplierController.51")+lastsupplied+Messages.getString("AdminSupplierController.52")+id+Messages.getString("AdminSupplierController.53"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	 DBInitialize.statement.executeUpdate(upquery);
		
    	//update table
    	// get data from db
 		String query = Messages.getString("AdminSupplierController.54"); //$NON-NLS-1$
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
    	 Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminSupplierController.55")); //$NON-NLS-1$
 		al.showAndWait();
	}
	}//end of else
}
