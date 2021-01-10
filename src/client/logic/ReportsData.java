package client.logic;

import java.io.Serializable;

public class ReportsData  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subCancel = null;
	private String regularCancel = null;
	private String guideCancel = null;
	private String subnotArrived = null;
	private String regularnotArrived = null;
	private String guidenotArrived = null;
	private String subArrived = null;
	private String regularArrived = null;
	private String guideArrived = null;
	private String parkNumber = null;
	private String date;
	private String[][] amountPerOrderKind;
	
	public String[][] getAmountPerOrderKind() {
		return amountPerOrderKind;
	}
	public void setAmountPerOrderKind(String[][] amountPerOrderKind) {
		this.amountPerOrderKind = amountPerOrderKind;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getParkNumber() {
		return parkNumber;
	}
	public void setParkNumber(String parkNumber) {
		this.parkNumber = parkNumber;
	}
	public String getSubCancel() {
		return subCancel;
	}
	public void setSubCancel(String subCancel) {
		this.subCancel = subCancel;
	}
	public String getSubnotArrived() {
		return subnotArrived;
	}
	public void setSubnotArrived(String subnotArrived) {
		this.subnotArrived = subnotArrived;
	}
	public String getSubArrived() {
		return subArrived;
	}
	public void setSubArrived(String subArrived) {
		this.subArrived = subArrived;
	}
	public String getRegularCancel() {
		return regularCancel;
	}
	public void setRegularCancel(String regularCancel) {
		this.regularCancel = regularCancel;
	}
	public String getGuideCancel() {
		return guideCancel;
	}
	public void setGuideCancel(String guideCancel) {
		this.guideCancel = guideCancel;
	}
	public String getRegularnotArrived() {
		return regularnotArrived;
	}
	public void setRegularnotArrived(String regularnotArrived) {
		this.regularnotArrived = regularnotArrived;
	}
	public String getGuidenotArrived() {
		return guidenotArrived;
	}
	public void setGuidenotArrived(String guidenotArrived) {
		this.guidenotArrived = guidenotArrived;
	}
	public String getRegularArrived() {
		return regularArrived;
	}
	public void setRegularArrived(String regularArrived) {
		this.regularArrived = regularArrived;
	}
	public String getGuideArrived() {
		return guideArrived;
	}
	public void setGuideArrived(String guideArrived) {
		this.guideArrived = guideArrived;
	}
	
	
}
