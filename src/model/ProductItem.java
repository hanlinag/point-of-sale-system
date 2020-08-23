package model;

public class ProductItem {
	
	String name;
	String categoryid;
	String categoryname;
	String dateadded;
	String expiredate;
	String price;
	String barcode;
	String supplierid;
	String suppliername;
	String stockamount;
	String discount;
	String discountmore;
	int count ;

	public ProductItem(String name, String categoryid, String categoryname, String dateadded, String expiredate,
			String price, String barcode, String supplierid, String suppliername, String stockamount) {
		super();
		this.name = name;
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.dateadded = dateadded;
		this.expiredate = expiredate;
		this.price = price;
		this.barcode = barcode;
		this.supplierid = supplierid;
		this.suppliername = suppliername;
		this.stockamount = stockamount;
	}

	public ProductItem(String name, String categoryid, String dateadded, String expiredate,
			String price, String barcode, String supplierid, String stockamount) {
		super();
		this.name = name;
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.dateadded = dateadded;
		this.expiredate = expiredate;
		this.price = price;
		this.barcode = barcode;
		this.supplierid = supplierid;
		this.suppliername = suppliername;
		this.stockamount = stockamount;
	}



	public String getDiscountmore() {
		return discountmore;
	}

	public void setDiscountmore(String discountmore) {
		this.discountmore = discountmore;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductItem() {
		
	}


	

	public String getCategoryname() {
		return categoryname;
	}




	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}




	public String getSuppliername() {
		return suppliername;
	}




	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}




	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}


	public String getDateadded() {
		return dateadded;
	}


	public void setDateadded(String dateadded) {
		this.dateadded = dateadded;
	}


	public String getExpiredate() {
		return expiredate;
	}


	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getBarcode() {
		return barcode;
	}


	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getSupplierid() {
		return supplierid;
	}


	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}


	public String getStockamount() {
		return stockamount;
	}


	public void setStockamount(String stockamount) {
		this.stockamount = stockamount;
	}


	
}
