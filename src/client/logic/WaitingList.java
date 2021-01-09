package client.logic;

import java.io.Serializable;

public class WaitingList implements Serializable {

	private static final long serialVersionUID = 1L;
	private String parkName;
	private String time;
	private String date;
	private String NumOfVisitors;
	private String email;
	private String orderNumber;
	private String nameOnOrder;
	private String ID;
	private String timeOfEntrance;
	private String dateOfEntrance;
    private String orderKind;
    private String needsToApprove;

	

	


	public WaitingList(String parkName, String time, String date, String numOfVisitors, String email,
			String orderNumber, String nameOnOrder, String iD, String timeofenterence, String dateofentrance, String orderKind, String needsToApprove) {
		this.parkName = parkName;
		this.time = time;
		this.date = date;
		this.NumOfVisitors = numOfVisitors;
		this.email = email;
		this.orderNumber = orderNumber;
		this.nameOnOrder = nameOnOrder;
		this.ID = iD;
		this.timeOfEntrance= timeofenterence;
		this.dateOfEntrance=dateofentrance;
		this.orderKind=orderKind;
		this.needsToApprove=needsToApprove;
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

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getNameOnOrder() {
		return nameOnOrder;
	}

	public String getID() {
		return ID;
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

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setNameOnOrder(String nameOnOrder) {
		this.nameOnOrder = nameOnOrder;
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	
	public String getDateOfEntrance() {
		return dateOfEntrance;
	}
	
	public void setDateOfEntrance(String dateOfEntrance) {
		this.dateOfEntrance = dateOfEntrance;
	}


	public String getTimeOfEntrance() {
		return timeOfEntrance;
	}

	public void setTimeOfEntrance(String timeOfEnterence) {
		this.timeOfEntrance = timeOfEnterence;
	}



	public String getNeedsToApprove() {
		return needsToApprove;
	}


	public void setNeedsToApprove(String needsToApprove) {
		this.needsToApprove = needsToApprove;
	}
	
	public String getOrderKind() {
		return orderKind;
	}

	public void setOrderKind(String orderKind) {
		this.orderKind = orderKind;
	}
	
	
	

	@Override
	public String toString() {
		return "WaitingList [parkName=" + parkName + ", time=" + time + ", date=" + date + ", NumOfVisitors="
				+ NumOfVisitors + ", email=" + email + ", orderNumber=" + orderNumber + ", nameOnOrder=" + nameOnOrder
				+ ", ID=" + ID + "]";
	}
	
	
	
	
	
}
