package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
		assert tb_product_item != null : Messages.getString("AdminProductController.0"); //$NON-NLS-1$
		assert tf_barcode != null : Messages.getString("AdminProductController.1"); //$NON-NLS-1$
		assert tf_name != null : Messages.getString("AdminProductController.2"); //$NON-NLS-1$
		assert tf_price != null : Messages.getString("AdminProductController.3"); //$NON-NLS-1$
		assert tf_date_added != null : Messages.getString("AdminProductController.4"); //$NON-NLS-1$
		assert tf_stock != null : Messages.getString("AdminProductController.5"); //$NON-NLS-1$
		assert tf_expired_date != null : Messages.getString("AdminProductController.6"); //$NON-NLS-1$
		assert bt_add != null : Messages.getString("AdminProductController.7"); //$NON-NLS-1$
		assert bt_update != null : Messages.getString("AdminProductController.8"); //$NON-NLS-1$
		assert cbo_category != null : Messages.getString("AdminProductController.9"); //$NON-NLS-1$
		assert cbo_supplier != null : Messages.getString("AdminProductController.10"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("AdminProductController.11"); //$NON-NLS-1$
		assert tf_name_search != null : Messages.getString("AdminProductController.12"); //$NON-NLS-1$
		assert tf_barcode_search != null : Messages.getString("AdminProductController.13"); //$NON-NLS-1$

		col_item_barcode = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.14")); //$NON-NLS-1$
		col_item_name = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.15")); //$NON-NLS-1$
		col_item_categroy = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.16")); //$NON-NLS-1$
		col_item_price = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.17")); //$NON-NLS-1$
		col_item_supplier = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.18")); //$NON-NLS-1$
		col_item_dateadded = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.19")); //$NON-NLS-1$
		col_item_stock = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.20")); //$NON-NLS-1$
		col_item_expire_date = new TableColumn<ProductItem, String>(Messages.getString("AdminProductController.21")); //$NON-NLS-1$

		col_item_barcode.setMinWidth(190.0);
		col_item_name.setMinWidth(200.0);
		col_item_categroy.setMinWidth(160.0);
		col_item_price.setMinWidth(90.0);
		col_item_supplier.setMinWidth(170.0);
		col_item_dateadded.setMinWidth(120.0);
		col_item_stock.setMinWidth(90.0);
		col_item_expire_date.setMinWidth(140.0);

		col_item_barcode.setStyle(Messages.getString("AdminProductController.22")); //$NON-NLS-1$
		col_item_name.setStyle(Messages.getString("AdminProductController.23")); //$NON-NLS-1$
		col_item_categroy.setStyle(Messages.getString("AdminProductController.24")); //$NON-NLS-1$
		col_item_price.setStyle(Messages.getString("AdminProductController.25")); //$NON-NLS-1$
		col_item_supplier.setStyle(Messages.getString("AdminProductController.26")); //$NON-NLS-1$
		col_item_dateadded.setStyle(Messages.getString("AdminProductController.27")); //$NON-NLS-1$
		col_item_stock.setStyle(Messages.getString("AdminProductController.28")); //$NON-NLS-1$
		col_item_expire_date.setStyle(Messages.getString("AdminProductController.29")); //$NON-NLS-1$

		col_item_barcode.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.30"))); //$NON-NLS-1$
		col_item_name.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.31"))); //$NON-NLS-1$
		col_item_categroy.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.32"))); //$NON-NLS-1$
		col_item_price.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.33"))); //$NON-NLS-1$
		col_item_supplier.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.34"))); //$NON-NLS-1$
		col_item_dateadded.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.35"))); //$NON-NLS-1$
		col_item_stock.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.36"))); //$NON-NLS-1$
		col_item_expire_date.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("AdminProductController.37"))); //$NON-NLS-1$

		tb_product_item.getColumns().addAll(col_item_barcode, col_item_name, col_item_categroy, col_item_price,
				col_item_supplier, col_item_dateadded, col_item_stock, col_item_expire_date);

		// get category and set to conmbobox
		String categoryquery = Messages.getString("AdminProductController.38"); //$NON-NLS-1$
		new DBInitialize();
		rsCategory = DBInitialize.statement.executeQuery(categoryquery);
		while (rsCategory.next()) {

			String categoryname = rsCategory.getString(2);
			categoryData.add(categoryname);
		}

		cbo_category.setItems(categoryData);
		cbo_category.setValue(Messages.getString("AdminProductController.39")); //$NON-NLS-1$

		// get supplier name and set to combobox
		String supplierquery = Messages.getString("AdminProductController.40"); //$NON-NLS-1$
		new DBInitialize();
		rsSupplier = DBInitialize.statement.executeQuery(supplierquery);
		while (rsSupplier.next()) {

			String suppliername = rsSupplier.getString(2);
			supplierData.add(suppliername);
		}

		cbo_supplier.setItems(supplierData);
		cbo_supplier.setValue(supplierData.get(1));

		new DBInitialize().DBInitialize();
		String query = Messages.getString("AdminProductController.41"); //$NON-NLS-1$

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
					System.out.println(Messages.getString("AdminProductController.42") + product.getName()); //$NON-NLS-1$

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

				MenuItem removeItem = new MenuItem(Messages.getString("AdminProductController.43")); //$NON-NLS-1$
				removeItem.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						ProductItem p = tb_product_item.getSelectionModel().getSelectedItem();

						Alert alert = new Alert(AlertType.CONFIRMATION, Messages.getString("AdminProductController.44") + p.getName() + Messages.getString("AdminProductController.45"), //$NON-NLS-1$ //$NON-NLS-2$
								ButtonType.YES, ButtonType.NO);
						alert.showAndWait();

						if (alert.getResult() == ButtonType.YES) {
							// do stuff
							String removequery = Messages.getString("AdminProductController.46") //$NON-NLS-1$
									+ p.getBarcode() + Messages.getString("AdminProductController.47"); //$NON-NLS-1$
							try {
								new DBInitialize().DBInitialize();
								new DBInitialize();
								DBInitialize.statement.executeUpdate(removequery);

								// update table
								// update table data
								new DBInitialize().DBInitialize();
								String queryupdatetable = Messages.getString("AdminProductController.48"); //$NON-NLS-1$

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

		if (tf_barcode.getText().toString().equals(Messages.getString("AdminProductController.49")) || tf_name.getText().toString().equals(Messages.getString("AdminProductController.50")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_price.getText().toString().equals(Messages.getString("AdminProductController.51")) || tf_stock.getText().toString().equals(Messages.getString("AdminProductController.52")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_expired_date.getText().toString().equals(Messages.getString("AdminProductController.53")) || tf_price.getText().matches(Messages.getString("AdminProductController.54")) //$NON-NLS-1$ //$NON-NLS-2$
				|| Double.parseDouble(tf_price.getText().toString()) <= 0 || tf_stock.getText().matches(Messages.getString("AdminProductController.55")) //$NON-NLS-1$
				|| Double.parseDouble(tf_stock.getText().toString()) <= 0 || tf_expired_date.getText().length() < 9) {

			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.56")); //$NON-NLS-1$
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
			String categoryQuery = Messages.getString("AdminProductController.57") + categoryname //$NON-NLS-1$
					+ Messages.getString("AdminProductController.58"); //$NON-NLS-1$
			int categoryid = 0;
			// new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rs = DBInitialize.statement.executeQuery(categoryQuery);
			if (rs.next()) {
				categoryid = rs.getInt(1);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.59")); //$NON-NLS-1$
				alert.showAndWait();
			}

			// get category id by name
			String supplierQuery = Messages.getString("AdminProductController.60") + suppliername + Messages.getString("AdminProductController.61"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(Messages.getString("AdminProductController.62") + suppliername); //$NON-NLS-1$
			int supplierid = 0;
			// new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet r = DBInitialize.statement.executeQuery(supplierQuery);
			if (r.next()) {
				supplierid = r.getInt(1);
				System.out.println(Messages.getString("AdminProductController.63") + supplierid); //$NON-NLS-1$
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.64")); //$NON-NLS-1$
				alert.showAndWait();
			}

			String query = Messages.getString("AdminProductController.65") + name + Messages.getString("AdminProductController.66") + categoryid //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("AdminProductController.67") + dateadded + Messages.getString("AdminProductController.68") + expireddate + Messages.getString("AdminProductController.69") + price //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ Messages.getString("AdminProductController.70") + supplierid + Messages.getString("AdminProductController.71") + stockamount + Messages.getString("AdminProductController.72") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ barcode + Messages.getString("AdminProductController.73"); //$NON-NLS-1$

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
			String queryupdatetable = Messages.getString("AdminProductController.74"); //$NON-NLS-1$

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
			Alert alert = new Alert(AlertType.INFORMATION, Messages.getString("AdminProductController.75")); //$NON-NLS-1$
			alert.showAndWait();
		} // end of else
	}

	// ---------------------------------------------------------------------------------------------------------------------==

	@FXML
	void onbtAddAction(ActionEvent event)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

		if (tf_barcode.getText().toString().equals(Messages.getString("AdminProductController.76")) || tf_name.getText().toString().equals(Messages.getString("AdminProductController.77")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_price.getText().toString().equals(Messages.getString("AdminProductController.78")) || tf_stock.getText().toString().equals(Messages.getString("AdminProductController.79")) //$NON-NLS-1$ //$NON-NLS-2$
				|| tf_expired_date.getText().toString().equals(Messages.getString("AdminProductController.80")) || tf_price.getText().matches(Messages.getString("AdminProductController.81")) //$NON-NLS-1$ //$NON-NLS-2$
				|| Double.parseDouble(tf_price.getText().toString()) <= 0 || tf_stock.getText().matches(Messages.getString("AdminProductController.82")) //$NON-NLS-1$
				|| Double.parseDouble(tf_stock.getText().toString()) <= 0 || tf_expired_date.getText().length() < 9) {

			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.83")); //$NON-NLS-1$
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
			String categoryQuery = Messages.getString("AdminProductController.84") + categoryname //$NON-NLS-1$
					+ Messages.getString("AdminProductController.85"); //$NON-NLS-1$
			int categoryid = 0;
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rs = DBInitialize.statement.executeQuery(categoryQuery);
			if (rs.next()) {
				categoryid = rs.getInt(1);
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.86")); //$NON-NLS-1$
				alert.showAndWait();
			}

			// get category id by name
			String supplierQuery = Messages.getString("AdminProductController.87") + suppliername + Messages.getString("AdminProductController.88"); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println(Messages.getString("AdminProductController.89") + suppliername); //$NON-NLS-1$
			int supplierid = 0;
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet r = DBInitialize.statement.executeQuery(supplierQuery);
			if (r.next()) {
				supplierid = r.getInt(1);
				System.out.println(Messages.getString("AdminProductController.90") + supplierid); //$NON-NLS-1$
			} else {
				// show alert
				Alert alert = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.91")); //$NON-NLS-1$
				alert.showAndWait();
			}

			String query = Messages.getString("AdminProductController.92") //$NON-NLS-1$
					+ Messages.getString("AdminProductController.93") + name + Messages.getString("AdminProductController.94") + categoryid + Messages.getString("AdminProductController.95") + dateadded + Messages.getString("AdminProductController.96") + expireddate + Messages.getString("AdminProductController.97") + price + Messages.getString("AdminProductController.98") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
					+ barcode + Messages.getString("AdminProductController.99") + supplierid + Messages.getString("AdminProductController.100") + stockamount + Messages.getString("AdminProductController.101"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			new DBInitialize().DBInitialize();
			new DBInitialize();
			DBInitialize.statement.executeUpdate(query);

			// update supplier table lastsupplied date
			String pat = Messages.getString("AdminProductController.102"); //$NON-NLS-1$
			String todaydate = new SimpleDateFormat(pat).format(new Date());
			System.out.println(Messages.getString("AdminProductController.103") + todaydate); //$NON-NLS-1$

			String upquery = Messages.getString("AdminProductController.104") + todaydate + Messages.getString("AdminProductController.105") + supplierid //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("AdminProductController.106"); //$NON-NLS-1$

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
			String queryupdatetable = Messages.getString("AdminProductController.107"); //$NON-NLS-1$

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
			Alert alert = new Alert(AlertType.INFORMATION, Messages.getString("AdminProductController.108")); //$NON-NLS-1$
			alert.showAndWait();
			
			}//end of try
	    	catch(Exception ex) {
	    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminProductController.109")+ex.getMessage()); //$NON-NLS-1$
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

		String query = Messages.getString("AdminProductController.110"); //$NON-NLS-1$

		String oldbarcode = Messages.getString("AdminProductController.111"); //$NON-NLS-1$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldbarcode = Messages.getString("AdminProductController.112") + rs.getString(1); //$NON-NLS-1$
		}

		// count +1 new Id
		String newbarcode = Messages.getString("AdminProductController.113") + (Long.parseLong(oldbarcode) + 1); //$NON-NLS-1$
		tf_barcode.setText(newbarcode);

		// create today date
		String pattern = Messages.getString("AdminProductController.114"); //$NON-NLS-1$
		String todaydate = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminProductController.115") + todaydate); //$NON-NLS-1$
		tf_date_added.setText(todaydate);
	}

	///////////////////////
	// barcode search

	@FXML
    void onBarcodeSearchActionn(KeyEvent event) {


		String searchKey = tf_barcode_search.getText().toString();
		System.out.println(Messages.getString("AdminProductController.116") + searchKey); //$NON-NLS-1$
		String query = Messages.getString("AdminProductController.117") //$NON-NLS-1$
				+ searchKey + Messages.getString("AdminProductController.118"); //$NON-NLS-1$

		// new DBInitialize().DBInitialize();

		System.out.println(Messages.getString("AdminProductController.119")); //$NON-NLS-1$
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

			System.out.println(Messages.getString("AdminProductController.120")); //$NON-NLS-1$
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

	///////////////
	// name search

	@FXML
	void onNameSearchAction(KeyEvent event) {

		String searchKey = tf_name_search.getText().toString();
		System.out.println(Messages.getString("AdminProductController.121") + searchKey); //$NON-NLS-1$
		String query = Messages.getString("AdminProductController.122") //$NON-NLS-1$
				+ searchKey + Messages.getString("AdminProductController.123"); //$NON-NLS-1$

		// new DBInitialize().DBInitialize();

		System.out.println(Messages.getString("AdminProductController.124")); //$NON-NLS-1$
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

			System.out.println(Messages.getString("AdminProductController.125")); //$NON-NLS-1$
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

}
