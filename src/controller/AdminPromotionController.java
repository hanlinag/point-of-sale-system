package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.DBInitialize;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import model.Promotion;

public class AdminPromotionController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<Promotion> tb_promo;

	private TableColumn<Promotion, String> col_item_id;

	private TableColumn<Promotion, String> col_item_name;

	private TableColumn<Promotion, String> col_item_product_id;

	private TableColumn<Promotion, String> col_item_percentage;

	private TableColumn<Promotion, String> col_item_more;

	private TableColumn<Promotion, String> col_item_product_name;

	private ObservableList<Promotion> promoData = FXCollections.observableArrayList();

	@FXML
	private JFXTextField tf_id;

	@FXML
	private JFXTextField tf_name;

	@FXML
	private JFXTextField tf_product_id;

	@FXML
	private JFXTextField tf_percentage;

	@FXML
	private JFXTextField tf_buy;

	@FXML
	private JFXTextField tf_get;

	@FXML
	private JFXButton bt_add;

	@FXML
	private JFXTextField tf_product_name;

	@FXML
	private JFXButton bt_new;

	@FXML
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		assert tb_promo != null : "fx:id=\"tb_promo\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_id != null : "fx:id=\"tf_id\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_name != null : "fx:id=\"tf_name\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_product_id != null : "fx:id=\"tf_product_id\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_percentage != null : "fx:id=\"tf_percentage\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_buy != null : "fx:id=\"tf_buy\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_get != null : "fx:id=\"tf_get\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert bt_add != null : "fx:id=\"bt_add\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert tf_product_name != null : "fx:id=\"tf_product_name\" was not injected: check your FXML file 'Admin_promotion.fxml'.";
		assert bt_new != null : "fx:id=\"bt_new\" was not injected: check your FXML file 'Admin_promotion.fxml'.";

		col_item_id = new TableColumn<Promotion, String>("ID");
		col_item_name = new TableColumn<Promotion, String>("Name");
		col_item_product_id = new TableColumn<Promotion, String>("Product ID");
		col_item_product_name = new TableColumn<Promotion, String>("Product Name");
		col_item_percentage = new TableColumn<Promotion, String>("Percentage");
		col_item_more = new TableColumn<Promotion, String>("More");

		col_item_id.setMinWidth(100.0);
		col_item_name.setMinWidth(200.0);
		col_item_product_id.setMinWidth(200.0);
		col_item_product_name.setMinWidth(200.0);
		col_item_percentage.setMinWidth(120.0);
		col_item_more.setMinWidth(150.0);

		col_item_id.setStyle("-fx-font-size: 18");
		col_item_name.setStyle("-fx-font-size: 18");
		col_item_product_id.setStyle("-fx-font-size: 18");
		col_item_product_name.setStyle("-fx-font-size: 18");
		col_item_percentage.setStyle("-fx-font-size: 18");
		col_item_more.setStyle("-fx-font-size: 18");

		col_item_id.setCellValueFactory(new PropertyValueFactory<Promotion, String>("id"));
		col_item_name.setCellValueFactory(new PropertyValueFactory<Promotion, String>("name"));
		col_item_product_id.setCellValueFactory(new PropertyValueFactory<Promotion, String>("productId"));
		col_item_product_name.setCellValueFactory(new PropertyValueFactory<Promotion, String>("productName"));
		col_item_percentage.setCellValueFactory(new PropertyValueFactory<Promotion, String>("percentage"));
		col_item_more.setCellValueFactory(new PropertyValueFactory<Promotion, String>("more"));

		tb_promo.getColumns().addAll(col_item_id, col_item_name, col_item_product_id, col_item_product_name,
				col_item_percentage, col_item_more);

		// get data from db
		String query = "SELECT promotion.id, promotion.name, promotion.productid, productitems.name,promotion.percentage, promotion.description FROM promotion, productitems WHERE promotion.productid = productitems.barcode";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			Promotion pro = new Promotion();
			pro.setId(rs.getString(1));
			pro.setName(rs.getString(2));
			pro.setProductId(rs.getString(3));
			pro.setProductName(rs.getString(4));
			pro.setPercentage(rs.getString(5));
			pro.setMore(rs.getString(6));

			promoData.add(pro);
		}

		// set data to table
		tb_promo.setItems(promoData);

		// row action
		tb_promo.setRowFactory(t -> {
			TableRow<Promotion> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				// get data from selected row

				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					Promotion pro = tb_promo.getSelectionModel().getSelectedItem();
					System.out.println("Double click is: " + pro.getName());

					// string processing / cut buy and get
					String more = pro.getMore();
					String[] buy = more.split(" ");
					// String get = more;

					// set data to tf
					tf_id.setText(pro.getId());
					tf_name.setText(pro.getName());
					tf_product_id.setText(pro.getProductId());
					tf_product_name.setText(pro.getProductName());
					;
					tf_percentage.setText(pro.getPercentage());
					tf_buy.setText(buy[1]);
					tf_get.setText(buy[3]);

				}
			});

			final ContextMenu rowMenu = new ContextMenu();

			MenuItem removeItem = new MenuItem("Delete");
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Promotion pro = tb_promo.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + pro.getName() + " ?",
							ButtonType.YES, ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff
						String removequery = "DELETE FROM `promotion` WHERE promotion.id = '" + pro.getId() + "';";
						try {
							new DBInitialize().DBInitialize();
							new DBInitialize();
							DBInitialize.statement.executeUpdate(removequery);

							// update table
							// update table data
							new DBInitialize().DBInitialize();
							String queryupdatetable = "SELECT promotion.id, promotion.name, promotion.productid, productitems.name,promotion.percentage, promotion.description FROM promotion, productitems WHERE promotion.productid = productitems.barcode";

							new DBInitialize();
							ResultSet rsu = DBInitialize.statement.executeQuery(queryupdatetable);
							
							promoData.clear();

							while (rsu.next()) {
								Promotion p = new Promotion();
								p.setId(rsu.getString(1));
								p.setName(rsu.getString(2));
								p.setProductId(rsu.getString(3));
								p.setProductName(rsu.getString(4));
								p.setPercentage(rsu.getString(5));
								p.setMore(rsu.getString(6));

								promoData.add(p);
							}
							// tb_product_item.getItems().clear();
							tb_promo.refresh();

							/*
							 * //show alert Alert al = new Alert(AlertType.INFORMATION, "Item removed!");
							 * al.showAndWait();
							 */

							// show alert
							Alert al = new Alert(AlertType.INFORMATION, "Item deleted!");
							al.showAndWait();

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
			row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));

			return row;

		});

	}

	@FXML
	void onProductIDSearch(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_product_id.getText().equals("")) {
			Alert al = new Alert(AlertType.ERROR, "Please enter product barcode!");
			al.showAndWait();
		}else {
			
		
		String productbarcode = tf_product_id.getText().toString();
		String searchPQuery = "SELECT productitems.name FROM `productitems` WHERE productitems.barcode = '"
				+ productbarcode + "'";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsp = DBInitialize.statement.executeQuery(searchPQuery);
		String productname = "";
		if (rsp.next()) {
			productname = rsp.getString(1);
		}
		else {
			Alert al = new Alert(AlertType.ERROR, "Invaild Product Barcode!");
			al.showAndWait();
		}

		tf_product_name.setText(productname);
		}//end of else
	}

	@FXML
	void onNewAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		tf_id.clear();
		tf_name.clear();
		tf_product_id.clear();
		tf_product_name.clear();
		tf_percentage.clear();
		tf_buy.clear();
		tf_get.clear();

		String query = "SELECT promotion.id FROM promotion ORDER BY promotion.id DESC LIMIT 1";

		String oldid = "";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldid = "" + rs.getInt(1);
		}

		// count +1 new Id
		String newId = "" + (Integer.parseInt(oldid) + 1);
		tf_id.setText(newId);

	}

	@FXML
	void onAddAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		if(tf_id.getText().equals("") ||tf_name.getText().equals("") || tf_product_id.getText().equals("") ||
				tf_percentage.getText().matches(".*[a-zA-Z]+.*")   
				) {
			Alert al = new Alert(AlertType.ERROR, "Invalid input or data missing!");
			al.showAndWait();
		}
		else {

		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String productid = tf_product_id.getText().toString();
		String percentage = tf_percentage.getText().toString();
		String buy = tf_buy.getText().toString();
		String get = tf_get.getText().toString();

		if (percentage.equals("")) {
			percentage = "0";
		}

		if (buy.equals("")) {
			buy = "0";
		}

		if (get.equals("")) {
			get = "0";
		}
		String desc = "Buy " + buy + " Get " + get;

		try {
		String addquery = "INSERT INTO `promotion`(`id`, `name`, `productid`, `percentage`, `description`) "
				+ "VALUES ('" + id + "','" + name + "','" + productid + "','" + percentage + "','" + desc + "')";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		DBInitialize.statement.executeUpdate(addquery);

		// update table
		String query = "SELECT promotion.id, promotion.name, promotion.productid, productitems.name,promotion.percentage, promotion.description FROM promotion, productitems WHERE promotion.productid = productitems.barcode";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);

		// clear promodata array
		promoData.clear();

		while (rs.next()) {
			Promotion pro = new Promotion();
			pro.setId(rs.getString(1));
			pro.setName(rs.getString(2));
			pro.setProductId(rs.getString(3));
			pro.setProductName(rs.getString(4));
			pro.setPercentage(rs.getString(5));
			pro.setMore(rs.getString(6));

			promoData.add(pro);
		}

		// set data to table
		tb_promo.refresh();
		
		tf_id.clear();
		tf_name.clear();
		tf_product_id.clear();
		tf_product_name.clear();
		tf_percentage.clear();
		tf_buy.clear();
		tf_get.clear();

		// show alert
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
	void onUpdateAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals("") ||tf_name.getText().equals("") || tf_product_id.getText().equals("") ||
				tf_percentage.getText().matches(".*[a-zA-Z]+.*")   
				) {
			Alert al = new Alert(AlertType.ERROR, "Invalid input or data missing!");
			al.showAndWait();
		}
		else {
			
	
		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String productid = tf_product_id.getText().toString();
		String percentage = tf_percentage.getText().toString();
		String buy = tf_buy.getText().toString();
		String get = tf_get.getText().toString();

		String desc = "Buy " + buy + " Get " + get;

		String updatequery = "UPDATE `promotion` SET `name`='" + name + "',`productid`='" + productid
				+ "',`percentage`='" + percentage + "',`description`='" + desc + "' WHERE promotion.id = '" + id + "';";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		DBInitialize.statement.executeUpdate(updatequery);

		// update table
		String query = "SELECT promotion.id, promotion.name, promotion.productid, productitems.name,promotion.percentage, promotion.description FROM promotion, productitems WHERE promotion.productid = productitems.barcode";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);

		// clear promodata array
		promoData.clear();

		while (rs.next()) {
			Promotion pro = new Promotion();
			pro.setId(rs.getString(1));
			pro.setName(rs.getString(2));
			pro.setProductId(rs.getString(3));
			pro.setProductName(rs.getString(4));
			pro.setPercentage(rs.getString(5));
			pro.setMore(rs.getString(6));

			promoData.add(pro);
		}

		// set data to table
		tb_promo.refresh();

		
		tf_id.clear();
		tf_name.clear();
		tf_product_id.clear();
		tf_product_name.clear();
		tf_percentage.clear();
		tf_buy.clear();
		tf_get.clear();
		
		// show alert
		Alert al = new Alert(AlertType.INFORMATION, "Item updated!");
		al.showAndWait();
	}
		
	}//end of else

}
