package client.logic;

import java.io.Serializable;

public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parkName;
	private String hour;
	private String date;
	private String email;
	private String orderNumber;
	private String numOfVisitors;
	private String nameOnOrder;
	private String ID;
	private String totalPrice;
	private String orderKind;

	public Order(String parkName, String hour, String date, String email, String orderNumber, String numOfVisitors, String nameOnOrder, String ID) {
		this.parkName = parkName;
		this.hour = hour;
		this.date = date;
		this.email = email;
		this.orderNumber = orderNumber;
		this.numOfVisitors=numOfVisitors;
		this.nameOnOrder=nameOnOrder;
		this.ID=ID;
	}
	
	public Order(String parkName, String hour, String date, String email, String orderNumber, String numOfVisitors,
			String nameOnOrder, String ID, String totalPrice, String orderKind) {
		super();
		this.parkName = parkName;
		this.hour = hour;
		this.date = date;
		this.email = email;
		this.orderNumber = orderNumber;
		this.numOfVisitors = numOfVisitors;
		this.nameOnOrder = nameOnOrder;
		this.ID = ID;
		this.totalPrice = totalPrice;
		this.orderKind=orderKind;
	}
	
	public String getOrderKind() {
		return orderKind;
	}

	public void setOrderKind(String orderKind) {
		this.orderKind = orderKind;
	}

	public Order() {
		
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNameOnOrder() {
		return nameOnOrder;
	}

	public void setNameOnOrder(String nameOnOrder) {
		this.nameOnOrder = nameOnOrder;
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
