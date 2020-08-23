package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.ProductItem;

public class AdminProductController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField tf_barcode;

	@FXML
	private JFXTextField tf_name;

	@FXML
	private JFXComboBox<String> cbo_category;

	@FXML
	private JFXComboBox<String> cbo_supplier;

	@FXML
	private JFXTextField tf_price;

	@FXML
	private JFXTextField tf_date_added;

	@FXML
	private JFXTextField tf_stock;

	@FXML
	private JFXTextField tf_expired_date;

	@FXML
	private JFXButton bt_new;

	@FXML
	private JFXTextField tf_name_search;

	@FXML
	private JFXTextField tf_barcode_search;

	@FXML
	private JFXButton bt_add;

	@FXML
	private JFXButton bt_update;

	@FXML
	private TableView<ProductItem> tb_product_item;

	private TableColumn<ProductItem, String> col_item_barcode;

	private TableColumn<ProductItem, String> col_item_name;

	private TableColumn<ProductItem, String> col_item_categroy;

	private TableColumn<ProductItem, String> col_item_price;

	private TableColumn<ProductItem, String> col_item_supplier;

	private TableColumn<ProductItem, String> col_item_dateadded;

	private TableColumn<ProductItem, String> col_item_stock;

	private TableColumn<ProductItem, String> col_item_expire_date;

	private ObservableList<ProductItem> productData = FXCollections.observableArrayList();

	private ObservableList<String> categoryData = FXCollections.observableArrayList();

	private ObservableList<String> supplierData = FXCollections.observableArrayList();

	private int index = 0;
	private int indexsupplier = 0;

	private ResultSet rs;
	private ResultSet rsCategory;
	private ResultSet rsSupplier;

	@FXML
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		assert tb_product_item != null : "fx:id=\"tb_product_item\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_barcode != null : "fx:id=\"tf_barcode\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_price != null : "fx:id=\"tf_price\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_date_added != null : "fx:id=\"tf_date_added\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_stock != null : "fx:id=\"tf_stock\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_expired_date != null : "fx:id=\"tf_expired_date\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert bt_update != null : "fx:id=\"bt_update\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert cbo_category != null : "fx:id=\"cbo_category\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert cbo_supplier != null : "fx:id=\"cbo_supplier\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_name_search != null : "fx:id=\"tf_name_search\" was not injected: check your FXML file 'admin_product.fxml'.";
		assert tf_barcode_search != null : "fx:id=\"tf_barcode_search\" was not injected: check your FXML file 'admin_product.fxml'.";

		col_item_barcode = new TableColumn<ProductItem, String>("Barcode");
		col_item_name = new TableColumn<ProductItem, String>("Name");
		col_item_categroy = new TableColumn<ProductItem, String>("Category");
		col_item_price = new TableColumn<ProductItem, String>("Price");
		col_item_supplier = new TableColumn<ProductItem, String>("Supplier");
		col_item_dateadded = new TableColumn<ProductItem, String>("Date Added");
		col_item_stock = new TableColumn<ProductItem, String>("Stock");
		col_item_expire_date = new TableColumn<ProductItem, String>("Expired Date");

		col_item_barcode.setMinWidth(190.0);
		col_item_name.setMinWidth(200.0);
		col_item_categroy.setMinWidth(160.0);
		col_item_price.setMinWidth(90.0);
		col_item_supplier.setMinWidth(170.0);
		col_item_dateadded.setMinWidth(120.0);
		col_item_stock.setMinWidth(90.0);
		col_item_expire_date.setMinWidth(140.0);

		col_item_barcode.setStyle("-fx-font-size: 18");
		col_item_name.setStyle("-fx-font-size: 18");
		col_item_categroy.setStyle("-fx-font-size: 18");
		col_item_price.setStyle("-fx-font-size: 18");
		col_item_supplier.setStyle("-fx-font-size: 18");
		col_item_dateadded.setStyle("-fx-font-size: 18");
		col_item_stock.setStyle("-fx-font-size: 18");
		col_item_expire_date.setStyle("-fx-font-size: 18");

		col_item_barcode.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("barcode"));
		col_item_name.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("name"));
		col_item_categroy.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("categoryname"));
		col_item_price.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("price"));
		col_item_supplier.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("suppliername"));
		col_item_dateadded.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("dateadded"));
		col_item_stock.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("stockamount"));
		col_item_expire_date.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("expiredate"));

		tb_product_item.getColumns().addAll(col_item_barcode, col_item_name, col_item_categroy, col_item_price,
				col_item_supplier, col_item_dateadded, col_item_stock, col_item_expire_date);

		// get category and set to conmbobox
		String categoryquery = "SELECT * FROM `productcategory`;";
		new DBInitialize();
		rsCategory = DBInitialize.statement.executeQuery(categoryquery);
		while (rsCategory.next()) {

			String categoryname = rsCategory.getString(2);
			categoryData.add(categoryname);
		}

		cbo_category.setItems(categoryData);
		cbo_category.setValue("Snacks");

		// get supplier name and set to combobox
		String supplierquery = "SELECT * FROM `supplier`;";
		new DBInitialize();
		rsSupplier = DBInitialize.statement.executeQuery(supplierquery);
		while (rsSupplier.next()) {

			String suppliername = rsSupplier.getString(2);
			supplierData.add(suppliername);
		}

		cbo_supplier.setItems(supplierData);
		cbo_supplier.setValue(supplierData.get(1));

		new DBInitialize().DBInitialize();
		String query = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";

		new DBInitialize();
		rs = DBInitialize.statement.executeQuery(query);

		while (rs.next()) {
			ProductItem product = new ProductItem();
			product.setBarcode(rs.getString(1));
			product.setName(rs.getString(2));
			product.setCategoryname(rs.getString(3));
			product.setPrice(rs.getString(4));
			product.setSuppliername(rs.getString(5));
			product.setDateadded(rs.getString(6));
			product.setStockamount(rs.getString(7));
			product.setExpiredate(rs.getString(8));

			productData.add(product);

		}
		tb_product_item.setItems(productData);

		tb_product_item.setRowFactory(t -> {
			TableRow<ProductItem> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				// get data from selected row

				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					ProductItem product = tb_product_item.getSelectionModel().getSelectedItem();
					System.out.println("Double click is: " + product.getName());

					// get category name
					String categoryfromproducttable = product.getCategoryname();

					for (int i = 0; i < categoryData.size(); i++) {
						if (categoryfromproducttable.equals(categoryData.get(i))) {
							index = i;
						}
					}

					// get supplier name
					String supplierfromproducttable = product.getSuppliername();

					for (int i = 0; i < supplierData.size(); i++) {
						if (supplierfromproducttable.equals(supplierData.get(i))) {
							indexsupplier = i;
						}
					}

					// add data to text fields
					tf_barcode.setText(product.getBarcode());
					tf_name.setText(product.getName());
					cbo_category.setValue(categoryData.get(index));
					tf_price.setText(product.getPrice());
					cbo_supplier.setValue(supplierData.get(indexsupplier));
					tf_date_added.setText(product.getDateadded());
					tf_stock.setText(product.getStockamount());
					tf_expired_date.setText(product.getExpiredate());

				}

				final ContextMenu rowMenu = new ContextMenu();

				MenuItem removeItem = new MenuItem("Delete");
				removeItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						ProductItem p = tb_product_item.getSelectionModel().getSelectedItem();

						Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + p.getName() + " ?",
								ButtonType.YES, ButtonType.NO);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES) {
							// do stuff
							String removequery = "DELETE FROM `productitems` WHERE productitems.barcode = '"
									+ p.getBarcode() + "';";
							try {
								new DBInitialize().DBInitialize();
								new DBInitialize();
								DBInitialize.statement.executeUpdate(removequery);

								// update table
								// update table data
								new DBInitialize().DBInitialize();
								String queryupdatetable = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";

								new DBInitialize();
								ResultSet rss = DBInitialize.statement.executeQuery(queryupdatetable);
								productData.clear();

								while (rss.next()) {
									ProductItem product = new ProductItem();
									product.setBarcode(rss.getString(1));
									product.setName(rss.getString(2));
									product.setCategoryname(rss.getString(3));
									product.setPrice(rss.getString(4));
									product.setSuppliername(rss.getString(5));
									product.setDateadded(rss.getString(6));
									product.setStockamount(rss.getString(7));
									product.setExpiredate(rss.getString(8));

									productData.add(product);

								}
								// tb_product_item.getItems().clear();
								tb_product_item.refresh();

								/*
								 * //show alert Alert al = new Alert(AlertType.INFORMATION, "Item removed!");
								 * al.showAndWait();
								 */

							} catch (ClassNotFoundException | SQLException | InstantiationException
									| IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}
				});
				rowMenu.getItems().addAll(removeItem);

				// only display context menu for non-null items:
				row.contextMenuProperty().bind(Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu)
						.otherwise((ContextMenu) null));
			});

			return row;

		});
	}

	@FXML
	void onBtUpdateAction(ActionEvent event)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		if (tf_barcode.getText().toString().equals("") || tf_name.getText().toString().equals("")
				|| tf_price.getText().toString().equals("") || tf_stock.getText().toString().equals("")
				|| tf_expired_date.getText().toString().equals("") || tf_price.getText().matches(".*[a-zA-Z]+.*")
				|| Double.parseDouble(tf_price.getText().toString()) <= 0 || tf_stock.getText().matches(".*[a-zA-Z]+.*")
				|| Double.parseDouble(tf_stock.getText().toString()) <= 0 || tf_expired_date.getText().length() < 9) {

			Alert al = new Alert(AlertType.ERROR, "Input Error or Data Missing!");
			al.showAndWait();
		} else {

			String name = tf_name.getText().toString();
			String categoryname = cbo_category.getValue().toString();
			String dateadded = tf_date_added.getText().toString();
			String expireddate = tf_expired_date.getText().toString();
			String price = tf_price.getText().toString();
			String barcode = tf_barcode.getText().toString();
			String suppliername = cbo_supplier.getValue().toString();
			String stockamount = tf_stock.getText().toString();

			// get category id by name
			String categoryQuery = "SELECT `id` FROM `productcategory` WHERE productcategory.name = '" + categoryname
					+ "'";
			int categoryid = 0;
			// new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rs = DBInitialize.statement.executeQuery(categoryQuery);
			if (rs.next()) {
				categoryid = rs.getInt(1);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, "Try Again1");
				alert.showAndWait();
			}

			// get category id by name
			String supplierQuery = "SELECT `id` FROM `supplier` WHERE supplier.companyname = '" + suppliername + "'";
			System.out.println("Supplier name is : " + suppliername);
			int supplierid = 0;
			// new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet r = DBInitialize.statement.executeQuery(supplierQuery);
			if (r.next()) {
				supplierid = r.getInt(1);
				System.out.println("Supplier id is : " + supplierid);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, "Try Again2");
				alert.showAndWait();
			}

			String query = "UPDATE `productitems` SET `name`= '" + name + "',`categoryid`=" + categoryid
					+ ",`dateadded`= '" + dateadded + "' ,`expireddate`= '" + expireddate + "' ,`price`= '" + price
					+ "'  ,`supplierid`=" + supplierid + " ,`stockamount`= '" + stockamount + "'  WHERE `barcode`= '"
					+ barcode + "'";

			// new DBInitialize().DBInitialize();
			new DBInitialize();
			DBInitialize.statement.executeUpdate(query);

			// clear
			tf_name.clear();
			tf_date_added.clear();
			tf_expired_date.clear();
			tf_price.clear();
			tf_barcode.clear();
			tf_stock.clear();

			// update table data
			// new DBInitialize().DBInitialize();
			String queryupdatetable = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";

			new DBInitialize();
			ResultSet rss = DBInitialize.statement.executeQuery(queryupdatetable);
			productData.clear();

			while (rss.next()) {
				ProductItem product = new ProductItem();
				product.setBarcode(rss.getString(1));
				product.setName(rss.getString(2));
				product.setCategoryname(rss.getString(3));
				product.setPrice(rss.getString(4));
				product.setSuppliername(rss.getString(5));
				product.setDateadded(rss.getString(6));
				product.setStockamount(rss.getString(7));
				product.setExpiredate(rss.getString(8));

				productData.add(product);

			}
			// tb_product_item.getItems().clear();
			tb_product_item.refresh();
			// tb_product_item.setItems(productData);

			// show alert
			Alert alert = new Alert(AlertType.INFORMATION, "Success! Item is update to database.");
			alert.showAndWait();
		} // end of else
	}

	// ---------------------------------------------------------------------------------------------------------------------==

	@FXML
	void onbtAddAction(ActionEvent event)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		if (tf_barcode.getText().toString().equals("") || tf_name.getText().toString().equals("")
				|| tf_price.getText().toString().equals("") || tf_stock.getText().toString().equals("")
				|| tf_expired_date.getText().toString().equals("") || tf_price.getText().matches(".*[a-zA-Z]+.*")
				|| Double.parseDouble(tf_price.getText().toString()) <= 0 || tf_stock.getText().matches(".*[a-zA-Z]+.*")
				|| Double.parseDouble(tf_stock.getText().toString()) <= 0 || tf_expired_date.getText().length() < 9) {

			Alert al = new Alert(AlertType.ERROR, "Input Error or Data Missing!");
			al.showAndWait();
		} else {

			String name = tf_name.getText().toString();
			String categoryname = cbo_category.getValue().toString();
			String dateadded = tf_date_added.getText().toString();
			String expireddate = tf_expired_date.getText().toString();
			String price = tf_price.getText().toString();
			String barcode = tf_barcode.getText().toString();
			String suppliername = cbo_supplier.getValue().toString();
			String stockamount = tf_stock.getText().toString();

			try {
			// get category id by name
			String categoryQuery = "SELECT `id` FROM `productcategory` WHERE productcategory.name = '" + categoryname
					+ "'";
			int categoryid = 0;
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rs = DBInitialize.statement.executeQuery(categoryQuery);
			if (rs.next()) {
				categoryid = rs.getInt(1);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, "Try Again1");
				alert.showAndWait();
			}

			// get category id by name
			String supplierQuery = "SELECT `id` FROM `supplier` WHERE supplier.companyname = '" + suppliername + "'";
			System.out.println("Supplier name is : " + suppliername);
			int supplierid = 0;
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet r = DBInitialize.statement.executeQuery(supplierQuery);
			if (r.next()) {
				supplierid = r.getInt(1);
				System.out.println("Supplier id is : " + supplierid);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, "Try Again2");
				alert.showAndWait();
			}

			String query = "INSERT INTO `productitems`(`name`, `categoryid`, `dateadded`, `expireddate`, `price`, `barcode`, `supplierid`, `stockamount`, `count`) VALUES"
					+ " ('" + name + "'," + categoryid + ",'" + dateadded + "','" + expireddate + "','" + price + "','"
					+ barcode + "'," + supplierid + ",'" + stockamount + "', 0);";

			new DBInitialize().DBInitialize();
			new DBInitialize();
			DBInitialize.statement.executeUpdate(query);

			// update supplier table lastsupplied date
			String pat = "dd/MM/yyyy";
			String todaydate = new SimpleDateFormat(pat).format(new Date());
			System.out.println("today is " + todaydate);

			String upquery = "UPDATE `supplier` SET `lastdatesupplied`='" + todaydate + "' WHERE `id`='" + supplierid
					+ "'";

			new DBInitialize().DBInitialize();
			new DBInitialize();
			DBInitialize.statement.executeUpdate(upquery);

			// clear
			tf_name.clear();
			tf_date_added.clear();
			tf_expired_date.clear();
			tf_price.clear();
			tf_barcode.clear();
			tf_stock.clear();

			// update table data
			new DBInitialize().DBInitialize();
			String queryupdatetable = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id  ORDER BY productitems.barcode DESC  ;";

			new DBInitialize();
			ResultSet rss = DBInitialize.statement.executeQuery(queryupdatetable);
			productData.clear();

			while (rss.next()) {
				ProductItem product = new ProductItem();
				product.setBarcode(rss.getString(1));
				product.setName(rss.getString(2));
				product.setCategoryname(rss.getString(3));
				product.setPrice(rss.getString(4));
				product.setSuppliername(rss.getString(5));
				product.setDateadded(rss.getString(6));
				product.setStockamount(rss.getString(7));
				product.setExpiredate(rss.getString(8));

				productData.add(product);

			}
			// tb_product_item.getItems().clear();
			tb_product_item.refresh();
			// tb_product_item.setItems(productData);

			// show alert
			Alert alert = new Alert(AlertType.INFORMATION, "Success! One product items is added to database.");
			alert.showAndWait();
			
			}//end of try
	    	catch(Exception ex) {
	    		Alert al = new Alert(AlertType.ERROR, ""+ex.getMessage());
	    		al.showAndWait();
	    	}
		} // end of else

	}

	@FXML
	void onbtNewAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// clear
		tf_name.clear();
		tf_date_added.clear();
		tf_expired_date.clear();
		tf_price.clear();
		tf_barcode.clear();
		tf_stock.clear();

		String query = "SELECT `barcode` FROM `productitems` ORDER BY productitems.barcode DESC LIMIT 1";

		String oldbarcode = "";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldbarcode = "" + rs.getString(1);
		}

		// count +1 new Id
		String newbarcode = "" + (Long.parseLong(oldbarcode) + 1);
		tf_barcode.setText(newbarcode);

		// create today date
		String pattern = "dd/MM/yyyy";
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("today is " + todaydate);
		tf_date_added.setText(todaydate);
	}

	///////////////////////
	// barcode search

	@FXML
    void onBarcodeSearchActionn(KeyEvent event) {


		String searchKey = tf_barcode_search.getText().toString();
		System.out.println("key entered is : " + searchKey);
		String query = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id AND productitems.barcode LIKE '"
				+ searchKey + "%'";

		// new DBInitialize().DBInitialize();

		System.out.println("working");
		try {
			// ResultSet rs = st.executeQuery("SELECT * FROM USER");
			ResultSet rs = DBInitialize.statement.executeQuery(query);
			ObservableList<ProductItem> row = FXCollections.observableArrayList();
			while (rs.next()) {

				ProductItem p = new ProductItem();
				p.setBarcode(rs.getString(1));
				p.setName(rs.getString(2));
				p.setCategoryname(rs.getString(3));
				p.setPrice(rs.getString(4));
				p.setSuppliername(rs.getString(5));
				p.setDateadded(rs.getString(6));
				p.setStockamount(rs.getString(7));
				p.setExpiredate(rs.getString(8));

				row.add(p);

			}
			tb_product_item.setItems(row);
			// System.out.println("working1"+data);

			// tb_total_item.getItems().clear();
			// tb_total_item.setItems(data);

			System.out.println("working2");
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

	///////////////
	// name search

	@FXML
	void onNameSearchAction(KeyEvent event) {

		String searchKey = tf_name_search.getText().toString();
		System.out.println("key entered is : " + searchKey);
		String query = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id AND productitems.name LIKE '"
				+ searchKey + "%'";

		// new DBInitialize().DBInitialize();

		System.out.println("working");
		try {
			// ResultSet rs = st.executeQuery("SELECT * FROM USER");
			ResultSet rs = DBInitialize.statement.executeQuery(query);
			ObservableList<ProductItem> row = FXCollections.observableArrayList();
			while (rs.next()) {
				ProductItem p = new ProductItem();
				p.setBarcode(rs.getString(1));
				p.setName(rs.getString(2));
				p.setCategoryname(rs.getString(3));
				p.setPrice(rs.getString(4));
				p.setSuppliername(rs.getString(5));
				p.setDateadded(rs.getString(6));
				p.setStockamount(rs.getString(7));
				p.setExpiredate(rs.getString(8));

				row.add(p);
			}
			tb_product_item.setItems(row);

			System.out.println("working2");
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

}
