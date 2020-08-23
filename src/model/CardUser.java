package model;

public class CardUser {
	String customrid ;
	String name;
	String age;
	String gender;
	String address;
	String phone;
	String email;
	String pin;
	String amount;
	String cardno;
	String lastdateused;
	String expireddate;
	String registerdate;
	
	
	

	public CardUser(String customrid, String name, String age, String gender, String address, String phone,
			String email, String pin, String amount, String cardno, String lastdateused, String expireddate,
			String registerdate) {
		super();
		this.customrid = customrid;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.pin = pin;
		this.amount = amount;
		this.cardno = cardno;
		this.lastdateused = lastdateused;
		this.expireddate = expireddate;
		this.registerdate = registerdate;
	}

	public CardUser() {
		
	}

	public String getCustomrid() {
		return customrid;
	}

	public void setCustomrid(String customrid) {
		this.customrid = customrid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getLastdateused() {
		return lastdateused;
	}

	public void setLastdateused(String lastdateused) {
		this.lastdateused = lastdateused;
	}

	public String getExpireddate() {
		return expireddate;
	}

	public void setExpireddate(String expireddate) {
		this.expireddate = expireddate;
	}

	public String getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(String registerdate) {
		this.registerdate = registerdate;
	}

	
	

}
