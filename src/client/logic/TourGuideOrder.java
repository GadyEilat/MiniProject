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
	private String prePaid;
	private String Payment;
	
	
	public TourGuideOrder(String parkName, String time, String date, String numOfVisitors, String email,
			String orderNumber, String nameOnOrder, String ID, String prepaid, String payment) {
		this.parkName = parkName;
		this.time = time;
		this.date = date;
		NumOfVisitors = numOfVisitors;
		this.email = email;
		this.orderNumber = orderNumber;
		this.nameOnOrder=nameOnOrder;
		this.ID=ID;
		this.prePaid=prepaid;
		this.Payment=payment;
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

	
	
	



	public String getPayment() {
		return Payment;
	}




	public void setPayment(String payment) {
		Payment = payment;
	}




	public String getPrePaid() {
		return prePaid;
	}




	public void setPrePaid(String prePaid) {
		this.prePaid = prePaid;
	}




	@Override
	public String toString() {
		return "TourGuideOrder [parkName=" + parkName + ", time=" + time + ", date=" + date + ", NumOfVisitors="
				+ NumOfVisitors + ", email=" + email + ", orderNumber=" + orderNumber + ", nameOnOrder=" + nameOnOrder
				+ ", ID=" + ID + "]";
	}
	
	
}
