package model;

public class DailyTable {

	String date;
	String itemcount;
	String netsale;

	public DailyTable(String date, String itemcount, String netsale) {
		super();
		this.date = date;
		this.itemcount = itemcount;
		this.netsale = netsale;
	}

	public DailyTable() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getItemcount() {
		return itemcount;
	}

	public void setItemcount(String itemcount) {
		this.itemcount = itemcount;
	}

	public String getNetsale() {
		return netsale;
	}

	public void setNetsale(String netsale) {
		this.netsale = netsale;
	}

}
