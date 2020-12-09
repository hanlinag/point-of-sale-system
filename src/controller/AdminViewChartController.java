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
		assert ch_dailySale != null : Messages.getString("AdminViewChartController.0"); //$NON-NLS-1$
		assert ch_month != null : Messages.getString("AdminViewChartController.1"); //$NON-NLS-1$
		assert ch_category != null : Messages.getString("AdminViewChartController.2"); //$NON-NLS-1$
		assert ch_customer_age != null : Messages.getString("AdminViewChartController.3"); //$NON-NLS-1$
		assert ch_cash_vs_card != null : Messages.getString("AdminViewChartController.4"); //$NON-NLS-1$

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
		String getAgeQuery = Messages.getString("AdminViewChartController.5"); //$NON-NLS-1$
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
		xAxis.setLabel(Messages.getString("AdminViewChartController.6")); //$NON-NLS-1$

		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(Messages.getString("AdminViewChartController.7")); //$NON-NLS-1$

		// Creating the Bar chart
		BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		barChart.setTitle(Messages.getString("AdminViewChartController.8")); //$NON-NLS-1$

		// Prepare XYChart.Series objects by setting data
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		XYChart.Series<String, Number> series3 = new XYChart.Series<>();
		XYChart.Series<String, Number> series4 = new XYChart.Series<>();
		XYChart.Series<String, Number> series5 = new XYChart.Series<>();

		series1.getData().add(new XYChart.Data<>(Messages.getString("AdminViewChartController.9"), fifth)); //$NON-NLS-1$
		series1.setName(Messages.getString("AdminViewChartController.10")); //$NON-NLS-1$
		series2.getData().add(new XYChart.Data<>(Messages.getString("AdminViewChartController.11"), fourth)); //$NON-NLS-1$
		series2.setName(Messages.getString("AdminViewChartController.12")); //$NON-NLS-1$
		series3.getData().add(new XYChart.Data<>(Messages.getString("AdminViewChartController.13"), third)); //$NON-NLS-1$
		series3.setName(Messages.getString("AdminViewChartController.14")); //$NON-NLS-1$
		series4.getData().add(new XYChart.Data<>(Messages.getString("AdminViewChartController.15"), second)); //$NON-NLS-1$
		series4.setName(Messages.getString("AdminViewChartController.16")); //$NON-NLS-1$
		series5.getData().add(new XYChart.Data<>(Messages.getString("AdminViewChartController.17"), first)); //$NON-NLS-1$
		series5.setName(Messages.getString("AdminViewChartController.18")); //$NON-NLS-1$

		System.out.println(Messages.getString("AdminViewChartController.19") + first); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.20") + second); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.21") + third); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.22") + fourth); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.23") + fifth); //$NON-NLS-1$

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
		System.out.println(Messages.getString("AdminViewChartController.24")); //$NON-NLS-1$
	}

	private void showingCashVsCardChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// current date
		String pattern = Messages.getString("AdminViewChartController.25"); //$NON-NLS-1$
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminViewChartController.26") + today); //$NON-NLS-1$

		// current month
		String[] tempAry = today.split(Messages.getString("AdminViewChartController.27")); //$NON-NLS-1$
		String currentMonth = Messages.getString("AdminViewChartController.28")+tempAry[1]; //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.29") + currentMonth); //$NON-NLS-1$

		// purchase data list
		 ObservableList<String> purchaseDateList = FXCollections.observableArrayList();

		// get data from database
		// search by (current) month
		String getIdPurchaseQuery = Messages.getString("AdminViewChartController.30") + currentMonth + Messages.getString("AdminViewChartController.31"); //$NON-NLS-1$ //$NON-NLS-2$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsPurchase = DBInitialize.statement.executeQuery(getIdPurchaseQuery);
		while (rsPurchase.next()) {

			String tem = rsPurchase.getString(1);
			String[] dateArray = tem.split(Messages.getString("AdminViewChartController.32")); //$NON-NLS-1$
			purchaseDateList.add(dateArray[0]);
		}
		

		//print distinct date
		for(int i = 0; i < purchaseDateList.size(); i++) {
			System.out.println(Messages.getString("AdminViewChartController.33")+purchaseDateList.get(i)); //$NON-NLS-1$
		}
		
		//purchase count list 
		ObservableList<Integer> purchaseCountTemp = FXCollections.observableArrayList();
		ObservableList<Integer> purchaseCount = FXCollections.observableArrayList();
		ObservableList<Integer> transactionCount = FXCollections.observableArrayList();
		
		//get count from db by date
		for(int i = 0 ; i < purchaseDateList.size(); i++) {
			
		//purchase count
		String countQuery = Messages.getString("AdminViewChartController.34")+purchaseDateList.get(i)+Messages.getString("AdminViewChartController.35")+currentMonth+Messages.getString("AdminViewChartController.36"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DBInitialize();
		ResultSet rsPCountTemp = DBInitialize.statement.executeQuery(countQuery);
		while(rsPCountTemp.next()) {
			purchaseCountTemp.add(rsPCountTemp.getInt(1));
		}

		String countTQuery = Messages.getString("AdminViewChartController.37")+purchaseDateList.get(i)+Messages.getString("AdminViewChartController.38")+currentMonth+Messages.getString("AdminViewChartController.39"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		new DBInitialize();
		ResultSet rsTCount = DBInitialize.statement.executeQuery(countTQuery);
		while(rsTCount.next()) {
			transactionCount.add(rsTCount.getInt(1));
		}
		
		purchaseCount.add( purchaseCountTemp.get(i) - transactionCount.get(i));
		
		}//end of for
		
		System.out.println(Messages.getString("AdminViewChartController.40")+purchaseDateList.size()); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.41")+purchaseCount.size()); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.42")+transactionCount.size()); //$NON-NLS-1$

		final NumberAxis xAxis = new NumberAxis(1, 31, 1);
		xAxis.setLabel(Messages.getString("AdminViewChartController.43")); //$NON-NLS-1$

		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(Messages.getString("AdminViewChartController.44")); //$NON-NLS-1$
		
		final StackedAreaChart<Number, Number> sac = new StackedAreaChart<Number, Number>(xAxis, yAxis);

		sac.setTitle(Messages.getString("AdminViewChartController.45")); //$NON-NLS-1$
		
		XYChart.Series<Number, Number> seriesCard = new XYChart.Series<Number, Number>();
		seriesCard.setName(Messages.getString("AdminViewChartController.46")); //$NON-NLS-1$
		
		for( int k = 0 ; k < purchaseDateList.size(); k++) {
			seriesCard.getData().add(new XYChart.Data<>(Integer.parseInt(purchaseDateList.get(k)), transactionCount.get(k)));
		}
		
		
		/*seriesCard.getData().add(new XYChart.Data(27, 21));
		seriesCard.getData().add(new XYChart.Data(30, 21));
		*/
		
		XYChart.Series<Number, Number> seriesCash = new XYChart.Series<Number, Number>();
		seriesCash.setName(Messages.getString("AdminViewChartController.47")); //$NON-NLS-1$
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
		System.out.println(Messages.getString("AdminViewChartController.48")); //$NON-NLS-1$
	}

	private void showingCategoryChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub

		// category array list
		ObservableList<String> categoryNameList = FXCollections.observableArrayList();
		ObservableList<String> categoryIDList = FXCollections.observableArrayList();

		// get category from database
		String getCategroyQuery = Messages.getString("AdminViewChartController.49"); //$NON-NLS-1$
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
			String getCategoryAmountQueyry = Messages.getString("AdminViewChartController.50") //$NON-NLS-1$
					+ categoryIDList.get(i) + Messages.getString("AdminViewChartController.51"); //$NON-NLS-1$
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
		pieChart.setTitle(Messages.getString("AdminViewChartController.52")); //$NON-NLS-1$

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

		System.out.println(Messages.getString("AdminViewChartController.53")); //$NON-NLS-1$

	}

	private void showingMonthlyChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// current date
		String pattern = Messages.getString("AdminViewChartController.54"); //$NON-NLS-1$
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminViewChartController.55") + today); //$NON-NLS-1$

		// current year
		String[] tempAry = today.split(Messages.getString("AdminViewChartController.56")); //$NON-NLS-1$
		String currentYear = tempAry[2];
		System.out.println(Messages.getString("AdminViewChartController.57") + currentYear); //$NON-NLS-1$

		// month list
		ObservableList<String> monthList = FXCollections.observableArrayList();
		ObservableList<String> monthTempList = FXCollections.observableArrayList();

		// get data from database
		String getMonthQuery = Messages.getString("AdminViewChartController.58") + currentYear //$NON-NLS-1$
				+ Messages.getString("AdminViewChartController.59"); //$NON-NLS-1$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetMonth = DBInitialize.statement.executeQuery(getMonthQuery);
		while (rsGetMonth.next()) {
			String dbDate = rsGetMonth.getString(1);
			String[] tempA = dbDate.split(Messages.getString("AdminViewChartController.60")); //$NON-NLS-1$
			String dbMonth = tempA[1];
			monthTempList.add(dbMonth);

		} // end of while

		System.out.println(Messages.getString("AdminViewChartController.61") + monthTempList.get(0)); //$NON-NLS-1$

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

		System.out.println(Messages.getString("AdminViewChartController.62") + set); //$NON-NLS-1$

		// convert set to array
		String[] setary = set.toArray(new String[set.size()]);

		System.out.print(Messages.getString("AdminViewChartController.63")); //$NON-NLS-1$
		for (int b = 0; b < setary.length; b++) {
			System.out.print(Messages.getString("AdminViewChartController.64") + setary[b]); //$NON-NLS-1$
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
			String monthAmountQuery = Messages.getString("AdminViewChartController.65") //$NON-NLS-1$
					+ monthList.get(j) + Messages.getString("AdminViewChartController.66"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsMonthAmount = DBInitialize.statement.executeQuery(monthAmountQuery);
			while (rsMonthAmount.next()) {

				amount = amount + Double.parseDouble(rsMonthAmount.getString(1));

			} // end of while
			monthAmountList.add(Messages.getString("AdminViewChartController.67") + amount); //$NON-NLS-1$

		} // end of for

		// Defining the x axis
		NumberAxis xAxis = new NumberAxis(1, 12, 1);
		xAxis.setLabel(Messages.getString("AdminViewChartController.68")); //$NON-NLS-1$

		// Defining the y axis
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(Messages.getString("AdminViewChartController.69")); //$NON-NLS-1$

		// Creating the line chart
		LineChart linechart = new LineChart(xAxis, yAxis);
		linechart.setTitle(Messages.getString("AdminViewChartController.70")); //$NON-NLS-1$

		// Prepare XYChart.Series objects by setting data
		XYChart.Series series1 = new XYChart.Series();
		series1.setName(Messages.getString("AdminViewChartController.71")); //$NON-NLS-1$

		for (int k = 0; k < monthList.size(); k++) {
			series1.getData().add(
					new XYChart.Data(Integer.parseInt(monthList.get(k)), Double.parseDouble(monthAmountList.get(k))));

		}

		// System.out.println("month "+monthList.get(1)+" amount "+
		// monthAmountList.get(1));

		/*
		 * series1.getData().add(new XYChart.Data(1, 20000));
		 * series1.getData().add(new XYChart.Data(4, 5000)); series1.getData().add(new
		 * XYChart.Data(6, 2000000)); series1.getData().add(new XYChart.Data(9,
		 * 10000)); series1.getData().add(new XYChart.Data(11, 900000));
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

		System.out.println(Messages.getString("AdminViewChartController.72")); //$NON-NLS-1$

	}

	private void showingDailyChart()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		// TODO Auto-generated method stub
		// get data from db and generated to chart data
		// current date
		String pattern = Messages.getString("AdminViewChartController.73"); //$NON-NLS-1$
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("AdminViewChartController.74") + today); //$NON-NLS-1$

		// current month
		String[] tempAry = today.split(Messages.getString("AdminViewChartController.75")); //$NON-NLS-1$
		String currentmonth = tempAry[1] + Messages.getString("AdminViewChartController.76") + tempAry[2]; //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.77") + currentmonth); //$NON-NLS-1$

		// get data of current month
		// get date of this month in purchase
		String getDateQuery = Messages.getString("AdminViewChartController.78") + currentmonth + Messages.getString("AdminViewChartController.79"); //$NON-NLS-1$ //$NON-NLS-2$

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
			String getAmountQuery = Messages.getString("AdminViewChartController.80") + dateAry.get(j) //$NON-NLS-1$
					+ Messages.getString("AdminViewChartController.81"); //$NON-NLS-1$
			System.out.println(Messages.getString("AdminViewChartController.82") + totalAmount); //$NON-NLS-1$

			new DBInitialize();
			ResultSet rsGetAmountData = DBInitialize.statement.executeQuery(getAmountQuery);

			while (rsGetAmountData.next()) {
				totalAmount = totalAmount + Double.parseDouble(rsGetAmountData.getString(1));

				System.out.println(Messages.getString("AdminViewChartController.83") + totalAmount); //$NON-NLS-1$
			} // end of while
			finalAmountAry.add((int) totalAmount);
			System.out.println(Messages.getString("AdminViewChartController.84") + totalAmount); //$NON-NLS-1$
		} // end of for
			// System.out.println("total amount4 is ...... "+totalAmount);

		System.out.println(Messages.getString("AdminViewChartController.85") + finalDateAry.get(0)); //$NON-NLS-1$
		System.out.println(Messages.getString("AdminViewChartController.86") + finalAmountAry.get(0)); //$NON-NLS-1$

		// Defining the x axis
		NumberAxis xAxis = new NumberAxis(1, 31, 2);
		xAxis.setLabel(Messages.getString("AdminViewChartController.87")); //$NON-NLS-1$

		// Defining the y axis
		NumberAxis yAxis = new NumberAxis(500, 10000, 1000);
		yAxis.setLabel(Messages.getString("AdminViewChartController.88")); //$NON-NLS-1$

		// Creating the line chart
		LineChart linechart = new LineChart(xAxis, yAxis);
		linechart.setTitle(Messages.getString("AdminViewChartController.89")); //$NON-NLS-1$

		// Prepare XYChart.Series objects by setting data
		XYChart.Series series1 = new XYChart.Series();
		series1.setName(Messages.getString("AdminViewChartController.90")); //$NON-NLS-1$

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

		System.out.println(Messages.getString("AdminViewChartController.91")); //$NON-NLS-1$

	}
}
