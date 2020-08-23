package model;

public class Promotion {

	String id;
	String name;
	String productId;
	String percentage;
	String productName;
	String more;
	
	public Promotion(String id, String name, String productId,String productName, String percentage, String more) {
		super();
		this.id = id;
		this.name = name;
		this.productId = productId;
		this.productName = productName;
		this.percentage = percentage;
		this.more = more;
	}
	
	public Promotion() {
		
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
}
