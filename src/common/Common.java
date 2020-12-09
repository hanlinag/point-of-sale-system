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
	public static String paidtype = Messages.getString("Common.0"); //$NON-NLS-1$
	public static String buygetpromo = Messages.getString("Common.1"); //$NON-NLS-1$
	public static String buygetitem = Messages.getString("Common.2"); //$NON-NLS-1$
	public static String cardinfo =Messages.getString("Common.3"); //$NON-NLS-1$
	public static boolean isIntegratedDevice = false;

}
