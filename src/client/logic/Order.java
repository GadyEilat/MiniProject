package client.logic;

public class Order {

	private String parkName;
	private String hour;
	private String date;
	private String email;
	private String orderNumber;

	public Order(String parkName, String hour, String date, String email, String orderNumber) {
		this.parkName = parkName;
		this.hour = hour;
		this.date = date;
		this.email = email;
		this.orderNumber = orderNumber;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

//	@Override
//	public String toString() {
//		return String.format("%s %s %s %s %s", id, fname, lname, email, teln);
//	}
	
}
