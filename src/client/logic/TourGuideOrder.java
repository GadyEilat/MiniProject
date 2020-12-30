package client.logic;

import java.io.Serializable;

public class TourGuideOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String parkName;
	private String time;
	private String date;
	private String NumOfVisitors;
	private String email;
	private String orderNumber;
	private String nameOnOrder;
	private String ID;
	
	
	public TourGuideOrder(String parkName, String time, String date, String numOfVisitors, String email,
			String orderNumber, String nameOnOrder, String ID) {
		this.parkName = parkName;
		this.time = time;
		this.date = date;
		NumOfVisitors = numOfVisitors;
		this.email = email;
		this.orderNumber = orderNumber;
		this.nameOnOrder=nameOnOrder;
		this.ID=ID;
	}
	
	
	
	
	public String getID() {
		return ID;
	}




	public void setID(String iD) {
		ID = iD;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getParkName() {
		return parkName;
	}
	public String getTime() {
		return time;
	}
	public String getDate() {
		return date;
	}
	public String getNumOfVisitors() {
		return NumOfVisitors;
	}
	public String getEmail() {
		return email;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setNumOfVisitors(String numOfVisitors) {
		NumOfVisitors = numOfVisitors;
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
	public String getNameOnOrder() {
		return nameOnOrder;
	}
	public void setNameOnOrder(String nameOnOrder) {
		this.nameOnOrder = nameOnOrder;
	}
	
	
}
