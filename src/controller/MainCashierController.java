package controller;

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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import common.Common;
import database.DBInitialize;
import functs.EditingCell;
import functs.ReportGenerator;
import functs.SearchBarcode;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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

		assert lb_cashier_name != null : Messages.getString("MainCashierController.0"); //$NON-NLS-1$
		assert bt_logout != null : Messages.getString("MainCashierController.1"); //$NON-NLS-1$
		assert tf_barcode_search != null : Messages.getString("MainCashierController.2"); //$NON-NLS-1$
		assert tf_name_search != null : Messages.getString("MainCashierController.3"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("MainCashierController.4"); //$NON-NLS-1$
		assert bt_redeem != null : Messages.getString("MainCashierController.5"); //$NON-NLS-1$
		assert bt_create_card != null : Messages.getString("MainCashierController.6"); //$NON-NLS-1$
		assert tb_total_item != null : Messages.getString("MainCashierController.7"); //$NON-NLS-1$
		assert tb_sale != null : Messages.getString("MainCashierController.8"); //$NON-NLS-1$
		assert bt_pay != null : Messages.getString("MainCashierController.9"); //$NON-NLS-1$
		assert tf_total != null : Messages.getString("MainCashierController.10"); //$NON-NLS-1$
		assert tf_pay_amount != null : Messages.getString("MainCashierController.11"); //$NON-NLS-1$
		assert tf_change != null : Messages.getString("MainCashierController.12"); //$NON-NLS-1$
		assert btPrint != null : Messages.getString("MainCashierController.13"); //$NON-NLS-1$
		assert lb_slip_no != null : Messages.getString("MainCashierController.14"); //$NON-NLS-1$

		// set slip number
		new DBInitialize().DBInitialize();
		String previousgetpurchaseid = Messages.getString("MainCashierController.15"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsslip = DBInitialize.statement.executeQuery(previousgetpurchaseid);
		String previousid = Messages.getString("MainCashierController.16"); //$NON-NLS-1$
		while (rsslip.next()) {
			previousid = rsslip.getString(Messages.getString("MainCashierController.17")); //$NON-NLS-1$
		}
		int nowid = Integer.parseInt(previousid) + 1;

		lb_slip_no.setText(Messages.getString("MainCashierController.18") + nowid); //$NON-NLS-1$
		Common.slipno = Messages.getString("MainCashierController.19") + nowid; //$NON-NLS-1$

		th = new Thread(() -> {
			try {
				//tb_total_item.refresh();
				tf_name_search.clear();
				tf_barcode_search.clear();

				ss = new ServerSocket(5000);
				System.out.println(Messages.getString("MainCashierController.20")); //$NON-NLS-1$

				while (true) {
					s = ss.accept();
					inputFromClient = new DataInputStream(s.getInputStream());
					outputToClient = new DataOutputStream(s.getOutputStream());

					String datafromandriod = inputFromClient.readUTF();
					// A8:81:95:8B:1C:AC

					System.out.println(Messages.getString("MainCashierController.21") + datafromandriod); //$NON-NLS-1$
					// inputFromClient.close();
					// s.close();

					Platform.runLater(() -> tf_barcode_search.setText(Messages.getString("MainCashierController.22") + datafromandriod)); //$NON-NLS-1$
					// tb_total_item.refresh();
					// tb_total_item.getItems().clear();
					// tb_total_item.refresh();
					data.clear();
					data = SearchBarcode.SearchByBarcode(datafromandriod);
					System.out.println(Messages.getString("MainCashierController.23") + data.get(0).getName()); //$NON-NLS-1$
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

		col_item_name = new TableColumn<ProductItem, String>(Messages.getString("MainCashierController.24")); //$NON-NLS-1$
		col_item_category = new TableColumn<ProductItem, String>(Messages.getString("MainCashierController.25")); //$NON-NLS-1$
		col_item_price = new TableColumn<ProductItem, String>(Messages.getString("MainCashierController.26")); //$NON-NLS-1$
		col_item_barcode = new TableColumn<ProductItem, String>(Messages.getString("MainCashierController.27")); //$NON-NLS-1$
		col_item_stock = new TableColumn<ProductItem, String>(Messages.getString("MainCashierController.28")); //$NON-NLS-1$

		col_item_name.setMinWidth(200.0);
		col_item_category.setMinWidth(160.0);
		col_item_price.setMinWidth(100.0);
		col_item_barcode.setMinWidth(220.0);
		col_item_stock.setMinWidth(90.0);

		col_item_name.setStyle(Messages.getString("MainCashierController.29")); //$NON-NLS-1$
		col_item_category.setStyle(Messages.getString("MainCashierController.30")); //$NON-NLS-1$
		col_item_price.setStyle(Messages.getString("MainCashierController.31")); //$NON-NLS-1$
		col_item_barcode.setStyle(Messages.getString("MainCashierController.32")); //$NON-NLS-1$
		col_item_stock.setStyle(Messages.getString("MainCashierController.33")); //$NON-NLS-1$

		col_item_name.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("MainCashierController.34"))); //$NON-NLS-1$
		col_item_category.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("MainCashierController.35"))); //$NON-NLS-1$
		col_item_price.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("MainCashierController.36"))); //$NON-NLS-1$
		col_item_barcode.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("MainCashierController.37"))); //$NON-NLS-1$
		col_item_stock.setCellValueFactory(new PropertyValueFactory<ProductItem, String>(Messages.getString("MainCashierController.38"))); //$NON-NLS-1$

		tb_total_item.getColumns().addAll(col_item_barcode, col_item_name, col_item_category, col_item_price,
				col_item_stock);

		// purchase table
		col_purchase_barcode = new TableColumn<Sale, String>(Messages.getString("MainCashierController.39")); //$NON-NLS-1$
		col_purchase_name = new TableColumn<Sale, String>(Messages.getString("MainCashierController.40")); //$NON-NLS-1$
		col_purchase_price = new TableColumn<Sale, String>(Messages.getString("MainCashierController.41")); //$NON-NLS-1$
		col_purchase_quantity = new TableColumn<Sale, Integer>(Messages.getString("MainCashierController.42")); //$NON-NLS-1$
		col_purchase_discount = new TableColumn<Sale, String>(Messages.getString("MainCashierController.43")); //$NON-NLS-1$
		col_purchase_totalamount = new TableColumn<Sale, String>(Messages.getString("MainCashierController.44")); //$NON-NLS-1$

		col_purchase_barcode.setMinWidth(120.0);
		col_purchase_name.setMinWidth(90.0);
		col_purchase_price.setMinWidth(60.0);
		col_purchase_quantity.setMinWidth(25.0);
		col_purchase_discount.setMinWidth(25.0);
		col_purchase_totalamount.setMinWidth(120.0);

		col_purchase_barcode.setStyle(Messages.getString("MainCashierController.45")); //$NON-NLS-1$
		col_purchase_name.setStyle(Messages.getString("MainCashierController.46")); //$NON-NLS-1$
		col_purchase_price.setStyle(Messages.getString("MainCashierController.47")); //$NON-NLS-1$
		col_purchase_quantity.setStyle(Messages.getString("MainCashierController.48")); //$NON-NLS-1$
		col_purchase_discount.setStyle(Messages.getString("MainCashierController.49")); //$NON-NLS-1$
		col_purchase_totalamount.setStyle(Messages.getString("MainCashierController.50")); //$NON-NLS-1$

		Callback<TableColumn<Sale, Integer>, TableCell<Sale, Integer>> cellFactory = (
				TableColumn<Sale, Integer> param) -> new EditingCell();

		col_purchase_barcode.setCellValueFactory(new PropertyValueFactory<Sale, String>(Messages.getString("MainCashierController.51"))); //$NON-NLS-1$
		col_purchase_name.setCellValueFactory(new PropertyValueFactory<Sale, String>(Messages.getString("MainCashierController.52"))); //$NON-NLS-1$
		col_purchase_price.setCellValueFactory(new PropertyValueFactory<Sale, String>(Messages.getString("MainCashierController.53"))); //$NON-NLS-1$

		col_purchase_quantity.setCellValueFactory(new PropertyValueFactory<Sale, Integer>(Messages.getString("MainCashierController.54"))); //$NON-NLS-1$
		col_purchase_quantity.setCellFactory(cellFactory);
		col_purchase_quantity.setOnEditCommit(new EventHandler<CellEditEvent<Sale, Integer>>() {
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void handle(CellEditEvent<Sale, Integer> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setQuantity((t.getNewValue()));

				System.out.println(Messages.getString("MainCashierController.55")); //$NON-NLS-1$

				t.getRowValue().setQuantity(t.getNewValue());
				double qty = t.getTableView().getItems().get(t.getTablePosition().getRow()).getQuantity();

				Double discountpercent = t.getTableView().getItems().get(t.getTablePosition().getRow())
						.getDiscount();

				String discountmore = t.getTableView().getItems().get(t.getTablePosition().getRow())
						.getDiscountmore();

				String itemmId = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBarcode();

				double unitprice = t.getRowValue().getUnitamount();
				double total1 = unitprice * qty;

				double total = 0;
				// promotion compute
				if (discountpercent.equals(Messages.getString("MainCashierController.56"))) { //$NON-NLS-1$

				} else {
					double tominuspromotion = total1 * (discountpercent / 100);
					total = total1 - tominuspromotion;
				}

				if (discountmore.equals(Messages.getString("MainCashierController.57"))) { //$NON-NLS-1$

				} else {
					/*
					 * Common.buygetdata.add(((Sale) t.getTableView().getItems().get(
					 * t.getTablePosition().getRow()) ).getBarcode());
					 */
				}

				System.out.println(Messages.getString("MainCashierController.58") + Common.buygetdata); //$NON-NLS-1$

				t.getRowValue().setTotalamount(total);
				tb_sale.refresh();
				tb_sale.getColumns().get(0).setVisible(false);
				tb_sale.getColumns().get(0).setVisible(true);

				int totalall = 0;
				for (Sale i : tb_sale.getItems()) {
					totalall += i.getTotalamount();
				}
				tf_total.setText(Messages.getString("MainCashierController.59") + totalall); //$NON-NLS-1$
				Common.totalAmount = Double.parseDouble(tf_total.getText());
				System.out.println(Messages.getString("MainCashierController.60") + Common.totalAmount); //$NON-NLS-1$
				// tb_sale.refresh();
			}
		});

		col_purchase_discount.setCellValueFactory(new PropertyValueFactory<Sale, String>(Messages.getString("MainCashierController.61"))); //$NON-NLS-1$

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
		col_purchase_totalamount.setCellValueFactory(new PropertyValueFactory<Sale, String>(Messages.getString("MainCashierController.62"))); //$NON-NLS-1$

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

		String tablequery = Messages.getString("MainCashierController.63"); //$NON-NLS-1$
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
					String dispercentage = Messages.getString("MainCashierController.64"); //$NON-NLS-1$
					String dismore = Messages.getString("MainCashierController.65"); //$NON-NLS-1$

					ProductItem product = tb_total_item.getSelectionModel().getSelectedItem();
					System.out.println(Messages.getString("MainCashierController.66") + product.getName()); //$NON-NLS-1$

					// get discount form db
					String discountQuery = Messages.getString("MainCashierController.67") //$NON-NLS-1$
							+ product.getBarcode() + Messages.getString("MainCashierController.68"); //$NON-NLS-1$
					try {
						new DBInitialize().DBInitialize();
						new DBInitialize();
						ResultSet rsd = DBInitialize.statement.executeQuery(discountQuery);

						if (rsd.next()) {
							dispercentage = rsd.getString(1);
							dismore = rsd.getString(2);
						} else {
							System.out.println(Messages.getString("MainCashierController.69")); //$NON-NLS-1$
						}

						System.out
								.println(Messages.getString("MainCashierController.70") + dispercentage + Messages.getString("MainCashierController.71") + dismore); //$NON-NLS-1$ //$NON-NLS-2$
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
					if (dispercentage.equals(Messages.getString("MainCashierController.72"))) { //$NON-NLS-1$

						sa.setTotalamount(Double.parseDouble(product.getPrice()));
						System.out.println(Messages.getString("MainCashierController.73")); //$NON-NLS-1$
					} else {

						System.out.println(Messages.getString("MainCashierController.74") + product.getPrice()); //$NON-NLS-1$
						System.out.println(Messages.getString("MainCashierController.75") + dispercentage); //$NON-NLS-1$
						double tominuspromotion = Double.parseDouble(product.getPrice())
								* (Double.parseDouble(dispercentage) / 100);
						total = Double.parseDouble(product.getPrice()) - tominuspromotion;
						System.out.println(Messages.getString("MainCashierController.76") + total); //$NON-NLS-1$

						sa.setTotalamount(Double.parseDouble(total + Messages.getString("MainCashierController.77"))); //$NON-NLS-1$
						System.out.println(Messages.getString("MainCashierController.78")); //$NON-NLS-1$
					}

					if (dismore.equals(Messages.getString("MainCashierController.79"))) { //$NON-NLS-1$
						// do nothing
					} else {
						Common.buygetdata.add(product.getBarcode());
					}

					System.out.println(Messages.getString("MainCashierController.80") + Common.buygetdata); //$NON-NLS-1$

					// set sale data
					sa.setBarcode(Messages.getString("MainCashierController.81") + product.getBarcode()); //$NON-NLS-1$
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
					tf_total.setText(Messages.getString("MainCashierController.82") + totalall); //$NON-NLS-1$
					Common.totalAmount = Double.parseDouble(tf_total.getText());
					System.out.println(Messages.getString("MainCashierController.83") + Common.totalAmount); //$NON-NLS-1$
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
					System.out.println(Messages.getString("MainCashierController.84") + sale.getName()); //$NON-NLS-1$

				}

				tb_sale.refresh();
			});

			final ContextMenu rowMenu = new ContextMenu();

			MenuItem removeItem = new MenuItem(Messages.getString("MainCashierController.85")); //$NON-NLS-1$
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Sale s = tb_sale.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION, Messages.getString("MainCashierController.86") + s.getName() + Messages.getString("MainCashierController.87"), //$NON-NLS-1$ //$NON-NLS-2$
							ButtonType.YES, ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff

						// reduce all total ammount
						Sale se = purchasedata.get(tb_sale.getSelectionModel().getFocusedIndex());
						Common.totalAmount = Common.totalAmount - se.getTotalamount();
						tf_total.setText(Messages.getString("MainCashierController.88") + Common.totalAmount); //$NON-NLS-1$
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
		FXMLLoader root = new FXMLLoader(getClass().getResource(Messages.getString("MainCashierController.89"))); //$NON-NLS-1$
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
		sg.setTitle(Messages.getString("MainCashierController.90")); //$NON-NLS-1$
		sg.getIcons().add(new Image(Messages.getString("MainCashierController.91"))); //$NON-NLS-1$
		sg.initModality(Modality.APPLICATION_MODAL);
		sg.show();

	}

	// for screen transaction from login to admin panel
	public class LoginPg extends Application {

		@Override
		public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource(Messages.getString("MainCashierController.92"))); //$NON-NLS-1$

			Scene scene = new Scene(root, 1320, 700);
			primaryStage.setScene(scene);
			primaryStage.setTitle(Messages.getString("MainCashierController.93")); //$NON-NLS-1$
			// primaryStage.sizeToScene();
			primaryStage.setResizable(false);
			primaryStage.setMaximized(false);
			primaryStage.show();
		}
	}

	// search product item table by barcode or name
	@FXML
	void tfTypeSearchAction(KeyEvent event) throws ClassNotFoundException, SQLException, InterruptedException {


		Thread.sleep(1);

		tf_name_search.clear();

		/*
		 * if(!th.isInterrupted()) { th.start();
		 * System.out.println("------------------------------- xD"); }else {
		 * th.interrupt(); System.out.println("------------------------------- xP"); }
		 */

		String searchKey = tf_barcode_search.getText().toString();
		System.out.println(Messages.getString("MainCashierController.94") + searchKey); //$NON-NLS-1$
		String query = Messages.getString("MainCashierController.95") //$NON-NLS-1$
				+ searchKey + Messages.getString("MainCashierController.96"); //$NON-NLS-1$

		// new DBInitialize().DBInitialize();

		System.out.println(Messages.getString("MainCashierController.97")); //$NON-NLS-1$
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

			System.out.println(Messages.getString("MainCashierController.98")); //$NON-NLS-1$
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

	}

	@FXML
	void tfNameSearchAction(KeyEvent event) throws InterruptedException {

		Thread.sleep(1);
		tf_barcode_search.clear();

		/*
		 * if(!th.isInterrupted()) { th.start();
		 * System.out.println("------------------------------- xD"); }else {
		 * th.interrupt(); System.out.println("------------------------------- xP"); }
		 */

		String searchKey = tf_name_search.getText().toString();
		System.out.println(Messages.getString("MainCashierController.99") + searchKey); //$NON-NLS-1$
		String query = Messages.getString("MainCashierController.100") //$NON-NLS-1$
				+ searchKey + Messages.getString("MainCashierController.101"); //$NON-NLS-1$

		// new DBInitialize().DBInitialize();

		System.out.println(Messages.getString("MainCashierController.102")); //$NON-NLS-1$
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

			System.out.println(Messages.getString("MainCashierController.103")); //$NON-NLS-1$
			// data.getItems().addAll(row);
		} catch (SQLException ex) {

		}

		

	}

	@FXML
	void onEnterButtonClick(ActionEvent event) {

		if (tf_pay_amount.getText().equals(Messages.getString("MainCashierController.104"))) { //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.105")); //$NON-NLS-1$
			al.showAndWait();
		} else if (tf_pay_amount.getText().matches(Messages.getString("MainCashierController.106"))) { //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.107")); //$NON-NLS-1$
			al.showAndWait();
		} else if (Double.parseDouble(tf_pay_amount.getText()) < Common.totalAmount) {
			// do nothing
			Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.108")); //$NON-NLS-1$
			al.showAndWait();
			tf_pay_amount.clear();
		} else {
			try {
				Common.payamount = Double.parseDouble(tf_pay_amount.getText());
				Common.change = Common.payamount - Common.totalAmount;
				tf_change.setText(Messages.getString("MainCashierController.109") + Common.change); //$NON-NLS-1$
				System.out.println(Messages.getString("MainCashierController.110") + Common.totalAmount); //$NON-NLS-1$
				System.out.println(Messages.getString("MainCashierController.111") + Common.payamount); //$NON-NLS-1$
				System.out.println(Messages.getString("MainCashierController.112") + Common.change); //$NON-NLS-1$
			} catch (Exception ex) {
				System.out.println(Messages.getString("MainCashierController.113") + ex.getMessage()); //$NON-NLS-1$
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
			return text.matches(Messages.getString("MainCashierController.114")); //$NON-NLS-1$
		}
	}

	@FXML
	void onbtCreateCardClick(ActionEvent event) {

		Stage sg = new Stage();
		FXMLLoader root = new FXMLLoader(getClass().getResource(Messages.getString("MainCashierController.115"))); //$NON-NLS-1$
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
		sg.setTitle(Messages.getString("MainCashierController.116")); //$NON-NLS-1$
		sg.getIcons().add(new Image(Messages.getString("MainCashierController.117"))); //$NON-NLS-1$
		sg.initModality(Modality.APPLICATION_MODAL);
		sg.show();

	}

	@FXML
	void onBtPayClick(ActionEvent event) {
		// get all data from table and add to oberable list
		if (tf_total.getText().equals(Messages.getString("MainCashierController.118"))) { //$NON-NLS-1$
			System.out.println(Messages.getString("MainCashierController.119")); //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.120")); //$NON-NLS-1$
			al.showAndWait();
		} else {

			ObservableList<Sale> saledata = FXCollections.observableArrayList();
			Common.saleitemsdatafromsaletable = saledata;// for generating report in pay action

			saledata = tb_sale.getItems();
			StringBuilder productid = new StringBuilder();
			StringBuilder qty = new StringBuilder();
			for (int i = 0; i < saledata.size(); i++) {
				productid.append(saledata.get(i).getBarcode());
				productid.append(Messages.getString("MainCashierController.121")); //$NON-NLS-1$

				qty.append(saledata.get(i).getQuantity());
				qty.append(Messages.getString("MainCashierController.122")); //$NON-NLS-1$

			}

			Common.totalAmount = Double.parseDouble(tf_total.getText());
			// Common.saleitemsdatafromsaletable = saledata;
			Common.productids = productid.toString();
			Common.productqtys = qty.toString();
			Common.saleitemsdatafromsaletable = saledata;// for generating report in pay action
			Common.paidtype = Messages.getString("MainCashierController.123"); //$NON-NLS-1$

			System.out.println(Messages.getString("MainCashierController.124") + qty.toString()); //$NON-NLS-1$

			// open new stage
			Stage sg = new Stage();
			FXMLLoader root = new FXMLLoader(getClass().getResource(Messages.getString("MainCashierController.125"))); //$NON-NLS-1$
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
			sg.setTitle(Messages.getString("MainCashierController.126")); //$NON-NLS-1$
			sg.getIcons().add(new Image(Messages.getString("MainCashierController.127"))); //$NON-NLS-1$
			sg.initModality(Modality.APPLICATION_MODAL);
			sg.show();
		}

	}

	@FXML
	void onbtPrintClick(ActionEvent event)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		if (tf_change.getText().equals(Messages.getString("MainCashierController.128")) || tf_pay_amount.getText().equals(Messages.getString("MainCashierController.129"))) { //$NON-NLS-1$ //$NON-NLS-2$
			// do nothing
			tf_change.clear();
			tf_pay_amount.clear();
			/*
			 * Common.change = 0; Common.payamount = 0; Common.totalAmount =
			 * Double.parseDouble(tf_total.getText().toString());
			 */
			Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.130")); //$NON-NLS-1$
			al.showAndWait();
		} else {

			ObservableList<Sale> saledata = FXCollections.observableArrayList();
			saledata = tb_sale.getItems();
			StringBuilder productid = new StringBuilder();
			StringBuilder qty = new StringBuilder();
			for (int i = 0; i < saledata.size(); i++) {
				productid.append(saledata.get(i).getBarcode());
				productid.append(Messages.getString("MainCashierController.131")); //$NON-NLS-1$

				qty.append(saledata.get(i).getQuantity());
				qty.append(Messages.getString("MainCashierController.132")); //$NON-NLS-1$

			}
			// add purchase items id to purchase table
			System.out.println(Messages.getString("MainCashierController.133") + productid); //$NON-NLS-1$
			System.out.println(Messages.getString("MainCashierController.134") + qty); //$NON-NLS-1$

			// get purchase id from previous row
			// get previous id and create now id
			try {
				new DBInitialize().DBInitialize();
				String previousgetpurchaseid = Messages.getString("MainCashierController.135"); //$NON-NLS-1$
				new DBInitialize();
				ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid);
				String previousid = Messages.getString("MainCashierController.136"); //$NON-NLS-1$
				while (rs.next()) {
					previousid = rs.getString(Messages.getString("MainCashierController.137")); //$NON-NLS-1$
				}
				int nowid = Integer.parseInt(previousid) + 1;
				System.out.println(Messages.getString("MainCashierController.138") + nowid); //$NON-NLS-1$
				Common.purchaseid = nowid;

				// create today date
				String pattern = Messages.getString("MainCashierController.139"); //$NON-NLS-1$
				String purchasedate = new SimpleDateFormat(pattern).format(new Date());
				System.out.println(Messages.getString("MainCashierController.140") + purchasedate); //$NON-NLS-1$

				// create today current time(purhcase time)
				String myDateString = Messages.getString("MainCashierController.141"); //$NON-NLS-1$
				LocalTime localTime = LocalTime.parse(myDateString, DateTimeFormatter.ofPattern(Messages.getString("MainCashierController.142"))); //$NON-NLS-1$
				int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
				int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
				int second = localTime.get(ChronoField.SECOND_OF_MINUTE);
				String currenttime = hour + Messages.getString("MainCashierController.143") + minute + Messages.getString("MainCashierController.144") + second + Messages.getString("MainCashierController.145"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				System.out.println(Messages.getString("MainCashierController.146") + currenttime); //$NON-NLS-1$
				// Common.currenttime = currenttime;

				Common.totalAmount = Double.parseDouble(tf_total.getText());

				// db
				new DBInitialize().DBInitialize();
				new DBInitialize();
				String querycreatepurchase = Messages.getString("MainCashierController.147") //$NON-NLS-1$
						+ Messages.getString("MainCashierController.148") + Common.purchaseid + Messages.getString("MainCashierController.149") + purchasedate + Messages.getString("MainCashierController.150") + currenttime + Messages.getString("MainCashierController.151") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						+ Common.cashierrec.getId() + Messages.getString("MainCashierController.152") + productid + Messages.getString("MainCashierController.153") + qty + Messages.getString("MainCashierController.154") + Common.totalAmount //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						+ Messages.getString("MainCashierController.155"); //$NON-NLS-1$
				DBInitialize.statement.executeUpdate(querycreatepurchase);

				// update count and stock amount
				// get all the purchase id and count +1 in db
				String[] purchasedproductitemsid = productid.toString().split(Messages.getString("MainCashierController.156")); //$NON-NLS-1$
				String[] purchasedproductitemsqty = qty.toString().split(Messages.getString("MainCashierController.157")); //$NON-NLS-1$

				for (int i = 0; i < purchasedproductitemsid.length; i++) {
					int oldcount = 0;
					int newcount = 0;
					// get old count
					String getOldCountQuery = Messages.getString("MainCashierController.158") //$NON-NLS-1$
							+ purchasedproductitemsid[i] + Messages.getString("MainCashierController.159"); //$NON-NLS-1$
					System.out.println(Messages.getString("MainCashierController.160") + purchasedproductitemsid[i]); //$NON-NLS-1$
					new DBInitialize().DBInitialize();
					new DBInitialize();
					ResultSet rsoldc = DBInitialize.statement.executeQuery(getOldCountQuery);
					while (rsoldc.next()) {
						oldcount = rsoldc.getInt(1);
					}
					System.out.println(Messages.getString("MainCashierController.161") + oldcount); //$NON-NLS-1$

					newcount = oldcount + Integer.parseInt(purchasedproductitemsqty[i]);
					System.out.println(Messages.getString("MainCashierController.162") + newcount + Messages.getString("MainCashierController.163") + purchasedproductitemsqty[i]); //$NON-NLS-1$ //$NON-NLS-2$
					String updatecountQuery = Messages.getString("MainCashierController.164") + newcount //$NON-NLS-1$
							+ Messages.getString("MainCashierController.165") + purchasedproductitemsid[i] + Messages.getString("MainCashierController.166"); //$NON-NLS-1$ //$NON-NLS-2$
					new DBInitialize().DBInitialize();
					new DBInitialize();
					DBInitialize.statement.executeUpdate(updatecountQuery);

					// get stock amount
					String getstockquery = Messages.getString("MainCashierController.167") //$NON-NLS-1$
							+ purchasedproductitemsid[i] + Messages.getString("MainCashierController.168"); //$NON-NLS-1$
					String oldstock = Messages.getString("MainCashierController.169"); //$NON-NLS-1$
					new DBInitialize();
					ResultSet rsst = DBInitialize.statement.executeQuery(getstockquery);
					while (rsst.next()) {
						oldstock = rsst.getString(1);
					}
					String newstock = Messages.getString("MainCashierController.170") + (Integer.parseInt(oldstock) - Integer.parseInt(purchasedproductitemsqty[i])); //$NON-NLS-1$
					// update stock
					String updatestockquery = Messages.getString("MainCashierController.171") + newstock //$NON-NLS-1$
							+ Messages.getString("MainCashierController.172") + purchasedproductitemsid[i] + Messages.getString("MainCashierController.173"); //$NON-NLS-1$ //$NON-NLS-2$
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
				Common.paidtype = Messages.getString("MainCashierController.174"); //$NON-NLS-1$
				Common.cardinfo = Messages.getString("MainCashierController.175"); //$NON-NLS-1$

				tf_total.clear();
				tf_pay_amount.clear();
				tf_change.clear();

				// update in stock table
				// get data from db and set it to table
				new DBInitialize().DBInitialize();
				data.clear();

				String tablequery = Messages.getString("MainCashierController.176"); //$NON-NLS-1$
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
				String getpreviouspurchaseid = Messages.getString("MainCashierController.177"); //$NON-NLS-1$
				new DBInitialize();
				ResultSet rsslipno = DBInitialize.statement.executeQuery(previousgetpurchaseid);
				String olddid = Messages.getString("MainCashierController.178"); //$NON-NLS-1$
				while (rsslipno.next()) {
					olddid = rsslipno.getString(Messages.getString("MainCashierController.179")); //$NON-NLS-1$
				}
				int nowwid = Integer.parseInt(olddid) + 1;

				lb_slip_no.setText(Messages.getString("MainCashierController.180") + nowwid); //$NON-NLS-1$
				Common.slipno = Messages.getString("MainCashierController.181") + nowwid; //$NON-NLS-1$

				Common.buygetdata.clear();
				Common.buygetitem = Messages.getString("MainCashierController.182"); //$NON-NLS-1$
				Common.buygetpromo = Messages.getString("MainCashierController.183"); //$NON-NLS-1$
				Common.totalAmount = 0;
				Common.change = 0;
				Common.payamount = 0;

			} // end of try
			catch (Exception ex) {
				Alert al = new Alert(AlertType.ERROR, Messages.getString("MainCashierController.184") + ex.getMessage()); //$NON-NLS-1$
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
		String previousgetpurchaseid = Messages.getString("MainCashierController.185"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsslip = DBInitialize.statement.executeQuery(previousgetpurchaseid);
		String previousid = Messages.getString("MainCashierController.186"); //$NON-NLS-1$
		while (rsslip.next()) {
			previousid = rsslip.getString(Messages.getString("MainCashierController.187")); //$NON-NLS-1$
		}
		int nowid = Integer.parseInt(previousid) + 1;

		lb_slip_no.setText(Messages.getString("MainCashierController.188") + nowid); //$NON-NLS-1$
		Common.slipno = Messages.getString("MainCashierController.189") + nowid; //$NON-NLS-1$

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

		String tablequery = Messages.getString("MainCashierController.190"); //$NON-NLS-1$
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
