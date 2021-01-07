package client.logic;

import java.io.Serializable;

public class casualOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Park;
	private String Date;
	private String Time;
	private String OrderKind;
	private String Payment;
	private String ExitTime;
	private String orderNumber;
	

	
	public casualOrder(String park, String date, String time, String orderKind, String payment, String exittime, String ordernumber) {
		super();
		Park = park;
		Date = date;
		Time = time;
		OrderKind = orderKind;
		Payment = payment;
		ExitTime=exittime;
		orderNumber=ordernumber;
	}
	public String getPark() {
		return Park;
	}
	public String getDate() {
		return Date;
	}
	public String getTime() {
		return Time;
	}
	public String getOrderKind() {
		return OrderKind;
	}
	public String getPayment() {
		return Payment;
	}
	public void setPark(String park) {
		Park = park;
	}
	public void setDate(String date) {
		Date = date;
	}
	public void setTime(String time) {
		Time = time;
	}
	public void setOrderKind(String orderKind) {
		OrderKind = orderKind;
	}
	public void setPayment(String payment) {
		Payment = payment;
	}
	public String getExitTime() {
		return ExitTime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setExitTime(String exitTime) {
		ExitTime = exitTime;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
