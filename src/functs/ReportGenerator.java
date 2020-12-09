package functs;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import common.Common;
import database.DBInitialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DailyReport;
import model.DailyTable;
import model.DayItemTable;
import model.ProductItem;
import model.Sale;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class ReportGenerator {

	public void generatevoucher(ObservableList<Sale> sale) throws JRException, FileNotFoundException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		BasicConfigurator.configure();
		//
		// new DBInitialize().DBInitialize();
		// Connection conn = ConnectionUtils.getConnection();

		// then using empty datasource.
		// JRDataSource dataSource = new JREmptyDataSource();

		HashMap<String, Object> param = new HashMap<String, Object>();

		/* List to hold Items */
		List<Sale> listItems = new ArrayList<Sale>();

		for (int i = 0; i < sale.size(); i++) {
			Sale sa = new Sale();
			sa.setName(sale.get(i).getName());
			sa.setUnitamount(sale.get(i).getUnitamount());
			sa.setDiscount(sale.get(i).getDiscount());
			sa.setQuantity(sale.get(i).getQuantity());
			sa.setTotalamount(sale.get(i).getTotalamount());

			listItems.add(sa);
		}

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);

		// current date
		String pattern = Messages.getString("ReportGenerator.0"); //$NON-NLS-1$
		String todaydate = new SimpleDateFormat(pattern).format(new Date());

		// create today current time(purhcase time)
		@SuppressWarnings("deprecation")
		String hour = Messages.getString("ReportGenerator.1") + new Date().getHours(); //$NON-NLS-1$
		@SuppressWarnings("deprecation")
		String min = Messages.getString("ReportGenerator.2") + new Date().getMinutes(); //$NON-NLS-1$
		String time = hour + Messages.getString("ReportGenerator.3") + min; //$NON-NLS-1$
		System.out.println(Messages.getString("ReportGenerator.4") + time); //$NON-NLS-1$

		/*
		 * //current time DateFormat format = new SimpleDateFormat("HHmm"); String time
		 * = format.format(new Date()); String time1 = time.split(1);
		 * System.out.println("time is : "+time);
		 */
		ObservableList<String> buyGetList = FXCollections.observableArrayList(Common.buygetdata);
		ObservableList<String> toPrintPromoList = FXCollections.observableArrayList();

		for (int i = 0; i < buyGetList.size(); i++) {
			// get buy get by promotion
			String getPromoQuery = Messages.getString("ReportGenerator.5") + buyGetList.get(i) //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.6"); //$NON-NLS-1$
			String promoDesc = Messages.getString("ReportGenerator.7"); //$NON-NLS-1$
			String itemName = Messages.getString("ReportGenerator.8"); //$NON-NLS-1$
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rsPromoDesc = DBInitialize.statement.executeQuery(getPromoQuery);
			while (rsPromoDesc.next()) {

				itemName = rsPromoDesc.getString(1);
				promoDesc = rsPromoDesc.getString(2);
			}

			toPrintPromoList.add(Messages.getString("ReportGenerator.9") + itemName //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.10") + promoDesc); //$NON-NLS-1$

		} // ennd of for

		// get id of buy get list and check the quantity and reduce to db
		for (int e = 0; e < buyGetList.size(); e++) {
			String barcode = buyGetList.get(e);
			for (int f = 0; f < sale.size(); f++) {
				Sale sale1 = sale.get(e);
				if (barcode.equals(sale1.getBarcode())) {
					int count = sale1.getQuantity();

					// get buy get data
					String getBuyGetQuery = Messages.getString("ReportGenerator.11") + barcode //$NON-NLS-1$
							+ Messages.getString("ReportGenerator.12"); //$NON-NLS-1$
					new DBInitialize().DBInitialize();
					new DBInitialize();
					ResultSet rsBuyGet = DBInitialize.statement.executeQuery(getBuyGetQuery);
					String buyGetData = Messages.getString("ReportGenerator.13"); //$NON-NLS-1$
					while (rsBuyGet.next()) {
						buyGetData = rsBuyGet.getString(1);

					} // end of while
					String[] buyGetDataAry = buyGetData.split(Messages.getString("ReportGenerator.14")); //$NON-NLS-1$
					String buy = buyGetDataAry[1];
					String get = buyGetDataAry[3];

					if (count >= Integer.parseInt(buy)) {
						int tempGive = count / Integer.parseInt(buy);
						int realGive = tempGive * Integer.parseInt(get);

						// get current stock amount and count
						String getCurrentStockAndCountQuery = Messages.getString("ReportGenerator.15") + barcode //$NON-NLS-1$
								+ Messages.getString("ReportGenerator.16"); //$NON-NLS-1$
						new DBInitialize();
						ResultSet rsCSAC = DBInitialize.statement.executeQuery(getCurrentStockAndCountQuery);
						String curStock = Messages.getString("ReportGenerator.17"); //$NON-NLS-1$
						int curCount = 0;
						while (rsCSAC.next()) {
							curStock = rsCSAC.getString(1);
							curCount = rsCSAC.getInt(2);
						}

						String newStock = Messages.getString("ReportGenerator.18") //$NON-NLS-1$
								+ (Integer.parseInt(curStock) - realGive);
						int newCount = curCount + realGive;

						// reduce the stock amount and count in db
						String reductStockACountQuery = Messages.getString("ReportGenerator.19") + newStock //$NON-NLS-1$
								+ Messages.getString("ReportGenerator.20") + newCount //$NON-NLS-1$
								+ Messages.getString("ReportGenerator.21") + barcode //$NON-NLS-1$
								+ Messages.getString("ReportGenerator.22"); //$NON-NLS-1$
						new DBInitialize();
						DBInitialize.statement.executeUpdate(reductStockACountQuery);

					} // end of check count if
					else {
						// do nothing
					}
				} // end of if

			} // end of for
		} // end of for

		// getItems from toPrintPromoList
		String toPrintPromo = Messages.getString("ReportGenerator.23"); //$NON-NLS-1$
		for (int j = 0; j < toPrintPromoList.size(); j++) {
			toPrintPromo = toPrintPromo + Messages.getString("ReportGenerator.24") + toPrintPromoList.get(j); //$NON-NLS-1$
		}

		param.put(Messages.getString("ReportGenerator.25"), Messages.getString("ReportGenerator.26")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.27"), itemsJRBean); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.28"), Common.cashierrec.getName()); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.29"), Common.totalAmount); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.30"), Common.payamount); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.31"), Common.change); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.32"), todaydate); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.33"), time); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.34"), Common.slipno); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.35"), Common.paidtype); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.36"), toPrintPromo); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.37"), Common.cardinfo); //$NON-NLS-1$

		// Make sure the output directory exists.
		File outDir = new File(Messages.getString("ReportGenerator.38")); //$NON-NLS-1$
		outDir.mkdirs();

		// JasperCompileManager.compileReportToFile( new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jrxml", new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jasper");
		// JasperCompileManager.compileReportToFile(new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jrxml", new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(new File(Messages.getString("ReportGenerator.39")).getAbsolutePath() //$NON-NLS-1$
						+ Messages.getString("ReportGenerator.40")); //$NON-NLS-1$

		// JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jasper");
		// System.out.println("file is : " + new File("").getAbsolutePath() +
		// "/src/jaspertemplate/voucherprint.jasper");

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				Messages.getString("ReportGenerator.41")); //$NON-NLS-1$
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle(Messages.getString("ReportGenerator.42")); //$NON-NLS-1$
		// jasperViewer.getIc

		Common.buygetdata.clear();
		Common.buygetitem = Messages.getString("ReportGenerator.43"); //$NON-NLS-1$
		Common.buygetpromo = Messages.getString("ReportGenerator.44"); //$NON-NLS-1$
		Common.totalAmount = 0;
		Common.change = 0;
		Common.payamount = 0;

	}

	/// daily report generation
	public void generateDailyReport()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException {
		BasicConfigurator.configure();

		// current date
		String pattern = Messages.getString("ReportGenerator.45"); //$NON-NLS-1$
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("ReportGenerator.46") + today); //$NON-NLS-1$

		// get today total sale amount
		String getTotalSaleQuery = Messages.getString("ReportGenerator.47") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.48"); //$NON-NLS-1$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetTotalAmount = DBInitialize.statement.executeQuery(getTotalSaleQuery);
		double totalAmount = 0;
		while (rsGetTotalAmount.next()) {
			totalAmount = totalAmount + Double.parseDouble(rsGetTotalAmount.getString(1));
		}

		// get today total sale count
		String getTotalItemCount = Messages.getString("ReportGenerator.49") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.50"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsGetTotalItemCount = DBInitialize.statement.executeQuery(getTotalItemCount);
		int itemCount = 0;
		while (rsGetTotalItemCount.next()) {
			String tempCount = rsGetTotalItemCount.getString(1);

			String[] tempAry = tempCount.split(Messages.getString("ReportGenerator.51")); //$NON-NLS-1$
			for (int i = 0; i < tempAry.length; i++) {
				System.out.println(Messages.getString("ReportGenerator.52") + tempAry[i]); //$NON-NLS-1$
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}

		System.out.println(Messages.getString("ReportGenerator.53") + itemCount); //$NON-NLS-1$

		// compute cash and card user for today
		// get total purchase
		String getTotalPurchaseCountQuery = Messages.getString("ReportGenerator.54") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.55"); //$NON-NLS-1$
		int totalPurchase = 0;
		new DBInitialize();
		ResultSet rsTotalPurchaseCount = DBInitialize.statement.executeQuery(getTotalPurchaseCountQuery);
		while (rsTotalPurchaseCount.next()) {
			totalPurchase = rsTotalPurchaseCount.getInt(1);
		}

		// get total card user (from transaction table)
		String getCardUserCountQuery = Messages.getString("ReportGenerator.56") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.57"); //$NON-NLS-1$
		int totalCard = 0;
		new DBInitialize();
		ResultSet rsTotalCardUser = DBInitialize.statement.executeQuery(getCardUserCountQuery);
		while (rsTotalCardUser.next()) {
			totalCard = rsTotalCardUser.getInt(1);

		}
		int totalCash = totalPurchase - totalCard;

		// get category from db
		// get category list from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = Messages.getString("ReportGenerator.58"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsCategory = DBInitialize.statement.executeQuery(getCagetoryListQuery);
		while (rsCategory.next()) {
			categoryList.add(rsCategory.getString(1));
		}

		// create array for category name, count , total sale amount
		String[] categoryNameAry = new String[categoryList.size()];
		int[] categoryCountAry = new int[categoryList.size()];
		Double[] categoryTotalAmountAry = new Double[categoryList.size()];

		// add item to category name array
		for (int a = 0; a < categoryNameAry.length; a++) {
			categoryNameAry[a] = categoryList.get(a);
			categoryCountAry[a] = 0;
			categoryTotalAmountAry[a] = 0.0;
		}

		ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
		ObservableList<String> qtyAryList = FXCollections.observableArrayList();

		// get barcode and find category type
		String getPurchaseBarcodeQuery = Messages.getString("ReportGenerator.59") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.60"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsPurchaseBarcode = DBInitialize.statement.executeQuery(getPurchaseBarcodeQuery);
		while (rsPurchaseBarcode.next()) {
			String barcode = rsPurchaseBarcode.getString(1);
			String qty = rsPurchaseBarcode.getString(2);

			String[] barcodeAry = barcode.split(Messages.getString("ReportGenerator.61")); //$NON-NLS-1$
			String[] qtyAry = qty.split(Messages.getString("ReportGenerator.62")); //$NON-NLS-1$
			for (int e = 0; e < barcodeAry.length; e++) {
				barcodeAryList.add(barcodeAry[e]);
				qtyAryList.add(qtyAry[e]);
			}
		} // end of while

		for (int i = 0; i < barcodeAryList.size(); i++) {
			String getCategoryQuery = Messages.getString("ReportGenerator.63") + barcodeAryList.get(i) //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.64"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsBarcodeToGategory = DBInitialize.statement.executeQuery(getCategoryQuery);
			while (rsBarcodeToGategory.next()) {
				String categoryName = rsBarcodeToGategory.getString(1);
				String price = rsBarcodeToGategory.getString(2);

				for (int k = 0; k < categoryNameAry.length; k++) {

					if (categoryNameAry[k].equals(categoryName)) {
						categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i)); // set count
						categoryTotalAmountAry[k] = categoryTotalAmountAry[k]
								+ Double.parseDouble(price) * Integer.parseInt(qtyAryList.get(i));
					} // end of if

				} // end of inner for

			} // end of inner while

		} // end of for

		double initialsale = 0;
		double promotion = 0;

		/* List to hold Items */
		List<DailyReport> listItems = new ArrayList<DailyReport>();
		// listItems.add(new DailyReport("Sattionary", 4, 3000.0));
		for (int f = 0; f < categoryNameAry.length; f++) {
			DailyReport dr = new DailyReport();
			dr.setCategoryname(categoryNameAry[f]);
			dr.setSalecount(categoryCountAry[f]);
			dr.setCategorytotalamount(categoryTotalAmountAry[f]);
			listItems.add(dr);
			initialsale = initialsale + categoryTotalAmountAry[f];
		}

		promotion = initialsale - totalAmount;

		/////////////////////////////////////////////////////////
		// for daily item sale table
		// list
		ObservableList<String> barcodeList = FXCollections.observableArrayList();
		// ObservableList<String> quantityList = FXCollections.observableArrayList();

		// query for getting all the item data from db
		String getAllItemQuery = Messages.getString("ReportGenerator.65"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsAllItem = DBInitialize.statement.executeQuery(getAllItemQuery);
		while (rsAllItem.next()) {
			barcodeList.add(rsAllItem.getString(1));
		}

		String[] barcodeAry = new String[barcodeList.size()];
		String[] barcodeCountAry = new String[barcodeList.size()];
		String[] barcodePriceAry = new String[barcodeList.size()];
		String[] barcodeCategoryAry = new String[barcodeList.size()];
		String[] barcodeNameAry = new String[barcodeList.size()];

		// add data to count
		for (int b = 0; b < barcodeCountAry.length; b++) {
			barcodeCountAry[b] = Messages.getString("ReportGenerator.66"); //$NON-NLS-1$
			barcodeAry[b] = barcodeList.get(b);
		}

		// barcodeArrayList & qty array list
		ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
		ObservableList<String> qtyArrayList = FXCollections.observableArrayList();

		// query
		String getPurchaseTodayQuery = Messages.getString("ReportGenerator.67") + today //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.68"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsPurchaseToday = DBInitialize.statement.executeQuery(getPurchaseTodayQuery);
		while (rsPurchaseToday.next()) {
			String barcodeTemp = (rsPurchaseToday.getString(1));
			String qtyTodayTemp = rsPurchaseToday.getString(2);

			String[] barcodeTempAry = barcodeTemp.split(Messages.getString("ReportGenerator.69")); //$NON-NLS-1$
			String[] qtyTodayTempAry = qtyTodayTemp.split(Messages.getString("ReportGenerator.70")); //$NON-NLS-1$

			for (int w = 0; w < barcodeTempAry.length; w++) {
				barcodeArrayList.add(barcodeTempAry[w]);
				qtyArrayList.add(qtyTodayTempAry[w]);
			}

		} // end of while

		for (int m = 0; m < barcodeAryList.size(); m++) {
			String bCode = barcodeAryList.get(m);

			for (int l = 0; l < barcodeAry.length; l++) {
				if (bCode.equals(barcodeAry[l])) {
					barcodeCountAry[l] = Messages.getString("ReportGenerator.71") //$NON-NLS-1$
							+ (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)));
				} // end of if
			} // end of inner for
		} // end of for

		// get category name and price in ordering
		for (int g = 0; g < barcodeAry.length; g++) {
			String getCandPQuery = Messages.getString("ReportGenerator.72") + barcodeAry[g] //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.73"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsGetCandP = DBInitialize.statement.executeQuery(getCandPQuery);
			while (rsGetCandP.next()) {
				String cName = rsGetCandP.getString(1);
				String cPrice = rsGetCandP.getString(2);
				String pName = rsGetCandP.getString(3);

				barcodeCategoryAry[g] = cName;
				barcodePriceAry[g] = cPrice;
				barcodeNameAry[g] = pName;
			} // end of while

		} // end of for loop

		/*
		 * //check point System.out.println("checking ............"); for(int i =0 ; i<
		 * barcodeAry.length ; i++) { System.out.print(barcodeAry[i]);
		 * System.out.print(barcodeCategoryAry[i]); System.out.print(barcodeNameAry[i]);
		 * System.out.print(barcodeCountAry[i]);
		 * 
		 * }
		 */

		/* List to hold Items */
		List<DayItemTable> listItems1 = new ArrayList<DayItemTable>();

		// construct object from model
		for (int v = 0; v < barcodeAry.length; v++) {
			DayItemTable dit = new DayItemTable();
			dit.setBarcode(barcodeAry[v]);
			dit.setCategory(barcodeCategoryAry[v]);
			dit.setName(barcodeNameAry[v]);
			dit.setSalecount(barcodeCountAry[v]);
			Double amount = Integer.parseInt(barcodeCountAry[v] + Messages.getString("ReportGenerator.74")) //$NON-NLS-1$
					* Double.parseDouble(barcodePriceAry[v]);
			dit.setSaleamount(amount + Messages.getString("ReportGenerator.75")); //$NON-NLS-1$

			listItems1.add(dit);
		}

		/*--------------------------------------------------*/

		HashMap<String, Object> param = new HashMap<String, Object>();

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
		JRBeanCollectionDataSource itemsJRBean1 = new JRBeanCollectionDataSource(listItems1);

		param.put(Messages.getString("ReportGenerator.76"), Messages.getString("ReportGenerator.77")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.78"), Messages.getString("ReportGenerator.79")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.80"), itemsJRBean); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.81"), itemsJRBean1); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.82"), //$NON-NLS-1$
				Double.parseDouble(Messages.getString("ReportGenerator.83") + totalAmount)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.84"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.85") + itemCount)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.86"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.87") + totalCard)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.88"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.89") + totalCash)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.90"), Messages.getString("ReportGenerator.91") + initialsale); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.92"), Messages.getString("ReportGenerator.93") + totalAmount); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.94"), Messages.getString("ReportGenerator.95") + promotion); //$NON-NLS-1$ //$NON-NLS-2$

		// Make sure the output directory exists.
		File outDir = new File(Messages.getString("ReportGenerator.96")); //$NON-NLS-1$
		outDir.mkdirs();

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(new File(Messages.getString("ReportGenerator.97")).getAbsolutePath() //$NON-NLS-1$
						+ Messages.getString("ReportGenerator.98")); //$NON-NLS-1$

		// JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() +
		// "/src/jaspertemplate/daily_report.jasper");
		System.out.println(Messages.getString("ReportGenerator.99") //$NON-NLS-1$
				+ new File(Messages.getString("ReportGenerator.100")).getAbsolutePath() //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.101")); //$NON-NLS-1$

		/* Using compiled version(.jasper) of Jasper report to generate PDF */
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param,
		// new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				Messages.getString("ReportGenerator.102")); //$NON-NLS-1$
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle(Messages.getString("ReportGenerator.103")); //$NON-NLS-1$
		// jasperViewer.getIc

	}

	public void generatePopularItem()
			throws JRException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		BasicConfigurator.configure();

		ObservableList<ProductItem> popularData = FXCollections.observableArrayList();

		// get popular item data from db
		new DBInitialize().DBInitialize();
		String query = Messages.getString("ReportGenerator.104"); //$NON-NLS-1$

		new DBInitialize();
		ResultSet rs = DBInitialize.statement.executeQuery(query);

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
			product.setCount(rs.getInt(9));

			popularData.add(product);

		}

		HashMap<String, Object> param = new HashMap<String, Object>();

		/*
		 * List to hold Items List<Sale> listItems = new ArrayList<Sale>();
		 */

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(popularData);

		param.put(Messages.getString("ReportGenerator.105"), Messages.getString("ReportGenerator.106")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.107"), itemsJRBean); //$NON-NLS-1$

		// Make sure the output directory exists.
		File outDir = new File(Messages.getString("ReportGenerator.108")); //$NON-NLS-1$
		outDir.mkdirs();

		// JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() +
		// "/src/jaspertemplate/popular_item_report.jasper");
		// System.out.println("file is : " + new File("").getAbsolutePath() +
		// "/src/jaspertemplate/popular_item_report.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(new File(Messages.getString("ReportGenerator.109")).getAbsolutePath() //$NON-NLS-1$
						+ Messages.getString("ReportGenerator.110")); //$NON-NLS-1$
		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				Messages.getString("ReportGenerator.111")); //$NON-NLS-1$
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle(Messages.getString("ReportGenerator.112")); //$NON-NLS-1$
	}

	/*-----------------------------------------------------------------------------------------------*/

	public void generateMonthlyReport()
			throws JRException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		BasicConfigurator.configure();

		// current date
		String pattern = Messages.getString("ReportGenerator.113"); //$NON-NLS-1$
		String todayy = new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("ReportGenerator.114") + todayy); //$NON-NLS-1$

		// get month
		String[] todayAry = todayy.split(Messages.getString("ReportGenerator.115")); //$NON-NLS-1$
		String currentMonth = todayAry[1] + Messages.getString("ReportGenerator.116") + todayAry[2]; //$NON-NLS-1$

		// get today total sale amount
		String getTotalSaleQuery = Messages.getString("ReportGenerator.117") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.118"); //$NON-NLS-1$
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetTotalAmount = DBInitialize.statement.executeQuery(getTotalSaleQuery);
		double totalAmount = 0;
		while (rsGetTotalAmount.next()) {
			totalAmount = totalAmount + Double.parseDouble(rsGetTotalAmount.getString(1));
		}

		// get today total sale count
		String getTotalItemCount = Messages.getString("ReportGenerator.119") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.120"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsGetTotalItemCount = DBInitialize.statement.executeQuery(getTotalItemCount);
		int itemCount = 0;
		while (rsGetTotalItemCount.next()) {
			String tempCount = rsGetTotalItemCount.getString(1);

			String[] tempAry = tempCount.split(Messages.getString("ReportGenerator.121")); //$NON-NLS-1$
			for (int i = 0; i < tempAry.length; i++) {
				System.out.println(Messages.getString("ReportGenerator.122") + tempAry[i]); //$NON-NLS-1$
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}

		System.out.println(Messages.getString("ReportGenerator.123") + itemCount); //$NON-NLS-1$

		// compute cash and card user for today
		// get total purchase
		String getTotalPurchaseCountQuery = Messages.getString("ReportGenerator.124") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.125"); //$NON-NLS-1$
		int totalPurchase = 0;
		new DBInitialize();
		ResultSet rsTotalPurchaseCount = DBInitialize.statement.executeQuery(getTotalPurchaseCountQuery);
		while (rsTotalPurchaseCount.next()) {
			totalPurchase = rsTotalPurchaseCount.getInt(1);
		}

		// get total card user (from transaction table)
		String getCardUserCountQuery = Messages.getString("ReportGenerator.126") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.127"); //$NON-NLS-1$
		int totalCard = 0;
		new DBInitialize();
		ResultSet rsTotalCardUser = DBInitialize.statement.executeQuery(getCardUserCountQuery);
		while (rsTotalCardUser.next()) {
			totalCard = rsTotalCardUser.getInt(1);

		}
		int totalCash = totalPurchase - totalCard;

		// get category from db
		// get category list from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = Messages.getString("ReportGenerator.128"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsCategory = DBInitialize.statement.executeQuery(getCagetoryListQuery);
		while (rsCategory.next()) {
			categoryList.add(rsCategory.getString(1));
		}

		// create array for category name, count , total sale amount
		String[] categoryNameAry = new String[categoryList.size()];
		int[] categoryCountAry = new int[categoryList.size()];
		Double[] categoryTotalAmountAry = new Double[categoryList.size()];

		// add item to category name array
		for (int a = 0; a < categoryNameAry.length; a++) {
			categoryNameAry[a] = categoryList.get(a);
			categoryCountAry[a] = 0;
			categoryTotalAmountAry[a] = 0.0;
		}

		ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
		ObservableList<String> qtyAryList = FXCollections.observableArrayList();

		// get barcode and find category type
		String getPurchaseBarcodeQuery = Messages.getString("ReportGenerator.129") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.130"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsPurchaseBarcode = DBInitialize.statement.executeQuery(getPurchaseBarcodeQuery);
		while (rsPurchaseBarcode.next()) {
			String barcode = rsPurchaseBarcode.getString(1);
			String qty = rsPurchaseBarcode.getString(2);

			String[] barcodeAry = barcode.split(Messages.getString("ReportGenerator.131")); //$NON-NLS-1$
			String[] qtyAry = qty.split(Messages.getString("ReportGenerator.132")); //$NON-NLS-1$
			for (int e = 0; e < barcodeAry.length; e++) {
				barcodeAryList.add(barcodeAry[e]);
				qtyAryList.add(qtyAry[e]);
			}
		} // end of while

		for (int i = 0; i < barcodeAryList.size(); i++) {
			String getCategoryQuery = Messages.getString("ReportGenerator.133") + barcodeAryList.get(i) //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.134"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsBarcodeToGategory = DBInitialize.statement.executeQuery(getCategoryQuery);
			while (rsBarcodeToGategory.next()) {
				String categoryName = rsBarcodeToGategory.getString(1);
				String price = rsBarcodeToGategory.getString(2);

				for (int k = 0; k < categoryNameAry.length; k++) {

					if (categoryNameAry[k].equals(categoryName)) {
						categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i)); // set count
						categoryTotalAmountAry[k] = categoryTotalAmountAry[k]
								+ Double.parseDouble(price) * Integer.parseInt(qtyAryList.get(i));
					} // end of if

				} // end of inner for

			} // end of inner while

		} // end of for

		double initialsale = 0;
		double promotion = 0;

		/* List to hold Items */
		List<DailyReport> listItems = new ArrayList<DailyReport>();
		// listItems.add(new DailyReport("Sattionary", 4, 3000.0));
		for (int f = 0; f < categoryNameAry.length; f++) {
			DailyReport dr = new DailyReport();
			dr.setCategoryname(categoryNameAry[f]);
			dr.setSalecount(categoryCountAry[f]);
			dr.setCategorytotalamount(categoryTotalAmountAry[f]);
			listItems.add(dr);
			initialsale = initialsale + categoryTotalAmountAry[f];
		}

		promotion = initialsale - totalAmount;

		/////////////////////////////////////////////////////////
		// for daily item sale table
		// list
		ObservableList<String> barcodeList = FXCollections.observableArrayList();
		// ObservableList<String> quantityList = FXCollections.observableArrayList();

		// query for getting all the item data from db
		String getAllItemQuery = Messages.getString("ReportGenerator.135"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsAllItem = DBInitialize.statement.executeQuery(getAllItemQuery);
		while (rsAllItem.next()) {
			barcodeList.add(rsAllItem.getString(1));
		}

		String[] barcodeAry = new String[barcodeList.size()];
		String[] barcodeCountAry = new String[barcodeList.size()];
		String[] barcodePriceAry = new String[barcodeList.size()];
		String[] barcodeCategoryAry = new String[barcodeList.size()];
		String[] barcodeNameAry = new String[barcodeList.size()];

		// add data to count
		for (int b = 0; b < barcodeCountAry.length; b++) {
			barcodeCountAry[b] = Messages.getString("ReportGenerator.136"); //$NON-NLS-1$
			barcodeAry[b] = barcodeList.get(b);
		}

		// barcodeArrayList & qty array list
		ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
		ObservableList<String> qtyArrayList = FXCollections.observableArrayList();

		// query
		String getPurchaseTodayQuery = Messages.getString("ReportGenerator.137") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.138"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsPurchaseToday = DBInitialize.statement.executeQuery(getPurchaseTodayQuery);
		while (rsPurchaseToday.next()) {
			String barcodeTemp = (rsPurchaseToday.getString(1));
			String qtyTodayTemp = rsPurchaseToday.getString(2);

			String[] barcodeTempAry = barcodeTemp.split(Messages.getString("ReportGenerator.139")); //$NON-NLS-1$
			String[] qtyTodayTempAry = qtyTodayTemp.split(Messages.getString("ReportGenerator.140")); //$NON-NLS-1$

			for (int w = 0; w < barcodeTempAry.length; w++) {
				barcodeArrayList.add(barcodeTempAry[w]);
				qtyArrayList.add(qtyTodayTempAry[w]);
			}

		} // end of while

		for (int m = 0; m < barcodeAryList.size(); m++) {
			String bCode = barcodeAryList.get(m);

			for (int l = 0; l < barcodeAry.length; l++) {
				if (bCode.equals(barcodeAry[l])) {
					barcodeCountAry[l] = Messages.getString("ReportGenerator.141") //$NON-NLS-1$
							+ (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)));
				} // end of if
			} // end of inner for
		} // end of for

		// get category name and price in ordering
		for (int g = 0; g < barcodeAry.length; g++) {
			String getCandPQuery = Messages.getString("ReportGenerator.142") + barcodeAry[g] //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.143"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsGetCandP = DBInitialize.statement.executeQuery(getCandPQuery);
			while (rsGetCandP.next()) {
				String cName = rsGetCandP.getString(1);
				String cPrice = rsGetCandP.getString(2);
				String pName = rsGetCandP.getString(3);

				barcodeCategoryAry[g] = cName;
				barcodePriceAry[g] = cPrice;
				barcodeNameAry[g] = pName;
			} // end of while

		} // end of for loop

		// check point
		System.out.println(Messages.getString("ReportGenerator.144")); //$NON-NLS-1$
		for (int i = 0; i < barcodeAry.length; i++) {
			System.out.print(barcodeAry[i]);
			System.out.print(barcodeCategoryAry[i]);
			System.out.print(barcodeNameAry[i]);
			System.out.print(barcodeCountAry[i]);

		}

		/* List to hold Items */
		List<DayItemTable> listItems1 = new ArrayList<DayItemTable>();

		// construct object from model
		for (int v = 0; v < barcodeAry.length; v++) {
			DayItemTable dit = new DayItemTable();
			dit.setBarcode(barcodeAry[v]);
			dit.setCategory(barcodeCategoryAry[v]);
			dit.setName(barcodeNameAry[v]);
			dit.setSalecount(barcodeCountAry[v]);
			Double amount = Integer.parseInt(barcodeCountAry[v] + Messages.getString("ReportGenerator.145")) //$NON-NLS-1$
					* Double.parseDouble(barcodePriceAry[v]);
			dit.setSaleamount(amount + Messages.getString("ReportGenerator.146")); //$NON-NLS-1$

			listItems1.add(dit);
		}

		/*--------------------------------------------------*/

		// for daily sale items
		// days array
		String[] daysAry = new String[31];
		String[] daysAmountAry = new String[31];
		String[] daysCountAry = new String[31];

		// set data to arrays
		for (int z = 0; z < daysAry.length; z++) {
			daysAry[z] = String.format(Messages.getString("ReportGenerator.147"), (z + 1)); //$NON-NLS-1$
			daysAmountAry[z] = 0 + Messages.getString("ReportGenerator.148"); //$NON-NLS-1$
			daysCountAry[z] = Messages.getString("ReportGenerator.149"); //$NON-NLS-1$
		}

		// get daily purchase in this month
		String getDailyPurchaseQuery = Messages.getString("ReportGenerator.150") + currentMonth //$NON-NLS-1$
				+ Messages.getString("ReportGenerator.151"); //$NON-NLS-1$
		new DBInitialize();
		ResultSet rsDailyPurchase = DBInitialize.statement.executeQuery(getDailyPurchaseQuery);
		while (rsDailyPurchase.next()) {
			String dateTemp = rsDailyPurchase.getString(1);
			String totalAmountTemp = rsDailyPurchase.getString(2);

			String[] dateTempAry = dateTemp.split(Messages.getString("ReportGenerator.152")); //$NON-NLS-1$
			// String totalAmountTempAry

			for (int r = 0; r < daysAry.length; r++) {
				if (dateTempAry[0].equals(daysAry[r])) {
					daysAmountAry[r] = Messages.getString("ReportGenerator.153") //$NON-NLS-1$
							+ (Double.parseDouble(daysAmountAry[r]) + Double.parseDouble(totalAmountTemp));
				}
			}
		}

		// get the total count , initial sale and discount
		for (int s = 0; s < daysAry.length; s++) {
			// get totalCount
			String getTotalCountQueryy = Messages.getString("ReportGenerator.154") + daysAry[s] //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.155") + currentMonth //$NON-NLS-1$
					+ Messages.getString("ReportGenerator.156"); //$NON-NLS-1$
			new DBInitialize();
			ResultSet rsTotalCountt = DBInitialize.statement.executeQuery(getTotalCountQueryy);
			while (rsTotalCountt.next()) {
				String coulntTemppp = rsTotalCountt.getString(1);
				String[] countTempAryyy = coulntTemppp.split(Messages.getString("ReportGenerator.157")); //$NON-NLS-1$
				for (int c = 0; c < countTempAryyy.length; c++) {
					daysCountAry[s] = Messages.getString("ReportGenerator.158") //$NON-NLS-1$
							+ (Integer.parseInt(daysCountAry[s]) + Integer.parseInt(countTempAryyy[c]));
				} // end of inner for

			} // end of while

			/*
			 * //get price and compute initial sale String getDailyBarcodeQuery =
			 * "SELECT `date`, `barcode` FROM `purchase` WHERE purchase.date LIKE '%"
			 * +currentMonth+"'"; new DBInitialize(); ResultSet rsDailyBarcode =
			 * DBInitialize.statement.executeQuery(getDailyBarcodeQuery);
			 * while(rsDailyBarcode.next()) { String dailyDate =
			 * rsDailyBarcode.getString(1); String Dailybarcode =
			 * rsDailyBarcode.getString(2);
			 * 
			 * }
			 */

		} // end of for

		/* List to hold Items */
		List<DailyTable> listItems2 = new ArrayList<DailyTable>();

		// adding data object
		for (int q = 0; q < daysAry.length; q++) {
			DailyTable dat = new DailyTable();
			dat.setDate(daysAry[q] + Messages.getString("ReportGenerator.159") + currentMonth); //$NON-NLS-1$
			dat.setItemcount(daysCountAry[q]);
			dat.setNetsale(daysAmountAry[q]);

			listItems2.add(dat);
		}

		/*--------------------------------------------------*/

		HashMap<String, Object> param = new HashMap<String, Object>();

		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
		JRBeanCollectionDataSource itemsJRBean1 = new JRBeanCollectionDataSource(listItems1);
		JRBeanCollectionDataSource itemsJRBean2 = new JRBeanCollectionDataSource(listItems2);

		param.put(Messages.getString("ReportGenerator.160"), Messages.getString("ReportGenerator.161")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.162"), Messages.getString("ReportGenerator.163")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.164"), Messages.getString("ReportGenerator.165")); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.166"), itemsJRBean); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.167"), itemsJRBean1); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.168"), itemsJRBean2); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.169"), //$NON-NLS-1$
				Double.parseDouble(Messages.getString("ReportGenerator.170") + totalAmount)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.171"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.172") + itemCount)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.173"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.174") + totalCard)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.175"), //$NON-NLS-1$
				Integer.parseInt(Messages.getString("ReportGenerator.176") + totalCash)); //$NON-NLS-1$
		param.put(Messages.getString("ReportGenerator.177"), Messages.getString("ReportGenerator.178") + initialsale); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.179"), Messages.getString("ReportGenerator.180") + totalAmount); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.181"), Messages.getString("ReportGenerator.182") + promotion); //$NON-NLS-1$ //$NON-NLS-2$
		param.put(Messages.getString("ReportGenerator.183"), Messages.getString("ReportGenerator.184")); //$NON-NLS-1$ //$NON-NLS-2$

		// Make sure the output directory exists.
		File outDir = new File(Messages.getString("ReportGenerator.185")); //$NON-NLS-1$
		outDir.mkdirs();

		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObjectFromFile(new File(Messages.getString("ReportGenerator.186")).getAbsolutePath() //$NON-NLS-1$
						+ Messages.getString("ReportGenerator.187")); //$NON-NLS-1$

		// JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() +
		// "/src/jaspertemplate/monthly_report.jasper");
		// System.out.println("file is : " + new File("").getAbsolutePath() +
		// "/src/jaspertemplate/monthly_report.jasper");

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				Messages.getString("ReportGenerator.188")); //$NON-NLS-1$
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle(Messages.getString("ReportGenerator.189")); //$NON-NLS-1$

	}
}