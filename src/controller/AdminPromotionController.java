package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		assert tb_promo != null : Messages.getString("AdminPromotionController.0"); //$NON-NLS-1$
		assert tf_id != null : Messages.getString("AdminPromotionController.1"); //$NON-NLS-1$
		assert tf_name != null : Messages.getString("AdminPromotionController.2"); //$NON-NLS-1$
		assert tf_product_id != null : Messages.getString("AdminPromotionController.3"); //$NON-NLS-1$
		assert tf_percentage != null : Messages.getString("AdminPromotionController.4"); //$NON-NLS-1$
		assert tf_buy != null : Messages.getString("AdminPromotionController.5"); //$NON-NLS-1$
		assert tf_get != null : Messages.getString("AdminPromotionController.6"); //$NON-NLS-1$
		assert bt_add != null : Messages.getString("AdminPromotionController.7"); //$NON-NLS-1$
		assert tf_product_name != null : Messages.getString("AdminPromotionController.8"); //$NON-NLS-1$
		assert bt_new != null : Messages.getString("AdminPromotionController.9"); //$NON-NLS-1$

		col_item_id = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.10")); //$NON-NLS-1$
		col_item_name = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.11")); //$NON-NLS-1$
		col_item_product_id = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.12")); //$NON-NLS-1$
		col_item_product_name = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.13")); //$NON-NLS-1$
		col_item_percentage = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.14")); //$NON-NLS-1$
		col_item_more = new TableColumn<Promotion, String>(Messages.getString("AdminPromotionController.15")); //$NON-NLS-1$

		col_item_id.setMinWidth(100.0);
		col_item_name.setMinWidth(200.0);
		col_item_product_id.setMinWidth(200.0);
		col_item_product_name.setMinWidth(200.0);
		col_item_percentage.setMinWidth(120.0);
		col_item_more.setMinWidth(150.0);

		col_item_id.setStyle(Messages.getString("AdminPromotionController.16")); //$NON-NLS-1$
		col_item_name.setStyle(Messages.getString("AdminPromotionController.17")); //$NON-NLS-1$
		col_item_product_id.setStyle(Messages.getString("AdminPromotionController.18")); //$NON-NLS-1$
		col_item_product_name.setStyle(Messages.getString("AdminPromotionController.19")); //$NON-NLS-1$
		col_item_percentage.setStyle(Messages.getString("AdminPromotionController.20")); //$NON-NLS-1$
		col_item_more.setStyle(Messages.getString("AdminPromotionController.21")); //$NON-NLS-1$

		col_item_id.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.22"))); //$NON-NLS-1$
		col_item_name.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.23"))); //$NON-NLS-1$
		col_item_product_id.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.24"))); //$NON-NLS-1$
		col_item_product_name.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.25"))); //$NON-NLS-1$
		col_item_percentage.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.26"))); //$NON-NLS-1$
		col_item_more.setCellValueFactory(new PropertyValueFactory<Promotion, String>(Messages.getString("AdminPromotionController.27"))); //$NON-NLS-1$

		tb_promo.getColumns().addAll(col_item_id, col_item_name, col_item_product_id, col_item_product_name,
				col_item_percentage, col_item_more);

		// get data from db
		String query = Messages.getString("AdminPromotionController.28"); //$NON-NLS-1$
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
					System.out.println(Messages.getString("AdminPromotionController.29") + pro.getName()); //$NON-NLS-1$

					// string processing / cut buy and get
					String more = pro.getMore();
					String[] buy = more.split(Messages.getString("AdminPromotionController.30")); //$NON-NLS-1$
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

			MenuItem removeItem = new MenuItem(Messages.getString("AdminPromotionController.31")); //$NON-NLS-1$
			removeItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Promotion pro = tb_promo.getSelectionModel().getSelectedItem();

					Alert alert = new Alert(AlertType.CONFIRMATION, Messages.getString("AdminPromotionController.32") + pro.getName() + Messages.getString("AdminPromotionController.33"), //$NON-NLS-1$ //$NON-NLS-2$
							ButtonType.YES, ButtonType.NO);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.YES) {
						// do stuff
						String removequery = Messages.getString("AdminPromotionController.34") + pro.getId() + Messages.getString("AdminPromotionController.35"); //$NON-NLS-1$ //$NON-NLS-2$
						try {
							new DBInitialize().DBInitialize();
							new DBInitialize();
							DBInitialize.statement.executeUpdate(removequery);

							// update table
							// update table data
							new DBInitialize().DBInitialize();
							String queryupdatetable = Messages.getString("AdminPromotionController.36"); //$NON-NLS-1$

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
							Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminPromotionController.37")); //$NON-NLS-1$
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

		if(tf_product_id.getText().equals(Messages.getString("AdminPromotionController.38"))) { //$NON-NLS-1$
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminPromotionController.39")); //$NON-NLS-1$
			al.showAndWait();
		}else {
			
		
		String productbarcode = tf_product_id.getText().toString();
		String searchPQuery = Messages.getString("AdminPromotionController.40") //$NON-NLS-1$
				+ productbarcode + Messages.getString("AdminPromotionController.41"); //$NON-NLS-1$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsp = DBInitialize.statement.executeQuery(searchPQuery);
		String productname = Messages.getString("AdminPromotionController.42"); //$NON-NLS-1$
		if (rsp.next()) {
			productname = rsp.getString(1);
		}
		else {
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminPromotionController.43")); //$NON-NLS-1$
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

		String query = Messages.getString("AdminPromotionController.44"); //$NON-NLS-1$

		String oldid = Messages.getString("AdminPromotionController.45"); //$NON-NLS-1$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);
		while (rs.next()) {
			oldid = Messages.getString("AdminPromotionController.46") + rs.getInt(1); //$NON-NLS-1$
		}

		// count +1 new Id
		String newId = Messages.getString("AdminPromotionController.47") + (Integer.parseInt(oldid) + 1); //$NON-NLS-1$
		tf_id.setText(newId);

	}

	@FXML
	void onAddAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		if(tf_id.getText().equals(Messages.getString("AdminPromotionController.48")) ||tf_name.getText().equals(Messages.getString("AdminPromotionController.49")) || tf_product_id.getText().equals(Messages.getString("AdminPromotionController.50")) || //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				tf_percentage.getText().matches(Messages.getString("AdminPromotionController.51"))    //$NON-NLS-1$
				) {
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminPromotionController.52")); //$NON-NLS-1$
			al.showAndWait();
		}
		else {

		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String productid = tf_product_id.getText().toString();
		String percentage = tf_percentage.getText().toString();
		String buy = tf_buy.getText().toString();
		String get = tf_get.getText().toString();

		if (percentage.equals(Messages.getString("AdminPromotionController.53"))) { //$NON-NLS-1$
			percentage = Messages.getString("AdminPromotionController.54"); //$NON-NLS-1$
		}

		if (buy.equals(Messages.getString("AdminPromotionController.55"))) { //$NON-NLS-1$
			buy = Messages.getString("AdminPromotionController.56"); //$NON-NLS-1$
		}

		if (get.equals(Messages.getString("AdminPromotionController.57"))) { //$NON-NLS-1$
			get = Messages.getString("AdminPromotionController.58"); //$NON-NLS-1$
		}
		String desc = Messages.getString("AdminPromotionController.59") + buy + Messages.getString("AdminPromotionController.60") + get; //$NON-NLS-1$ //$NON-NLS-2$

		try {
		String addquery = Messages.getString("AdminPromotionController.61") //$NON-NLS-1$
				+ Messages.getString("AdminPromotionController.62") + id + Messages.getString("AdminPromotionController.63") + name + Messages.getString("AdminPromotionController.64") + productid + Messages.getString("AdminPromotionController.65") + percentage + Messages.getString("AdminPromotionController.66") + desc + Messages.getString("AdminPromotionController.67"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		DBInitialize.statement.executeUpdate(addquery);

		// update table
		String query = Messages.getString("AdminPromotionController.68"); //$NON-NLS-1$
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
		Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminPromotionController.69")); //$NON-NLS-1$
		al.showAndWait();

		}//end of try
    	catch(Exception ex) {
    		Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminPromotionController.70")+ex.getMessage()); //$NON-NLS-1$
    		al.showAndWait();
    	}
		
		}//end of else
	}

	@FXML
	void onUpdateAction(ActionEvent event)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		if(tf_id.getText().equals(Messages.getString("AdminPromotionController.71")) ||tf_name.getText().equals(Messages.getString("AdminPromotionController.72")) || tf_product_id.getText().equals(Messages.getString("AdminPromotionController.73")) || //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				tf_percentage.getText().matches(Messages.getString("AdminPromotionController.74"))    //$NON-NLS-1$
				) {
			Alert al = new Alert(AlertType.ERROR, Messages.getString("AdminPromotionController.75")); //$NON-NLS-1$
			al.showAndWait();
		}
		else {
			
	
		String id = tf_id.getText().toString();
		String name = tf_name.getText().toString();
		String productid = tf_product_id.getText().toString();
		String percentage = tf_percentage.getText().toString();
		String buy = tf_buy.getText().toString();
		String get = tf_get.getText().toString();

		String desc = Messages.getString("AdminPromotionController.76") + buy + Messages.getString("AdminPromotionController.77") + get; //$NON-NLS-1$ //$NON-NLS-2$

		String updatequery = Messages.getString("AdminPromotionController.78") + name + Messages.getString("AdminPromotionController.79") + productid //$NON-NLS-1$ //$NON-NLS-2$
				+ Messages.getString("AdminPromotionController.80") + percentage + Messages.getString("AdminPromotionController.81") + desc + Messages.getString("AdminPromotionController.82") + id + Messages.getString("AdminPromotionController.83"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		new DBInitialize().DBInitialize();
		new DBInitialize();
		DBInitialize.statement.executeUpdate(updatequery);

		// update table
		String query = Messages.getString("AdminPromotionController.84"); //$NON-NLS-1$
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
		Alert al = new Alert(AlertType.INFORMATION, Messages.getString("AdminPromotionController.85")); //$NON-NLS-1$
		al.showAndWait();
	}
		
	}//end of else

}
