package controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import common.Common;
import database.DBInitialize;
import functs.ReportGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.CardUser;
import net.sf.jasperreports.engine.JRException;

public class CardPayController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField tf_purchase_id;

    @FXML
    private JFXTextField tf_total_amount;

    @FXML
    private JFXTextField tf_card_no;

    @FXML
    private JFXButton bt_pay;
    
    @FXML
    private JFXTextField tf_pay_amount;

    String purchasedate;
   
    String cardno;
    
    private double payamount;
    double cardamount;

    @FXML
    void initialize() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        assert tf_purchase_id != null : Messages.getString("CardPayController.0"); //$NON-NLS-1$
        assert tf_total_amount != null : Messages.getString("CardPayController.1"); //$NON-NLS-1$
        assert tf_card_no != null : Messages.getString("CardPayController.2"); //$NON-NLS-1$
        assert bt_pay != null : Messages.getString("CardPayController.3"); //$NON-NLS-1$
        assert tf_pay_amount != null : Messages.getString("CardPayController.4"); //$NON-NLS-1$

        
        tf_card_no.setEditable(true);
        tf_pay_amount.setEditable(false);
       
        /*new Thread(() -> {
        	try {
				ServerSocket ss = new ServerSocket(5000);
			    System.out.println("Server is running at port : 5000");
        
        		while(true) {
        			Socket s = ss.accept();
        			DataInputStream inputFromClient = new DataInputStream( s.getInputStream());
    				
        			String message =inputFromClient.readUTF();
                    System.out.println("Received from android: " + message);
                   // inputFromClient.close();
                   // s.close();
    				
    				
    				Platform.runLater(()->
    					tf_card_no.setText(""+message));
    				
    	        }
	        	 
        	}
        	catch(Exception ex) {
        		
        	}
        }).start();
*/
        tf_total_amount.setText(Messages.getString("CardPayController.5")+Common.totalAmount); //$NON-NLS-1$
        
        //get purchase id from previous row
        //get previous id and create now id
  		new DBInitialize().DBInitialize();
  		String previousgetpurchaseid = Messages.getString("CardPayController.6"); //$NON-NLS-1$
  		new DBInitialize();
  		ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid);
  		String previousid = Messages.getString("CardPayController.7"); //$NON-NLS-1$
  		while(rs.next()) {
  			previousid = rs.getString(Messages.getString("CardPayController.8")); //$NON-NLS-1$
  		}
  		int nowid = Integer.parseInt(previousid) + 1;
  		
  		tf_purchase_id.setText(Messages.getString("CardPayController.9")+nowid); //$NON-NLS-1$
  		Common.purchaseid = nowid;
  		
  		tf_card_no.setOnAction(e->{
  			 cardno = tf_card_no.getText().toString();
  			String getCardInfoQuery = Messages.getString("CardPayController.10")+cardno+Messages.getString("CardPayController.11"); //$NON-NLS-1$ //$NON-NLS-2$
  			
  			CardUser c = new CardUser();
  			try {
				new DBInitialize().DBInitialize();
				new DBInitialize();
				ResultSet rsc = DBInitialize.statement.executeQuery(getCardInfoQuery);
				if(rsc.next()) {
					
					c.setCardno(rsc.getString(1));
					c.setCustomrid(rsc.getString(2));
					c.setAmount(rsc.getString(3));
					c.setLastdateused(rsc.getString(4));
					c.setRegisterdate(rsc.getString(5));
					c.setExpireddate(rsc.getString(6));
					c.setPin(Messages.getString("CardPayController.12")+rsc.getInt(7)); //$NON-NLS-1$
					
					
					double totalamount = Double.parseDouble(tf_total_amount.getText().toString());
		  			 cardamount = Double.parseDouble(c.getAmount());
		  			
		  			if(totalamount> cardamount) {
		  				Alert al = new Alert(AlertType.ERROR, Messages.getString("CardPayController.13")); //$NON-NLS-1$
						al.showAndWait();
		  			}
		  			else {
		  				
		  				double tominus = (totalamount) * (0.15);
		  				System.out.println(Messages.getString("CardPayController.14")+tominus); //$NON-NLS-1$
		  				 payamount = totalamount - tominus;
		  				System.out.println(Messages.getString("CardPayController.15")+payamount); //$NON-NLS-1$
		  				tf_pay_amount.setText(Messages.getString("CardPayController.16")+payamount); //$NON-NLS-1$
		  				Common.payamount = payamount;
		  			}
		  			///
				}else {
					Alert al = new Alert(AlertType.ERROR, Messages.getString("CardPayController.17")); //$NON-NLS-1$
					al.showAndWait();
				}
				
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
  			
  			
  		});
        
    }
    
    @SuppressWarnings("deprecation")
	@FXML
    void onbtPayAction(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
 
    	
    	/*ObservableList<Sale> saledata = FXCollections.observableArrayList();
    	saledata = Common.saleitemsdatafromsaletable;*/
    	
    	if(tf_pay_amount.getText().isEmpty()) {
    		//do nothing
    	}else {
    		
    		String cardinfoforprint = tf_card_no.getText().toString();
    		Common.cardinfo = Messages.getString("CardPayController.18")+ cardinfoforprint.substring(cardinfoforprint.length() - 4); //$NON-NLS-1$
    	
    	
    	//get purchase id from previous row
        //get previous id and create now id
  		new DBInitialize().DBInitialize();
  		String previousgetpurchaseid = Messages.getString("CardPayController.19"); //$NON-NLS-1$
  		new DBInitialize();
  		ResultSet rs = DBInitialize.statement.executeQuery(previousgetpurchaseid);
  		String previousid = Messages.getString("CardPayController.20"); //$NON-NLS-1$
  		while(rs.next()) {
  			previousid = rs.getString(Messages.getString("CardPayController.21")); //$NON-NLS-1$
  		}
  		int nowid = Integer.parseInt(previousid) + 1;
  		System.out.println(Messages.getString("CardPayController.22")+nowid); //$NON-NLS-1$
  		Common.purchaseid = nowid;
  		
  		//create today date
  		String pattern = Messages.getString("CardPayController.23"); //$NON-NLS-1$
		 purchasedate =new SimpleDateFormat(pattern).format(new Date());
		System.out.println(Messages.getString("CardPayController.24")+purchasedate); //$NON-NLS-1$
		
		//create today current time(purhcase time)
		 @SuppressWarnings("deprecation")
		String hour = Messages.getString("CardPayController.25")+new Date().getHours(); //$NON-NLS-1$
		 String min = Messages.getString("CardPayController.26")+new Date().getMinutes(); //$NON-NLS-1$
		String time  = hour+Messages.getString("CardPayController.27")+min; //$NON-NLS-1$
	    System.out.println(Messages.getString("CardPayController.28")+time); //$NON-NLS-1$
		
	   
	    
		//db
    	new DBInitialize().DBInitialize();
    	new DBInitialize();
    	String querycreatepurchase = Messages.getString("CardPayController.29") //$NON-NLS-1$
    			+ Messages.getString("CardPayController.30")+Common.purchaseid+Messages.getString("CardPayController.31")+purchasedate+Messages.getString("CardPayController.32")+time+Messages.getString("CardPayController.33")+Common.cashierrec.getId()+Messages.getString("CardPayController.34")+Common.productids+Messages.getString("CardPayController.35")+Common.productqtys+Messages.getString("CardPayController.36")+payamount+Messages.getString("CardPayController.37"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
    	DBInitialize.statement.executeUpdate(querycreatepurchase);
    	
    	
    	//get old transaction id and count +1 for new INSERT
    	String getTranasctionIDQuery = Messages.getString("CardPayController.38"); //$NON-NLS-1$
    	new DBInitialize();
    	ResultSet rstid = DBInitialize.statement.executeQuery(getTranasctionIDQuery);
    	String oldtid = Messages.getString("CardPayController.39"); //$NON-NLS-1$
    	while (rstid.next()) {
    		oldtid = rstid.getString(1);
    	}
    	String newtid = Messages.getString("CardPayController.40")+(Integer.parseInt(oldtid) + 1); //$NON-NLS-1$
    	
    	//add to transaction table
    	String addtransactionquery = Messages.getString("CardPayController.41")+newtid+Messages.getString("CardPayController.42")+Common.cashierrec.getId()+Messages.getString("CardPayController.43")+cardno+Messages.getString("CardPayController.44")+Common.purchaseid+Messages.getString("CardPayController.45")+payamount+Messages.getString("CardPayController.46"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    	new DBInitialize();
    	DBInitialize.statement.executeUpdate(addtransactionquery);
    	
    	//compute the new card balance amount
    	double newbalance = cardamount - payamount;
    	String reductcardmoneyquery = Messages.getString("CardPayController.47")+newbalance+Messages.getString("CardPayController.48")+purchasedate+Messages.getString("CardPayController.49")+cardno+Messages.getString("CardPayController.50"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    	new DBInitialize();
    	DBInitialize.statement.executeUpdate(reductcardmoneyquery);
    	
    	
    	//update count and stock amount
    	//get all the purchase id and count +1 in db
    	String[] purchasedproductitemsid = Common.productids.split(Messages.getString("CardPayController.51")); //$NON-NLS-1$
    	String[] purchasedproductitemsqty = Common.productqtys.split(Messages.getString("CardPayController.52")); //$NON-NLS-1$
    	
    	for(int i=0; i< purchasedproductitemsid.length; i++) {
    		int oldcount = 0 ;
    		int newcount = 0;
    		//get old count
    		String getOldCountQuery = Messages.getString("CardPayController.53")+purchasedproductitemsid[i]+Messages.getString("CardPayController.54"); //$NON-NLS-1$ //$NON-NLS-2$
    		System.out.println(Messages.getString("CardPayController.55")+purchasedproductitemsid[i]); //$NON-NLS-1$
    		new DBInitialize().DBInitialize();
    		new DBInitialize();
    		ResultSet rsoldc = DBInitialize.statement.executeQuery(getOldCountQuery);
    		while(rsoldc.next()) {
    			oldcount = rsoldc.getInt(1);
    		}
    		System.out.println(Messages.getString("CardPayController.56")+oldcount); //$NON-NLS-1$
    		
    		newcount  = oldcount + Integer.parseInt(purchasedproductitemsqty[i]);
    		System.out.println(Messages.getString("CardPayController.57")+newcount+Messages.getString("CardPayController.58")+purchasedproductitemsqty[i]); //$NON-NLS-1$ //$NON-NLS-2$
    		String updatecountQuery = Messages.getString("CardPayController.59")+newcount+Messages.getString("CardPayController.60")+purchasedproductitemsid[i]+Messages.getString("CardPayController.61"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    		new DBInitialize().DBInitialize();
    		new DBInitialize();
    		DBInitialize.statement.executeUpdate(updatecountQuery);
    		
    		//get stock amount
    		String getstockquery = Messages.getString("CardPayController.62")+purchasedproductitemsid[i]+Messages.getString("CardPayController.63"); //$NON-NLS-1$ //$NON-NLS-2$
    		String oldstock = Messages.getString("CardPayController.64");  //$NON-NLS-1$
    		new DBInitialize();
    		ResultSet rsst = DBInitialize.statement.executeQuery(getstockquery);
    		while(rsst.next()) {
    			oldstock = rsst.getString(1);
    		}
    		String newstock = Messages.getString("CardPayController.65")+(Integer.parseInt(oldstock) - Integer.parseInt(purchasedproductitemsqty[i])); //$NON-NLS-1$
    		//update stock
    		String updatestockquery = Messages.getString("CardPayController.66")+newstock+Messages.getString("CardPayController.67")+purchasedproductitemsid[i]+Messages.getString("CardPayController.68"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    		new DBInitialize();
    		DBInitialize.statement.executeUpdate(updatestockquery);
    	
    	}//end of for
    	
    	/*System.out.println("qty is: ");
    	for(int i = 0; i< purchasedproductitemsqty.length ;  i++) {
    		System.out.println(""+purchasedproductitemsqty[i]);
    	}
    	System.out.println("length of id and qty are : "+purchasedproductitemsid.length+"  & "+purchasedproductitemsqty.length);*/
    	//MainCashierController.clearsaletableitems();
    	((Stage)bt_pay.getScene().getWindow()).close();
    	//alert
    	Alert trancompleteal = new Alert(AlertType.INFORMATION, Messages.getString("CardPayController.69")+payamount+Messages.getString("CardPayController.70")); //$NON-NLS-1$ //$NON-NLS-2$
    	trancompleteal.showAndWait();
    
    	
    	//generate report
    	try {
			new ReportGenerator().generatevoucher(Common.saleitemsdatafromsaletable);
			//trancompleteal.close();
		} catch (JRException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	}//end of if check tfamount is empty
    }
}

