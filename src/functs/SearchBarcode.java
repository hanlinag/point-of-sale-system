package functs;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBInitialize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ProductItem;

public class SearchBarcode {
	private static ObservableList<ProductItem> itemsdata;
	public static ObservableList<ProductItem> SearchByBarcode(String key) {
		
		//search by barcode action
    	//String searchKey = 	tf_barcode_search.getText().toString();
    	//System.out.println("key entered is : "+key);
    	String query = Messages.getString("SearchBarcode.0")+key+Messages.getString("SearchBarcode.1"); //$NON-NLS-1$ //$NON-NLS-2$
    	
    	
    //new DBInitialize().DBInitialize();
    	
    	itemsdata =FXCollections.observableArrayList();
    	itemsdata.clear();
    	System.out.println(Messages.getString("SearchBarcode.2")+key); //$NON-NLS-1$
    	try {
            //ResultSet rs = st.executeQuery("SELECT * FROM USER");
    		ResultSet rs = DBInitialize.statement.executeQuery(query);
    		ObservableList<ProductItem> row = FXCollections.observableArrayList();
             if (rs.next()) {
                 
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
             else {
            	 return null;
             }
             
             itemsdata.addAll(row);
	//System.out.println("working1"+data);
	
	//tb_total_item.getItems().clear();
	//tb_total_item.setItems(data);
    	
	
	System.out.println(Messages.getString("SearchBarcode.3")+itemsdata.get(0).getName()); //$NON-NLS-1$
		//data.getItems().addAll(row);
    	}catch (SQLException ex) {
            
        }
		return itemsdata;
	}

}
