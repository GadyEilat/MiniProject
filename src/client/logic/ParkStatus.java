package client.logic;

import java.io.Serializable;

public class ParkStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Date;
	private String Park;
	private String amount;
	private String maxAmount;
	private String Discount;
	

	
	public ParkStatus(String date, String park, String amount, String maxamount, String discount) {
		//super();
		Date = date;
		Park = park;
		this.amount = amount;
		this.maxAmount=maxamount;
		this.Discount=discount;
	}
	public String getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}
	public String getDate() {
		return Date;
	}
	public String getPark() {
		return Park;
	}
	public String getAmount() {
		return amount;
	}
	public void setDate(String date) {
		Date = date;
	}
	public void setPark(String park) {
		Park = park;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDiscount() {
		return Discount;
	}
	public void setDiscount(String discount) {
		Discount = discount;
	}
	
	
	
	
	
	
	
}
