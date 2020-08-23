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
		String pattern = "dd/MM/yyyy";
		String todaydate = new SimpleDateFormat(pattern).format(new Date());

		// create today current time(purhcase time)
		String hour = "" + new Date().getHours();
		String min = "" + new Date().getMinutes();
		String time = hour + " : " + min;
		System.out.println("purhcase time is : " + time);

		/*
		 * //current time DateFormat format = new SimpleDateFormat("HHmm"); String time
		 * = format.format(new Date()); String time1 = time.split(1);
		 * System.out.println("time is : "+time);
		 */
		ObservableList<String> buyGetList = FXCollections.observableArrayList(Common.buygetdata);
		ObservableList<String> toPrintPromoList = FXCollections.observableArrayList();
		
		for(int i =0; i < buyGetList.size(); i++) {
			//get buy get by promotion
			String getPromoQuery = "SELECT productitems.name , promotion.description FROM promotion, productitems WHERE promotion.productid = '"+buyGetList.get(i)+"' AND promotion.productid = productitems.barcode";
			String promoDesc = "";
			String itemName = "";
			new DBInitialize().DBInitialize();
			new DBInitialize();
			ResultSet rsPromoDesc = DBInitialize.statement.executeQuery(getPromoQuery);
			while(rsPromoDesc.next()) {
				
				itemName = rsPromoDesc.getString(1);
				promoDesc = rsPromoDesc.getString(2);
			}
			
			toPrintPromoList.add("For Item : "+ itemName +" : "+promoDesc);
			
		}//ennd of for
		
		//get id of buy get list and check the quantity and reduce to db
		for(int e =0 ; e < buyGetList.size(); e++) {
			String barcode = buyGetList.get(e);
			for(int f = 0 ; f < sale.size() ; f++) {
				Sale sale1 = sale.get(e);
				if(barcode.equals(sale1.getBarcode())){
					int count = sale1.getQuantity();
					
					//get buy get data
					String getBuyGetQuery = "SELECT `description` FROM `promotion` WHERE promotion.productid = '"+barcode+"'";
					new DBInitialize().DBInitialize();
					new DBInitialize();
					ResultSet rsBuyGet = DBInitialize.statement.executeQuery(getBuyGetQuery);
					String buyGetData = "";
					while(rsBuyGet.next()) {
						buyGetData = rsBuyGet.getString(1);
						
					}//end of while
					String[] buyGetDataAry = buyGetData.split("\\s+");
					String buy = buyGetDataAry[1];
					String get = buyGetDataAry[3];
					
					if(count >= Integer.parseInt(buy)) {
						int tempGive = (int)(count / Integer.parseInt(buy));
						int realGive = (int)(tempGive * Integer.parseInt(get));
						
						//get current stock amount and count
						String getCurrentStockAndCountQuery = "SELECT `stockamount`, `count` FROM `productitems` WHERE productitems.barcode = '"+barcode+"'";
						new DBInitialize();
						ResultSet rsCSAC = DBInitialize.statement.executeQuery(getCurrentStockAndCountQuery);
						String curStock = "";
						int curCount = 0;
						while(rsCSAC.next()) {
							 curStock = rsCSAC.getString(1);
							 curCount = rsCSAC.getInt(2);
						}
						
						String newStock = ""+(Integer.parseInt(curStock) - realGive);
						int newCount = curCount + realGive;
						
						//reduce the stock amount and count in db
						String reductStockACountQuery = "UPDATE `productitems` SET `stockamount`= '"+newStock+"' ,`count`= "+newCount+" WHERE productitems.barcode = '"+barcode+"'";
						new DBInitialize();
						DBInitialize.statement.executeUpdate(reductStockACountQuery); 
						
						
					}//end of check count if
					else {
						//do nothing
					}
				}//end of if
				
			}//end of for
		}//end of for
		
		//getItems from toPrintPromoList
		String toPrintPromo ="";
		for( int j =0 ; j < toPrintPromoList.size() ; j++) {
			toPrintPromo = toPrintPromo +"\n"+ toPrintPromoList.get(j);
		}

		
		param.put("ItemDataSource", "HELLO");
		param.put("DS1", itemsJRBean);
		param.put("cashiername", Common.cashierrec.getName());
		param.put("total", Common.totalAmount);
		param.put("pay", Common.payamount);
		param.put("change", Common.change);
		param.put("date", todaydate);
		param.put("time", time);
		param.put("slipno", Common.slipno);
		param.put("paidtype", Common.paidtype);
		param.put("buygetpromo", toPrintPromo);
		param.put("cardinfo", Common.cardinfo);

		
		
		// Make sure the output directory exists.
		File outDir = new File("../Desktop/UCSMPOS");
		outDir.mkdirs();

		//JasperCompileManager.compileReportToFile( new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jrxml", new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jasper");
		//JasperCompileManager.compileReportToFile(new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jrxml", new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jasper");
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/jaspertemplate/voucherprint.jasper");
		
		//JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jasper");
		//System.out.println("file is : " + new File("").getAbsolutePath() + "/src/jaspertemplate/voucherprint.jasper");

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"../Desktop/UCSMPOS/Voucher.pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle("UCSM POS System: Printing service");
		// jasperViewer.getIc
		
		Common.buygetdata.clear();
		Common.buygetitem = "";
		Common.buygetpromo = "";
		Common.totalAmount = 0;
		Common.change = 0;
		Common.payamount = 0;

	}

	
	
	
	///daily report generation 
	public void generateDailyReport() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JRException {
		BasicConfigurator.configure();

		// current date
		String pattern = "dd/MM/yyyy";
		String today = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + today);

		// get today total sale amount
		String getTotalSaleQuery = "SELECT `totalamount` FROM `purchase` WHERE purchase.date = '"+today+"'";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetTotalAmount = DBInitialize.statement.executeQuery(getTotalSaleQuery);
		double totalAmount = 0;
		while(rsGetTotalAmount.next()) {
			totalAmount = totalAmount + Double.parseDouble(rsGetTotalAmount.getString(1));
		}

		
		//get today total sale count
		String getTotalItemCount = "SELECT `quantity` FROM `purchase` WHERE purchase.date = '"+today+"'"; 
		new DBInitialize();
		ResultSet rsGetTotalItemCount = DBInitialize.statement.executeQuery(getTotalItemCount);
		int itemCount = 0;
		while(rsGetTotalItemCount.next()) {
			String tempCount = rsGetTotalItemCount.getString(1);
			
			String[] tempAry = tempCount.split(",");
			for( int i = 0 ; i < tempAry.length  ; i++) {
				System.out.println("count is : "+tempAry[i]);
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}
		
		System.out.println("Total sale item count : "+itemCount);
		
		
		//compute cash and card user for today
		//get total purchase
		String getTotalPurchaseCountQuery = "SELECT COUNT(*) FROM `purchase` WHERE purchase.date = '"+today+"'";
		int totalPurchase = 0;
		new DBInitialize();
		ResultSet rsTotalPurchaseCount = DBInitialize.statement.executeQuery(getTotalPurchaseCountQuery);
		while(rsTotalPurchaseCount.next()) {
			totalPurchase = rsTotalPurchaseCount.getInt(1);
		}
		
		//get total card user (from transaction table)
		String getCardUserCountQuery = "SELECT COUNT(*) FROM transaction, purchase WHERE transaction.purchaseid = purchase.id AND purchase.date = '"+today+"'";
		int totalCard = 0;
		new DBInitialize();
		ResultSet rsTotalCardUser = DBInitialize.statement.executeQuery(getCardUserCountQuery);
		while(rsTotalCardUser.next()) {
			totalCard = rsTotalCardUser.getInt(1);
			
		}
		int totalCash = totalPurchase - totalCard ; 
		
		
		
		
		//get category from db
		//get category list from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = "SELECT  `name` FROM `productcategory`;";
		new DBInitialize();
		ResultSet rsCategory = DBInitialize.statement.executeQuery(getCagetoryListQuery);
		while(rsCategory.next()) {
			categoryList.add(rsCategory.getString(1));
		}
		
		//create array for category name, count , total sale amount 
		String [] categoryNameAry = new String[categoryList.size()];
		int [] categoryCountAry = new int [categoryList.size()];
		Double [] categoryTotalAmountAry = new Double[categoryList.size()];
		
		//add item to category name array
		for(int a = 0 ; a < categoryNameAry.length; a++) {
			categoryNameAry[a] = categoryList.get(a);
			categoryCountAry[a] = 0;
			categoryTotalAmountAry[a] = 0.0 ;
		}
		
		ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
		ObservableList<String> qtyAryList = FXCollections.observableArrayList();
		
		//get barcode and find category type
		String getPurchaseBarcodeQuery = "SELECT `barcode`, `quantity` FROM `purchase` WHERE purchase.date = '"+today+"'";
		new DBInitialize();
		ResultSet rsPurchaseBarcode = DBInitialize.statement.executeQuery(getPurchaseBarcodeQuery);
		while(rsPurchaseBarcode.next()) {
			String barcode = rsPurchaseBarcode.getString(1);
			String qty = rsPurchaseBarcode.getString(2);
			
			String[] barcodeAry = barcode.split(",");
			String[] qtyAry = qty.split(",");
			for(int e = 0 ; e < barcodeAry.length; e++) {
				barcodeAryList.add(barcodeAry[e]);
				qtyAryList.add(qtyAry[e]);
			}
		}//end of while
		
		
		for(int i = 0 ; i < barcodeAryList.size(); i++) {
			String getCategoryQuery = "SELECT productcategory.name , productitems.price FROM productcategory, productitems WHERE productitems.barcode = '"+barcodeAryList.get(i)+"' AND productitems.categoryid = productcategory.id;";
			new DBInitialize();
			ResultSet rsBarcodeToGategory = DBInitialize.statement.executeQuery(getCategoryQuery);
			while(rsBarcodeToGategory.next()) {
				String categoryName = rsBarcodeToGategory.getString(1);
				String price = rsBarcodeToGategory.getString(2);
				
				for(int k = 0 ; k < categoryNameAry.length; k++) {
					
					if(categoryNameAry[k].equals(categoryName)) {
						categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i)); //set count
						categoryTotalAmountAry[k] = categoryTotalAmountAry[k] + Double.parseDouble(price) * Integer.parseInt(qtyAryList.get(i)) ;
					}//end of if
					
				}//end of inner for
				
			}//end of inner while
			
		}//end of for
		
		double initialsale = 0;
		double promotion = 0 ;
		
		/* List to hold Items */
		List<DailyReport> listItems = new ArrayList<DailyReport>();
		//listItems.add(new DailyReport("Sattionary", 4, 3000.0));
		for(int f = 0 ; f < categoryNameAry.length; f++) {
			DailyReport dr = new DailyReport();
			dr.setCategoryname(categoryNameAry[f]);
			dr.setSalecount(categoryCountAry[f]);
			dr.setCategorytotalamount(categoryTotalAmountAry[f]);
			listItems.add(dr);
			initialsale = initialsale + categoryTotalAmountAry[f]  ;
		}

		promotion = initialsale - totalAmount;
		
		

		
		/////////////////////////////////////////////////////////
		//for daily item sale table 
		//list
		ObservableList<String> barcodeList = FXCollections.observableArrayList();
		//ObservableList<String> quantityList = FXCollections.observableArrayList();
		
		//query for getting all the item data from db
		String getAllItemQuery = "SELECT `barcode` FROM `productitems`";
		new DBInitialize();
		ResultSet rsAllItem = DBInitialize.statement.executeQuery(getAllItemQuery);
		while(rsAllItem.next()) {
			barcodeList.add(rsAllItem.getString(1));
		}
		
		String[] barcodeAry = new String[barcodeList.size()];
		String[] barcodeCountAry = new String[barcodeList.size()];
		String[] barcodePriceAry = new String[barcodeList.size()];
		String[] barcodeCategoryAry = new String[barcodeList.size()];
		String[] barcodeNameAry = new String[barcodeList.size()];
		
		//add data to count 
		for( int b = 0 ; b < barcodeCountAry.length; b++) {
			barcodeCountAry[b] = "0";
			barcodeAry[b] = barcodeList.get(b);
		}
		
		//barcodeArrayList & qty array list
		ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
		ObservableList<String> qtyArrayList = FXCollections.observableArrayList();
		
		
		//query
		String getPurchaseTodayQuery = "SELECT `barcode`, `quantity` FROM `purchase` WHERE purchase.date = '"+today+"'";
		new DBInitialize();
		ResultSet rsPurchaseToday = DBInitialize.statement.executeQuery(getPurchaseTodayQuery);
		while(rsPurchaseToday.next()) {
			String barcodeTemp = (rsPurchaseToday.getString(1));
			String qtyTodayTemp = rsPurchaseToday.getString(2);
			
			String[] barcodeTempAry = barcodeTemp.split(",");
			String[] qtyTodayTempAry = qtyTodayTemp.split(",");
			
			for(int w = 0 ; w < barcodeTempAry.length; w++) {
				barcodeArrayList.add(barcodeTempAry[w]);
				qtyArrayList.add(qtyTodayTempAry[w]);
			}
			
		}//end of while
		
		for(int m =0 ; m < barcodeAryList.size(); m++) {
			String bCode = barcodeAryList.get(m);
			
			for(int l = 0; l < barcodeAry.length; l++) {
				if(bCode.equals(barcodeAry[l])){
					barcodeCountAry[l] = "" + (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)) );
				}//end of if
			}//end of inner for
		}//end of for
		
		
		//get category name and price in ordering
		for(int g = 0 ; g < barcodeAry.length; g ++) {
		String getCandPQuery = "SELECT productcategory.name , productitems.price, productitems.name FROM productitems, productcategory WHERE productitems.barcode = '"+barcodeAry[g]+"' AND productitems.categoryid = productcategory.id";
		new DBInitialize();
		ResultSet rsGetCandP = DBInitialize.statement.executeQuery(getCandPQuery);
		while(rsGetCandP.next()) {
		String cName = rsGetCandP.getString(1);
		String cPrice = rsGetCandP.getString(2);
		String pName = rsGetCandP.getString(3);
		
		barcodeCategoryAry[g] = cName;
		barcodePriceAry[g] = cPrice;
		barcodeNameAry[g] = pName;
		}//end of while
		
		}//end of for loop
		
		/*//check point
		System.out.println("checking ............");
		for(int i =0 ; i< barcodeAry.length ; i++) {
			System.out.print(barcodeAry[i]);
			System.out.print(barcodeCategoryAry[i]);
			System.out.print(barcodeNameAry[i]);
			System.out.print(barcodeCountAry[i]);
			
		}*/
		
		/* List to hold Items */
		List<DayItemTable> listItems1 = new ArrayList<DayItemTable>();
		
		//construct object from model 
		for(int v = 0 ; v < barcodeAry.length; v++) {
			DayItemTable dit = new DayItemTable();
			dit.setBarcode(barcodeAry[v]);
			dit.setCategory(barcodeCategoryAry[v]);
			dit.setName(barcodeNameAry[v]);
			dit.setSalecount(barcodeCountAry[v]);
			Double amount = Integer.parseInt(barcodeCountAry[v]+"") * Double.parseDouble(barcodePriceAry[v]); 
			dit.setSaleamount(amount+"");
			
			listItems1.add(dit);
		}
		
		/*--------------------------------------------------*/
		
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		
		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(listItems);
		JRBeanCollectionDataSource itemsJRBean1 = new JRBeanCollectionDataSource(listItems1);
		
		param.put("CategoryDataset", "HELLO");
		param.put("ItemDataset", "HELLO");
		param.put("DS", itemsJRBean);
		param.put("DSItem", itemsJRBean1);
		param.put("totalsaleamount", Double.parseDouble(""+totalAmount));
		param.put("totalsaleitemcount", Integer.parseInt(""+itemCount));
		param.put("cardcustomer", Integer.parseInt(""+totalCard));
		param.put("cashpaidcustomer", Integer.parseInt(""+totalCash));
		param.put("initialsale", ""+initialsale);
		param.put("netsale", ""+totalAmount);
		param.put("promotion",""+promotion );
		
		// Make sure the output directory exists.
		File outDir = new File("../Desktop/UCSMPOS");
		outDir.mkdirs();

		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/jaspertemplate/daily_report.jasper");
		
		//JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/jaspertemplate/daily_report.jasper");
		System.out.println("file is : " + new File("").getAbsolutePath() + "/src/jaspertemplate/daily_report.jasper");

		/* Using compiled version(.jasper) of Jasper report to generate PDF */
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"../Desktop/UCSMPOS/daily_report.pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle("UCSM POS System: Printing service");
		// jasperViewer.getIc

	}
	
	public void generatePopularItem() throws JRException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		BasicConfigurator.configure();
		
		 ObservableList<ProductItem> popularData = FXCollections.observableArrayList();
		
		 //get popular item  data from db
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
		

		HashMap<String, Object> param = new HashMap<String, Object>();

	/*	 List to hold Items 
		List<Sale> listItems = new ArrayList<Sale>();*/


		/* Convert List to JRBeanCollectionDataSource */
		JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(popularData);

		
		param.put("PopularDataset", "HELLO");
		param.put("DS1", itemsJRBean);
		
		

		// Make sure the output directory exists.
		File outDir = new File("../Desktop/UCSMPOS");
		outDir.mkdirs();

		//JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/jaspertemplate/popular_item_report.jasper");
	//	System.out.println("file is : " + new File("").getAbsolutePath() + "/src/jaspertemplate/popular_item_report.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/jaspertemplate/popular_item_report.jasper");
		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"../Desktop/UCSMPOS/Popular_item.pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle("UCSM POS System: Printing service");
	}
	
	
	
	
	
	
	
	
		/*-----------------------------------------------------------------------------------------------*/
	
	
	
	public void generateMonthlyReport() throws JRException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		
		
		BasicConfigurator.configure();

		// current date
		String pattern = "dd/MM/yyyy";
		String todayy = new SimpleDateFormat(pattern).format(new Date());
		System.out.println("Today is .......  " + todayy);
		
		//get month
		String [] todayAry = todayy.split("/");
		String currentMonth = todayAry[1] + "/" + todayAry[2];

		// get today total sale amount
		String getTotalSaleQuery = "SELECT `totalamount` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
		new DBInitialize().DBInitialize();
		new DBInitialize();
		ResultSet rsGetTotalAmount = DBInitialize.statement.executeQuery(getTotalSaleQuery);
		double totalAmount = 0;
		while(rsGetTotalAmount.next()) {
			totalAmount = totalAmount + Double.parseDouble(rsGetTotalAmount.getString(1));
		}

		
		//get today total sale count
		String getTotalItemCount = "SELECT `quantity` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'"; 
		new DBInitialize();
		ResultSet rsGetTotalItemCount = DBInitialize.statement.executeQuery(getTotalItemCount);
		int itemCount = 0;
		while(rsGetTotalItemCount.next()) {
			String tempCount = rsGetTotalItemCount.getString(1);
			
			String[] tempAry = tempCount.split(",");
			for( int i = 0 ; i < tempAry.length  ; i++) {
				System.out.println("count is : "+tempAry[i]);
				itemCount = itemCount + Integer.parseInt(tempAry[i]);
			}
		}
		
		System.out.println("Total sale item count : "+itemCount);
		
		
		//compute cash and card user for today
		//get total purchase
		String getTotalPurchaseCountQuery = "SELECT COUNT(*) FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
		int totalPurchase = 0;
		new DBInitialize();
		ResultSet rsTotalPurchaseCount = DBInitialize.statement.executeQuery(getTotalPurchaseCountQuery);
		while(rsTotalPurchaseCount.next()) {
			totalPurchase = rsTotalPurchaseCount.getInt(1);
		}
		
		//get total card user (from transaction table)
		String getCardUserCountQuery = "SELECT COUNT(*) FROM transaction, purchase WHERE transaction.purchaseid = purchase.id AND purchase.date LIKE '%"+currentMonth+"'";
		int totalCard = 0;
		new DBInitialize();
		ResultSet rsTotalCardUser = DBInitialize.statement.executeQuery(getCardUserCountQuery);
		while(rsTotalCardUser.next()) {
			totalCard = rsTotalCardUser.getInt(1);
			
		}
		int totalCash = totalPurchase - totalCard ; 
		
		
		
		
		//get category from db
		//get category list from db
		ObservableList<String> categoryList = FXCollections.observableArrayList();
		String getCagetoryListQuery = "SELECT  `name` FROM `productcategory`;";
		new DBInitialize();
		ResultSet rsCategory = DBInitialize.statement.executeQuery(getCagetoryListQuery);
		while(rsCategory.next()) {
			categoryList.add(rsCategory.getString(1));
		}
		
		//create array for category name, count , total sale amount 
		String [] categoryNameAry = new String[categoryList.size()];
		int [] categoryCountAry = new int [categoryList.size()];
		Double [] categoryTotalAmountAry = new Double[categoryList.size()];
		
		//add item to category name array
		for(int a = 0 ; a < categoryNameAry.length; a++) {
			categoryNameAry[a] = categoryList.get(a);
			categoryCountAry[a] = 0;
			categoryTotalAmountAry[a] = 0.0 ;
		}
		
		ObservableList<String> barcodeAryList = FXCollections.observableArrayList();
		ObservableList<String> qtyAryList = FXCollections.observableArrayList();
		
		//get barcode and find category type
		String getPurchaseBarcodeQuery = "SELECT `barcode`, `quantity` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
		new DBInitialize();
		ResultSet rsPurchaseBarcode = DBInitialize.statement.executeQuery(getPurchaseBarcodeQuery);
		while(rsPurchaseBarcode.next()) {
			String barcode = rsPurchaseBarcode.getString(1);
			String qty = rsPurchaseBarcode.getString(2);
			
			String[] barcodeAry = barcode.split(",");
			String[] qtyAry = qty.split(",");
			for(int e = 0 ; e < barcodeAry.length; e++) {
				barcodeAryList.add(barcodeAry[e]);
				qtyAryList.add(qtyAry[e]);
			}
		}//end of while
		
		
		for(int i = 0 ; i < barcodeAryList.size(); i++) {
			String getCategoryQuery = "SELECT productcategory.name , productitems.price FROM productcategory, productitems WHERE productitems.barcode = '"+barcodeAryList.get(i)+"' AND productitems.categoryid = productcategory.id;";
			new DBInitialize();
			ResultSet rsBarcodeToGategory = DBInitialize.statement.executeQuery(getCategoryQuery);
			while(rsBarcodeToGategory.next()) {
				String categoryName = rsBarcodeToGategory.getString(1);
				String price = rsBarcodeToGategory.getString(2);
				
				for(int k = 0 ; k < categoryNameAry.length; k++) {
					
					if(categoryNameAry[k].equals(categoryName)) {
						categoryCountAry[k] = categoryCountAry[k] + Integer.parseInt(qtyAryList.get(i)); //set count
						categoryTotalAmountAry[k] = categoryTotalAmountAry[k] + Double.parseDouble(price) * Integer.parseInt(qtyAryList.get(i)) ;
					}//end of if
					
				}//end of inner for
				
			}//end of inner while
			
		}//end of for
		
		double initialsale = 0;
		double promotion = 0 ;
		
		/* List to hold Items */
		List<DailyReport> listItems = new ArrayList<DailyReport>();
		//listItems.add(new DailyReport("Sattionary", 4, 3000.0));
		for(int f = 0 ; f < categoryNameAry.length; f++) {
			DailyReport dr = new DailyReport();
			dr.setCategoryname(categoryNameAry[f]);
			dr.setSalecount(categoryCountAry[f]);
			dr.setCategorytotalamount(categoryTotalAmountAry[f]);
			listItems.add(dr);
			initialsale = initialsale + categoryTotalAmountAry[f]  ;
		}

		promotion = initialsale - totalAmount;
		
		

		
		/////////////////////////////////////////////////////////
		//for daily item sale table 
		//list
		ObservableList<String> barcodeList = FXCollections.observableArrayList();
		//ObservableList<String> quantityList = FXCollections.observableArrayList();
		
		//query for getting all the item data from db
		String getAllItemQuery = "SELECT `barcode` FROM `productitems`";
		new DBInitialize();
		ResultSet rsAllItem = DBInitialize.statement.executeQuery(getAllItemQuery);
		while(rsAllItem.next()) {
			barcodeList.add(rsAllItem.getString(1));
		}
		
		String[] barcodeAry = new String[barcodeList.size()];
		String[] barcodeCountAry = new String[barcodeList.size()];
		String[] barcodePriceAry = new String[barcodeList.size()];
		String[] barcodeCategoryAry = new String[barcodeList.size()];
		String[] barcodeNameAry = new String[barcodeList.size()];
		
		//add data to count 
		for( int b = 0 ; b < barcodeCountAry.length; b++) {
			barcodeCountAry[b] = "0";
			barcodeAry[b] = barcodeList.get(b);
		}
		
		//barcodeArrayList & qty array list
		ObservableList<String> barcodeArrayList = FXCollections.observableArrayList();
		ObservableList<String> qtyArrayList = FXCollections.observableArrayList();
		
		
		//query
		String getPurchaseTodayQuery = "SELECT `barcode`, `quantity` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
		new DBInitialize();
		ResultSet rsPurchaseToday = DBInitialize.statement.executeQuery(getPurchaseTodayQuery);
		while(rsPurchaseToday.next()) {
			String barcodeTemp = (rsPurchaseToday.getString(1));
			String qtyTodayTemp = rsPurchaseToday.getString(2);
			
			String[] barcodeTempAry = barcodeTemp.split(",");
			String[] qtyTodayTempAry = qtyTodayTemp.split(",");
			
			for(int w = 0 ; w < barcodeTempAry.length; w++) {
				barcodeArrayList.add(barcodeTempAry[w]);
				qtyArrayList.add(qtyTodayTempAry[w]);
			}
			
		}//end of while
		
		for(int m =0 ; m < barcodeAryList.size(); m++) {
			String bCode = barcodeAryList.get(m);
			
			for(int l = 0; l < barcodeAry.length; l++) {
				if(bCode.equals(barcodeAry[l])){
					barcodeCountAry[l] = "" + (Integer.parseInt(barcodeCountAry[l]) + Integer.parseInt(qtyArrayList.get(m)) );
				}//end of if
			}//end of inner for
		}//end of for
		
		
		//get category name and price in ordering
		for(int g = 0 ; g < barcodeAry.length; g ++) {
		String getCandPQuery = "SELECT productcategory.name , productitems.price, productitems.name FROM productitems, productcategory WHERE productitems.barcode = '"+barcodeAry[g]+"' AND productitems.categoryid = productcategory.id";
		new DBInitialize();
		ResultSet rsGetCandP = DBInitialize.statement.executeQuery(getCandPQuery);
		while(rsGetCandP.next()) {
		String cName = rsGetCandP.getString(1);
		String cPrice = rsGetCandP.getString(2);
		String pName = rsGetCandP.getString(3);
		
		barcodeCategoryAry[g] = cName;
		barcodePriceAry[g] = cPrice;
		barcodeNameAry[g] = pName;
		}//end of while
		
		}//end of for loop
		
		//check point
		System.out.println("checking ............");
		for(int i =0 ; i< barcodeAry.length ; i++) {
			System.out.print(barcodeAry[i]);
			System.out.print(barcodeCategoryAry[i]);
			System.out.print(barcodeNameAry[i]);
			System.out.print(barcodeCountAry[i]);
			
		}
		
		/* List to hold Items */
		List<DayItemTable> listItems1 = new ArrayList<DayItemTable>();
		
		//construct object from model 
		for(int v = 0 ; v < barcodeAry.length; v++) {
			DayItemTable dit = new DayItemTable();
			dit.setBarcode(barcodeAry[v]);
			dit.setCategory(barcodeCategoryAry[v]);
			dit.setName(barcodeNameAry[v]);
			dit.setSalecount(barcodeCountAry[v]);
			Double amount = Integer.parseInt(barcodeCountAry[v]+"") * Double.parseDouble(barcodePriceAry[v]); 
			dit.setSaleamount(amount+"");
			
			listItems1.add(dit);
		}
		
		/*--------------------------------------------------*/
		
		//for daily sale items
		//days array
		String[] daysAry = new String[31];
		String[] daysAmountAry = new String[31];
		String[] daysCountAry = new String[31];
		
		//set data to  arrays
		for(int z =0 ; z < daysAry.length; z++) {
			daysAry[z] = String.format("%02d", (z+1));
			daysAmountAry[z] = 0+""; 
			daysCountAry[z] = "0";
		}
		
		//get daily purchase in this month
		String getDailyPurchaseQuery = "SELECT `date`, `totalamount` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
		new DBInitialize();
		ResultSet rsDailyPurchase = DBInitialize.statement.executeQuery(getDailyPurchaseQuery);
		while(rsDailyPurchase.next()) {
			String dateTemp = rsDailyPurchase.getString(1);
			String totalAmountTemp = rsDailyPurchase.getString(2);
			
			String[] dateTempAry = dateTemp.split("/");
			//String totalAmountTempAry 
			
			for(int r =0 ; r < daysAry.length; r++) {
				if(dateTempAry[0].equals(daysAry[r])) {
					daysAmountAry[r] = "" + (Double.parseDouble(daysAmountAry[r]) + Double.parseDouble(totalAmountTemp));
				}
			}
		}
		
		//get the total count , initial sale and discount
		for(int s =0 ; s < daysAry.length; s++) {
			//get totalCount
			String getTotalCountQueryy = "SELECT `quantity` FROM `purchase` WHERE purchase.date = '"+daysAry[s]+"/"+currentMonth+"'";
			new DBInitialize();
			ResultSet rsTotalCountt = DBInitialize.statement.executeQuery(getTotalCountQueryy);
			while(rsTotalCountt.next() ) {
				String coulntTemppp = rsTotalCountt.getString(1);
				String[] countTempAryyy = coulntTemppp.split(",");
				for(int c =0 ; c < countTempAryyy.length; c++) {
					daysCountAry[s] = "" + ( Integer.parseInt(daysCountAry[s]) + Integer.parseInt(countTempAryyy[c]));
				}//end of inner for
				
			}//end of while
			
			/*//get price and compute initial sale
			String getDailyBarcodeQuery = "SELECT `date`, `barcode` FROM `purchase` WHERE purchase.date LIKE '%"+currentMonth+"'";
			new DBInitialize();
			ResultSet rsDailyBarcode = DBInitialize.statement.executeQuery(getDailyBarcodeQuery);
			while(rsDailyBarcode.next()) {
				String dailyDate = rsDailyBarcode.getString(1);
				String Dailybarcode = rsDailyBarcode.getString(2);
				
			} */
			
			
		}//end of for
		
		
		
		/* List to hold Items */
		List<DailyTable> listItems2 = new ArrayList<DailyTable>();
		
		//adding data object
		for(int q =0; q < daysAry.length; q ++) {
			DailyTable dat = new DailyTable();
			dat.setDate(daysAry[q]+"/"+currentMonth);
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
		
		param.put("CategoryDataset", "HELLO");
		param.put("ItemDataset", "HELLO");
		param.put("Dataset1", "HELLO");
		param.put("DS", itemsJRBean);
		param.put("DSItem", itemsJRBean1);
		param.put("DS1", itemsJRBean2);
		param.put("totalsaleamount", Double.parseDouble(""+totalAmount));
		param.put("totalsaleitemcount", Integer.parseInt(""+itemCount));
		param.put("cardcustomer", Integer.parseInt(""+totalCard));
		param.put("cashpaidcustomer", Integer.parseInt(""+totalCash));
		param.put("initialsale", ""+initialsale);
		param.put("netsale", ""+totalAmount);
		param.put("promotion",""+promotion );
		param.put("categorysale","Item Sale" );
		
		// Make sure the output directory exists.
		File outDir = new File("../Desktop/UCSMPOS");
		outDir.mkdirs();

		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File("").getAbsolutePath() +"/src/jaspertemplate/monthly_report.jasper");
		
		//JasperDesign jasperDesign = JRXmlLoader.load(new File("").getAbsolutePath() + "/src/jaspertemplate/monthly_report.jasper");
	//	System.out.println("file is : " + new File("").getAbsolutePath() + "/src/jaspertemplate/monthly_report.jasper");

		// OutputStream outputfile = new FileOutputStream(new
		// File("/Users/tylersai/Desktop/jaspervoucher.pdf"));

		//JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());
		// JasperExportManager.exportReportToPdfStream(jasperPrint, outputfile);

		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		ExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
		// ExporterInput
		exporter.setExporterInput(exporterInput);

		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"../Desktop/UCSMPOS/monthly_report.pdf");
		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
		jasperViewer.setVisible(true);
		jasperViewer.setFitPageZoomRatio();
		jasperViewer.setTitle("UCSM POS System: Printing service");
		
		
	}
}