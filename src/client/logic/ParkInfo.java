package client.logic;

import java.io.Serializable;

public class ParkInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String numberOfPark;
	private String maxVisitors;
	private String gapOfVisitors;
	private String maxHourToVisit;
	private String numOfSub;
	private String discountDates[][];
	private String Role;
	private String currentVisitors;

	private boolean changeSettingToTrue = false;

	public ParkInfo(String numberOfPark, String maxVisitors, String gapOfVisitors, String maxHourToVisit,
			String numOfSu) {
		this.numberOfPark = numberOfPark;
		this.maxVisitors = maxVisitors;
		this.gapOfVisitors = gapOfVisitors;
		this.maxHourToVisit = maxHourToVisit;
		this.numOfSub = numOfSu;

	}

	public ParkInfo(String[][] discountDates, String numberOfPark, String maxVisitors, String gapOfVisitors,
			String maxHourToVisit, String Role) {
		this.discountDates = discountDates;
		this.numberOfPark = numberOfPark;
		this.maxVisitors = maxVisitors;
		this.gapOfVisitors = gapOfVisitors;
		this.maxHourToVisit = maxHourToVisit;
		this.Role = Role;
	}

	public boolean isChangeSettingToTrue() {
		return changeSettingToTrue;
	}

	public void setChangeSettingToTrue(boolean changeSettingToTrue) {
		this.changeSettingToTrue = changeSettingToTrue;
	}

	public void setNumberOfPark(String numberOfPark) {
		this.numberOfPark = numberOfPark;
	}

	public void setDiscountDates(String[][] discountDates) {
		this.discountDates = discountDates;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getMaxVisitors() {
		return maxVisitors;
	}

	public String getRole() {
		return Role;
	}

	public void setMaxVisitors(String maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	public String getGapOfVisitors() {
		return gapOfVisitors;
	}

	public String[][] getDiscountDates() {
		return discountDates;
	}

	public String getNumberOfPark() {
		return numberOfPark;
	}

	public void setDatesDiscount(String[][] discountDates) {
		this.discountDates = discountDates;
	}

	public void setGapOfVisitors(String gapOfVisitors) {
		this.gapOfVisitors = gapOfVisitors;
	}

	public String getMaxHourToVisit() {
		return maxHourToVisit;
	}

	public void setMaxHourToVisit(String maxHourToVisit) {
		this.maxHourToVisit = maxHourToVisit;
	}

	public String getNumOfSub() {
		return numOfSub;
	}

	public void setNumOfSub(String numOfSub) {
		this.numOfSub = numOfSub;
	}
	public String getCurrentVisitors() {
		return currentVisitors;
	}

	public void setCurrentVisitors(String currentVisitors) {
		this.currentVisitors = currentVisitors;
	}


}
