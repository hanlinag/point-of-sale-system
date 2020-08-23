package model;

public class DayItemTable {
	String barcode;
	String name;
	String category;
	String salecount;
	String saleamount;

	public DayItemTable(String barcode, String name, String category, String salecount, String saleamount) {
		super();
		this.barcode = barcode;
		this.name = name;
		this.category = category;
		this.salecount = salecount;
		this.saleamount = saleamount;
	}

	public DayItemTable() {
		super();
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSalecount() {
		return salecount;
	}

	public void setSalecount(String salecount) {
		this.salecount = salecount;
	}

	public String getSaleamount() {
		return saleamount;
	}

	public void setSaleamount(String saleamount) {
		this.saleamount = saleamount;
	}

}
