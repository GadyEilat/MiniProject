package client.logic;

public class Order {

	private String parkName;
	private String hour;
	private String date;
	private String email;
	private String orderNumber;
	private String numOfVisitors;

	public Order(String parkName, String hour, String date, String email, String orderNumber, String numOfVisitors) {
		this.parkName = parkName;
		this.hour = hour;
		this.date = date;
		this.email = email;
		this.orderNumber = orderNumber;
		this.numOfVisitors=numOfVisitors;
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
	
	public String getNumOfVisitors() {
		return numOfVisitors;
	}
	public void setNumOfVisitors(String numOfVisitors) {
		this.numOfVisitors = numOfVisitors;
	}

//	@Override
//	public String toString() {
//		return String.format("%s %s %s %s %s", id, fname, lname, email, teln);
//	}
	
}
