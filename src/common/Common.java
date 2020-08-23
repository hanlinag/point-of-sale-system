package common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cashier;
import model.Sale;

public class Common {
	
	
	public static Cashier cashierrec = new Cashier();
	public static double totalAmount ;
	public static double change;
	public static double payamount;
	public static int purchaseid;
	public static ObservableList<String> buygetdata = FXCollections.observableArrayList();
	public static ObservableList<Sale> saleitemsdatafromsaletable = FXCollections.observableArrayList();
	public static String productids;
	public static String productqtys;
	public static String slipno;
	public static String paidtype = "";
	public static String buygetpromo = "";
	public static String buygetitem = "";
	public static String cardinfo ="";
	public static boolean isIntegratedDevice = false;

}
