package model;

public class Supplier {
	
	String id;
	String name;
	String lastSupplied;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastSupplied() {
		return lastSupplied;
	}
	public void setLastSupplied(String lastSupplied) {
		this.lastSupplied = lastSupplied;
	}
	
	public Supplier(String id, String name, String lastSupplied) {
		super();
		this.id = id;
		this.name = name;
		this.lastSupplied = lastSupplied;
	}
	
	public Supplier() {
		
	}

}
