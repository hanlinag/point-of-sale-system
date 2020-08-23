package model;

public class Category {
	String id;
	String name;
	String dateCreated;
	
	public Category(String id, String name, String dateCreated) {
		super();
		this.id = id;
		this.name = name;
		this.dateCreated = dateCreated;
	}
	
	public Category() {
		
	}

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

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	
}
