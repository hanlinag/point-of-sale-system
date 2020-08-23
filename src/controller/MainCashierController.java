package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import common.Common;
import database.DBInitialize;
import functs.EditingCell;
import functs.ReportGenerator;
import functs.SearchBarcode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ProductItem;
import model.Sale;
import net.sf.jasperreports.engine.JRException;

public class MainCashierController {

	@FXML
	private Label lb_cashier_name;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton bt_logout;

	@FXML
	private JFXTextField tf_barcode_search;

	@FXML
	private JFXButton bt_new;

	@FXML
	private JFXButton bt_create_card;

	@FXML
	private TableView<ProductItem> tb_total_item;

	private TableColumn<ProductItem, String> col_item_name;

	private TableColumn<ProductItem, String> col_item_category;

	private TableColumn<ProductItem, String> col_item_price;

	private TableColumn<ProductItem, String> col_item_barcode;

	private TableColumn<ProductItem, String> col_item_stock;

	private ObservableList<ProductItem> data = FXCollections.observableArrayList();

	private static ObservableList<Sale> purchasedata = FXCollections.observableArrayList();;

	@FXML
	private TableView<Sale> tb_sale;

	private TableColumn<Sale, String> col_purchase_barcode;

	private TableColumn<Sale, String> col_purchase_name;

	private TableColumn<Sale, String> col_purchase_price;

	private TableColumn<Sale, Integer> col_purchase_quantity;

	private TableColumn<Sale, String> col_purchase_discount;

	private TableColumn<Sale, String> col_purchase_totalamount;

	@FXML
	private JFXButton bt_pay;

	@FXML
	private JFXTextField tf_total;

	@FXML
	private JFXTextField tf_pay_amount;

	@FXML
	private JFXTextField tf_change;

	@FXML
	private JFXTextField tf_name_search;

	@FXML
	private JFXButton btPrint;

	@FXML
	private Label lb_slip_no;

	@FXML
	private JFXButton bt_redeem;

	private Socket s;
	DataInputStream inputFromClient;
	DataOutputStream outputToClient;
	ServerSocket ss;

	private Thread th;
	/*
	 * private Thread th1; private Thread th2;
	 */

	// public static Thread thcashier;

	@FXML
	void onLogoutClick(ActionEvent event) {

		// scene transaction
		try {
			new LoginPg().start((Stage) bt_logout.getScene().getWindow());
			th.interrupt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		// col_item_id.setCellValueFactory(new PropertyValueFactory<ProductItem,
		// String>("id"));

		assert lb_cashier_name != null : "fx:id=\"lb_cashier_name\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert bt_logout != null : "fx:id=\"bt_logout\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tf_barcode_search != null : "fx:id=\"tf_barcode_search\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tf_name_search != null : "fx:id=\"tf_name_search\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert bt_new != null : "fx:id=\"bt_barcode_scan\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert bt_redeem != null : "fx:id=\"bt_redeem\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert bt_create_card != null : "fx:id=\"bt_create_card\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tb_total_item != null : "fx:id=\"tb_total_item\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tb_sale != null : "fx:id=\"tb_sale\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert bt_pay != null : "fx:id=\"bt_pay\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tf_total != null : "fx:id=\"tf_total\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tf_pay_amount != null : "fx:id=\"tf_pay_amount\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert tf_change != null : "fx:id=\"tf_change\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert btPrint != null : "fx:id=\"btPrint\" was not injected: check your FXML file 'cashier_main.fxml'.";
		assert lb_slip_no != null : "fx:id=\"lb_slip_no\" was not injected: check your FXML file 'cashier_main.fxml'.";

		// set slip number
		new DBInitialize().DBInitialize();
		String previousgetpurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
		new DBInitialize();
		ResultSet rsslip = DBInitialize.statement.executeQuery(previousgetpurchaseid);
		String previousid = "";
		while (rsslip.next()) {
			previousid = rsslip.getString("id");
		}
		int nowid = Integer.parseInt(previousid) + 1;

		lb_slip_no.setText("" + nowid);
		Common.slipno = "" + nowid;

		th = new Thread(() -> {
			try {
				//tb_total_item.refresh();
				tf_name_search.clear();
				tf_barcode_search.clear();

				ss = new ServerSocket(5000);
				System.out.println("Server is running at port : 5000");

				while (true) {
					s = ss.accept();
					inputFromClient = new DataInputStream(s.getInputStream());
					outputToClient = new DataOutputStream(s.getOutputStream());

					String datafromandriod = inputFromClient.readUTF();
					// A8:81:95:8B:1C:AC

					System.out.println("Received from android: " + datafromandriod);
					// inputFromClient.close();
					// s.close();

					Platform.runLater(() -> tf_barcode_search.setText("" + datafromandriod));
					// tb_total_item.refresh();
					// tb_total_item.getItems().clear();
					// tb_total_item.refresh();
					data.clear();
					data = SearchBarcode.SearchByBarcode(datafromandriod);
					System.out.println("data from function db qr search is : " + data.get(0).getName());
					// tb_total_item.refresh();
					
					tb_total_item.setItems(data);
					tb_total_item.refresh();
					//tb_total_item.refresh();

				} // end of if
			}
			catch (Exception ex) {
			}
		});
		th.start();
		

		tf_total.setAlignment(Pos.BOTTOM_RIGHT);
		tf_pay_amount.setAlignment(Pos.BOTTOM_RIGHT);
		tf_change.setAlignment(Pos.BOTTOM_RIGHT);

		col_item_name = new TableColumn<ProductItem, String>("Name");
		col_item_category = new TableColumn<ProductItem, String>("Category");
		col_item_price = new TableColumn<ProductItem, String>("Price");
		col_item_barcode = new TableColumn<ProductItem, String>("Barcode");
		col_item_stock = new TableColumn<ProductItem, String>("Stock");

		col_item_name.setMinWidth(200.0);
		col_item_category.setMinWidth(160.0);
		col_item_price.setMinWidth(100.0);
		col_item_barcode.setMinWidth(220.0);
		col_item_stock.setMinWidth(90.0);

		col_item_name.setStyle("-fx-font-size: 18");
		col_item_category.setStyle("-fx-font-size: 18");
		col_item_price.setStyle("-fx-font-size: 18");
		col_item_barcode.setStyle("-fx-font-size: 18");
		col_item_stock.setStyle("-fx-font-size: 18");

		col_item_name.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("name"));
		col_item_category.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("categoryname"));
		col_item_price.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("price"));
		col_item_barcode.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("barcode"));
		col_item_stock.setCellValueFactory(new PropertyValueFactory<ProductItem, String>("stockamount"));

		tb_total_item.getColumns().addAll(col_item_barcode, col_item_name, col_item_category, col_item_price,
				col_item_stock);

		// purchase table
		col_purchase_barcode = new TableColumn<Sale, String>("Barcode");
		col_purchase_name = new TableColumn<Sale, String>("Name");
		col_purchase_price = new TableColumn<Sale, String>("Price");
		col_purchase_quantity = new TableColumn<Sale, Integer>("Quantity");
		col_purchase_discount = new TableColumn<Sale, String>("Discount");
		col_purchase_totalamount = new TableColumn<Sale, String>("TotalAmount");

		col_purchase_barcode.setMinWidth(120.0);
		col_purchase_name.setMinWidth(90.0);
		col_purchase_price.setMinWidth(60.0);
		col_purchase_quantity.setMinWidth(25.0);
		col_purchase_discount.setMinWidth(25.0);
		col_purchase_totalamount.setMinWidth(120.0);

		col_purchase_barcode.setStyle("-fx-font-size: 15");
		col_purchase_name.setStyle("-fx-font-size: 15");
		col_purchase_price.setStyle("-fx-font-size: 15");
		col_purchase_quantity.setStyle("-fx-font-size: 15");
		col_purchase_discount.setStyle("-fx-font-size: 15");
		col_purchase_totalamount.setStyle("-fx-font-size: 15");

		Callback<TableColumn<Sale, Integer>, TableCell<Sale, Integer>> cellFactory = (
				TableColumn<Sale, Integer> param) -> new EditingCell();

		col_purchase_barcode.setCellValueFactory(new PropertyValueFactory<Sale, String>("barcode"));
		col_purchase_name.setCellValueFactory(new PropertyValueFactory<Sale, String>("name"));
		col_purchase_price.setCellValueFactory(new PropertyValueFactory<Sale, String>("unitamount"));

		col_purchase_quantity.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("quantity"));
		col_purchase_quantity.setCellFactory(cellFactory);
		col_purchase_quantity.setOnEditCommit(new EventHandler<CellEditEvent<Sale, Integer>>() {
			@Override
			public void handle(CellEditEvent<Sale, Integer> t) {
				((Sale) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity((t.getNewValue()));

				System.out.println("Qty edit Working");

				t.getRowValue().setQuantity(t.getNewValue());
				double qty = ((Sale) t.getTableView().getItems().get(t.getTablePosition().getRow())).getQuantity();

				Double discountpercent = ((Sale) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.getDiscount();

				String discountmore = ((Sale) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.getDiscountmore();

				String itemmId = ((Sale) t.getTableView().getItems().get(t.getTablePosition().getRow())).getBarcode();

				double unitprice = t.getRowValue().getUnitamount();
				double total1 = unitprice * qty;

				double total = 0;
				// promotion compute
				if (discountpercent.equals("0")) {

				} else {
					double tominuspromotion = total1 * (discountpercent / 100);
					total = total1 - tominuspromotion;
				}

				if (discountmore.equals("Buy 0 Get 0")) {

				} else {
					/*
					 * Common.buygetdata.add(((Sale) t.getTableView().getItems().get(
					 * t.getTablePosition().getRow()) ).getBarcode());
					 */
				}

				System.out.println("buy get is " + Common.buygetdata);

				t.getRowValue().setTotalamount(total);
				tb_sale.refresh();
				tb_sale.getColumns().get(0).setVisible(false);
				tb_sale.getColumns().get(0).setVisible(true);

				int totalall = 0;
				for (Sale i : tb_sale.getItems()) {
					totalall += i.getTotalamount();
				}
				tf_total.setText("" + totalall);
				Common.totalAmount = Double.parseDouble(tf_total.getText());
				System.out.println("Total amount is : " + Common.totalAmount);
				// tb_sale.refresh();
			}
		});

		col_purchase_discount.setCellValueFactory(new PropertyValueFactory<Sale, String>("discount"));

		/*
		 * col_purchase_totalamount.setCellValueFactory(new
		 * Callback<CellDataFeatures<Sale, String>, ObservableValue<String>>() {
		 * 
		 * 
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<Sale, String> param) {
		 * 
		 * 
		 * double total = param.getValue().getQuantity() *
		 * Double.parseDouble(param.getValue().getUnitamount());
		 * 
		 * 
		 * return new SimpleStringProperty(""+total);
		 * 
		 * }
		 * 
		 * });
		 */
		col_purchase_totalamount.setCellValueFactory(new PropertyValueFactory<Sale, String>("totalamount"));

		/*
		 * col_purchase_totalamount.setCellValueFactory(cellData -> { Sale data =
		 * cellData.getValue(); return Bindings.createDoubleBinding( () -> { try {
		 * double price = data.getUnitamount(); double quantity = data.getQuantity();
		 * return price * quantity ; } catch (NumberFormatException nfe) { return 0 ; }
		 * }, data.totalamountProperty(), data.quantityProperty() ); });
		 */
		tb_sale.setEditable(true);
		tb_sale.setItems(purchasedata);
		tb_sale.getColumns().addAll(col_purchase_barcode, col_purchase_name, col_purchase_price, col_purchase_quantity,
				col_purchase_discount, col_purchase_totalamount);
		tb_sale.refresh();

		/*
		 * col_item_id.setCellValueFactory(new Callback<CellDataFeatures<ProductItem,
		 * String>, ObservableValue<String>>() {
		 * 
		 * public ObservableValue<String> call(CellDataFeatures<ProductItem, String>
		 * param) {
		 * 
		 * return new SimpleStringProperty(""); } });
		 */

		// set cashier name
		lb_cashier_name.setText(Common.cashierrec.getName());

		// get data from db and set it to table
		new DBInitialize().DBInitialize();

		String tablequery = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";
		ResultSet rs = DBInitialize.statement.executeQuery(tablequery);
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

			data.add(p);
		}

		tb_total_item.setItems(data);

		tb_total_item.setRowFactory(t -> {
			TableRow<ProductItem> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				// get data from selected row
				// ProductItem productItem =
				// tb_total_item.getSelectionModel().getSelectedItem();
				// System.out.println("Select row is : "+productItem.getName());

				if (e.getClickCount() == 2 && (!row.isEmpty())) {
					String dispercentage = "0";
					String dismore = "Buy 0 Get 0";

					ProductItem product = tb_total_item.getSelectionModel().getSelectedItem();
					System.out.println("Double click is: " + product.getName());

					// get discount form db
					String discountQuery = "SELECT promotion.percentage, promotion.description FROM `promotion` WHERE promotion.productid = '"
							+ product.getBarcode() + "';";
					try {
						new DBInitialize().DBInitialize();
						new DBInitialize();
						ResultSet rsd = DBInitialize.statement.executeQuery(discountQuery);

						if (rsd.next()) {
							dispercentage = rsd.getString(1);
							dismore = rsd.getString(2);
						} else {
							System.out.println("no discount");
						}

						System.out
								.println("percentage from db is ::::" + dispercentage + " &&&& more is ::::" + dismore);
						product.setDiscount(dispercentage);
						product.setDiscountmore(dismore);

					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// create virtual sale item
					Sale sa = new Sale();

					double total = 0;
					// promotion compute
					if (dispercentage.equals("0")) {

						sa.setTotalamount(Double.parseDouble(product.getPrice()));
						System.out.println("dispercent 0 is working");
					} else {

						System.out.println("unit price is :::" + product.getPrice());
						System.out.println("discount percnet is :::" + dispercentage);
						double tominuspromotion = Double.parseDouble(product.getPrice())
								* (Double.parseDouble(dispercentage) / 100);
						total = Double.parseDouble(product.getPrice()) - tominuspromotion;
						System.out.println("unit amount after discount is : " + total);

						sa.setTotalamount(Double.parseDouble(total + ""));
						System.out.println("dispercent compute is working");
					}

					if (dismore.equals("Buy 0 Get 0")) {
						// do nothing
					} else {
						Common.buygetdata.add(product.getBarcode());
					}

					System.out.println("buy get is :::::::::::::" + Common.buygetdata);

					// set sale data
					sa.setBarcode("" + product.getBarcode());
					sa.setName(product.getName());
					sa.setQuantity(1);
					sa.setUnitamount(Double.parseDouble(product.getPrice()));
					sa.setDiscount(Double.parseDouble(product.getDiscount()));
					sa.setDiscountmore(product.getDiscountmore());

					// double totalAmount = count * Double.parseDouble(sale.getUnitamount());
					// sa.setTotalamount(Double.parseDouble(""+product.getPrice()));

					purchasedata.add(sa);
					tb_sale.refresh();
					int totalall = 0;
					for (Sale i : tb_sale.getItems()) {
						totalall += i.getTotalamount();
					}
					tf_total.setText("" + totalall);
					Common.totalAmount = Double.parseDouble(tf_total.getText());
					System.out.println("Total amount is : " + Common.totalAmount);
				}
			});

			return row;
		});

		tb_sale.setRowFactory(t -> {
			TableRow<Sale> r = new TableRow<>();
			r.setOnMouseClicked(e -> {
				// get data from selected row
				// ProductItem productItem =
				// tb_total_item.getSelectionModel().getSelectedItem();
				// System.out.println("Select row is : "+productItem.getName());

				if (e.getClickCount() == 2 && (!r.isEmpty())) {
					Sale sale = tb_sale.getSelectionModel().getSelectedItem();
					System.out.println("sale Double click is: " + sale.getName());

				}

				tb_sale.refresh();
			});

			final ContextMenu rowMenu = new ContextMenu();

			MenuItem removeItem = new MenuItem("Delete");
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Sale s = tb_sale.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION, "Are U Sure To Delete " + s.getName() + " ?",
							ButtonType.YES, ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff

						// reduce all total ammount
						Sale se = purchasedata.get(tb_sale.getSelectionModel().getFocusedIndex());
						Common.totalAmount = Common.totalAmount - se.getTotalamount();
						tf_total.setText("" + Common.totalAmount);
						tf_pay_amount.clear();
						tf_change.clear();
						purchasedata.remove(tb_sale.getSelectionModel().getFocusedIndex());

						tb_sale.refresh();

					}
				}
			});
			rowMenu.getItems().addAll(removeItem);

			// only display context menu for non-null items:
			r.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(r.itemProperty())).then(rowMenu).otherwise((ContextMenu) null));

			return r;
		});
		// display total
		// tf_total.textProperty().setValue();

		// tf_total.setText(""+total);

	}

	@FXML
	void onbtRedeemClick(ActionEvent event) {

		Stage sg = new Stage();
		FXMLLoader root = new FXMLLoader(getClass().getResource("/ui/Card_redeem.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(((Parent) root.load()), 600, 450);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sg.setScene(scene);
		sg.setResizable(false);
		sg.setMaximized(false);
		sg.setTitle("Redeem Cash Card");
		sg.getIcons().add(new Image("graphic/poslogorect.png"));
		sg.initModality(Modality.APPLICATION_MODAL);
		sg.show();

	}

	// for screen transaction from login to admin panel
	public class LoginPg extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/ui/Page_login.fxml"));

			Scene scene = new Scene(root, 1320, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cashier");
			// primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.show();
		}
	}

	// search product item table by barcode or name
	@FXML
	void tfTypeSearchAction(KeyEvent event) throws ClassNotFoundException, SQLException, InterruptedException {


		th.sleep(1);

		tf_name_search.clear();

		/*
		 * if(!th.isInterrupted()) { th.start();
		 * System.out.println("------------------------------- xD"); }else {
		 * th.interrupt(); System.out.println("------------------------------- xP"); }
		 */

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
			tb_total_item.setItems(row);
			// System.out.println("working1"+data);

			// tb_total_item.getItems().clear();
			// tb_total_item.setItems(data);

			System.out.println("working2");
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

	@FXML
	void tfNameSearchAction(KeyEvent event) throws InterruptedException {

		th.sleep(1);
		tf_barcode_search.clear();

		/*
		 * if(!th.isInterrupted()) { th.start();
		 * System.out.println("------------------------------- xD"); }else {
		 * th.interrupt(); System.out.println("------------------------------- xP"); }
		 */

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
			tb_total_item.setItems(row);
			// System.out.println("working1"+data);

			// tb_total_item.getItems().clear();
			// tb_total_item.setItems(data);

			System.out.println("working2");
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

		

	}

	@FXML
	void onEnterButtonClick(ActionEvent event) {

		if (tf_pay_amount.getText().equals("")) {
			Alert al = new Alert(AlertType.ERROR, "No Input!");
			al.showAndWait();
		} else if (tf_pay_amount.getText().matches(".*[a-zA-Z]+.*")) {
			Alert al = new Alert(AlertType.ERROR, "Please input the right amount in number!");
			al.showAndWait();
		} else if (Double.parseDouble(tf_pay_amount.getText()) < Common.totalAmount) {
			// do nothing
			Alert al = new Alert(AlertType.ERROR, "Invalid amount!");
			al.showAndWait();
			tf_pay_amount.clear();
		} else {
			try {
				Common.payamount = Double.parseDouble(tf_pay_amount.getText());
				Common.change = Common.payamount - Common.totalAmount;
				tf_change.setText("" + Common.change);
				System.out.println("Total Amount is: " + Common.totalAmount);
				System.out.println("Pay Amount is: " + Common.payamount);
				System.out.println("Change is: " + Common.change);
			} catch (Exception ex) {
				System.out.println("Error in payamount: " + ex.getMessage());
			}

		} // end of else
	}

	// customer number textfield
	public class NumberTextField extends TextField {

		@Override
		public void replaceText(int start, int end, String text) {
			if (validate(text)) {
				super.replaceText(start, end, text);
			}
		}

		@Override
		public void replaceSelection(String text) {
			if (validate(text)) {
				super.replaceSelection(text);
			}
		}

		private boolean validate(String text) {
			return text.matches("[0-9]*");
		}
	}

	@FXML
	void onbtCreateCardClick(ActionEvent event) {

		Stage sg = new Stage();
		FXMLLoader root = new FXMLLoader(getClass().getResource("/ui/Create_card.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(((Parent) root.load()), 600, 450);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sg.setScene(scene);
		sg.setResizable(true);
		sg.setMaximized(false);
		sg.setTitle("Create Card");
		sg.getIcons().add(new Image("graphic/poslogorect.png"));
		sg.initModality(Modality.APPLICATION_MODAL);
		sg.show();

	}

	@FXML
	void onBtPayClick(ActionEvent event) {
		// get all data from table and add to oberable list
		if (tf_total.getText().equals("")) {
			System.out.println("Do nothing");
			Alert al = new Alert(AlertType.ERROR, "No item to sale");
			al.showAndWait();
		} else {

			ObservableList<Sale> saledata = FXCollections.observableArrayList();
			Common.saleitemsdatafromsaletable = saledata;// for generating report in pay action

			saledata = tb_sale.getItems();
			StringBuilder productid = new StringBuilder();
			StringBuilder qty = new StringBuilder();
			for (int i = 0; i < saledata.size(); i++) {
				productid.append(saledata.get(i).getBarcode());
				productid.append(",");

				qty.append(saledata.get(i).getQuantity());
				qty.append(",");

			}

			Common.totalAmount = Double.parseDouble(tf_total.getText());
			// Common.saleitemsdatafromsaletable = saledata;
			Common.productids = productid.toString();
			Common.productqtys = qty.toString();
			Common.saleitemsdatafromsaletable = saledata;// for generating report in pay action
			Common.paidtype = "Card";

			System.out.println("appended qty is : " + qty.toString());

			// open new stage
			Stage sg = new Stage();
			FXMLLoader root = new FXMLLoader(getClass().getResource("/ui/Card_pay.fxml"));
			Scene scene = null;
			try {
				scene = new Scene(((Parent) root.load()), 550, 330);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sg.setScene(scene);
			sg.setResizable(true);
			sg.setMaximized(false);
			sg.setTitle("Card Payment");
			sg.getIcons().add(new Image("graphic/poslogorect.png"));
			sg.initModality(Modality.APPLICATION_MODAL);
			sg.show();
		}

	}

	@FXML
	void onbtPrintClick(ActionEvent event)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		if (tf_change.getText().equals("") || tf_pay_amount.getText().equals("")) {
			// do nothing
			tf_change.clear();
			tf_pay_amount.clear();
			/*
			 * Common.change = 0; Common.payamount = 0; Common.totalAmount =
			 * Double.parseDouble(tf_total.getText().toString());
			 */
			Alert al = new Alert(AlertType.ERROR, "Please enter pay amount!");
			al.showAndWait();
		} else {

			ObservableList<Sale> saledata = FXCollections.observableArrayList();
			saledata = tb_sale.getItems();
			StringBuilder productid = new StringBuilder();
			StringBuilder qty = new StringBuilder();
			for (int i = 0; i < saledata.size(); i++) {
				productid.append(saledata.get(i).getBarcode());
				productid.append(",");

				qty.append(saledata.get(i).getQuantity());
				qty.append(",");

			}
			// add purchase items id to purchase table
			System.out.println("Product barcodes are : " + productid);
			System.out.println("Product quantities are : " + qty);

			// get purchase id from previous row
			// get previous id and create now id
			try {
				new DBInitialize().DBInitialize();
				String previousgetpurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
				new DBInitialize();
				ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid);
				String previousid = "";
				while (rs.next()) {
					previousid = rs.getString("id");
				}
				int nowid = Integer.parseInt(previousid) + 1;
				System.out.println("purchase id for now is : " + nowid);
				Common.purchaseid = nowid;

				// create today date
				String pattern = "dd/MM/yyyy";
				String purchasedate = new SimpleDateFormat(pattern).format(new Date());
				System.out.println("date purchase is " + purchasedate);

				// create today current time(purhcase time)
				String myDateString = "13:24:40";
				LocalTime localTime = LocalTime.parse(myDateString, DateTimeFormatter.ofPattern("HH:mm:ss"));
				int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
				int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
				int second = localTime.get(ChronoField.SECOND_OF_MINUTE);
				String currenttime = hour + ":" + minute + ":" + second + "";
				System.out.println("purhcase time is : " + currenttime);
				// Common.currenttime = currenttime;

				Common.totalAmount = Double.parseDouble(tf_total.getText());

				// db
				new DBInitialize().DBInitialize();
				new DBInitialize();
				String querycreatepurchase = "INSERT INTO `purchase`(`id`, `date`, `time`, `cashierid`, `barcode`, `quantity`, `totalamount` ) "
						+ "VALUES (" + Common.purchaseid + ", '" + purchasedate + "', '" + currenttime + "','"
						+ Common.cashierrec.getId() + "','" + productid + "','" + qty + "','" + Common.totalAmount
						+ "')";
				DBInitialize.statement.executeUpdate(querycreatepurchase);

				// update count and stock amount
				// get all the purchase id and count +1 in db
				String[] purchasedproductitemsid = productid.toString().split(",");
				String[] purchasedproductitemsqty = qty.toString().split(",");

				for (int i = 0; i < purchasedproductitemsid.length; i++) {
					int oldcount = 0;
					int newcount = 0;
					// get old count
					String getOldCountQuery = "SELECT `count` FROM `productitems` WHERE productitems.barcode = '"
							+ purchasedproductitemsid[i] + "';";
					System.out.println("product barcode is " + purchasedproductitemsid[i]);
					new DBInitialize().DBInitialize();
					new DBInitialize();
					ResultSet rsoldc = DBInitialize.statement.executeQuery(getOldCountQuery);
					while (rsoldc.next()) {
						oldcount = rsoldc.getInt(1);
					}
					System.out.println("old count is : " + oldcount);

					newcount = oldcount + Integer.parseInt(purchasedproductitemsqty[i]);
					System.out.println("new count is " + newcount + "purchase qty" + purchasedproductitemsqty[i]);
					String updatecountQuery = "UPDATE `productitems` SET `count`= " + newcount
							+ " WHERE productitems.barcode = '" + purchasedproductitemsid[i] + "'";
					new DBInitialize().DBInitialize();
					new DBInitialize();
					DBInitialize.statement.executeUpdate(updatecountQuery);

					// get stock amount
					String getstockquery = "SELECT `stockamount` FROM `productitems` WHERE productitems.barcode = '"
							+ purchasedproductitemsid[i] + "';";
					String oldstock = "";
					new DBInitialize();
					ResultSet rsst = DBInitialize.statement.executeQuery(getstockquery);
					while (rsst.next()) {
						oldstock = rsst.getString(1);
					}
					String newstock = "" + (Integer.parseInt(oldstock) - Integer.parseInt(purchasedproductitemsqty[i]));
					// update stock
					String updatestockquery = "UPDATE `productitems` SET `stockamount`='" + newstock
							+ "' WHERE productitems.barcode = '" + purchasedproductitemsid[i] + "'";
					new DBInitialize();
					DBInitialize.statement.executeUpdate(updatestockquery);

				} // end of for

				/*
				 * System.out.println("qty is: "); for(int i = 0; i<
				 * purchasedproductitemsqty.length ; i++) {
				 * System.out.println(""+purchasedproductitemsqty[i]); }
				 * System.out.println("length of id and qty are : "+purchasedproductitemsid.
				 * length+"  & "+purchasedproductitemsqty.length);
				 */
				// MainCashierController.clearsaletableitems();
				// ((Stage)bt_pay.getScene().getWindow()).close();
				// alert

				Common.payamount = Double.parseDouble(tf_pay_amount.getText().toString());
				Common.change = Double.parseDouble(tf_change.getText().toString());
				Common.paidtype = "Cash";
				Common.cardinfo = "";

				tf_total.clear();
				tf_pay_amount.clear();
				tf_change.clear();

				// update in stock table
				// get data from db and set it to table
				new DBInitialize().DBInitialize();
				data.clear();

				String tablequery = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";
				ResultSet rsu = DBInitialize.statement.executeQuery(tablequery);
				while (rsu.next()) {

					ProductItem pr = new ProductItem();
					pr.setBarcode(rsu.getString(1));
					pr.setName(rsu.getString(2));
					pr.setCategoryname(rsu.getString(3));
					pr.setPrice(rsu.getString(4));
					pr.setSuppliername(rsu.getString(5));
					pr.setDateadded(rsu.getString(6));
					pr.setStockamount(rsu.getString(7));
					pr.setExpiredate(rsu.getString(8));

					data.add(pr);
				}

				tb_total_item.refresh();

				/*
				 * //alert Alert trancompleteal = new Alert(AlertType.INFORMATION,
				 * "Please wait! Voucher is being generated..."); trancompleteal.showAndWait();
				 */

				// Common.payamount =
				// generate report
				try {
					new ReportGenerator().generatevoucher(saledata);
					// trancompleteal.close();
				} catch (JRException | FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// clear sale data
				purchasedata.clear();
				tb_sale.refresh();
				Common.totalAmount = 0;
				tf_total.clear();

				// set slip number
				new DBInitialize().DBInitialize();
				String getpreviouspurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
				new DBInitialize();
				ResultSet rsslipno = DBInitialize.statement.executeQuery(previousgetpurchaseid);
				String olddid = "";
				while (rsslipno.next()) {
					olddid = rsslipno.getString("id");
				}
				int nowwid = Integer.parseInt(olddid) + 1;

				lb_slip_no.setText("" + nowwid);
				Common.slipno = "" + nowwid;

				Common.buygetdata.clear();
				Common.buygetitem = "";
				Common.buygetpromo = "";
				Common.totalAmount = 0;
				Common.change = 0;
				Common.payamount = 0;

			} // end of try
			catch (Exception ex) {
				Alert al = new Alert(AlertType.ERROR, "" + ex.getMessage());
				al.showAndWait();
			}
		}

	}

	@FXML
	void onbtNewClick(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		
		Common.buygetdata.clear();
		Common.saleitemsdatafromsaletable.clear();
		// set slip number
		new DBInitialize().DBInitialize();
		String previousgetpurchaseid = " SELECT `id` FROM `purchase` ORDER BY `id` DESC LIMIT 1 ";
		new DBInitialize();
		ResultSet rsslip = DBInitialize.statement.executeQuery(previousgetpurchaseid);
		String previousid = "";
		while (rsslip.next()) {
			previousid = rsslip.getString("id");
		}
		int nowid = Integer.parseInt(previousid) + 1;

		lb_slip_no.setText("" + nowid);
		Common.slipno = "" + nowid;

		// clear sale data
		purchasedata.clear();
		tb_sale.refresh();
		Common.totalAmount = 0;
		tf_total.clear();
		tf_pay_amount.clear();
		tf_change.clear();

		// update instock table
		// get data from db and set it to table
		new DBInitialize().DBInitialize();
		data.clear();

		String tablequery = "SELECT productitems.barcode, productitems.name, productcategory.name, productitems.price, supplier.companyname, productitems.dateadded, productitems.stockamount, productitems.expireddate FROM productitems, supplier,productcategory WHERE productitems.categoryid = productcategory.id AND productitems.supplierid = supplier.id ORDER BY productitems.barcode DESC;";
		ResultSet rs = DBInitialize.statement.executeQuery(tablequery);
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

			data.addAll(p);
		}

		tb_total_item.refresh();
		
		//tf_name_search.clear();
		//tf_barcode_search.clear();
		
	}

}
