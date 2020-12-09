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
        assert tb_category != null : Messages.getString("AdminCategoryController.0"); //$NON-NLS-1$
        assert tf_id != null : Messages.getString("AdminCategoryController.1"); //$NON-NLS-1$
        assert tf_name != null : Messages.getString("AdminCategoryController.2"); //$NON-NLS-1$
        assert tf_date_created != null : Messages.getString("AdminCategoryController.3"); //$NON-NLS-1$
        assert bt_add != null : Messages.getString("AdminCategoryController.4"); //$NON-NLS-1$
        assert bt_update != null : Messages.getString("AdminCategoryController.5"); //$NON-NLS-1$
        assert bt_new != null : Messages.getString("AdminCategoryController.6"); //$NON-NLS-1$

        
        tf_date_created.setEditable(true);

        //current date
        String pattern = Messages.getString("AdminCategoryController.7"); //$NON-NLS-1$
		String datecreated =new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminCategoryController.8")+datecreated); //$NON-NLS-1$
		tf_date_created.setText(datecreated);
        
        col_item_id = new TableColumn<Category, String>(Messages.getString("AdminCategoryController.9")); //$NON-NLS-1$
        col_item_name = new TableColumn<Category, String>(Messages.getString("AdminCategoryController.10")); //$NON-NLS-1$
        col_item_date_created = new TableColumn<Category, String>(Messages.getString("AdminCategoryController.11")); //$NON-NLS-1$
        
        col_item_id.setMinWidth(150.0);
        col_item_name.setMinWidth(280.0);
        col_item_date_created.setMinWidth(200.0);
        
        
        col_item_id.setStyle(Messages.getString("AdminCategoryController.12")); //$NON-NLS-1$
        col_item_name.setStyle(Messages.getString("AdminCategoryController.13")); //$NON-NLS-1$
        col_item_date_created.setStyle(Messages.getString("AdminCategoryController.14")); //$NON-NLS-1$


        col_item_id.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>(Messages.getString("AdminCategoryController.15"))); //$NON-NLS-1$
        col_item_name.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>(Messages.getString("AdminCategoryController.16"))); //$NON-NLS-1$
        col_item_date_created.setCellValueFactory(
        	    new PropertyValueFactory<Category, String>(Messages.getString("AdminCategoryController.17"))); //$NON-NLS-1$


        tb_category.getColumns().addAll(col_item_id
        		,col_item_name
        		,col_item_date_created
        		);
        
        
        //setup
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	String querygetId = Messages.getString("AdminCategoryController.18"); //$NON-NLS-1$
    	
    	String oldid = Messages.getString("AdminCategoryController.19"); //$NON-NLS-1$
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rss = DBInitialize.statement.executeQuery(querygetId);
    	while(rss.next()) {
    		oldid = Messages.getString("AdminCategoryController.20")+rss.getInt(1); //$NON-NLS-1$
    	}
    	
    	//count +1 new Id
    	String newId = Messages.getString("AdminCategoryController.21")+(Integer.parseInt(oldid) + 1 ); //$NON-NLS-1$
    	tf_id.setText(newId);
    	
    	//create today date
    	String pattern1 = Messages.getString("AdminCategoryController.22"); //$NON-NLS-1$
		String todaydate =new SimpleDateFormat(pattern1).format(new Date());
		System.out.println(Messages.getString("AdminCategoryController.23")+todaydate); //$NON-NLS-1$
		tf_date_created.setText(todaydate);
        //---------------------------------------------------------------------
        
        //get data from db and add to category model
        String query= Messages.getString("AdminCategoryController.24"); //$NON-NLS-1$
        new DBInitialize().DBInitialize();
        new DBInitialize();
        ResultSet rs = DBInitialize.statement.executeQuery(query);
        
        while (rs.next()) {
        	Category ca = new Category();
        	ca.setId(Messages.getString("AdminCategoryController.25")+rs.getInt(1)); //$NON-NLS-1$
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
    	            System.out.println(Messages.getString("AdminCategoryController.26")+category.getName()); //$NON-NLS-1$
    	    
    	            //set data to tf
    	            tf_id.setText(category.getId());
    	            tf_name.setText(category.getName());
    	            tf_date_created.setText(category.getDateCreated());
    
    	        }
    		});

            final ContextMenu rowMenu = new ContextMenu();
            
            MenuItem removeItem = new MenuItem(Messages.getString("AdminCategoryController.27")); //$NON-NLS-1$
            removeItem.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                	Category ca = tb_category.getSelectionModel().getSelectedItem();

                	Alert alert = new Alert(AlertType.CONFIRMATION, Messages.getString("AdminCategoryController.28") + ca.getName() + Messages.getString("AdminCategoryController.29"), ButtonType.YES, ButtonType.NO); //$NON-NLS-1$ //$NON-NLS-2$
                	alert.showAndWait();

                	
                	if (alert.getResult() == ButtonType.YES) {
                		
                		//check if this current category is used in product items
                    	//get count 
                		int cateCount = 0;
                    	String getCateUsedCount = Messages.getString("AdminCategoryController.30")+ca.getId()+Messages.getString("AdminCategoryController.31"); //$NON-NLS-1$ //$NON-NLS-2$
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
                    		Alert aal = new Alert(AlertType.ERROR, Messages.getString("AdminCategoryController.32")); //$NON-NLS-1$
                    		aal.showAndWait();
                    		
                    	}else {
                	    //do stuff
                     	String removequery = Messages.getString("AdminCategoryController.33")+ca.getId()+Messages.getString("AdminCategoryController.34"); //$NON-NLS-1$ //$NON-NLS-2$
                    	try {
    						new DBInitialize().DBInitialize();
    						new DBInitialize();
    	                	DBInitialize.statement.executeUpdate(removequery);
    	                	
    	                	//update table
    	            		//update table data
    	           		 new DBInitialize().DBInitialize();
    	           	        String queryupdatetable = Messages.getString("AdminCategoryController.35"); //$NON-NLS-1$
    	           	        
    	           	        new DBInitialize();
    	           			ResultSet rsss = DBInitialize.statement.executeQuery(queryupdatetable);
    	           			categoryData.clear();
    	           			
    	           			while(rsss.next()) {
    	           				Category caa = new Category();
    	           				caa.setId(Messages.getString("AdminCategoryController.36")+rsss.getInt(1)); //$NON-NLS-1$
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
    	           	    	Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCategoryController.37")); //$NON-NLS-1$
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
    	
    	String query = Messages.getString("AdminCategoryController.38"); //$NON-NLS-1$
    	
    	String oldid = Messages.getString("AdminCategoryController.39"); //$NON-NLS-1$
    	
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	ResultSet rs = DBInitialize.statement.executeQuery(query);
    	while(rs.next()) {
    		oldid = Messages.getString("AdminCategoryController.40")+rs.getInt(1); //$NON-NLS-1$
    	}
    	
    	//count +1 new Id
    	String newId = Messages.getString("AdminCategoryController.41")+(Integer.parseInt(oldid) + 1 ); //$NON-NLS-1$
    	tf_id.setText(newId);
    	
    	//create today date
    	String pattern = Messages.getString("AdminCategoryController.42"); //$NON-NLS-1$
		String todaydate =new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminCategoryController.43")+todaydate); //$NON-NLS-1$
		tf_date_created.setText(todaydate);
    	
    }
    	
    
    @FXML
    void onAddAction(ActionEvent event) throws SQLException {

    	if(tf_id.getText().equals(Messages.getString("AdminCategoryController.44")) ||  tf_name.getText().equals(Messages.getString("AdminCategoryController.45")) || tf_date_created.getText().equals(Messages.getString("AdminCategoryController.46")) ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCategoryController.47")); //$NON-NLS-1$
    		al.showAndWait();
    	}
    	else {
    	String id = tf_id.getText().toString();
    	String name = tf_name.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	try {
    	String addQuery = Messages.getString("AdminCategoryController.48")+Integer.parseInt(id)+Messages.getString("AdminCategoryController.49")+name+Messages.getString("AdminCategoryController.50")+datecreated+Messages.getString("AdminCategoryController.51"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(addQuery);
    	
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	try {
    	//get tabe data
    	String getTableDataQuery = Messages.getString("AdminCategoryController.52"); //$NON-NLS-1$
    	categoryData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Category ca = new Category();
    		ca.setId(Messages.getString("AdminCategoryController.53")+getrs.getInt(1)); //$NON-NLS-1$
    		ca.setName(getrs.getString(2));
    		ca.setDateCreated(getrs.getString(3));
    		categoryData.add(ca);
    	}
    	//set to table
    	tb_category.refresh();
    	//tb_category.setItems(categoryData);
    	//show alert
    	Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCategoryController.54")); //$NON-NLS-1$
		al.showAndWait();
		
    	}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCategoryController.55")+ex.getMessage()); //$NON-NLS-1$
    		al.showAndWait();
    	}
    	}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCategoryController.56")+ex.getMessage()); //$NON-NLS-1$
    		al.showAndWait();
    	}
    	}//end of else of if
    }

    @FXML
    void onUpdateAction(ActionEvent event) throws SQLException {
    	if(tf_id.getText().equals(Messages.getString("AdminCategoryController.57")) ||  tf_name.getText().equals(Messages.getString("AdminCategoryController.58")) || tf_date_created.getText().equals(Messages.getString("AdminCategoryController.59")) ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminCategoryController.60")); //$NON-NLS-1$
    		al.showAndWait();
    	}
    	else {
    	String id = tf_id.getText().toString();
    	String name = tf_name.getText().toString();
    	String datecreated = tf_date_created.getText().toString();
    	
    	String updateQuery = Messages.getString("AdminCategoryController.61")+name+Messages.getString("AdminCategoryController.62")+datecreated+Messages.getString("AdminCategoryController.63")+id+Messages.getString("AdminCategoryController.64"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    	new DBInitialize();
    	
    	DBInitialize.statement.executeUpdate(updateQuery);
    	
    	tf_id.clear();
    	tf_name.clear();
    	tf_date_created.clear();
    	
    	//get tabe data
    	String getTableDataQuery = Messages.getString("AdminCategoryController.65"); //$NON-NLS-1$
    	categoryData.clear();//clear category data
    	new DBInitialize();
    	ResultSet getrs = DBInitialize.statement.executeQuery(getTableDataQuery);
    	while (getrs.next()) {
    		Category ca = new Category();
    		ca.setId(Messages.getString("AdminCategoryController.66")+getrs.getInt(1)); //$NON-NLS-1$
    		ca.setName(getrs.getString(2));
    		ca.setDateCreated(getrs.getString(3));
    		categoryData.add(ca);
    	}
    	//set to table
    	tb_category.refresh();
    	//show alert
    	Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminCategoryController.67")); //$NON-NLS-1$
		al.showAndWait();
    }
    }//end of first if
}
