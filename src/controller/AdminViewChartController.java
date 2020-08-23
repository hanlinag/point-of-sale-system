package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


import database.DBInitialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class AdminViewChartController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane ch_dailySale;

	@FXML
	private AnchorPane ch_month;

	@FXML
	private AnchorPane ch_category;

	@FXML
	private AnchorPane ch_customer_age;

	@FXML
	private AnchorPane ch_cash_vs_card;

	@FXML
	void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		assert ch_dailySale != null : "fx:id=\"ch_dailySale\" was not injected: check your FXML file 'Admin_view_chart.fxml'.";
		assert ch_month != null : "fx:id=\"ch_month\" was not injected: check your FXML file 'Admin_view_chart.fxml'.";
		assert ch_category != null : "fx:id=\"ch_category\" was not injected: check your FXML file 'Admin_view_chart.fxml'.";
		assert ch_customer_age != null : "fx:id=\"ch_customer_age\" was not injected: check your FXML file 'Admin_view_chart.fxml'.";
		assert ch_cash_vs_card != null : "fx:id=\"ch_cash_va_card\" was not injected: check your FXML file 'Admin_view_chart.fxml'.";

		showingDailyChart();
		showingMonthlyChart();
		showingCategoryChart();
		showingCashVsCardChart();
		showingCustomerAge();
	}

	private void showingCustomerAge()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// age interval count
		int first = 0;
		int second = 0;
		int third = 0;
		int fourth = 0;
		int fifth = 0;

		// get age data from database
		String getAgeQuery = "SELECT `age` FROM `Customer`";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsAge = DBInitialize.statement.executeQuery(getAgeQuery);
		while (rsAge.next()) {
			int age = Integer.parseInt(rsAge.getString(1));
			if (age >= 50) {
				// first
				first++;
			} else if (age >= 41) {
				// second
				second++;
			} else if (age >= 30) {
				// third
				third++;
			} else if (age >= 21) {
				// fourth
				fourth++;
			} else {
				// fifth
				fifth++;
			}

		}

		// Defining the axes
		CategoryAxis xAxis = new CategoryAxis();
		// xAxis.setCategories(FXCollections.<String>
		// observableArrayList(Arrays.asList("10 - 20", "21 - 29", "30 - 40", "41 - 49",
		// "50 and above")));
		xAxis.setLabel("Age Interval");

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number of people");

		// Creating the Bar chart
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setTitle("Customer Age");

		// Prepare XYChart.Series objects by setting data
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		XYChart.Series<String, Number> series3 = new XYChart.Series<>();
		XYChart.Series<String, Number> series4 = new XYChart.Series<>();
		XYChart.Series<String, Number> series5 = new XYChart.Series<>();

		series1.getData().add(new XYChart.Data<>("", fifth));
		series1.setName("13 - 20");
		series2.getData().add(new XYChart.Data<>("", fourth));
		series2.setName("21 - 29");
		series3.getData().add(new XYChart.Data<>("", third));
		series3.setName("30 - 40");
		series4.getData().add(new XYChart.Data<>("", second));
		series4.setName("41 - 49");
		series5.getData().add(new XYChart.Data<>("", first));
		series5.setName("50 and above");

		System.out.println("50 and above is:...... " + first);
		System.out.println("41-50 is:....... " + second);
		System.out.println("30-40 is:........ " + third);
		System.out.println("21-29:......... " + fourth);
		System.out.println("13-20 is:........ " + fifth);

		// Setting the data to bar chart
		barChart.getData().addAll(series1, series2, series3, series4, series5);

		// Setting the data to Line chart
		barChart.setAnimated(true);
		barChart.animatedProperty();

		barChart.setPrefHeight(440);
		barChart.setMinHeight(440);
		barChart.setMaxHeight(440);

		barChart.setPrefWidth(470);
		barChart.setMinWidth(470);
		barChart.setMaxWidth(470);

		barChart.setPrefSize(405, 290);
		barChart.setMinSize(405, 290);
		barChart.setMaxSize(405, 290);

		ch_customer_age.getChildren().add(barChart);
		System.out.println("Customer age chart is generated ...... ");
	}

	private void showingCashVsCardChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// current date
		String pattern = "dd/MM/yyyy";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// current month
		String[] tempAry = today.split("/");
		String currentMonth = ""+tempAry[1];
		System.out.println("current month is .......  " + currentMonth);

		// purchase data list
		 ObservableList<String> purchaseDateList = FXCollections.observableArrayList();

		// get data from database
		// search by (current) month
		String getIdPurchaseQuery = "SELECT DISTINCT `date` FROM `purchase` WHERE purchase.date LIKE '%" + currentMonth + "%'";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsPurchase = DBInitialize.statement.executeQuery(getIdPurchaseQuery);
		while (rsPurchase.next()) {

			String tem = rsPurchase.getString(1);
			String[] dateArray = tem.split("/");
			purchaseDateList.add(dateArray[0]);
		}
		

		//print distinct date
		for(int i = 0; i < purchaseDateList.size(); i++) {
			System.out.println("Date List :.......  "+purchaseDateList.get(i));
		}
		
		//purchase count list 
		ObservableList<Integer> purchaseCountTemp = FXCollections.observableArrayList();
		ObservableList<Integer> purchaseCount = FXCollections.observableArrayList();
		ObservableList<Integer> transactionCount = FXCollections.observableArrayList();
		
		//get count from db by date
		for(int i = 0 ; i < purchaseDateList.size(); i++) {
			
		//purchase count
		String countQuery = "SELECT COUNT(*) FROM `purchase` WHERE purchase.date LIKE '"+purchaseDateList.get(i)+"/"+currentMonth+"%'";
		new DBInitialize();
		ResultSet rsPCountTemp = DBInitialize.statement.executeQuery(countQuery);
		while(rsPCountTemp.next()) {
			purchaseCountTemp.add(rsPCountTemp.getInt(1));
		}

		String countTQuery = "SELECT COUNT(*) FROM `purchase`, transaction WHERE purchase.date LIKE '"+purchaseDateList.get(i)+"/"+currentMonth+"%' AND purchase.id = transaction.purchaseid";
		new DBInitialize();
		ResultSet rsTCount = DBInitialize.statement.executeQuery(countTQuery);
		while(rsTCount.next()) {
			transactionCount.add(rsTCount.getInt(1));
		}
		
		purchaseCount.add( purchaseCountTemp.get(i) - transactionCount.get(i));
		
		}//end of for
		
		System.out.println("size of date list is : --------------------- "+purchaseDateList.size());
		System.out.println("size of purchase count list is : --------------------- "+purchaseCount.size());
		System.out.println("size of transaction count  list is : --------------------- "+transactionCount.size());

		final NumberAxis xAxis = new NumberAxis(1, 31, 1);
		xAxis.setLabel("Days");

		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Number of Customer");
		
		final StackedAreaChart<Number, Number> sac = new StackedAreaChart<Number, Number>(xAxis, yAxis);

		sac.setTitle("Cash Usage VS Card Usage");
		
		XYChart.Series<Number, Number> seriesCard = new XYChart.Series<Number, Number>();
		seriesCard.setName("Card User");
		
		for( int k = 0 ; k < purchaseDateList.size(); k++) {
			seriesCard.getData().add(new XYChart.Data<>(Integer.parseInt(purchaseDateList.get(k)), transactionCount.get(k)));
		}
		
		
		/*seriesCard.getData().add(new XYChart.Data(27, 21));
		seriesCard.getData().add(new XYChart.Data(30, 21));
		*/
		
		XYChart.Series<Number, Number> seriesCash = new XYChart.Series<Number, Number>();
		seriesCash.setName("Cash User");
		for( int t = 0 ; t < purchaseDateList.size(); t++) {
			seriesCash.getData().add(new XYChart.Data<>(Integer.parseInt(purchaseDateList.get(t)), purchaseCount.get(t) ));
		}
		
		/*
		seriesCash.getData().add(new XYChart.Data(27, 26));
		seriesCash.getData().add(new XYChart.Data(31, 26));*/

		sac.getData().addAll(seriesCard, seriesCash);

		// Setting the data to Line chart
		sac.setAnimated(true);
		sac.animatedProperty();

		sac.setPrefHeight(555);
		sac.setMinHeight(555);
		sac.setMaxHeight(555);

		sac.setPrefWidth(400);
		sac.setMinWidth(400);
		sac.setMaxWidth(400);

		sac.setPrefSize(555, 280);
		sac.setMinSize(555, 280);
		sac.setMaxSize(555, 280);

		ch_cash_vs_card.getChildren().add(sac);
		System.out.println("cash vs card chart is generated ...........");
	}

	private void showingCategoryChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub

		// category array list
		ObservableList<String> categoryNameList = FXCollections.observableArrayList();
		ObservableList<String> categoryIDList = FXCollections.observableArrayList();

		// get category from database
		String getCategroyQuery = "SELECT `name`, `id` FROM `productcategory`";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsCategory = DBInitialize.statement.executeQuery(getCategroyQuery);
		while (rsCategory.next()) {
			categoryNameList.add(rsCategory.getString(1));
			categoryIDList.add(rsCategory.getString(2));
		}

		// category count list
		ObservableList<Integer> categoryCount = FXCollections.observableArrayList();

		// search count amount by category and plus
		for (int i = 0; i < categoryIDList.size(); i++) {
			String getCategoryAmountQueyry = "SELECT `count` FROM `productitems` WHERE productitems.categoryid = '"
					+ categoryIDList.get(i) + "'";
			new DBInitialize();
			int cCount = 0;
			ResultSet rsCategoryAmount = DBInitialize.statement.executeQuery(getCategoryAmountQueyry);
			while (rsCategoryAmount.next()) {
				cCount = cCount + rsCategoryAmount.getInt(1);
			}
			categoryCount.add(cCount);
		}

		// Preparing ObservbleList object
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		// add pie chart data
		for (int j = 0; j < categoryNameList.size(); j++) {
			pieChartData.add(new PieChart.Data(categoryNameList.get(j), categoryCount.get(j)));
		}

		/*
		 * new PieChart.Data("Iphone 5S", 13), new PieChart.Data("Samsung Grand", 25),
		 * new PieChart.Data("MOTO G", 10), new PieChart.Data("Nokia Lumia", 22), new
		 * PieChart.Data("Iphone 6S", 18), new PieChart.Data("Samsung S9", 26), new
		 * PieChart.Data("MOTO G", 17), new PieChart.Data("Nokia 8", 23));
		 */

		// Creating a Pie chart
		PieChart pieChart = new PieChart(pieChartData);

		// Setting the title of the Pie chart
		pieChart.setTitle("Category Sales");

		pieChart.setPrefHeight(400);
		pieChart.setMinHeight(400);
		pieChart.setMaxHeight(400);

		pieChart.setPrefWidth(300);
		pieChart.setMinWidth(300);
		pieChart.setMaxWidth(300);

		pieChart.setPrefSize(420, 300);
		pieChart.setMinSize(420, 300);
		pieChart.setMaxSize(420, 300);

		// setting the direction to arrange the data
		pieChart.setClockwise(true);

		// Setting the length of the label line
		pieChart.setLabelLineLength(50);

		// Setting the labels of the pie chart visible
		pieChart.setLabelsVisible(true);

		// Setting the start angle of the pie chart
		pieChart.setStartAngle(180);
		pieChart.setAnimated(true);
		pieChart.animatedProperty();

		ch_category.getChildren().add(pieChart);

		System.out.println("Category chart is generated... ");

	}

	private void showingMonthlyChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// current date
		String pattern = "dd/MM/yyyy";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// current year
		String[] tempAry = today.split("/");
		String currentYear = tempAry[2];
		System.out.println("current year is .......  " + currentYear);

		// month list
		ObservableList<String> monthList = FXCollections.observableArrayList();
		ObservableList<String> monthTempList = FXCollections.observableArrayList();

		// get data from database
		String getMonthQuery = "SELECT DISTINCT `date` FROM `purchase` WHERE purchase.date LIKE '%/" + currentYear
				+ "'";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetMonth = DBInitialize.statement.executeQuery(getMonthQuery);
		while (rsGetMonth.next()) {
			String dbDate = rsGetMonth.getString(1);
			String[] tempA = dbDate.split("/");
			String dbMonth = tempA[1];
			monthTempList.add(dbMonth);

		} // end of while

		System.out.println("month temp list 1 ........" + monthTempList.get(0));

		// add data from monthtemp to array
		String[] monthAry = new String[monthTempList.size()];
		for (int m = 0; m < monthTempList.size(); m++) {
			monthAry[m] = monthTempList.get(m);
		}

		// System.out.println("month temp before......."+monthAry);

		// delete duplicate data
		Set<String> set = new HashSet<String>();
		// String[] array = {1,1,2,2,2,3,3,4,5,6,8};

		for (String num : monthAry) {
			set.add(num);
		}

		System.out.println("Set .............................." + set);

		// convert set to array
		String[] setary = set.toArray(new String[set.size()]);

		System.out.print("\n set ary is  ...................");
		for (int b = 0; b < setary.length; b++) {
			System.out.print(", " + setary[b]);
		}

		// add data from setary to real monthlist
		for (int a = 0; a < setary.length; a++) {
			monthList.add(setary[a]);
		}

		/*
		 * String current = monthAry[0]; boolean found = false;
		 * 
		 * for (int n = 0; n < monthAry.length ; n++) { if (current == monthAry[n] &&
		 * !found) { found = true; } else if (current != monthAry[n]) {
		 * System.out.print(" " + current); current = monthAry[n]; found = false; } }
		 */
		// System.out.print("month temp after " + monthAry);

		// month amount list
		ObservableList<String> monthAmountList = FXCollections.observableArrayList();

		// search amount by month and plus
		for (int j = 0; j < monthList.size(); j++) {
			double amount = 0;
			String monthAmountQuery = "SELECT `totalamount` FROM `purchase` WHERE purchase.date LIKE '%"
					+ monthList.get(j) + "%'";
			new DBInitialize();
			ResultSet rsMonthAmount = DBInitialize.statement.executeQuery(monthAmountQuery);
			while (rsMonthAmount.next()) {

				amount = (double) amount + Double.parseDouble(rsMonthAmount.getString(1));

			} // end of while
			monthAmountList.add("" + amount);

		} // end of for

		// Defining the x axis
		NumberAxis xAxis = new NumberAxis(1, 12, 1);
		xAxis.setLabel("Months");

		// Defining the y axis
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Sale Amount");

		// Creating the line chart
		LineChart linechart = new LineChart(xAxis, yAxis);
		linechart.setTitle("Monthly Sale");

		// Prepare XYChart.Series objects by setting data
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Monthly sale progress for this year");

		for (int k = 0; k < monthList.size(); k++) {
			series1.getData().add(
					new XYChart.Data(Integer.parseInt(monthList.get(k)), Double.parseDouble(monthAmountList.get(k))));

		}

		// System.out.println("month "+monthList.get(1)+" amount "+
		// monthAmountList.get(1));

		/*
		 * series1.getData().add(new XYChart.Data(1, 2000000));
		 * series1.getData().add(new XYChart.Data(4, 500000)); series1.getData().add(new
		 * XYChart.Data(6, 2000000)); series1.getData().add(new XYChart.Data(9,
		 * 1000000)); series1.getData().add(new XYChart.Data(11, 900000));
		 * series1.getData().add(new XYChart.Data(12, 4000000));
		 */

		// Setting the data to Line chart
		linechart.getData().add(series1);
		linechart.setAnimated(true);
		linechart.animatedProperty();

		linechart.setPrefHeight(555);
		linechart.setMinHeight(555);
		linechart.setMaxHeight(555);

		linechart.setPrefWidth(400);
		linechart.setMinWidth(400);
		linechart.setMaxWidth(400);

		linechart.setPrefSize(555, 300);
		linechart.setMinSize(555, 300);
		linechart.setMaxSize(555, 300);

		ch_month.getChildren().add(linechart);

		System.out.println("Monthly Sale chart is generated...");

	}

	private void showingDailyChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// get data from db and generated to chart data
		// current date
		String pattern = "dd/MM/yyyy";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// current month
		String[] tempAry = today.split("/");
		String currentmonth = tempAry[1] + "/" + tempAry[2];
		System.out.println("current month is .......  " + currentmonth);

		// get data of current month
		// get date of this month in purchase
		String getDateQuery = "SELECT DISTINCT `date` FROM `purchase` WHERE purchase.date LIKE '%" + currentmonth + "'";

		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetData = DBInitialize.statement.executeQuery(getDateQuery);
		ObservableList<String> dateAry = FXCollections.observableArrayList();
		while (rsGetData.next()) {

			dateAry.add(rsGetData.getString(1));

		}

		// get finaldate array
		ObservableList<Integer> finalDateAry = FXCollections.observableArrayList();

		for (int i = 0; i < dateAry.size(); i++) {
			finalDateAry.addAll(Integer.parseInt(dateAry.get(i).substring(0, 2)));
		}

		// plus all the amount within that day
		ObservableList<Integer> finalAmountAry = FXCollections.observableArrayList();
		new DBInitialize().DBInitialize();
		for (int j = 0; j < dateAry.size(); j++) {
			double totalAmount = 0;
			String getAmountQuery = "SELECT `totalamount` FROM `purchase` WHERE purchase.date = '" + dateAry.get(j)
					+ "'";
			System.out.println("total amount1 is  ......  " + totalAmount);

			new DBInitialize();
			ResultSet rsGetAmountData = DBInitialize.statement.executeQuery(getAmountQuery);

			while (rsGetAmountData.next()) {
				totalAmount = totalAmount + Double.parseDouble(rsGetAmountData.getString(1));

				System.out.println("total amount2 is  ......  " + totalAmount);
			} // end of while
			finalAmountAry.add((int) totalAmount);
			System.out.println("total amount3 is  ......  " + totalAmount);
		} // end of for
			// System.out.println("total amount4 is ...... "+totalAmount);

		System.out.println("final date ......  " + finalDateAry.get(0));
		System.out.println("final amount of date 1 is  ......  " + finalAmountAry.get(0));

		// Defining the x axis
		NumberAxis xAxis = new NumberAxis(1, 31, 2);
		xAxis.setLabel("Days");

		// Defining the y axis
		NumberAxis yAxis = new NumberAxis(50000, 1000000, 100000);
		yAxis.setLabel("Sale Amount");

		// Creating the line chart
		LineChart linechart = new LineChart(xAxis, yAxis);
		linechart.setTitle("Daily Sale");

		// Prepare XYChart.Series objects by setting data
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Daily sale progress within this month");

		for (int k = 0; k < dateAry.size(); k++) {
			series1.getData().add(new XYChart.Data(finalDateAry.get(k), finalAmountAry.get(k)));
		}
		/*
		 * series1.getData().add(new XYChart.Data(1970, 15)); series1.getData().add(new
		 * XYChart.Data(1980, 30)); series1.getData().add(new XYChart.Data(1990, 60));
		 * series1.getData().add(new XYChart.Data(2000, 120)); series1.getData().add(new
		 * XYChart.Data(2013, 240)); series1.getData().add(new XYChart.Data(2014, 300));
		 */

		// Setting the data to Line chart
		linechart.getData().add(series1);
		linechart.setAnimated(true);
		linechart.animatedProperty();

		linechart.setPrefHeight(515);
		linechart.setMinHeight(515);
		linechart.setMaxHeight(515);

		linechart.setPrefWidth(400);
		linechart.setMinWidth(400);
		linechart.setMaxWidth(400);

		linechart.setPrefSize(510, 300);
		linechart.setMinSize(510, 300);
		linechart.setMaxSize(510, 300);

		ch_dailySale.getChildren().add(linechart);

		System.out.println("Daily Sale chart is generated...");

	}
}
