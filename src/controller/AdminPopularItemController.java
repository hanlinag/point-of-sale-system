package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.DBInitialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProductItem;

public class AdminPopularItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ProductItem> tb_popular;
    
    private TableColumn<ProductItem, String> col_item_barcode;

	private TableColumn<ProductItem, String> col_item_name;

	private TableColumn<ProductItem, String> col_item_categroy;

	private TableColumn<ProductItem, String> col_item_price;

	private TableColumn<ProductItem, String> col_item_supplier;

	private TableColumn<ProductItem, String> col_item_dateadded;

	private TableColumn<ProductItem, String> col_item_stock;

	private TableColumn<ProductItem, String> col_item_expire_date;
	
	private TableColumn<ProductItem, String> col_item_count;

	private ObservableList<ProductItem> popularData = FXCollections.observableArrayList();
	

    @FXML
    void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        assert tb_popular != null : Messages.getString("AdminPopularItemController.0"); //$NON-NLS-1$
        

        col_item_barcode = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.1")); //$NON-NLS-1$
        col_item_name = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.2")); //$NON-NLS-1$
        col_item_categroy = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.3")); //$NON-NLS-1$
        col_item_price = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.4")); //$NON-NLS-1$
        col_item_supplier = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.5")); //$NON-NLS-1$
        col_item_dateadded = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.6")); //$NON-NLS-1$
        col_item_stock = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.7")); //$NON-NLS-1$
        col_item_expire_date = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.8")); //$NON-NLS-1$
        col_item_count = new TableColumn<ProductItem, String>(Messages.getString("AdminPopularItemController.9")); //$NON-NLS-1$
        
        col_item_barcode.setMinWidth(190.0);
        col_item_name.setMinWidth(200.0);
        col_item_categroy.setMinWidth(160.0);
        col_item_price.setMinWidth(90.0);
        col_item_supplier.setMinWidth(170.0);
        col_item_dateadded.setMinWidth(120.0);
        col_item_stock.setMinWidth(90.0);
        col_item_expire_date.setMinWidth(140.0);
        col_item_count.setMinWidth(60.0);
        
        col_item_barcode.setStyle(Messages.getString("AdminPopularItemController.10")); //$NON-NLS-1$
        col_item_name.setStyle(Messages.getString("AdminPopularItemController.11")); //$NON-NLS-1$
        col_item_categroy.setStyle(Messages.getString("AdminPopularItemController.12")); //$NON-NLS-1$
        col_item_price.setStyle(Messages.getString("AdminPopularItemController.13")); //$NON-NLS-1$
        col_item_supplier.setStyle(Messages.getString("AdminPopularItemController.14")); //$NON-NLS-1$
        col_item_dateadded.setStyle(Messages.getString("AdminPopularItemController.15")); //$NON-NLS-1$
        col_item_stock.setStyle(Messages.getString("AdminPopularItemController.16")); //$NON-NLS-1$
        col_item_expire_date.setStyle(Messages.getString("AdminPopularItemController.17")); //$NON-NLS-1$
        col_item_count.setStyle(Messages.getString("AdminPopularItemController.18")); //$NON-NLS-1$
        
        col_item_barcode.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.19"))); //$NON-NLS-1$
        col_item_name.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.20"))); //$NON-NLS-1$
        col_item_categroy.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.21"))); //$NON-NLS-1$
        col_item_price.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.22"))); //$NON-NLS-1$
        col_item_supplier.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.23"))); //$NON-NLS-1$
        col_item_dateadded.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.24"))); //$NON-NLS-1$
        col_item_stock.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.25"))); //$NON-NLS-1$
        col_item_expire_date.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.26"))); //$NON-NLS-1$
        col_item_count.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminPopularItemController.27"))); //$NON-NLS-1$
    
        tb_popular.getColumns().addAll(col_item_barcode, col_item_name, col_item_categroy, col_item_price, col_item_supplier, col_item_dateadded, col_item_stock, col_item_expire_date, col_item_count);
     

        //get table data
        new DBInitialize().DBInitialize();
        String query = Messages.getString("AdminPopularItemController.28"); //$NON-NLS-1$
        
        new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		
		while(rs.next()) {
			ProductItem product = new ProductItem();
			product.setBarcode(rs.getString(1));
			product.setName(rs.getString(2));
			product.setCategoryname(rs.getString(3));
			product.setPrice(rs.getString(4));
			product.setSuppliername(rs.getString(5));
			product.setDateadded(rs.getString(6));
			product.setStockamount(rs.getString(7));
			product.setExpiredate(rs.getString(8));
			product.setCount(rs.getInt(9));
			
			popularData.add(product);
			
		}
		tb_popular.setItems(popularData);
		
        
    }
}

