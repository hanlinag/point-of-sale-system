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
        assert tb_popular != null : "fx:id=\"tb_popular\" was not injected: check your FXML file 'Admin_popular_item.fxml'.";
        

        col_item_barcode = new TableColumn<ProductItem, String>("Barcode");
        col_item_name = new TableColumn<ProductItem, String>("Name");
        col_item_categroy = new TableColumn<ProductItem, String>("Category");
        col_item_price = new TableColumn<ProductItem, String>("Price");
        col_item_supplier = new TableColumn<ProductItem, String>("Supplier");
        col_item_dateadded = new TableColumn<ProductItem, String>("Date Added");
        col_item_stock = new TableColumn<ProductItem, String>("Stock");
        col_item_expire_date = new TableColumn<ProductItem, String>("Expired Date");
        col_item_count = new TableColumn<ProductItem, String>("Count");
        
        col_item_barcode.setMinWidth(190.0);
        col_item_name.setMinWidth(200.0);
        col_item_categroy.setMinWidth(160.0);
        col_item_price.setMinWidth(90.0);
        col_item_supplier.setMinWidth(170.0);
        col_item_dateadded.setMinWidth(120.0);
        col_item_stock.setMinWidth(90.0);
        col_item_expire_date.setMinWidth(140.0);
        col_item_count.setMinWidth(60.0);
        
        col_item_barcode.setStyle("-fx-font-size: 18");
        col_item_name.setStyle("-fx-font-size: 18");
        col_item_categroy.setStyle("-fx-font-size: 18");
        col_item_price.setStyle("-fx-font-size: 18");
        col_item_supplier.setStyle("-fx-font-size: 18");
        col_item_dateadded.setStyle("-fx-font-size: 18");
        col_item_stock.setStyle("-fx-font-size: 18");
        col_item_expire_date.setStyle("-fx-font-size: 18");
        col_item_count.setStyle("-fx-font-size: 18");
        
        col_item_barcode.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("barcode"));
        col_item_name.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("name"));
        col_item_categroy.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("categoryname"));
        col_item_price.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("price"));
        col_item_supplier.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("suppliername"));
        col_item_dateadded.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("dateadded"));
        col_item_stock.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("stockamount"));
        col_item_expire_date.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("expiredate"));
        col_item_count.setCellValueFactory(
        	    new PropertyValueFactory<ProductItem, String>("count"));
    
        tb_popular.getColumns().addAll(col_item_barcode, col_item_name, col_item_categroy, col_item_price, col_item_supplier, col_item_dateadded, col_item_stock, col_item_expire_date, col_item_count);
     

        //get table data
        new DBInitialize().DBInitialize();
        String query = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate, productitems.count FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.count DESC LIMIT 25";
        
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

