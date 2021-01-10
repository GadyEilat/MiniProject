package client.logic;

import java.io.Serializable;

/**
 * ParkInfo class. The class implements Serializable which transmits the
 * information from the client to the server. The class is responsible for
 * holding the information of the park.
 * 
 * @author Liran Amilov
 */

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

	/**
	 * ParkInfo method. A constructor that contains specific fields.
	 * 
	 * @param numberOfPark
	 * @param maxVisitors
	 * @param gapOfVisitors
	 * @param maxHourToVisit
	 * @param numOfSu
	 */

	public ParkInfo(String numberOfPark, String maxVisitors, String gapOfVisitors, String maxHourToVisit,
			String numOfSu) {
		this.numberOfPark = numberOfPark;
		this.maxVisitors = maxVisitors;
		this.gapOfVisitors = gapOfVisitors;
		this.maxHourToVisit = maxHourToVisit;
		this.numOfSub = numOfSu;
	}

	/**
	 * ParkInfo method. A constructor that contains specific fields.
	 * 
	 * @param discountDates
	 * @param numberOfPark
	 * @param maxVisitors
	 * @param gapOfVisitors
	 * @param maxHourToVisit
	 * @param Role
	 */

	public ParkInfo(String[][] discountDates, String numberOfPark, String maxVisitors, String gapOfVisitors,
			String maxHourToVisit, String Role) {
		this.discountDates = discountDates;
		this.numberOfPark = numberOfPark;
		this.maxVisitors = maxVisitors;
		this.gapOfVisitors = gapOfVisitors;
		this.maxHourToVisit = maxHourToVisit;
		this.Role = Role;
	}

	/**
	 * isChangeSettingToTrue method.
	 * 
	 * @return changeSettingToTrue
	 */

	public boolean isChangeSettingToTrue() {
		return changeSettingToTrue;
	}

	/**
	 * setChangeSettingToTrue method. The method inserts the changeSettingToTrue
	 * into the changeSettingToTrue of the object.
	 * 
	 * @param changeSettingToTrue
	 */

	public void setChangeSettingToTrue(boolean changeSettingToTrue) {
		this.changeSettingToTrue = changeSettingToTrue;
	}

	/**
	 * setNumberOfPark method. The method inserts the numberOfPark into the
	 * numberOfPark of the object.
	 * 
	 * @param numberOfPark
	 */

	public void setNumberOfPark(String numberOfPark) {
		this.numberOfPark = numberOfPark;
	}

	/**
	 * setDiscountDates method. The method inserts the discountDates into the
	 * discountDates of the object.
	 * 
	 * @param discountDates
	 */

	public void setDiscountDates(String[][] discountDates) {
		this.discountDates = discountDates;
	}

	/**
	 * setRole method. The method inserts the role into the role of the object.
	 * 
	 * @param role
	 */

	public void setRole(String role) {
		Role = role;
	}

	/**
	 * getMaxVisitors method.
	 * 
	 * @return maxVisitors
	 */

	public String getMaxVisitors() {
		return maxVisitors;
	}

	/**
	 * getRole method.
	 * 
	 * @return Role
	 */

	public String getRole() {
		return Role;
	}

	/**
	 * setMaxVisitors method. The method inserts the maxVisitors into the
	 * maxVisitors of the object.
	 * 
	 * @param maxVisitors
	 */

	public void setMaxVisitors(String maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	/**
	 * getGapOfVisitors method.
	 * 
	 * @return gapOfVisitors
	 */

	public String getGapOfVisitors() {
		return gapOfVisitors;
	}

	/**
	 * getDiscountDates method.
	 * 
	 * @return discountDates
	 */

	public String[][] getDiscountDates() {
		return discountDates;
	}

	/**
	 * getNumberOfPark method.
	 * 
	 * @return numberOfPark
	 */

	public String getNumberOfPark() {
		return numberOfPark;
	}

	/**
	 * setDatesDiscount method. The method inserts the discountDates into the
	 * discountDates of the object.
	 * 
	 * @param discountDates
	 */

	public void setDatesDiscount(String[][] discountDates) {
		this.discountDates = discountDates;
	}

	/**
	 * setGapOfVisitors method. The method inserts the gapOfVisitors into the
	 * gapOfVisitors of the object.
	 * 
	 * @param gapOfVisitors
	 */

	public void setGapOfVisitors(String gapOfVisitors) {
		this.gapOfVisitors = gapOfVisitors;
	}

	/**
	 * getMaxHourToVisit method.
	 * 
	 * @return maxHourToVisit
	 */

	public String getMaxHourToVisit() {
		return maxHourToVisit;
	}

	/**
	 * setMaxHourToVisit method. The method inserts the maxHourToVisit into the
	 * maxHourToVisit of the object.
	 * 
	 * @param maxHourToVisit
	 */

	public void setMaxHourToVisit(String maxHourToVisit) {
		this.maxHourToVisit = maxHourToVisit;
	}

	/**
	 * getNumOfSub method.
	 * 
	 * @return numOfSub
	 */

	public String getNumOfSub() {
		return numOfSub;
	}

	/**
	 * setNumOfSub method. The method inserts the numOfSub into the numOfSub of the
	 * object.
	 * 
	 * @param numOfSub
	 */

	public void setNumOfSub(String numOfSub) {
		this.numOfSub = numOfSub;
	}

	/**
	 * getCurrentVisitors method.
	 * 
	 * @return currentVisitors
	 */

	public String getCurrentVisitors() {
		return currentVisitors;
	}

	/**
	 * setCurrentVisitors method. The method inserts the currentVisitors into the
	 * currentVisitors of the object.
	 * 
	 * @param currentVisitors
	 */

	public void setCurrentVisitors(String currentVisitors) {
		this.currentVisitors = currentVisitors;
	}

}
