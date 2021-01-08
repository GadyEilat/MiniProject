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
	private String OrderKind;
	private String prePaid;
	private String approved;
	

	public Order(String parkName, String hour, String date, String email, String orderNumber, String numOfVisitors,
			String nameOnOrder, String iD, String totalPrice, String orderkind, String prepaid, String approved) {
		this.parkName = parkName;
		this.hour = hour;
		this.date = date;
		this.email = email;
		this.orderNumber = orderNumber;
		this.numOfVisitors = numOfVisitors;
		this.nameOnOrder = nameOnOrder;
		this.ID = iD;
		this.totalPrice = totalPrice;
		this.OrderKind=orderkind;
		this.prePaid=prepaid;
		this.approved=approved;
	}

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
	
	public Order() {
		
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

	public String getTotalPrice() {
		return totalPrice;
	}

	public String getOrderKind() {
		return OrderKind;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setOrderKind(String orderKind) {
		OrderKind = orderKind;
	}

	public String getPrePaid() {
		return prePaid;
	}

	public void setPrePaid(String prePaid) {
		this.prePaid = prePaid;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	
	
	
	

//	@Override
//	public String toString() {
//		return String.format("%s %s %s %s %s", id, fname, lname, email, teln);
//	}
	
}
