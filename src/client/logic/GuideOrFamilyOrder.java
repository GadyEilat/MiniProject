package client.logic;

import java.io.Serializable;

public class GuideOrFamilyOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String parkName;
	private String time;
	private String date;
	private String NumOfVisitors;
	private String email;
	private String orderNumber;
	
	public GuideOrFamilyOrder(String parkName, String time, String date, String numOfVisitors, String email, String orderNumber) {
		this.parkName = parkName;
		this.time = time;
		this.date = date;
		NumOfVisitors = numOfVisitors;
		this.email = email;
		this.orderNumber = orderNumber;
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
}
